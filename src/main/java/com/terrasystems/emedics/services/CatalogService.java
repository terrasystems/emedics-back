package com.terrasystems.emedics.services;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;

import java.io.IOException;

public interface CatalogService {
    ResponseDto getTemplateById(String id) throws IOException;
    ResponseDto getAllTemplates(CriteriaDto criteriaDto);
    ResponseDto createTemplate(TemplateDto templateDto);
    ResponseDto previewTemplate(String id) throws IOException;
    ResponseDto usedByUser(String id);
}
