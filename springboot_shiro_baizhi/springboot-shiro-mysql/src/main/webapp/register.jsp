<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>登陆页面</title>
</head>
<body>
    <h1>我的登陆页面</h1>
    <form action="${pageContext.request.contextPath}/user/register" method="post">
        <input type="text" name="username"><br>
        <input type="text" name="password"><br>
        <input type="submit" value="用户注册"><br>
    </form>
</body>
</html>

