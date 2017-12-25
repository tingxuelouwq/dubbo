package com.kevin.user.entity;

import com.kevin.common.entity.BaseEntity;

/**
 * @class: PmsUser
 * @package: com.kevin.user.entity
 * @author: kevin[wangqi2017@xinhua.org]
 * @date: 2017/12/23 12:15
 * @version: 1.0
 * @desc: 权限用户实体类
 */
public class PmsUser extends BaseEntity {
    private static final long serialVersionUID = 595299357711944545L;

    private String username;
    private String userPwd;
    private String email;
    private String mobileNo;

    public PmsUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
