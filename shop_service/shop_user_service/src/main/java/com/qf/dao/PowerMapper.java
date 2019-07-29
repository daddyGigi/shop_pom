package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Power;

import java.util.List;

/**
 * Created by  .Life on 2019/07/02/0002 16:18
 */
public interface PowerMapper extends BaseMapper<Power> {
    List<Power> queryAllpowers();

    List<Power> queryPowerByRid(Integer rid);
}
