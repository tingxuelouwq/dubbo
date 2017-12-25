package com.kevin.user.dao;

import com.kevin.common.dao.BaseDao;
import com.kevin.user.entity.PmsUser;

public interface PmsUserDao extends BaseDao<PmsUser> {

    PmsUser getByUsername(String username);
}
