package com.kevin.controller;

import com.kevin.constant.SessionConstant;
import com.kevin.constant.UserStatusEnum;
import com.kevin.constant.UserTypeEnum;
import com.kevin.entity.PmsUser;
import com.kevin.service.IPmsUserService;
import com.kevin.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @类名：com.kevin.user
 * @包名：PmsUserController
 * @作者：kevin
 * @时间：2017/7/11 9:43
 * @版本：1.0
 * @描述：PmsUser控制层
 */
@Controller
public class PmsUserController {

    private static final Logger log = LoggerFactory.getLogger(PmsUserController.class);

    @Autowired
    private IPmsUserService pmsUserService;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("userNo") String userNo,
                              @RequestParam("userPwd") String userPwd,
                              HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            if (StringUtils.isEmpty(userNo)) {
                modelAndView.setViewName("login");
                modelAndView.addObject("userNoMsg", "用户名不能为空");
                return modelAndView;
            }

            modelAndView.addObject("userNo", userNo);
            PmsUser pmsUser = pmsUserService.findByUserNo(userNo);
            if (pmsUser == null) {
                log.warn("==no such user");
                modelAndView.setViewName("login");
                modelAndView.addObject("userNoMsg", "用户名或密码不正确");
                return modelAndView;
            }

            if (pmsUser.getStatus().intValue() == UserStatusEnum.INACTIVE.getValue()) {
                log.warn("==账号[" + userNo + "]已被冻结");
                modelAndView.setViewName("login");
                modelAndView.addObject("userNoMsg", "该账号已被冻结");
                return modelAndView;
            }

            if (StringUtils.isEmpty(userPwd)) {
                modelAndView.setViewName("login");
                modelAndView.addObject("userPwdMsg", "密码不能为空");
                return modelAndView;
            }

            // 加密明文密码
            if (pmsUser.getUserPwd().equals(DigestUtils.sha1Hex(userPwd))) {
                // 将用户信息放入Session
                session.setAttribute(SessionConstant.USER_SESSION_KEY, pmsUser);

                // 将主账号ID放入Session
                if (UserTypeEnum.MAIN_USER.getValue().equals(pmsUser.getUserType())) {
                    session.setAttribute(SessionConstant.MAIN_USER_ID_SESSION_KEY, pmsUser.getId());
                } else if (UserTypeEnum.SUB_USER.getValue().equals(pmsUser.getUserType())) {
                    session.setAttribute(SessionConstant.MAIN_USER_ID_SESSION_KEY, pmsUser.getMainUserId());
                } else {    // 其他类型用户的主账号ID默认为0
                    session.setAttribute(SessionConstant.MAIN_USER_ID_SESSION_KEY, 0);
                }

                modelAndView.addObject("userNo", userNo);
                modelAndView.addObject("lastLoginTime", pmsUser.getLastLoginTime());

                try {   // 更新登录数据
                    pmsUser.setLastLoginTime(new Date());
                    pmsUser.setPwdErrorCount(0);
                    pmsUserService.update(pmsUser);
                } catch (Exception e) {
                    modelAndView.setViewName("login");
                    modelAndView.addObject("errorMsg", e.getMessage());
                }

                // 判断用户是否重置了密码，如果重置，弹出强制修改密码页面
                modelAndView.addObject("isChangedPwd", pmsUser.getChangedPwd());
                modelAndView.setViewName("index");
                return modelAndView;
            } else {
                log.warn("==wrong password");
                Integer pwdErrorCount = pmsUser.getPwdErrorCount();
                if (pwdErrorCount == null) {
                    pwdErrorCount = 0;
                }
                pmsUser.setPwdErrorCount(pwdErrorCount + 1);
                pmsUser.setPwdErrorTime(new Date());

                String msg = "";
                if (pmsUser.getPwdErrorCount().intValue() >= SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT) {
                    pmsUser.setStatus(UserStatusEnum.INACTIVE.getValue());
                    msg += "<br/>密码已连续输错[" + SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT + "]次，账号被冻结";
                } else {
                    msg += "<br/>密码错误，再输错[" +
                            (SessionConstant.WEB_PWD_INPUT_ERROR_LIMIT - pmsUser.getPwdErrorCount().intValue()) +
                            "]次将冻结账户";
                }
                pmsUserService.update(pmsUser);

                modelAndView.setViewName("login");
                modelAndView.addObject("userPwdMsg", msg);
                return modelAndView;
            }
        } catch (Exception e) {
            log.error("login exception:", e);
            modelAndView.setViewName("login");
            modelAndView.addObject("errorMsg", "登录出错");
        }

        return modelAndView;
    }
}
