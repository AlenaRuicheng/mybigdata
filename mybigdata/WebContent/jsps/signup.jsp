<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign up</title>
</head>
<body>
	<form action='<c:url value="/user/signup"/>' method="post">
		<table>
			<tr>
				<td>Username:</td><td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Age:</td><td><input type="text" name="age"></td>
			</tr>
			<tr>
				<td>Password:</td><td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="Sign Up">
	</form>
</body>
</html>