package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Types;
import com.qf.service.ITypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by  .Life on 2019/07/06/0006 9:59
 */
@Controller
@RequestMapping("/types")
public class TypesController {
    @Reference
    private ITypesService typesService;

    @RequestMapping("/list")
    public String typeList(Model model){
        List<Types> types = typesService.queryTypes();
        model.addAttribute("types",types);
        return "typeslist";
    }
    @RequestMapping("/listajax")
    @ResponseBody
    public List<Types> ajaxList(){
        return typesService.queryTypes();
    }

    @RequestMapping("/insert")
    public String insert(Types types){
            typesService.insertType(types);
        return "redirect:/types/list";
    }
}
