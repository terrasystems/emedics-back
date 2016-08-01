package com.terrasystems.emedics.utils;


import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilsImpl implements Utils {

    public static final long allowedFormsCount = 2;

    @Autowired
    UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        User user = userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return user;
    }

    @Override
    public ResponseDto generateResponse(boolean state, String msg, Object result) {
        ResponseDto responseDto = new ResponseDto(state, msg, result);
        return responseDto;
    }
}
