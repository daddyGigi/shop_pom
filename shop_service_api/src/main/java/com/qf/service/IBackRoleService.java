package com.qf.service;

import com.qf.entity.BackRole;

import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 21:07
 */
public interface IBackRoleService {
    List<BackRole> queryAll();

    int insert(BackRole backRole);

    List<BackRole> roleListByUid(Integer uid);

    int updateRolePowers(Integer rid,Integer[] pids);
}
