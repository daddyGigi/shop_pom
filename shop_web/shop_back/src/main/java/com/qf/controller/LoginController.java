package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by  .Life on 2019/07/01/0001 17:35
 */
@Controller
@SessionAttributes("loginUser")
public class LoginController {
    @Reference
    private IBackUserService userService;

    @RequestMapping("/tologin")
    public String toLogin(){

        return "/login";
    }
    @RequestMapping("/noperssion")
    public String noPerssion(){
        return "noperssion";
    }

   /* @RequestMapping("/login")
    public String Login(String username, String password, Model model){

        BackUser user = userService.login(username, password);

        if (user!=null){
            model.addAttribute("loginUser",user);
            return "index";
        }

        return "redirect:/tologin?error=1";
    }*/
}
