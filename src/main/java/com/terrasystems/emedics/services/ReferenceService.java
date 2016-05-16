package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;
import java.util.Set;


public interface ReferenceService {
    List<ReferenceDto> findAllReferencesByCriteria(String searchCriteria);
    StateDto addReferences(Set<String> references);
    Iterable<ReferenceDto> getAllReferences();
    void removeReferences(Set<String> refs) throws Exception;

}
