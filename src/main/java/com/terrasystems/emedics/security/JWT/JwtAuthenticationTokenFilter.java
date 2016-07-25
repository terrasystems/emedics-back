package com.terrasystems.emedics.security.JWT;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("emedics")
    private UserDetailsService userDetailsService;

    @Value("X-AUTH-TOKEN")
    private String tokenHeader;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, user)) {
                /*UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));*/
                UserAuthentication userAuthentication = new UserAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
        }

        chain.doFilter(request, response);
    }
}
