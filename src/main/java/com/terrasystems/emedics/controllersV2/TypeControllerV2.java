package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TypeDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/types")
public class TypeControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllTypes(@RequestBody CriteriaDto criteria) {
        List<TypeDto> typeDtos = new ArrayList<>();
        typeDtos.add(new TypeDto());
        typeDtos.add(new TypeDto());
        return new ResponseDto(true, "Base msg", typeDtos);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getType(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new TypeDto());
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeType(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createType(@RequestBody TypeDto request) {
        return new ResponseDto(true, "Base msg");
    }
}
