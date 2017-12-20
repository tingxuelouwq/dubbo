package com.kevin.service;

import com.kevin.dto.PageBean;
import com.kevin.dto.PageParam;
import com.kevin.entity.PmsUser;

import java.util.Map;

/**
 * @类名：IPmsUserService
 * @包名：com.kevin.service
 * @作者：kevin
 * @时间：2017/7/28 19:49
 * @版本：1.0
 * @描述：用户表业务层接口
 */
public interface IPmsUserService {

    /**
     * @方法名：save
     * @作者：kevin
     * @时间：2017/7/28 19:50
     * @描述：保存用户
     * @param pmsUser
     * @return
     */
    void save(PmsUser pmsUser);

    /**
     * @方法名：getById
     * @作者：kevin
     * @时间：2017/7/28 19:51
     * @描述：根据id获取用户对象
     * @param userId
     * @return 
     */
    PmsUser getById(Long userId);

    /**
     * @方法名：findByUserNo
     * @作者：kevin
     * @时间：2017/7/28 19:52
     * @描述：根据登录名获取用户对象
     * @param userNo
     * @return 
     */
    PmsUser findByUserNo(String userNo);

    /**
     * @方法名：deleteById，type='1'的超级管理员不能删除
     * @作者：kevin
     * @时间：2017/7/28 19:53
     * @描述：根据id删除一个用户
     * @param userId
     * @return 
     */
    void deleteById(Long userId);

    /**
     * @方法名：update
     * @作者：kevin
     * @时间：2017/7/28 19:55
     * @描述：更新用户对象
     * @param pmsUser
     * @return
     */
    void update(PmsUser pmsUser);

    /**
     * @方法名：updateUserPwd
     * @作者：kevin
     * @时间：2017/7/28 19:57
     * @描述：根据用户id更新用户密码
     * @param userId
     * @param newPwd
     * @return 
     */
    void updateUserPwd(Long userId, String newPwd);

    /**
     * @方法名：listPage
     * @作者：kevin
     * @时间：2017/7/28 19:59
     * @描述：查询并分页列出用户信息
     * @param pageParam
     * @param paramMap
     * @return 
     */
    PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);
}
