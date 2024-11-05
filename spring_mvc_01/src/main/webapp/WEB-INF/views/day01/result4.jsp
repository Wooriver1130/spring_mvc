<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배열처리</title>
</head>
<body>
	<h2> 결과보기</h2>
	<c:forEach var="k" items="${carName }">
		<span>${k }</span>
	</c:forEach>

</body>
</html>