<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页</title>
</head>
<body>
受保护页1
<%
 //session=(HttpSession)request.getAttribute("session");

 //session=session==null?request.getSession():session;
 System.out.println("index 当前会话ID:"+session.getId());
%>
</body>
</html>