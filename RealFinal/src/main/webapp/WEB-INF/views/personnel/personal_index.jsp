<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%
    String ctxPath = request.getContextPath();
%>

 <script type="text/javascript">

  $(document).ready(function(){
	  
	  loopshowNowTime();
	  
	  work_status_print();
	  
	 
	  
	  
	 
	
	$('button#work_status').on('click', (e) => {  //출근, 퇴큰 버튼
		   
		  const type=e.target.parentElement.value;
		 
		  if(type==6){
			  alert("출근이 등록되었습니다.");
		  }
		  else{
			  alert("퇴근이 등록되었습니다.");
		  }
		  workstatus_insert(type);
		  
		  
		  
		
	 });
	
	
	$('button#work_status_four').on('click', (e) => {  
		
		
		 
		  const type=e.target.value;
		  
		  if(typeof type=="undefined"){
			  
			  
			  const typeRe=e.target.parentElement.value;
			 
			  workstatus_insert(typeRe);
			   
		  }
		  else{
			  
		 
			  workstatus_insert(type);
			   
			  
		  }
		  
		  
		 
		  
		
	 });
	
	
	$('button#modify').on('click', (e) => {  //근무수정 버튼 클릭시
		
		
		  
		  const type=e.target.value;
		  
			$(location).attr("href", `<%=ctxPath%>/personnel/work_modify.gw`)
		 
		 
		  
		
	 });
	
	$('button#work_vaction').on('click', (e) => {  //휴가신청 버튼 클릭시
		
		
	 
		  const type=e.target.value;
		  
			$(location).attr("href", `<%=ctxPath%>/personnel/personal_vaction_application.gw`)
		 
		
	 });
	 
	 const idd=3;
	 
	 
	 
	 
	  
	 const frm=document.attendanceFrm;
	     
	    
	    
  });// end of $(document).ready(function(){})------------
  
  

  	function workstatus_insert(type){
	
	  
	   
  		 $.ajax({
	          url : "<%= ctxPath%>/personnel/workstatus_insert.gw",
	          type : "post",
	          data : { "worktype":type  },
	          
	          dataType:"json",
	          success:function(json){
	            
	        	  work_status_print();
	          },
	          error: function(request, status, error){
	          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
  }
  
	   
	
	function work_status_print(){
		
		$.ajax({
	          url : "<%= ctxPath%>/personnel/workstatus_print.gw",
	          type : "post",
	          data : { "worktype":6  },
	         
	          dataType:"json",
	          success:function(json){
	              
	              let v_html = "";
	              
	              
	 			 if(json.length > 0) {
	 				 
	 				 $.each(json, function(index, item){
	 					 
		 					 v_html += "<li data-v-41cd5b14=''>						<span data-v-41cd5b14=''		class='time'>"; 
		 					 v_html +=    item.resisterdaytime +"</span>		<span data-v-41cd5b14=''	 class='task'>";
		 					
		 				 	 
		 				 if(item.worktype=="출근" || item.worktype=="퇴근"){
		 					 
		 					
		 					v_html += item.worktype+"</span><span data-v-41cd5b14=''	 class='tag yellow'>"+item.worktype+"</span></li>";
		 				 }
		 				 else{
		 					 
		 					v_html += item.worktype+"</span>	</li>";
		 					 
		 				 }
		 					  
 
	 					        
	 				 });      
	 				 
	 				 
	 				 
	 				 
	 			 }
	 			 else {
	 				 
	 			 }
	 			 
	 			 $("ol#statusOL").html(v_html);
	             
	          
	          },
	          error: function(request, status, error){
	          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	          }
	      });
		  
		
		
	} 
	
	
	

	function showNowTime() {
	      
	      const now = new Date();
	   
	      let month = now.getMonth() + 1;
	      if(month < 10) {
	         month = "0"+month;
	      }
	      
	      let date = now.getDate();
	      if(date < 10) {
	         date = "0"+date;
	      }
	      
	      let strNow_back = now.getFullYear() + "-" + month + "-" + date;
	      
	      month+="월";
	    
	      $("div#monthdiv").html(month);
	      $("div#datediv").html(date);
	      
	      let dayOfWeek = now.getDay(); 
	     let wok="근무일";
	     let woho="09:00 ~ 18:00 (소정 8시간)";
	      
	      if(dayOfWeek==0){
	    	  dayOfWeek="일요일";
	    	  wok="휴무일";
	    	  woho="";
	      }
	      else if (dayOfWeek==1){
	    	  dayOfWeek="월요일";
	      }
		else if (dayOfWeek==2){
			  
			dayOfWeek="화요일";
		 			      }
		else if (dayOfWeek==3){
			dayOfWeek="수요일";
		}
		else if (dayOfWeek==4){
			dayOfWeek="목요일";
		}
		else if (dayOfWeek==5){
			dayOfWeek="금요일";
		}
		else{
			dayOfWeek="토요일";
			wok="휴무일";
			 woho="";
		}
	      
	      $("div#daydiv").html(dayOfWeek);
	      
	      $("span#working").html(wok);
	      $("span#workho").html(woho);
	      
	      let hour = "";
	       if(now.getHours() < 10) {
	           hour = "0"+now.getHours();
	       } 
	       else {
	          hour = now.getHours();
	       }
	      
	       
	      let minute = "";
	      if(now.getMinutes() < 10) {
	         minute = "0"+now.getMinutes();
	      } else {
	         minute = now.getMinutes();
	      }
	      
	      let second = "";
	      if(now.getSeconds() < 10) {
	         second = "0"+now.getSeconds();
	      } else {
	         second = now.getSeconds();
	      }
	      
	      let strNow = hour + " : " + minute + " : " + second;
	      
	      $("span#clock").html(strNow);
	   
	   }// end of function showNowTime() -----------------------------
	   
	   
	   function loopshowNowTime() {
		      showNowTime();
		      
		      const timejugi = 1000;   // 시간을 1초 마다 자동 갱신하려고.
		      
		      setTimeout(function() {
		                  loopshowNowTime();   
		               }, timejugi);
		      
	   }// end of loopshowNowTime() 
	   

</script>    
 
 
 
  
<div id="router_split_item" class="split-item right"
	style="width: calc(100% - 276px);">
	<div id="contents" class="contents-wrap">
		<div data-v-63c5cb89="" class="vacation-and-work-wrapper">
			<!---->
			<section data-v-63c5cb89="" class="info-year">
				<h2 data-v-63c5cb89="" class="section-title mb-30">올해 근무 정보</h2>
				<div data-v-63c5cb89="" class="box-group">
					<div data-v-52107848="" data-v-63c5cb89="">
						<div data-v-52107848="" class="box-title">
							<i data-v-52107848=""
								class="gis gi-list gt-icon-solid-color mr-10"></i><span
								data-v-52107848="">근태 현황</span>
						</div>
						<!--  /////////////////////-->
						<form name="attendanceFrm">
						<input type="hidden" name="empid" value="${requestScope.empid}" readonly /> 
						<input type="hidden" name="sysdate" value="${requestScope.sysdate}" readonly /> 
						
						
						
						<div data-v-52107848="" class="box">
							<ul data-v-52107848="" class="division-list">
								<li data-v-4292f724="" data-v-52107848=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span  data-v-4292f724="">지각</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span  id="tardy" data-v-4292f724="">${requestScope.tardy_cnt}</span>회
									</div></li>
								<li data-v-4292f724="" data-v-52107848=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">조기퇴근</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724="">${requestScope.early_work_cnt}</span>회
									</div></li>
								<li data-v-4292f724="" data-v-52107848=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">퇴근미체크</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724="">${requestScope.not_leave_work}</span>회
									</div></li>
								<li data-v-4292f724="" data-v-52107848=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">결근</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724="">${requestScope.absenteeism}</span>회
									</div></li>
							</ul>
						</div>
						
						</form>
						<!--  /////////////////////-->
					</div>
					<div data-v-ca725702="" data-v-63c5cb89="">
						<div data-v-ca725702="" class="box-title">
							<i data-v-ca725702=""
								class="gis gi-calendar-check gt-icon-solid-color mr-10"></i><span
								data-v-ca725702="">휴가 현황</span>
						</div>
						<div data-v-ca725702=""
							class="box d-flex align-items-center justify-content-between ph-30">
							<div data-v-ca725702=""
								class="remain-vacation flex-grow-2 text-center rest-vacation">
								<div data-v-ca725702=""
									class="font-weight-bold rest-vacation-title">잔여 휴가</div>
								<div data-v-ca725702="" class="mt-20 rest-vacation-value">
									<span>${requestScope.vaction_cnt}</span><span>일</span>
								</div>
							</div>
							<div data-v-ca725702="" class="flex-grow-1 text-right">
								<div data-v-ca725702="" class="d-inline-block">
									<button  id="vacationBtn"  data-v-f8d3258e="" data-v-ca725702="" type="button"
										class="hw-button text">
										<!---->
										<!-- <span data-v-f8d3258e="" class="label">휴가 현황</span> -->
										<!---->
									</button>
									<!-- <div data-v-ca725702="" class="divider ml-15"></div> -->
								</div>
								<button data-v-cde747bc="" data-v-ca725702="" type="button" id="work_vaction" value="7"
									class="hu-button pill-shape-outline font-size-13 ml-15">
									<!---->
									<span data-v-cde747bc="" class="label">휴가 신청 </span>
									<!---->
								</button>
							</div>
						</div>
					</div>
					<div data-v-b43a2120="" data-v-63c5cb89="">
						<div data-v-b43a2120="" class="box-title">
							<i data-v-b43a2120=""
								class="gis gi-chart-pie gt-icon-solid-color mr-10"></i><span
								data-v-b43a2120="">근무시간</span>
						</div>
						<div data-v-b43a2120="" class="box">
							<ul data-v-b43a2120="" class="division-list">
								<li data-v-4292f724="" data-v-b43a2120=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">근무일수</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724="">${requestScope.workday_cnt}</span>일
									</div></li>
								<li data-v-4292f724="" data-v-b43a2120=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">총근무시간</span>
										<!---->
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724=""></span>
										<div data-v-b43a2120="" data-v-4292f724="">
											<span>${requestScope.year_hour}</span><!-- <span>시간</span> -->
										</div>
									</div></li>
								<li data-v-4292f724="" data-v-b43a2120=""><div
										data-v-4292f724="" class="font-weight-bold">
										<span data-v-4292f724="">보정평균</span>
										
										<!-- <span data-v-4292f724=""><div
												role="tooltip" id="el-popover-5295" aria-hidden="true"
												class="el-popover el-popper tooltip-popover el-popover--plain"
												tabindex="0" style="width: 340px; display: none;">
												
												보정평균근무시간=(총근무시간+시간차휴가)/근무일수
											</div>
											<span class="el-popover__reference-wrapper"><button
													data-v-4292f724=""
													class="tooltip-popover-icon el-popover__reference"
													aria-describedby="el-popover-5295" tabindex="0">
													<i data-v-4292f724=""
														class="gi gi-question-alt font-size-15 ml-5"></i>
												</button></span></span> -->
												
												
									</div>
									<div data-v-4292f724="" class="mt-20">
										<span data-v-4292f724=""></span>
										<div data-v-b43a2120="" data-v-4292f724="">
											<span>${requestScope.avg_str}</span><!-- <span>ㅇ시간</span> -->
										</div>
									</div></li>
							</ul>
						</div>
					</div>
				</div>
			</section>
			<section data-v-63c5cb89="" class="info-day">
				<h2 data-v-63c5cb89="" class="section-title mb-30">오늘 근무현황</h2>
				<div data-v-63c5cb89="" class="box-group">
					<div data-v-afc08ac2="" data-v-63c5cb89="" class="info-day-plan">
						<div data-v-afc08ac2="" class="box-title">
							<span data-v-afc08ac2=""><i data-v-afc08ac2=""
								class="gis gi-calendar gt-icon-solid-color mr-10"></i>근무계획</span>
						</div>
						<div data-v-afc08ac2="" class="box p-24">
							<div data-v-afc08ac2="" class="calendar-icon">
								<div data-v-afc08ac2="" class="head" id ="monthdiv"></div>
								<div data-v-afc08ac2="" class="body">
									<div data-v-afc08ac2="" class="date" id="datediv"></div>
									<div data-v-afc08ac2="" class="day" id ="daydiv"></div>
								</div>
							</div>
							<div data-v-afc08ac2="" class="calendar-description">
								<div data-v-afc08ac2="">
									<span data-v-afc08ac2="" class="rule-type-area align-middle" id="working"></span>
								</div>
								<div data-v-afc08ac2="">
									<span data-v-afc08ac2="" class="rule-type-area align-middle" id="workho"></span>
								</div>
								<!---->
								<div data-v-afc08ac2="" class="button-area">
								
								<!-- 
									<div data-v-afc08ac2="" class="my-work-plan">
										<button data-v-cde747bc="" data-v-afc08ac2="" type="button"
											class="hu-button text">
											
											<span data-v-cde747bc="" class="label">내 근무 계획</span>
											
										</button>
									</div>
									<div data-v-afc08ac2="" class="apply">
										<button data-v-cde747bc="" data-v-afc08ac2="" type="button"
											class="hu-button pill-shape-outline font-size-13">
											
											<span data-v-cde747bc="" class="label">연장근무신청 </span>
											
										</button>
										<button data-v-cde747bc="" data-v-afc08ac2="" type="button"
											class="hu-button pill-shape-outline font-size-13">
											
											<span data-v-cde747bc="" class="label">휴(무)일근무신청 </span>
											
										</button>
									</div>
									
									 -->
									
								</div>
								
							</div>
						</div>
					</div>
					<div data-v-39e81d47="" data-v-63c5cb89="" class="info-day-check">
						<div data-v-39e81d47="" class="box-title">
							<i data-v-39e81d47=""
								class="gis gi-clock gt-icon-solid-color mr-10"></i><span
								data-v-39e81d47="">근무체크</span>
						</div>
						<div data-v-39e81d47=""
							class="box flex-column justify-content-between">
							<div data-v-39e81d47="" class="timer-wrapper">
								<span id="clock" data-v-39e81d47="" class="current-time">
									</span><!-- <span data-v-39e81d47="" class="tag blue">근무 종료</span> -->
									<!-- 현재시각 :&nbsp; <span  style="color:green; font-weight:bold;"></span> -->
							</div>
							<ul data-v-39e81d47="" class="division-list">
								<li data-v-39e81d47="" value="6"><button data-v-39e81d47=""
										type="button" id="work_status"  value="6"  >
										<!-- disabled="disabled" class="inactive"-->
										  <img data-v-39e81d47="" src="<%= ctxPath%>/resources/image/icon/checkin.ac627d8a.svg"
											alt="출근하기" class="icon-in-out"  > 
										<div data-v-39e81d47="" class="check-btn"    >출근하기</div>
										<!-- <div data-v-39e81d47="" class="check-time">01:53:56</div> -->
									</button></li>
								<li data-v-39e81d47="" value="5"><button data-v-39e81d47=""
										type="button"  id="work_status" value="5" >
										<!-- disabled="disabled"  class="inactive" -->
										  <img data-v-39e81d47="" src="<%= ctxPath%>/resources/image/icon/checkout.4919929e.svg"
											alt="퇴근하기" class="icon-in-out"    >   
										<div data-v-39e81d47="" class="check-btn">퇴근하기</div>
										<!-- <div data-v-39e81d47="" class="check-time">13:44:58</div> -->
									</button></li>
							</ul>
							<div data-v-39e81d47="" class="list-btns-wrap">
								<div data-v-39e81d47="" class="list-btns">
									<button data-v-cde747bc="" data-v-39e81d47="" type="button" id="work_status_four" value="1"
										 class="hu-button pill-shape-outline">
										<!--disabled="disabled"-->
										<span data-v-cde747bc="" class="label" value="1">업무 </span>
										<!---->
									</button>
									<button data-v-cde747bc="" data-v-39e81d47="" type="button" id="work_status_four" value="2"
										 class="hu-button pill-shape-outline">
										<!--disabled="disabled"-->
										<span data-v-cde747bc="" class="label">외출 </span>
										<!---->
									</button>
									<button data-v-cde747bc="" data-v-39e81d47="" type="button" id="work_status_four" value="3"
										 class="hu-button pill-shape-outline">
										<!--disabled="disabled"-->
										<span data-v-cde747bc="" class="label">회의 </span>
										<!---->
									</button>
									<button data-v-cde747bc="" data-v-39e81d47="" type="button" id="work_status_four" value="4"
										 class="hu-button pill-shape-outline">
										<!--disabled="disabled"-->
										<span data-v-cde747bc="" class="label">외근 </span>
										<!---->
									</button>
								</div>
							</div>
						</div>
					</div>
					<div data-v-41cd5b14="" data-v-63c5cb89="" class="info-day-status">
						<div data-v-41cd5b14="" class="box-title">
							<span data-v-41cd5b14="" class="mr-8"><i
								data-v-41cd5b14="" class="fas fa-tv gt-icon-solid-color mr-10"></i><span
								data-v-41cd5b14="">근무현황</span></span>
						</div>
						<div data-v-41cd5b14=""
							class="box flex-column justify-content-between align-items-start pl-24 position-relative">
							<div data-v-41cd5b14="" class="w-100">
								<div data-v-41cd5b14="" class="vb vb-invisible"
									style="width: auto; height: 230px; position: relative; overflow: hidden;">
									<div class="vb-content"
										style="display: block; overflow: hidden scroll; height: 100%; width: calc(100% + 8px);">
										<ol data-v-41cd5b14="" id="statusOL" class="hw-timeline">
										
									 <!-- 
											<li data-v-41cd5b14="">
											<span data-v-41cd5b14=""		class="time">01:53</span>											
												<span data-v-41cd5b14=""	 class="task">출근</span>
													<span data-v-41cd5b14=""	 class="tag yellow">출근</span>
												</li>
												
												
											<li data-v-41cd5b14="">
											<span data-v-41cd5b14=""		class="time">01:54</span>
											<span data-v-41cd5b14=""	class="task">회의</span>
											</li>
											
											
											<li data-v-41cd5b14="">
											<span data-v-41cd5b14="" class="time">13:44</span>
												<span data-v-41cd5b14=""class="task">퇴근</span>
												<span data-v-41cd5b14=""	class="tag yellow">퇴근</span>
											</li>   -->
												
										</ol>
									</div>
									<div class="vb-dragger"
										style="position: absolute; height: 0px; top: 0px;">
										<div class="vb-dragger-styler"></div>
									</div>
								</div>
								<button data-v-cde747bc="" data-v-41cd5b14="" type="button" id="modify"
									class="hu-button pill-shape-outline font-size-13"
									style="position: absolute; right: 24px; bottom: 24px;">
									<!---->
									<span data-v-cde747bc="" class="label"  >근무체크수정 </span>
									<!---->
								</button>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- <section data-v-63c5cb89="" class="info-week">
				<h2 data-v-63c5cb89="" class="section-title">주간 근무현황</h2>
				<div data-v-366f4357="" data-v-63c5cb89=""
					class="calendar-container">
					<section data-v-366f4357="" class="calendar-nav">
						<button data-v-366f4357="" class="hw-icon">
							<i data-v-366f4357="" class="gi gi-short-arrow-left-alt"></i>
						</button>
						<span data-v-366f4357="" class="period">2023년 11월 27일 ~ 03일</span>
						<button data-v-366f4357="" class="hw-icon">
							<i data-v-366f4357="" class="gi gi-short-arrow-right-alt"></i>
						</button>
						<button data-v-f8d3258e="" data-v-366f4357="" type="button"
							class="hw-button pill-shape-outline font-size-13 ml-15">
							
							<span data-v-f8d3258e="" class="label">이번 주</span>
							
						</button>
					</section>
					<section data-v-366f4357="">
						<div data-v-366f4357="" class="mb-20">
							<div data-v-366f4357="" class="progress-wrapper">
								<div class="progress-bar-legend">주간 누적근무시간</div>
								<div class="progress-bar-wrapper">
									<div class="progress-bar"
										style="width: 100%; background: linear-gradient(90deg, rgb(164, 188, 108) 0%, rgb(230, 230, 230) 0%, rgb(230, 230, 230) 0%, rgb(205, 106, 81) 0%, rgb(205, 106, 81) 0%, rgb(230, 230, 230) 0%, rgb(230, 230, 230) 100%);"></div>
								</div>
								<div class="unit" style="left: 0%;">
									<span class="unit-value">0</span>
								</div>
								<div class="unit" style="left: 76.9231%;">
									<span class="unit-value">40</span>
								</div>
								<div class="unit" style="left: 100%;">
									<span class="unit-value">52</span>
								</div>
							</div>
						</div>
						<div data-v-366f4357="" class="mt-20">
							<div data-v-366f4357="" class="toggle-button">
								<p data-v-366f4357="">
									<button data-v-f8d3258e="" data-v-366f4357="" type="button"
										class="hw-button pill-shape-outline font-size-13 ml-15">
										
										<span data-v-f8d3258e="" class="label">근무계획보기 </span>
										
									</button>
								</p>
							</div>
							<table data-v-aa5e3de2="" data-v-366f4357=""
								class="calendar-week">
								<colgroup data-v-aa5e3de2="">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
									<col data-v-aa5e3de2="" width="12.5%">
								</colgroup>
								<thead data-v-aa5e3de2="">
									<tr data-v-aa5e3de2="" class="text-left">
										<th data-v-aa5e3de2="" class="">27 (월)</th>
										<th data-v-aa5e3de2="" class="">28 (화)</th>
										<th data-v-aa5e3de2="" class="">29 (수)</th>
										<th data-v-aa5e3de2="" class="">30 (목)</th>
										<th data-v-aa5e3de2="" class="">1 (금)</th>
										<th data-v-aa5e3de2="" class="today">2 (토)</th>
										<th data-v-aa5e3de2="" class="">3 (일)</th>
										<th data-v-aa5e3de2="" class="aside">주간 합계</th>
									</tr>
								</thead>
								<tbody data-v-aa5e3de2="">
									<tr data-v-aa5e3de2="">
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													
												</div>
												
												
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
											</div></td>
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													
												</div>
												
												
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
											</div></td>
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													
												</div>
												
												
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
											</div></td>
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													
												</div>
												
												
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
											</div></td>
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													
												</div>
												
												
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
											</div></td>
										<td data-v-aa5e3de2="" class="today"><div
												data-v-aa5e3de2="" class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													
													<p data-v-aa5e3de2="" class="day_off">휴무일</p>
												</div>
												
												
												
												
												
												
											</div></td>
										<td data-v-aa5e3de2="" class=""><div data-v-aa5e3de2=""
												class="work-status-cell">
												<div data-v-aa5e3de2="" class="note">
													<p data-v-aa5e3de2="" class="day_off">휴일</p>
													
												</div>
												
												<div data-v-aa5e3de2="" class="note"></div>
												
												
												
												
											</div></td>
										<td data-v-aa5e3de2="" class="aside"><div
												data-v-aa5e3de2="" class="date-info">
												<div data-v-aa5e3de2="" class="note">
													<div data-v-aa5e3de2="" class="plan-content">
														<p data-v-aa5e3de2="">계획 0시간</p>
														<p data-v-aa5e3de2="">휴가 0시간</p>
													</div>
													<div data-v-aa5e3de2="" class="status-content">
														<strong data-v-aa5e3de2="">실근무</strong>
														<p data-v-aa5e3de2="">총: 0시간</p>
														
														
														<p data-v-aa5e3de2="">야간: 0시간</p>
													</div>
												</div>
											</div></td>
									</tr>
								</tbody>
							</table>
						</div>
					</section>
				</div>
			</section>
			<section data-v-63c5cb89="" class="info-month">
				<h2 data-v-63c5cb89="" class="section-title">2023년 11월 근무현황</h2>
				<div data-v-67f262b7="" data-v-63c5cb89="">
					<table data-v-67f262b7="" class="setting-table center">
						<colgroup data-v-67f262b7="">
							<col data-v-67f262b7="">
							<col data-v-67f262b7="">
							<col data-v-67f262b7="">
							<col data-v-67f262b7="">
							<col data-v-67f262b7="">
							<col data-v-67f262b7="">
						</colgroup>
						<thead data-v-67f262b7="">
							<tr data-v-67f262b7="">
								<th data-v-67f262b7=""></th>
								<th data-v-67f262b7="">기준근무</th>
								<th data-v-67f262b7="">계획</th>
								<th data-v-67f262b7="">실근무</th>
								<th data-v-67f262b7="">연월차</th>
								<th data-v-67f262b7="">모든 휴가</th>
							</tr>
						</thead>
						<tbody data-v-67f262b7="">
							<tr data-v-67f262b7="">
								<td data-v-67f262b7="">소정</td>
								<td data-v-67f262b7="">176시간</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
							</tr>
							<tr data-v-67f262b7="">
								<td data-v-67f262b7="">연장, 휴일</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
							</tr>
							<tr data-v-67f262b7="">
								<td data-v-67f262b7="">총 근무</td>
								<td data-v-67f262b7="">176시간</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
								<td data-v-67f262b7="">-</td>
							</tr>
						</tbody>
					</table>
					<br data-v-67f262b7="">
				</div>
			</section> -->
			<div data-v-b2fca032="" data-v-63c5cb89="" class="el-dialog__wrapper"
				style="display: none;">
				<div role="dialog" aria-modal="true" aria-label="dialog"
					class="el-dialog sm-title" style="margin-top: 15vh; width: 500px;">
					<div class="el-dialog__header">
						<div data-v-b2fca032="" class="modal-title">() 근무현황</div>
						<button type="button" aria-label="Close"
							class="el-dialog__headerbtn">
							<i class="el-dialog__close el-icon el-icon-close"></i>
						</button>
					</div>
					<!---->
					<div class="el-dialog__footer">
						<span data-v-b2fca032="" class="dialog-footer"><button
								data-v-f8d3258e="" data-v-b2fca032="" type="button"
								class="hw-button secondary">
								<!---->
								<span data-v-f8d3258e="" class="label">닫기</span>
								<!---->
							</button>
							<button data-v-f8d3258e="" data-v-b2fca032="" type="button"
								class="hw-button primary">
								<!---->
								<span data-v-f8d3258e="" class="label">근무체크수정</span>
								<!---->
							</button></span>
					</div>
				</div>
			</div>
			<div data-v-4ff123c8="" data-v-63c5cb89="" class="el-dialog__wrapper"
				style="display: none;">
				<div role="dialog" aria-modal="true" aria-label="dialog"
					class="el-dialog sm-title" style="margin-top: 15vh; width: 500px;">
					<div class="el-dialog__header">
						<div data-v-4ff123c8="" class="modal-title">() 근무계획</div>
						<button type="button" aria-label="Close"
							class="el-dialog__headerbtn">
							<i class="el-dialog__close el-icon el-icon-close"></i>
						</button>
					</div>
					<!---->
					<div class="el-dialog__footer">
						<span data-v-4ff123c8="" class="dialog-footer"><button
								data-v-f8d3258e="" data-v-4ff123c8="" type="button"
								class="hw-button secondary">
								<!---->
								<span data-v-f8d3258e="" class="label">닫기</span>
								<!---->
							</button>
							<button data-v-f8d3258e="" data-v-4ff123c8="" type="button"
								class="hw-button primary" style="display: none;">
								<!---->
								<span data-v-f8d3258e="" class="label">근무계획변경</span>
								<!---->
							</button></span>
					</div>
				</div>
			</div>
			<div data-v-46292040="" data-v-63c5cb89="" class="el-dialog__wrapper"
				style="display: none;">
				<div role="dialog" aria-modal="true" aria-label="dialog"
					class="el-dialog sm" style="margin-top: 15vh;">
					<div class="el-dialog__header">
						<span class="el-dialog__title"></span>
						<button type="button" aria-label="Close"
							class="el-dialog__headerbtn">
							<i class="el-dialog__close el-icon el-icon-close"></i>
						</button>
					</div>
					<!---->
					<div class="el-dialog__footer">
						<span data-v-46292040="" class="dialog-footer"><button
								data-v-f8d3258e="" data-v-46292040="" type="button"
								class="hw-button secondary">
								<!---->
								<span data-v-f8d3258e="" class="label">취소</span>
								<!---->
							</button>
							<!----></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

