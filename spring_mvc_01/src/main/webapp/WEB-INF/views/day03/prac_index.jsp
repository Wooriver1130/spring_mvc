<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2> 이미지 보기</h2>
	<img alt="" src="resources/images/img01.jpg" style="width: 100px;"><br>
	<img alt="" src="/resources/images/img01.jpg" style="width: 100px;"><br>
	<img alt="" src="<c:url value='/resources/images/img01.jpgm' />" style="width: 100px;"><br>
	<img alt="" src="<c:url value='/resources/images/img01.jpgm' />" style="width: 100px;"><br>
	
	<h2>파일 업로드 - 1</h2>
	<form action="/file_up1" method="post" enctype="multipart/form-data">
		<p>올린사람: <input type="text" name="name"> </p>
		<p>파일 선택: <input type="file" name="file"> </p>
		<input type="submit" name="파일 업로드">
	</form>
	<h2>파일 업로드 - 2</h2>
	<form action="/file_up2" method="post" enctype="multipart/form-data">
		<p>올린사람: <input type="text" name="name"> </p>
		<p>파일 선택: <input type="file" name="file"> </p>
		<input type="submit" name="파일 업로드">
	</form>
	

</body>
</html>