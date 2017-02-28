package com.walker.loginservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rettwalker on 2/3/17.
 */
@RestController
public class ApiController {
    @RequestMapping(value = "/api", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView swaggerui() {
        return new ModelAndView("redirect:swagger-ui.html");
    }
}
