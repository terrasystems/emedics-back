package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class UserMapperTest {

    @Test
    @Transactional
    public void TestToEntity() {
        UserMapper userMapper = new UserMapper();
        UserDto userDto = new UserDto();
        userDto.setEmail("testEmail");
        userDto.setPassword("testPassword");
        User user = userMapper.toEntity(userDto);

        Assert.assertEquals(user.getEmail(), "testEmail");
        Assert.assertEquals(user.getPassword(), "testPassword");
    }

    @Test
    @Transactional
    public void TestToDto() {
        UserMapper userMapper = new UserMapper();
        User user = new User();
        user.setEmail("testEmail");
        user.setPassword("testPassword");
        UserDto userDto = userMapper.toDTO(user);

        Assert.assertEquals(userDto.getEmail(), "testEmail");
        Assert.assertEquals(userDto.getPassword(), "testPassword");
    }

}
