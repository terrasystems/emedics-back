package com.terrasystems.emedics.controllersV2.filters;

import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.terrasystems.emedics.enums.MessageEnums;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/errors")
public class ApplicationExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping("resourcenotfound")
    @ResponseBody
    public ResponseDto resourceNotFound(HttpServletRequest request) throws Exception {
        ResponseDto response = new ResponseDto(false, "Resource Not Found");
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("unauthorised")
    @ResponseBody
    public ResponseDto unAuthorised(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseDto resp = new ResponseDto(false, MessageEnums.MSG_NOT_ACTIVATE.toString());
        return resp;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping("incorrect")
    @ResponseBody
    public ResponseDto incorrect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseDto resp = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString());
        return resp;
    }
}
