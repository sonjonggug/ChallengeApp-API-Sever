package com.upside.api.service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.upside.api.dto.FileUploadDto;
import com.upside.api.entity.FileUploadEntity;
import com.upside.api.entity.MemberEntity;
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
	
	 @Value("${file.upload-dir}")
	 private String uploadDir;
	
	
	/**
	 *  파일 업로드 ( 외부에다 저장 )
	 * @param file
	 * @param fileUploadDto
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file , FileUploadDto fileUploadDto) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
	  	LocalDate now = LocalDate.now();  
	 	try {
	 	// 업로드된 파일 이름 가져오기
	        String fileName = fileUploadDto.getEmail()+"_"+now+"_"+StringUtils.cleanPath(file.getOriginalFilename());

	        // 파일 저장 경로 생성
	        Path uploadPath = Paths.get(uploadDir);
	        	        
	        // 파일 저장 경로가 없을 경우 생성
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        // 파일 저장 경로와 파일 이름을 조합한 경로 생성
	        Path filePath = uploadPath.resolve(fileName).normalize();		        		        
	        
	        // 문자열에서 백슬래시()는 이스케이프 문자(escape character)로 사용되기 때문에 사용할려면 \\ 두개로 해야 \로 인식
	        String fileRoute = uploadPath.toString() + "\\" + fileName ; 
	        		        		        		        		        
	        fileUploadDto.setFileRoute(fileRoute);
	        fileUploadDto.setFileName(fileName);
	        fileUploadDto.setUploadDate(now);		        
	        
	        // 파일 이름 및 경로 DB 저장 
	        result = fileRouteInsert(fileUploadDto);
	        	// DB 저장 성공 시 
		        if(result.get("HttpStatus").equals("2.00")) {			        	
		        	// 파일 저장
			        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);				        		        			        	
		        }		        
		        return result;
		        
			} catch (IOException e) {
				result.put("HttpStatus", "1.00");
				return result;
			}	 	
    }
	
	/**
	 * 파일 업로드 시 이름 및 경로 DB 저장 
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
	
	/**
	 * 파일 다운로드 Base64 인코딩 방식
	 * @param fileUploadDto
	 * @return
	 */
	public String myAuthImage(String fileRoute) {
		
		log.info("본인 인증 이미지  ------> " + "Start");
		
		
		String encoded="N";
		try {		        			
		 // 파일 경로
        String filePath = fileRoute ;
        
        // 파일을 바이트 배열로 읽어옴
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        
        // Base64 인코딩
         encoded = Base64.getEncoder().encodeToString(fileContent);
                        
		
		} catch (IOException e) {			
			encoded = "N";
			log.info("본인 인증 이미지  ------> " + "실패");
		}
		 return encoded ;				 	 	    			    		   
	}
	
	}
