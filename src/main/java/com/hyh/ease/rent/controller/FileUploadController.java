package com.hyh.ease.rent.controller;

import com.hyh.ease.rent.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gaoxuyang
 *@user 图片上传及显示
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {


    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;




    /**
     * @return 跳转到文件上传页面
     */
    @RequestMapping("/upload")
    public String index(){
        return "upload";
    }
    /**
     *
     * @return 跳转到文件显示页面
     */
    @RequestMapping("/show")
    public String show(){
        return "show";
    }
    /**
     *
     * @param file 上传的文件
     * @return
     */
    @ResponseBody
    @RequestMapping("/load")
    public String upload(@RequestParam("file")MultipartFile file ){
        //2获得文件名字
        String fileName=file.getOriginalFilename();
        //2上传失败提示
        String warning="";
        if(FileUtils.upload(file, path, fileName)){
            //上传成功
            warning="上传成功";

        }else{
            warning="上传失败";
        }
        System.out.println(warning);
        return "上传成功";
    }

    /**
     * 显示图片
     * @param fileName 文件名
     * @return
     */

    @RequestMapping("/shows")
    public ResponseEntity shows(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}