package com.terrasystems.emedics.utils;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface Utils {
    User getCurrentUser();
    ResponseDto generateResponse(boolean state, String msg, Object result);
    boolean isPatient(User current);
    boolean isDoctor(User current);
    boolean isStaff(User current);
    boolean isOrg(User current);
}
