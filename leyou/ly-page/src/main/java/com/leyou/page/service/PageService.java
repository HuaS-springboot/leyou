package com.leyou.page.service;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecParamsClient;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class PageService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecParamsClient paramsClient;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String,Object> loadPage(Long spuId){
        Map<String,Object> map = new HashMap<>();
        Spu spu = goodsClient.querySpuById(spuId);
        List<Sku> skus = spu.getSkus();
        SpuDetail spuDetail = spu.getSpuDetail();
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<SpecGroup> groups = paramsClient.queryGroupByCid(spu.getCid3());
        map.put("spu",spu);
        map.put("skus",skus);
        map.put("spuDetail",spuDetail);
        map.put("brand",brand);
        map.put("categories",categories);
        map.put("groups",groups);
        return map;
    }

    public void creatHTML(Long spuId) {
        Context context = new Context();
        context.setVariables(loadPage(spuId));
        File file = new File("E:\\upload",spuId+".html");
        if(file.exists()){
            file.delete();
        }
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")){
            templateEngine.process("item",context,writer );
        }catch (Exception e){
            log.error("静态页生成异常");
        }
    }

    public void deleteHTML(Long spuId) {
        File file = new File("E:\\upload",spuId+".html");
        if(file.exists()){
            file.delete();
        }
    }
}
