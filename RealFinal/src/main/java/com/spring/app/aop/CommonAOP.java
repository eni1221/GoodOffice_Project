package com.spring.app.aop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.approval.service.ApprovalService;
import com.spring.app.common.MyUtil;
import com.spring.app.common.domain.EmployeeVO;

/**
 *   @FileName  : ApprovalAOP.java 
 * 
 * @Project   : TempFinal 
 * @Date      : 2023. 12. 13 
 * @작성자      : 신예진 (yejjinny) 
 * @변경이력 : 
 * @프로그램설명 : 
 */

@Aspect
@Component
public class CommonAOP {
	
	@Autowired
	ApprovalService service;

	@Pointcut("execution(public * com.spring.app..*Controller.*(..))")
	// Controller 클래스에 속한 파마리터가 0개 이상인 모든 메서드
	public void requiredLogin() {
	}

	@Before("requiredLogin()")
	public void loginCheck(JoinPoint joinPoint) {
		// 로그인 유무 검사를 하는 메소드

		HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
		HttpServletResponse res = (HttpServletResponse) joinPoint.getArgs()[1]; // 주업무 메소드의 두번째 파라미터를 얻어오는 것이다.

		HttpSession session = req.getSession();
		
		Object thisObject = joinPoint.getThis(); // 프락시를 가져온다.
		

		// 매핑되는 클래스명으로 관리자 종류를 알 수 있다
		String headerManage = thisObject.toString().substring(thisObject.toString().lastIndexOf(".") + 1,
				thisObject.toString().indexOf("Controller"));
		
		if(!"Common".equals(headerManage)) {
			if (session.getAttribute("loginUser") == null) {
				String message = "로그인이 되어있지 않습니다. 로그인을 해주세요.";

				String loc = req.getContextPath() + "/common/login.gw";

				req.setAttribute("message", message);
				req.setAttribute("loc", loc);

				// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 만들기 <<< //
				String url = MyUtil.getCurrentURL(req);
				session.setAttribute("goBackURL", url); // 세션에 url 정보를 저장시켜둔다.
				RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

				try {
					dispatcher.forward(req, res);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}

			}
		}
		
		

	}

	@Pointcut("execution(public * com.spring.app..*Controller.*Admin*(..))")
	// Controller 클래스에 속한 파마리터가 0개 이상인 모든 메서드
	public void adminCheck() {
	}

	@Before("adminCheck()")
	public void checkAuthority(JoinPoint joinPoint) {
		// 관리자 권한이 있는지 확인

		HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
		HttpServletResponse res = (HttpServletResponse) joinPoint.getArgs()[1]; // 주업무 메소드의 두번째 파라미터를 얻어오는 것이다.

		HttpSession session = req.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");

		Object thisObject = joinPoint.getThis(); // 프락시를 가져온다.

		// 매핑되는 클래스명으로 관리자 종류를 알 수 있다
		String adminType = thisObject.toString().substring(thisObject.toString().lastIndexOf(".") + 1,
				thisObject.toString().indexOf("Controller"));

		if (loginUser == null || loginUser.getIsAdmin() != 1) {
			// 로그인하지 않았거나 관리자가 아닐 경우

			String message = "접근할 수 있는 권한이 없습니다.";
			String loc = null;

			if (loginUser == null) {
				// 로그인하지 않았을 경우

				loc = req.getContextPath() + "/common/login.gw";
			} else {
				// 관리자가 아닐 경우
				loc = "javascript:history.back()";
			}

			req.setAttribute("message", message);
			req.setAttribute("loc", loc);

			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

			try {
				dispatcher.forward(req, res);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}

		} else {
			// 로그인을 했거나 관리자일 경우

			if (loginUser.getIsAdmin() == 1) {
				// 관리자일 경우

				if (!adminType.equals(loginUser.getAdminType()) && !"All".equals(loginUser.getAdminType())) {
					// 매핑되어서 들어온 클래스의 기능과 관리자 기능이 같거나 전체일 경우

					String message = "접근할 수 있는 권한이 없습니다.";
					String loc = null;

					loc = "javascript:history.back()";

					req.setAttribute("message", message);
					req.setAttribute("loc", loc);

					RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

					try {
						dispatcher.forward(req, res);
					} catch (ServletException | IOException e) {
						e.printStackTrace();
					}
				}

			} else {
				// 관리자가 아닐 경우
				String message = "접근할 수 있는 권한이 없습니다.";
				String loc = null;

				loc = "javascript:history.back()";

				req.setAttribute("message", message);
				req.setAttribute("loc", loc);

				RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");

				try {
					dispatcher.forward(req, res);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Pointcut("execution(public * com.spring.app..*Controller.*(..))")
	// Controller 클래스 속 모든 메소드
	public void headerDynamicManager() {
	}

	@Before("headerDynamicManager()")
	public void headerDynamicManager(JoinPoint joinPoint) {
		// 헤더 동적으로 제어할 수 있도록 파라미터 값을 넣어준다

		HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
		ModelAndView mav = (ModelAndView) joinPoint.getArgs()[2];

		Object thisObject = joinPoint.getThis(); // 프락시를 가져온다.
		

		// 매핑되는 클래스명으로 관리자 종류를 알 수 있다
		String headerManage = thisObject.toString().substring(thisObject.toString().lastIndexOf(".") + 1,
				thisObject.toString().indexOf("Controller"));
		
		mav.addObject("headerManage", headerManage);

	}
	
	@Pointcut("execution(public * com.spring.app..ApprovalController.*_AfterGetListSize(..))")
	// ApprovalController 클래스에 속해 메소드 명에 AdminSetting이 들어가는 
	public void approvalListSize() {
	}

	@After("approvalListSize()")
	public void approvalListSize(JoinPoint joinPoint) {
		// 헤더 동적으로 제어할 수 있도록 파라미터 값을 넣어준다

		HttpServletRequest req = (HttpServletRequest) joinPoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
		ModelAndView mav = (ModelAndView) joinPoint.getArgs()[2];

		HttpSession session = req.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("loginUser");
		
		if (loginUser != null) {
			Map<String, String> sizeMap = new HashMap<>();
			sizeMap.put("searchType", "");
			sizeMap.put("searchWord", "");
			sizeMap.put("empId", String.valueOf(loginUser.getEmpId()));

			mav.addObject("aSize", service.getTotalCountApprovalAllIngList(sizeMap));
			mav.addObject("wSize", service.getTotalCountApprovalWaitingList(sizeMap));
			mav.addObject("vSize", service.getTotalCountApprovalCheckList(sizeMap));
			mav.addObject("eSize", service.getTotalCountApprovalScheduleList(sizeMap));
			mav.addObject("pSize", service.getTotalCountApprovalProgressList(sizeMap));
		}

	}

}
