package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.DocTypeRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.DocTypeEnum;
import com.terrasystems.emedics.model.DocType;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.DocTypeDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.DocTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocTypeServiceImpl implements DocTypeService, CurrentUserService {

    @Autowired
    DocTypeRepository docTypeRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<DocType> getAll() {
        List<DocType> docTypes = (List<DocType>) docTypeRepository.findAll();
        return docTypes;
    }

    @Override
    public StateDto addDocType(DocTypeDto docTypeDto) {
        DocTypeMapper mapper = DocTypeMapper.getInstance();
        docTypeRepository.save(mapper.toEntity(docTypeDto));
        return new StateDto(true, "DocType added");
    }

    @Override
    public List<DocType> getByValueDoctor() {
        List<DocType> docTypes = docTypeRepository.findByValue(DocTypeEnum.DOCTOR);
        return docTypes;
    }

    @Override
    public List<DocType> getByValueOrganization() {
        List<DocType> docTypes = docTypeRepository.findByValue(DocTypeEnum.ORG);
        return docTypes;
    }
}
