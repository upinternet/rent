package com.hyh.ease.rent.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping(value = "/detail/{id}" , method = RequestMethod.GET)
    public String getUserName(@PathVariable String id)
    {
        return "hello " + id;
    }

}
