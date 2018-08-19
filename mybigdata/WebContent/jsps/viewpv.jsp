<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View PV</title>
<link href="../css/forTable.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<table width="100%" id="table-1" border="1">
		<tr>
			<th>year</th> <th>month</th> <th>day</th> <th>page</th> <th>pv</th>
		</tr>
		<c:forEach items="${pvItems}" var="item">
			<tr>
				<td><c:out value="${item[4]}"/></td>
				<td><c:out value="${item[2]}"/></td>
				<td><c:out value="${item[1]}"/></td>
				<td><c:out value="${item[3]}"/></td>
				<td><c:out value="${item[0]}"/></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>