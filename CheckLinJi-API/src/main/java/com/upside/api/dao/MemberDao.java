package com.upside.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.upside.api.entity.MemberEntity;

@Mapper
public interface MemberDao {
	
	public int signUp(MemberEntity meberDto);
}
