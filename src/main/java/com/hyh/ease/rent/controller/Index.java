package com.hyh.ease.rent.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller
public class Index {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("msg", "hello world");
        return "index";
    }

/*
    @RequestMapping("/image")
    public HttpServletResponse index(HttpServletResponse response , HttpServletRequest request) {
        BufferedImage image ;
        ServletOutputStream out;
        try {
            image = ImageIO.read(new File("C:\\Users\\heyan\\Pictures\\2.jpg"));

//            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content- Disposition", "attachment;filename=2.jpg");
            response.setContentType("image/jpeg");
//            response.setHeader("Content-Type", "image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
*/

}
