package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by  .Life on 2019/07/15/0015 20:58
 */
@Component
public class MyRabbitListener {
    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "search_queue")
    public void msgHander(Goods goods){
        //同步到索引库
        searchService.addGoods(goods);
    }
}
