package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.TypesMapper;
import com.qf.entity.Types;
import com.qf.service.ITypesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by  .Life on 2019/07/06/0006 10:03
 */
@Service
public class TypesServiceImpl implements ITypesService {
    @Autowired
    private TypesMapper typesMapper;
    @Override
    public List<Types> queryTypes() {
        return typesMapper.queryAllTypes();
    }

    @Override
    public int insertType(Types types) {
        return typesMapper.insert(types);
    }
}
