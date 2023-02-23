package com.upside.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.upside.api.dto.MemberDto;

@Mapper
public interface MemberDao {
	
	public int signUp(MemberDto memberDto);
}
