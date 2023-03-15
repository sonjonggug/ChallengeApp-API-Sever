package com.upside.api.entity;

import java.time.LocalDate;
import java.util.Optional;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 변경된 필드만 적용
@Table(name = "FileUpload")
public class FileUploadEntity { // 사용자가 참여한 첼린지 정보를 저장하는 테이블

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "file_id")
 private Long fileId;
 
 @ManyToOne(fetch = FetchType.LAZY) // fileEntity 입장에선 Member와 다대일 관계이므로 @ManyToOne이 됩니다.
 @JoinColumn(name = "email") // 외래 키를 매핑할 때 사용합니다. name 속성에는 매핑 할 외래 키 이름을 지정합니다.
 private MemberEntity memberEntity;
 
 @Column(nullable = false) // 파일 저장 경로
 private String fileRoute;
 
 @Column(nullable = false) // 파일 이름
 private String fileName;
 
 @Column(nullable = false) // 등록일
 private LocalDate uploadDate;
 
 @Column(nullable = false) // 느낀점
 private String userFeeling; 

 
 
 
@Builder
public FileUploadEntity(MemberEntity memberEntity , String fileRoute , String fileName, LocalDate uploadDate , String userFeeling) {		
	super();
	this.memberEntity = memberEntity;
	this.fileRoute = fileRoute;
	this.fileName = fileName;
	this.uploadDate = uploadDate;
	this.userFeeling = userFeeling;
}
}
