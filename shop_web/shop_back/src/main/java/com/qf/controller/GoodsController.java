package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by  .Life on 2019/07/05/0005 16:33
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private IGoodsService goodsService;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private FastFileStorageClient fafsFileStorageClient;

    @RequestMapping("/list")
    public String GoodsList(Model model){
        List<Goods> goods = goodsService.queryGoodsList();
        model.addAttribute("goods",goods);
        return "goodslist";
    }
    @RequestMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile file) {
        String uploadFile = "";
        //截取上传图片的后缀
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i+1);
        try {
            StorePath storePath = fafsFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), substring, null);
            //获得上传路径
            uploadFile = storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "{\"filepath\":\""+uploadFile+"\"}";
    }

    @RequestMapping("/insert")
    public String insertGoods(Goods goods){
        goodsService.insertGoods(goods);
        return "redirect:/goods/list";
    }
    @RequestMapping("/getImg")
    public void getImg(String imgPath, HttpServletResponse response){
        File file = new File(imgPath);
        try(
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream();
        ) {
                IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*修改商品的分类*/
    @RequestMapping("/updateTypes")
    public String updateTypes(Integer tid,@RequestParam("pids[]") Integer[] pids){
        System.out.println("tid="+tid+",pids="+pids);
        return "redirect:/goods/list";
    }
}
