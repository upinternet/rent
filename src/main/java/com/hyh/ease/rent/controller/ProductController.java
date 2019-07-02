package com.hyh.ease.rent.controller;

import com.hyh.ease.rent.dao.product.ProductDao;
import com.hyh.ease.rent.entity.Product;
import com.hyh.ease.rent.utils.FileUtils;
import com.hyh.ease.rent.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {


    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public ProductController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    private ProductDao productDao;




    /**
     * @return 跳转到文件上传页面
     */
    @RequestMapping("/new")
    public String index(){
        return "product/new";
    }

    /**
     *
     * @param file 上传的文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/new" , method = RequestMethod.POST)
    public String upload(Product product, @RequestParam("file")MultipartFile file ){
        //2获得文件名字
        String fileName=file.getOriginalFilename();
        String productId = UUIDUtils.getUUID();
        fileName = productId + fileName.substring(fileName.lastIndexOf("."));
        //2上传失败提示
        String warning="";
        if(FileUtils.upload(file, path, fileName)){
            //上传成功
            warning="上传成功:" + productId;

            product.setId(productId);
            product.setPath(fileName);

            productDao.insert(product);

        }else{
            warning="上传失败";
        }
        System.out.println(warning);
        return warning;
    }


    @ResponseBody
    @RequestMapping("/query")
    public List<Product> query(){
        return productDao.query();
    }


    @ResponseBody
    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") String productId) {
        productDao.removeById(productId);
        return "删除成功";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Product> products = productDao.query();
        model.addAttribute("products", products);
        return "product/list";
    }

/*    @ResponseBody
    @RequestMapping("/image/{id}")
    public void index(HttpServletResponse response , HttpServletRequest request , @PathVariable("id") String productId) {
        BufferedImage image ;
        ServletOutputStream out = null;
        try {
            Product product = productDao.selectByPrimaryKey(productId);
            image = ImageIO.read(new File(path + product.getPath()));

            response.setHeader("Content- Disposition", "attachment;filename=" + product.getPath());
            String suffix = product.getPath().substring(product.getPath().lastIndexOf(".") + 1);
            if(!"png".equals(suffix))
            {
                suffix = "jpeg";
            }
            response.setContentType("image/" + suffix);
            out = response.getOutputStream();
            ImageIO.write(image, suffix, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @ResponseBody
    @RequestMapping("/{id}/image")
    public void index(HttpServletResponse response , HttpServletRequest request , @PathVariable("id") String productId) {
        ServletOutputStream out = null;
        FileInputStream is = null;
        try {
            Product product = productDao.selectByPrimaryKey(productId);
            File f = new File(path + product.getPath());
            is = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            int length = is.read(data);

            response.setHeader("Content- Disposition", "attachment;filename=" + product.getPath());
            String suffix = product.getPath().substring(product.getPath().lastIndexOf(".") + 1);
            if(!"png".equals(suffix))
            {
                suffix = "jpeg";
            }
            response.setContentType("image/" + suffix);
            out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            is.close();
        } catch (IOException e) {
            logger.error("IOException" , e);
        }

    }
}