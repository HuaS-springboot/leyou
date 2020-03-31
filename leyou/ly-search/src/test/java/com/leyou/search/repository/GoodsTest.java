package com.leyou.search.repository;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpecParam;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecParamsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsTest {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SpecParamsClient specParamsClient;

    @Test
    public void brandTest(){
        List<SpecParam> specParams = specParamsClient.querySpecParamsList(null, 76L, true);
        for(SpecParam specParam : specParams){
            System.out.println(specParam);
        }
//  =========================================
//        Brand brand = brandClient.queryBrandById(1115L);
//        System.out.println(brand);
// ============================================
//        List<Long> longs = Arrays.asList(1L, 2L, 3L);
//        List<Category> categories = categoryClient.queryCategoryByIds(longs);
//        for(Category category:categories){
//            System.out.println(category);
//        }
    }
}

