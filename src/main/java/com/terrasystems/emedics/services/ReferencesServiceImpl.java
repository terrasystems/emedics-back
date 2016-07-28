package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferencesServiceImpl implements ReferencesService {

    private final static String USER_TYPE_PATIENT = "PATIENT";
    private final static String USER_TYPE_DOCTOR = "DOCTOR";

    @Autowired
    Utils utils;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto getAllReferences(CriteriaDto criteriaDto) {
        ResponseDto responseDto = new ResponseDto();
        String currentUserId = utils.getCurrentUserId();
        List<ReferenceDto> refs = new ArrayList<>();
        ReferenceConverter converter = new ReferenceConverter();
        if (userRepository.findByEmail(currentUserId).getUserType().toString().equals(USER_TYPE_PATIENT)) {
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUserId, UserType.DOCTOR, criteriaDto.getSearch(), criteriaDto.getSearch())));
            responseDto.setMsg(MessageEnums.MSG_PAT_REF.toString());
            responseDto.setState(true);
            responseDto.setResult(refs);
            return responseDto;
        } else if (userRepository.findByEmail(currentUserId).getUserType().toString().equals(USER_TYPE_DOCTOR)) {
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUserId, UserType.DOCTOR, criteriaDto.getSearch(), criteriaDto.getSearch())));
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUserId, UserType.PATIENT, criteriaDto.getSearch(), criteriaDto.getSearch())));
            responseDto.setMsg(MessageEnums.MSG_DOC_REF.toString());
            responseDto.setState(true);
            responseDto.setResult(refs);
            return responseDto;
        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public ResponseDto removeReference(String id) {
        User user = userRepository.findByEmail(utils.getCurrentUserId());
        User refToRemove = userRepository.findById(id);
        if (user == null || refToRemove == null) {
            return new ResponseDto(false, MessageEnums.MSG_USER_NOT_FOUND.toString());
        }
        user.getReferences().remove(refToRemove);
        refToRemove.getReferences().remove(user);
        userRepository.save(user);
        userRepository.save(refToRemove);
        return new ResponseDto(true, MessageEnums.MSG_REF_REMOVED.toString());
    }

    @Override
    @Transactional
    public ResponseDto addReference(String id) {
        User user = userRepository.findByEmail(utils.getCurrentUserId());
        User refToAdd = userRepository.findById(id);
        if (user == null || refToAdd == null) {
            return new ResponseDto(false, MessageEnums.MSG_USER_NOT_FOUND.toString());
        }
        user.getReferences().add(refToAdd);
        refToAdd.getReferences().add(user);
        userRepository.save(refToAdd);
        userRepository.save(user);
        return new ResponseDto(true, MessageEnums.MSG_REF_ADDED.toString());
    }


}
