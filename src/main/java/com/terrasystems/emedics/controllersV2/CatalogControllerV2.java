package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;
import com.terrasystems.emedics.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v2/catalog")
public class CatalogControllerV2 {

    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllCatalog(@RequestBody CriteriaDto criteria) {
        return catalogService.getAllTemplates(criteria);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getCatalog(@PathVariable String id) throws IOException {
        return catalogService.getTemplateById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createCatalog(TemplateDto templateDto) {
        return catalogService.createTemplate(templateDto);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto viewCatalog(@PathVariable String id) throws IOException {
        return catalogService.previewTemplate(id);
    }

    @RequestMapping(value = "/usedByUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto userByUsed(@PathVariable String id) {
        return catalogService.usedByUser(id);
    }
}
