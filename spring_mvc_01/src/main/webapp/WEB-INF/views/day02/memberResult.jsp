<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Member 전체 내용보기</h1>
	<c:choose>
		<c:when test="${empty list }">
			<h2>내용이 존재하지 않습니다</h2>
		</c:when>
		<c:otherwise>
			<c:forEach var="k" items="${list }">
				<li>${k.u_idx }</li>
				<li>${k.u_id }</li>
				<li>${k.u_pw }</li>
				<li>${k.u_name }</li>
				<li>${k.u_age }</li>
				<li>${k.u_reg }</li>
				<li>${k.u_reg }</li>
				<hr>
			</c:forEach>		
		</c:otherwise>
	</c:choose>

</body>
</html>