package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BrandApi {
    /**
     * 根据id查询品牌
     * @param id
     * @returnid
     */
    @GetMapping("/brand/id/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
    /**
     * 根据ids查询品牌列表
     * @param ids
     * @returnid
     */
    @GetMapping("/brand/list")
    List<Brand> queryBrandByIds (@RequestParam("ids") List<Long> ids);
}
