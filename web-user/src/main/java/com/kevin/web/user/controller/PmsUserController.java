package com.kevin.web.user.controller;

import com.kevin.common.controller.BaseController;
import com.kevin.common.page.PageBean;
import com.kevin.common.page.PageParam;
import com.kevin.facade.user.service.PmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PmsUserController extends BaseController {

    @Autowired
    private PmsUserService pmsUserService;

    @RequestMapping("/users")
    public ModelAndView listUsers(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                  @RequestParam(value = "numPerPage", required = false) Integer numPerPage) {
        ModelAndView modelAndView = new ModelAndView("pms/user_list");

        if (pageNum == null) {
            pageNum = 1;
        }
        if (numPerPage == null) {
            numPerPage = 5;
        }
        PageParam pageParam = new PageParam(pageNum, numPerPage);
        PageBean pageBean = pmsUserService.listPage(pageParam, null);
        modelAndView.addObject("pageBean", pageBean);

        return modelAndView;
    }
}
