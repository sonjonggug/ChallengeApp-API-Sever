package com.upside.api.controller;



import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.ChallengeDto;
import com.upside.api.dto.MemberDto;
import com.upside.api.dto.MessageDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.service.ChallengeSerivce;
import com.upside.api.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
	
	private final ChallengeSerivce challengeSerivce ;
				
	
	
		
	@PostMapping("/create") // 첼린지 생성
	public ResponseEntity<MessageDto>createChallenge (@RequestBody ChallengeDto challengeDto) {
		
		MessageDto message = new MessageDto();		
		Map<String, String> result = challengeSerivce.createChallenge(challengeDto);
				
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
	
	@PostMapping("/join") // 첼린지 참가
	public ResponseEntity<MessageDto> joinChallenge (@RequestBody ChallengeDto challengeDto) {
			
		MessageDto message = new MessageDto();		
		Map<String, String> result = challengeSerivce.createChallenge(challengeDto);
				
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
	
	@PostMapping("/submit") // 첼린지 제출
	public ResponseEntity<MessageDto> submitChallenge (@RequestBody ChallengeDto challengeDto) {
			
		MessageDto message = new MessageDto();		
		Map<String, String> result = challengeSerivce.createChallenge(challengeDto);
				
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
	
		@PostMapping("/completed") // 챌린지 완료 처리
		public ResponseEntity<MessageDto> completedChallenge (@RequestBody ChallengeDto challengeDto) {
				
			MessageDto message = new MessageDto();		
			Map<String, String> result = challengeSerivce.createChallenge(challengeDto);
					
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
}
