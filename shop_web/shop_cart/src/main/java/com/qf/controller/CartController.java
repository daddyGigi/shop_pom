package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by  .Life on 2019/07/21/0021 13:08
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private ICartService cartService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*
        添加购物车
    */
    @IsLogin
    @RequestMapping("/insert")
    public String insert(ShopCart shopCart, User user, @CookieValue(value = "cartToken",required = false) String cartToken, HttpServletResponse response){

        if (cartToken == null){
            cartToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cartToken",cartToken);
            cookie.setMaxAge(60*60*24*365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //添加购物车
        cartService.insertCart(shopCart,user,cartToken);

        return "ok";
    }

    @RequestMapping("/list")
    @ResponseBody
    @IsLogin
    public String queryCart(@CookieValue(value = "cartToken",required = false) String cartToken,User user,String callback){


        List<ShopCart> shopCartList = cartService.queryCartList(user,cartToken);

        return callback !=null ? callback + "("+ JSON.toJSONString(shopCartList)+")":JSON.toJSONString(shopCartList);
    }

    //去到购物车页面
    @IsLogin
    @RequestMapping("/showlist")
    public String showCart(User user, @CookieValue(value = "cartToken",required = false) String cartToken, Model model){

        List<ShopCart> shopCarts = cartService.queryCartList(user, cartToken);
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : shopCarts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }
        model.addAttribute("carts",shopCarts);
        model.addAttribute("allprice",bigDecimal.doubleValue());
        return "cartlist";
    }
}
