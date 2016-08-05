package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TypeRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Types;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TypeDto;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements  TypeService {

    @Autowired
    TypeRepository typeRepository;
    @Autowired
    Utils utils;

    @Override
    public ResponseDto getAllTypes(CriteriaDto criteriaDto) {

        if (criteriaDto.getType() != null) {
            TypeMapper mapper = TypeMapper.getInstance();
            List<TypeDto> types = typeRepository.findByUserType(criteriaDto.getType()).stream()
                    .map(type -> {
                        TypeDto typeDto = mapper.toDto(type);
                        return typeDto;
                    }).collect(Collectors.toList());
            return utils.generateResponse(true, criteriaDto.getType().toString().toLowerCase() + " types", types);
        }

        return utils.generateResponse(false, MessageEnums.MSG_TYPE_IS_NULL.toString(), null);
    }

    @Override
    public ResponseDto getType(String id) {
        Types type = typeRepository.findOne(id);
        if (type == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        TypeMapper mapper = TypeMapper.getInstance();
        TypeDto typeDto = mapper.toDto(type);
        return utils.generateResponse(true, MessageEnums.MSG_TYPE_BY_ID.toString(), typeDto);
    }

    @Override
    public ResponseDto removeType(String id) {
        Types type = typeRepository.findOne(id);
        if (type == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        typeRepository.delete(type);
        return utils.generateResponse(true, MessageEnums.MSG_TYPE_REMOVED.toString(), null);
    }

    @Override
    public ResponseDto createType(TypeDto dto) {
        if (dto.getUserType() == null || dto.getName() == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        Types type = new Types();
        type.setName(dto.getName());
        type.setUserType(dto.getUserType());
        typeRepository.save(type);
        return utils.generateResponse(true, MessageEnums.MSG_TYPE_CREATED.toString(), null);
    }
}
