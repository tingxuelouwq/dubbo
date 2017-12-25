package com.kevin.user.service.impl;

import com.kevin.common.service.impl.BaseServiceImpl;
import com.kevin.user.dao.PmsUserDao;
import com.kevin.user.entity.PmsUser;
import com.kevin.user.service.PmsUserService;
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
