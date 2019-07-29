package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by  .Life on 2019/07/01/0001 21:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class BackRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String rolename;
    private String rolealias;
    private Date createtime = new Date();
    private Integer status;

    @TableField(exist = false)
    private boolean checked;
}
