package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TypeRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TypeDto;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements  TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    public ResponseDto getAllTypes(CriteriaDto criteriaDto) {

        if (criteriaDto.getType() != null) {
            ResponseDto responseDto = new ResponseDto();
            TypeMapper mapper = TypeMapper.getInstance();
            List<TypeDto> types = typeRepository.findByUserType(criteriaDto.getType()).stream()
                    .map(type -> {
                        TypeDto typeDto = new TypeDto();
                        typeDto = mapper.toDto(type);
                        return typeDto;
                    }).collect(Collectors.toList());

            responseDto.setResult(types);
            responseDto.setState(true);
            responseDto.setMsg(criteriaDto.getType().toString().toLowerCase() + " types");
            return responseDto;
        }

        return new ResponseDto(false, MessageEnums.MSG_TYPE_IS_NULL.toString());
    }
}
