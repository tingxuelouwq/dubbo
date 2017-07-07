package com.kevin.user.dao;

import com.kevin.common.dao.BaseDao;
import com.kevin.user.entity.PmsUser;

/**
 * @描述：用户表数据访问层接口
 * @作者：kevin
 * @时间：2017/7/5 12:52
 * @版本：1.0
 */
public interface PmsUserDao extends BaseDao<PmsUser> {

    /**
     * 根据用户登录名获取用户信息
     * @param userNo
     * @return
     */
    PmsUser findByUserNo(String userNo);
}
