package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

/**
 * Created by  .Life on 2019/07/05/0005 16:48
 */
public interface GoodsMapper extends BaseMapper<Goods> {
        List<Goods> queryList();
}
