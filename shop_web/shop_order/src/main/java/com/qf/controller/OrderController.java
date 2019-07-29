package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Order;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by  .Life on 2019/07/24/0024 19:54
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Reference
    private ICartService cartService;
    @Reference
    private IAddressService addressService;
    @Reference
    private IOrderService orderService;

    @IsLogin(mustLogin = true)
    @RequestMapping("/edit")
    public String toOrderEdit(User user, Model model){
        //购物车的所有商品
        List<ShopCart> shopCarts = cartService.queryCartList(user, null);
        //当前用户的收获地址
        List<Address> addresses = addressService.queryByUid(user.getId());
        //计算总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : shopCarts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }

        model.addAttribute("carts",shopCarts);
        model.addAttribute("addresses",addresses);
        model.addAttribute("allprice",bigDecimal.doubleValue());
        return "orderedit";
    }

    //下单
    @IsLogin(mustLogin = true)
    @RequestMapping("/insertOrder")
    public String insertOrder(Integer aid,User user){
        Order order = orderService.insertOrder(aid, user);
        return "redirect:/pay/alipay?orderid="+order.getOrderid();
    }


    /*
    查询我的订单
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/list")
    public String showList(User user,Model model){
        List<Order> orders = orderService.queryByUid(user.getId());
        model.addAttribute("orders",orders);
        return "orderlist";
    }
}
