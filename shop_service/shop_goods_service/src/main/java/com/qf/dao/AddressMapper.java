package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;

/**
 * Created by  .Life on 2019/07/25/0025 17:40
 */
public interface AddressMapper extends BaseMapper<Address> {

    int insertAddress(Address address);
}
