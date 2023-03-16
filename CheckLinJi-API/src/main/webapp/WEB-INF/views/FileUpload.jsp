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
<img src="data:image/png;base64,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" alt="My Image">
</body>
</html>