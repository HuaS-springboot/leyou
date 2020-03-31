package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecParamsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.aspectj.weaver.ast.Var;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecParamsClient specificationClient;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate template;

    public Goods buildGoods(Spu spu){
        //查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND) ;
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if(brand == null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        //搜素字段
        String all = spu.getSubTitle()+StringUtils.join(names, " ")+brand.getName();
        //查询sku
        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());
        if(CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.SKU_NOT_FIND);
        }
        Set<Long> priceList = skus.stream().map(Sku::getPrice).collect(Collectors.toSet());
        //处理skus
        List<Map<String,Object>> skuList = new ArrayList<>();
        for(Sku sku:skus){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            skuList.add(map);
        }
        //查询商品规格参数
        List<SpecParam> specParams = specificationClient.querySpecParamsList(null, spu.getCid3(), true);
        if(CollectionUtils.isEmpty(specParams)){
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FIND);
        }
        //查询商品详情
        SpuDetail spuDetail = goodsClient.querySpuDetailBySpuId(spu.getId());
        if(spuDetail == null){
            throw new LyException(ExceptionEnum.SPU_DETAIL_NULL);
        }
        //获取通用规格参数
        Map<Long, String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //获取特有规格参数
        String json = spuDetail.getSpecialSpec();
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(json, new TypeReference<Map<Long, List<String>>>(){});

        //规格参数，key是规格参数的名字，值是规格参数的值
        Map<String, Object> specs = new HashMap<>();
        for(SpecParam Param: specParams){
            //规格名称
            String key = Param.getName();
            Object value="";
            //判断是否是通用规格
            if(Param.getGeneric()){
                value = genericSpec.get(Param.getId());
                //判断是否是数值类型
                if(Param.getNumeric()){
                    //把数组类型处理成段
                    value = chooseSegment(value.toString(),Param);
                }
            }else{
                value = specialSpec.get(Param.getId());
            }
            //存入map
            specs.put(key,value);
        }


        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(new Date());
        goods.setId(spu.getId());
        goods.setAll(all); //  搜素字段，包含标题，分类，品牌，规格等
        goods.setPrice(priceList);// 所有sku的价格集
        goods.setSkus(JsonUtils.toString(skuList)); // 所有的sku集合的json格式
        goods.setSpecs(specs);// 所有可搜索的规格参数
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }

    private String chooseSegment(Object value, SpecParam p) {
        if(value == null || StringUtils.isBlank(value.toString())){
            return "其它";
        }
        double val = NumberUtils.toDouble(value.toString());
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> querySearchPage(SearchRequest request) {
        String key = request.getKey();
        Integer page = request.getPage()-1;
        String aggCategory = "Category_agg";
        String aggBrands = "Brands_agg";
        //创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page,request.getSize()));
        //聚合分类
        queryBuilder.addAggregation(AggregationBuilders.terms(aggCategory).field("cid3"));
        //聚合品牌
        queryBuilder.addAggregation(AggregationBuilders.terms(aggBrands).field("brandId"));
        //过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all",key));
        //查询
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        //解析分页结果
        long totalElements = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<Goods> goods = result.getContent();
        //解析聚合结果
        Aggregations aggregations = result.getAggregations();
        List<Category> categories = parseCategoryAggs(aggregations.get(aggCategory));
        List<Brand> brands =parseBrandAggs(aggregations.get(aggBrands));

        return new SearchResult(totalElements, totalPages, goods, categories, brands);
    }

    private List<Brand> parseBrandAggs(LongTerms  terms) {
        try {
            List<LongTerms.Bucket> buckets = terms.getBuckets();
            List<Long> brandIds = buckets.stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brands = brandClient.queryBrandByIds(brandIds);
            return brands;
        }catch (Exception e){
            log.error("[搜索服务]查询品牌异常");
            return null;
        }
    }

    private List<Category> parseCategoryAggs(LongTerms terms) {
        try {
            List<Long> collect = terms.getBuckets().stream().map(c -> c.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Category> categories = categoryClient.queryCategoryByIds(collect);
            return categories;
        }catch (Exception e){
            log.error("[搜索服务]查询分类异常");
            return null;
        }
    }


    public void insertAndFUpdate(Long spuId) {
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        //构建goods对象
        Goods goods = buildGoods(spu);
        //把goods对象存入索引库
        goodsRepository.save(goods);
    }

    public void delete(Long spuId) {
        goodsRepository.deleteById(spuId);
    }
}
