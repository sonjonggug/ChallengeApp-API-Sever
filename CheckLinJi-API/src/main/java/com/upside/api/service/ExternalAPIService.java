package com.upside.api.service;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import lombok.extern.slf4j.Slf4j;





@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class ExternalAPIService {

	@Value("${chatGpt.api.key}")
	private String chatGptKey;
	 /**
	  * 명언 API 
	  * @return
	  */
	 public String getWiseSayingAPI () {
		 	
		 	log.info("명언 API ------> Start");
		 
	     
	        String reqURL = "https://api.qwer.pw/request/helpful_text?apikey=guest";
	        String answer = "";
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            
	            conn.setRequestMethod("GET");
//	            conn.setDoOutput(true); // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

	            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//	            StringBuilder sb = new StringBuilder();
//	            sb.append("apikey=guest");	            
//	            bw.write(sb.toString());
//	            bw.flush();

	            //결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            
	            log.info("명언 API responseCode ------>"+ responseCode);	            

	            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            
	            log.info("명언 API responseBody ------>"+ result);	            

	            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonReader reader = new JsonReader(new StringReader(result));
	            JsonElement element = JsonParser.parseReader(reader).getAsJsonArray().get(1);
	            	            	            
	             answer = element.getAsJsonObject().get("respond").getAsString();
	            
	            log.info("명언 API answer ------> "+ answer);
	            
	            br.close();
//	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        log.info("명언 API ------> End");
	        
	        return answer ;
	    }	
	 
	 /**
	  * ChatGpt API
	  * @param request
	  * @return
	  */
	 @SuppressWarnings("unchecked")
		public String chatGptAPI(String request) {
	    	        
		 	  
	          Map<String, String> requestHeaders = new HashMap<>();  // 헤더 값 	     
	          
	          requestHeaders.put("Authorization", "Bearer "+ chatGptKey); // API 키 값
	          
	          requestHeaders.put("Content-Type", "application/json");
	          
	          String apiUrl = "https://api.openai.com/v1/completions";
	          
	          JSONObject jsonData = new JSONObject(); // JSON 형식으로 전달
	          jsonData.put("model", "text-davinci-003"); // 어떤 GPT 모델을 사용할지 지정
	          jsonData.put("prompt", request); // 질문할 문장 
	          jsonData.put("max_tokens", 150); // 응답의 컨텍스트 길이
	          jsonData.put("temperature", 0); // 얼마나 창의적인 답을 작성하도록 할지 지정하는 값. 클수록 창의적
	          	              
	          String responseBody = post(apiUrl, requestHeaders, jsonData);
	                   
	          return responseBody;  
	      }

	      private static String post(String apiUrl, Map<String, String> requestHeaders, JSONObject jsonData) {
	          HttpURLConnection con = connect(apiUrl);

	          try {
	              con.setRequestMethod("POST"); // POST 형식으로
	              for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                  con.setRequestProperty(header.getKey(), header.getValue()); // Request 속성 값 설정하기 원래는 con.setRequestProperty("Content-type", "application/json"); 으로 설정하는데 for문으로 간단하게 처리
	              }
	                    
	              con.setDoOutput(true); // 받아온 Json 데이터를 출력 가능한 상태(True)
	              try (OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8")) {            	 
	            	  wr.write(jsonData.toString());  // JSON형식을 String으로 다시 변환
	                  wr.flush();
	              }

	              int responseCode = con.getResponseCode();
	              if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
	                  return readBody(con.getInputStream());
	              } else {  // 에러 응답
	                  return readBody(con.getErrorStream());
	              }
	          } catch (IOException e) {
	              throw new RuntimeException("API 요청과 응답 실패", e);
	          } finally {
	              con.disconnect(); // Connection을 재활용할 필요가 없는 프로세스일 경우
	          }
	      }

	      private static HttpURLConnection connect(String apiUrl) {
	          try {
	              URL url = new URL(apiUrl);
	              return (HttpURLConnection) url.openConnection();
	          } catch (MalformedURLException e) {
	              throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	          } catch (IOException e) {
	              throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	          }
	      }

	      private static String readBody(InputStream body) {
	          InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

	          try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	              StringBuilder responseBody = new StringBuilder();

	              String line;
	              while ((line = lineReader.readLine()) != null) {
	                  responseBody.append(line);
	              }
	              System.out.println(responseBody.toString());
	              return responseBody.toString();
	          } catch (IOException e) {
	              throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
	          }
	      }
	}
