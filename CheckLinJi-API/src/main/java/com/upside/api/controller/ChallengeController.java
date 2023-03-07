package com.upside.api.controller;



import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.ChallengeDto;
import com.upside.api.dto.ChallengeSubmissionDto;
import com.upside.api.dto.MemberDto;
import com.upside.api.dto.MessageDto;
import com.upside.api.dto.UserChallengeDto;
import com.upside.api.service.ChallengeSerivce;

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
	public ResponseEntity<MessageDto> joinChallenge (@RequestBody UserChallengeDto userChallengeDto) {
			
		MessageDto message = new MessageDto();		
		Map<String, String> result = challengeSerivce.joinChallenge(userChallengeDto.getChallengeName() , userChallengeDto.getEmail());
				
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
	public ResponseEntity<MessageDto> submitChallenge (@RequestBody  ChallengeSubmissionDto submissonDto) {
			
		MessageDto message = new MessageDto();		
		Map<String, String> result = challengeSerivce.submitChallenge(submissonDto);
				
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
