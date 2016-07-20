package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/catalog")
public class CatalogControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllCatalog(@RequestBody CriteriaDto criteria) {
        return new ResponseDto(true, "Base msg", new TemplateDto());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getCatalog(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new TemplateDto());
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto addCatalog(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeCatalog(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto viewCatalog(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new TemplateDto());
    }
}
