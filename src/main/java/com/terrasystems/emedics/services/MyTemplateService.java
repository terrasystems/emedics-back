package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

import java.io.IOException;

public interface MyTemplateService {
    ResponseDto getAllMyTemplates(CriteriaDto criteriaDto);
    ResponseDto getById(String id) throws IOException;
    ResponseDto addMyTemplate(String id);
    ResponseDto removeMyTemplate(String id);
}
