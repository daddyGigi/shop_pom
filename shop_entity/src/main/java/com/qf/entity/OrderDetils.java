package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by  .Life on 2019/07/25/0025 21:03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetils implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderid;
    private Integer gid;
    private String gname;
    private BigDecimal gprice;
    private String gimage;
    private Integer gnumber;
    private BigDecimal sprice;
}
