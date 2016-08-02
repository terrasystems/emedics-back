package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface CatalogService {
    ResponseDto getTemplateById(String id);
    ResponseDto getAllTemplates(CriteriaDto criteriaDto);
    ResponseDto addTemplate(String id);
    ResponseDto previewTemplate(String id);
    ResponseDto usedByUser(String id);
}
