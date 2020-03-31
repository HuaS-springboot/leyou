package com.leyou.page.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeLeafTest {

    @GetMapping("show")
    public String testHello(Model model){
        model.addAttribute("msg","hello thymeleaf");
        return "hello";
    }
}
