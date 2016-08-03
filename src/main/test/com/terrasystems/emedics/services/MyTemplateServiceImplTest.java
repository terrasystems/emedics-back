package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;
import com.terrasystems.emedics.model.mapping.TemplateMapper;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyTemplateServiceImplTest {

    @InjectMocks
    MyTemplateServiceImpl myTemplateServiceImpl;

    @Mock
    Utils utils;
    @Mock
    UserTemplateRepository userTemplateRepository;
    @Mock
    TemplateRepository templateRepository;

    @Test
    public void test_will_return_request_incorrect_for_getAllMyTemplates() {
        CriteriaDto criteriaDto = new CriteriaDto();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.getAllMyTemplates(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_all_my_templates() {
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("");
        User user = new User();
        List<TemplateDto> templateDtos = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templateDtos);
        List<UserTemplate> userTemplates = new ArrayList<>();

        when(utils.getCurrentUser()).thenReturn(user);
        when(userTemplateRepository.findByUserAndTemplate_NameContainingIgnoreCase(user, criteriaDto.getSearch())).thenReturn(userTemplates);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templateDtos)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.getAllMyTemplates(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_template_not_found_for_getById() throws IOException {
        UserTemplate userTemplate = null;
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(userTemplateRepository.findOne("id")).thenReturn(userTemplate);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.getById("id");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_template_not_found() throws IOException {
        UserTemplate userTemplate = new UserTemplate();
        Template templateEntity = new Template();
        templateEntity.setBody("{}");
        userTemplate.setTemplate(templateEntity);
        TemplateMapper mapper = TemplateMapper.getInstance();
        TemplateDto template = mapper.toDto(userTemplate.getTemplate());
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);

        when(userTemplateRepository.findOne("id")).thenReturn(userTemplate);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.getById("id");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_template_not_found_for_removeMyTemplate() {
        UserTemplate userTemplate = null;
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(userTemplateRepository.findOne("id")).thenReturn(userTemplate);
        when(utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.removeMyTemplate("id");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_remove_my_template() {
        UserTemplate userTemplate = new UserTemplate();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_REMOVED.toString(), null);

        when(userTemplateRepository.findOne("id")).thenReturn(userTemplate);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_REMOVED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = myTemplateServiceImpl.removeMyTemplate("id");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_template_is_already_load() {
        Template template = new Template();
        UserTemplate userTemplate = new UserTemplate();
        template.setId("template");
        User user = new User();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_ALREADY_HAVE_THIS_TEMPL.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(templateRepository.findOne("template")).thenReturn(template);
        when(utils.generateResponse(true, MessageEnums.MSG_ALREADY_HAVE_THIS_TEMPL.toString(), null)).thenReturn(responseDto);
        when(userTemplateRepository.findByUser_IdAndTemplate_Id(user.getId(), template.getId())).thenReturn(userTemplate);

        ResponseDto responseDto1 = myTemplateServiceImpl.addMyTemplate("template");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_template_is_commercial() {
        Template template = new Template();
        template.setCommercialEnum(true);
        UserTemplate userTemplate = null;
        template.setId("template");
        User user = new User();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(templateRepository.findOne("template")).thenReturn(template);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null)).thenReturn(responseDto);
        when(userTemplateRepository.findByUser_IdAndTemplate_Id(user.getId(), template.getId())).thenReturn(userTemplate);

        ResponseDto responseDto1 = myTemplateServiceImpl.addMyTemplate("template");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_allowed_forms_count_false() {
        Template template = new Template();
        template.setCommercialEnum(false);
        UserTemplate userTemplate = null;
        template.setId("template");
        User user = new User();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(templateRepository.findOne("template")).thenReturn(template);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null)).thenReturn(responseDto);
        when(userTemplateRepository.findByUser_IdAndTemplate_Id(user.getId(), template.getId())).thenReturn(userTemplate);
        when(userTemplateRepository.countByUser_IdAndTemplate_CommercialEnumIsFalse(user.getId())).thenReturn(1l);

        ResponseDto responseDto1 = myTemplateServiceImpl.addMyTemplate("template");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_allowed_forms_count_true() {
        Template template = new Template();
        template.setCommercialEnum(false);
        UserTemplate userTemplate = null;
        template.setId("template");
        User user = new User();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_ALLOW_FORM_IS_FULL.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(templateRepository.findOne("template")).thenReturn(template);
        when(utils.generateResponse(false, MessageEnums.MSG_ALLOW_FORM_IS_FULL.toString(), null)).thenReturn(responseDto);
        when(userTemplateRepository.findByUser_IdAndTemplate_Id(user.getId(), template.getId())).thenReturn(userTemplate);
        when(userTemplateRepository.countByUser_IdAndTemplate_CommercialEnumIsFalse(user.getId())).thenReturn(5l);

        ResponseDto responseDto1 = myTemplateServiceImpl.addMyTemplate("template");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }
}
