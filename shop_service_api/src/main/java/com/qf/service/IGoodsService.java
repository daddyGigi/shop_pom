package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * Created by  .Life on 2019/07/05/0005 16:35
 */
public interface IGoodsService {
    List<Goods> queryGoodsList();
    Goods insertGoods(Goods goods);

    Goods queryById(Integer gid);
}
