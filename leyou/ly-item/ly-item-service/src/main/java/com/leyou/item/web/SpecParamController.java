package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecParamService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.genid.GenIdUtil;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService;

    /**
     * 动态的查询规格参数列表
     * @param gid 规格组id
     * @param cid 商品分类id
     * @param searching 搜索条件
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParamsList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching){
        List<SpecParam> specParams = specParamService.querySpecParamsList(gid, cid, searching);
        return ResponseEntity.ok(specParams);
    }

    /**
     * 根据cid查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("/group")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@RequestParam("cid") Long cid){
        return ResponseEntity.ok(specParamService.queryLBistyCid(cid));
    }

}
