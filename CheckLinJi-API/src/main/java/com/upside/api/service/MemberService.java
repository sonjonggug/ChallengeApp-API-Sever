package com.upside.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.dto.MemberDto;
import com.upside.api.entity.MemberEntity;
import com.upside.api.repository.MemberRepository;

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
	public int signUp(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null || memberDto.getUserName() == null || memberDto.getPassword() == null || memberDto.getEmail() == null ||
		   memberDto.getAge() == 0 || memberDto.getBirth() == null || memberDto.getSex() == null || 
		   memberDto.getLoginDate() == null || memberDto.getJoinDate() == null ) {
			return 3 ; // 인서트 실패 : 입력값 에러
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
		 
		 Optional<MemberEntity> result = memberRepository.findById(memberDto.getUserId());
		 
		 if (result == null) {
			 return 0;  // 인서트 실패 : 서버 에러 
		 } else {
			 return 1; // 인서트 성공
		 }
										
	}
	
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public int updateMember(MemberDto memberDto) {
		
		if(memberDto.getUserId() == null ) {
			return 3 ; // 인서트 실패 : 입력값 에러
		} 
						
		MemberEntity user = memberRepository.findById(memberDto.getUserId());
		
		
		 
		 
//		 Optional<MemberEntity> result = memberRepository.findById(memberDto.getUserId());
		 
		 if (result == null) {
			 return 0;  // 인서트 실패 : 서버 에러 
		 } else {
			 return 1; // 인서트 성공
		 }
										
	}
	
}
