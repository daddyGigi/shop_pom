package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by  .Life on 2019/07/24/0024 20:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private Integer isdefault = 0;
    private Date createtime = new Date();


}
