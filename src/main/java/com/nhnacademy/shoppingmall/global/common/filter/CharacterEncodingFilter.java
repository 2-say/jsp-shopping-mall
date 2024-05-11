package com.nhnacademy.shoppingmall.global.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;


@WebFilter(
        urlPatterns = "/*",
        initParams= {@WebInitParam(name = "encoding", value = "UTF-8")})
public class CharacterEncodingFilter  implements Filter {

    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //todo8 UTF-8 인코딩, initParams의 encoding parameter value값을 charset 으로 지정합니다.
        if (encoding != null) {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
