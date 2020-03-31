package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecParamsApi {
    /**
     * 动态的查询规格参数列表
     * @param gid 规格组id
     * @param cid 商品分类id
     * @param searching 搜索条件
     * @return
     */
    @GetMapping("/spec/params")
    List<SpecParam> querySpecParamsList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching);

    @GetMapping("spec/group")
    List<SpecGroup> queryGroupByCid(@RequestParam("cid") Long cid);
}
