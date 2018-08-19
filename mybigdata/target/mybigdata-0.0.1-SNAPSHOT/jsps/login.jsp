<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<font color="red" size="20">
		<c:if test="${error!=null}"><c:out value="${error} "/></c:if>
	</font>
	<form action='<c:url value="/user/login"/>' method="post">
		<table>
			<tr>
				<td>Username:</td><td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Password:</td><td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="Login">
	</form>
</body>
</html>