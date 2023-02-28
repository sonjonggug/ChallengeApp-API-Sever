package com.upside.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.upside.api.dto.MessageDto;
import com.upside.api.service.OAuthSerivce;

import lombok.RequiredArgsConstructor;

@Controller
//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social/login")
public class SocialController {

    private final Environment env;
    private final OAuthSerivce oAuthSerivce;

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirect;

//    @Value("${spring.social.google.client_id}")
//    private String googleClientId;
//
//    @Value("${spring.social.google.redirect}")
//    private String googleRedirect;
//
//    @Value("${spring.social.naver.client_id}")
//    private String naverClientId;
//
//    @Value("${spring.social.naver.redirect}")
//    private String naverRedirect;

    // 카카오 로그인 페이지 테스트
    @GetMapping
    public String socialKakaoLogin(ModelAndView mav) {
        StringBuilder loginUrl1 = new StringBuilder()
                .append(env.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(kakaoRedirect);

//        StringBuilder loginUrl2 = new StringBuilder()
//                .append(env.getProperty("spring.social.google.url.login"))
//                .append("?client_id=").append(googleClientId)
//                .append("&response_type=code")
//                .append("&scope=email%20profile")
//                .append("&redirect_uri=").append(googleRedirect);
//
//        StringBuilder loginUrl3 = new StringBuilder()
//                .append(env.getProperty("spring.social.naver.url.login"))
//                .append("?client_id=").append(naverClientId)
//                .append("&response_type=code")
//                .append("&state=project")
//                .append("&redirect_uri=").append(naverRedirect);

//        mav.addObject("loginUrl1", loginUrl1);
////        mav.addObject("loginUrl2", loginUrl2);
////        mav.addObject("loginUrl3", loginUrl3);
//        mav.setViewName("login");
        return "Login";
    }
    @ResponseBody
    // 인증 완료 후 리다이렉트 페이지
    @GetMapping("/kakao")
   	public ResponseEntity<MessageDto> redirectKakao (@RequestParam String code) {							 
    	System.out.println("ddd");
    	System.out.println(code);
   		 String result = oAuthSerivce.getAccessToken(code);
   		 
   		 System.out.println(result);
   		 
   		 MessageDto message = new MessageDto();
   		    		
   		 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST); 
   		 
   	}
}
