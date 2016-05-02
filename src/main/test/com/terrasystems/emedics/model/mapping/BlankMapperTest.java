package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.BlankDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class BlankMapperTest {

    @Test
    @Transactional
    public void TestToEntity() {

        BlankMapper blankMapper = new BlankMapper();
        BlankDto blankDto = new BlankDto();
        blankDto.setId("test_id");
        blankDto.setType("test_type");
        blankDto.setCategory("test_category");
        blankDto.setBody("test_body");
        blankDto.setDescr("test_descr");
        blankDto.setForms(new ArrayList<Form>());
        Blank blank = blankMapper.toEntity(blankDto);

        Assert.assertEquals(blank.getId(), "test_id" );
        Assert.assertEquals(blank.getType(), "test_type");
        Assert.assertEquals(blank.getCategory(), "test_category");
        Assert.assertEquals(blank.getBody(), "test_body");
        Assert.assertEquals(blank.getDescr(), "test_descr");
        Assert.assertEquals(blank.getForms(), new ArrayList<Form>());
    }

    @Test
    @Transactional
    public void testToDao() {

        BlankMapper blankMapper = new BlankMapper();
        Blank blank = new Blank();
        blank.setId("test_id");
        blank.setType("test_type");
        blank.setCategory("test_category");
        blank.setBody("test_body");
        blank.setDescr("test_descr");
        blank.setForms(new ArrayList<Form>());
        BlankDto blankDto = blankMapper.toDto(blank);

        Assert.assertEquals(blankDto.getId(), "test_id" );
        Assert.assertEquals(blankDto.getType(), "test_type");
        Assert.assertEquals(blankDto.getCategory(), "test_category");
        Assert.assertEquals(blankDto.getBody(), "test_body");
        Assert.assertEquals(blankDto.getDescr(), "test_descr");
        Assert.assertEquals(blankDto.getForms(), new ArrayList<Form>());
    }
}
