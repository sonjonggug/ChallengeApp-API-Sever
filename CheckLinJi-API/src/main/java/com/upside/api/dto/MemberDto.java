package com.upside.api.dto;

import lombok.Data;

@Data
public class MemberDto {
 
 private String userId;
 private String password;
 private String userName;
 private int age;
 private String email;  
 private String birth;  
 private String sex;  
 private String joinDate; 
 private String loginDate;
 
}
