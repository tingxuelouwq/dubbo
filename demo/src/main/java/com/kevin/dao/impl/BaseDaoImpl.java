package com.kevin.dao.impl;

import com.kevin.entity.BaseEntity;
import com.kevin.exception.BizException;
import com.kevin.dto.PageBean;
import com.kevin.dto.PageParam;
import com.kevin.dao.BaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述：数据访问层基础支撑类
 * @作者：kevin
 * @时间：2017/7/4 22:16
 * @版本：1.0
 */
public abstract class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {
    protected static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_DELETE_BY_ID = "deleteById";
    public static final String SQL_BATCH_DELETE_BY_ID = "batchDeleteById";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_BATCH_UPDATE = "batchUpdate";
    public static final String SQL_GET_BY_ID = "getById";
    public static final String SQL_LIST_ALL = "listAll";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";

    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public SqlSessionTemplate getSessionTemplate() {
        return sessionTemplate;
    }

    @Override
    public long insert(T entity) {
        if (entity == null) {
            return 0;
        }

        int result = sessionTemplate.insert(getStatement(SQL_INSERT), entity);
        if (result <= 0) {
            throw new BizException(BizException.DB_INSERT_RESULT_0, "数据库操作,insert返回0.{%s}", getStatement(SQL_INSERT));
        }

        return result;
    }

    @Override
    public long insert(List<T> list) {
        if (list == null || list.size() <= 0) {
            return 0;
        }

        int result = sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
        if (result <= 0) {
            throw new BizException(BizException.DB_INSERT_RESULT_0, "数据库操作,insert返回0.{%s}", getStatement(SQL_BATCH_INSERT));
        }

        return result;
    }

    @Override
    public int deleteById(long id) {
        return sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    @Override
    public int deleteById(List<Long> list) {
        return sessionTemplate.delete(getStatement(SQL_BATCH_DELETE_BY_ID), list);
    }

    @Override
    public int update(T entity) {
        if (entity == null) {
            return 0;
        }

        int result = sessionTemplate.update(getStatement(SQL_UPDATE), entity);
        if (result <= 0) {
            throw new BizException(BizException.DB_UPDATE_RESULT_0, "数据库操作,update返回0.{%s}", getStatement(SQL_UPDATE));
        }

        return result;
    }

    @Override
    public int update(List<T> list) {
        if (list == null || list.size() <= 0) {
            return 0;
        }

        int result = sessionTemplate.update(getStatement(SQL_BATCH_UPDATE), list);
        if (result <= 0) {
            throw new BizException(BizException.DB_UPDATE_RESULT_0, "数据库操作,update返回0.{%s}", getStatement(SQL_BATCH_UPDATE));
        }

        return result;
    }

    @Override
    public T getById(long id) {
        return sessionTemplate.selectOne(getStatement(SQL_GET_BY_ID), id);
    }

    @Override
    public List<T> listAll() {
        return sessionTemplate.selectList(getStatement(SQL_LIST_ALL));
    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());

        // 统计总记录数
        Long count = sessionTemplate.selectOne(getStatement(SQL_LIST_PAGE_COUNT), pageParam);

        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE), paramMap);

        // 构造分页对象
        return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), count.intValue(), list);
    }

    public String getStatement(String sqlId) {
        String name = this.getClass().getInterfaces()[0].getName();
        StringBuffer sb = new StringBuffer();
        sb.append(name).append(".").append(sqlId);
        String statement = sb.toString();

        return statement;
    }
}
