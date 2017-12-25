package com.kevin.service.user.service.impl;

import com.kevin.facade.user.dao.PmsUserDao;
import com.kevin.facade.user.entity.PmsUser;
import com.kevin.facade.user.service.PmsUserService;
import com.kevin.service.user.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PmsUserServiceImpl extends BaseServiceImpl<PmsUser> implements PmsUserService {

    private PmsUserDao pmsUserDao;

    @Autowired
    public void setPmsUserDao(PmsUserDao pmsUserDao) {
        this.pmsUserDao = pmsUserDao;
        super.setBaseDao(pmsUserDao);
    }

    @Override
    public PmsUser getByUsername(String username) {
        return pmsUserDao.getByUsername(username);
    }
}
