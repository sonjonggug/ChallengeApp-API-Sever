package com.upside.api.service;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import lombok.extern.slf4j.Slf4j;





@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class WiseSayingAPISerivce {

	
	 
	 
	 public String getWiseSaying () {
		 	
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
	}
