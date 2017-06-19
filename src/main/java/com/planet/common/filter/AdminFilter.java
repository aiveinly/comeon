package com.planet.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yehao on 2016/2/18.
 */
public class AdminFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String[] notFilter = new String[]{"/admin/login","/pc/index", "/admin/submitLogin", "/static/.*",
                "/wechat/.*", "/app/.*", "/personal/.*","/dict/dictlist.*",
                "/product/detail.*","/product/list.*","/req/product-req.*",
                "/req/req-file-upload.*","/config/get-config.*","/dict/pre.*","/req/getRequirement.*",
                "/pc/*.*"
        };

        String uri = request.getRequestURI();
        boolean isPC = false;
        StringBuffer url = request.getRequestURL();
        if (url!=null && (url.indexOf("local")>0|| url.indexOf("www")>0)&&(uri.indexOf("/pc/index") != -1)){
            isPC=true;
        }

        boolean doFilter = true;
        if (isPC==false)
        {
            for (String str : notFilter) {
                if (uri.indexOf(str) != -1 || uri.equals("/") || uri.matches(str)) {
                    doFilter = false;
                    break;
                }
            }
        }

        if (doFilter) {
            Object o = request.getSession().getAttribute("admin");
            if (isPC==true) {

                response.sendRedirect("/pc/index");
            } else if (o == null){

                response.sendRedirect("/admin/login");
            }

            else {

                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }


}
