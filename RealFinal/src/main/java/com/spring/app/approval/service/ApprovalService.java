
package com.spring.app.approval.service;

import java.util.List;
import java.util.Map;

import com.spring.app.approval.domain.AdminHistoryVO;
import com.spring.app.approval.domain.ApprovalDetailVO;
import com.spring.app.approval.domain.ApprovalFileVO;
import com.spring.app.approval.domain.ApprovalOpinionVO;
import com.spring.app.approval.domain.ApprovalProcedureVO;
import com.spring.app.approval.domain.ApprovalVO;
import com.spring.app.approval.domain.BatchVO;
import com.spring.app.approval.domain.DayOffVO;
import com.spring.app.approval.domain.EmpProofDetailVO;
import com.spring.app.approval.domain.FormVO;
import com.spring.app.approval.domain.ModifyWorkRequestVO;
import com.spring.app.approval.domain.SecurityVO;
import com.spring.app.approval.domain.WorkApplicationVO;
import com.spring.app.common.domain.AdminVO;
import com.spring.app.common.domain.EmployeeVO;

/** 
* @FileName  : ApprovalService.java 
* @Project   : TempFinal 
* @Date      : 2023. 12. 5 
* @작성자      : 신예진 (yejjinny)
* @변경이력	 : 
* @프로그램설명	 : 
*/
public interface ApprovalService {
	/** 
	* @Method Name  : getApprovalTempBox_withSearchAndPaging 
	* @작성일   : 2023. 12. 11 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 문서함_임시저장 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalTempBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getSecurityLevelList 
	* @작성일   : 2023. 12. 12 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_기본 설정_보안 등급별 열람 설정을 하기 위해 기존 보안 등급별 열람 정보를 가져온다
	* @return 
	*/
	public List<SecurityVO> getSecurityLevelDetailList();

	/** 
	* @Method Name  : getSecurityLevelList 
	* @작성일   : 2023. 12. 12 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_기본 설정_보안 등급별 열람 설정을 하기 위해 보안등급 정보를 가져온다
	* @return 
	*/
	public List<SecurityVO> getSecurityLevelList();

	/** 
	* @Method Name  : setSecurityLevel 
	* @작성일   : 2023. 12. 12 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_기본 설정_보안 등급 수정
	* @param paraMap
	* @return 
	*/
	public boolean setSecurityLevel(Map<String, String> paraMap);

	/** 
	 * @param searchWord 
	* @Method Name  : getFormList 
	* @작성일   : 2023. 12. 12 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_양식함 관리 메인 화면
	* @return 
	*/
	public List<FormVO> getFormList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalFormList 
	* @작성일   : 2023. 12. 12 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_양식함 관리_양식 갯수 취득
	* @return 
	*/
	public int getTotalCountApprovalFormList(String searchWord);

	/** 
	* @Method Name  : getDocumentAllList 
	* @작성일   : 2023. 12. 13 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_전체 문서 목록
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getDocumentAllList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountDocumentAllList 
	* @작성일   : 2023. 12. 13 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_전체 문서 목록 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountDocumentAllList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountDocumentDeleteList 
	* @작성일   : 2023. 12. 13 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_삭제 문서 목록 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountDocumentDeleteList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getDocumentDeleteList_withSearchAndPaging 
	* @작성일   : 2023. 12. 13 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_삭제 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getDocumentDeleteList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getAdminList 
	* @작성일   : 2023. 12. 13 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_전자결재 관리자 목록 취득
	* @return 
	*/
	public List<AdminVO> getAdminList();

	/** 
	* @Method Name  : getApprovalDocumentView 
	* @작성일   : 2023. 12. 16 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기
	* @param paraMap
	* @return 
	*/
	public ApprovalDetailVO getApprovalDocumentView(Map<String, Long> paraMap);

	/** 
	* @Method Name  : getApprovalOpinion 
	* @작성일   : 2023. 12. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재 상세의 의견
	* @param approvalId
	* @return 
	*/
	public List<ApprovalOpinionVO> getApprovalOpinionList(Long approvalId);

	/** 
	 * @param empId 
	 * @param approvalId 
	* @Method Name  : isDraftEmp 
	* @작성일   : 2023. 12. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 기안 혹은 신청자인지 확인
	* @return 
	*/
	public int isDraftEmp(Map<String, Long> paraMap);

	/** 
	* @Method Name  : updateApprovalSecurity 
	* @작성일   : 2023. 12. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_보안 등급 수정
	* @param paraMap
	* @return 
	*/
	public int updateApprovalSecurity(Map<String, Long> paraMap);

	/** 
	* @Method Name  : searchEmpName 
	* @작성일   : 2023. 12. 19 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_사원명 검색
	* @param empName
	* @return 
	*/
	public List<EmployeeVO> searchEmpName(String empName);

	/** 
	* @Method Name  : updateApprovalLineSetting 
	* @작성일   : 2023. 12. 19 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_결재선 수정
	* @param updateList
	 * @param approvalId 
	* @return 
	*/
	public boolean updateApprovalLineSetting(List<ApprovalProcedureVO> updateList, Long approvalId, Long procedureType);
	
	/** 
	* @Method Name  : getProcedureTypeApproval 
	* @작성일   : 2023. 12. 19 
	* @작성자   : 신예진 (yejjinny)
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_절차 취득
	* @param approvalId
	* @return 
	*/
	public List<ApprovalProcedureVO> getProcedureTypeApproval(Long approvalId);

	/** 
	* @Method Name  : addRef 
	* @작성일   : 2023. 12. 20 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_참조, 수신참조, 수신 추가
	* @param refType
	* @param empId
	 * @param approvalId 
	* @return 
	*/
	public boolean addRef(String refType, Long empId, Long approvalId);

	/** 
	* @Method Name  : userProcedureType 
	* @작성일   : 2023. 12. 20 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_유저의 절차 타입 취득
	* @param paraMap
	* @return 
	*/
	public int getUserProcedureType(Map<String, Long> paraMap);

	/** 
	* @Method Name  : delRef 
	* @작성일   : 2023. 12. 20 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_참조, 수신참조, 수신 삭제
	* @param refType
	* @param empId
	* @param approvalId
	* @return 
	*/
	public boolean delRef(Long empId, Long approvalId);

	/** 
	* @Method Name  : insertOrUpdateApprovalFile 
	* @작성일   : 2023. 12. 21 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_파일 추가 혹은 업데이트
	* @param parameter
	* @param afList
	* @return 
	*/
	public boolean insertOrUpdateApprovalFile(String approvalId, List<ApprovalFileVO> afList);

	/** 
	* @Method Name  : deleteSavedFile 
	* @작성일   : 2023. 12. 22 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_저장한 파일 삭제
	* @param fileId
	* @return 
	*/
	public boolean deleteSavedFile(Long fileId);

	/** 
	* @Method Name  : getApprovalDocumentFile 
	* @작성일   : 2023. 12. 22 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_파일 상세
	* @param fileId
	* @return 
	*/
	public ApprovalFileVO getApprovalDocumentFile(Long fileId);

	/** 
	* @Method Name  : insertOpinion 
	* @작성일   : 2023. 12. 22 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_의견 등록
	* @param approvalId
	* @param opinion
	* @return 
	*/
	public boolean insertOpinion(Map<String, String> paraMap);

	/** 
	* @Method Name  : deleteOpinion 
	* @작성일   : 2023. 12. 22 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_의견 삭제
	* @param opinionId
	* @return 
	*/
	public boolean deleteOpinion(Long opinionId);

	/** 
	* @Method Name  : isReturn 
	* @작성일   : 2023. 12. 23 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 반려 여부 확인
	* @param paraMap
	* @return 
	*/
	public boolean isReturn(Long approvalId);

	/** 
	* @Method Name  : deleteImportant 
	* @작성일   : 2023. 12. 23 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_중요 삭제
	* @param paraMap
	* @return 
	*/
	public boolean deleteImportant(Map<String, Long> paraMap);

	/** 
	* @Method Name  : insertImportant 
	* @작성일   : 2023. 12. 23 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_중요 등록
	* @param paraMap
	* @return 
	*/
	public boolean insertImportant(Map<String, Long> paraMap);

	/** 
	* @Method Name  : updateActionOfApproval 
	* @작성일   : 2023. 12. 23 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_결재하기
	* @param paraMap
	* @return 
	*/
	public boolean updateActionOfApproval(Map<String, String> paraMap);

	/** 
	* @Method Name  : updateRefRead 
	* @작성일   : 2023. 12. 23 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_읽음처리하기
	* @param paraMap
	* @return 
	*/
	public boolean updateRefRead(Map<String, Long> paraMap);

	/** 
	* @Method Name  : getEmpProofDetail 
	* @작성일   : 2023. 12. 24 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_재직증명서 취득
	* @param paraMap
	* @return 
	*/
	public EmpProofDetailVO getEmpProofDetail(Map<String, Long> paraMap);


	/** 
	* @Method Name  : updateRoundRobinApprovalLineSetting 
	* @작성일   : 2023. 12. 26 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서_상세보기_품의서 결재선 수정
	* @param updateList
	 * @param procedureType 
	* @param long1
	* @return 
	*/
	public boolean updateRoundRobinApprovalLineSetting(List<ApprovalProcedureVO> updateList, Long approvalId, Long procedureType);


	/** 
	* @Method Name  : getProcedureTypeAgree 
	* @작성일   : 2023. 12. 26 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_합의 +버튼_확인 눌렀을 경우_기존에 있던 거인지, 추가한거인지 확인하기 위한 기존 값 리스트
	* @param paraMap
	* @return 
	*/
	public List<ApprovalProcedureVO> getProcedureTypeAgree(Long approvalId);

	/** 
	* @Method Name  : getWorkApplicationDetail 
	* @작성일   : 2023. 12. 27 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_연장근무 혹은 휴일근무 신청
	* @param approvalId
	* @return 
	*/
	public WorkApplicationVO getWorkApplicationDetail(Long approvalId);

	/** 
	* @Method Name  : getFormNameList 
	* @작성일   : 2023. 12. 27 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_목록화면에서 문서 드롭다운 리스트에 보여줄 문서 종류 리스트
	* @return 
	*/
	public List<FormVO> getFormNameList();

	/** 
	* @Method Name  : getDayOffDetail 
	* @작성일   : 2023. 12. 27 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_휴가 신청
	* @param approvalId
	* @return 
	*/
	public DayOffVO getDayOffDetail(Long approvalId);

	/** 
	* @Method Name  : getModifyWorkRequest 
	* @작성일   : 2023. 12. 28 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_근무체크 수정 정보
	* @param approvalId
	* @return 
	*/
	public ModifyWorkRequestVO getModifyWorkRequest(Long approvalId);

	/** 
	* @Method Name  : updateReadReturn 
	* @작성일   : 2023. 12. 29 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_반려 읽었을 경우 읽음처리
	* @param paraMap
	* @return 
	*/
	public int updateReadReturn(Map<String, Long> paraMap);

	/** 
	* @Method Name  : cancleApproval 
	* @작성일   : 2023. 12. 29 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_기안 취소
	* @param paraMap
	* @return 
	*/
	public boolean cancleApproval(Map<String, Long> paraMap);

	/** 
	* @Method Name  : getPreservationYear 
	* @작성일   : 2023. 12. 29 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_작성하기 _문서종류에 따른 보존연한 취득
	* @param formId
	* @return 
	*/
	public int getPreservationYear(Long formId);

	/** 
	* @Method Name  : insertDocumentWrite 
	* @작성일   : 2023. 12. 30 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_작성하기
	* @param paraArrMap
	* @param paraMap
	* @return 
	*/
	public String insertDocumentWrite(Map<String, String[]> paraArrMap, Map<String, String> paraMap);

	/** 
	* @Method Name  : insertTempDocumentWrite 
	* @작성일   : 2023. 12. 30 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_임시저장하기
	* @param paraArrMap
	* @param paraMap
	* @return 
	*/
	public String insertTempDocumentWrite(Map<String, String[]> paraArrMap, Map<String, String> paraMap);

	/** 
	* @Method Name  : getFormId 
	* @작성일   : 2024. 1. 4 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_작성하기 _formId 취득
	* @param approvalId
	* @return 
	*/
	public Long getFormId(Long approvalId);

	/** 
	* @Method Name  : batchApproval 
	* @작성일   : 2024. 1. 5 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_일괄 결재
	* @param bvo
	* @return 
	*/
	public int batchApproval(BatchVO bvo);

	/** 
	* @Method Name  : batchCheck 
	* @작성일   : 2024. 1. 5 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_일괄 확인
	* @param bvo
	* @return 
	*/
	public int batchCheck(BatchVO bvo);

	/** 
	* @Method Name  : batchDelete 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_일괄 삭제
	* @param bvo
	* @return 
	*/
	public int batchDelete(BatchVO bvo);

	/** 
	* @Method Name  : batchRestore 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_일괄 복원
	* @param bvo
	* @return 
	*/
	public int batchRestore(BatchVO bvo);

	/** 
	* @Method Name  : addAppovalAdminManager 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자설정 _관리자 추가
	* @param paraMap
	* @return 
	*/
	public boolean addAppovalAdminManager(Map<String, Long> paraMap);

	/** 
	* @Method Name  : deleteAppovalAdminManager 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자설정 _관리자 삭제
	* @param paraMap
	* @return 
	*/
	public boolean deleteAppovalAdminManager(Map<String, Long> paraMap);

	/** 
	* @Method Name  : grantAdminRead 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자설정 _관리자 전체문서 열람 허용 비허용 설정 
	* @param paraMap
	* @return 
	*/
	public boolean grantAdminRead(Map<String, Long> paraMap);

	/** 
	* @Method Name  : getAdminHistoryList 
	* @작성일   : 2024. 1. 6 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자설정 _관리자 설정 이력
	* @return 
	*/
	public List<AdminHistoryVO> getAdminHistoryList();

	/** 
	* @Method Name  : getFormDetail 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_양식함 관리_양식 상세
	* @param formId
	* @return 
	*/
	public FormVO getFormDetail(Long formId);

	/** 
	* @Method Name  : getFormNameListByWrite 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서 작성시 드롭다운 리스트에 보여줄 문서 종류 리스트
	* @return 
	*/
	public List<FormVO> getFormNameListByWrite();

	/** 
	* @Method Name  : updateForm 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_양식함 관리_양식 상세_수정
	* @param paraMap
	* @return 
	*/
	public boolean updateForm(Map<String, String> paraMap);

	/** 
	 * @param paraMap 
	* @Method Name  : isReadAble 
	* @작성일   : 2024. 1. 11 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서 상세보기_열람 허용 여부
	* @return 
	*/
	public boolean isReadAble(Map<String, Long> paraMap);

	/** 
	* @Method Name  : isAlreadyAdmin 
	* @작성일   : 2024. 1. 11 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_관리자 설정_관리자 추가 전 다른 기능 관리자인지 체크
	* @param empId
	* @return 
	*/
	public boolean isAlreadyAdmin(Long empId);

	/** 
	* @Method Name  : isExistApproval 
	* @작성일   : 2024. 1. 11 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 전자결재_상세보기_존재하는 전자결재 문서인지 확인
	* @param paraMap
	* @return 
	*/
	public boolean isExistApproval(Map<String, Long> paraMap);

	
	
	
	
	
	
	
	
	
	
	
	
	/** 
	* @Method Name  : getApprovalProgressList_withSearchAndPaging 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_진행 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalProgressList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalScheduleList_withSearchAndPaging 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_예정 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalScheduleList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalCheckList_withSearchAndPaging 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_확인 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalCheckList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalWaitingList_withSearchAndPaging 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_대기 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalWaitingList_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalAllIngList_withSearchAndPaging 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_전체 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalAllIngList_withSearchAndPaging(Map<String, String> paraMap);

	
	
	
	
	
	
	
	
	/** 
	* @Method Name  : getTotalCountApprovalAllIngList 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_전체 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalAllIngList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalWaitingList 
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_대기 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalWaitingList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalCheckList
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_확인 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalCheckList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalScheduleList
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_예정 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalScheduleList(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalProgressList
	* @작성일   : 2024. 1. 19 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_진행중인 문서_진행 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalProgressList(Map<String, String> paraMap);

	
	
	
	
	
	
	
	
	
	
	
	/** 
	* @Method Name  : getApprovalAllBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_전체 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalAllBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalWriterBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_기안/신청 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalWriterBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalApprovalBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_결재/처리/합의/재무합의 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalApprovalBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalReferBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_참조 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalReferBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalReadBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_수신/수신참조 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalReadBox_withSearchAndPaging(Map<String, String> paraMap);

	/** 
	* @Method Name  : getApprovalReturnBox_withSearchAndPaging 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_반려 문서 목록 취득
	* @param paraMap
	* @return 
	*/
	public List<ApprovalVO> getApprovalReturnBox_withSearchAndPaging(Map<String, String> paraMap);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 
	* @Method Name  : getTotalCountApprovalAllBox 
	* @작성일   : 2024. 1. 20 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_전체 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalAllBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalWriterBox
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_기안/신청 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalWriterBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalApprovalBox
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_결재/처리/합의/재무합의 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalApprovalBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalReferBox
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_참조 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalReferBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalReadBox
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_수신/수신참조 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalReadBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalReturnBox
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_반려 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalReturnBox(Map<String, String> paraMap);

	/** 
	* @Method Name  : getTotalCountApprovalTempBox 
	* @작성일   : 2024. 1. 21 
	* @작성자   : 신예진 (yejjinny)   
	* @변경이력  : 
	* @Method 설명 : 전자결재_문서함_임시문서 문서 총 갯수 취득
	* @param paraMap
	* @return 
	*/
	public int getTotalCountApprovalTempBox(Map<String, String> paraMap);


}
