package com.kevin.common.core.service.impl;

import com.kevin.common.core.dao.BaseDao;
import com.kevin.common.entity.BaseEntity;
import com.kevin.common.exception.BaseException;
import com.kevin.common.page.PageBean;
import com.kevin.common.page.PageParam;
import com.kevin.common.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @类名: BaseServiceImpl
 * @包名：com.kevin.service.impl
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/18 14:30
 * @版本：1.0
 * @描述：业务层基础支撑类
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public int insert(T entity) throws BaseException {
        return baseDao.insert(entity);
    }

    @Override
    public int batchInsert(List<T> list) throws BaseException {
        return baseDao.batchInsert(list);
    }

    @Override
    public T getById(long id) {
        return baseDao.getById(id);
    }

    @Override
    public List<T> batchGetById(List<Long> list) {
        return baseDao.batchGetById(list);
    }

    @Override
    public int update(T entity) throws BaseException {
        return baseDao.update(entity);
    }

    @Override
    public int batchUpdate(List<T> list) throws BaseException {
        return baseDao.batchUpdate(list);
    }

    @Override
    public int deleteById(long id) {
        return baseDao.deleteById(id);
    }

    @Override
    public int batchDeleteById(List<Long> list) {
        return baseDao.batchDeleteById(list);
    }

    @Override
    public List<T> listAll() {
        return baseDao.listAll();
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        return baseDao.listPage(pageParam, paramMap);
    }
}
