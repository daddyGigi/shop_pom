package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.BackUser;

/**
 * Created by  .Life on 2019/07/01/0001 19:29
 */
public interface BackUserMapper extends BaseMapper<BackUser> {

    BackUser queryByUsername(String username);
}
