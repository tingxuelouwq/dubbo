package com.kevin.common.page;

import java.io.Serializable;

/**
 * @描述：分页参数传递工具类
 * @作者：kevin
 * @时间：2017/7/5 16:42
 * @版本：1.0
 */
public class PageParam implements Serializable {
    private static final long serialVersionUID = -4797994199161260682L;

    private int pageNum;    // 当前页数
    private int numPerPage; // 每页记录数

    public PageParam(int pageNum, int numPerPage) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
