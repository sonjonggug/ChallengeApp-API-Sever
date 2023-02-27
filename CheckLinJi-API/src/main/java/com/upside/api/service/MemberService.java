package com.upside.api.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.config.JwtTokenProvider;
import com.upside.api.dto.MemberDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.repository.MemberRepository;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@RequiredArgsConstructor
@Service
public class MemberService {
	
	
	private final MemberRepository memberRepository;		
	private final JwtTokenProvider jwtTokenProvider;		
	private final PasswordEncoder passwordEncoder;
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
	public Map<String, String> signUp(MemberDto memberDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		if(memberDto.getUserId() == null || memberDto.getUserName() == null || memberDto.getPassword() == null || memberDto.getEmail() == null ||
		   memberDto.getAge() == 0 || memberDto.getBirth() == null || memberDto.getSex() == null || memberDto.getAuthority() == null ) {  
		    									
			log.info("회원가입 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			result.put("HttpStatus","1.01");
			result.put("Msg",Constants.NOT_EXIST_PARAMETER);
			return result ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		} 
		
		
		 boolean idSame = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if(idSame == true) {
			 log.info("회원가입 실패 ------> " + "중복된 ID 입니다.");
			 
			 result.put("HttpStatus","1.02");
			 result.put("Msg",Constants.DUPLICATE_ID);
			 
			 return result ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		 }
		 
		SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh:mm");        		
		
		MemberEntity memberEntity = MemberEntity.builder()
				.userId(memberDto.getUserId())
				.userName(memberDto.getUserName())
				.password(passwordEncoder.encode(memberDto.getPassword())) 
				.email(memberDto.getEmail())
				.age(memberDto.getAge())
				.birth(memberDto.getBirth())
				.sex(memberDto.getSex())
				.loginDate(today.format(new Date()))
				.joinDate(today.format(new Date()))
				.authority(memberDto.getAuthority())
				.build();
						
		 memberRepository.save(memberEntity);
		 
		 boolean  exsistUser = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if (exsistUser == true) {
			 log.info("회원가입 성공 ------> " + memberDto.getUserId());
			 result.put("HttpStatus","2.00");
			 result.put("UserId",memberDto.getUserId());
			 result.put("Msg",Constants.SUCCESS);
			 return result;  // 인서트 성공  
		 } else {
			 log.info("회원가입 실패 ------> " + Constants.FAIL);
			 result.put("HttpStatus","1.00");
			 result.put("Msg",Constants.FAIL);
			 return result ; 
		 }
		 
	}
	
	 /**
     * Unique한 값을 가져야하나, 중복된 값을 가질 경우를 검증
     * @param email
     */
    public Map<String, String> validateDuplicated(String email) {
    	
    	Map<String, String> result = new HashMap<String, String>();
    	
        if (memberRepository.findById(email).isPresent()) {
        	log.info("아이디 검증 ------> " + "중복된 ID 입니다.");			 
			 result.put("HttpStatus","1.02");
			 result.put("Msg",Constants.DUPLICATE_ID);			 
        	
        } else {
        	result.put("HttpStatus","2.00");
        	result.put("Msg",Constants.SUCCESS);
        	
        }
        return result ;
    }
	
	
	/**
	 * 회원정보 업데이트
	 * @param memberDto
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public  Map<String, String> updateMember(MemberDto memberDto) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(memberDto.getUserId() == null ) {
			log.info("회원정보 업데이트 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			result.put("HttpStatus","1.01");
			result.put("Msg",Constants.NOT_EXIST_PARAMETER);
			return result ; // 요청은 잘 만들어졌지만, 문법 오류로 인하여 따를 수 없습니다.
		} 
				
		MemberEntity user = memberRepository.findByUserId(memberDto.getUserId());		
		
		if(user.getUserId() == null) { // 아이디가 없으면
			log.info("회원정보 업데이트 실패 ------> " + Constants.NOT_EXIST_ID);
			result.put("HttpStatus","1.04");
			result.put("Msg",Constants.NOT_EXIST_ID);			
			return result ; 
		} else if(memberDto.getPassword() != null && !passwordEncoder.matches(memberDto.getPassword(), user.getPassword())) { // 패스워드 변경시
			log.info("회원정보 패스워드 업데이트 ------> " + user.getUserId());
			 user.setPassword(passwordEncoder.encode(memberDto.getPassword()));			 
			 result.put("HttpStatus","2.00");
			 result.put("Msg",Constants.SUCCESS);		 
			 return result ; // 요청 성공	
		} else {
			
			 user.setUserName(memberDto.getUserName());
			 user.setEmail(memberDto.getEmail());			 
			 user.setBirth(memberDto.getBirth());
			 user.setAge(memberDto.getAge());
			 user.setSex(memberDto.getSex());
			 
			 result.put("HttpStatus","2.00");
			 result.put("Msg",Constants.SUCCESS);
			 log.info("회원정보 업데이트 ------> " + user.getUserId()); 
			 return result ; // 요청 성공			
		}				
				 	
	}
	
	/**
	 * 회원정보 삭제
	 * @param memberId
	 * @return
	 */
	public Map<String, String> deleteMember(String userId) {
		Map<String, String> result = new HashMap<String, String>();
		
		 if(memberRepository.findById(userId).isPresent() == true ) {
			 memberRepository.deleteById(userId);
			 log.info("삭제 성공 ------> " + userId);
			 result.put("HttpStatus", "2.00");
			 result.put("Msg", Constants.SUCCESS);
			 return result ;	
		 } else {
			 log.info("삭제 실패 ------> " + userId);
			 result.put("HttpStatus", "1.00");
			 result.put("Msg", Constants.FAIL);
			 return result ; // 서버가 처리 방법을 모르는 상황이 발생했습니다. 서버는 아직 처리 방법을 알 수 없습니다.	
		 }
	
}	
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> loginMember(MemberDto memberDto) {
		Map<String, String> result = new HashMap<String, String>();
		
		MemberEntity memberEntity = memberRepository.findByUserId(memberDto.getUserId());
		    if (!passwordEncoder.matches(memberDto.getPassword(), memberEntity.getPassword())) {
		    	result.put("HttpStatus", "1.03");
		    	result.put("UserId", null);
		    	result.put("Msg", Constants.INBALID_ID_PASSWORD);
		    	
		    } else {
		    memberEntity.setRefreshToken((jwtTokenProvider.createRefreshToken())); // refresh Token DB 저장
		    result.put("HttpStatus", "2.00");
		    result.put("Header", jwtTokenProvider.createToken(memberDto.getUserId()));
		    result.put("refreshToken", memberEntity.getRefreshToken());
		    result.put("UserId", memberDto.getUserId());
		    result.put("Msg", Constants.SUCCESS);
		    
		    }
		    return result ;	
		    
		    

}
}