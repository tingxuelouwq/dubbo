package com.kevin.user.service;

import com.kevin.common.service.BaseService;
import com.kevin.user.entity.PmsUser;

public interface PmsUserService extends BaseService<PmsUser> {

    PmsUser getByUsername(String username);
}
