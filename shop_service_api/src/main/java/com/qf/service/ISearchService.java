package com.qf.service;

import com.qf.entity.Goods;
import com.qf.entity.Page;

import java.util.List;

/**
 * Created by  .Life on 2019/07/11/0011 11:51
 */
public interface ISearchService{
    Page searchByKey(Page page);

    int addGoods(Goods goods);
}
