package com.upside.api.dto;

import lombok.Data;

@Data
public class MessageDto {
  
 private String userEmail;
 private String userNickName;
 private String msg;
 private String statusCode;
 private String toKen;
 private String refreshToken;
}
