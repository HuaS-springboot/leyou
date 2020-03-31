package com.vsj.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 过滤器
 */
@Component
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    //排除过滤的 uri 地址
    private static final String LOGIN_URI = "/admin/api/v1/user/checkLogin";
    /**
     * 过滤器的类型 pre表示请求在路由之前被过滤
     * @return 类型
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器的执行顺序
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否会被执行
     * @return true
     */
    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest  request = requestContext.getRequest();

        //只拦截管理后台
           /* if ("/admin/api/user/checkLogin".equalsIgnoreCase(request.getRequestURI())) {
                return false;
            }*/
           /* if (LOGIN_URI.equalsIgnoreCase(request.getRequestURI())){
                return false;
            }*/
        return true;
    }

    /**
     * 过滤逻辑
     * @return 过滤结果
     */
    @Override
    public Object run() {
       /* RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        logger.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());

        String token = request.getHeader("token");
        if(StringUtils.isBlank((token))){
            token  = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }else{
            requestContext.setSendZuulResponse(true);
            requestContext.setResponseStatusCode(200);
        }*/

        return null;
    }



}
