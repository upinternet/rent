package com.hyh.ease.rent.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String getUserName(@PathVariable String id) {
        return "hello " + id;
    }


    @RequestMapping
    public String index(Model model) {
        model.addAttribute("msg", "hello");
        return "user";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/admin/test1")
    @ResponseBody
    public String adminTest1() {
        return "ROLE_USER";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/admin/test2")
    @ResponseBody
    public String adminTest2() {
        return "ROLE_ADMIN";


    }

}
