package com.upside.api.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.MemberDto;
import com.upside.api.dto.MessageDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	
	private final MemberService memberService ;
	
	

	
	
	
	@GetMapping 						  /* default size = 10 */
	public Page<MemberEntity> memberList(@PageableDefault (sort = "email", direction = Sort.Direction.DESC) Pageable pageable  ) {
				
				
		return memberService.memberList(pageable);
	}
	
	@PostMapping("/email")
	public ResponseEntity<Map<String , Object>> selectMember(@RequestBody MemberDto memberDto) {				
		Map<String , Object> result = new HashMap<String, Object>();
		
			result = memberService.selectMember(memberDto.getEmail());
		
		if (result.get("HttpStatus").equals("2.00")) { // 성공
			
			return new ResponseEntity<>(result,HttpStatus.OK);			
		} else {			

			return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
		} 			
	}
	
	@PostMapping("/validateDuplicated")
	public ResponseEntity<MessageDto> validateDuplicated (@RequestBody MemberDto memberDto) {
		MessageDto message = new MessageDto();
		Map<String , String> result = new HashMap<String, String>();
		
		if(memberDto.getEmail() != null) {
			 result = memberService.validateDuplicatedEmail(memberDto.getEmail());
			 message.setStatusCode(result.get("HttpStatus"));
		 	 message.setMsg(result.get("Msg"));
			return new ResponseEntity<>(message,HttpStatus.OK); 
		}
		
		if(memberDto.getNickName() != null) {
			 result = memberService.validateDuplicatedNickName(memberDto.getNickName());
			 message.setStatusCode(result.get("HttpStatus"));
		 	 message.setMsg(result.get("Msg"));
			return new ResponseEntity<>(message,HttpStatus.OK); 
		}
				
		return new ResponseEntity<>(message,HttpStatus.OK);		
					
	}
	
	
	@PostMapping("/sign")
	public ResponseEntity<MessageDto> signUp(@RequestBody MemberDto memberDto) {
			
		Map<String, String> result = memberService.signUp(memberDto);
		MessageDto message = new MessageDto();
		
		if (result.get("HttpStatus").equals("2.00")) { // 성공
			message.setStatusCode(result.get("HttpStatus"));
			message.setMsg(result.get("Msg"));						
			return new ResponseEntity<>(message,HttpStatus.OK);			
		} else {			
			message.setMsg(result.get("Msg"));
			message.setStatusCode(result.get("HttpStatus"));
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		} 					
	}
	
	@PostMapping("/update")
	public ResponseEntity<MessageDto> updateMember(@RequestBody MemberDto memberDto) {
		
		 Map<String, String> result = memberService.updateMember(memberDto);
		 
		 MessageDto message = new MessageDto();
		 
		 if(result.get("HttpStatus").equals("2.00")) {
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.OK);
		 } else {
			 message.setMsg(result.get("Msg")); 
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		 }		
	}
	
	@PostMapping("/delete")
	public ResponseEntity<MessageDto> deleteMember(@RequestBody MemberDto memberDto) {
		
		Map<String, String> result = memberService.deleteMember(memberDto.getEmail());
		 
		 MessageDto message = new MessageDto();
		
		 if(result.get("HttpStatus").equals("2.00")) {
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.OK);
		 } else {
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		 }				
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<MessageDto> loginMember(@RequestBody MemberDto memberDto) {			
		
//		 HttpHeaders headers = new HttpHeaders();
		 		  		 
		 Map<String, String> result = memberService.loginMember(memberDto);
		 
		 MessageDto message = new MessageDto();
		 
		 if(result.get("HttpStatus").equals("2.00")) {			 		 
//			 headers.add("Authorization", result.get("Header"));
			 message.setUserEmail(result.get("UserEmail"));
			 message.setMsg(result.get("Msg"));
			 message.setToKen(result.get("Token"));
			 message.setRefreshToken(result.get("RefreshToken"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.OK);
		 } else {			 
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST); 
		 }
	}
	
	 // JWT 토큰 검증 요청 처리
	@PostMapping("/refreshToken")
	public ResponseEntity<MessageDto> validateRefreshToken (@RequestBody MemberDto memberDto) {							 

		 Map<String, String> result = memberService.validateRefreshToken(memberDto);
		 
		 MessageDto message = new MessageDto();
		 
		 if(result.get("HttpStatus").equals("2.00")) {			 		 			 
			 message.setUserEmail(result.get("UserEmail"));
			 message.setMsg(result.get("Msg"));
			 message.setToKen(result.get("Token"));
			 message.setRefreshToken(result.get("RefreshToken"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message, HttpStatus.OK);
		 } else {			 
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST); 
		 }
	}
	
  }
