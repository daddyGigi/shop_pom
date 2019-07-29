package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackRole;
import com.qf.service.IBackRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 21:01
 */
@Controller
@RequestMapping("/brole")
public class BackRoleController {
    @Reference
    private IBackRoleService backRoleService;
    @RequestMapping("/list")
    public String list(Model model){
        List<BackRole> backRoles = backRoleService.queryAll();
        model.addAttribute("role",backRoles);
        return "brolelist";
    }
    @RequestMapping("/insert")
    public String roleAdd(BackRole backRole){
        backRoleService.insert(backRole);
        return "redirect:/brole/list";
    }
    @RequestMapping("/listajax")
    @ResponseBody
    public List<BackRole> roleList(Integer uid){
        List<BackRole> backRoles = backRoleService.roleListByUid(uid);
        return backRoles;
    }
    @RequestMapping("/updatePower")
    @ResponseBody
    public String updatePower(Integer rid,@RequestParam("pids[]") Integer[] pids){
            backRoleService.updateRolePowers(rid,pids);
        return "succ";
    }
}
