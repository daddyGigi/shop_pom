package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackRole;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * Created by  .Life on 2019/07/01/0001 19:15
 */
@Controller
@RequestMapping("/buser")
public class BackUserController {
    @Reference
    private IBackUserService backUserService;
    @RequestMapping("/list")
    public String list(Model model){
        List<BackUser> backUsers = backUserService.queryAll();
        model.addAttribute("users",backUsers);
        return "buserlist";
    }
    @RequestMapping("/insert")
    public String userAdd(BackUser backUser){
        backUserService.insertUser(backUser);
            return "redirect:/buser/list";
    }
    @RequestMapping("/updaterole")
    public String updaterole(Integer uid,Integer[] rid){

        backUserService.updateUserRole(uid,rid);
        return "redirect:/buser/list";
    }
}
