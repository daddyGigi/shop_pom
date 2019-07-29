package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * Created by  .Life on 2019/07/25/0025 17:37
 */
public interface IAddressService {

    List<Address> queryByUid(Integer uid);

    int insertAddress(Address address);

    Address queryByAid(Integer aid);
}
