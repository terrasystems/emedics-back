package com.terrasystems.emedics.security.token;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

@Service
public class TokenAuthService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

    private final TokenUtil tokenUtil;

    @Autowired
    public TokenAuthService(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        response.addHeader(AUTH_HEADER_NAME, tokenUtil.createTokenForUser(user));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final User user = tokenUtil.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}

