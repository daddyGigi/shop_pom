package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by  .Life on 2019/07/02/0002 23:30
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePowerTable implements Serializable {
    private Integer rid;
    private Integer pid;

}
