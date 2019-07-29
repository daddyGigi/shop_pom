package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.network.HttpUtil;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by  .Life on 2019/07/05/0005 16:47
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Reference
    private ISearchService searchService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public List<Goods> queryGoodsList() {

        return goodsMapper.queryList();
    }

    @Override
    public Goods insertGoods(Goods goods) {
        //将商品添加到数据库
        int insert = goodsMapper.insert(goods);
        //将商品信息添加到索引库
        //searchService.addGoods(goods);
        //发送详情到详情工程生成静态页面
       // HttpUtil.sendGet("http://localhost:8083/item/createhtml?gid="+goods.getId());

        rabbitTemplate.convertAndSend("goods_exchange","",goods);
        return goods;
    }

    @Override
    public Goods queryById(Integer gid) {
        return goodsMapper.selectById(gid);
    }
}
