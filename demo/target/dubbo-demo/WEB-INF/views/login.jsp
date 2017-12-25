<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 2017/12/23
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/taglib.jsp"%>
<html>
<head>
    <title>用户登录</title>
    <link href="${ctx}/static/css/login.css" type="text/css" rel="stylesheet">
</head>
<body>
    <div>
        <div class="content">
            <form action="${ctx}/login" method="post">
                <div>
                    <input name="username" type="text" placeholder="请输入用户名"/>
                </div>
                <div>
                    <input name="userPwd" type="password" placeholder="请输入密码"/>
                    <br/><span class="info">${errorMsg}</span>
                </div>
                <div>
                    <input type="submit" value="登录" />
                </div>
            </form>
        </div>
        <div>
            <ul>
                <li>测试帐号：admin</li>
                <li>默认密码：123456</li>
            </ul>
        </div>
    </div>
</body>
</html>
