package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.FormDto;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class FormMapperTest {


    /*@Test
    @Transactional
    public void TestToEntity() {
        FormMapper formMapper = new FormMapper();
        FormDto formDto = new FormDto();
        formDto.setId("test_id");
        formDto.setType("test_type");
        formDto.setCategory("test_category");
        formDto.setBody("test_body");
        formDto.setDescr("test_descr");
        Form form = formMapper.toEntity(formDto);

        Assert.assertEquals(form.getId(), "test_id" );
        //Assert.assertEquals(form.getType(), "test_type");
        //Assert.assertEquals(form.getCategory(), "test_category");
        Assert.assertEquals(form.getBody(), "test_body");
        //Assert.assertEquals(form.getDescr(), "test_descr");
    }*/

    /*@Test
    @Transactional
    public void TestToDto() {
        FormMapper formMapper = new FormMapper();
        Form form = new Form();
        form.setId("test_id");
        //form.setType("test_type");
        //form.setCategory("test_category");
        form.setBody("test_body");
        //form.setDescr("test_descr");
        FormDto formDto = formMapper.toDto(form);

        Assert.assertEquals(formDto.getId(), "test_id" );
        //Assert.assertEquals(formDto.getType(), "test_type");
        //Assert.assertEquals(formDto.getCategory(), "test_category");
        Assert.assertEquals(formDto.getBody(), "test_body");
        //Assert.assertEquals(formDto.getDescr(), "test_descr");
    }*/
}
