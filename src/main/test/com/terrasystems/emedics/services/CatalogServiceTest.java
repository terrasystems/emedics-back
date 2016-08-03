package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Event;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Mock
    UserRepository userRepository;
    @Mock
    EventRepository eventRepository;

    @InjectMocks
    CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

    @Test
    public void test_will_return_template_not_found_for_method_getTemplateById() throws IOException {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(templateRepository.findOne("")).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getTemplateById("").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getTemplateById("").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getTemplateById("").getMsg());
    }

    @Test
    public void test_will_return_template_by_id() throws IOException {
        Template templateEntity = new Template();
        templateEntity.setBody("{}");
        TemplateMapper mapper = TemplateMapper.getInstance();
        TemplateDto template = mapper.toDto(templateEntity);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);

        when(templateRepository.findOne("")).thenReturn(templateEntity);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getTemplateById("").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getTemplateById("").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getTemplateById("").getMsg());

    }

    @Test
    public void test_will_return_all_templatesForPatient() {
        List<Template> templates = new ArrayList<>();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
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
        criteriaDto.setSearch("a");
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
    public void test_will_return_criteria_search_is_null() {
        CriteriaDto criteriaDto = new CriteriaDto();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.getAllTemplates(criteriaDto).getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.getAllTemplates(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.getAllTemplates(criteriaDto).getMsg());
    }

    @Test
    public void test_will_return_template_not_found_for_preview() throws IOException {
        Template template = null;
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);

        when(templateRepository.findOne("id")).thenReturn(template);
        when(utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.previewTemplate("id").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.previewTemplate("id").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.previewTemplate("id").getMsg());
    }

    @Test
    public void test_will_return_template_for_preview() throws IOException {
        Template templateEntity = new Template();
        templateEntity.setBody("{}");
        TemplateMapper mapper = TemplateMapper.getInstance();
        TemplateDto template = mapper.toDto(templateEntity);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);

        when(templateRepository.findOne("id")).thenReturn(templateEntity);
        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.previewTemplate("id").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.previewTemplate("id").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.previewTemplate("id").getMsg());
    }

    @Test
    public void test_will_return_user_not_found_for_method_usedByUser() {
        User user = null;
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);

        when(userRepository.findOne("user")).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), catalogServiceImpl.usedByUser("user").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.usedByUser("user").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.usedByUser("user").getMsg());
    }

    @Test
    public void test_will_return_used_by_user_for_patient() {
        User user = new User();
        user.setUserType(UserType.PATIENT);
        Set<TemplateDto> templates = new HashSet<>();
        List<Event> eventsNew = new ArrayList<>();
        List<Event> eventsProcessed = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);

        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates)).thenReturn(responseDto);
        when(userRepository.findOne("user")).thenReturn(user);
        when(eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.NEW)).thenReturn(eventsNew);
        when(eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.PROCESSED)).thenReturn(eventsProcessed);

        assertEquals(responseDto.getResult(), catalogServiceImpl.usedByUser("user").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.usedByUser("user").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.usedByUser("user").getMsg());
    }

    @Test
    public void test_will_return_used_by_user_for_not_patient() {
        User user = new User();
        user.setUserType(UserType.DOCTOR);
        Set<TemplateDto> templates = new HashSet<>();
        List<Event> eventsNew = new ArrayList<>();
        List<Event> eventsProcessed = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);

        when(utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates)).thenReturn(responseDto);
        when(userRepository.findOne("user")).thenReturn(user);
        when(eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.NEW)).thenReturn(eventsNew);
        when(eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.PROCESSED)).thenReturn(eventsProcessed);

        assertEquals(responseDto.getResult(), catalogServiceImpl.usedByUser("user").getResult());
        assertEquals(responseDto.getState(), catalogServiceImpl.usedByUser("user").getState());
        assertEquals(responseDto.getMsg(), catalogServiceImpl.usedByUser("user").getMsg());
    }
}
