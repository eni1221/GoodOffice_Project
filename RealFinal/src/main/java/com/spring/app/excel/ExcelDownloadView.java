package com.spring.app.excel;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

/** 
* @FileName  : ExcelDownloadView.java 
* @Project   : TempFinal 
* @Date      : 2024. 1. 1 
* @작성자      : 신예진 (yejjinny) 
* @변경이력	 : 
* @프로그램설명	 : 
*/

/* 
  AbstractView 를 이용하여 파일다운로드를 구현한다.
   즉, AbstractView 를 상속받아 파일다운로드를 처리해주는 뷰로 사용될 클래스를 만들어 준다. 
*/
public class ExcelDownloadView extends AbstractView {
	
	@Override
	protected void renderMergedOutputModel(	Map<String, Object> model, 
											HttpServletRequest request,
											HttpServletResponse response) throws Exception { 
		
		Locale locale = (Locale) model.get("locale");             // 넘겨받은 모델(다운로드되어질 엑셀파일의 정보)
        // Locale 클래스는 지역의 [언어][나라] 등의 정보를 담고 있는 클래스이다. 
		
		String workbookName = (String) model.get("workbookName"); // 넘겨받은 모델(다운로드되어질 엑셀파일의 정보)
		
		// 엑셀파일로 다운로드시 다운로드 되어지는 파일 이름의 중복을 피하기 위해 현재시간을 이용해서 파일 이름에 추가하도록 한다.
        java.util.Date now = new java.util.Date();
        SimpleDateFormat dayformat = new SimpleDateFormat("yyyyMMdd", locale);
        SimpleDateFormat hourformat = new SimpleDateFormat("hhmmss", locale);
        String day = dayformat.format(now);
        String hour = hourformat.format(now);
        String fileName = workbookName + "_" + day + "_" + hour + ".xlsx";         
        
        // === 여기서부터는 각 브라우저에 따른 파일이름 인코딩작업이다. 시작 === //
        String browser = request.getHeader("User-Agent");
        if (browser.indexOf("MSIE") > -1) {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.indexOf("Trident") > -1) {       // IE11
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.indexOf("Firefox") > -1) {
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.indexOf("Opera") > -1) {
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.indexOf("Chrome") > -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < fileName.length(); i++) {
               char c = fileName.charAt(i);
               if (c > '~') {
                     sb.append(URLEncoder.encode("" + c, "UTF-8"));
                       } else {
                             sb.append(c);
                       }
                }
                fileName = sb.toString();
        } else if (browser.indexOf("Safari") > -1){
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
        } else {
             fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
        }
        // === 여기서부터는 각 브라우저에 따른 파일이름 인코딩작업이다. 끝 === //
        
        response.setContentType("application/download;charset=utf-8");
        // 임의의 파일 다운로드 형식은 utf-8 문자코드로 사용한다.
        
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        // 파일 다운로드시 다운로드 되어질 파일명은 첨부된 파일명으로 지정하는 헤더 속성이다.
        
        response.setHeader("Content-Transfer-Encoding", "binary");
        // 다운로드 되어지는 파일이 이진파일일 경우 사용해야하는 헤더 속성이다.
        
        OutputStream ost = null;
        SXSSFWorkbook workbook = null;
       
        try {
            workbook = (SXSSFWorkbook) model.get("workbook");
            ost = response.getOutputStream();
            
            // 엑셀파일생성 
            workbook.write(ost); 
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if(workbook != null) {
               try {
                   workbook.close(); 
                   // 엑셀파일생성에 사용했던 SXSSFWorkbook workbook 은 역할을 다했으므로 메모리 관리를 위해 close로 메모리상에서 삭제하도록 한다. 
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           
           if(ost != null) {
               try {
                   ost.close();
                   // 엑셀파일생성에 사용했던 OutputStream ost 는 역할을 다했으므로 메모리 관리를 위해 close로 메모리상에서 삭제하도록 한다. 
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }
        
	}
}
