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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.terrasystems.emedics.utils.UtilsImpl.allowedFormsCount;
import static java.util.stream.Collectors.*;

@Service
public class MyTemplateServiceImpl implements MyTemplateService {

    @Autowired
    UserTemplateRepository userTemplateRepository;
    @Autowired
    Utils utils;
    @Autowired
    TemplateRepository templateRepository;

    @Override
    public ResponseDto getAllMyTemplates(CriteriaDto criteriaDto) {
        if (criteriaDto.getSearch() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        User user = utils.getCurrentUser();
        List<TemplateDto> templateDtos = userTemplateRepository.findByUserAndTemplate_NameContainingIgnoreCase(user, criteriaDto.getSearch()).stream()
                .map(userTemplate -> {
                    TemplateMapper mapper = TemplateMapper.getInstance();
                    try {
                        return mapper.toDto(userTemplate.getTemplate());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_LIST.toString(), templateDtos);
    }

    @Override
    public ResponseDto getById(String id) throws IOException {
        UserTemplate userTemplate = userTemplateRepository.findOne(id);
        if (userTemplate == null) {
            return utils.generateResponse(true, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);
        }
        TemplateMapper mapper = TemplateMapper.getInstance();
        TemplateDto template = mapper.toDto(userTemplate.getTemplate());
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_BY_ID.toString(), template);
    }

    @Override
    public ResponseDto addMyTemplate(String id) {
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
    public ResponseDto removeMyTemplate(String id) {
        UserTemplate userTemplate = userTemplateRepository.findOne(id);
        if (userTemplate == null) {
            return utils.generateResponse(false, MessageEnums.MSG_TEMPL_NOT_FOUND.toString(), null);
        }
        userTemplateRepository.delete(userTemplate);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_REMOVED.toString(), null);
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
        userTemplate.setType(template.getTypeEnum());
        userTemplate.setDescription(template.getDescr());
        userTemplateRepository.save(userTemplate);
        return utils.generateResponse(true, MessageEnums.MSG_TEMPL_ADDED.toString(), null);
    }
}
