<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
	table{width: 800px; border-collapse: collapse;}
	table, th, td{border: 1px solid red;  text-align: center;}
</style>
</head>
<body>
<h2>Ajax 연습하는 장소</h2>
	<button id="btn1">text 버튼</button>
	<button id="btn2">xml 버튼</button>
	<button id="btn3">날씨 xml 버튼</button>
	<button id="btn4">공공데이터 포털 xml 버튼</button>
	<hr>
	<div id="result"></div>
	<script type="text/javascript">
		$("#btn1").click(function() {
			$("#result").empty();
			$.ajax({
				url: "/test01", 		// 서버주소
				method: "post", // 전달방식
				dataType: "text",	// 가져요는 결과의 데이터 타입
				//data: "",	    // 서버에 갈 떄 가져갈 파라미터값 
				// async: true(비동기), 또는 false(동기) => 비동기가 기본값이며 생략된다. 필요할떄 쓰기
				success: function(data) {
					$("#result").append(data);
				}, 
				error: function() {
					alert("읽기실패");
				}
			});
		});
		
		$("#btn2").click(function() {
			$("#result").empty();
			$.ajax({
				url: "/test02", 
				method: "post",
				dataType: "xml",
				success: function(data) {
					let table = "<table>";
					table += "<thead><tr><th>회사</th><th>차이름</th><th>몇대?</th></tr></thead>";
					table += "<tbody>";
					$(data).find("product").each(function() {
						let company = $(this).text();
						let name = $(this).attr("name");
						let count = $(this).attr("count");
						
						table += "<tr>";
						table += "<td>" + company + "</td>";
						table += "<td>" + name + "</td>";
						table += "<td>" + count + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				}, 
				error: function() {
					alert("읽기실패");
				}
			});
		});
		
		$("#btn3").click(function() {
			$("#result").empty();
			$.ajax({
				url: "/test03", 	
				method: "post", 
				dataType: "xml",
				
				success: function(data) {
					let table = "<table>";
					table += "<thead><tr><th>지역명</th><th>지역번호</th><th>아이콘</th><th>날씨상태</th><th>온도</th><th>강수량</th></tr></thead>"
					table += "<tbody>";
					$(data).find("local").each(function() {
						let localName = $(this).text();
						let stn_id = $(this).attr("stn_id");
						let icon = $(this).attr("icon");
						let weather = $(this).attr("desc");
						let temper = $(this).attr("ta");
						let rain = $(this).attr("rn_hr1");
						table += "<tr>";
						table += "<td>" + localName  + "</td>";
						table += "<td>" + stn_id  + "</td>";
						 table += "<td><img src='https://www.kma.go.kr/images/icon/NW/NB" + icon + ".png'></td>";
						table += "<td>" + weather  + "</td>";
						table += "<td>" + temper  + "</td>";
						table += "<td>" + rain  + "</td>";
						table += "</tr>";
					});
					
					table += "</tbody>";
					table += "</table>";
					$("#result").append(table);
				}, 
				error: function() {
					alert("읽기실패");
				}
			});
		});
		
		$("#btn4").click(function() {
			$("#result").empty();
			$.ajax({
				url: "/test04", 		
				method: "post",
				dataType: "xml",	
				success: function(data) {
					let table = "<table>"
					table += "<thead><tr><th>지역</th><th>초미세먼지</th><th>미세먼지</th><th>오존</th><th>이산화질소</th><th>일산화탄소</th></tr><thead>"
					table += "<tbody>"
					$(data).find("item").each(function() {
						table += "<tr>";
						table += "<td>" + $(this).find("sidoName").text() + "</td>";
						table += "<td>" + $(this).find("pm25Value").text() + "</td>";
						table += "<td>" + $(this).find("pm10Value").text() + "</td>";
						table += "<td>" + $(this).find("o3Value").text() + "</td>";
						table += "<td>" + $(this).find("no2Value").text() + "</td>";
						table += "<td>" + $(this).find("coValue").text() + "</td>";
						table += "</tr>";
					})
					table += "</tbody>"
					table += "</table>"
					$("#result").append(table);
					
				}, 
				error: function() {
					alert("읽기실패");
				}
			});
		});
	</script>

</body>
</html>