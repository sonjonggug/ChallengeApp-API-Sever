package com.upside.api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FileUploadDto { 

	 private String email; // 유저 이메일	 	 
	 private String fileRoute;	// 파일 저장 경로
	 private String fileName; // 파일 이름
	 private LocalDate uploadDate; // 등록일	 	 
	 private String userFeeling; // 느낀점
	 
	 
}
