package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.services.ReferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/references")
public class ReferenceControllerV2 {

    @Autowired
    ReferencesService referencesService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllReferences(@RequestBody CriteriaDto criteria) {
        ResponseDto responseDto = referencesService.getAllReferences(criteria);
        return responseDto;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getReference(@PathVariable String id) {
        ResponseDto responseDto = referencesService.getReferenceById(id);
        return responseDto;
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto myReferences(@RequestBody CriteriaDto criteria) {
        ResponseDto responseDto = referencesService.myReferences(criteria);
        return responseDto;
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto addReferences(@PathVariable String id) {
        ResponseDto responseDto = referencesService.addReference(id);
        return responseDto;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createReferences(@RequestBody ReferenceDto referenceDto) {
        ResponseDto responseDto = referencesService.createReference(referenceDto);
        return responseDto;
    }

    @RequestMapping(value = "/invite/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto invite(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeReference(@PathVariable String id) {
        ResponseDto responseDto = referencesService.removeReference(id);
        return responseDto;
    }
}
