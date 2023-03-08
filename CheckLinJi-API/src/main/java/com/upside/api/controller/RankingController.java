package com.upside.api.controller;



import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upside.api.dto.MemberDto;
import com.upside.api.dto.RankingMessageDto;
import com.upside.api.service.RankingSerivce;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class RankingController {
	
	private final RankingSerivce rankingSerivce ;
				
	
	
	/**
	  * 미션 성공 총 횟수 (월)
	  * @param memberDto
	  * @return
	  */	
	@PostMapping("/completed") // 첼린지 생성
	public ResponseEntity<RankingMessageDto> missionCompletedCnt (@RequestBody MemberDto memberDto) {
		
		RankingMessageDto message = new RankingMessageDto();		
		Map<String, String> result = rankingSerivce.missionCompletedCnt(memberDto);
				
		if (result.get("HttpStatus").equals("2.00")) { // 성공
			message.setMsg(result.get("Msg"));
			message.setStatusCode(result.get("HttpStatus"));
			message.setOwn(String.valueOf(result.get("own")));
			message.setUser(String.valueOf(result.get("userAvg")));
			return new ResponseEntity<>(message,HttpStatus.OK);					
		} else {			
			message.setMsg(result.get("Msg"));
			message.setStatusCode(result.get("HttpStatus"));
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		} 
					
	}
	
	/**
	  * 실시간 랭킹
	  * @param memberDto
	  * @return
	  */
	@PostMapping("/ranking") // 첼린지 생성
	public ResponseEntity<RankingMessageDto> missionRanking (@RequestBody MemberDto memberDto) {
		
		RankingMessageDto message = new RankingMessageDto();		
		Map<String, Object > result = rankingSerivce.missionRanking(memberDto);				
				
		if (result.get("HttpStatus").equals("2.00")) { // 성공
			message.setMsg((String) result.get("Msg"));
			message.setStatusCode((String) result.get("HttpStatus"));
			message.setUserList(result.get("missionRankingTop"));
			message.setOwnList(result.get("missionRankingOwn"));		
			
			return new ResponseEntity<>(message,HttpStatus.OK);					
		} else if (result.get("HttpStatus").equals("1.01")) { // 본인이 참여중이 아닐때			
			message.setMsg((String) result.get("Msg"));
			message.setStatusCode((String) result.get("HttpStatus"));
			message.setUserList(result.get("missionRankingTop"));
			return new ResponseEntity<>(message,HttpStatus.OK);
		} else {			
			message.setMsg((String) result.get("Msg"));
			message.setStatusCode((String) result.get("HttpStatus"));
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		} 
					
	}
	
}
