package com.qf.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.OrderDetilsMapper;
import com.qf.dao.OrderMapper;
import com.qf.dataconfig.DynamicDataSource;
import com.qf.entity.*;
import com.qf.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 21:15
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetilsMapper orderDetilsMapper;
    @Reference
    private IAddressService addressService;
    @Reference
    private ICartService cartService;
    @Autowired
    private OrderUtil orderUtil;

    /*
    * 添加订单
     */
    @Override
    //@Transactional
    public Order insertOrder(Integer aid, User user) {

        //确定数据源，获得用户id，确定库
        int uids = Integer.parseInt(orderUtil.getUid(user.getId()));
        int dbIndex = uids % 2 +1;
        System.out.println("定位到的库"+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);
        //确定表的位置,不能与数据库的规则一样
        int tableIndex = uids /2 %2 +1;
        System.out.println("定位到的表"+tableIndex);


        //通过收货地址查询地址详情
        Address address = addressService.queryByAid(aid);
        //查询购物车信息，计算总价
        List<ShopCart> shopCarts = cartService.queryCartList(user, null);
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : shopCarts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }
        //手动创建订单详情
        Order order = new Order(
                orderUtil.createOrderId(user.getId()),
                address.getPerson(),
                address.getAddress(),
                address.getPhone(),
                bigDecimal,
                new Date(),
                0,
                user.getId(),
                null
        );
        //根据购物车列表生成订单详情
        int i =0;
        List<OrderDetils> orderDetilsList = new ArrayList<>();
        for (ShopCart shopCart : shopCarts) {
            OrderDetils orderDetils = new OrderDetils(
                    null,
                    order.getOrderid(),
                    shopCart.getGid(),
                    shopCart.getGoods().getGname(),
                    shopCart.getGoods().getGprice(),
                    shopCart.getGoods().getGimage(),
                    shopCart.getGnumber(),
                    shopCart.getSprice()
            );
            orderDetilsList.add(orderDetils);
            i++;
            if (i % 1000 == 0 || i == shopCarts.size()){
                //用集合做批量插入
                orderDetilsMapper.insertDetils(orderDetilsList,tableIndex);
                //添加之后清空集合
                orderDetilsList.clear();
            }
        }
        //插入到订单表和订单详情表
        orderMapper.insertOrder(order,tableIndex);

        //清空购物车
        cartService.deleteCart(user);
        return order;
    }

    @Override
    public List<Order> queryByUid(Integer uid) {
        //库的定位
        int uids = Integer.parseInt(orderUtil.getUid(uid));
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        //表的定位
        int tableIndex = uids /2 %2 +1;

        //查询所有订单及订单详情
        return orderMapper.queryOrderByUid(uid,tableIndex);
    }


    /*
    * 通过订单号查询订单信息
    */
    @Override
    public Order queryByOid(String orderid) {
        //解析订单号，拿到用户ID
        Integer uid = orderUtil.parseOrderId(orderid);
        //通过uid库的定位
        int uids = Integer.parseInt(orderUtil.getUid(uid));
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        //表的定位
        int tableIndex = uids /2 %2 +1;

        //还是要通过订单id查询订单，解析用户id是为了定位库和表的位置
        return orderMapper.queryByOrderId(orderid,tableIndex);
    }
    /*
    * 修改订单状态
    */
    @Override
    public int updateOrderStatus(String orderid, int status) {
        //解析订单号，拿到用户ID
        Integer uid = orderUtil.parseOrderId(orderid);
        //通过uid库的定位
        int uids = Integer.parseInt(orderUtil.getUid(uid));
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        //表的定位
        int tableIndex = uids /2 %2 +1;
        return orderMapper.updateOrderStatus(orderid,status,tableIndex);
    }
}
