package com.terrasystems.emedics.security.token;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class TokenUtil {


    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SEPARATOR = ":";
    private static final String SEPARATOR_SPLITTER = ":";

    private final Mac hmac;

    public TokenUtil(byte[] secretKey) {
        try {
            hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
    }

    public String parseUserFromToken(String token) {
        final String[] parts = token.split(SEPARATOR_SPLITTER);
        if (parts.length == 3 && parts[0].length() > 0 && parts[1].length() > 0) {
            final String email = parts[0];
            return email;

                }
        return null;
    }



    public String createTokenForUser(User user) {

        final StringBuilder sb = new StringBuilder(170);
        sb.append(user.getEmail());
        sb.append(SEPARATOR);
        sb.append(user.getPassword());
        sb.append(SEPARATOR);
        sb.append(user.getDiscriminatorValue());
        return sb.toString();
    }

    private User fromJSON(final byte[] userBytes) {
        try {
            return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] toJSON(User user) {
        try {
            return new ObjectMapper().writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toBase64(byte[] content) {
        return DatatypeConverter.printBase64Binary(content);
    }

    private byte[] fromBase64(String content) {
        return DatatypeConverter.parseBase64Binary(content);
    }


    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }
}
