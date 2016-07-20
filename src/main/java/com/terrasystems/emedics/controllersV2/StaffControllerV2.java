package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.StaffDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/staff")
public class StaffControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllStaff(@RequestBody CriteriaDto criteria) {
        List<StaffDto> staffDtos = new ArrayList<>();
        staffDtos.add(new StaffDto());
        staffDtos.add(new StaffDto());
        return new ResponseDto(true, "Base msg", staffDtos);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getStaff(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new StaffDto());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createStaff(@RequestBody StaffDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto editStaff(@RequestBody StaffDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/inactive/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto inactiveStaff(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/active/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto activeStaff(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }
}
