package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetils;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 21:17
 */
public interface OrderDetilsMapper extends BaseMapper<OrderDetils> {

    int insertDetils(@Param("orderDetils") List<OrderDetils> orderDetils,@Param("tableIndex")int tableIndex);
}
