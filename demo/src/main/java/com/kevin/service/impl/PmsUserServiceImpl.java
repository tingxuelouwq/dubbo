package com.kevin.service.impl;

import com.kevin.dao.PmsUserDao;
import com.kevin.dto.PageBean;
import com.kevin.dto.PageParam;
import com.kevin.entity.PmsUser;
import com.kevin.service.IPmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @类名：PmsUserServiceImpl
 * @包名：com.kevin.service
 * @作者：kevin
 * @时间：2017/7/28 20:03
 * @版本：1.0
 * @描述：用户表业务层接口实现类
 */
@Service
public class PmsUserServiceImpl implements IPmsUserService {

    @Autowired
    private PmsUserDao pmsUserDao;

    @Override
    public void save(PmsUser pmsUser) {
        pmsUserDao.insert(pmsUser);
    }

    @Override
    public PmsUser getById(Long userId) {
        return pmsUserDao.getById(userId);
    }

    @Override
    public PmsUser findByUserNo(String userNo) {
        return pmsUserDao.findByUserNo(userNo);
    }

    @Override
    public void deleteById(Long userId) {
        pmsUserDao.deleteById(userId);
    }

    @Override
    public void update(PmsUser pmsUser) {
        pmsUserDao.update(pmsUser);
    }

    @Override
    public void updateUserPwd(Long userId, String newPwd) {
        PmsUser pmsUser = pmsUserDao.getById(userId);
        pmsUser.setUserPwd(newPwd);
        pmsUser.setPwdErrorCount(0);
        pmsUser.setChangedPwd(true);
        pmsUserDao.update(pmsUser);
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return pmsUserDao.listPage(pageParam, paramMap);
    }
}
