package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/references")
public class ReferenceControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllReferences(@RequestBody CriteriaDto criteria) {
        return new ResponseDto(true, "Base msg", new ReferenceDto());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getReference(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new ReferenceDto());
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto myReferences(@RequestBody CriteriaDto criteria) {
        return new ResponseDto(true, "Base msg", new ReferenceDto());
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto addReferences(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createReferences(@RequestBody ReferenceDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/invite/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto invite(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeReference(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }
}
