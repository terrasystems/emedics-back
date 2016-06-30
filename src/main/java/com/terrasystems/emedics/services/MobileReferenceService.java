package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.ReferenceDto;

import java.util.List;

public interface MobileReferenceService {
    List<ReferenceDto> findReferencesForMobile(String search, int start, int count);
}
