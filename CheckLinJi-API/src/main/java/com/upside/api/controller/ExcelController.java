package com.upside.api.controller;




import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upside.api.dto.MemberDto;
import com.upside.api.dto.MessageDto;
import com.upside.api.util.Constants;
import com.upside.api.util.Excel;
import com.upside.api.util.ExcelApiDownload;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/excel")
public class ExcelController {
    
    private final Excel excel;
    private final ExcelApiDownload excelApi;
        


    
//    @PostMapping("/memberList")     
//    public void MemberListExcel(@ModelAttribute MemberDto memberDto ,  HttpServletResponse response) throws Exception {    	
//    	String data = "memberListExcel";    	
//    	
//    	boolean result = excel.getReserveExcel(memberDto,data,response);         
//         	
//    } 
    
    
    @ResponseBody
    @PostMapping("/memberList")     
    public void MemberListExcel(@RequestBody MemberDto memberDto ,  HttpServletResponse response) throws Exception {    	
    	String data = "memberListExcel";    	
    	MessageDto message = new MessageDto();
    	
    	ByteArrayInputStream result = excelApi.getReserveExcel(memberDto,data,response);  
    	
    	if(result != null) {
    		 System.out.println(" 값있음");
    		 response.setContentType("application/octet-stream");
             //첨부파일의 다운로드 이름을 설정합니다.
             response.setHeader("Content-Disposition", "attachment; filename=excelFileName.xlsx");
             
//             IOUtils.copy(result, response.getOutputStream());
             
             try (OutputStream out = response.getOutputStream()) {
            	    IOUtils.copy(result, out);
            	} finally {
            	    IOUtils.closeQuietly(result);
            	}
    		
    	} else {
    		message.setMsg(Constants.FAIL);
			message.setStatusCode("1.00");
			
    	              	        
    	}
    	    	                 
    }
}
