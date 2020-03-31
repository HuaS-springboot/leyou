package com.vsj.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.model.VsjMember;
import com.vsj.service.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/member", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MemberController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMemberService memberServiceImpl;

	@RequestMapping("findMemberById")
	public BaseResponseParam<VsjMember> findMemberById(@RequestParam("id") int id) {
		logger.info("接收到会员信息查询请求,id={}",id);
		VsjMember member = memberServiceImpl.findMemberById(id);
		logger.info("会员信息查询请求处理完成,member={}",JSON.toJSONString(member));
		if(null == member){
			return BaseResponseParam.fail("未查询到会员信息");
		}else{
			return BaseResponseParam.success(member);
		}
	}

}
