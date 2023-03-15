package com.upside.api.service;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import lombok.extern.slf4j.Slf4j;





@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class KaKaoOAuthService {

	
	 @Value("${kakao.client_id}") 
	    private String kakaoId; 
	 @Value("${kakao.redirect_uri}") 
	    private String redirectUri;
	 
	
	 public String getKakaoAccessToken (String code) {
		 	
		 	log.info("KaKao getKakaoAccessToken ------> Start");
		 
	        String access_Token = "";
//	        String refresh_Token = "";
	        String reqURL = "https://kauth.kakao.com/oauth/token";

	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id="+ kakaoId); // TODO REST_API_KEY 입력
	            sb.append("&redirect_uri="+ redirectUri); // TODO 인가코드 받은 redirect_uri 입력
	            sb.append("&code=" + code);
	            bw.write(sb.toString());
	            bw.flush();

	            //결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            log.info("KaKao responseCode ------> "+ responseCode);

	            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            
	            log.info("KaKao responseBody ------> "+ result);

	            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonReader reader = new JsonReader(new StringReader(result));
	            JsonElement element = JsonParser.parseReader(reader);
	            
//	            JsonParser parser = new JsonParser();	            
//	            JsonElement element = parser.parse(result);

	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

	            log.info("KaKao access_Token ------> "+ access_Token);
	            

	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        log.info("KaKao getKakaoAccessToken ------> End");
	        return access_Token;
	    }
	 
	 public Map<String, String> getKakaoUserInfo(String token)  {
		 
		 	log.info("KaKao getKakaoUserInfo ------> Start");
		 	Map<String, String> result = new HashMap<String, String>();
		 	
		 	String email = "";
			String nickName = "";
		 	
			String reqURL = "https://kapi.kakao.com/v2/user/me";						 
		    //access_token을 이용하여 사용자 정보 조회
		    try {
		       URL url = new URL(reqURL);
		       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		       conn.setRequestMethod("POST");
		       conn.setDoOutput(true);
		       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

		       //결과 코드가 200이라면 성공
		       int responseCode = conn.getResponseCode();
		       log.info("KaKao responseCode ------> "+ responseCode);

		       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
		       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		       
		       String line = "";
		       String userInfo = "";

		       while ((line = br.readLine()) != null) {
		    	   userInfo += line;
		       }
		       
		       log.info("KaKao responseBody ------> "+ userInfo);
		       	
		       JsonReader reader = new JsonReader(new StringReader(userInfo));
	           JsonElement element = JsonParser.parseReader(reader);	           

//	           int id = element.getAsJsonObject().get("id").getAsInt();
	           boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
	          
	           
	           if(hasEmail){
	               email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
	           } else {
	        	   email = "N" ;
	           }
	           
	           nickName = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
	           
	           log.info("KaKao userEmail ------>" + email);
	           log.info("KaKao nickName ------>" + nickName);
	           
	           result.put("Email", email);
	           result.put("NickName", nickName);
	           
		       br.close();
		       		       		       
		       } catch (IOException e) {
		            e.printStackTrace();
		       }
		    log.info("KaKao getKakaoUserInfo ------> End");
			return result;		    
		 }
	
	}
