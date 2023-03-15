<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello, World!</title>    
    <script>

</script>
</head>
<body>
<p id="token-result"></p>

    <h1>Hello, World!</h1>
    <form method="post" action="/api/challenge/submit" enctype="multipart/form-data">
    <label for="file">Choose file:</label>
    <input type="file" name="file" id="file"/>
    <br/>
    <input type="hidden" name="challengeName" id="challengeName" value="테스트용1" />
    <input type="hidden" name="email" id="email" value="thswhdrnr12@naver.com" />
<!--     <input type="hidden" name="userFeeling" id="userFeeling" value="테스트입니다." /> -->
	<input type="hidden" name="submissionText" id="submissionText" value="테스트입니다." />

    <input type="submit" value="Upload"/>
    
</form>
</body>
</html>