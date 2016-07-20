package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/mytemplates")
public class MyTemplateControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllMyTemplates(@RequestBody Object criteria) {
        return new ResponseDto(true, "Base msg", new TemplateDto());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getMyTemplate(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new TemplateDto());
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeMyTemplate(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto addTemplate(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }
}
