package com.kevin.common.dao;

import com.kevin.common.page.PageBean;
import com.kevin.common.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @类名: BaseDao
 * @包名：com.kevin.dao
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/18 14:22
 * @版本：1.0
 * @描述：数据访问层基础支撑接口
 */
public interface BaseDao<T> {

    /**
     * @param entity 实体对象
     * @return 影响行数
     * @方法名：insert
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:23
     * @描述：根据实体对象保存记录
     */
    int insert(T entity);

    /**
     * @param list 对象列表
     * @return 影响行数
     * @方法名：batchInsert
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:23
     * @描述：根据实体对象列表批量保存记录
     */
    int batchInsert(List<T> list);

    /**
     * @param id id
     * @return 实体对象
     * @方法名：getById
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:29
     * @描述：根据id查找记录
     */
    T getById(long id);

    /**
     * @方法名：batchGetById
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/9/25 21:03
     * @描述：根据ids查找记录
     * @param list
     * @return java.util.List<T>
     * @exception
     */
    List<T> batchGetById(List<Long> list);

    /**
     * @param entity 实体对象
     * @return 影响行数
     * @方法名：update
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:28
     * @描述：更新实体对象对应的记录
     */
    int update(T entity);

    /**
     * @param list
     * @return 影响行数
     * @方法名：update
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:29
     * @描述：批量更新实体对象对应的记录
     */
    int batchUpdate(List<T> list);

    /**
     * @param id id
     * @return 影响行数
     * @方法名：deleteById
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:27
     * @描述：根据id删除记录
     */
    int deleteById(long id);

    /**
     * @param list id列表
     * @return 影响行数
     * @方法名：batchDeleteById
     * @作者：kevin[wangqi2017@xinhua.org]
     * @时间：2017/7/18 14:27
     * @描述：批量根据id删除记录
     */
    int batchDeleteById(List<Long> list);

    /**
     * @method: listAll
     * @author: kevin[wangqi2017@xinhua.org]
     * @date: 2017/12/23 12:31
     * @version: 1.0
     * @desc: 列表所有实体
     * @param
     * @return java.util.List<T>
     * @throws
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
