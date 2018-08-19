<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Users</title>
</head>
<body>
	<table>
		<tr>
			<td>ID</td><td>NAME</td><td>AGE</td><td>删除</td><td>查看</td>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><c:out value="${user.id}"/></td>
				<td><c:out value="${user.name}"/></td>
				<td><c:out value="${user.age}"/></td>
				<td><a href='<c:url value="/user/delete_one?id=${user.id}"/>'>删除</a></td>
				<td><a href='<c:url value="/user/user_info?id=${user.id}"/>'>查看</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>