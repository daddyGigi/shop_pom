package com.qf.service;

import com.qf.entity.Types;

import java.util.List;

/**
 * Created by  .Life on 2019/07/06/0006 10:02
 */
public interface ITypesService {
    List<Types> queryTypes();
    int insertType(Types types);
}
