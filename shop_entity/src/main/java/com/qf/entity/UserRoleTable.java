package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by  .Life on 2019/07/02/0002 20:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleTable implements Serializable {
    private Integer uid;
    private Integer rid;
}
