package com.terrasystems.emedics.security.filters;


import com.terrasystems.emedics.security.token.TokenAuthServiceImp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    private final TokenAuthServiceImp tokenAuthService;

    public AuthenticationFilter(TokenAuthServiceImp tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        //TODO NPE
       // Authentication auth = tokenAuthService.getAuthentication(req)
        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication((HttpServletRequest) req));


        chain.doFilter(req, res);
    }
}
