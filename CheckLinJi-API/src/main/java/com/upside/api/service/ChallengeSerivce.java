package com.upside.api.service;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.dto.ChallengeDto;
import com.upside.api.dto.MemberDto;
import com.upside.api.entity.ChallengeEntity;
import com.upside.api.entity.MemberEntity;
import com.upside.api.entity.UserChallengeEntity;
import com.upside.api.repository.ChallengeRepository;
import com.upside.api.repository.UserChallengeRepository;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class ChallengeSerivce {
		
	private final ChallengeRepository challengeRepository;
	 private final UserChallengeRepository userChallengeRepository;
	
	
	/**
	 * 첼린지 생성
	 * @param challengeDTO
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> createChallenge (ChallengeDto challengeDTO) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("첼린지 생성 ------> " + "Start");
		
		ChallengeEntity challenge =  ChallengeEntity.builder()
									 .challengeName(challengeDTO.getChallengeName())
									 .description(challengeDTO.getDescription())
									 .startTime(challengeDTO.getStartTime())
									 .endTime(challengeDTO.getEndTime())
									 .build();
				
        challengeRepository.save(challenge);
        
        boolean  exsistChallenge = challengeRepository.findByChallengeName(challengeDTO.getChallengeName()).isPresent();
        
        if(exsistChallenge == true) {
         log.info("첼린지 생성 ------> " + Constants.SUCCESS);
         result.put("HttpStatus","2.00");		
		 result.put("Msg",Constants.SUCCESS);
        } else {
    	 log.info("첼린지 생성 ------> " + Constants.FAIL);
         result.put("HttpStatus","1.00");		
   		 result.put("Msg",Constants.FAIL);
        }
	
	    return result ;			    		   
	}
	
	/**
	 * 첼린지 가입
	 * @param memberDto
	 * @param challengeDto
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> joinChallenge (MemberDto memberDto , ChallengeDto challengeDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("첼린지 가입 ------> " + "Start");
		
		 ChallengeEntity challenge =  ChallengeEntity.builder().challengeName(challengeDto.getChallengeName()).build();
		 
		 MemberEntity member =  MemberEntity.builder().userId(memberDto.getUserId()).build();
		
		 UserChallengeEntity userChallenge =  UserChallengeEntity.builder()
				 							   .memberEntity(member)
				 							   .challengeEntity(challenge)
				 							   .registrationTime(LocalDateTime.now())
				 							   .completed(false)
				 							   .build();
		 userChallengeRepository.save(userChallenge);
		 
		 boolean  exsistUserChallenge = userChallengeRepository.findByMemberEntityAndChallengeEntity(member,challenge).isPresent();
		 
		 if(exsistUserChallenge == true) {
	         log.info("첼린지 가입 ------> " + Constants.SUCCESS);
	         result.put("HttpStatus","2.00");		
			 result.put("Msg",Constants.SUCCESS);
	        } else {
	    	 log.info("첼린지 가입 ------> " + Constants.FAIL);
	         result.put("HttpStatus","1.00");		
	   		 result.put("Msg",Constants.FAIL);
	        }
		
		    return result ;				 	    		   
}	
	}
