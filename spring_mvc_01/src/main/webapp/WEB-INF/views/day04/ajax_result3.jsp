  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax를 활용한 DB처리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">
	span{width: 150px; color: red;}
	input{border: 1px solid red;}
	table{width: 800px; margin: 0 auto;}
	table, th, td{border: 1px solid lightgray;  text-align: center;}
	h2 {text-align: center;}
</style>
</head>
<body>
	<h2>Ajax를 활용한 DB처리</h2>
	<form method="post" id="my_form">
		<fieldset>
			<legend>회원 정보 입력하기</legend>
			<table>
				<thead>
					<tr><th>아이디</th><th>패스워드</th><th>이름</th><th>나이</th></tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" size="14" name="u_id" id="u_id"><br><span>중복여부</span></td>
						
						<td><input type="password" size="10" name="u_pw" id="u_pw"></td>
						<td><input type="text" size="14" name="u_name" id="u_name"></td>
						<td><input type="number" size="14" name="u_age" id="u_age"></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" align="center"> <input type="button" value="가입하기" id="btn_join" disabled>  </td>
					</tr>
				</tfoot>
			</table>
		</fieldset>
	</form>
	<br><br><br><br><br>
	<div>
		<table id="list">
			<thead>
				<tr><th>번호</th><th>아이디</th><th>패스워드</th><th>이름</th><th>나이</th><th>가입일</th><th>삭제</th></tr>
			</thead>
			<tbody id="tbody"></tbody>
		</table>
	</div>
	
	<script type="text/javascript">
		// ajax를 이용해서 DB정보 가져오기 (첫 접속, 삭제, 삽입 할때마다 사용할 메소드)
		function getList() {
			$.ajax({
				url: "/ajax_db_list", 
				method: "post", 
				dataType: "xml", 
				success: function(data) {
					console.log(data);
					let tbody = "";
					$(data).find("member").each(function(index, value) {
						tbody += "<tr>";
						tbody += "<td> " + (index + 1) + "</td>";
						tbody += "<td> " + $(this).find("u_id").text() + "</td>";
						tbody += "<td> " + $(this).find("u_pw").text() + "</td>";
						tbody += "<td> " + $(this).find("u_name").text() + "</td>";
						tbody += "<td> " + $(this).find("u_age").text() + "</td>";
						tbody += "<td> " + $(this).find("u_reg").text() + "</td>";
						tbody += "<td><input type='button' value='삭제' id='member_del' name='" + $(this).find("u_idx").text() + "'></td>";
						tbody += "</tr>";
					});
					$("#tbody").append(tbody);
				}, 
				error: function() {
					alert("삐삑 실패하셨습니다")
				}
			});	
		};
		
		
		function getList2() {
			$.ajax({
				url: "/ajax_db_list2", 
				method: "post", 
				dataType: "json", 
				success: function(data) {
					console.log(data); 
					let tbody = "";
					$.each(data, function(index, obj) {
						tbody += "<tr>";
						tbody += "<td> " + obj.u_idx + "</td>";
						tbody += "<td> " + obj.u_id + "</td>";
						tbody += "<td> " + obj.u_pw + "</td>";
						tbody += "<td> " + obj.u_name + "</td>";
						tbody += "<td> " + obj.u_age + "</td>";
						tbody += "<td> " + obj.u_reg + "</td>";
						tbody += "<td>삭제</td>";
						tbody += "</tr>";
					});
					$("#tbody").append(tbody);
					
				}, 
				error: function() {
					alert("삐삑 실패하셨습니다");
				}
			});	
		};
		
		function getList3() {
			$.ajax({
				url: "/ajax_db_list3", 
				method: "post", 
				dataType: "text", 
				success: function(data) {
					/* console.log(data);  */
					let rows = data.split("\n");
					let tbody = "";
					$.each(rows, function(index, row) {
						// 헤더가 있으면 제외
						if(index === 0 || row.trim() === "") return true;
						
						let columns = row.split(",");
						tbody += "<tr>";
						
						$.each(columns, function(i, column) {
							tbody += "<td>" + column + "</td>";
						});
						tbody += "<td>삭제</td>";
						tbody += "</tr>";				
					});
					$("#tbody").append(tbody);
				}, 
				error: function() {
					alert("삐삑 실패하셨습니다")
				}
			});	
		};
		
		let idAvailChk = false;
		function toogleJoinButton() {
			const idIdfilled = $("#u_id").val() !== "";
			const isPwFilled = $("#u_pw").val() !== "";
			const isNameFilled = $("#u_name").val() !== "" ;
			const isAgeFilled = $("#u_age").val() !== "" && parseInt($("#u_age").val()) > 0 ;
			if (idAvailChk  && idIdfilled && isPwFilled && isNameFilled && isAgeFilled ) {
				$("#btn_join").prop("disabled", false) ; // 비활성화라고 생각하면 편함
			}else {
				$("#btn_join").prop("disabled", true) ;
			}
		}
		
		$("#u_id").keyup(function() {
			$.ajax({
				
					url: "/ajax_id_chk",
					data: "u_id=" + $("#u_id").val(), // 파라미터 한개일떄 사용 
					method: "post", 
					dataType: "text",
					success: function(data) {
						if (data == '1') {
							// 사용불가
							$("span").text("사용불가");
							idAvailChk = false ;
						}else if (data == '0') {
							// 사용가능
							$("span").text("사용가능");
							idAvailChk = true;
						}
						toogleJoinButton();
					}, 
					error: function() {
		
						alert("읽기실패");
					}
		});
		});
		
		
		$("#u_pw, #u_name").keyup(toogleJoinButton);
		$("#u_age").on('change keyup', toogleJoinButton);
		
		// 파라미터가 여러 개 일떄 => 보통 직렬화(serialize()) => form 태그 안에서만 가능
		$("#btn_join").click( function() {
			let param = $("#my_form").serialize();
			$.ajax({
				url: "/ajax_member_join", 
				method: "post",
				data: param, /*   방법1 */
				/* data: { // 방법 2
					 	 u_id: $("#u_id").val(),
						u_pw: $("#u_pw").val(),
						u_name: $("#u_name").val(),
						u_age: $("#u_age").val(), 
					  },   */
				dataType: "text", 
				success: function(data) {
					if (data == "0") {
						alert("가입 실패");
					}else  if(data == "1"){
						$("#tbody").empty();
						getList();
						
						// 가입창 초기화
	/*		방법1 : form => serialize는 배열로 넘어온다.	*/		
					$("#my_form")[0].reset(0); 
 						
						// 방법 2 						
/* 						$("#u_id").val("");
						$("#u_pw").val("");
						$("#u_name").val("");
						$("#u_age").val("");
						$("#btn_join").prop("disabled", true);  */
					}
				}, 
				error: function() {
					alert("실패하셨습니다.");
			    }
			});
		});
		
		
		// 동적바인딩 변수(click안됨, on 사용)
		$("#list").on("click", "#member_del", function() {
		/* 	// let u_id = $(this).attr("name");
			alert(u_id); */
			if (confirm("정말 삭제하시겠습니까?")) {
				
			$.ajax({
				url: "/ajax_member_delete", 
				method: "post",
				data: "u_idx=" + $(this).attr("name"), 
				dataType: "text", 
				success: function(data) {
					if (data == "0") {
						alert("삭제 실패");
					}else if (data == "1") {
						$("#tbody").empty();
						getList();
					}
				}, 
				error: function() {
					alert("비빅실패");
				}
			});
			}else {
				
			}
		});
		getList();
		
	</script>
</body>
</html>