package com.upside.api.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.config.Encrypt;
import com.upside.api.config.JwtTokenProvider;
import com.upside.api.dto.MemberDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.repository.MemberRepository;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
//@RequiredArgsConstructor
@Service
public class MemberService {
	
	@Autowired 
	MemberRepository memberRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	/**
	 * 회원목록 조회
	 * @param memberDto
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<MemberEntity> memberList(Pageable pageable) {																				 		 		 		 		
		return  memberRepository.findAll(pageable);							
	}
	
	/**
	 * 회원가입 
	 * @param memberDto
	 * @return 
	 */
	public HttpStatus signUp(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null || memberDto.getUserName() == null || memberDto.getPassword() == null || memberDto.getEmail() == null ||
		   memberDto.getAge() == 0 || memberDto.getBirth() == null || memberDto.getSex() == null ) {  
		    									
			log.info("회원가입 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			
			return HttpStatus.UNPROCESSABLE_ENTITY ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		} 
		
		
		 boolean idSame = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if(idSame == true) {
			 log.info("회원가입 실패 ------> " + "중복된 ID 입니다.");
			 return HttpStatus.UNPROCESSABLE_ENTITY ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		 }
		 
		SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh:mm");        		
		 
		MemberEntity memberEntity = MemberEntity.builder()
				.userId(memberDto.getUserId())
				.userName(memberDto.getUserName())
				.password(Encrypt.sha2String(memberDto.getPassword())) 
				.email(memberDto.getEmail())
				.age(memberDto.getAge())
				.birth(memberDto.getBirth())
				.sex(memberDto.getSex())
				.loginDate(today.format(new Date()))
				.joinDate(today.format(new Date()))
				.build();
						
		 memberRepository.save(memberEntity);
		 
		 boolean  result = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if (result == true) {
			 log.info("회원가입 성공 ------> " + memberDto.getUserId());
			 return HttpStatus.OK;  // 인서트 성공  
		 } else {
			 log.info("회원가입 실패 ------> " + Constants.FAIL);
			 return HttpStatus.INTERNAL_SERVER_ERROR ; // 서버가 처리 방법을 모르는 상황이 발생했습니다. 서버는 아직 처리 방법을 알 수 없습니다.
		 }
		 
	}
	
	/**
	 * 회원정보 업데이트
	 * @param memberDto
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public HttpStatus updateMember(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null ) {
			log.info("회원정보 업데이트 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			
			return HttpStatus.UNPROCESSABLE_ENTITY ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		} 
				
		MemberEntity user = memberRepository.findById(memberDto.getUserId()).get();
		
		if(user.getUserId() == null) {
			
			return HttpStatus.UNPROCESSABLE_ENTITY ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		}
						
		 user.setUserName(memberDto.getUserName());
		 user.setPassword(memberDto.getPassword());
		 user.setBirth(memberDto.getBirth());
		 user.setAge(memberDto.getAge());
		 user.setSex(memberDto.getSex());
		 		 		 
		 return HttpStatus.OK ; // 요청 성공				
	}
	
	/**
	 * 회원정보 삭제
	 * @param memberId
	 * @return
	 */
	public HttpStatus deleteMember(String userId) {
		
		 if(memberRepository.findById(userId).isPresent() == true ) {
			 memberRepository.deleteById(userId);
			 log.info("삭제 성공 ------> " + userId);
			 
			 return HttpStatus.OK ; // 요청 성공	
		 } else {
			 log.info("삭제 실패 ------> " + userId);
			 return HttpStatus.INTERNAL_SERVER_ERROR ; // 서버가 처리 방법을 모르는 상황이 발생했습니다. 서버는 아직 처리 방법을 알 수 없습니다.	
		 }
	
}	
	
	public Map<String, String> loginMember(MemberDto memberDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		MemberEntity memberEntity = memberRepository.findByUserId(memberDto.getUserId());
		    if (!passwordEncoder.matches(memberDto.getPassword(), memberEntity.getPassword())) {
		    	result.put("HttpStatus", "422");
		    	result.put("UserId", null);
		    } else {
		    result.put("HttpStatus", "200");
		    result.put("Header", jwtTokenProvider.createToken(memberDto.getEmail()));
		    result.put("UserId", memberDto.getEmail());
//		    return new MemberLoginResponseDto(MemberEntity.getUserId(), jwtTokenProvider.createToken(requestDto.getEmail()));
		    }
		    return result ;	
		    
		    
//		memberDto.setPassword(Encrypt.sha2String(memberDto.getPassword()));
//		
//		MemberEntity memberEntity = memberRepository.findByUserId(memberDto.getUserId());
//		
//		Map<String, String> result = new HashMap<String, String>();
//		
//		if (memberEntity.getUserId().equals(memberDto.getUserId()) && memberEntity.getPassword().equals(memberDto.getPassword())) {	
//			System.out.println(jwtTokenProvider.createToken(memberDto.getUserId()));
////			result.put("Header", jwtTokenProvider.createToken(memberDto.getUserId()));
//			result.put("Header", jwtTokenProvider.createToken(memberDto.getUserId()));
//			result.put("HttpStatus", "200");
//				        							
//		} else {
//			result.put("HttpStatus", "422");			
//		}
//		return result ;		
//	}
}
}