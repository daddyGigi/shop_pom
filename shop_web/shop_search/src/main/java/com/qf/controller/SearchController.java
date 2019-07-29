package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by  .Life on 2019/07/11/0011 16:22
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Reference
    private ISearchService searchService;

    @RequestMapping("/searchByKey")
    public String searchByKey(Page page, Model model){

        page = searchService.searchByKey(page);
        page.setUrl("/search/searchByKey");

        model.addAttribute("page",page);

        return "searchlist";
    }
}
