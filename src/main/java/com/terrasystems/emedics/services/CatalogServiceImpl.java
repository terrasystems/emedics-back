package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.TypeEnum;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.terrasystems.emedics.utils.UtilsImpl.allowedFormsCount;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    Utils utils;
    @Autowired
    UserTemplateRepository userTemplateRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    @Override
    @Transactional
    public ResponseDto getTemplateById(String id) {
        Template template = templateRepository.findOne(id);
        if (template == null) {
            return utils.generateResponse(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);
        }
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);
    }

    @Override
    @Transactional
    public ResponseDto getAllTemplates(CriteriaDto criteriaDto) {
        if (criteriaDto.getSearch() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        User user = utils.getCurrentUser();
        if (isPatient(user)) {
            return getAllTemplatesForPatient(criteriaDto);
        }
        List<Template> templates = templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch());
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }

    @Override
    @Transactional
    public ResponseDto createTemplate(TemplateDto templateDto) {
        return null;
    }

    @Override
    public ResponseDto previewTemplate(String id) {
        Template template = templateRepository.findOne(id);
        if (template == null) {
            return utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);
        }
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);
    }

    @Override
    @Transactional
    public ResponseDto usedByUser(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        if (isPatient(user)) {
            return usedByUserPatient(user);
        }
        return usedByUserNotPatient(user);
    }

    private ResponseDto usedByUserPatient(User user) {
        List<Event> events = eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.NEW);
        events.addAll(eventRepository.findByPatient_Id_AndStatus(user.getId(), StatusEnum.PROCESSED));
        Set<TemplateDto> templates = lambdaFromEventToTemplateDto(events);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }

    private Set<TemplateDto> lambdaFromEventToTemplateDto(List<Event> events) {
        TemplateMapper mapper = TemplateMapper.getInstance();
        return events.stream()
                .map(event -> {
                    try {
                        return mapper.toDto(event.getTemplate());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toSet());
    }

    private ResponseDto usedByUserNotPatient(User user) {
        List<Event> events = eventRepository.findByFromUser_IdAndStatus(user.getId(), StatusEnum.NEW);
        events.addAll(eventRepository.findByFromUser_IdAndStatus(user.getId(), StatusEnum.PROCESSED));
        Set<TemplateDto> templates = lambdaFromEventToTemplateDto(events);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }

    private ResponseDto getAllTemplatesForPatient(CriteriaDto criteriaDto) {
        List<Template> templates = templateRepository.findByNameContainingIgnoreCaseAndTypeEnum(criteriaDto.getSearch(), TypeEnum.PATIENT);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }



    private boolean isMedicalTemplate(Template template) {return TypeEnum.MEDICAL.equals(template.getTypeEnum());}

    private boolean isPatient(User current) {
        return UserType.PATIENT.equals(current.getUserType());
    }

    private boolean isDoctor(User current) {
        return UserType.DOCTOR.equals(current.getUserType());
    }

    private boolean isStaff(User current) {
        return UserType.STAFF.equals(current.getUserType());
    }

    private boolean isOrg(User current) {
        return UserType.ORG.equals(current.getUserType());
    }

}
