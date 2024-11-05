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
	<h2>Json 연습하는 장소</h2>
	<button id="btn1"> json파일에서  가져오기</button>
	<button id="btn2">web  json 가져오기</button>
	<button id="btn3">공공데이터 포털 json 버튼</button>

	<hr>
	<div id="result"></div>
	
	<script type="text/javascript">
	$("#btn1").click(function() {
		$("#result").empty();
		$.ajax({
			url: "/test05", 		
			method: "post",
			dataType: "json",	
			
			success: function(data) {
				 console.log(data);
				let table = "<table>";
				table += "<thead>";
				table += "<tr><th>이름</th><th>스코프</th></tr>";
				table += "</thead>";
				table += "<tbody>";
				$.each(data, function(index, obj) {
					let name = obj.name;
					let scope = obj.scope;
				
				table += "<tr>"
				table += "<td>" +  name +  "</td>"; 
				table += "<td>" +  scope +  "</td>"; 
				table += "</tr>"					
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
	$("#btn2").click(function() {
		$("#result").empty();
		$.ajax({
			url: "/test06", 		
			method: "post",
			dataType: "json",	
			
			success: function(data) {
				console.log(data);
				let table = "<table>";
				table += "<thead>";
				table += "<tr><th>제품번호</th><th>제품이름</th><th>브랜드</th><th>가격(달러)</th><th>별점</th><th>제품 타입</th><th>이미지</th></tr>";
				table += "</thead>";
				table += "<tbody>";
				$.each(data, function(index, obj) {
					// 최대 5개만 표시
					if (index >= 5) {
						return false;
					}
					let id = obj.id;
					let name = obj.name;
					let brand = obj.brand;
					let price = obj.price;
					let rating = obj.rating;
					let product_type = obj.product_type;
					let img = obj.image_link; // 주소값
				
				table += "<tr>"
				table += "<td>" +  id +  "</td>"; 
				table += "<td>" +  name +  "</td>"; 
				table += "<td>" +  brand +  "</td>"; 
				table += "<td>" +  price +  "</td>"; 
				table += "<td>" +  rating +  "</td>"; 
				table += "<td>" +  product_type +  "</td>"; 
				table += "<td><img src='" +  img +"' width='100'></td>"; 
				table += "</tr>"					
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
			url: "/test07", 		
			method: "post",
			dataType: "json",	
			
			success: function(data) {
				console.log(data);
				let items = data.response.body.items.item;
				console.log(items);
				let table = "<table>";
				table += "<thead><tr><th>날짜</th><th>최소온도</th><th>최대온도</th></tr></thead>";
				table += "<tbody>";
				// 오늘 구하기
				let today = new Date();
				
				$.each(items, function(index, obj) {
					for (var i = 3; i < 11; i++) {
						let f_date = new Date(today);
						f_date.setDate(today.getDate() + i);// 3 일후  날짜부터...
						
						// 날짜형식을 YYYY - MM - DD로 변환 후 T로 구분하고 배열의 첫번째 가져옴
						let r_date = f_date.toISOString().split('T')[0];
					table += "<tr>";
					table += "<td>" + r_date + "</td>";
					table += "<td>" + obj["taMin" + i ] + "</td>";
					table += "<td>" + obj["taMax" + i ] + "</td>";
					table += "</tr>";
					}
					});
					
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