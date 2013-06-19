<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>Insert title here</title>
</head>
<body>
<form action="j_security_check" method="post">
<table border="1">
<tr><td>User Name</td><td><input type="text" name="j_username"/></td></tr>
<tr><td>Password</td><td><input type="password" name="j_password"/></td></tr>
</table>
<input type="submit" value="login" />
</form>

</body>
</html>