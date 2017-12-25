package com.kevin.service.user.dao.impl;

import com.kevin.facade.user.dao.PmsUserDao;
import com.kevin.facade.user.entity.PmsUser;
import com.kevin.service.user.common.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class PmsUserDaoImpl extends BaseDaoImpl<PmsUser> implements PmsUserDao {

    private static final String SQL_GET_BY_USERNAME = "getByUsername";

    @Override
    public PmsUser getByUsername(String username) {
        return getSessionTemplate().selectOne(getStatement(SQL_GET_BY_USERNAME), username);
    }
}
