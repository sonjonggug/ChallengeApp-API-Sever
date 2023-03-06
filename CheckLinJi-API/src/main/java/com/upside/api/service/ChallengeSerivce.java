package com.upside.api.service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.dto.ChallengeDto;
import com.upside.api.dto.MemberDto;
import com.upside.api.entity.ChallengeEntity;
import com.upside.api.entity.ChallengeSubmissionEntity;
import com.upside.api.entity.MemberEntity;
import com.upside.api.entity.UserChallengeEntity;
import com.upside.api.repository.ChallengeRepository;
import com.upside.api.repository.ChallengeSubmissionRepository;
import com.upside.api.repository.MemberRepository;
import com.upside.api.repository.UserChallengeRepository;
import com.upside.api.util.Constants;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class ChallengeSerivce {
		
	 private final ChallengeRepository challengeRepository;
	 private final UserChallengeRepository userChallengeRepository;
	 private final MemberRepository memberRepository;
	 private final ChallengeSubmissionRepository challengeSubmissionRepository;
	 	 
	
	
	/**
	 * 첼린지 생성
	 * @param challengeDTO
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> createChallenge (ChallengeDto challengeDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("첼린지 생성 ------> " + "Start");
		
		 // 현재 날짜와 시간을 LocalDateTime 객체로 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        
        // 현재 년도와 월을 가져옵니다.
        int year = now.getYear();
//        int month = now.getMonthValue();
        int month = Integer.parseInt(challengeDto.getStartTime());
        
        // YearMonth 객체를 생성하여 해당 월의 날짜 범위를 가져옵니다.
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        
        // 월초와 월말의 날짜를 LocalDateTime 객체로 생성합니다.
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, month, daysInMonth, 23, 59, 59);
        
        // 월초부터 월말까지의 날짜 범위를 생성합니다.
        LocalDate startDate = startOfMonth.toLocalDate();
        LocalDate endDate = endOfMonth.toLocalDate();
		
		
        boolean existsChallenge = challengeRepository.findById(challengeDto.getChallengeName()).isPresent();
        
        if(existsChallenge == true) {
        	 log.info("첼린지 생성 ------> " + "존재하는 첼린지입니다.");
             result.put("HttpStatus","1.00");		
      		 result.put("Msg","존재하는 첼린지입니다.");
      		 return result;
        }
		
		
		ChallengeEntity challenge =  ChallengeEntity.builder()
									 .challengeName(challengeDto.getChallengeName())
									 .description(challengeDto.getDescription())
									 .startTime(startDate)
									 .endTime(endDate)
									 .build();
		
		
		
        challengeRepository.save(challenge);
        
        log.info("첼린지 생성 ------> " + Constants.SUCCESS);
        result.put("HttpStatus","2.00");		
		result.put("Msg",Constants.SUCCESS);
	
	    return result ;			    		   
	}
	
	/**
	 * 첼린지 가입
	 * @param memberDto
	 * @param challengeDto
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> joinChallenge (String challengeName , String userId) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("첼린지 가입 ------> " + "Start");
		
		
		boolean existsChallenge = challengeRepository.findById(challengeName).isPresent();
		
		boolean existsMember = memberRepository.findById(userId).isPresent();
				
		
		if(existsChallenge== false || existsMember== false) {
			 log.info("첼린지 가입 ------> " + "첼린지 혹은 아이디가 존재하지 않습니다.");
             result.put("HttpStatus","1.00");		
     		 result.put("Msg","첼린지 혹은 아이디가 존재하지 않습니다.");
     	   return result ;
		}				
		
		 ChallengeEntity challenge =  ChallengeEntity.builder().challengeName(challengeName).build();
						 
		 MemberEntity member =  MemberEntity.builder().userId(userId).build();
		 
		 boolean  exsistUserChallenge = userChallengeRepository.findByMemberEntityAndChallengeEntity(member,challenge).isPresent();
		 		 
		 
		 if(exsistUserChallenge == true) {
			 log.info("첼린지 가입 ------> " + "이미 참여하였습니다.");
             result.put("HttpStatus","1.00");		
     		 result.put("Msg","이미 참여하였습니다.");
     	   return result ;
		 }
		 
		 UserChallengeEntity userChallenge =  UserChallengeEntity.builder()
				 							   .memberEntity(member)
				 							   .challengeEntity(challenge)
				 							   .registrationTime(LocalDate.now())
				 							   .completed(false)
				 							   .build();
		 
		 	userChallengeRepository.save(userChallenge);
		 		 
	         log.info("첼린지 가입 ------> " + Constants.SUCCESS);
	         result.put("HttpStatus","2.00");		
			 result.put("Msg",Constants.SUCCESS);	       
			 log.info("첼린지 가입 ------> " + "End");
		  return result ;				 	    		   
}
	
	
	/**
	 * 첼린지 제출
	 * @param challengeDTO
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> submitChallenge (String challengeName , String userId) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("첼린지 제출 ------> " + "Start");
		
		boolean existsChallenge = challengeRepository.findById(challengeName).isPresent();
		
		boolean existsMember = memberRepository.findById(userId).isPresent();
				
		
		if(existsChallenge== false || existsMember== false) {
			 log.info("첼린지 제출 ------> " + "첼린지 혹은 아이디가 존재하지 않습니다.");
             result.put("HttpStatus","1.00");		
     		 result.put("Msg","첼린지 혹은 아이디가 존재하지 않습니다.");
     	   return result ;
		}				
		
		 	ChallengeEntity challenge =  ChallengeEntity.builder().challengeName(challengeName).build();
						 
		 	MemberEntity member =  MemberEntity.builder().userId(userId).build();
		
		 	Optional<UserChallengeEntity>  exsistUserChallenge = userChallengeRepository.findByMemberEntityAndChallengeEntity(member,challenge);
		 	 
		 	 if(exsistUserChallenge.isPresent() == false) {
		 		 log.info("첼린지 제출 ------> " + "첼린지에 참여하고 계시지 않습니다.");
	             result.put("HttpStatus","1.00");		
	     		 result.put("Msg","첼린지에 참여하고 계시지 않습니다.");
	     	   return result ; 
		 	 }
		 	 		 	
		 	UserChallengeEntity userChallenge = exsistUserChallenge.get();		 			 	
		 	
		 	boolean submitYn = challengeSubmissionRepository.findByUserChallengeAndSubmissionTime(userChallenge, LocalDate.now()).isPresent();
		 	 
		 	if(submitYn == true) {
		 		 log.info("첼린지 제출 ------> " + "오늘은 이미 제출이 완료되었습니다.");
	             result.put("HttpStatus","1.00");		
	     		 result.put("Msg","이미 제출이 완료되었습니다.");
	     	   return result ; 
		 	}
		 			 			 			 			 			
		 	ChallengeSubmissionEntity challengeSubmission = ChallengeSubmissionEntity.builder()
				   											.submissionTime(LocalDate.now()) // 제출 일시
				   											.submissionText(true) // 제출 결과
				   											.userChallenge(userChallenge) // 유저 첼린지 ID 
				   											.build();
	        challengeSubmissionRepository.save(challengeSubmission);
	        userChallenge.setCompleted(true);	        	        
	        
	        log.info("첼린지 제출 ------> " + Constants.SUCCESS);
	        result.put("HttpStatus","2.00");		
			result.put("Msg",Constants.SUCCESS);	       
			log.info("첼린지 제출 ------> " + "End");
	        
	    return result ;			    		   
	}
	
	}
