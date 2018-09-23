package com.hyh.ease.rent.controller;


import com.hyh.ease.rent.entity.User;
import com.hyh.ease.rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String getUserName(@PathVariable Integer id) {
        User user = userService.selectByPrimaryKey(id);
        return "hello " + user.getUserAccount();
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
