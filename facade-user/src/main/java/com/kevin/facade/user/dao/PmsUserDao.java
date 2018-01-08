package com.kevin.facade.user.dao;

import com.kevin.common.dao.BaseDao;
import com.kevin.facade.user.entity.PmsUser;

public interface PmsUserDao extends BaseDao<PmsUser> {

    PmsUser getByUsername(String username);
}
