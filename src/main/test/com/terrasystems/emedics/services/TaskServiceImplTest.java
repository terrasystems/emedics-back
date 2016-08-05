package com.terrasystems.emedics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.*;
import com.terrasystems.emedics.model.mapping.TaskMapper;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    @Mock
    Utils utils;
    @Mock
    TemplateRepository templateRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    EventRepository eventRepository;

    @Test
    public void test_will_return_incorrect_request_without_template() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(null);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_patient() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(null);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_fromUser() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_userType() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_will_return_you_already_have_this_task_for_doctor_in_new() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(true);
        when(utils.isOrg(fromUserEntity)).thenReturn(false);
        when(utils.isMedicalForm(template)).thenReturn(true);
        when(eventRepository.countByFromUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.NEW)).thenReturn(1l);
        when(eventRepository.countByToUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.PROCESSED)).thenReturn(0l);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_will_return_you_already_have_this_task_for_org_in_processed() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(false);
        when(utils.isOrg(fromUserEntity)).thenReturn(true);
        when(utils.isMedicalForm(template)).thenReturn(true);
        when(eventRepository.countByToUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.PROCESSED)).thenReturn(1l);
        when(eventRepository.countByFromUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.NEW)).thenReturn(0l);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_model() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        Event event = new Event();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(true);
        when(utils.isOrg(fromUserEntity)).thenReturn(false);
        when(utils.isMedicalForm(template)).thenReturn(false);
        when(eventRepository.save(event)).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_created_task_for_doctor() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        JsonNode data = new IntNode(10);
        Event event = new Event();
        taskDto.setModel(data);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_CREATED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_TASK_CREATED.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(true);
        when(utils.isOrg(fromUserEntity)).thenReturn(false);
        when(utils.isMedicalForm(template)).thenReturn(false);
        when(eventRepository.save(event)).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_created_task_for_patient() {
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        JsonNode data = new IntNode(10);
        Event event = new Event();
        taskDto.setModel(data);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_CREATED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_TASK_CREATED.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isPatient(fromUserEntity)).thenReturn(true);
        when(eventRepository.save(event)).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.createTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_getTaskById() throws IOException {
        Event event = null;
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.getTaskById("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_task_by_id() throws IOException {
        Event event = new Event();
        User fromUser = new User();
        User patient = new User();
        Template template = new Template();
        template.setBody("{}");
        event.setTemplate(template);
        event.setFromUser(fromUser);
        event.setPatient(patient);
        event.setData("{}");
        TaskMapper mapper = TaskMapper.getInstance();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_BY_ID.toString(), mapper.toDto(event));

        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.generateResponse(true, MessageEnums.MSG_TASK_BY_ID.toString(), mapper.toDto(event))).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.getTaskById("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_editTask() {
        TaskDto taskDto = new TaskDto();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(eventRepository.findOne(taskDto.getId())).thenReturn(null);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.editTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_edit_task() {
        TaskDto taskDto = new TaskDto();
        Event event = new Event();
        JsonNode jsonNode =  new IntNode(10);
        taskDto.setModel(jsonNode);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_EDITED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_TASK_EDITED.toString(), null)).thenReturn(responseDto);
        when(eventRepository.findOne(taskDto.getId())).thenReturn(event);

        ResponseDto responseDto1 = taskServiceImpl.editTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_closeTask() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(eventRepository.findOne("")).thenReturn(null);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.closeTask("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_close_task() {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_CLOSED.toString(), null);
        Event event = new Event();

        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.generateResponse(true, MessageEnums.MSG_TASK_CLOSED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.closeTask("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_multiCreateTask() {
        List<TaskDto> taskDtos = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.multiCreateTask(taskDtos);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_you_already_have_this_task_for_multiCreateTask() {
        List<TaskDto> taskDtos = new ArrayList<>();
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        JsonNode data = new IntNode(10);
        Event event = new Event();
        taskDto.setModel(data);
        taskDtos.add(taskDto);
        taskDtos.add(taskDto);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(true);
        when(utils.isOrg(fromUserEntity)).thenReturn(false);
        when(utils.isMedicalForm(template)).thenReturn(true);
        when(eventRepository.countByToUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.PROCESSED)).thenReturn(1l);
        when(eventRepository.countByFromUserAndTemplateAndStatus(fromUserEntity, template, StatusEnum.NEW)).thenReturn(0l);
        when(eventRepository.save(event)).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.multiCreateTask(taskDtos);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_multi_create_tasks() {
        List<TaskDto> taskDtos = new ArrayList<>();
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto patient = new UserDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setPatient(patient);
        taskDto.setFromUser(fromUser);
        User patientEntity = new User();
        User fromUserEntity = new User();
        Template template = new Template();
        JsonNode data = new IntNode(10);
        Event event = new Event();
        taskDto.setModel(data);
        taskDtos.add(taskDto);
        taskDtos.add(taskDto);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_CREATED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_TASK_CREATED.toString(), null)).thenReturn(responseDto);
        when(templateRepository.findOne(taskDto.getTemplateDto().getId())).thenReturn(template);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(patientEntity);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(fromUserEntity);
        when(utils.isDoctor(fromUserEntity)).thenReturn(true);
        when(utils.isOrg(fromUserEntity)).thenReturn(false);
        when(utils.isMedicalForm(template)).thenReturn(false);
        when(eventRepository.save(event)).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.multiCreateTask(taskDtos);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_event() {
        User user = new User();
        User recipient = new User();
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto userDto = new UserDto();
        taskDto.setToUser(userDto);
        taskDto.setTemplateDto(templateDto);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(null);
        when(userRepository.findOne(taskDto.getToUser().getId())).thenReturn(recipient);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.sendTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_without_recipient() {
        User user = new User();
        Event event = new Event();
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto userDto = new UserDto();
        taskDto.setToUser(userDto);
        taskDto.setTemplateDto(templateDto);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(event);
        when(userRepository.findOne(taskDto.getToUser().getId())).thenReturn(null);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.sendTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_you_cant_sent_med_form_to_pat() {
        User user = new User();
        Event event = new Event();
        Template template = new Template();
        template.setTypeEnum(TypeEnum.MEDICAL);
        event.setTemplate(template);
        User recipient = new User();
        recipient.setUserType(UserType.PATIENT);
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto userDto = new UserDto();
        taskDto.setToUser(userDto);
        taskDto.setTemplateDto(templateDto);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_YOU_CANT_SEND_MED_FORM_TO_PAT.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(event);
        when(userRepository.findOne(taskDto.getToUser().getId())).thenReturn(recipient);
        when(utils.generateResponse(false, MessageEnums.MSG_YOU_CANT_SEND_MED_FORM_TO_PAT.toString(), null)).thenReturn(responseDto);
        when(utils.isPatient(recipient)).thenReturn(true);
        when(utils.isMedicalForm(event.getTemplate())).thenReturn(true);

        ResponseDto responseDto1 = taskServiceImpl.sendTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_sent_task_to_user_and_add_to_ref() {
        User user = new User();
        Event event = new Event();
        user.setReferences(new HashSet<>());
        Template template = new Template();
        template.setTypeEnum(TypeEnum.MEDICAL);
        event.setTemplate(template);
        User recipient = new User();
        recipient.setReferences(new HashSet<>());
        recipient.setUserType(UserType.PATIENT);
        TaskDto taskDto = new TaskDto();
        UserDto patient = new UserDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto userDto = new UserDto();
        taskDto.setPatient(patient);
        taskDto.setToUser(userDto);
        taskDto.setTemplateDto(templateDto);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_SENT.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(event);
        when(userRepository.findOne(taskDto.getToUser().getId())).thenReturn(recipient);
        when(utils.generateResponse(true, MessageEnums.MSG_TASK_SENT.toString(), null)).thenReturn(responseDto);
        when(utils.isPatient(recipient)).thenReturn(false);
        when(utils.isMedicalForm(event.getTemplate())).thenReturn(false);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(recipient);

        ResponseDto responseDto1 = taskServiceImpl.sendTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_request_incorrect_without_patient() {
        User user = new User();
        Event event = new Event();
        user.setReferences(new HashSet<>());
        Template template = new Template();
        template.setTypeEnum(TypeEnum.MEDICAL);
        event.setTemplate(template);
        User recipient = new User();
        recipient.setReferences(new HashSet<>());
        recipient.setUserType(UserType.PATIENT);
        TaskDto taskDto = new TaskDto();
        UserDto patient = new UserDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto userDto = new UserDto();
        taskDto.setPatient(patient);
        taskDto.setToUser(userDto);
        taskDto.setTemplateDto(templateDto);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(event);
        when(userRepository.findOne(taskDto.getToUser().getId())).thenReturn(recipient);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(utils.isPatient(recipient)).thenReturn(false);
        when(utils.isMedicalForm(event.getTemplate())).thenReturn(false);
        when(userRepository.findOne(taskDto.getPatient().getId())).thenReturn(null);

        ResponseDto responseDto1 = taskServiceImpl.sendTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    //TODO have no idea how that method test
    /*@Test
    public void test_will_return_all_tasks_by_criteria() {
        User current = new User();
        List<Event> events = new ArrayList<>();
        TaskCriteriaDto criteria = new TaskCriteriaDto();
        Object object = new Object();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_ALL_TASKS.toString(), object);

        when(eventRepository.findAll(anyList())).thenReturn(events);
        when(utils.generateResponse(true, MessageEnums.MSG_ALL_TASKS.toString(), object)).thenReturn(responseDto);
        when(utils.getCurrentUser()).thenReturn(current);

        ResponseDto responseDto1 = taskServiceImpl.getAllTasks(criteria);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }*/

    @Test
    public void test_will_return_incorrect_request_for_assignTask() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setFromUser(fromUser);

        when(eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId())).thenReturn(null);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(null);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.assignTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_assign_task() {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TASK_ASSIGNED.toString(), null);
        TaskDto taskDto = new TaskDto();
        TemplateDto templateDto = new TemplateDto();
        User user = new User();
        UserDto fromUser = new UserDto();
        taskDto.setTemplateDto(templateDto);
        taskDto.setFromUser(fromUser);
        Event event = new Event();

        when(eventRepository.findOne(taskDto.getId())).thenReturn(event);
        when(userRepository.findOne(taskDto.getFromUser().getId())).thenReturn(user);
        when(utils.generateResponse(true, MessageEnums.MSG_TASK_ASSIGNED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = taskServiceImpl.assignTask(taskDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

}
