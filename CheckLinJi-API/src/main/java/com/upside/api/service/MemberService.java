package com.upside.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired 
	MemberRepository memberRepository;
	
	/**
	 * 회원목록 조회
	 * @param memberDto
	 * @return
	 */
	public List<MemberEntity> memberList(MemberDto memberDto) {																				 		 		 		 		
		return  memberRepository.findAll();							
	}
	
	/**
	 * 회원가입 
	 * @param memberDto
	 * @return 
	 */
	public String signUp(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null || memberDto.getUserName() == null || memberDto.getPassword() == null || memberDto.getEmail() == null ||
		   memberDto.getAge() == 0 || memberDto.getBirth() == null || memberDto.getSex() == null || 
		   memberDto.getLoginDate() == null || memberDto.getJoinDate() == null ) {									
			log.info("회원가입 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			return Constants.NOT_EXIST_PARAMETER ; // 인서트 실패 : 입력값 에러
		} 
		
		
		 boolean idSame = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if(idSame == true) {
			 log.info("회원가입 실패 ------> " + "중복된 ID 입니다.");
			 return "중복된 ID 입니다." ;
		 }
		 		
		MemberEntity memberEntity = MemberEntity.builder()
				.userId(memberDto.getUserId())
				.userName(memberDto.getUserName())
				.password(memberDto.getPassword())
				.email(memberDto.getEmail())
				.age(memberDto.getAge())
				.birth(memberDto.getBirth())
				.sex(memberDto.getSex())
				.loginDate(memberDto.getLoginDate())
				.joinDate(memberDto.getJoinDate())
				.build();
						
		 memberRepository.save(memberEntity);
		 
		 boolean  result = memberRepository.findById(memberDto.getUserId()).isPresent();
		 
		 if (result == true) {
			 log.info("회원가입 성공 ------> " + memberDto.getUserId());
			 return Constants.SUCCESS;  // 인서트 성공  
		 } else {
			 log.info("회원가입 실패 ------> " + Constants.FAIL);
			 return Constants.FAIL; // 인서트 실패
		 }
										
	}
	
	/**
	 * 회원정보 업데이트
	 * @param memberDto
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public String updateMember(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null ) {
			log.info("회원정보 업데이트 실패 ------> " + Constants.NOT_EXIST_PARAMETER);
			return Constants.NOT_EXIST_PARAMETER ; // 인서트 실패 : 입력값 에러
		} 
				
		MemberEntity user = memberRepository.findById(memberDto.getUserId()).get();
		
		if(user.getUserId() == null) {
			return Constants.FAIL ; 
		}
						
		 user.setUserName(memberDto.getUserName());
		 user.setPassword(memberDto.getPassword());
		 user.setBirth(memberDto.getBirth());
		 user.setAge(memberDto.getAge());
		 user.setSex(memberDto.getSex());
		 		 		 
		 return Constants.SUCCESS ;						
	}
	
	/**
	 * 회원정보 삭제
	 * @param memberId
	 * @return
	 */
	public String deleteMember(String memberId) {
		
		 if(memberRepository.findById(memberId).isPresent() == true ) {
			 memberRepository.deleteById(memberId);
			 log.info("삭제 성공 ------> " + memberId);
			return Constants.SUCCESS ;	
		 } else {
			 log.info("삭제 실패 ------> " + memberId);
			 return Constants.FAIL ;	
		 }
	
}

}