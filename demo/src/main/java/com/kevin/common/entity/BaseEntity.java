package com.kevin.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kevin on 2017/7/4.
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 2108732904738509088L;

    /** 主键id **/
    private Long id;

    /** 版本号 **/
    private Integer version;

    /** 创建时间 **/
    private Date createTime;

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
