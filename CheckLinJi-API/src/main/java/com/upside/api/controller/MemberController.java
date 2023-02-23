package com.upside.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.MemberDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

	@Autowired
	MemberService memberService ;
	
	@GetMapping
	public List<MemberEntity> memberList(@ModelAttribute MemberDto memberDto) {
				
		List<MemberEntity> result = memberService.memberList(memberDto);
		
		return result;
	}
						
	@PostMapping
	public int signUp(@ModelAttribute MemberDto memberDto) {
		
		int result = memberService.signUp(memberDto);
		
		return result;
	}
	
	@PatchMapping
	public int updateMember(@ModelAttribute MemberDto memberDto) {
		
		int result = memberService.signUp(memberDto);
		
		return result;
	}
}
