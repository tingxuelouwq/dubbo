package com.kevin.dao;

import com.kevin.dto.PageBean;
import com.kevin.dto.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @描述：数据访问层基础支撑接口
 * @作者：kevin
 * @时间：2017/7/4 19:54
 * @版本：1.0
 */
public interface BaseDao<T> {

    /**
     * 根据实体对象新增记录
     * @param entity
     * @return id
     */
    long insert(T entity);

    /**
     * 批量保存对象
     * @param list
     * @return
     */
    long insert(List<T> list);

    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    int deleteById(long id);

    /**
     * 批量根据id删除记录
     * @param
     * @return
     */
    int deleteById(List<Long> list);

    /**
     * 更新实体对应的记录
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 批量更新对象
     * @param list
     * @return
     */
    int update(List<T> list);

    /**
     * 根据id查找记录
     * @param id
     * @return
     */
    T getById(long id);

    /**
     * 查询所有记录
     * @return
     */
    List<T> listAll();

    /**
     * 分页查询
     * @param pageParam 分页参数
     * @param paramMap 业务条件查询参数
     * @return
     */
    PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);
}
