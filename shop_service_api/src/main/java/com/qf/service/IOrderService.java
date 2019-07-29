package com.qf.service;

import com.qf.entity.Order;
import com.qf.entity.User;

import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 21:08
 */
public interface IOrderService {
    Order insertOrder(Integer aid, User user);
    List<Order> queryByUid(Integer uid);
    Order queryByOid(String orderid);

    int updateOrderStatus(String orderid,int status);
}
