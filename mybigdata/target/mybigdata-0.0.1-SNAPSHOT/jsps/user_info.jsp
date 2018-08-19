<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Information</title>
</head>
<body>
	<table>
		<tr>
			<td>ID</td><td>NAME</td><td>AGE</td>
		</tr>
		<tr>
			<td><c:out value='${user.id}'/></td><td><c:out value="${user.name}"/></td><td><c:out value="${user.age}"/></td>
		</tr>
	</table>
</body>
</html>