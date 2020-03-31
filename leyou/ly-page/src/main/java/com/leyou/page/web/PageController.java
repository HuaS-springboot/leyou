package com.leyou.page.web;

import com.leyou.page.service.PageService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model){
        Map<String,Object> spuMap = pageService.loadPage(id);
        model.addAttribute("spuMap",spuMap);
        return "item";
    }

}
