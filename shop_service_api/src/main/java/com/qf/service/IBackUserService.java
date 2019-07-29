package com.qf.service;

import com.qf.entity.BackUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 19:22
 */
public interface IBackUserService extends UserDetailsService {
    List<BackUser> queryAll();
    int insertUser(BackUser backUser);
    int updateUserRole(Integer uid,Integer[] rid);
//    BackUser login(String username,String password);
}
