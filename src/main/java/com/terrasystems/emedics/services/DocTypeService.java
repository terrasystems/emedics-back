package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.DocType;
import com.terrasystems.emedics.model.dto.DocTypeDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface DocTypeService {

    List<DocType> getAll();
    StateDto addDocType(DocTypeDto docTypeDto);
}
