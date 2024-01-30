package com.spring.app.personnel.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.spring.app.approval.domain.AdminHistoryVO;
import com.spring.app.approval.domain.FormVO;
import com.spring.app.common.domain.AdminVO;
import com.spring.app.common.domain.EmployeeVO;
import com.spring.app.personnel.domain.SalaryDetailVO;
import com.spring.app.personnel.domain.SalaryVO;
import com.spring.app.personnel.domain.WorkhistoryVO;

public interface PersonnelService {

	 

	/** 
	* @Method Name  : sysdate_get 
	* @작성일   : 2023. 12. 20 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 현재 날짜 가져오기
	* @return 
	*/
	String sysdate_get();

	 

	 

	/** 
	* @Method Name  : year_hour 
	* @작성일   : 2023. 12. 20 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 올해 총 근무 시간
	* @param paraMapYear
	* @return 
	*/
	String year_hour(Map<String, String> paraMapYear);

	/** 
	* @Method Name  : workhistory_print 
	* @작성일   : 2023. 12. 28 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 현재 근무현황 출력
	* @param paraMap
	* @return 
	*/
	List<WorkhistoryVO> workhistory_print(Map<String, String> paraMap);

	/** 
	* @Method Name  : workhistory_insert 
	* @작성일   : 2023. 12. 29 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 현재 근무현황 추가
	* @param paraMap
	* @return 
	*/
	int workhistory_insert(Map<String, String> paraMap);

	/** 
	* @Method Name  : personnel_search_name
	* @작성일   : 2024. 1. 1 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 사원이름검색
	* @param empName
	* @return 
	*/
	List<EmployeeVO> personnel_search_name(String empName);

	/** 
	* @Method Name  : personnel_selct_empid 
	* @작성일   : 2024. 1. 2 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 사원아이디검색
	* @param empid
	* @return 
	*/
	String personnel_selct_empid(String empid);

	/** 
	* @Method Name  : vaction_cnt 
	* @작성일   : 2024. 1. 4 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 휴가잔여일
	* @param empid
	* @return 
	*/
	String vaction_cnt(String empid);

	/** 
	* @Method Name  : va_approval_insert 
	* @작성일   : 2024. 1. 5 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 휴가신청 전자
	* @param empid
	* @return 
	*/
	int va_approval_insert(String empid);

	/** 
	* @Method Name  : va_dayoff_insert 
	* @작성일   : 2024. 1. 5 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 휴가신청
	* @param paraMap
	* @return 
	*/
	int va_dayoff_insert(Map<String, String> paraMap);

	/** 
	* @Method Name  : procedure_insert 
	* @작성일   : 2024. 1. 5 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 전자 기안순서
	* @param paraMaps
	* @return 
	*/
	int procedure_insert(Map<String, String> paraMaps);

	/** 
	* @Method Name  : tardy_cnt 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 지각
	* @param empid
	* @return 
	*/
	String tardy_cnt(String empid);

	/** 
	* @Method Name  : early_work_cnt 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 조기퇴근
	* @param empid
	* @return 
	*/
	String early_work_cnt(String empid);

	/** 
	* @Method Name  : not_leave_work 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 :  퇴근미체크
	* @param empid
	* @return 
	*/
	String not_leave_work(String empid);

	/** 
	* @Method Name  : workday_cnt 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 :  근무일
	* @param empid
	* @return 
	*/
	String workday_cnt(String empid);

	/** 
	* @Method Name  : absenteeism 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 결근 
	* @param empid
	* @return 
	*/
	String absenteeism(String empid);

	/** 
	* @Method Name  : mowork_approval_insert 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 수정전자
	* @param empid
	* @return 
	*/
	int mowork_approval_insert(String empid);

	/** 
	* @Method Name  : work_modify_add_insert 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무수정신청 추가
	* @param paraMaps
	* @return 
	*/
	int work_modify_add_insert(Map<String, String> paraMaps);

	/** 
	* @Method Name  : work_modify_del_insert 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무수정신청 삭제
	* @param paraMaps
	* @return 
	*/
	int work_modify_del_insert(Map<String, String> paraMaps);

	/** 
	* @Method Name  : work_modify_edit_insert 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무수정신청 수정
	* @param paraMaps
	* @return 
	*/
	int work_modify_edit_insert(Map<String, String> paraMaps);

	/** 
	* @Method Name  : avg_hour 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 평균시간
	* @param paraMapYear
	* @return 
	*/
	String avg_hour(Map<String, String> paraMapYear);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// 예진 코드 시작 -------------------------------------------------------------------------------------
	
	
	/** 
	* @Method Name  : getFormList 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 양식 목록 취득
	* @return 
	*/
	List<FormVO> getFormList();

	/** 
	* @Method Name  : getAdminList 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사관리자 목록 취득
	* @return 
	*/
	List<AdminVO> getAdminList();

	/** 
	* @Method Name  : getAdminHistoryList 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사관리자 설정 이력 취득
	* @return 
	*/
	List<AdminHistoryVO> getAdminHistoryList();

	/** 
	* @Method Name  : isExistPayrollThisMonth 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 해당 달의 급여 대장 존재하는지 확인 존재한다면 그 값을 취득
	* @param month
	* @return 
	*/
	SalaryVO isExistPayrollThisMonth(String month);

	/** 
	* @Method Name  : getSalaryDetail 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여 대장 속 급여 내용 취득
	* @param paraMap
	* @return 
	*/
	List<SalaryDetailVO> getSalaryDetail_withSearch(Map<String, String> paraMap);

	/** 
	* @Method Name  : fixPayroll 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여 대장 확정
	* @param paraMap
	* @return 
	*/
	boolean fixPayroll(Map<String, String> paraMap);

	/** 
	* @Method Name  : deletePayroll 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여 대장 삭제
	* @param salaryId
	* @return 
	*/
	boolean deletePayroll(Long salaryId);

	/** 
	* @Method Name  : insertPayroll 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여 대장 등록
	* @param paraList
	 * @param svo 
	* @return 
	*/
	boolean insertPayroll(List<SalaryDetailVO> paraList, SalaryVO svo);

	/** 
	* @Method Name  : getSalarySequence 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여 대장 등록시 필요한 시퀀스 값 취득
	* @return 
	*/
	Long getSalarySequence();

	/** 
	* @Method Name  : isExistEmployee 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 이미 존재하는 사원인지 확인
	* @param sdvo
	* @return 
	*/
	Long isExistEmployee(SalaryDetailVO sdvo);

	/** 
	* @Method Name  : payrollToExcel 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여대장을 엑셀로 생성
	* @param salaryId
	* @param model 
	*/
	void payrollToExcel(String salaryId, Model model);

	/** 
	* @Method Name  : getSalaryByYear 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여대장을 연도에 맞춰 취득
	* @param paraMap
	* @return 
	*/
	List<SalaryVO> getSalaryByYear(Map<String, String> paraMap);

	/** 
	* @Method Name  : getSalary 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여대장 취득
	* @param salaryId
	* @return 
	*/
	SalaryVO getSalary(String salaryId);

	/** 
	* @Method Name  : getSalaryDetailBySalaryIdAndEmpId 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 급여id와 사원id로 검색해 급여 상세내용을 취득한다
	* @param paraMap
	* @return 
	*/
	SalaryDetailVO getSalaryDetailBySalaryIdAndEmpId(Map<String, String> paraMap);

	/** 
	* @Method Name  : privatePayrollToExcel 
	* @작성일   : 2024. 1. 10 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 개인 급여명세를 엑셀로 생성
	* @param salaryId
	* @param model 
	*/
	void privatePayrollToExcel(Map<String, String> paraMap, Model model);

	
	// 예진 코드 끝    -------------------------------------------------------------------------------------
	 

}
