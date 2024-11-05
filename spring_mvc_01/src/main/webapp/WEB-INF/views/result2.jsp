<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>result2</title>
</head>
<body>
	<h2>결과: result2.jsp</h2>
	<h3>원피스 등장인물</h3>
	<c:choose>
	<c:when test="${empty onePiece}">
		<span>비어있습니다</span>
	</c:when>
	<c:otherwise>
		<c:forEach var="k" items="${onePiece}">
			<span>${k}</span>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	<h3>과일 종류</h3>
	<c:choose>
	<c:when test="${empty list}">
		<span>비어있습니다</span>
	</c:when>
	<c:otherwise>
		<c:forEach var="k" items="${list}">
			<span> ${k}</span>
		</c:forEach>
	</c:otherwise>
	</c:choose>
	
</body>
</html>