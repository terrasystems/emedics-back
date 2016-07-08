import com.terrasystems.emedics.services.TemplateService;
import com.terrasystems.emedics.services.TemplateServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoadTemplateTest extends TestIT {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    TemplateServiceImpl templateService;
    @BeforeClass
    public void init() {

    }
    @Test
    public void PatientLoadTemplateITTest() {
        TemplateServiceImpl templateService=mock(TemplateServiceImpl.class,CALLS_REAL_METHODS
        );
        when(templateService.deleteUserTemplateById(any(String.class)))
                .then(inv->{



                    return null;
                }
                );
        when(templateService.getPrincipals()).thenReturn("serhiimorunov@gmail.com");
    }
}
