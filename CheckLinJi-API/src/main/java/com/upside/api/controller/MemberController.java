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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.config.JwtTokenProvider;
import com.upside.api.dto.MemberDto;
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
						
	@PostMapping("/sign")
	public ResponseEntity<Void> signUp(@RequestBody MemberDto memberDto) {
			
		
		return new ResponseEntity<>(memberService.signUp(memberDto));
	}
	
	@PatchMapping
	public ResponseEntity<Void> updateMember(@RequestBody MemberDto memberDto) {
		
		
		return new ResponseEntity<>(memberService.updateMember(memberDto));		
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteMember(@RequestParam String userId) {
		
		
		return new ResponseEntity<>(memberService.deleteMember(userId));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<MemberDto> loginMember(@RequestBody MemberDto memberDto) {
			System.out.println("도착");
		 HttpHeaders headers = new HttpHeaders();
		 		  		 
		 Map<String, String> result = memberService.loginMember(memberDto);
		 
		 MemberDto member = new MemberDto();
		 
		 if(result.get("HttpStatus").equals("200")) {			 		 
			 headers.add("Authorization", result.get("header"));
			  member.setUserId(result.get("userId"));
			  System.out.println(result.get("userId"));
			  System.out.println(result.get("header"));
			 return new ResponseEntity<>(member,headers, HttpStatus.OK);
		 } else {			 
			 System.out.println("22");
			 return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY); 
		 }
	}
	
	 // JWT 토큰 검증 요청 처리
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Authorization Header에서 JWT 토큰 추출
    	
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