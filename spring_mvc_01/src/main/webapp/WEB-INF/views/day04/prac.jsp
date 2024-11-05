<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	let isExistChk = false;
	function judgeJoinButton() {
		const isPwFilled = $("u_pw").val() !== "";
		const isNameFilled = $("u_name").val() !== "";
		const isAgeFilled = $("u_age").val() !== "" && parseInt($("u_age").val()) > 0 ;
		
		if (isPwFilled && isNameFilled && isAgeFilled && idAvailChk) {
			$("#btn_join").prop("disabled", false);
		}else {
			$("#btn_join").prop("disabled", true);
		}
	}
	
	$("u_id").keyup(function() {
		$.ajax({
			url: "ajax_id_chk", 
			method: "post", 
			data:"u_id=" + $("u_id").val(), 
			dataType: "text", 
			success: function(data) {
				
			}, 
			error: function() {
				
			}
		});
	});
	$("#u_pw, #u_name").on('keyup', judgeJoinButton);
	$("#u_age").on('change keyup', judgeJoinButton);
	
	
	$("#btn_join").on("click", function() {
		let params = $("my_form").serialize();
		$.ajax({
			url: "/ajax_member_join", 
			method: "post", 
			data: params, 
			dataType: "text", 
			success: function() {
				
			}, 
			error: function() {
				
			}
		});
	})
</script>
</body>
</html>