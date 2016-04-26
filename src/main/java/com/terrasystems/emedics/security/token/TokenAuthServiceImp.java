package com.terrasystems.emedics.security.token;



import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;

@Service
public class TokenAuthServiceImp implements TokenAuthService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
    private static final String RESPONSE_JSON_CONTENT_TYPE = "application/json";

    private final TokenUtil tokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TokenAuthServiceImp(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }
    @Override
    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        String token = tokenUtil.createTokenForUser(user);
        response.addHeader(AUTH_HEADER_NAME, token);
        response.setContentType(RESPONSE_JSON_CONTENT_TYPE);
        try {
            response.getWriter().write(String.format("{\"email\":\"%s\",\"token\":\"%s\"}", user.getEmail(), token));
            //response.getWriter().write();
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final String email = tokenUtil.parseUserFromToken(token);
            User user = userRepository.findByEmail(email);
            if (user != null || (new Date().getTime() < user.getExpires())) {
                return new UserAuthentication(user);
            }
        }
        //TODO deadl with NullPointerException
        return null;
    }
}

