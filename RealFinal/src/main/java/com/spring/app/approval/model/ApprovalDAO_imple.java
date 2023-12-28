/** 
* @FileName  : ApprovalDAO_imple.java 
* @Project   : TempFinal 
* @Date      : 2023. 12. 5 
* @작성자      : syxzi 
* @변경이력	 : 
* @프로그램설명	 : 
*/
package com.spring.app.approval.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.approval.domain.AdminVO;
import com.spring.app.approval.domain.ApprovalDetailVO;
import com.spring.app.approval.domain.ApprovalFileVO;
import com.spring.app.approval.domain.ApprovalOpinionVO;
import com.spring.app.approval.domain.ApprovalProcedureVO;
import com.spring.app.approval.domain.ApprovalVO;
import com.spring.app.approval.domain.DayOffVO;
import com.spring.app.approval.domain.EmpProofDetailVO;
import com.spring.app.approval.domain.FormVO;
import com.spring.app.approval.domain.ModifyDetailVO;
import com.spring.app.approval.domain.ModifyWorkRequestVO;
import com.spring.app.approval.domain.SecurityVO;
import com.spring.app.approval.domain.WorkApplicationVO;
import com.spring.app.common.domain.EmployeeVO;

import sun.print.resources.serviceui;

/**
 *   @FileName  : ApprovalDAO_imple.java 
 * 
 * @Project   : TempFinal 
 * @Date      : 2023. 12. 5 
 * @작성자      : 신예진 
 * @변경이력 : 
 * @프로그램설명 : 
 */

@Repository
public class ApprovalDAO_imple implements ApprovalDAO {

	@Resource
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ApprovalVO> getApprovalAllIngList_withSearchAndPaging(Map<String, String> paraMap) {
		return sqlSession.selectList("approval.getApprovalAllIngList_withSearchAndPaging", paraMap);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public int hasReturn(Long fk_approvalId) {
		// 전자결재절차 테이블에서 해당 전자결재id 반려 존재여부
		return sqlSession.selectOne("approval.hasReturn", fk_approvalId);
	}

	@Override
	public int hasLowerApplicantAllAccept(Map<String, Long> paramMap) {
		// 유저보다 하위 신청 or 기안자들이 전원 승인하였는지 확인용 (0 : 전원 승인)
		return sqlSession.selectOne("approval.hasLowerApplicantAllAccept", paramMap);
	}

	@Override
	public int hasLowerApplicantWait(Map<String, Long> paramMap) {
		// 유저보다 하위 신청 or 기안자들 중에 대기가 있는지 확인 (0 : 대기 없음)
		return sqlSession.selectOne("approval.hasLowerApplicantWait", paramMap);
	}

	@Override
	public int hasUpperApplicantAndApproverWait(Map<String, Long> paramMap) {
		// 상위 신청 or 기안자 및 결재자 중에 대기가 있을 경우
		return sqlSession.selectOne("approval.hasUpperApplicantAndApproverWait", paramMap);
	}

	@Override
	public int hasLowerApplicantAndApproverAllAccept(Map<String, Long> paramMap) {
		// 유저보다 하위의 신청 or 기안자 및 결재 or 처리자가 다 승인했을 경우 (0:전원 승인)
		return sqlSession.selectOne("approval.hasLowerApplicantAndApproverAllAccept", paramMap);
	}

	@Override
	public int hasLowerApplicantAndApproverWait(Map<String, Long> paramMap) {
		// 유저보다 하위의 신청 or 기안자 및 결재 or 처리자 중에 대기가 있을 경우
		return sqlSession.selectOne("approval.hasLowerApplicantAndApproverWait", paramMap);
	}

	@Override
	public int hasUpperApproverWait(Map<String, Long> paramMap) {
		// 유저보다 상위의 신청 or 기안자 및 결재 or 처리자 중에 대기가 있을 경우
		return sqlSession.selectOne("approval.hasUpperApproverWait", paramMap);
	}

	@Override
	public int hasApplicantAndApproverAllAccept(Map<String, Long> paramMap) {
		// 결재자와 신청자가 모두 승인했는지 확인한다 (0: 전원 승인, 그 외의 경우 모두 승인하기 전)
		return sqlSession.selectOne("approval.hasApplicantAndApproverAllAccept", paramMap);
	}

	@Override
	public int hasUnderRankerAllAccept(Map<String, Long> paramMap) {
		// 하위 순서가 모두 승인하였을 경우 (0: 전원 승인)
		return sqlSession.selectOne("approval.hasUnderRankerAllAccept", paramMap);
	}

	@Override
	public int hasUnderRankerWait(Map<String, Long> paramMap) {
		// 하위 순서 중에 대기가 있을 경우
		return sqlSession.selectOne("approval.hasUnderRankerWait", paramMap);
	}

	@Override
	public int hasUpperRankerWait(Map<String, Long> paramMap) {
		// 상위 순서 중에 대기가 있을 경우
		return sqlSession.selectOne("approval.hasUpperRankerWait", paramMap);
	}














	@Override
	public List<ApprovalVO> getApprovalAllBox_withViewAllAndSearchAndPaging(Map<String, String> paraMap) {
		// 문서함 
		return sqlSession.selectList("approval.getApprovalAllBox_withViewAllAndSearchAndPaging", paraMap);
	}
	
	@Override
	public List<ApprovalVO> getApprovalAllBox_withSearchAndPaging(Map<String, String> paraMap) {
		return sqlSession.selectList("approval.getApprovalAllBox_withSearchAndPaging", paraMap);
	}


	@Override
	public int hasAllAccept(Map<String, Long> paramMap) {
		// 전원 승인했는지 확인
		return sqlSession.selectOne("approval.hasAllAccept", paramMap);
	}


	@Override
	public List<ApprovalVO> getApprovalTempBox_withSearchAndPaging(Map<String, String> paraMap) {
		// 임시저장함
		return sqlSession.selectList("approval.getApprovalTempBox_withSearchAndPaging", paraMap);
	}














	@Override
	public List<SecurityVO> getSecurityLevelDetailList() {
		// 전자결재_관리자 설정_기본 설정_보안 등급별 열람 설정을 하기 위해 기존 보안 등급별 열람 정보를 가져온다
		return sqlSession.selectList("approval.getSecurityLevelDetailList");
	}

	@Override
	public List<SecurityVO> getSecurityLevelList() {
		// 전자결재_관리자 설정_기본 설정_보안 등급별 열람 설정을 하기 위해 보안등급 정보를 가져온다
		return sqlSession.selectList("approval.getSecurityLevelList");
	}














	@Override
	public int setSecurityLevelA(String level) {
		// 전자결재_관리자 설정_기본 설정_보안 등급 설정 A등급
		return sqlSession.insert("approval.setSecurityLevelA", level);
	}

	@Override
	public int setSecurityLevelB(String level) {
		// 전자결재_관리자 설정_기본 설정_보안 등급 B등급
		return sqlSession.insert("approval.setSecurityLevelB", level);
	}



//////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<FormVO> getFormList(Map<String, String> paraMap) {
		// 전자결재_관리자 설정_양식함 관리_메인화면
		return sqlSession.selectList("approval.getFormList", paraMap);
	}

	@Override
	public int getTotalCountApprovalFormList(String searchWord) {
		// 전자결재_관리자 설정_양식함 관리_메인화면
		return sqlSession.selectOne("approval.getTotalCountApprovalFormList", searchWord);
	}












	////////////////////////////////////////////////////////////////////////////////////////////////




	@Override
	public List<ApprovalVO> getDocumentAllList_withSearchAndPaging(Map<String, String> paraMap) {
		return sqlSession.selectList("approval.getDocumentAllList_withSearchAndPaging", paraMap);
	}

	@Override
	public int getTotalCountDocumentAllList(Map<String, String> paraMap) {
		return sqlSession.selectOne("approval.getTotalCountDocumentAllList", paraMap);
	}














	@Override
	public int getTotalCountDocumentDeleteList(Map<String, String> paraMap) {
		return sqlSession.selectOne("approval.getTotalCountDocumentDeleteList", paraMap);
	}

	@Override
	public List<ApprovalVO> getDocumentDeleteList_withSearchAndPaging(Map<String, String> paraMap) {
		return sqlSession.selectList("approval.getDocumentDeleteList_withSearchAndPaging", paraMap);
	}














	@Override
	public List<AdminVO> getAdminList() {
		return sqlSession.selectList("approval.getAdminList");
	}














	@Override
	public ApprovalDetailVO getApprovalDocumentView(Map<String, Long> paraMap) {
		return sqlSession.selectOne("approval.getApprovalDocumentView", paraMap);
	}














	@Override
	public List<ApprovalProcedureVO> getApprovalDocumentView_Procedure(Long approvalId) {
		return sqlSession.selectList("approval.getApprovalDocumentView_Procedure", approvalId);
	}














	@Override
	public List<ApprovalFileVO> getApprovalDocumentView_File(Long approvalId) {
		return sqlSession.selectList("approval.getApprovalDocumentView_File", approvalId);
	}




























	@Override
	public List<ApprovalOpinionVO> getApprovalOpinionList(Long approvalId) {
		return sqlSession.selectList("approval.getApprovalOpinionList", approvalId);
	}














	@Override
	public int isDraftEmp(Map<String, Long> paraMap) {
		return sqlSession.selectOne("approval.isDraftEmp", paraMap);
	}














	@Override
	public int updateApprovalSecurity(Map<String, Long> paraMap) {
		return sqlSession.update("approval.updateApprovalSecurity", paraMap);
	}














	@Override
	public List<EmployeeVO> searchEmpName(String empName) {
		return sqlSession.selectList("approval.searchEmpName", empName);
	}











	@Override
	public List<ApprovalProcedureVO> getProcedureTypeApproval(Long approvalId) {
		return sqlSession.selectList("approval.getProcedureTypeApproval", approvalId);
	}


	@Override
	public int updateApprovalLineSetting(Map<String, String> paraMap) {
		
		return sqlSession.update("approval.updateApprovalLineSetting", paraMap);
	}














	@Override
	public int insertApprovalLineSetting(Map<String, String> paraMap) {
		return sqlSession.insert("approval.insertApprovalLineSetting", paraMap);
	}

	@Override
	public int getApprovalProcedureLastSeq(Long approvalId) {
		return sqlSession.selectOne("approval.getApprovalProcedureLastSeq", approvalId);
	}














	@Override
	public int deleteApprovalLineSetting(Map<String, String> paraMap) {
		return sqlSession.delete("approval.deleteApprovalLineSetting", paraMap);
	}














	@Override
	public int addRef(Map<String, String> paraMap) {
		return sqlSession.insert("approval.addRef", paraMap);
	}














	@Override
	public int getUserProcedureType(Map<String, Long> paraMap) {
		return sqlSession.selectOne("approval.getUserProcedureType", paraMap);
	}














	@Override
	public int delRef(Map<String, String> paraMap) {
		return sqlSession.delete("approval.delRef", paraMap);
	}














	@Override
	public int updateApprovalFile(Map<String, String> paraMap) {
		return sqlSession.update("approval.updateApprovalFile", paraMap);
	}


	@Override
	public int deleteApprovalFile(Map<String, String> paraMap) {
		return sqlSession.delete("approval.deleteApprovalFile", paraMap);
	}

	@Override
	public int insertApprovalFile(Map<String, String> paraMap) {
		return sqlSession.insert("approval.insertApprovalFile", paraMap);
	}














	@Override
	public ApprovalFileVO getApprovalDocumentFile(Long fileId) {
		return sqlSession.selectOne("approval.getApprovalDocumentFile", fileId);
	}














	@Override
	public int insertOpinion(Map<String, String> paraMap) {
		return sqlSession.insert("approval.insertOpinion", paraMap);
	}

	@Override
	public int deleteOpinion(Long opinionId) {
		return sqlSession.delete("approval.deleteOpinion", opinionId);
	}














	@Override
	public int deleteImportant(Map<String, Long> paraMap) {
		return sqlSession.delete("approval.deleteImportant", paraMap);
	}

	@Override
	public int insertImportant(Map<String, Long> paraMap) {
		return sqlSession.insert("approval.insertImportant", paraMap);
	}














	@Override
	public int updateActionOfApproval(Map<String, String> paraMap) {
		return sqlSession.update("approval.updateActionOfApproval", paraMap);
	}

	@Override
	public int updateRefRead(Map<String, Long> paraMap) {
		return sqlSession.update("approval.updateRefRead", paraMap);
	}














	@Override
	public int updateApprovalComplete(Map<String, String> paraMap) {
		return sqlSession.update("approval.updateApprovalComplete", paraMap);
	}














	@Override
	public EmpProofDetailVO getEmpProofDetail(Map<String, Long> paraMap) {
		return sqlSession.selectOne("approval.getEmpProofDetail", paraMap);
	}














//	@Override
//	public int insertProcessLineSetting(Map<String, String> paraMap) {
//		return sqlSession.insert("approval.insertProcessLineSetting", paraMap);
//	}














	@Override
	public List<ApprovalProcedureVO> getProcedureTypeApplication(Long approvalId) {
		return sqlSession.selectList("approval.getProcedureTypeApplication", approvalId);
	}


//	@Override
//	public int insertApplicationLineSetting(Map<String, String> paraMap) {
//		return sqlSession.insert("approval.insertApplicationLineSetting", paraMap);
//	}














	@Override
	public List<ApprovalProcedureVO> getProcedureTypeAgree(Long approvalId) {
		return sqlSession.selectList("approval.getProcedureTypeAgree", approvalId);
	}


	@Override
	public List<ApprovalProcedureVO> getProcedureTypeFiAgree(Long approvalId) {
		return sqlSession.selectList("approval.getProcedureTypeFiAgree", approvalId);
	}














	@Override
	public WorkApplicationVO getWorkApplicationDetail(Long approvalId) {
		return sqlSession.selectOne("approval.getWorkApplicationDetail", approvalId);
	}



	@Override
	public List<FormVO> getFormNameList() {
		return sqlSession.selectList("approval.getFormNameList");
	}


	@Override
	public DayOffVO getDayOffDetail(Long approvalId) {
		return sqlSession.selectOne("approval.getDayOffDetail", approvalId);
	}














	@Override
	public ModifyWorkRequestVO getModifyWorkRequest(Long approvalId) {
		return sqlSession.selectOne("approval.getModifyWorkRequest", approvalId);
	}














	@Override
	public int updateWorkHistoryByRequest(ModifyDetailVO mdvo) {
		return sqlSession.update("approval.updateWorkHistoryByRequest", mdvo);
	}

	@Override
	public int insertWorkHistoryByRequest(ModifyDetailVO mdvo) {
		return sqlSession.insert("approval.insertWorkHistoryByRequest", mdvo);
	}

	@Override
	public int deleteWorkHistoryByRequest(ModifyDetailVO mdvo) {
		return sqlSession.delete("approval.deleteWorkHistoryByRequest", mdvo);
	}


























	














	


}