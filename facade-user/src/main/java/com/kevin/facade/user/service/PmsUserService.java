package com.kevin.facade.user.service;

import com.kevin.facade.user.common.service.BaseService;
import com.kevin.facade.user.entity.PmsUser;

public interface PmsUserService extends BaseService<PmsUser> {

    PmsUser getByUsername(String username);
}
