package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.PatientDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/patients")
public class PatientControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllPatients(@RequestBody Object criteria) {
        return new ResponseDto(true, "Base msg", new PatientDto());
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getPatient(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new PatientDto());
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getMyPatients(@RequestBody Object criteria) {
        return new ResponseDto(true, "Base msg", new PatientDto());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createPatient(@RequestBody PatientDto request) {
        return new ResponseDto(true, "Base msg");
    }
}
