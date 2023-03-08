package com.upside.api.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	
	HashMap<String,String> missionCompletedCnt (Map<String, String> data);
	
	ArrayList<Map<String, Object>> missionRankingTop (Map<String, String> data);
	
	HashMap<String,String> missionRankingOwn (Map<String, String> data);
}
