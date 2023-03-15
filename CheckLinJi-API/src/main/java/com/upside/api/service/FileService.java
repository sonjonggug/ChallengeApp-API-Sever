package com.upside.api.service;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upside.api.dto.FileUploadDto;
import com.upside.api.entity.FileUploadEntity;
import com.upside.api.entity.MemberEntity;
import com.upside.api.entity.UserChallengeEntity;
import com.upside.api.repository.FileUploadRepository;
import com.upside.api.repository.MemberRepository;
import com.upside.api.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@RequiredArgsConstructor
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음.
@Service
public class FileService {
		
	private final FileUploadRepository fileUploadRepository;
	private final MemberRepository memberRepository; 
	
	
	/**
	 * 파일 업로드 시 이름 및 경로 저장 
	 * @param fileRoute
	 * @param fileName
	 * @return
	 */
	@Transactional // 트랜잭션 안에서 entity를 조회해야 영속성 상태로 조회가 되고 값을 변경해면 변경 감지(dirty checking)가 일어난다.
	public Map<String, String> fileRouteInsert (FileUploadDto fileUploadDto) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(fileUploadDto.getEmail()==null || fileUploadDto.getFileName()==null || fileUploadDto.getFileRoute()==null 
			|| fileUploadDto.getUploadDate()==null || fileUploadDto.getUserFeeling()==null ) {
			
			log.info("파일 업로드 ------> " + Constants.NOT_EXIST_PARAMETER);
            result.put("HttpStatus","1.00");		
   		 	result.put("Msg",Constants.NOT_EXIST_PARAMETER);
   		 	return result ;
		}
		
		Optional<MemberEntity> existsMember = memberRepository.findById(fileUploadDto.getEmail());
		
		if(!existsMember.isPresent()) {
			 log.info("파일 업로드 ------> " + "이메일이 존재하지 않습니다.");
             result.put("HttpStatus","1.00");		
    		 result.put("Msg","이메일이 존재하지 않습니다.");
    		 return result ;
		}		
		
		 MemberEntity member =  existsMember.get();
		 
		 boolean alreadySubmitYn = fileUploadRepository.findByMemberEntityAndUploadDate(member, fileUploadDto.getUploadDate()).isPresent();
		 
		 if(alreadySubmitYn) {
			 log.info("파일 업로드 ------> " + "오늘은 이미 제출하였습니다.");
             result.put("HttpStatus","1.00");		
     		 result.put("Msg","오늘은 이미 제출하였습니다");
     	   return result ;
		 }
		
		 FileUploadEntity fileUploadEntity =  FileUploadEntity.builder()
				   .memberEntity(member)
				   .fileRoute(fileUploadDto.getFileRoute())
				   .fileName(fileUploadDto.getFileName())
				   .uploadDate(fileUploadDto.getUploadDate())
				   .userFeeling(fileUploadDto.getUserFeeling())
				   .build();

		 fileUploadRepository.save(fileUploadEntity);
		 
		 log.info("파일 업로드 ------> " + Constants.SUCCESS);
         result.put("HttpStatus","2.00");		
		 result.put("Msg",Constants.SUCCESS);	       
		 
	  return result ;				 	    			    		   
	}
	
	
	
	}
