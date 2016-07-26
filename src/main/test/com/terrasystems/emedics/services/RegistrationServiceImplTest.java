package com.terrasystems.emedics.services;

import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import com.terrasystems.emedics.security.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:resources/testContext.xml"})
@WebAppConfiguration
public class RegistrationServiceImplTest {

    @Autowired
    RegistrationService registrationService;

    @Test
    public void registerUserAdminIsNot() {
        UserDto dto = new UserDto();
        dto.setFirstName("Roman");
        dto.setLastName("Melnyk");
        dto.setDob(new Date());
        dto.setPass("qwerty");
        dto.setUserType(UserType.PATIENT);
        dto.setEmail("orakylik@ukr.net");

        registrationService.registerUser(dto);
    }
}
