package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.dto.ReferenceDto;

import java.util.List;


public interface ReferenceService {
    List<ReferenceDto> findAllReferencesByCriteria(String searchCriteria);

}
