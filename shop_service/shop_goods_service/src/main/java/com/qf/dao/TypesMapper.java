package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Types;

import java.util.List;

/**
 * Created by  .Life on 2019/07/06/0006 10:03
 */
public interface TypesMapper extends BaseMapper<Types> {

    List<Types> queryAllTypes();
}
