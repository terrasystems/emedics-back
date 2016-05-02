package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.PatientDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class PatientMapperTest {


    @Test
    @Transactional
    public void TestToEntity() {
        PatientMapper patientMapper = new PatientMapper();
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail("test_email");
        patientDto.setPassword("test_password");
        patientDto.setAllowedFormsCount(5);
        Patient patient = patientMapper.toEntity(patientDto);

        Assert.assertEquals(patient.getEmail(), "test_email");
        Assert.assertEquals(patient.getPassword(), "test_password");
        Assert.assertEquals(patient.getAllowedFormsCount(), 5);
    }

    @Test
    @Transactional
    public void TestToDto() {
        PatientMapper patientMapper = new PatientMapper();
        Patient patient = new Patient();
        patient.setEmail("test_email");
        patient.setPassword("test_password");
        patient.setAllowedFormsCount(5);
        PatientDto patientDto = patientMapper.toDto(patient);

        Assert.assertEquals(patientDto.getEmail(), "test_email");
        Assert.assertEquals(patientDto.getPassword(), "test_password");
        Assert.assertEquals(patientDto.getAllowedFormsCount(), 5);
    }
}
