package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TypeDto;

public interface TypeService {

    ResponseDto getAllTypes(CriteriaDto criteriaDto);
    ResponseDto getType(String id);
    ResponseDto removeType(String id);
    ResponseDto createType(TypeDto dto);
}
