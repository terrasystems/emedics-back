package com.terrasystems.emedics.security.token;


import com.terrasystems.emedics.security.UserAuthentication;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenAuthService {
     void addAuthentication(HttpServletResponse response, UserAuthentication authentication);
     Authentication getAuthentication(HttpServletRequest request);
}
