package com.terrasystems.emedics.security;

import com.terrasystems.emedics.security.filters.AuthenticationFilter;
import com.terrasystems.emedics.security.filters.LoginFilter;
import com.terrasystems.emedics.security.token.TokenAuthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;
    /* @Autowired
     private AuthenticationManager authenticationManager;*/
    @Autowired
    private TokenAuthServiceImp tokenAuthService;

    public SecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl();
        http.authorizeRequests()
                .antMatchers("/rest/public/**").permitAll()
                //.antMatchers(HttpMethod.POST, "/rest/public/login").permitAll()
                .antMatchers(HttpMethod.POST,"/rest/private/**").authenticated()
                //.antMatchers(HttpMethod.POST, "/rest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/**").hasRole("PATIENT").and()
                //{"username":"<name>","password":"<password>"}
                .addFilterBefore(new LoginFilter("/rest/public/login", tokenAuthService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
