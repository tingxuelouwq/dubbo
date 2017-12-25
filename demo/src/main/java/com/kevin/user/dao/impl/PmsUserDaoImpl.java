package com.kevin.user.dao.impl;

import com.kevin.common.dao.impl.BaseDaoImpl;
import com.kevin.user.dao.PmsUserDao;
import com.kevin.user.entity.PmsUser;
import org.springframework.stereotype.Repository;

@Repository
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {

    private static final String SQL_GET_BY_USERNAME = "getByUsername";

    @Override
    public PmsUser getByUsername(String username) {
        return getSessionTemplate().selectOne(getStatement(SQL_GET_BY_USERNAME), username);
    }
}
