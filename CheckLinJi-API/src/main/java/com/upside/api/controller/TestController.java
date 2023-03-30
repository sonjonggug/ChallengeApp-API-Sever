package com.upside.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/test")
public class TestController {

	
	
	 // 카카오 로그인 페이지 테스트
    @GetMapping
    public String socialKakaoLogin(ModelAndView mav) {

        return "Login2";
    }
}
