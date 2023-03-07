package com.upside.api.service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.upside.api.dto.MemberDto;
import com.upside.api.mapper.MemberMapper;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class RankingSerivce {
		
	
	private final MemberMapper memberMapper ;
	 	 
	
	
	
	
	 /**
	  * 미션 성공 총 횟수 (월)
	  * @param memberDto
	  * @return
	  */
	public Map<String, String> missionCompletedCnt (MemberDto memberDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		log.info("미션 성공 총 횟수 (월) ------> " + "Start");
		
		 // 현재 날짜와 시간을 LocalDateTime 객체로 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        
        // 현재 년도와 월을 가져옵니다.
        int year = now.getYear();
        int month = now.getMonthValue();
        
        
        // YearMonth 객체를 생성하여 해당 월의 날짜 범위를 가져옵니다.
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        
        // 월초와 월말의 날짜를 LocalDateTime 객체로 생성합니다.
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, month, daysInMonth, 23, 59, 59);
        
        // 월초부터 월말까지의 날짜 범위를 생성합니다.
        LocalDate startDate = startOfMonth.toLocalDate();
        LocalDate endDate = endOfMonth.toLocalDate();
		
        result = memberMapper.missionCompletedCnt(memberDto);
        
        System.out.println(result);
        
        log.info("미션 성공 총 횟수 (월) ------> " + Constants.SUCCESS);
        result.put("HttpStatus","2.00");		
		result.put("Msg",Constants.SUCCESS);
	
	    return result ;			    		   
	}
		
}
