package com.vsj.material.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    public Integer getPlatformCode() {
        String platformCode = request.getHeader("platformCode");
        return StringUtils.isBlank(platformCode) ? 1 : Integer.parseInt(platformCode);
    }

    public Integer getUserInfo() {
        String userId = request.getHeader("userId");
        return Integer.parseInt(userId);
    }

    public Integer getRegisterInfo() {
        String registerId = request.getHeader("registerId");
        return Integer.parseInt(registerId);
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
