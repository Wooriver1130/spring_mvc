<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>

<script type="text/javascript">
<c:if test="${result == '1'}">
    alert("회원가입 성공");
</c:if>
<c:if test="${errorChk == '1'}">
    alert("회원가입에 실패하였습니다.");
</c:if>
<c:if test="${loginChk == '0'}">
    alert("회원 정보가 일치하지 않습니다.");
</c:if>

</script>

</head>
<body>


	<form method="post" action="/login_ok">
			<fieldset>
				<legend>로그인 하기</legend>
				<table>
					<thead>
						<tr><th>아이디</th><th>패스워드</th></tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" size="14" name="u_id" required><br></td>
							<td><input type="password" size="10" name="u_pw" required></td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2" align="center"> <input type="submit" value="로그인 하기">  </td>
						</tr>
					</tfoot>
				</table>
			</fieldset>
		</form>
		<h2><a href="/join_form">회원가입</a>  </h2>

</body>
</html>