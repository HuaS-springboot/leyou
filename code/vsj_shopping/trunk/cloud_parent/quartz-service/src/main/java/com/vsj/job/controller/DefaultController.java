package com.vsj.job.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultController {

	@RequestMapping(value="/index")
	public String index(){			
		return "/index.html";
	}

}
