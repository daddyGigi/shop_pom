package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 21:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String orderid;
    private String person;
    private String address;
    private String phone;
    private BigDecimal allprice;
    private Date createtime;
    private Integer status;
    private Integer uid;

    @TableField(exist = false)
    private List<OrderDetils> orderDetils;
}
