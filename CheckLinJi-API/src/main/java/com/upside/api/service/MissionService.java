package com.upside.api.service;



import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.dto.FileUploadDto;
import com.upside.api.dto.MemberDto;
import com.upside.api.entity.FileUploadEntity;
import com.upside.api.entity.MemberEntity;
import com.upside.api.mapper.MemberMapper;
import com.upside.api.repository.MemberRepository;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class MissionService {
		
	
	private final MemberMapper memberMapper ;
	
	private final MemberRepository memberRepository ;
	 	 
	
	
	
	
	 /**
	  * 미션 성공 총 횟수 (월)
	  * @param memberDto
	  * @return
	  */
	public Map<String, String> missionCompletedCnt (MemberDto memberDto) {
		Map<String, String> result = new HashMap<String, String>();
						
		 // 현재 날짜와 시간을 LocalDateTime 객체로 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        
        // 현재 년도와 월을 가져옵니다.
        int year = now.getYear();
        int month = now.getMonthValue();
                                                 		
        Map<String, String> data = new HashMap<String, String>();
        
        data.put("year", String.valueOf(year));
        data.put("month", String.valueOf(month));
        data.put("email", memberDto.getEmail());
        
        result = memberMapper.missionCompletedCnt(data);
        
        if (String.valueOf(result.get("own")) == null || String.valueOf(result.get("own")).equals("0")) {
        	result.put("HttpStatus","1.00");		
    		result.put("Msg",Constants.FAIL);
    		return result ;
        }
        
        result.put("HttpStatus","2.00");		
		result.put("Msg",Constants.SUCCESS);
		
		log.info("미션 성공 총 횟수 (월) ------> " + Constants.SUCCESS);
		
	    return result ;			    		   
	}
	
	/**
	  * 실시간 랭킹
	  * @param memberDto
	  * @return
	  */
	public Map<String, Object> missionRanking (MemberDto memberDto) {
		
	   Map<String, Object> result = new HashMap<String, Object>();
	   
	   
              		
       Map<String, String> data = new HashMap<String, String>();              
       
       data.put("email", memberDto.getEmail());
       
       ArrayList<Map<String, Object>> missionRankingTop = memberMapper.missionRankingTop(data);
       
       Map<String, String> missionRankingOwn = memberMapper.missionRankingOwn(data);
                     
       if (missionRankingTop.get(0) == null ) {
    	    log.info("실시간 랭킹 ------> " + "TOP 3 조회 실패");
    	    result.put("HttpStatus","1.00");		
   			result.put("Msg","TOP 3 조회 실패");
   		 return result ;
       }
       
       if (missionRankingOwn == null ) {
    	    log.info("실시간 랭킹 ------> " + "해당 사용자 참여중 아닐땐 TOP3 만");
   	    	result.put("HttpStatus","2.01");		
  			result.put("Msg","참여중이 아닙니다.");
  			result.put("missionRankingTop",missionRankingTop);
  		 return result ;
       }
       
       result.put("HttpStatus","2.00");		
	   result.put("Msg",Constants.SUCCESS);
	   result.put("missionRankingTop",missionRankingTop);
	   result.put("missionRankingOwn",missionRankingOwn);
		
		log.info("실시간 랭킹 ------> " + Constants.SUCCESS);
		
	    return result ;			    		   
	}
	
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> myAuth(FileUploadDto fileUploadDto) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		     
		 
	  return result ;				 	    			    		   
	}
}
