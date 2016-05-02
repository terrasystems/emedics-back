package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.dto.DoctorDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class DoctorMapperTest {

    @Test
    @Transactional
    public void TestToEntity() {
        DoctorMapper doctorMapper = new DoctorMapper();
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setEmail("test_email");
        doctorDto.setPassword("test_password");
        doctorDto.setClinic("test_clinic");
        Doctor doctor = doctorMapper.toEntity(doctorDto);

        Assert.assertEquals(doctor.getEmail(), "test_email");
        Assert.assertEquals(doctor.getPassword(), "test_password");
        Assert.assertEquals(doctor.getClinic(), "test_clinic");
    }

    @Test
    @Transactional
    public void TestToDto() {
        DoctorMapper doctorMapper = new DoctorMapper();
        Doctor doctor = new Doctor();
        doctor.setEmail("test_email");
        doctor.setPassword("test_password");
        doctor.setClinic("test_clinic");
        DoctorDto doctorDto = doctorMapper.toDto(doctor);

        Assert.assertEquals(doctorDto.getEmail(), "test_email");
        Assert.assertEquals(doctorDto.getPassword(), "test_password");
        Assert.assertEquals(doctorDto.getClinic(), "test_clinic");
    }
}
