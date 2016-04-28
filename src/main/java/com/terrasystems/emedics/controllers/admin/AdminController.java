package com.terrasystems.emedics.controllers.admin;

import com.terrasystems.emedics.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "Secure!";
    }

    @RequestMapping(value = "/admin/hello", method = RequestMethod.GET)
    @ResponseBody

    public String hello() {
        User name = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return name.getUsername();
    }
}
