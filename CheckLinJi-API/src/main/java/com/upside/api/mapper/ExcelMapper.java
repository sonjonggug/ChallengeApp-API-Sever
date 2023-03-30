package com.upside.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.upside.api.dto.MemberDto;

@Mapper
public interface ExcelMapper {
	
	/**
	 * Excel Member 정보 리스트 출력
	 * @param memberDto
	 * @return
	 */
	List<MemberDto> memberListExcel (MemberDto memberDto);
	
	
}
