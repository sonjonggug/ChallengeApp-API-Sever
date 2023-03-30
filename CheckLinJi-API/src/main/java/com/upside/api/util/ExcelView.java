//package com.upside.api.util;
//
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFFooter;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.Footer;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.servlet.view.document.AbstractXlsxView;
//
//import com.upside.api.dto.MemberDto;
//
//public class ExcelView extends AbstractXlsxView {
//
//	
//
//	@Override
//	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		// TODO Auto-generated method stub
//		buildExcelDocument(model, (XSSFWorkbook)workbook, request, response);
//	}
//	
//	protected void buildExcelDocument(Map<String, Object> model, XSSFWorkbook workbook, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		response.reset();
//
//		String excelGubn = (String) model.get("excelGubn"); // 생성 구분.
//		String fileName = (String) model.get("fileName");
//
//		
//
//		// 임시로 파일명을 날자로 준다.
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
//		fileName = fileName + date.format(cal.getTime()) + ".xlsx";
//		String excelName = URLEncoder.encode(fileName, "UTF-8"); // 생성될 파일명
//
//		XSSFSheet sht = null;
//		XSSFRow row = null;
//		XSSFCell cell = null;
//
//		
//		// 제목 줄 생성
//		String[] title1 = null;
//
//		@SuppressWarnings("unused")
//		String[] title2 = null;
//		@SuppressWarnings("unused")
//		String[][] titleGroup = null;
//		@SuppressWarnings("unused")
//		String[][] gbTitle = null;
//		@SuppressWarnings("unused")
//		String[] shtNameGroup = null;
//		int[] cellwidth = null;
//		@SuppressWarnings("unused")
//		int[][] cellwidthGroup = null;
//
//		// 접근 경로에 따라 제목 정의.
//		if (excelGubn.equals("eqList")) {
//			title1 = new String[] { "IMEI", "회선번호", "MAC ID", "장비 모델", "장비 종류", "상태", "보유여부", "도입일", "변경일"};
//			cellwidth = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20 };
//		} 
//
//		
//		
//		sht = workbook.createSheet(excelName + " WorkSheet");
//
//		//sht.setGridsPrinted(true);
//		sht.setFitToPage(true);
//		sht.setDisplayGuts(true);
//
//		// 쉬트 이름 주기
//		workbook.setSheetName(0, fileName);
//
//		row = sht.createRow((short) 0);
//		row.setHeight((short) 500); // 칼럼 높이
//		short width = 265;
//
//		for (int i = 0; i < title1.length; i++) {
//
//			sht.setColumnWidth(i, (cellwidth[i] * width)); // Column 넓이 설정
//			cell = row.createCell(i);
//			cell.setCellValue(new XSSFRichTextString(title1[i]));
//
//		}
//
//		// =========== Table Contents ===================
//		
//		
//		  if (excelGubn.equals("eqList")) {
//
//			@SuppressWarnings("unchecked")
//			List<MemberDto> list = (List<MemberDto>) model.get("MemberDto");
//
//			for (int i = 0; i < list.size(); i++) {
//				row = sht.createRow(i + 1);
//				row.setHeight((short) 500); // 칼럼 높이
//
//				cell = row.createCell((0));
//				cell.setCellValue(new XSSFRichTextString(list.get(i).getEmail()));
//
//				cell = row.createCell((1));
//				cell.setCellValue(new XSSFRichTextString(list.get(i).getName()));
//
//				cell = row.createCell((2));
//				cell.setCellValue(new XSSFRichTextString(list.get(i).getNickName()));
//				
//				cell = row.createCell((3));
//				cell.setCellValue(new XSSFRichTextString(list.get(i).getBirth()));
//
//				cell = row.createCell((4));
//				cell.setCellValue(new XSSFRichTextString(list.get(i).getSex()));																
//				
//			}
//		
//		}
//
//		// =====================================================
//
//		// 출력설정
//		XSSFPrintSetup hps = sht.getPrintSetup();
//		// 용지설정
//		hps.setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
//		// 출력방향설정
//		hps.setLandscape(false);
//		// 출력비율설정
//		hps.setScale((short) 100);
//		// footer에 페이지번호 설정
//		Footer footer = sht.getFooter();
//		footer.setCenter(HSSFFooter.page() + "/" + HSSFFooter.numPages());
//
//		// 쉬트 여백 설정
//		sht.setMargin(XSSFSheet.TopMargin, 0.6);
//		sht.setMargin(XSSFSheet.BottomMargin, 0.6);
//		sht.setMargin(XSSFSheet.LeftMargin, 0.6);
//		sht.setMargin(XSSFSheet.RightMargin, 0.6);
//
//		// 반복행 설정
//		// wb.setRepeatingRowsAndColumns(0, 0, 3, 0, 0);
//
//		// 직접 다운로드 response정의.
//		response.setContentType("Application/Msexcel");
//		response.setHeader("Content-Disposition", "ATTachment; Filename=" + excelName + ";");
//
//	}
//
//	/**
//	 *
//	 * 기본 타이틀 스타일.
//	 *
//	 * @파라미터 :
//	 * @파라미터 :
//	 * @return : XSSFCellStyle
//	 * @exception :
//	 */
//	@SuppressWarnings("unused")
//	private static XSSFCellStyle getTitleStyle(XSSFWorkbook wb) {
//		// 제목 폰트
//		XSSFFont hf = wb.createFont();
//		hf.setFontHeightInPoints((short) 8);
//		hf.setColor((short) IndexedColors.BLACK.getIndex());
//		hf.setBold(true);
//
//		// Header style setting
//		XSSFCellStyle hcs = wb.createCellStyle();
//		hcs.setFont(hf);
//		hcs.setAlignment(HorizontalAlignment.CENTER);
//
//		// set border style
//		hcs.setBorderBottom(BorderStyle.THIN);
//		hcs.setBorderRight(BorderStyle.THIN);
//		hcs.setBorderLeft(BorderStyle.THIN);
//		hcs.setBorderTop(BorderStyle.THIN);
//		hcs.setBorderBottom(BorderStyle.THIN);
//
//		// set color
//		hcs.setFillBackgroundColor((short) IndexedColors.WHITE.getIndex());
//		hcs.setFillForegroundColor((short) IndexedColors.GREY_25_PERCENT.getIndex());
//		hcs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		hcs.setLocked(true);
//		hcs.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		return hcs;
//	}
//
//	/**
//	 *
//	 * 셀 스타일.
//	 *
//	 * @파라미터 : XSSFWorkbook
//	 * @파라미터 : short
//	 * @파라미터 : String
//	 * @return : XSSFCellStyle
//	 * @exception :
//	 */
//	@SuppressWarnings("unused")
//	private static XSSFCellStyle getTextStyle(XSSFWorkbook wb, short format, String align) {
//		XSSFFont hf = wb.createFont();
//		hf.setFontHeightInPoints((short) 8);
//		hf.setColor((short) IndexedColors.BLACK.getIndex());
//
//		XSSFCellStyle hcs = wb.createCellStyle();
//		hcs.setFont(hf);
//		hcs.setAlignment(HorizontalAlignment.CENTER);
//
//		// set border style
//		hcs.setBorderBottom(BorderStyle.THIN);
//		hcs.setBorderRight(BorderStyle.THIN);
//		hcs.setBorderLeft(BorderStyle.THIN);
//		hcs.setBorderTop(BorderStyle.THIN);
//		hcs.setBorderBottom(BorderStyle.THIN);
//		hcs.setDataFormat((short) format);
//		// set color
//		hcs.setFillBackgroundColor((short) IndexedColors.WHITE.getIndex());
//		hcs.setFillForegroundColor((short) IndexedColors.WHITE.getIndex());
//		hcs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		hcs.setLocked(true);
//		hcs.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		if (align.equals("center"))
//			hcs.setAlignment(HorizontalAlignment.CENTER);
//		if (align.equals("left"))
//			hcs.setAlignment(HorizontalAlignment.LEFT);
//		if (align.equals("right"))
//			hcs.setAlignment(HorizontalAlignment.RIGHT);
//		return hcs;
//	}
//
//	/**
//	 *	
//	 * 기본 셀 스타일.
//	 *
//	 * @파라미터 : XSSFWorkbook
//	 * @파라미터 :
//	 * @return : XSSFCellStyle
//	 * @exception :
//	 */
//	@SuppressWarnings("unused")
//	private static XSSFCellStyle getTextStyle(XSSFWorkbook wb) {
//		XSSFFont hf = wb.createFont();
//		hf.setFontHeightInPoints((short) 8);
//		hf.setColor((short) IndexedColors.BLACK.getIndex());
//
//		XSSFCellStyle hcs = wb.createCellStyle();
//		hcs.setFont(hf);
//		hcs.setAlignment(HorizontalAlignment.CENTER);
//
//		// set border style
//		hcs.setBorderBottom(BorderStyle.THIN);
//		hcs.setBorderRight(BorderStyle.THIN);
//		hcs.setBorderLeft(BorderStyle.THIN);
//		hcs.setBorderTop(BorderStyle.THIN);
//		hcs.setBorderBottom(BorderStyle.THIN);
//		hcs.setDataFormat((short) 0x31);
//		// set color
//		hcs.setFillBackgroundColor((short) IndexedColors.WHITE.getIndex());
//		hcs.setFillForegroundColor((short) IndexedColors.WHITE.getIndex());
//		hcs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		hcs.setLocked(true);
//		hcs.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		return hcs;
//	}
//
//
//}
//
