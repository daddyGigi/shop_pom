package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 21:16
 */
public interface OrderMapper extends BaseMapper<Order> {

    int insertOrder(@Param("order") Order order,@Param("tableIndex") int tableIndex);

    List<Order> queryOrderByUid(@Param("uid") Integer uid,@Param("tableIndex") Integer tableIndex);

    Order queryByOrderId(@Param("orderid") String orderid,@Param("tableIndex") int tableIndex);

    int updateOrderStatus(@Param("orderid") String orderid,@Param("status") int status,@Param("tableIndex") int tableIndex);
}
