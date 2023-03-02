package com.upside.api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.upside.api.dto.MessageDto;
import com.upside.api.service.KaKaoOAuthSerivce;
import com.upside.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social/login")
public class SocialController {
    
    private final KaKaoOAuthSerivce oAuthSerivce;
    private final MemberService memberService;    

    // 카카오 로그인 페이지 테스트
    @GetMapping
    public String socialKakaoLogin(ModelAndView mav) {

        return "Login";
    }
    @ResponseBody
    // 인증 완료 후 리다이렉트 페이지
    @GetMapping("/kakao")
   	public ResponseEntity<MessageDto> redirectKakao (@RequestParam String code)  {							 
    	 
    	 MessageDto message = new MessageDto();
    	 
   		 String getKakaoAccessToken = oAuthSerivce.getKakaoAccessToken(code);
   		 
   		Map<String, String> getKakaoUserInfo = oAuthSerivce.getKakaoUserInfo(getKakaoAccessToken);
   		 
   		 if(getKakaoUserInfo.get("Email").equals("N") ) {
   			message.setStatusCode("1.00");
   			message.setMsg("이메일 정보 수집동의에 체크 해주시기 바랍니다");
   			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
   		 }
   		 
   		
   		Map<String, String> result = memberService.validateEmail(getKakaoUserInfo.get("Email") , getKakaoUserInfo.get("NickName"));
   		
   		if(result.get("HttpStatus").equals("2.01")) { // 신규 회원일 경우
   			message.setStatusCode("2.01");
   			message.setUserEmail(result.get("UserEmail"));
   			message.setUserNickName("NickName");
   			return new ResponseEntity<>(message,HttpStatus.OK);
   		} else if (result.get("HttpStatus").equals("2.02")){ // 이메일은 있으나 닉네임이 다를경우     			
   			message.setStatusCode("2.02");
   			message.setMsg(result.get("Msg"));   			   			
   			return new ResponseEntity<>(message,HttpStatus.OK);
   		}else { // 로그인    			
   			message.setStatusCode("2.00");
   			message.setUserEmail(result.get("UserId"));
   			message.setToKen(result.get("Token"));
   			message.setRefreshToken(result.get("RefreshToken"));
   			return new ResponseEntity<>(message,HttpStatus.OK);
   		}
   		    		    		   		 
   		 
   	}
}
