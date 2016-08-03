package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;

public interface CatalogService {
    ResponseDto getTemplateById(String id);
    ResponseDto getAllTemplates(CriteriaDto criteriaDto);
    ResponseDto createTemplate(TemplateDto templateDto);
    ResponseDto previewTemplate(String id);
    ResponseDto usedByUser(String id);
}