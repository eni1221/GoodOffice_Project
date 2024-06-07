package com.spring.app.personnel.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.admin.domain.SearchEmployeeVO;
import com.spring.app.approval.domain.AdminHistoryVO;
import com.spring.app.approval.service.ApprovalService;
import com.spring.app.common.FileManager;
import com.spring.app.common.domain.EmployeeVO;
import com.spring.app.personnel.domain.SalaryDetailVO;
import com.spring.app.personnel.domain.SalaryVO;
import com.spring.app.personnel.domain.WorkhistoryVO;
import com.spring.app.personnel.service.PersonnelService;

/**
 *   @FileName  : PersonnelController.java 
 * 
 * @Project   : TempFinal 
 * @Date      : 2023. 12. 10 
 * @작성자      : 윤예인 (eni1221), 신예진 (yejjinny)
 * @변경이력 : 
 * @프로그램설명 : 
 */
@Controller
public class PersonnelController {

	@Autowired
	private PersonnelService service;

	
	// 예진 코드 시작 ---------------------
	@Autowired
	private ApprovalService aService;

	@Autowired 
	private FileManager fileManager;
	// 예진 코드 끝 ----------------------
	
	
	
	 
	 
	/** 
	* @Method Name  : work_modify 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무수정하기
	* @param request
	* @param response
	* @return 
	*/
	@GetMapping("/personnel/work_modify.gw")
	public String work_modify(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		 
		return "work_modify_number_next_MyVacationAndWork.personnel";

	}

	/** 
	* @Method Name  : workstatus_day_select 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무내역 조회
	* @param request
	* @param response
	* @param date
	* @return 
	*/
	@ResponseBody
	@PostMapping("/personnel/workstatus_day_select.gw")
	public String workstatus_day_select(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ,String date) {

		 
		 

		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("date", date);

		List<WorkhistoryVO> workhistoryList = service.workhistory_print(paraMap); // 현재 근무현황 출력

		 
		JSONArray jsonArr = new JSONArray(); // []


		if (workhistoryList != null) {

			for (WorkhistoryVO vo : workhistoryList) {

				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("workhistoryid", vo.getWorkhistoryid());
				jsonObj.put("fk_empid", vo.getFk_empid());

				String type = vo.getWorktype();

				switch (type) {

				case "1":
					type = "업무";
					break;
				case "2":
					type = "외출";
					break;
				case "3":
					type = "회의";
					break;
				case "4":
					type = "외근";
					break;
				case "5":
					type = "퇴근";
					break;
				case "6":
					type = "출근";
					break;

				default:
					break;
				}
				 
				jsonObj.put("worktype", type);

				String time = vo.getResisterdaytime().substring(11, 16);
				 
				jsonObj.put("resisterdaytime", time);

				 
				jsonArr.put(jsonObj);
			} // end of for-----------------------------------
		}
		return jsonArr.toString();

	}

	/**
	 *  
	 * 
	 * @Method Name  : personal_index 
	 * @작성일   : 2023. 12. 10 
	 * @작성자   : 윤예인 (eni1221)  @변경이력  :
	 * @Method 설명 :  인사 인덱스
	 * @param request
	 * @param response @return 
	 */
	@GetMapping("/personnel/personal_index.gw")
	public ModelAndView personal_index(HttpServletRequest request, HttpServletResponse response , ModelAndView mav ) {

		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String sysdate = service.sysdate_get(); // 현재날짜 가져오기
		request.setAttribute("sysdate", sysdate);
		
		request.setAttribute("type", "workside");
		 
		 
		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("sysdate", sysdate);

		 
		request.setAttribute("empid", empid);

		String year = sysdate.substring(0, 4); // 현재 연도

		Map<String, String> paraMapYear = new HashMap<>();
		paraMapYear.put("empid", empid);
		paraMapYear.put("year", year);

		String year_hour = service.year_hour(paraMapYear); //총근무시간

		request.setAttribute("year_hour", year_hour);
		
		String avg_hour = service.avg_hour(paraMapYear); //총근무 분
		
		///////////////////////////////////
		String tardy_cnt = service.tardy_cnt(empid); //지각
		request.setAttribute("tardy_cnt", tardy_cnt);
		
		String early_work_cnt = service.early_work_cnt(empid); //조기퇴근
		request.setAttribute("early_work_cnt", early_work_cnt);
		
		String not_leave_work = service.not_leave_work(empid); //퇴근미체크
		request.setAttribute("not_leave_work", not_leave_work);
		
		String absenteeism = service.absenteeism(empid); //결근
		request.setAttribute("absenteeism", absenteeism);
		
		String workday_cnt = service.workday_cnt(empid); //근무일수
		request.setAttribute("workday_cnt", workday_cnt);
		///
		String avg_str="";
		if(Integer.parseInt(avg_hour)==0 ||Integer.parseInt(workday_cnt)==0) {
			
			avg_str="0분";
		}
		else {
			int avg_min= Integer.parseInt(avg_hour) /Integer.parseInt(workday_cnt);
			int  a=  avg_min   /60;
			 
			int b=  avg_min  %60;
			avg_str= Integer.toString(a)+"시간 "+Integer.toString(b)+"분";
		}
		
		
		request.setAttribute("avg_str", avg_str);
		////
		String vaction_cnt = service.vaction_cnt(empid); //잔여 휴가
		request.setAttribute("vaction_cnt", vaction_cnt);
		
		mav.setViewName("personal_index.personnel");

		return mav;

		 
		

	}

	/**
	 *  
	 * 
	 * @Method Name  : workstatus_insert
	 * @작성일   : 2023. 12. 27 
	 * @작성자   : 윤예인 (eni1221)  @변경이력  : 
	 * @Method 설명 : 근무 내역 추가
	 * @param request
	 * @param response
	 * @param a @return 
	 */
	@ResponseBody
	@PostMapping("/personnel/workstatus_insert.gw")
	public String workstatus_insert(HttpServletRequest request, HttpServletResponse response,  ModelAndView mav,String worktype) {

		 
		JSONObject jsonObj = new JSONObject();
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("worktype", worktype);

		int n = service.workhistory_insert(paraMap); // 근무현황 인서트

		try {
			jsonObj.put("result", 1);

		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("result", 0);
		}

		return jsonObj.toString();
	}

	/**
	 *  
	 * 
	 * @Method Name  : workstatus_print 
	 * @작성일   : 2023. 12. 27 
	 * @작성자   : 윤예인 (eni1221)  @변경이력  : 
	 * @Method 설명 : 근무내역 출력
	 * @param request
	 * @param response
	 * @param worktype @return 
	 */
	@ResponseBody
	@PostMapping("/personnel/workstatus_print.gw")
	public String workstatus_print(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,String worktype) {

	 
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		String sysdate = service.sysdate_get(); // 현재날짜 가져오기

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("worktype", worktype);
		paraMap.put("date", sysdate);

		List<WorkhistoryVO> workhistoryList = service.workhistory_print(paraMap); // 현재 근무현황 출력

		 
		 
		JSONArray jsonArr = new JSONArray(); // []

		if (workhistoryList != null) {

			for (WorkhistoryVO vo : workhistoryList) {

				JSONObject jsonObj = new JSONObject(); // {}
				jsonObj.put("workhistoryid", vo.getWorkhistoryid());
				jsonObj.put("fk_empid", vo.getFk_empid());

				String type = vo.getWorktype();

				switch (type) {

				case "1":
					type = "업무";
					break;
				case "2":
					type = "외출";
					break;
				case "3":
					type = "회의";
					break;
				case "4":
					type = "외근";
					break;
				case "5":
					type = "퇴근";
					break;
				case "6":
					type = "출근";
					break;

				default:
					break;
				}
			 
				jsonObj.put("worktype", type);

				String time = vo.getResisterdaytime().substring(11, 16);
			 
				jsonObj.put("resisterdaytime", time);

				 
				jsonArr.put(jsonObj);
			} // end of for-----------------------------------
		}

		return jsonArr.toString();

		 
	}

	/** 
	* @Method Name  : personal_vaction_application 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 휴가일수
	* @param request
	* @param response
	* @return 
	*/
	@GetMapping("/personnel/personal_vaction_application.gw")
	public String personal_vaction_application(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		String vaction_cnt = service.vaction_cnt(empid);

		mav.addObject("type", "vactionside");

		request.setAttribute("vaction_cnt", vaction_cnt);

		return "approval_vacation_apply.personnel";
	}

	/** 
	* @Method Name  : personal_vaction_insert 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 휴가 기안 등록
	* @param request
	* @param response
	* @return 
	*/
	@ResponseBody
	@PostMapping("/personnel/vaction_insert.gw")
	public String personal_vaction_insert(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
 

		 
		
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디

		int va_approval_insert = service.va_approval_insert(empid);

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("datepi", datepi);
		paraMap.put("reason", reason);
		paraMap.put("vtype", vtype);

		int va_dayoff_insert = service.va_dayoff_insert(paraMap);
		 

		int sts = 1; // 기안 처리 순서

		// 신청 신청자는 상태 2로
		String[] empidLisths = empidListh.split(",");
		String[] empnameLisths = empnameListh.split(",");

		for (int i = 0; i < empidLisths.length; i++) {

		 

			if (i == 0) { // 신청한 사람일경우
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLisths[i]);
				paraMaps.put("ptype", "2");
				paraMaps.put("status", "2");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			} else {
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLisths[i]);
				paraMaps.put("ptype", "2");
				paraMaps.put("status", "1");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

		// 처리
		String[] empidLists = empidList.split(",");
		String[] empnameLists = empnameList.split(",");

		for (int i = 0; i < empidLists.length; i++) {

			if (!empidLists[i].equals("")) {
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLists[i]);
				paraMaps.put("ptype", "4");
				paraMaps.put("status", "1");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

		// 참조 는 확인으로
		String[] empidListRefs = empidListRef.split(",");
		String[] empnameListRefs = empnameListRef.split(",");

		int n = 0;
		for (int i = 0; i < empidListRefs.length; i++) {

			if (!empidListRefs[i].equals("")) {
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidListRefs[i]);
				paraMaps.put("ptype", "5");
				paraMaps.put("status", "5");
				paraMaps.put("seq", Integer.toString(sts));
				n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

	 
		JSONObject jsonObj = new JSONObject();

		if (n == 0) {
			jsonObj.put("result", "0");
		} else {
			jsonObj.put("result", "1");
		}

		return jsonObj.toString();
	}

	
	/** 
	* @Method Name  : personal_workmodi_insert 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 근무수정 기안 등록
	* @param request
	* @param response
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping("/personnel/workmodi_insert.gw")
	public String personal_workmodi_insert(HttpServletRequest request, HttpServletResponse response, ModelAndView mav ) {
 
 
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		String empid = String.valueOf(loginUser.getEmpId()); // 로그인한 사원 아이디


		int mowork_approval_insert = service.mowork_approval_insert(empid);
		
		//////////////////////////////////근무체크 수정 insert
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empid", empid);
		paraMap.put("datepi", datepi);
		paraMap.put("reason", reason);
		 

	 
		// 삭제
		if(deleteidval != "") {
			 
			String[] deleteidvals = deleteidval.split(",");
			 

			for (int i = 0; i < deleteidvals.length; i++) {

				
				
				if (!deleteidvals[i].equals("")) {
					 
					 
					Map<String, String> paraMaps = new HashMap<>();
					paraMaps.put("deleteid", deleteidvals[i]);
					 
					int n = service.work_modify_del_insert(paraMaps);
					 
				}

			}
		}
		//추가
		if(addhourval != "") {
			 
			String[] addhourvals = addhourval.split(",");
			String[] addminvals = addminval.split(",");
			String[] addtypevals = addtypeval.split(",");
			 

			for (int i = 0; i < addhourvals.length; i++) {

				
				
				if (!addhourvals[i].equals("")) {
					 
					String time= datepi+" "+addhourvals[i]+":"+addminvals[i];
					
					 
					////
					String typemod="";
					switch (addtypevals[i]) {

					case "업무":
						typemod = "1";
						break;
					case "외출":
						typemod = "2";
						break;
					case "회의":
						typemod = "3";
						break;
					case "외근":
						typemod = "4";
						break;
					case "퇴근":
						typemod = "5";
						break;
					case "출근":
						typemod = "6";
						break;

					default:
						break;
					}
					 
				 
					Map<String, String> paraMaps = new HashMap<>();
					 
					paraMaps.put("time", time);
					paraMaps.put("typemod", typemod);
				 
					int n = service.work_modify_add_insert(paraMaps);
					 
				}

			}
		}
		
		/////수정
		
		if(idval != null) {
			 
			String[] idvals = idval.split(",");
			String[] editvalhourvals = editvalhourval.split(",");
			String[] editvalminvals = editvalminval.split(",");
			 

			for (int i = 0; i < idvals.length; i++) {

				if (!idvals[i].equals("")) {
					 
					String time= datepi+" "+editvalhourvals[i]+":"+editvalminvals[i];
					 
					Map<String, String> paraMaps = new HashMap<>();
					paraMaps.put("editid", idvals[i]);
					paraMaps.put("time", time);
					 
					int n = service.work_modify_edit_insert(paraMaps);
					 
				}
				 

			}
		}
				


		////////////////////////////////////////////
		int sts = 1; // 기안 처리 순서

		// 신청 신청자는 상태 2로
		String[] empidLisths = empidListh.split(",");
		String[] empnameLisths = empnameListh.split(",");

		for (int i = 0; i < empidLisths.length; i++) {

		 

			if (i == 0) { // 신청한 사람일경우
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLisths[i]);
				paraMaps.put("ptype", "2");
				paraMaps.put("status", "2");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			} else {
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLisths[i]);
				paraMaps.put("ptype", "2");
				paraMaps.put("status", "1");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

		// 처리
		String[] empidLists = empidList.split(",");
		String[] empnameLists = empnameList.split(",");

		for (int i = 0; i < empidLists.length; i++) {

			if (!empidLists[i].equals("")) {
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidLists[i]);
				paraMaps.put("ptype", "4");
				paraMaps.put("status", "1");
				paraMaps.put("seq", Integer.toString(sts));
				int n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

		// 참조 는 확인으로
		String[] empidListRefs = empidListRef.split(",");
		String[] empnameListRefs = empnameListRef.split(",");

		int n = 0;
		for (int i = 0; i < empidListRefs.length; i++) {

			if (!empidListRefs[i].equals("")) {
				 
				Map<String, String> paraMaps = new HashMap<>();
				paraMaps.put("empid", empidListRefs[i]);
				paraMaps.put("ptype", "5");
				paraMaps.put("status", "5");
				paraMaps.put("seq", Integer.toString(sts));
				n = service.procedure_insert(paraMaps);
				sts++;
			}

		}

	 
		JSONObject jsonObj = new JSONObject();

		if (n == 0) {
			jsonObj.put("result", "0");
		} else {
			jsonObj.put("result", "1");
		}

		return jsonObj.toString();
	}
	/**
	 *  
	 * 
	 * @Method Name  : personnel_search_name 
	 * @작성일   : 2023. 12. 31 
	 * @작성자   : 윤예인 (eni1221)  @변경이력  : 
	 * @Method 설명 : 사원이름검색
	 * @param request
	 * @param response
	 * @param empName @return 
	 */
	@ResponseBody
	@PostMapping("/personnel/search_name.gw")
	public String personnel_search_name(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, String empName) {

		List<EmployeeVO> empList = service.personnel_search_name(empName);

		JSONArray jsonArr = new JSONArray();

		for (EmployeeVO evo : empList) {

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("empId", evo.getEmpId());
			jsonObj.put("empName", evo.getEmpName());
			jsonObj.put("teamName", evo.getTeamName());

			jsonArr.put(jsonObj);
		}

		return jsonArr.toString();

	}

	/** 
	* @Method Name  : personnel_selct_empid 
	* @작성일   : 2024. 1. 7 
	* @작성자   : 윤예인 (eni1221) 
	* @변경이력  : 
	* @Method 설명 : 사원아이디 검색
	* @param request
	* @param response
	* @param empid
	* @return 
	*/
	@ResponseBody
	@PostMapping("/personnel/selct_empid.gw")
	public String personnel_selct_empid(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, String empid) {

		String empname = service.personnel_selct_empid(empid);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("empname", empname);

		return jsonObj.toString();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// 예진 코드 시작 ---------------------------------------------------------------------------------------------
	/** 
	* @Method Name  : personnelApprovalForms 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_인사 설정_결재 양식_메인
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@GetMapping("/personnel/approvalForms.gw")
	public ModelAndView personnelApprovalForms(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {

		mav.addObject("formList", service.getFormList());
		mav.addObject("type", "approvalForms");
		mav.setViewName("approval-forms.personnel");

		return mav;
	}


	/** 
	* @Method Name  : personnelAdminInsaManager 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_인사설정_인사 관리자_메인
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@GetMapping("/personnel/insaManager.gw")
	public ModelAndView personnelAdminInsaManager(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {
		// 관리자

		mav.addObject("type", "insaManager");
		mav.addObject("adminList", service.getAdminList());
		mav.setViewName("insa_manage_insamanager.personnel");

		return mav;

	}

	/** 
	* @Method Name  : addPersonnelAdminManager 
	* @작성일   : 2024. 1. 8 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_인사설정_인사 관리자_관리자 추가
	* @param req
	* @param res
	* @param mav
	* @param empId
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/addPersonnelAdminManager.gw", produces = "text/plain;charset=UTF-8")
	public String addPersonnelAdminManager(HttpServletRequest req, HttpServletResponse res, ModelAndView mav,
			Long empId) {

		HttpSession session = req.getSession();

		Map<String, Long> paraMap = new HashMap<>();
		paraMap.put("empId", empId);
		paraMap.put("userEmpId", ((EmployeeVO) session.getAttribute("loginUser")).getEmpId());
		paraMap.put("adminType", 2L);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isAdd", aService.addAppovalAdminManager(paraMap));

		return jsonObj.toString();
	}

	/** 
	* @Method Name  : deletePersonnelAdminManager 
	* @작성일   : 2024. 1. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_인사설정_인사 관리자_관리자 삭제
	* @param req
	* @param res
	* @param mav
	* @param adminId
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/deletePersonnelAdminManager.gw", produces = "text/plain;charset=UTF-8")
	public String deletePersonnelAdminManager(HttpServletRequest req, HttpServletResponse res, ModelAndView mav,
			Long adminId) {

		HttpSession session = req.getSession();

		Map<String, Long> paraMap = new HashMap<>();
		paraMap.put("adminId", adminId);
		paraMap.put("userEmpId", ((EmployeeVO) session.getAttribute("loginUser")).getEmpId());

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isDelete", aService.deleteAppovalAdminManager(paraMap));

		return jsonObj.toString();
	}

	/** 
	* @Method Name  : getAdminHistory 
	* @작성일   : 2024. 1. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_인사설정_인사 관리자_관리자 설정 이력
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/getAdminHistory.gw", produces = "text/plain;charset=UTF-8")
	public String getAdminHistory(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {

		List<AdminHistoryVO> adminHistoryList = service.getAdminHistoryList();

		JSONArray jsonArr = new JSONArray();

		for (AdminHistoryVO ahvo : adminHistoryList) {
			JSONObject jsonObj = new JSONObject();

			jsonObj.put("id", ahvo.getId());
			jsonObj.put("empName", ahvo.getEmpName());
			jsonObj.put("registerId", ahvo.getRegisterId());
			jsonObj.put("registerEmpName", ahvo.getRegisterEmpName());
			jsonObj.put("registerDay", ahvo.getRegisterDay());
			jsonObj.put("registerType", ahvo.getRegisterType());

			jsonArr.put(jsonObj);
		}

		return jsonArr.toString();
	}


	/** 
	* @Method Name  : personnelAdminPayrollSetting 
	* @작성일   : 2024. 1. 9
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_메인
	* @param req
	* @param res
	* @param mav
	* @param sevo
	* @return 
	*/
	@GetMapping("/personnel/payrollSetting.gw")
	public ModelAndView personnelAdminPayrollSetting(HttpServletRequest req, HttpServletResponse res, ModelAndView mav,
			SearchEmployeeVO sevo) {

		String searchWord = sevo.getSearchWord();
		String orderType = sevo.getOrderType();

		if (searchWord == null) {
			searchWord = "";
		} else {
			searchWord = searchWord.trim();
		}

		if (orderType == null) {
			orderType = "asc";
		}

		// 특정 일자
		String month = req.getParameter("monthPicker");

		if (month == null) {
			// 이번달 1일 구하기 (귀속월에 사용)
			YearMonth today1 = YearMonth.now();

			month = today1.toString();
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchWord", searchWord);
		paraMap.put("orderType", orderType);

		SalaryVO svo = service.isExistPayrollThisMonth(month);
		mav.addObject("salary", svo);

		if (svo != null) {
			// 존재 한다면

			paraMap.put("salaryId", String.valueOf(svo.getSalaryId()));
			List<SalaryDetailVO> sdList = service.getSalaryDetail_withSearch(paraMap);
			mav.addObject("sdList", sdList);
		}

		mav.addObject("searchWord", searchWord);
		mav.addObject("orderType", orderType);
		mav.addObject("month", month);
		mav.addObject("type", "payrollSetting");

		mav.setViewName("payroll_setting_payroll.personnel");

		return mav;

	}

	/** 
	* @Method Name  : approvalAdminFileDownload 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_급여 양식 다운로드
	* @param req
	* @param res
	* @param mav 
	*/
	@GetMapping(value = "/personnel/salaryFormDownload.gw")
	public void approvalAdminFileDownload(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {

		// *** 웹브라우저에 출력하기 시작 *** //
		// HttpServletResponse response 객체는 전송되어져온 데이터를 조작해서 결과물을 나타내고자 할때 쓰인다.
		res.setContentType("text/html; charset=UTF-8");

		PrintWriter out = null;
		// out 은 웹브라우저에 기술하는 대상체라고 생각하자.

		try {
			// 정상적으로 다운로드를 할 경우

			String fileName = "payroll_sample_excel.xlsx";

			String orgFilename = "payroll_sample_excel.xlsx";
			// 다운로드시 보여줄 파일명

			HttpSession session = req.getSession();
			String root = session.getServletContext().getRealPath("/");

			String path = root + "resources" + File.separator + "file";

			// ***** file 다운로드 하기 ***** //
			boolean flag = false; // file 다운로드 성공, 실패인지 여부를 알려주는 용도
			flag = fileManager.doFileDownload(fileName, orgFilename, path, res);
			// file 다운로드 성공시 flag 는 true,
			// file 다운로드 실패시 flag 는 false 를 가진다.

			if (!flag) {
				// 다운로드가 실패한 경우 메시지를 띄워준다.
				out = res.getWriter();
				// out 은 웹브라우저에 기술하는 대상체라고 생각하자.

				out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다.'); history.back();</script>");
			}

		} catch (NumberFormatException | IOException e) {
			try {
				out = res.getWriter();
				// out 은 웹브라우저에 기술하는 대상체라고 생각하자.

				out.println("<script type='text/javascript'>alert('파일다운로드가 불가합니다.'); history.back();</script>");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	/** 
	* @Method Name  : personnelAdminFixPayroll 
	* @작성일   : 2024. 1. 9
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_확정
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/fixPayroll.gw", produces = "text/plain;charset=UTF-8")
	public String personnelAdminFixPayroll(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {
		// 확정여부 변경

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("salaryId", req.getParameter("salaryId"));
		paraMap.put("isFix", req.getParameter("isFix"));

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("isFix", service.fixPayroll(paraMap));

		return jsonObj.toString();
	}

	/** 
	* @Method Name  : personnelAdminDeletePayroll 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_급여 대장 삭제
	* @param req
	* @param res
	* @param mav
	* @param salaryId
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/deletePayroll.gw", produces = "text/plain;charset=UTF-8")
	public String personnelAdminDeletePayroll(HttpServletRequest req, HttpServletResponse res, ModelAndView mav,
			Long salaryId) {
		// 삭제

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("isDelete", service.deletePayroll(salaryId));

		return jsonObj.toString();
	}

	/** 
	* @Method Name  : personnelAdminIsExistThisMonth 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_등록 모달_확인버튼 클릭시 이미 급여정보가 존재하는 귀속월인지 확인
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/isExistThisMonth.gw", produces = "text/plain;charset=UTF-8")
	public String personnelAdminIsExistThisMonth(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {
		String month = req.getParameter("month");

		SalaryVO svo = service.isExistPayrollThisMonth(month);

		JSONObject jsonObj = new JSONObject();

		if (svo != null) {
			// 존재 한다면
			jsonObj.put("isExist", true);
		} else {
			jsonObj.put("isExist", false);
		}

		return jsonObj.toString();
	}

	/** 
	* @Method Name  : personnelAdminInsertPayroll 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여 대장_등록 모달_확인버튼 클릭시 급여 등록
	* @param mtp_request
	* @param res
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/insertPayroll.gw", produces = "text/plain;charset=UTF-8")
	public String personnelAdminInsertPayroll(MultipartHttpServletRequest mtp_request, HttpServletResponse res,
			ModelAndView mav) {

		HttpSession session = mtp_request.getSession();

		// 엑셀
		MultipartFile mtp_excel_file = mtp_request.getFile("payrollFile");

		JSONObject jsonObj = new JSONObject();

		if (mtp_excel_file != null) {

			try {
				// == MultipartFile 을 File 로 변환하기 시작 ==
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "file";

				File excel_file = new File(path + File.separator + mtp_excel_file.getOriginalFilename());
				mtp_excel_file.transferTo(excel_file);
				// == MultipartFile 을 File 로 변환하기 끝 ==

				OPCPackage opcPackage = OPCPackage.open(excel_file);

				XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

				// 첫번째 시트 불러오기
				XSSFSheet sheet = workbook.getSheetAt(0);

				List<Map<String, String>> paraMapList = new ArrayList<>();
				List<SalaryDetailVO> paraList = new ArrayList<>();
				List<SalaryDetailVO> errorList = new ArrayList<>();

				// 11번째 행부터
				for (int i = 10; i < sheet.getLastRowNum() + 1; i++) {
					Map<String, String> paraMap = new HashMap<>();

					SalaryDetailVO sdvo = new SalaryDetailVO();

					XSSFRow row = sheet.getRow(i);

					// 행이 존재하지 않으면 건너띈다.
					if (row == null) {
						continue;
					}

					// 행의 1번째 열(이름)
					XSSFCell cell = row.getCell(0);
					if (cell != null) {
						paraMap.put("empName", String.valueOf(cellReader(cell)));
						sdvo.setEmpName(String.valueOf(cellReader(cell)));
					}else {
						continue;
					}

					// 행의 2번째 열(id)
					cell = row.getCell(1);

					if (cell != null) {
						paraMap.put("id", String.valueOf(cellReader(cell)));
						sdvo.setId(String.valueOf(cellReader(cell)));
					}else {
						continue;
					}

					// 행의 3번째 열(기본급)
					cell = row.getCell(2);

					if (cell != null) {
						paraMap.put("basePay", String.valueOf(cellReader(cell)));

						sdvo.setBasePay(Long.parseLong(cellReader(cell)));
					}

					// 행의 4번째 열(연장근로수당)
					cell = row.getCell(3);

					if (cell != null) {
						paraMap.put("overTimePay", String.valueOf(cellReader(cell)));

						sdvo.setOverTimePay(Long.parseLong(cellReader(cell)));
					}

					// 행의 5번째 열(야간근로수당)
					cell = row.getCell(4);

					if (cell != null) {
						paraMap.put("overTimePay", String.valueOf(cellReader(cell)));

						sdvo.setNightTimePay(Long.parseLong(cellReader(cell)));
					}

					// 행의 6번째 열(휴일근로수당)
					cell = row.getCell(5);

					if (cell != null) {
						paraMap.put("holidayPay", String.valueOf(cellReader(cell)));

						sdvo.setHolidayPay(Long.parseLong(cellReader(cell)));
					}

					// 행의 7번째 열(미사용연차수당)
					cell = row.getCell(6);

					if (cell != null) {
						paraMap.put("unUsedAnnualPay", String.valueOf(cellReader(cell)));

						sdvo.setUnUsedAnnualPay(Long.parseLong(cellReader(cell)));
					}

					// 행의 8번째 열(지급총액은 앞의 값들을 더해 얻는다)
					sdvo.setTotalPay(sdvo.getBasePay() + sdvo.getOverTimePay() + sdvo.getNightTimePay()
							+ sdvo.getHolidayPay() + sdvo.getUnUsedAnnualPay());

					// 행의 9번째 열(소득세)
					cell = row.getCell(8);

					if (cell != null) {
						paraMap.put("incomeTax", String.valueOf(cellReader(cell)));

						sdvo.setIncomeTax(Long.parseLong(cellReader(cell)));
					}

					// 행의 10번째 열(지방소득세)
					cell = row.getCell(9);

					if (cell != null) {
						paraMap.put("localTax", String.valueOf(cellReader(cell)));

						sdvo.setLocalTax(Long.parseLong(cellReader(cell)));
					}

					// 행의 11번째 열(국민연금)
					cell = row.getCell(10);

					if (cell != null) {
						paraMap.put("nationalPension", String.valueOf(cellReader(cell)));

						sdvo.setNationalPension(Long.parseLong(cellReader(cell)));
					}

					// 행의 12번째 열(건강보험)
					cell = row.getCell(11);

					if (cell != null) {
						paraMap.put("healthInsurance", String.valueOf(cellReader(cell)));

						sdvo.setHealthInsurance(Long.parseLong(cellReader(cell)));
					}

					// 행의 13번째 열(장기요양보험)
					cell = row.getCell(12);

					if (cell != null) {
						paraMap.put("longTermCareInsurance", String.valueOf(cellReader(cell)));

						sdvo.setLongTermCareInsurance(Long.parseLong(cellReader(cell)));
					}

					// 행의 14번째 열(공제총액)

					sdvo.setTotalDeduction(sdvo.getIncomeTax() + sdvo.getLocalTax() + sdvo.getNationalPension()
							+ sdvo.getHealthInsurance() + sdvo.getLongTermCareInsurance());

					// 행의 15번째 열(실 지급액)
					sdvo.setActualPay(sdvo.getTotalPay() - sdvo.getTotalDeduction());

					// 존재여부 확인하는 겸 empId를 받아온다
					sdvo.setEmpId(service.isExistEmployee(sdvo));
					if (sdvo.getEmpId() == null || sdvo.getEmpId() == 0) {
						// 존재하지 않는다면
						errorList.add(sdvo);
					} else {
						// 존재한다면
						paraList.add(sdvo);
					}

					paraMapList.add(paraMap);

				} // end of for----------------------------

				workbook.close();

				// 엑셀에 넣은 id값을 저장시킬 List이자 중복되지 않은 경우의 id를 넣어 중복체크 활용
				List<String> idList = new ArrayList<>();
				// 중복 체크
				for (SalaryDetailVO sdvo : paraList) {
					String id = sdvo.getId();

					if (idList.contains(id)) {
						// 중복되었다
						errorList.add(sdvo);
					} else {
						// 중복되지 않아 유저id에 넣고 중복체크에 사용하겠다
						idList.add(id);
					}
				}

				boolean isCheck = true;

				if (errorList.size() > 0) {
					// 에러 값이 하나라도 있다면 등록시키지 않는다

					// 에러 값 저장용
					JSONArray jsonArr = new JSONArray();

					for (SalaryDetailVO sdvo : errorList) {
						JSONObject json = new JSONObject();

						json.put("empName", sdvo.getEmpName());
						json.put("id", sdvo.getId());
						json.put("basePay", sdvo.getBasePay());
						json.put("overTimePay", sdvo.getOverTimePay());
						json.put("nightTimePay", sdvo.getNightTimePay());
						json.put("holidayPay", sdvo.getHolidayPay());
						json.put("unUsedAnnualPay", sdvo.getUnUsedAnnualPay());
						json.put("totalPay", sdvo.getTotalPay());
						json.put("incomeTax", sdvo.getIncomeTax());
						json.put("localTax", sdvo.getLocalTax());
						json.put("nationalPension", sdvo.getNationalPension());
						json.put("healthInsurance", sdvo.getHealthInsurance());
						json.put("longTermCareInsurance", sdvo.getLongTermCareInsurance());
						json.put("totalDeduction", sdvo.getTotalDeduction());
						json.put("actualPay", sdvo.getActualPay());

						jsonArr.put(json);
					}

					return jsonArr.toString();

				} else {
					// 에러가 없을 경우 등록 시킨다

					SalaryVO svo = new SalaryVO();
					svo.setSalaryId(service.getSalarySequence());
					svo.setEmpId(((EmployeeVO) session.getAttribute("loginUser")).getEmpId());
					svo.setMonth(mtp_request.getParameter("month"));
					svo.setStartDay(mtp_request.getParameter("startDay"));
					svo.setEndDay(mtp_request.getParameter("endDay"));
					svo.setPayDay(mtp_request.getParameter("payDay"));
					svo.setSalaryId(service.getSalarySequence());

					isCheck = service.insertPayroll(paraList, svo);

				}

				jsonObj.put("isInsert", isCheck);

				excel_file.delete(); // 업로드된 파일 삭제하기

			} catch (Exception e) {
				e.printStackTrace();
				jsonObj.put("isInsert", false);
			}

		} else {
			jsonObj.put("isInsert", false);
		}

		return jsonObj.toString();
	}

	
	
	
	
	/** 
	* @Method Name  : downloadAdminExcelFile 
	* @작성일   : 2024. 1. 9
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 인사_급여 대장_급여 대장 다운로드
	* @Method 설명 : 
	* @param req
	* @param res
	* @param mav
	* @param salaryId
	* @param model
	* @return 
	*/
	@PostMapping("/personnel/downloadPayroll.gw") 
	public String downloadAdminExcelFile(HttpServletRequest req, HttpServletResponse res, ModelAndView mav, @RequestParam(defaultValue = "") String salaryId,
                                    Model model) {
		
		
		service.payrollToExcel(salaryId, model);
		
		return "excelDownloadView";
	}
	
	
	
	
	
	
	
	
	/** 
	* @Method Name  : payAnnualList 
	* @작성일   : 2024. 1. 9 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여_메인
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@GetMapping("/personnel/payAnnualList.gw")
	public ModelAndView payAnnualList(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {
		// 인사_개인이 급여 버튼 눌렀을 경우 메인화면


		HttpSession session = req.getSession();
		
		// 현재연도 구하기  
		String strYear = req.getParameter("year");
		
		if(strYear == null || strYear.isEmpty()) {
			LocalDate now = LocalDate.now();      
			strYear = String.valueOf(now.getYear());
		}
		
		String orderType = req.getParameter("orderType");
		if(orderType == null || orderType.isEmpty()) {
			orderType = "desc";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("year", strYear);
		paraMap.put("orderType", orderType);
		paraMap.put("empId", String.valueOf(((EmployeeVO) session.getAttribute("loginUser")).getEmpId()));
		
		List<SalaryVO> svoList = service.getSalaryByYear(paraMap);
		
		mav.addObject("svoList", svoList);
		mav.addObject("year", strYear);
		mav.addObject("orderType", orderType);
		mav.addObject("type", "payAnnualList");
		mav.setViewName("pay_annual_list.personnel");

		return mav;
	}
	
	
	
	/** 
	* @Method Name  : personnelDetailPrivatePayroll 
	* @작성일   : 2024. 1. 9
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여_급여명세서 보기버튼 눌렀을 경우
	* @param req
	* @param res
	* @param mav
	* @return 
	*/
	@ResponseBody
	@PostMapping(value = "/personnel/detailPrivatePayroll.gw", produces = "text/plain;charset=UTF-8")
	public String personnelDetailPrivatePayroll(HttpServletRequest req, HttpServletResponse res, ModelAndView mav) {

		HttpSession session = req.getSession();
		
		String salaryId = req.getParameter("salaryId");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("empId", String.valueOf(((EmployeeVO) session.getAttribute("loginUser")).getEmpId()));
		paraMap.put("salaryId", salaryId);
		
		
		SalaryVO svo = service.getSalary(salaryId);
		SalaryDetailVO sdvo = service.getSalaryDetailBySalaryIdAndEmpId(paraMap);
		
		JSONObject jsonObj = new JSONObject();
		
		if(svo != null || sdvo != null) {
			jsonObj.put("isExist", true);
			
			jsonObj.put("month", svo.getMonth());
			jsonObj.put("startDay", svo.getStartDay());
			jsonObj.put("endDay", svo.getEndDay());
			jsonObj.put("payDay", svo.getPayDay());
			
			jsonObj.put("basePay", sdvo.getBasePay());
			jsonObj.put("overTimePay", sdvo.getOverTimePay());
			jsonObj.put("nightTimePay", sdvo.getNightTimePay());
			jsonObj.put("holidayPay", sdvo.getHolidayPay());
			jsonObj.put("unUsedAnnualPay", sdvo.getUnUsedAnnualPay());
			jsonObj.put("totalPay", sdvo.getTotalPay());
			jsonObj.put("incomeTax", sdvo.getIncomeTax());
			jsonObj.put("localTax", sdvo.getLocalTax());
			jsonObj.put("nationalPension", sdvo.getNationalPension());
			jsonObj.put("healthInsurance", sdvo.getHealthInsurance());
			jsonObj.put("longTermCareInsurance", sdvo.getLongTermCareInsurance());
			jsonObj.put("totalDeduction", sdvo.getTotalDeduction());
			jsonObj.put("actualPay", sdvo.getActualPay());
			
			jsonObj.put("empName", sdvo.getEmpName());
			jsonObj.put("team", sdvo.getTeam());
			
		}else {
			jsonObj.put("isExist", false);
		}
		
		return jsonObj.toString();
	}
	
	
	
	/** 
	* @Method Name  : downloadPrivateExcelFile 
	* @작성일   : 2024. 1. 18 
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 인사_급여_급여 명세 다운로드
	* @param req
	* @param res
	* @param mav
	* @param year
	* @param orderType
	* @param model
	* @return 
	*/
	@PostMapping("/personnel/downloadPrivatePayroll.gw") 
	public String downloadPrivateExcelFile(HttpServletRequest req, HttpServletResponse res, ModelAndView mav, @RequestParam(defaultValue = "") String year, @RequestParam(defaultValue = "") String orderType, Model model) {
		
		HttpSession session = req.getSession();
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("year", year);
		paraMap.put("orderType", orderType);
		paraMap.put("empId", String.valueOf(((EmployeeVO) session.getAttribute("loginUser")).getEmpId()));
		
		service.privatePayrollToExcel(paraMap, model);
		
		return "excelDownloadView";
	}
	
	
	
	
	//	---------------------------------------------------------------------------
	/** 
	* @Method Name  : cellReader 
	* @작성일   : 2024. 1. 8
	* @작성자   : 신예진 (yejjinny) 
	* @변경이력  : 
	* @Method 설명 : 엑셀 셀 리더기
	* @param cell
	* @return 
	*/
	@SuppressWarnings("incomplete-switch")
	private static String cellReader(XSSFCell cell) {
		// 엑셀 셀 리더기

		String value = "0";
		CellType ct = cell.getCellType();
		if (ct != null) {
			switch (cell.getCellType()) {
			case FORMULA:
				value = cell.getCellFormula() + "";
				break;
			case NUMERIC:
				value = String.format("%.0f", cell.getNumericCellValue());
				break;
			case STRING:
				value = cell.getStringCellValue() + "";
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;
			case ERROR:
				value = cell.getErrorCellValue() + "";
				break;
			}
		}
		return value;
	}
	
	// 예진 코드 끝 ---------------------------------------------------------------------------------------------


}
