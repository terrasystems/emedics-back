package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceCreateRequest;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;
import java.util.Set;


public interface ReferenceService {
    List<ReferenceDto> findAllReferencesByCriteria(String searchCriteria, String type);
    List<ReferenceDto> findMyReferencesByCriteria(String searchCriteria);
    StateDto addReferences(String reference);
    Iterable<ReferenceDto> getAllReferences();
    StateDto removeReferences(String reference) throws Exception;
    String createReference(ReferenceCreateRequest request);
    List<ReferenceDto> findMyRefs(String search, String type);
}
