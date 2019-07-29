package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.pass.BCryptUtil;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by  .Life on 2019/07/19/0019 19:51
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User username = userMapper.selectOne(queryWrapper);
        if (username != null){
            //说明用户名已存在
            return -1;
        }
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("eamil",user.getEmail());
        User email = userMapper.selectOne(queryWrapper);
        if (email != null){
            //说明邮箱已存在
            return -2;
        }
        user.setPassword(BCryptUtil.hashPassword(user.getPassword()));
        //可以注册
        return userMapper.insert(user);
    }

    @Override
    public User queryByUsername(String username) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }



    @Override
    public int updatePassword(String username, String password) {
        User user = queryByUsername(username);
        user.setPassword(BCryptUtil.hashPassword(password));
        return userMapper.updateById(user);

    }

    @Override
    public User login(User user) {
        User u = queryByUsername(user.getUsername());
        if (u != null){
            //用户名不等于空再判断密码
            boolean flag = BCryptUtil.checkPassword(u.getPassword(), user.getPassword());
            if (flag){
                //登录成功
                return u;
            }
        }
        return null;
    }
}
