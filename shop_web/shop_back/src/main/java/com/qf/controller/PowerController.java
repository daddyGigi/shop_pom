package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by  .Life on 2019/07/02/0002 16:10
 */
@Controller
@RequestMapping("/power")
public class PowerController {
    @Reference
    private IPowerService powerService;
    @RequestMapping("/list")
    public String list(Model model){
        List<Power> powers = powerService.powerList();
        model.addAttribute("powers",powers);
        return "powerlist";
    }
    @RequestMapping("/listajax")
    @ResponseBody
    public List<Power> ajaxList(){
        return powerService.powerList();
    }
    @RequestMapping("/insert")
    public String insert(Power power){
            powerService.insert(power);
        return "redirect:/power/list";
    }
    @RequestMapping("/queryPowersByRid")
    @ResponseBody
    public List<Power> queryRid(Integer rid){
        return powerService.queryByRid(rid);

    }
}
