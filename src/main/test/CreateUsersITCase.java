import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.ReferenceCreateRequest;
import com.terrasystems.emedics.services.ReferenceCreateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateUsersITCase extends TestIT {
    @Autowired
    ReferenceCreateService referenceCreateService;
    @Test
    @Transactional
    public void createPatientTest() {
        ReferenceCreateRequest request = new ReferenceCreateRequest();
        request.setBirth(new Date());
        request.setEmail("test@testmail.com");
        request.setDocType("22");
        request.setFirstName("Mora");
        request.setLastName(null);
        Patient patient = referenceCreateService.createPatient(request);
        assertNotNull(patient);
        assertEquals(request.getEmail(),patient.getEmail());
        assertEquals(request.getFirstName(), patient.getName());
    }
    @Test
    @Transactional
    public void createDoctor() {
        ReferenceCreateRequest request = new ReferenceCreateRequest();
        request.setBirth(new Date());
        request.setEmail("test2@testmail.com");
        request.setFirstName(null);
        request.setLastName(null);
        request.setDocType("21");
        Doctor doctor = referenceCreateService.createDoctor(request);
        assertNotNull(doctor);
        assertEquals(request.getEmail(), doctor.getEmail());
        assertEquals(request.getEmail(), doctor.getName());
        assertEquals(request.getDocType(), doctor.getType().getId());
    }
}
