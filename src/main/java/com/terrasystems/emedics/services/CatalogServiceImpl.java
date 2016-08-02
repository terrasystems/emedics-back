package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.terrasystems.emedics.utils.UtilsImpl.allowedFormsCount;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    Utils utils;
    @Autowired
    UserTemplateRepository userTemplateRepository;

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
        User user = utils.getCurrentUser();
        if (isPatient(user)) {
            return getAllTemplatesForPatient(criteriaDto);
        }
        List<Template> templates = templateRepository.findByNameContainingIgnoreCase(criteriaDto.getSearch());
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }

    @Override
    @Transactional
    public ResponseDto addTemplate(String id) {
        User user = utils.getCurrentUser();
        Template template = templateRepository.findOne(id);
        if (isAlreadyLoad(user, template)) {
            return utils.generateResponse(true, MessageEnums.MSG_ALREADY_HAVE_THIS_TEMPL.toString(), null);
        }
        if (template.isCommercialEnum()) {
            return addUserTemplate(user, template);
        }
        if (isCountAllowed(user)) {
            return addUserTemplate(user, template);
        }
        return utils.generateResponse(false, MessageEnums.MSG_ALLOW_FORM_IS_FULL.toString(), null);
    }

    @Override
    public ResponseDto previewTemplate(String id) {
        Template template = templateRepository.findOne(id);
        if (template == null) {
            return utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);
        }
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);
    }

    private ResponseDto getAllTemplatesForPatient(CriteriaDto criteriaDto) {
        List<Template> templates = templateRepository.findByNameContainingIgnoreCaseAndTypeEnum(criteriaDto.getSearch(), TypeEnum.PATIENT);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templates);
    }

    private boolean isAlreadyLoad(User user, Template template) {
        UserTemplate userTemplate = userTemplateRepository.findByUser_IdAndTemplate_Id(user.getId(), template.getId());
        if (userTemplate != null) {
            return true;
        }
        return false;
    }

    private boolean isCountAllowed(User user) {
        return allowedFormsCount > userTemplateRepository.countByUser_IdAndTemplate_CommercialEnumIsFalse(user.getId());
    }

    private ResponseDto addUserTemplate(User user, Template template) {
        UserTemplate userTemplate = new UserTemplate();
        userTemplate.setUser(user);
        userTemplate.setTemplate(template);
        userTemplateRepository.save(userTemplate);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null);
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
