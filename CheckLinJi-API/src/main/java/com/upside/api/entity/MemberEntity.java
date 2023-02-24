package com.upside.api.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "MemberInfo")
public class MemberEntity {

 @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
 private String userId;
 
 @Column(name = "password")
 private String password;
 
 @Column(name = "userName")
 private String userName;
 
 @Column(name = "age")
 private int age;
 
 @Column(name = "email")
 private String email;  
 
 @Column(name = "birth")
 private String birth;
 
 @Column(name = "sex")
 private String sex;
 
 @Column(name = "joinDate")
 private String joinDate;
 
 @Column(name = "loginDate")
 private String loginDate;

 @Column(name = "authority")
 private String authority;
 
 
	/*
	 * public void save (MemberDto memberDto) { this.userId = memberDto.getUserId();
	 * this.password = memberDto.getPassword(); this.userName =
	 * memberDto.getUserName(); this.age = memberDto.getAge(); this.email =
	 * memberDto.getEmail(); this.birth = memberDto.getBirth(); this.sex =
	 * memberDto.getSex(); this.joinDate = memberDto.getJoinDate(); this.loginDate =
	 * memberDto.getLoginDate(); }
	 */


@Builder
public MemberEntity(String userId, String password, String userName, int age, String email, String birth, String sex,
		String joinDate, String loginDate , String authority) {
	super();
	this.userId = userId;
	this.password = password;
	this.userName = userName;
	this.age = age;
	this.email = email;
	this.birth = birth;
	this.sex = sex;
	this.joinDate = joinDate;
	this.loginDate = loginDate;
	this.authority = authority;
}
}
