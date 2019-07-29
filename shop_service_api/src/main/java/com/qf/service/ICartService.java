package com.qf.service;

import com.qf.entity.ShopCart;
import com.qf.entity.User;

import java.util.List;

/**
 * Created by  .Life on 2019/07/22/0022 19:34
 */
public interface ICartService {
    int insertCart(ShopCart shopCart, User user,String cartToken);

    List<ShopCart> queryCartList(User user,String cartToken);

    int mergeCarts(String cartToken,User user);

    int deleteCart(User user);
}
