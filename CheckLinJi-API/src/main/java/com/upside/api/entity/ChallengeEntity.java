package com.upside.api.entity;

import java.time.LocalDateTime;

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
@Table(name = "ChallengeList")
public class ChallengeEntity { // Challenge 테이블: 첼린지 정보를 저장하는 테이블

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "challenge_id")
 private Long challengeId;
 
 @Column(nullable = false , name = "challengeName")
 private String challengeName;
 
 @Column(name = "description")
 private String description;
 
 @Column(nullable = false , name = "startTime")
 private LocalDateTime  startTime;
 
 @Column(nullable = false , name = "endTime")
 private LocalDateTime  endTime;
 

@Builder
public ChallengeEntity(String challengeName, String description , LocalDateTime startTime, LocalDateTime endTime) {		
	super();
	this.challengeName = challengeName;
	this.description = description;	
	this.startTime = startTime;
	this.endTime = endTime;
	

}
}
