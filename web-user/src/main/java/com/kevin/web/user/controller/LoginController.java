package com.kevin.web.user.controller;

import com.kevin.common.web.controller.BaseController;
import com.kevin.common.util.StringUtil;
import com.kevin.facade.user.entity.PmsUser;
import com.kevin.facade.user.service.PmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private PmsUserService pmsUserService;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("userPwd") String userPwd) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (StringUtil.isEmpty(username) ||
                StringUtil.isEmpty(userPwd)) {
            modelAndView.addObject("errorMsg", "请输入用户名密码");
            return modelAndView;
        }

        PmsUser pmsUser = pmsUserService.getByUsername(username);
        if (pmsUser == null) {
            modelAndView.addObject("errorMsg", "用户名密码错误");
            return modelAndView;
        }

        modelAndView.setViewName("forward:/users");
        return modelAndView;
    }
}
