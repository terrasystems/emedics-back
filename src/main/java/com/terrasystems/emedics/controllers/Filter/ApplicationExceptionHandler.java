package com.terrasystems.emedics.controllers.Filter;

import com.terrasystems.emedics.model.dto.DashboardReferenceResponse;
import com.terrasystems.emedics.model.dto.ExceptionHandlerResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/errors")
public class ApplicationExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping("resourcenotfound")
    @ResponseBody
    public ExceptionHandlerResponse resourceNotFound(HttpServletRequest request) throws Exception {
        ExceptionHandlerResponse response = new ExceptionHandlerResponse();
        StateDto stateDto = new StateDto(false, "Resource Not Found");
        response.setState(stateDto);
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("unauthorised")
    @ResponseBody
    public ExceptionHandlerResponse unAuthorised(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExceptionHandlerResponse resp = new ExceptionHandlerResponse();
        StateDto stateDto = new StateDto(false, "MSG_NOT_ACTIVATE");
        resp.setState(stateDto);
        return resp;
    }
}
