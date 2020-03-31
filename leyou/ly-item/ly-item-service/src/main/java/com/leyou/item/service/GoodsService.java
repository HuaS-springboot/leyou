package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.*;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult<Spu> querySpuByPage(Integer page,Integer rows,Boolean saleable,String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().andLike("name",'%'+key+'%');
        }
        if(saleable != null){
            example.createCriteria().andEqualTo("saleable",saleable);
        }
        //排序
//        example.setOrderByClause("updata_time DESC");

        //查询商品
        List<Spu> spus = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FIND);
        }
        //id转化为name
        loadCategoryAndBrandName(spus);

        //处理分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return  new  PageResult<>(info.getTotal(),spus);
    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        //处理分类
        for(Spu spu:spus){
            List<Long> ids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
            List<Category> categories = categoryService.queryCategoryByIds(ids);
            /*
把查询出来的集合对象转化为stream流，
并调用map()方法；--> 先从Category对象中取出getName这一列，并把Category对象类型转化成String类型
             */
            Stream<String> stringStream = categories.stream().map(Category::getName);
            //继续调用流的collect方法，把stream流转化成集合
            List<String> names = stringStream.collect(Collectors.toList());
//把数组中的元素连接成一个字符串返回，-->StringUtils.join({"as","df","gh","jk"})) == "as df gh jk"
            spu.setCname(StringUtils.join(names,"/"));

            //处理品牌
            Brand brand = brandService.queryBrandById(spu.getBrandId());
            spu.setBname(brand.getName());
        }

    }

    @Transactional
    public void saveGoods(Spu spu) {
        //新增spu
        spu.setCreateTime(new Date());
        spu.setId(null);
        spu.setSaleable(true);
        spu.setUpdateTime(spu.getCreateTime());
        int i = spuMapper.insert(spu);
        if(i != 1){
            throw new LyException(ExceptionEnum.SAVE_GOODS_ERROR);
        }
        //新增spuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        int insert = spuDetailMapper.insert(spuDetail);
        if(insert !=1){
            throw new LyException(ExceptionEnum.SAVE_GOODS_ERROR);
        }
        //新增sku
        List<Sku> skus = spu.getSkus();
        for(Sku sku:skus){
            sku.setCreateTime(new Date());
            sku.setEnable(true);
            sku.setId(null);
            sku.setSpuId(spu.getId());
            sku.setUpdateTime(sku.getCreateTime());
            i = skuMapper.insert(sku);
            if(i != 1){
                throw new LyException(ExceptionEnum.SAVE_GOODS_ERROR);
            }
        }
        //发送MQ消息
        amqpTemplate.convertAndSend("item.insert",spu.getId());
    }

    @Transactional
    public void updateGoods(Spu spu) {
        //删除sku
        if(spu.getId() == null){
            throw new LyException(ExceptionEnum.SPU_ID_NOT_NULL);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skus = skuMapper.select(sku);
        if(!CollectionUtils.isEmpty(skus)){
            skuMapper.delete(sku);
        }
        //修改spu
        spu.setSaleable(null);
        spu.setUpdateTime(new Date());
        spu.setCreateTime(null);
        int i = spuMapper.updateByPrimaryKey(spu);
        if(i == 0){
            throw new LyException(ExceptionEnum.UPDATE_SPU_ERROR);
        }
        //修改spuDetail
        i = spuDetailMapper.updateByPrimaryKey(spu.getSpuDetail());
        if(i == 0){
            throw new LyException(ExceptionEnum.UPDATE_SPU_ERROR);
        }
        //新增sku
        List<Sku> skuList = spu.getSkus();
        if(!CollectionUtils.isEmpty(skuList)){
            for(Sku s: skuList){
                i = skuMapper.insert(s);
                if(i == 0){
                    throw new LyException(ExceptionEnum.UPDATE_SPU_ERROR);
                }
            }
        }
        //发送MQ消息
        amqpTemplate.convertAndSend("item.update",spu.getId());

    }

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuId);
        SpuDetail detail = spuDetailMapper.selectByPrimaryKey(spuDetail);
        if(detail == null){
            throw new LyException(ExceptionEnum.SPU_DETAIL_NULL);
        }
        return detail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.SKU_NOT_FIND);
        }
        return skuList;
    }

    public Spu querySkuById(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu == null){
            throw new LyException(ExceptionEnum.SPU_NOT_FIND);
        }
        spu.setSkus(querySkuBySpuId(id));
        spu.setSpuDetail(querySpuDetailBySpuId(id));
        return spu;
    }
}
