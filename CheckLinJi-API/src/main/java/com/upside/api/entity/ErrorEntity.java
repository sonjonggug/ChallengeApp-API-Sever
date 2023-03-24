package com.upside.api.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ErrorLog")
public class ErrorEntity { // User 테이블: 사용자 정보를 저장하는 테이블

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long error_seq;
 
 @Column(name = "errorTitle")
 private String errorTitle;
 
 @Column(name = "errorMsg")
 private String errorMsg;
 
 @Column(name = "errorDate")
 private LocalDate errorDate;
 
 @Column(name = "completed")
 private String completed;
 
 
 

@Builder
public ErrorEntity(String errorTitle, String errorMsg, LocalDate errorDate , String completed) {
	super();
	this.errorTitle = errorTitle;
	this.errorMsg = errorMsg;
	this.errorDate = errorDate;
	this.completed = completed;
	
}
}




