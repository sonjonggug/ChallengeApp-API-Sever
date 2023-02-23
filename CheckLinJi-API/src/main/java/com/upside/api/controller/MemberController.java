package com.upside.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.MemberDto;
import com.upside.api.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	@Autowired
	MemberService memberService ;
	
	@PostMapping
	public String signUp(@ModelAttribute MemberDto memberDto) {
		
		memberService.signUp(memberDto);
		
		return "d";
	}
	
}
