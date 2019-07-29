package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by  .Life on 2019/07/06/0006 9:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Types implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid = -1;
    private String tname;
    private Integer status;
    @TableField(exist = false)
    private String pname;
}
