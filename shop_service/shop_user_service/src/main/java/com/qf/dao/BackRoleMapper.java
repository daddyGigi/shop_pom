package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.BackRole;

import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 21:09
 */
public interface BackRoleMapper extends BaseMapper<BackRole> {

        List<BackRole> queryRoleByUid(Integer uid);
}
