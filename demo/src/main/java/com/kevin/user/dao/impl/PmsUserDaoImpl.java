package com.kevin.user.dao.impl;

import com.kevin.common.dao.BaseDaoImpl;
import com.kevin.user.dao.PmsUserDao;
import com.kevin.user.entity.PmsUser;

/**
 * @描述：用户表数据访问层接口实现类
 * @作者：kevin
 * @时间：2017/7/7 10:15
 * @版本：1.0
 */
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {
    @Override
    public PmsUser findByUserNo(String userNo) {
        return super.getSessionTemplate().selectOne(getStatement("findByUserNo"), userNo);
    }
}
