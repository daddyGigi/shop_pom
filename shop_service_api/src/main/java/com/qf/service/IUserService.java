package com.qf.service;

import com.qf.entity.User;

/**
 * Created by  .Life on 2019/07/19/0019 19:50
 */
public interface IUserService {
    int register(User user);

    User queryByUsername(String username);

    int updatePassword(String username,String passwordn);

    User login(User user);
}
