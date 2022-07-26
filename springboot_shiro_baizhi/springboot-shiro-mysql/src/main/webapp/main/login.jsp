<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h1>我的登陆页面</h1>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        账号：<input type="text" name="username"><br>
        密码：<input type="text" name="password"><br>
        <input type="submit" value="登陆">
    </form>

</body>
</html>
