<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
System.out.println("Login 当前会话ID："+session.getId());
%>
 
<form action="SessionLogin" method="post">
username:<input type="text" name="username"><br/>
password:<input type="text" name="password"><br/>
<input type="submit">
</form>
</body>
</html>