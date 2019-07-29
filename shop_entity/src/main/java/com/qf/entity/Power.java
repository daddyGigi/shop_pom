package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by  .Life on 2019/07/02/0002 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Power implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid = -1;
    private String powername;
    private String powerpath;
    private Date createtime = new Date();
    private Integer status;
    @TableField(exist = false)
    private String pname;
    @TableField(exist = false)
    private boolean checked;
}
