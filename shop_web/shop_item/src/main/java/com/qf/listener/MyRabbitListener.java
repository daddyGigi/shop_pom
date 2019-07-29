package com.qf.listener;

import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  .Life on 2019/07/15/0015 21:05
 */
@Component
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void msgHander(Goods goods) throws IOException {
        //根据freemarker生成商品静态页
        Template template = configuration.getTemplate("goodsitem.ftl");

        Map<String,Object> map = new HashMap<>();
        map.put("goods",goods);
        map.put("images",goods.getGimage().split("\\|"));
        map.put("contextPath","");

        //获得classpath路径
        String path = ItemController.class.getResource("/static").getPath();
        File file = new File(path+"/page");
       //如果路径不存在则创建
        if (!file.exists()){
            file.mkdir();
        }
        try (
                Writer writer = new FileWriter(file.getAbsoluteFile()+"/"+goods.getId()+".html");
                ){
            template.process(map,writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
