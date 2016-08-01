package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceTest {

    @Mock
    TemplateRepository templateRepository;
    @Mock
    Utils utils;

    @InjectMocks
    CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

    @Test
    public void test_will_return_template_not_found_for_method_getTemplateById() {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(templateRepository.findOne("")).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getTemplateById("").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getTemplateById("").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getTemplateById("").getMsg());
    }

    @Test
    public void test_will_return_template_by_id() {
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);

        when(templateRepository.findOne("")).thenReturn(template);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getTemplateById("").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getTemplateById("").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getTemplateById("").getMsg());

    }

    @Test
    public void test_will_return_msg_empty_list() {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST_NULL.toString(), null);
        CriteriaDto criteriaDto = new CriteriaDto();

        when(templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch())).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST_NULL.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getAllTemplates(criteriaDto).getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getAllTemplates(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getAllTemplates(criteriaDto).getMsg());
    }

    @Test
    public void test_will_return_all_templates() {
        List<Template> templates = new ArrayList<>();
        CriteriaDto criteriaDto = new CriteriaDto();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);

        when(templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch())).thenReturn(templates);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getAllTemplates(criteriaDto).getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getAllTemplates(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getAllTemplates(criteriaDto).getMsg());

    }
}
