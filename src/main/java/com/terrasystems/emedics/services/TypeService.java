package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface TypeService {

    ResponseDto getAllTypes(CriteriaDto criteriaDto);
}
