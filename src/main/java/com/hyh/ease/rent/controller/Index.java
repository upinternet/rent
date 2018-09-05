package com.hyh.ease.rent.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("msg", "hello world");
        return "index";
    }

}
