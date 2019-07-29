package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by  .Life on 2019/07/22/0022 19:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCart implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer gid;
    private Integer uid;
    private Integer gnumber;
    private BigDecimal sprice;
    private Date createtime;
    @TableField(exist = false)
    private Goods goods;
}
