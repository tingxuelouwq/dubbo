package com.kevin.common.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @描述：分页组件
 * @作者：kevin
 * @时间：2017/7/5 16:42
 * @版本：1.0
 */
public class PageBean implements Serializable {
    private static final long serialVersionUID = 7009985934550739960L;

    // 指定的或者是页面参数
    private int currentPage;    // 当前页
    private int numPerPage;     // 每页显示多少条

    // 查询数据库
    private int totalCount; // 总记录数
    private List<Object> recordList;    // 本页的数据列表

    // 计算
    private int pageCount;  // 总页数
    private int beginPageIndex; // 页码列表的开始索引（包含）
    private int endPageIndex;   // 页码列表的结束索引（包含）

    public PageBean() {}

    /**
     * 只接受前4个必要的属性，会自动计算出其他3个属性的值
     * @param currentPage
     * @param numPerPage
     * @param totalCount
     * @param recordList
     */
    public PageBean(int currentPage, int numPerPage, int totalCount, List<Object> recordList) {
        this.currentPage = currentPage;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
        this.recordList = recordList;

        // 计算总页码
        pageCount = (totalCount + numPerPage - 1) / numPerPage;

        // 计算beginPageIndex和endPageIndex
        // >> 总页数不多于10页，则全部显示
        if (pageCount <= 10) {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        } else {    // >> 总页数多于10页，则显示当前页附近的共10个页码
            // 当前页附近的共10个页码（前4个+当前页+后5个）
            beginPageIndex = currentPage - 4;
            endPageIndex = currentPage + 5;
            if (beginPageIndex < 1) {   // 当前面的页码不足4个时，则显示前10个页码
                beginPageIndex = 1;
                endPageIndex = 10;
            } else if (endPageIndex > pageCount) {  // 当后面的页码不足5个时，则显示后10个页码
                endPageIndex = pageCount;
                beginPageIndex = pageCount - 10 + 1;
            }
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Object> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Object> recordList) {
        this.recordList = recordList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }
}
