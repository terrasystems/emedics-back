import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.security.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:resources/testContext.xml"})
@WebAppConfiguration
public class TestIT {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    UserRepository userRepository;


    @Test
    public void dbTest() {

    }

    @Test

    public void registrationDoctorTest() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root;
        ObjectNode user = mapper.createObjectNode();
        user.put("email","serhiimorunov@gmail.com")
                .put("password", "1234")
                .put("firstName", "Sergo")
                .put("lastName", "Sergo")
                .put("username", "vasa");
        ObjectNode organization = mapper.createObjectNode();
        organization.put("name","name")
                .put("fullname","fullname")
                .put("website", "website")
                .put("address","address");
        UserDto dto = new UserDto();
        dto.setFirstName("Sergo");
        dto.setLastName("Sergo");
        dto.setEmail("serhiimorunov@gmail.com");
        dto.setPassword("1234");
        dto.setPhone("11-22-33");
        dto.setBirth(new Date());
        StateDto state = registrationService.registerDoctor(dto);
        org.junit.Assert.assertTrue(state.isValue());
        User testUser = userRepository.findByEmail(dto.getEmail());
        assertNul


    }

}
