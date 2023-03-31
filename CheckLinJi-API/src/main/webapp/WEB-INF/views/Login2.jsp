<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello, World!</title>    
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<p id="token-result"></p>

    <h1>Hello, World!</h1>
    <form method="post" action="/api/excel/memberList">              
    <input type="text" name="email" id="email" value="thswhdrnr12@naver.com" />
<!-- 	<button type="submit"> 버튼 </button>             -->
	<button type="button" onclick="memberList();"> 버튼 </button> 
</form>


<script>
function memberList(){	
	$.ajax({		 		
	 	type : 'POST', // 메소드 타입
		url : '/api/excel/memberList', // url 
		xhrFields: {
		        responseType: 'blob' // 반환된 데이터를 Blob으로 받아오기 위해 설정
		    },
		contentType : 'application/json; charset=utf-8', // 서버에 데이터를 보낼 때 사용 content - type 헤더의 값
		data :  JSON.stringify({
			email : 'thswhdrnr12@naver.com' 			
		}),
	 	   success : function(data) {
	 		  var a = document.createElement('a');
	 	        var url = window.URL.createObjectURL(data);
	 	        a.href = url;
	 	        a.download = 'memberListExcel.xlsx';
	 	        document.body.append(a);
	 	        a.click();
	 	        a.remove();
	 	        window.URL.revokeObjectURL(url);
	 	    },
	 	   error : function(error) {
	 	       alert(error);    	 	        
	 	    }
	 		});
}

</script>
</body>
</html>