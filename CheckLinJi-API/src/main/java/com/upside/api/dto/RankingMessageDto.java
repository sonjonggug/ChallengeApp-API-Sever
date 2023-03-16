package com.upside.api.dto;

import lombok.Data;

@Data
public class RankingMessageDto {
  
 private String own;
 private String user;
 private Object userList;
 private Object ownList;
 private String msg;
 private String statusCode;
 private String file;
}
