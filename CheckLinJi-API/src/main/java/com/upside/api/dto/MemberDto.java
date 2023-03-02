package com.upside.api.dto;

import lombok.Data;

@Data
public class MemberDto {
 
 private String userId;
 private String password;
 private String name;
 private String nickName;
 private int age;
 private String email;  
 private String birth;  
 private String sex;  
 private String joinDate; 
 private String loginDate;
 private String authority;
 private String accessToken;
 private String refreshToken;
 
}
