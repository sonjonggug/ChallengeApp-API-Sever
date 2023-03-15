package com.upside.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.upside.api.dto.FileUploadDto;
import com.upside.api.service.FileService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
@RequestMapping("/api/file")

public class FileUploadController {

	 @Value("${file.upload-dir}")
	 private String uploadDir;
	 
	 private final FileService fileSerivce ; 
	 
	
	 @GetMapping("/upload")
	 public String viewImage() {
	     
	     return "FileUpload";
	 }
	 
	 @ResponseBody
	 @PostMapping("/upload")
	    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file , FileUploadDto fileUploadDto) throws IOException {
		 
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
		        
		        Map<String, String> result = fileSerivce.fileRouteInsert(fileUploadDto);
		        
			        if(result.get("HttpStatus").equals("2.00")) {			        	
			        	// 파일 저장
				        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);				        
			        	return ResponseEntity.ok(result.get("Msg"));
			        	
			        }else {
			        	return ResponseEntity.ok(result.get("Msg"));
			        }		        		        		        
			} catch (IOException e) {				
				return ResponseEntity.ok("실패");
			}
	    }
	}
	 

