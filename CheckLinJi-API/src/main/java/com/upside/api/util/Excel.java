package com.upside.api.util;

import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.upside.api.dto.MemberDto;
import com.upside.api.mapper.ExcelMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class Excel {
	
	private final ExcelMapper excelMapper ;
	
	/**
	 * 해더의 스타일을 담당합니다.
	 * @param cs
	 * @param font
	 * @param cell
	 */
	private void setHeaderCS(CellStyle cs, Font font, Cell cell) {
		  cs.setAlignment(CellStyle.ALIGN_CENTER);
		  cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		  cs.setBorderTop(CellStyle.BORDER_THIN);
		  cs.setBorderBottom(CellStyle.BORDER_THIN);
		  cs.setBorderLeft(CellStyle.BORDER_THIN);
		  cs.setBorderRight(CellStyle.BORDER_THIN);
		  cs.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		  cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		  setHeaderFont(font, cell);
		  cs.setFont(font);
		  cell.setCellStyle(cs);
		}
		 
		/**
		 * 해더의 스타일의 폰트를 담당합니다.
		 * @param font
		 * @param cell
		 */
		private void setHeaderFont(Font font, Cell cell) {
		  font.setBoldweight((short) 700);
		  font.setColor(HSSFColor.WHITE.index);
		}
		 
		/**
		 * 본문 데이터의 스타일을 담당합니다.
		 * @param cs
		 * @param cell
		 */
		private void setCmmnCS2(CellStyle cs, Cell cell) {
		  cs.setAlignment(CellStyle.ALIGN_LEFT);
		  cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		  cs.setBorderTop(CellStyle.BORDER_THIN);
		  cs.setBorderBottom(CellStyle.BORDER_THIN);
		  cs.setBorderLeft(CellStyle.BORDER_THIN);
		  cs.setBorderRight(CellStyle.BORDER_THIN);
		  cell.setCellStyle(cs);
		}
		 
		 
		/**
		 * 실제 엑셀 기능 다운로드 기능을 담당
		 * @param memberDto
		 * @param excelGubun
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public boolean getReserveExcel(MemberDto memberDto , String excelGubun ,  HttpServletResponse response) throws Exception {
		
			try {
				 SXSSFWorkbook wb = new SXSSFWorkbook();
				  Sheet sheet = wb.createSheet();
					if(excelGubun.equals("memberListExcel")) {
						List<MemberDto> list = excelMapper.memberListExcel(memberDto);
						  
						  // 셀 크기
						  sheet.setColumnWidth((short) 0, (short) 1300); 
						  sheet.setColumnWidth((short) 1, (short) 8000);
						  sheet.setColumnWidth((short) 2, (short) 2000);
						  sheet.setColumnWidth((short) 3, (short) 4000);
						  sheet.setColumnWidth((short) 4, (short) 3000);
						  sheet.setColumnWidth((short) 5, (short) 1000);
						  sheet.setColumnWidth((short) 6, (short) 2000);
						  sheet.setColumnWidth((short) 7, (short) 5000);
						  
						  Row row = sheet.createRow(0);
						  Cell cell = null;
						  CellStyle cs = wb.createCellStyle();
						  Font font = wb.createFont();
						  cell = row.createCell(0);
						  cell.setCellValue("사용자 정보 - 사용자 리스트");
						  setHeaderCS(cs, font, cell);
						  sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 7));
						  
						  row = sheet.createRow(1);
						  cell = null;
						  cs = wb.createCellStyle();
						  font = wb.createFont();
						  
						  cell = row.createCell(0);
						  cell.setCellValue("번호");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(1);
						  cell.setCellValue("이메일");
						  setHeaderCS(cs, font, cell);
						 
						  cell = row.createCell(2);
						  cell.setCellValue("이름");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(3);
						  cell.setCellValue("닉네임");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(4);
						  cell.setCellValue("생년월일");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(5);
						  cell.setCellValue("성별");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(6);
						  cell.setCellValue("권한");
						  setHeaderCS(cs, font, cell);
						  
						  cell = row.createCell(7);
						  cell.setCellValue("가입날짜");
						  setHeaderCS(cs, font, cell);
						  
						 
						  int i = 2; // Row 초기 값
						  int ii = 1;
						  for (MemberDto vo  : list) {
						      					
						 
						  row = sheet.createRow(i);
						  cell = null;
						  cs = wb.createCellStyle();
						  font = wb.createFont();
						  
						  cell = row.createCell(0);
						  cell.setCellValue(ii);
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(1);
						  cell.setCellValue(vo.getEmail());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(2);
						  cell.setCellValue(vo.getName());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(3);					  
						  cell.setCellValue(vo.getNickName());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(4);
						  cell.setCellValue(vo.getBirth());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(5);
						  cell.setCellValue(vo.getSex());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(6);
						  cell.setCellValue(vo.getAuthority());
						  setCmmnCS2(cs, cell);
						  
						  cell = row.createCell(7);					  
						  cell.setCellValue(vo.getJoinDate());
						  setCmmnCS2(cs, cell);
						  
						  i++;
						  ii++;
					}
			}			  
			  response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			  response.setHeader("Content-Disposition", String.format("attachment; filename=\"ReserveManageList.xlsx\""));
			  wb.write(response.getOutputStream());
			  	log.info("엑셀 다운로드 성공 ");
			  return true;
			} catch (Exception e) {
				log.info("엑셀 다운로드 실패 ");
			  return false;
			}			 		 
		}

}
