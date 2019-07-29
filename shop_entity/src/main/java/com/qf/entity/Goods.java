package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by  .Life on 2019/07/05/0005 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String gname;
    private String ginfo;
    private String gimage;
    private BigDecimal gprice;
    private Integer tid;
    private Integer gsave;
    @TableField(exist = false)
    private String tname;
}
