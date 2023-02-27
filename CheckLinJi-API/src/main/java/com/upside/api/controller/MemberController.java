package com.upside.api.controller;



import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.config.JwtTokenProvider;
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
	
	
	private final JwtTokenProvider jwtTokenProvider ;
	
	
	
	@GetMapping 						  /* default size = 10 */
	public Page<MemberEntity> memberList(@PageableDefault (sort = "userId", direction = Sort.Direction.DESC) Pageable pageable  ) {
				
				
		return memberService.memberList(pageable);
	}
	
	@PostMapping("/validateDuplicated")
	public ResponseEntity<MessageDto> validateDuplicated (@RequestBody MemberDto memberDto) {
			
		Map<String, String> result = memberService.signUp(memberDto);
		MessageDto message = new MessageDto();
		
		if (result.get("HttpStatus").equals("2.00")) { // 성공
			message.setMsg(result.get("Msg"));
			message.setStatusCode(result.get("HttpStatus"));						
			return new ResponseEntity<>(message,HttpStatus.OK);					
		} else {			
			message.setMsg(result.get("Msg"));
			message.setStatusCode(result.get("HttpStatus"));
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		} 
					
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
	
	@PatchMapping
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
	
	@DeleteMapping
	public ResponseEntity<MessageDto> deleteMember(@RequestBody MemberDto memberDto) {
		
		Map<String, String> result = memberService.deleteMember(memberDto.getUserId());
		 
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
		
		 HttpHeaders headers = new HttpHeaders();
		 		  		 
		 Map<String, String> result = memberService.loginMember(memberDto);
		 
		 MessageDto message = new MessageDto();
		 
		 if(result.get("HttpStatus").equals("2.00")) {			 		 
			 headers.add("Authorization", result.get("Header"));
			 message.setUserId(result.get("UserId"));
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message,headers, HttpStatus.OK);
		 } else {			 
			 message.setMsg(result.get("Msg"));
			 message.setStatusCode(result.get("HttpStatus"));
			 return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST); 
		 }
	}
	
	 // JWT 토큰 검증 요청 처리
    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Authorization Header에서 JWT 토큰 추출
    	System.out.println("도착");
        String token = authorizationHeader.substring(7);
        System.out.println(token);
        // JWT 토큰 검증
        if (jwtTokenProvider.validateTokenExceptExpiration(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired");
        }
        }
    }
