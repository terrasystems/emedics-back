package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
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

import static com.terrasystems.emedics.utils.UtilsImpl.allowedFormsCount;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceTest {

    @Mock
    TemplateRepository templateRepository;
    @Mock
    Utils utils;
    @Mock
    UserTemplateRepository userTemplateRepository;

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
    public void test_will_return_all_templatesForPatient() {
        List<Template> templates = new ArrayList<>();
        CriteriaDto criteriaDto = new CriteriaDto();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
        User user = new User();
        user.setUserType(UserType.PATIENT);

        when(templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch())).thenReturn(templates);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates)).thenReturn(responseDto);
        when(utils.getCurrentUser()).thenReturn(user);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getAllTemplates(criteriaDto).getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getAllTemplates(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getAllTemplates(criteriaDto).getMsg());

    }

    @Test
    public void test_will_return_all_templatesForNonPatient() {
        List<Template> templates = new ArrayList<>();
        CriteriaDto criteriaDto = new CriteriaDto();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
        User user = new User();
        user.setUserType(UserType.DOCTOR);

        when(templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch())).thenReturn(templates);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates)).thenReturn(responseDto);
        when(utils.getCurrentUser()).thenReturn(user);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getAllTemplates(criteriaDto).getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getAllTemplates(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getAllTemplates(criteriaDto).getMsg());

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

        assertEquals(responseDto.getResult(), catalogServiceImpl.addTemplate("template").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.addTemplate("template").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.addTemplate("template").getMsg());
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

        assertEquals(responseDto.getResult(), catalogServiceImpl.addTemplate("template").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.addTemplate("template").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.addTemplate("template").getMsg());
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

        assertEquals(responseDto.getResult(), catalogServiceImpl.addTemplate("template").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.addTemplate("template").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.addTemplate("template").getMsg());
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

        assertEquals(responseDto.getResult(), catalogServiceImpl.addTemplate("template").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.addTemplate("template").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.addTemplate("template").getMsg());
    }

    @Test
    public void test_will_return_template_not_found_for_preview() {
        Template template = null;
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(templateRepository.findOne("id")).thenReturn(template);
        when(utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.previewTemplate("id").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.previewTemplate("id").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.previewTemplate("id").getMsg());
    }

    @Test
    public void test_will_return_template_for_preview() {
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);

        when(templateRepository.findOne("id")).thenReturn(template);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.previewTemplate("id").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.previewTemplate("id").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.previewTemplate("id").getMsg());
    }
}
