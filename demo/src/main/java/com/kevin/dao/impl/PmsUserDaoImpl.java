package com.kevin.dao.impl;

import com.kevin.dao.PmsUserDao;
import com.kevin.entity.PmsUser;
import org.springframework.stereotype.Repository;

/**
 * @描述：用户表数据访问层接口实现类
 * @作者：kevin
 * @时间：2017/7/7 10:15
 * @版本：1.0
 */
@Repository
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {
    @Override
    public PmsUser findByUserNo(String userNo) {
        return super.getSessionTemplate().selectOne(getStatement("findByUserNo"), userNo);
    }
}
