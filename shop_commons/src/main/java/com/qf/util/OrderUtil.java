package com.qf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by  .Life on 2019/07/27/0027 9:45
 */
@Component
public class OrderUtil {
    //stringRedisTemplate不会被序列化
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /*
    生成订单号
     必须全局唯一
     能抗并发
     不宜过长
     不能有敏感的业务信息
    */
    public String createOrderId(Integer uid){
        StringBuffer stringBuffer = new StringBuffer("");
        //拼接当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        stringBuffer.append(simpleDateFormat.format(new Date()));
        //截取用户ID后四位，不足补0
        stringBuffer.append(getUid(uid));
        //拼接一个流水号
        String orderNumber = stringRedisTemplate.opsForValue().get("order_number");
        if (orderNumber == null){
            stringRedisTemplate.opsForValue().set("order_number","0");
        }
        //获得自增的流水号
        Long orderNumbers = stringRedisTemplate.opsForValue().increment("order_number");
        stringBuffer.append(orderNumbers);

        return stringBuffer.toString();
    }

    public String getUid(Integer uid){
        StringBuffer stringBuffer = new StringBuffer();
        String uidStr = uid +"";
        if (uidStr.length()<4){
            for(int i=0;i<(4-uidStr.length());i++){
                stringBuffer.append("0");
            }
            stringBuffer.append(uidStr);
        }else {
            stringBuffer.append(uidStr.substring(uidStr.length()-4));
        }
        return stringBuffer.toString();
    }


    /*
    * 解析订单号
    */
    public Integer parseOrderId(String orderid){
        String uid = orderid.substring(6,10);
        return Integer.parseInt(uid);
    }
}
