<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>菜单页面</title>
</head>
<body>
    <h1>菜单页面</h1>
    <ul>
        <li><a href="">用户管理</a></li>
        <li><a href="">订单管理</a></li>
        <li><a href="">商品管理</a></li>
        <li><a href="">物流管理</a></li>
    </ul>
    <h1><a href="${pageContext.request.contextPath}/user/logout">退出登陆</a></h1>
</body>