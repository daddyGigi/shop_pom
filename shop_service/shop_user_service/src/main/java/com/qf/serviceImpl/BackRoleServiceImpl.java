package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.BackRoleMapper;
import com.qf.dao.BackUserMapper;
import com.qf.dao.RolePowerMapper;
import com.qf.entity.BackRole;
import com.qf.entity.RolePowerTable;
import com.qf.service.IBackRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 21:09
 */
@Service
public class BackRoleServiceImpl implements IBackRoleService {
    @Autowired
    private BackRoleMapper backRoleMapper;
    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public List<BackRole> queryAll() {
        return backRoleMapper.selectList(null);
    }

    @Override
    public int insert(BackRole backRole) {
        return backRoleMapper.insert(backRole);
    }

    @Override
    public List<BackRole> roleListByUid(Integer uid) {
        return backRoleMapper.queryRoleByUid(uid);
    }

    @Override
    public int updateRolePowers(Integer rid, Integer[] pids) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid",rid);
        rolePowerMapper.delete(queryWrapper);

        for (Integer pid : pids) {
            rolePowerMapper.insert(new RolePowerTable(rid,pid));
        }
        return 1;
    }
}
