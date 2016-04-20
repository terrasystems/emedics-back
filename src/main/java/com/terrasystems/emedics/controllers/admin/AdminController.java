package com.terrasystems.emedics.controllers.admin;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @RequestMapping(value = "/rest/admin/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "Secure!";
    }
}
