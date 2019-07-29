package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by  .Life on 2019/07/18/0018 19:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {

    private String to;
    //发给谁

    private String subject;
    //标题

    private String content;
    //内容
}
