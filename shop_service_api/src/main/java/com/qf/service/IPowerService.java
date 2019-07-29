package com.qf.service;

import com.qf.entity.Power;

import java.util.List;

/**
 * Created by  .Life on 2019/07/02/0002 16:11
 */
public interface IPowerService {
    List<Power> powerList();

    int insert(Power power);
    List<Power> queryByRid(Integer rid);
}
