package com.upside.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserChallengeDto { // UserChallenge 테이블: 사용자가 참여한 첼린지 정보를 저장하는 테이블

 
 private Long userChallengeId; 
 private MemberDto memberDto;
 private ChallengeDto challengeDto; 
 private LocalDateTime registrationTime;
 private boolean completed;
 
}
