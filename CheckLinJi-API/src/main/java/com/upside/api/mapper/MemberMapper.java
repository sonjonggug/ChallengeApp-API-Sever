package com.upside.api.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.upside.api.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	HashMap<String,String> missionCompletedCnt (MemberDto memberDto);
}
