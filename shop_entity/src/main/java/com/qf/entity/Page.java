package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by  .Life on 2019/07/11/0011 21:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page implements Serializable {

    //当前页
    private Integer pageNum = 1 ;

    //每页条数
    private Integer pageSize = 5;

    //总条数
    private Integer totalCount;

    //总页数
    private Integer totalPage;

    //查询参数
    private String Keyword;

    //当前页数据
    private List<Goods> goods;

    private String url;

public void setTotalCount(Integer totalCount){
    this.totalCount = totalCount;
    if (totalCount % pageSize ==0){
        this.totalPage = totalCount/pageSize;
    }else {
        this.totalPage = totalCount/pageSize +1;
        }
    }
}
