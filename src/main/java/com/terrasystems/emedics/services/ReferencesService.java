package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface ReferencesService {
    ResponseDto getAllReferences(CriteriaDto criteriaDto);
    ResponseDto removeReference(String id);
    ResponseDto addReference(String id);
}
