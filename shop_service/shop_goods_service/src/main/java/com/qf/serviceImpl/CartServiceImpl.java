package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by  .Life on 2019/07/22/0022 19:35
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IGoodsService goodsService;
    @Override
    public int insertCart(ShopCart shopCart, User user, String cartToken) {
        //计算小计
        Goods goods = goodsService.queryById(shopCart.getGid());
        int gnumber = shopCart.getGnumber();
        BigDecimal sprice = goods.getGprice().multiply(BigDecimal.valueOf(gnumber));

        shopCart.setSprice(sprice);
        //创建时间
        shopCart.setCreatetime(new Date());

        if (user != null){
            //说明已登录
            shopCart.setUid(user.getId());
            //保存到数据库即可
            cartMapper.insert(shopCart);
        }else {
            //说明未登录
            redisTemplate.opsForList().leftPush(cartToken,shopCart);

        }

        return 0;
    }

    @Override
    public List<ShopCart> queryCartList(User user,String cartToken) {
        List<ShopCart> shopCartList = null;

        if (user!=null){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            shopCartList = cartMapper.selectList(queryWrapper);
        }else {
            if (cartToken != null){
                //获得链表的长度
                long len = redisTemplate.opsForList().size(cartToken);
                //获得指定链表的范围值
                shopCartList = redisTemplate.opsForList().range(cartToken,0,len);
            }
        }
        //根据购物车里的信息，查询商品信息
        if (shopCartList != null){
            for (ShopCart shopCart : shopCartList) {
               Goods goods = goodsService.queryById(shopCart.getGid());
                shopCart.setGoods(goods);
            }
        }

        return shopCartList;
    }

    //合并购物车
    @Override
    public int mergeCarts(String cartToken, User user) {
        if (cartToken != null){
                long size = redisTemplate.opsForList().size(cartToken);
                List<ShopCart> shopCarts = redisTemplate.opsForList().range(cartToken,0,size);
            for (ShopCart cart : shopCarts) {
                cart.setUid(user.getId());
                cartMapper.insert(cart);
            }
            //清空临时购物车
            redisTemplate.delete(cartToken);
            return 1;
        }

        return 0;
    }

    @Override
    public int deleteCart(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",user.getId());
        return cartMapper.delete(queryWrapper);

    }
}
