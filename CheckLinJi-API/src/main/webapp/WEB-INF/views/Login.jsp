<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello, World!</title>
    <script type='text/javascript'>
  Kakao.init('d0cd4908c80b4b8bd32622ea61d704e0');
    $("#kakao-login-btn").on("click", function(){
    	alert("Hi");
    	 //1. 로그인 시도
        Kakao.Auth.login({
            success: function(authObj) {         
              //2. 로그인 성공시, API 호출
              Kakao.API.request({
                url: '/v2/user/me',
                success: function(res) {
                  console.log(res);
                  var id = res.id;
    			  scope : 'account_email';
    			alert('로그인성공');
                  location.href="callback주소";

            }
              })
              console.log(authObj);
              var token = authObj.access_token;
            },
            fail: function(err) {
              alert(JSON.stringify(err));
            }
          });  
            
    }); //
    
    
        function handleClick() {
            alert("Button clicked!");
        }
    </script>
</head>
<body>
    <h1>Hello, World!</h1>
    <button onclick="handleClick()">Click me</button>
    
    <button id="kakao-login-btn">Click kakao</button>
</body>
</html>