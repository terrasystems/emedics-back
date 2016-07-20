package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.PatientDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/patients")
public class PatientControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllPatients(@RequestBody Object criteria) {
        List<PatientDto> patientDtos = new ArrayList<>();
        patientDtos.add(new PatientDto());
        patientDtos.add(new PatientDto());
        return new ResponseDto(true, "Base msg", patientDtos);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getPatient(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new PatientDto());
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getMyPatients(@RequestBody Object criteria) {
        List<PatientDto> patientDtos = new ArrayList<>();
        patientDtos.add(new PatientDto());
        patientDtos.add(new PatientDto());
        return new ResponseDto(true, "Base msg", patientDtos);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createPatient(@RequestBody PatientDto request) {
        return new ResponseDto(true, "Base msg");
    }
}
