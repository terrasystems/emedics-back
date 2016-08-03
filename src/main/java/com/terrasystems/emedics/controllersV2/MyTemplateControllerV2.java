package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;
import com.terrasystems.emedics.services.MyTemplateService;
import com.terrasystems.emedics.services.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/mytemplates")
public class MyTemplateControllerV2 {

    @Autowired
    MyTemplateService myTemplateService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllMyTemplates(@RequestBody CriteriaDto criteria) {
        return myTemplateService.getAllMyTemplates(criteria);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getMyTemplate(@PathVariable String id) throws IOException {
        return myTemplateService.getById(id);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeMyTemplate(@PathVariable String id) {
        return myTemplateService.removeMyTemplate(id);
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto addTemplate(@PathVariable String id) {
        return myTemplateService.addMyTemplate(id);
    }
}
