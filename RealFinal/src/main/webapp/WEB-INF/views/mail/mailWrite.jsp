<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript">

	$(document).ready(function(){
		
			
		// 메일쓰기 옵션 드롭다운 설정
		$("a#m_write_option").click(function(){ 
			if($("div#m_write_option_detail").hasClass("hide") === true) { // 옵션드롭다운이 보이지 않는 중일 경우
				$("div#m_write_option_detail").removeClass("hide")
			}
			else{ // 옵션드롭다운이 보이는 중일 경우 
				$("div#m_write_option_detail").addClass("hide")
			}
		});
		
		
		// 숨은참조에 값이 입력된 경우 숨은참조 입력칸 보이게
		const hiddenReference = $("textarea[name='hiddenReferenceMail']").val().trim();
		if(hiddenReference != "") { 
			$("div#hiddenReference").css("display","");
			$("a#referencePlus").css("display","none");	
		}
		 
		
		// 옵션의 발송에약 체크의 값이 변화했을 때
		$("input#reserve_mail").change(function(){
		    if($(this).is(":checked")){ // 체크 되어 있을 때
		    	$("input#send_date, select#send_hour, select#send_minute").attr("disabled", false); // 값을 변경하지 못하도록 
		    }
		    else{ // 체크 해제 되어 있을 때
		    	$("input#send_date, select#send_hour, select#send_minute").attr("disabled", true); // 값을 변경할 수 있도록
		    }
		}); 
		
		
		<%-- === 스마트 에디터 구현 시작 === --%>
	    //전역변수
	    var obj = [];
	      
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	    	oAppRef: obj,
	        elPlaceHolder: "mailContent", // id가 mailContent인 textarea에 에디터를 넣어준다.
	        sSkinURI: "<%=ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
	        htParams : {
	        	// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseModeChanger : true,
	        }
	    });
	    <%-- === 스마트 에디터 구현 끝 === --%>
	    
	    
	    <%-- === jQuery 를 사용하여 드래그앤드롭(DragAndDrop)을 통한 파일 업로드 시작 === --%>
		let file_arr = []; // 첨부된어진 파일 정보를 담아 둘 배열

        // == 파일 Drag & Drop 만들기 == //
	    $("div#fileDrop").on("dragenter", function(e){ /* "dragenter" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 최초로 들어왔을 때 */ 
	    	e.preventDefault(); // 브라우저에서 발생하는 행동을 막기 위해 사용
	        e.stopPropagation(); // 이벤트 버블링을 막기위해서 사용
	    }).on("dragover", function(e){ /* "dragover" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 머물러 있는 중일 때. 필수이벤트이다. dragover 이벤트를 적용하지 않으면 drop 이벤트가 작동하지 않음 */ 
	        e.preventDefault();
	        e.stopPropagation();
	        $(this).css("border", "2px dashed #558ed5"); 
	    }).on("dragleave", function(e){ /* "dragleave" 이벤트는 Drag 한 파일이 드롭대상인 박스 밖으로 벗어났을 때  */
	        e.preventDefault();
	        e.stopPropagation();
	        $(this).css("background-color", "#fff");
	    }).on("drop", function(e){      /* "drop" 이벤트는 드롭대상인 박스 안에서 Drag 한것을 Drop(Drag 한 파일(객체)을 놓는것) 했을 때. 필수이벤트이다. */
	        e.preventDefault();
	
	        var files = e.originalEvent.dataTransfer.files;  
			
	        if(files != null && files != undefined){
	   		     
	        	for(let i=0; i<files.length; i++){
	        		
	        		let html = "";
	        		
	            	let f = files[i];
	            	let fileName = f.name;  // 파일명
	            	let fileSize = f.size/1024/1024;  // 파일크기	/* 파일의 크기는 MB로 나타내기 위하여 /1024/1024 하였음 */
	             
	             	if(fileSize >= 10) {
		        		alert("10MB 이상인 파일은 업로드할 수 없습니다.!!");
		        		$(this).css("background-color", "#fff");
		        		return;
		        	}
		        	else {
		        		file_arr.push(f); //  드롭대상인 박스 안에 첨부파일을 드롭하면 파일들을 담아둘 배열인 file_arr 에 파일들을 저장
			            fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
		                arrLength = file_arr.length; 
		                html += 
		                    "<tr id='fileList' >" + 
		                        "<td>" + 
		                        	"<div class='fileName'>" + 
		                        		"<img src='/static/images/common/icon/xlsx.png' alt=''>" + fileName + " (" + fileSize + "MB) " +
		                        		"<a href='#' class='icon file_delete'></a>" +
				                    	"<input type='hidden' name='attach' value='normal'>" +
				                    "</div>" +
				                "</td>" +
			                    "<td>" +
			                    	"<span class='bt_left_txt'></span>" +
			                    "</td>" +
		                    "</tr>";
		                    
			            $("tbody").append(html);
		        	}
	             
	            } // end of for ------------------------
	        	
	        }// end of if(files != null && files != undefined)--------------------------
	        
	        $(this).css("background-color", "#fff");
	        $(this).find("div#m_fileupload_drag_txt").css("display", "none");
	    	$(this).find("div#div_m_attach_file_list").css("display", "block");
	    });
		
		
	    // == Drop 되어진 파일목록 제거하기 == // 
	    $(document).on("click", "a.file_delete", function(e){
	    	let idx = $("a.file_delete").index($(e.target));
	    
	    	file_arr.splice(idx,1); // 드롭대상인 박스 안에 첨부파일을 드롭하면 파일들을 담아둘 배열인 file_arr 에서 파일을 제거시키도록 한다.
	    
            $(e.target).parent().parent().parent().remove(); // <tr id='fileList'> 태그를 삭제하도록 한다.	 
            
            if(file_arr.length == 0){
            	$("div#m_fileupload_drag_txt").css("display", "");
            	$("div#div_m_attach_file_list").css("display", "none");
    	    }
	    });
	    <%-- === jQuery 를 사용하여 드래그앤드롭(DragAndDrop)을 통한 파일 업로드 끝 === --%>
	    
	    
	 	// 글쓰기 버튼
		$("a#sendMail").click(function(){
			$("input:hidden[name='isTemporary']").val("0"); // 글쓰기 버튼을 눌렸을 경우에는 isTemporary값을 1로 설정.
		});// end of $("a#sendMail").click(function(){})--------------
		
		
		// 저장하기 버튼
		$("a#tempMail").click(function(){
		    $("input:hidden[name='isTemporary']").val("1"); // 저장하기 버튼을 눌렸을 경우에는 isTemporary값을 1로 설정.
		});// end of $("a#tempMail").click(function(){})--------------
		
	    
	 	// 글쓰기 버튼 또는 저장하기 버튼을 클릭했을 경우 
		$("a#sendMail, a#tempMail").click(function(){
			
			<%-- === 스마트 에디터 구현 시작 === --%>
		    // id가 content인 textarea에 에디터에서 대입
		    obj.getById["mailContent"].exec("UPDATE_CONTENTS_FIELD", []);
		    <%-- === 스마트 에디터 구현 끝 === --%>
			
		 	// 받는사람 유효성 검사
			var incomeMail = $("textarea[name='incomeMail']").val().trim();
			if(incomeMail == "") {
				alert("받는사람을 입력하세요!");
			  	return; // 종료
			}
			else{
		
				if( incomeMail.substring(incomeMail.length-1, incomeMail.length) == ';'   ){  // 마지막문자가 ; 라면 ; 없애기
					incomeMail = incomeMail.substring(0, incomeMail.length-1);	
				}		
			
				incomeMail = incomeMail.split(';');
				$("input:hidden[name='incomeEmpCount']").val(incomeMail.length); // 몇명인지 알아온 값을 넣어주기
				$("input[name='incomeEmpId']").remove();
				
				var incomeMail_result = 1;  
				let html = "";
            	let incomeEmp_str = "";
            	
				$.each(incomeMail, function(index, item){
					
					if(!incomeMail[index].includes('@')){
						incomeMail[index] = incomeMail[index].trim() + "@project.com";
					}
					
					$.ajax({
		            	url : "<%=ctxPath%>/mail/isExistMail.gw",
		                type : "post",
		                data : {"email":incomeMail[index]},
		            	dataType:"json",
		            	async : false,
		                success:function(json){
		                	if(json.n == 0){
		                		incomeMail_result *= json.n;
	                 		}
	                 		else{
    	                		html += "<input type='text' name='incomeEmpId' value='"+json.empId+"'/>";
    	                		incomeEmp_str += json.empMail + ",";
    	                	}
		                },
		                error: function(request, status, error){
							alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
						}
					});
					
					if(incomeMail_result == 0){
						alert("받는 사람 리스트에 올바르지 않은 사내 메일 주소가 포함되어 있습니다.");
						return false;
					}
					
				});
				
				if(incomeMail_result == 1){
                 	let html_incomeEmp_str = "<div id='incomeEmp_str' name='incomeEmp_str' style='display: none;'>"+incomeEmp_str+"</div>";
                 	
                 	$("input:hidden[name='incomeEmpCount']").append(html);
                 	$("input:hidden[name='incomeEmpCount']").parent().append(html_incomeEmp_str);
             	}
				else{
					return false;
				}
			}
				
			
			// 참조 유효성 검사
			var reference = $("textarea[name='referenceMail']").val().trim();
			if(reference != "") {
				
				if (reference.substring(reference.length - 1, reference.length) == ';') {  // 마지막문자가 ; 라면 ; 없애기
					reference = reference.substring(0, reference.length - 1);
				}

				reference = reference.split(';');
				$("input:hidden[name='referenceEmpCount']").val(reference.length); // 몇명인지 알아온 값을 넣어주기

				$("input[name='referenceEmpId']").remove();

				
				var reference_result = 1;    
				let html = "";	
				let referenceEmp_str = "";
				
				$.each(reference, function (index, item) {
					
					if (!reference[index].includes('@')) {
						reference[index] = reference[index].trim() + "@project.com";
					}
					
					$.ajax({
		            	url : "<%=ctxPath%>/mail/isExistMail.gw",
		                type : "post",
		                data : { "email":reference[index] },
		            	dataType:"json",
		            	async : false,
		                success:function(json){
		                	if (json.n == 0) {
		    					reference_result *= json.n;
		    				}
		    				else {
		    					html += "<input type='text' name='referenceEmpId' value='"+json.empId+"'/>";
		    					referenceEmp_str += json.empMail + ",";
		    				}	
		                },
		                error: function(request, status, error){
							alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
						}
					}); 
					
					if (reference_result == 0) {
						alert("참조 리스트에 올바르지 않은 사내 메일 주소가 포함되어 있습니다.");
						return false;
					}
					
				});
				
				if(reference_result == 1){
					let html_referenceEmp_str = "<div id='referenceEmp_str' name='referenceEmp_str' style='display: none;'>" + referenceEmp_str + "</div>";

	        		$("input:hidden[name='referenceEmpCount']").append(html);
	        		$("input:hidden[name='referenceEmpCount']").parent().append(html_referenceEmp_str);
	        	}
				else{
					return false;
				}
			}
			
			
			// 숨은참조 유효성 검사
			var hiddenReference = $("textarea[name='hiddenReferenceMail']").val().trim();
			if(hiddenReference != "") {
				
				if (hiddenReference.substring(hiddenReference.length - 1, hiddenReference.length) == ';') {  // 마지막문자가 ; 라면 ; 없애기
					hiddenReference = hiddenReference.substring(0, hiddenReference.length - 1);
				}

				hiddenReference = hiddenReference.split(';');
				$("input:hidden[name='hiddenReferenceEmpCount']").val(hiddenReference.length); // 몇명인지 알아온 값을 넣어주기

				$("input[name='hiddenReferenceEmpId']").remove();

				
				var hiddenReference_result = 1;   
				let html = "";	
				let hiddenReferenceEmp_str = "";
				
				$.each(hiddenReference, function (index, item) {
					
					if (!hiddenReference[index].includes('@')) {
						hiddenReference[index] = hiddenReference[index].trim() + "@project.com";
					}
					
					$.ajax({
						url : "<%=ctxPath%>/mail/isExistMail.gw",
		                type : "post",
		                data : { "email":hiddenReference[index] },
		            	dataType:"json",
		            	async : false,
		                success:function(json){
		    				if (json.n == 0) {
		    					hiddenReference_result *= json.n;
		    				}
		    				else {
		    					html += "<input type='text' name='hiddenReferenceEmpId' value='"+json.empId+"'/>";
		                 	    hiddenReferenceEmp_str += json.empMail + ",";
		    				}	
		                },
		                error: function(request, status, error){
							alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
						}
					}); 	
					
					if (hiddenReference_result == 0) {
						alert("숨은참조 리스트에 올바르지 않은 사내 메일 주소가 포함되어 있습니다.");
						return false;
					}
					
				});
				
				if(hiddenReference_result == 1){
					let html_hiddenReferenceEmp_str = "<div id='hiddenReferenceEmp_str' name='hiddenReferenceEmp_str' style='display: none;'>" + hiddenReferenceEmp_str + "</div>";

	        		$("input:hidden[name='hiddenReferenceEmpCount']").append(html);
	        		$("input:hidden[name='hiddenReferenceEmpCount']").parent().append(html_hiddenReferenceEmp_str);
	        	}
				else{
					return false;
				}
			}
			
			
		 	// 메일제목 유효성 검사
			const subject = $("input:text[name='subject']").val().trim();
			if(subject == "") {
				alert("메일제목을 입력하세요!");
			  	return; // 종료
			} 
	      
			
			<%-- === 메일내용 유효성 검사(스마트 에디터 사용 할 경우) 시작 === --%>
			let mailContent = $("textarea#mailContent").val();
			  
			// 메일내용 유효성 검사 하기 
		       
		    mailContent = mailContent.replace(/&nbsp;/gi, ""); // 공백을 "" 으로 변환
		    /*    
				대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
				==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
				    그리고 뒤의 gi는 다음을 의미합니다.
				
				   g : 전체 모든 문자열을 변경 global
				   i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
			*/ 
		       
		    mailContent = mailContent.substring(mailContent.indexOf("<p>")+3);
		    mailContent = mailContent.substring(0, mailContent.indexOf("</p>"));
		                
		    if(mailContent.trim().length == 0) {
		    	alert("메일내용을 입력하세요!");
		        return;
		    }
			<%-- === 메일내용 유효성 검사(스마트 에디터 사용 할 경우) 끝 === --%> 
			
			
			var formData = new FormData($("form[name='mailFrm']").get(0)); // $("form[name='mailFrm']").get(0) 폼 에 작성된 모든 데이터 보내기 
		      
			if(file_arr.length > 0) { // 파일첨부가 있을 경우 
	        	
				// 첨부한 파일의 총합의 크기가 10MB 이상 이라면 메일 전송을 하지 못하게 막는다.
	      	  	let sum_file_size = 0;
	          	for(let i=0; i<file_arr.length; i++) {
	            	sum_file_size += file_arr[i].size;
	          	}// end of for-----------------------
	         
	          	if( sum_file_size >= 10*1024*1024 ) { // 첨부한 파일의 총합의 크기가 10MB 이상 이라면 
	            	alert("첨부한 파일의 총합의 크기가 10MB 이상이라서 파일을 업로드할 수 없습니다.!!");
	          	
	        	  	return; // 종료
	          	}
	          	else { // formData 속에 첨부파일 넣어주기
	        		file_arr.forEach(function(item){
	                	formData.append("file_arr", item);  // 첨부파일 추가하기. // "file_arr" 이 키값이고  item 이 밸류값인데 file_arr 배열속에 저장되어진 배열요소인 파일첨부되어진 파일이 되어진다.
	                                                        // 같은 key를 가진 값을 여러 개 넣을 수 있다.(덮어씌워지지 않고 추가가 된다.)
	              });
	          }
	        }
			
			$.ajax({
	            url : "<%= ctxPath%>/mail/mailWriteEnd.gw",
	            type : "post",
	            data : formData,
	            processData:false,  // 파일 전송시 설정 
	            contentType:false,  // 파일 전송시 설정 
	            dataType:"json",
	            success:function(json){
	          	  
	                if(json.result == 1) {
	                	
	                	$("input:hidden[name='isTemporary']").val(json.isTemporary); // 
	                	
	          	  		// 폼(form)을 전송(submit)
	        			const frm = document.mailFrm;
	        		 	frm.method = "post"; 
	        		 	frm.action = "<%=ctxPath%>/mail/mailWriteResult.gw";
	        		 	frm.submit();
	          	  		
	                }
	                else {
	              	  alert("메일쓰기를 실패했습니다.");
	                }
	            },
	            error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			      }
	        });
			 
		});// end of $("a#sendMail, a#tempMail").click(function(){})--------------
	
		
	});// end of $(document).ready(function(){})-----------------

	
	
	// Function Declaration
	function showHiddenReference(){
		$("div#hiddenReference").css("display","");
		$("a#referencePlus").css("display","none");
	}

</script>


<div id="contents">
	
	<form name="mailFrm" enctype="multipart/form-data">
	
		<input type="hidden" name="loginEmpId" value="${sessionScope.loginUser.empId}">
		<input type="hidden" name="mailId" value="${mailId}">

		<div class="content_title" id="content_title">
			<!-- Title -->
			<fieldset>
				<span class="detail_select">
					<a href="javascript:void(0)" id="sendMail">보내기</a>
				</span>
				<span id="sTimeReserveMail" style="float: left; margin-left: -15px; padding-right: 26px;"></span>
				<span class="detail_select">
					<a href="javascript:void(0)" id="tempMail">저장하기</a>
					<input type="hidden" name="isTemporary" />
				</span>
				<span id="sTimeMailSaved" style="margin-right: 127px; float: right;"></span>
			</fieldset>
			<!-- Title End -->
		</div>

		<div class="content_inbox">
			<!-- Contents -->
			<div class="cont_box">
				<fieldset>
					<div class="write_field">

						<div class="write_input">
							<p>받는 사람</p>
							<div class="txt">
								<div class="position">
									<%-- == 원메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId eq null}'>
										<textarea class="cc_addr autocomplete ui-autocomplete-input" name="incomeMail" id="incomeMail" placeholder="메일 주소 사이에 ;(세미콜론)으로 구분하여 입력하세요." autocomplete="off" onkeydown="if(event.keyCode==13)return false;"></textarea>
									</c:if>
									<%-- == 답장메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType ne 4}'>
				     					<textarea class="cc_addr autocomplete ui-autocomplete-input" name="incomeMail" id="incomeMail" placeholder="메일 주소 사이에 ;(세미콜론)으로 구분하여 입력하세요." autocomplete="off" onkeydown="if(event.keyCode==13)return false;">${requestScope.orgMailInfo_map.sendEmpEmail}</textarea>
									</c:if>
									<%-- == 임시저장메일쓰기인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType eq 4}'>
				     					<textarea class="cc_addr autocomplete ui-autocomplete-input" name="incomeMail" id="incomeMail" placeholder="메일 주소 사이에 ;(세미콜론)으로 구분하여 입력하세요." autocomplete="off" onkeydown="if(event.keyCode==13)return false;">${requestScope.orgMailInfo_map.incomeEmp_str}</textarea>
									</c:if>
									<input type="hidden" name="incomeEmpCount" />
								</div>
								<!-- predictive text input -->
								
								<div class="dropdown" style="display: none">
									<ul class="dropdown-menu address2">
										<li>
											<a href="#">
												<span class="point_color">자동 완성 설정</span>
											</a>
										</li>
									</ul>
								</div>
							</div>
						</div>

						<div class="write_input">
							<p>
								참조
								<a href="#" id="referencePlus" onclick="showHiddenReference()">
									<span class="add-sendlist">+</span>
								</a>
							</p>
							<div class="txt">
								<div class="position">
									<%-- == 임시저장메일쓰기가 아닌 경우 == --%>
									<c:if test='${mailType ne 4}'>
										<textarea class="cc_addr" name="referenceMail" id="referenceMail" autocomplete="off" tabindex="2" onkeydown="if(event.keyCode==13)return false;"></textarea>
									</c:if>
									<%-- == 임시저장메일쓰기인 경우 == --%>
									<c:if test='${mailType eq 4}'>
										<textarea class="cc_addr" name="referenceMail" id="referenceMail" autocomplete="off" tabindex="2" onkeydown="if(event.keyCode==13)return false;">${requestScope.orgMailInfo_map.referenceEmp_str}</textarea>
									</c:if>
									<input type="hidden" name="referenceEmpCount" />
								</div>
							</div>
						</div>

						<div class="write_input" style="display: none" id="hiddenReference">
							<p>숨은 참조</p>
							<div class="txt">
								<div class="position">
									<%-- == 임시저장메일쓰기가 아닌 경우 == --%>
									<c:if test='${mailType ne 4}'>
										<textarea class="cc_addr" name="hiddenReferenceMail" id="hiddenReferenceMail" autocomplete="off" tabindex="3" onkeydown="if(event.keyCode==13)return false;"></textarea>
									</c:if>
									<%-- == 임시저장메일쓰기인 경우 == --%>
									<c:if test='${mailType eq 4}'>
										<textarea class="cc_addr" name="hiddenReferenceMail" id="hiddenReferenceMail" autocomplete="off" tabindex="3" onkeydown="if(event.keyCode==13)return false;">${requestScope.orgMailInfo_map.hiddenReferenceEmp_str}</textarea>
									</c:if>
									<input type="hidden" name="hiddenReferenceEmpCount" />
								</div>
							</div>
						</div>

						<div class="write_input">
							<label for="write_txt3">제목</label>
							<div class="txt title">
								<div class="position">
									<%-- == 원메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId eq null}'>
										<input name="subject" id="subject" type="text" value="" tabindex="4" maxlength="180" autocomplete="off">
									</c:if>
									<%-- == 답장메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType ne 4}'>
				     					<input name="subject" id="subject" type="text" value="RE: ${requestScope.orgMailInfo_map.subject}" tabindex="4" maxlength="180" autocomplete="off">
								    </c:if>
								    <%-- == 임시저장메일쓰기인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType eq 4}'>
				     					<input name="subject" id="subject" type="text" value="${requestScope.orgMailInfo_map.subject}" tabindex="4" maxlength="180" autocomplete="off">
								    </c:if>
								</div>
							</div>
						</div>

						<!-- filebox area-->
						<div class="write_input" style="display: block">
							<div class="txt file">
								<div class="position">
									<div class="file-list" id="fileDrop">
										<div class="top">

											
											<div class="left">
												<a href="javascript:void(0)" for="fileAttach" class="addfile" onclick="HiworksMailUploader.selectFiles()">
													
												</a> 
											</div>

										</div>
										<div class="center" style="display: block" id="m_fileupload_drag_txt">여기로 파일을 끌어놓으세요.</div>

										<div class="list" style="display: none" id="div_m_attach_file_list">
											<table id="tbl_m_attach_file_list">
												<caption>파일 첨부 목록으로 파일명, 위치, 기간, 삭제선택으로 구성되어 있습니다.</caption>
												<colgroup>
													<col width="">
													<col width="208">
													<col width="115">
												</colgroup>
												<tbody>


												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- filebox area end-->




						<!-- 스마트에디터 시작 -->
						<div class="write_input">
							<div class="txt edit">
								<div class="position">
									<%-- == 원메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId eq null}'>
										<textarea name="mailContent" id="mailContent" style="width: 100%; height: 500px; outline: none; box-sizing: border-box; padding: 18px 23px; resize: vertical; border: 1px solid #d6d6d6; font-size: 16px; line-height: 1.6; color: #333; display: none">
										</textarea>	
									</c:if>
									<%-- == 답장메일쓰기 인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType ne 4}'>
				     					<textarea name="mailContent" id="mailContent" style="width: 100%; height: 500px; outline: none; box-sizing: border-box; padding: 18px 23px; resize: vertical; border: 1px solid #d6d6d6; font-size: 16px; line-height: 1.6; color: #333; display: none">
											<br><br><br><br><br><br><br><br><br>
											원메일 --------------------------------<br>
											${requestScope.orgMailInfo_map.mailContent}
										</textarea>	
				     				</c:if>
				     				<%-- == 임시저장메일쓰기인 경우 == --%>
									<c:if test='${requestScope.orgMailId ne null && mailType eq 4}'>
				     					<textarea name="mailContent" id="mailContent" style="width: 100%; height: 500px; outline: none; box-sizing: border-box; padding: 18px 23px; resize: vertical; border: 1px solid #d6d6d6; font-size: 16px; line-height: 1.6; color: #333; display: none">
											${requestScope.orgMailInfo_map.mailContent}
										</textarea>	
								    </c:if>
								</div>
							</div>
						</div>
						<!-- 스마트에디터 끝 -->

					</div>
				</fieldset>
			</div>
		</div>

		<%-- === 답장메일인 경우 시작 === --%>
		<input type="hidden" name="orgMailId" value="${requestScope.orgMailInfo_map.mailId}" />
		<%-- === 답장메일인 경우 끝 === --%>

		
	</form>


</div>