<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2017/12/24
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
    <title>用户列表</title>
    <link href="${ctx}/static/css/user_list.css" type="text/css" rel="stylesheet">
    <script src="${ctx}/static/js/user_list.js" type="text/javascript"></script>
</head>
<body>
    <c:set var="currentPage" value="${pageBean.currentPage}"/>
    <c:set var="numPerPage" value="${pageBean.numPerPage}"/>
    <c:set var="totalCount" value="${pageBean.totalCount}"/>
    <c:set var="recordList" value="${pageBean.recordList}"/>
    <c:set var="pageCount" value="${pageBean.pageCount}"/>
    <c:set var="beginPageIndex" value="${pageBean.beginPageIndex}"/>
    <c:set var="endPageIndex" value="${pageBean.endPageIndex}"/>
    <c:set var="prePageNum" value="1"/>
    <c:set var="nextPageNum" value="${pageCount}"/>

    <div>
        <div class="pageContent">
            <table>
                <thead>
                    <tr>
                        <th width="150px">用户名</th>
                        <th width="250px">邮箱</th>
                        <th width="200px">手机号码</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${recordList}">
                        <tr>
                                <td>${item.username}</td>
                                <td>${item.email}</td>
                                <td>${item.mobileNo}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="panelBar">
            <span class="summary">总共${pageCount}页&nbsp;&nbsp;共${totalCount}条</span>
            <a href="${ctx}/users?pageNum=1&numPerPage=${numPerPage}"><span class="numBar">|<</span></a>
            <c:if test="${currentPage > 1}">
                <c:set var="prePageNum" value="${currentPage - 1}"/>
            </c:if>
            <a href="${ctx}/users?pageNum=${prePageNum}&numPerPage=${numPerPage}"><span class="numBar"><<</span></a>
            <c:forEach var="item" begin="${beginPageIndex}" end="${endPageIndex}" step="1">
                <c:choose>
                    <c:when test="${currentPage == item}">
                        <a href="${ctx}/users?pageNum=${item}&numPerPage=${numPerPage}"><span class="targetNumBar">${item}</span></a>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctx}/users?pageNum=${item}&numPerPage=${numPerPage}"><span class="numBar">${item}</span></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage < pageCount}">
                <c:set var="nextPageNum" value="${currentPage + 1}"/>
            </c:if>
            <a href="${ctx}/users?pageNum=${nextPageNum}&numPerPage=${numPerPage}"><span class="numBar">>></span></a>
            <a href="${ctx}/users?pageNum=${pageCount}&numPerPage=${numPerPage}"><span class="numBar">>|</span></a>
            <span class="goBar" onclick="go(${pageCount})">Go</span>
            <input type="text" class="goNumBar" id="goNum" name="goNum" value="${currentPage}" onkeypress="goForEnter(${pageCount})"/>
            <span class="stripBar">每页<select id="newNumPerPage" onchange="changeNumPerPage(this.value)">
                <option value="5" <c:if test="${numPerPage == 5}">selected="selected"</c:if>>5</option>
                <option value="10" <c:if test="${numPerPage == 10}">selected="selected"</c:if>>10</option>
                <option value="15" <c:if test="${numPerPage == 15}">selected="selected"</c:if>>15</option>
                <option value="20" <c:if test="${numPerPage == 20}">selected="selected"</c:if>>20</option>
                <option value="25" <c:if test="${numPerPage == 25}">selected="selected"</c:if>>25</option>
            </select>条</span>
        </div>
    </div>
</body>
</html>
