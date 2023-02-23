package com.upside.api.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.upside.api.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//@Data
//@Entity

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "MemberInfo")
public class MemberEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long userId;
 
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

 
 
public void save (MemberDto memberDto) {			
	this.userId = memberDto.getUserId();
	this.password = memberDto.getPassword();
	this.userName = memberDto.getUserName();
	this.age = memberDto.getAge();
	this.email = memberDto.getEmail();
	this.birth = memberDto.getBirth();
	this.sex = memberDto.getSex();
	this.joinDate = memberDto.getJoinDate();
	this.loginDate = memberDto.getLoginDate();
	}


@Builder
public MemberEntity(Long userId, String password, String userName, int age, String email, String birth, String sex,
		String joinDate, String loginDate) {
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
}
}
