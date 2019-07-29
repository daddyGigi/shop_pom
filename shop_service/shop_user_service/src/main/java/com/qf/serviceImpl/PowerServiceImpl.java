package com.qf.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.PowerMapper;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by  .Life on 2019/07/02/0002 16:17
 */
@Service
public class PowerServiceImpl implements IPowerService {
    @Autowired
    private PowerMapper powerMapper;
    @Override
    public List<Power> powerList() {
        return powerMapper.queryAllpowers();
    }

    @Override
    public int insert(Power power) {
        return powerMapper.insert(power);
    }

    @Override
    public List<Power> queryByRid(Integer rid) {

        return powerMapper.queryPowerByRid(rid);
    }
}
