package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  .Life on 2019/07/12/0012 20:21
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Reference
    private IGoodsService goodsService;
    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    @ResponseBody
    public void createHtml(Integer gid, HttpServletRequest request) throws IOException, TemplateException {
        Goods goods = goodsService.queryById(gid);
        Template template = configuration.getTemplate("goodsitem.ftl");
        Map<String,Object> map = new HashMap<>();
        map.put("goods",goods);
        map.put("images",goods.getGimage().split("\\|"));
        map.put("contextPath",request.getContextPath());

        String path = ItemController.class.getResource("/static").getPath();
        File file = new File(path+"/page");
        if (!file.exists()){
            file.mkdir();
        }
        Writer writer = new FileWriter(file.getAbsoluteFile()+"/"+gid+".html");

        template.process(map,writer);

    }
}
