<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String ctxPath = request.getContextPath();
%>


<script>
$(document).ready(function() {
	
	$("select[name='formId']").change(function(){
		
		if($("select[name='formId'] option:selected").val() != ""){
			const frm = document.writeDocumentFrm;
			frm.method = "post";
			frm.action = "<%=ctxPath%>/approval/document/write/form.gw";
			frm.submit();
		}else{
			const frm = document.writeDocumentFrm;
			frm.method = "get";
			frm.action = "<%=ctxPath%>/approval/document/write/index.gw";
			frm.submit();
		}
		
	})
	
	
	
	<%-- === jQuery 를 사용하여 드래그앤드롭(DragAndDrop)을 통한 파일 업로드 시작 === --%>
	let file_arr = []; // 첨부된어진 파일 정보를 담아 둘 배열

    // == 파일 Drag & Drop 만들기 == //
    $("div#dragZone").on("dragenter", function(e){ /* "dragenter" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 최초로 들어왔을 때 */ 
        e.preventDefault();
        e.stopPropagation();
        

    }).on("dragover", function(e){ /* "dragover" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 머물러 있는 중일 때. 필수이벤트이다. dragover 이벤트를 적용하지 않으면 drop 이벤트가 작동하지 않음 */ 
        e.preventDefault();
        e.stopPropagation();
        $(this).css("background-color", "#e8ecee");
    }).on("dragleave", function(e){ /* "dragleave" 이벤트는 Drag 한 파일이 드롭대상인 박스 밖으로 벗어났을 때  */
        e.preventDefault();
        e.stopPropagation();
        $(this).css("background-color", "");
    }).on("drop", function(e){      /* "drop" 이벤트는 드롭대상인 박스 안에서 Drag 한것을 Drop(Drag 한 파일(객체)을 놓는것) 했을 때. 필수이벤트이다. */
        e.preventDefault();

        var files = e.originalEvent.dataTransfer.files;  
        

        if(files != null && files != undefined){
            
            let html = $("table#tableApprovalAttach tbody").html();
            const f = files[0]; // 파일 정보 어차피 files.length 의 값이 1 이므로 위의 for문을 사용하지 않고 files[0] 을 사용하여 1개만 가져오면 된다. 
        	let fileSize = f.size/1024/1024;  /* 파일의 크기는 MB로 나타내기 위하여 /1024/1024 하였음 */
        	
        	
        	if(fileSize >= 10) {
        		alert("10MB 이상인 파일은 업로드할 수 없습니다.!!");
        		$(this).css("background-color", "#fff");
        		return;
        	}else {
        		// 10MB 미만일 경우
        		
        		file_arr.push(f); //  드롭대상인 박스 안에 첨부파일을 드롭하면 파일들을 담아둘 배열인 file_arr 에 파일들을 저장시키도록 한다.
	        	const fileName = f.name; // 파일명	
        	
        	    fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
        	    // fileSize 가 1MB 보다 작으면 소수부는 반올림하여 소수점 3자리까지 나타내며, 
                // fileSize 가 1MB 이상이면 소수부는 반올림하여 소수점 1자리까지 나타낸다. 만약에 소수부가 없으면 소수점은 0 으로 표시한다.
                
        	   html += 
                    `<tr>
						<td>
							<div class="filename js-approval-attach">
								<span>` + fileName + ` (` + fileSize + `MB)</span>
								<button type="button" class="icon file_delete js-approval-attach-delete">
									<span class="blind">파일 삭제</span>
								</button>
								<div></div>
							</div>
						</td>
					</tr>`;
				$("table#tableApprovalAttach tbody").html(html);
				$("div#approvalAttachText").hide();
				$("div#approvalAttachList").show();
        	}
        }// end of if(files != null && files != undefined)--------------------------
        
        $(this).css("background-color", "#fff");
    });
	
	
	
	
	
	
	
/* 첨부 파일로 첨부된 거 저장 시작 --------------------------------------------------------------*/
	
	var fileList = new Object();

	// 첨부파일
	$('input#fileApprovalAttachWriteForm').change(function(e) {
		
		
	    fileList = $(this)[0].files;  //파일 대상이 리스트 형태로 넘어온다.
	    
	    html = '';
	    for(var i=0;i < fileList.length;i++){
	    	
	    	
	        var file = fileList[i]; // 파일 정보 자체
	        
	        let fileSize = file.size/1024/1024; // 파일 사이즈 (MB로 변환)
	        
	        if(fileSize >= 10) {
        		alert("10MB 이상인 파일은 업로드할 수 없습니다.!!");
        		return;
        		
        	}else{
        		
        		file_arr.push(file); //  드롭대상인 박스 안에 첨부파일을 드롭하면 파일들을 담아둘 배열인 file_arr 에 파일들을 저장시키도록 한다.
	        	const fileName = file.name; // 파일명	
        	
        	    fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1); // 화면에 보여주기용 반올림
        	    
        	    html += 
                    `<tr>
						<td>
							<div class="filename js-approval-attach">
								<span>` + fileName + ` (` + fileSize + `MB)</span>
								<button type="button" class="icon file_delete js-approval-attach-delete">
									<span class="blind">파일 삭제</span>
								</button>
								<div></div>
							</div>
						</td>
					</tr>`;
				
        	}
	    }
	    $("div#approvalAttachText").hide();
		$("div#approvalAttachList").show();
	    $("table#tableApprovalAttach tbody").append(html);
	    
	    
	    
	});
	
	
	
	
	
	// 파일 모달에서 삭제 버튼 클릭시 ------------------------------------------------------------------------------
	$(document).on("click", "button.js-approval-attach-delete", function(e){
		var this_fileDetail = $(e.target).parent().find("span").html();
		for(var i=0;i < file_arr.length;i++){
			// file_arr에서 삭제하기 위해
			
			var file = file_arr[i]; // 파일 정보 자체
			
			var fileName = file.name;
			var fileSize = file.size/1024/1024;
			fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
			
			var fileDetail = fileName.substring(fileName.lastIndexOf("\\") +1, fileName.length) + " (" + fileSize +"MB)";
			if(fileDetail == this_fileDetail){
				file_arr.splice(i, 1);
				break;
			}
		 }
		$(e.target).parent().parent().parent().detach();
		
		if(file_arr.length == 0){
	    	$("div#approvalAttachText").show();
			$("div#approvalAttachList").hide();
	    }
	})
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<%-- === #166. 스마트 에디터 구현 시작 === --%>
 	//전역변수
    var obj = [];
    
    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: obj,
        elPlaceHolder: "compose_contents", // id가 content인 textarea에 에디터를 넣어준다.
        sSkinURI: "<%=ctxPath%>/resources/smarteditor/SmartEditor2Skin_noImage.html",
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
	
	
   
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   /* 기안하기 버튼 클릭시 */
   $("button#btnWrite").click(function(){
	
		<%-- === 스마트 에디터 구현 시작 === --%>
		// id가 content인 textarea에 에디터에서 대입
		     obj.getById["compose_contents"].exec("UPDATE_CONTENTS_FIELD", []);
		<%-- === 스마트 에디터 구현 끝 === --%>
		
		let isCheck = true;
		
		/* 제목 내용 유효성 검사 */
	   	if($("input#approval_document_title").val().trim() == ""){
	   		alert("제목을 입력하세요.")
	   		isCheck = false;
	   	}
		
		
		
	   	/* 보안등급 체크 여부 */
		if($("select[name='security_level'] option:selected").val() == ""){
			alert("보안등급을 선택하세요.")
			isCheck = false;
		}
			
		
		
		
	    var formData = new FormData($("form[name='writeDocumentFrm']").get(0)); // $("form[name='addFrm']").get(0) 폼 에 작성된 모든 데이터 보내기 
	     
	     if(file_arr.length > 0) { // 파일첨부가 있을 경우 
	      
			// 첨부한 파일의 총합의 크기가 10MB 이상 이라면 메일 전송을 하지 못하게 막는다.
	   	  	let sum_file_size = 0;
	     	for(let i=0; i<file_arr.length; i++) {
	     		sum_file_size += file_arr[i].size;
	     	}// end of for---------------
	            
			if( sum_file_size >= 10*1024*1024 ) { // 첨부한 파일의 총합의 크기가 10MB 이상 이라면 
			    alert("첨부한 파일의 총합의 크기가 10MB 이상이라서 파일을 첨부할 수 없습니다!");
			 	return; // 종료
			 
			}else { // formData 속에 첨부파일 넣어주기
				
				file_arr.forEach(function(item){
				       formData.append("file_arr", item);  // 첨부파일 추가하기. // "file_arr" 이 키값이고  item 이 밸류값인데 file_arr 배열속에 저장되어진 배열요소인 파일첨부되어진 파일이 되어진다.
				                                           // 같은 key를 가진 값을 여러 개 넣을 수 있다.(덮어씌워지지 않고 추가가 된다.)
				});
			       
			}
	     }
		
		
		
		
		if(isCheck){
			// 문제 없을 경우
			$.ajax({
				url : "<%=ctxPath%>/approval/insertDocumentWrite/businessContact.gw",
				type : "post",
				data : formData,
				processData:false,  // 파일 전송시 설정 
				contentType:false,  // 파일 전송시 설정 
				dataType:"json",
				success:function(text){
					if(text.isSuccess) {
						let type = '';
						if(text.viewType == 'list'){
							type = 'A';
						}else{
							type = 'all';
						}
						$(location).attr('href', `<%=ctxPath%>/approval/documentDetail/` + text.viewType + `/` + type + `/view.gw?formId=107&approvalId=`+text.approvalId);
					}else {
						alert("기안하기가에 실패했습니다.");
					} 
				},
				error: function(request, status, error){
					alert("기안하기에 실패했습니다.");
			    }
			});
		}
		
		
		
			   
   })
   
   
   
   //////////////////////////////////////////////////////////////////////////////////////////////////
   /* 임시저장 버튼 클릭시 */
   $("button#btnTemp").click(function(){
	
		<%-- === 스마트 에디터 구현 시작 === --%>
		// id가 content인 textarea에 에디터에서 대입
		     obj.getById["compose_contents"].exec("UPDATE_CONTENTS_FIELD", []);
		<%-- === 스마트 에디터 구현 끝 === --%>
		
		let isCheck = true;
		
		/* 제목 내용 유효성 검사 */
	   	if($("input#approval_document_title").val().trim() == ""){
	   		alert("제목을 입력하세요.")
	   		isCheck = false;
	   	}
		
	   	/* 보안등급 체크 여부 */
		if($("select[name='security_level'] option:selected").val() == ""){
			alert("보안등급을 선택하세요.")
			isCheck = false;
		}
		
	    var formData = new FormData($("form[name='writeDocumentFrm']").get(0)); 
	     
	     if(file_arr.length > 0) { // 파일첨부가 있을 경우 
	      
			// 첨부한 파일의 총합의 크기가 10MB 이상 이라면 메일 전송을 하지 못하게 막는다.
	   	  	let sum_file_size = 0;
	     	for(let i=0; i<file_arr.length; i++) {
	     		sum_file_size += file_arr[i].size;
	     	}// end of for---------------
	            
			if( sum_file_size >= 10*1024*1024 ) { // 첨부한 파일의 총합의 크기가 10MB 이상 이라면 
			    alert("첨부한 파일의 총합의 크기가 10MB 이상이라서 파일을 첨부할 수 없습니다!");
			 	return; // 종료
			 
			}else { // formData 속에 첨부파일 넣어주기
				
				file_arr.forEach(function(item){
				       formData.append("file_arr", item);  // 첨부파일 추가하기. // "file_arr" 이 키값이고  item 이 밸류값인데 file_arr 배열속에 저장되어진 배열요소인 파일첨부되어진 파일이 되어진다.
				                                           // 같은 key를 가진 값을 여러 개 넣을 수 있다.(덮어씌워지지 않고 추가가 된다.)
				});
			       
			}
	     }
		
		
		if(isCheck){
			// 문제 없을 경우
			$.ajax({
				url : "<%=ctxPath%>/approval/insertTempDocumentWrite/businessContact.gw",
				type : "post",
				data : formData,
				processData:false,  // 파일 전송시 설정 
				contentType:false,  // 파일 전송시 설정 
				dataType:"json",
				success:function(text){
					if(text.isSuccess) {
						$(location).attr('href', `<%=ctxPath%>/approval/document/box/temp.gw`);
					}else {
						alert("임시저장에 실패했습니다.");
					} 
				},
				error: function(request, status, error){
					alert("임시저장에 실패했습니다.");
			    }
			});
		}
		
		
		
			   
   })
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// 결재 td 부분 관리 시작------------------------------------------
	// 화면에 보여질 수 - 결재자 수 
	<c:if test="${not empty requestScope.approvalDetail}">
		let cnt = 7;
		
		<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
			<c:if test="${procedure.procedureType eq '결재'}">
				cnt--;
			</c:if>
		</c:forEach>
		
		if(cnt > 0){
			let html_position = $("tr#approvalProcedureData_position").html();
			let html_stamp = $("tr#approvalProcedureData_stamp").html();
			let html_empName = $("tr#approvalProcedureData_empName").html();
			
			while(cnt != 0){
				html_position += `<td class="team name"></td>`;
				html_stamp += `<td class="stamp"></td>`;
				html_empName += `<td class="name gt-position-relative"></td>`;
				cnt--;
			}
			
			$("tr#approvalProcedureData_position").html(html_position)
			$("tr#approvalProcedureData_stamp").html(html_stamp)
			$("tr#approvalProcedureData_empName").html(html_empName)
		}
		
	</c:if>
	
	
	// 결재 td 부분 관리 끝------------------------------------------
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// 결재 + 버튼_사원명 입력시
	let prev_autoComplete = $("ul.approval_autocomplete").html();
	$("input#inputApprovalLineSetting").focus(function(){
		
		if($("input#inputApprovalLineSetting").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalLineSetting").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;

					}
					
					if(text.length > 0){
						$("ul.approval_autocomplete").html(html);
						$("ul.approval_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.approval_autocomplete").show();
						
					}else{
						$("ul.approval_autocomplete").html(prev_autoComplete);
						$("ul.approval_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
					alert("문제가 발생하였습니다. 다시 시도하여 주세요.")
				}
			});
		}else{
			$("ul.approval_autocomplete").html(prev_autoComplete);
			$("ul.approval_autocomplete").hide();
		}
		
	}).keyup(function(){
		
		if($("input#inputApprovalLineSetting").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalLineSetting").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1">
						<div>
						<span class="team-membername" id="addEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span>
						<span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span>
						<span style="display:none;">` + text[i]['positionName'] +`</span>
						</div></li>`;
					}
					
					if(text.length > 0){
						$("ul.approval_autocomplete").html(html);
						$("ul.approval_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.approval_autocomplete").show();
						
					}else{
						$("ul.approval_autocomplete").html(prev_autoComplete);
						$("ul.approval_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
					alert("문제가 발생하였습니다. 다시 시도하여 주세요.")
				}
			});
		}else{
			$("ul.approval_autocomplete").html(prev_autoComplete);
			$("ul.approval_autocomplete").hide();
		}
		
	})
	
	
	
	$("input#inputApprovalLineSetting").focusout(function(){
		$("ul.approval_autocomplete").html(prev_autoComplete);
		$("ul.approval_autocomplete").hide();
	}) 
	
	// 검색어 목록에서 선택하였을 경우
	$(document).on("mousedown", "ul.approval_autocomplete li", function() {
		
		let html = $("ul#sortApprovalLineSetting").html();
		let id = $(this).find('span:eq(0)').attr('id');
		let empId = id.substring(id.indexOf('_') +1,id.length);
		let empName = $(this).find('span:eq(0)').html();
		let positionName = $(this).find('span:eq(2)').html();
		
		
		let isExist = false;
		
		if(empId == `${sessionScope.loginUser.empId}`){
			isExist = true;
		}
		
		<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
			if("${procedure.procedureType}" != '결재' && ${procedure.empId} == empId){
				isExist = true;
				
			}
			
		</c:forEach>
		
		$("ul#sortApprovalLineSetting li").each(function(){
			// 리스트 안에도 있는 지 확인
			let liId = $(this).attr('id');
			let liEmpId = liId.substring(liId.indexOf('_') +1,liId.length);
			
			if(liEmpId == empId){
				isExist = true;
				return false;
			}
		})
		
		
		$("td#approvalSecondLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalThirdLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalFourthLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		if(isExist){
			// 이미 존재한다면
			alert("이미 포함된 결재자는 중복으로 설정할 수 없습니다.")
			$("input#inputApprovalLineSetting").val('');
		}else{
			html += `<li class="js-approval-line-setting unsortable" id="approvalLineSettingEmpId_` + empId +`" style="cursor: auto;">
			<span>` + empName 
				+ `<span class="icon file_delete js-approval-line-setting-delete" onclick ="deleteThis(approvalLineSettingEmpId_` + empId + `)"></span>
			</span>
			<span style="display:none;">` + positionName + `</span></li>`;
			$("ul#sortApprovalLineSetting").html(html);
			$("input#inputApprovalLineSetting").val('');
			
		}
	}); 
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 참조 + 버튼_입력시
	
	let prev_refAutoComplete = $("ul.reference_autocomplete").html();
	$(document).on("focus", "input#inputApprovalSecondLine", function(){
		
		if($("input#inputApprovalSecondLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalSecondLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addRefEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.reference_autocomplete").css('top','527px');
						$("ul.reference_autocomplete").html(html);
						$("ul.reference_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.reference_autocomplete").show();
						
					}else{
						$("ul.reference_autocomplete").html(prev_refAutoComplete);
						$("ul.reference_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.reference_autocomplete").html(prev_refAutoComplete);
			$("ul.reference_autocomplete").hide();
		}
		
	});
	
	$(document).on("keyup", "input#inputApprovalSecondLine", function(){
		
		if($("input#inputApprovalSecondLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalSecondLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addRefEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.reference_autocomplete").css('top','527px');
						$("ul.reference_autocomplete").html(html);
						$("ul.reference_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.reference_autocomplete").show();
						
					}else{
						$("ul.reference_autocomplete").html(prev_refAutoComplete);
						$("ul.reference_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.reference_autocomplete").html(prev_refAutoComplete);
			$("ul.reference_autocomplete").hide();
		}
		
	})
	
	// 입력하다가 다른 거 선택했을경우
	$(document).on("focusout", "input#inputApprovalSecondLine", function(){
		$("ul.reference_autocomplete").html(prev_refAutoComplete);
		$("ul.reference_autocomplete").hide();
	}) 
	
	// 드롭다운 리스트에서 선택했을 경우
	$(document).on("mousedown", "ul.reference_autocomplete li", function() {
		
		let html = $("td#approvalSecondLine").html();
		let id = $(this).find('span:eq(0)').attr('id');
		let empId = id.substring(id.indexOf('_') +1,id.length);
		let empName = $(this).find('span:eq(0)').html();
		
		let isExist = false;
		
		if(empId == `${sessionScope.loginUser.empId}`){
			isExist = true;
		}
		
		<c:if test="${not empty requestScope.approvalDetail}">
			<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
				if(${procedure.empId} == empId){
					isExist = true;
				}
			</c:forEach>
		</c:if>
		
		$("tr#approvalProcedureData_empName td").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalSecondLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalThirdLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
			
			if(sEmpId == empId){
				isExist = true;
				return false;
			}
		})
		
		$("td#approvalFourthLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		if(isExist){
			// 이미 존재한다면
			alert("이미 포함된 결재자는 중복으로 설정할 수 없습니다.")
			$("input#inputApprovalSecondLine").val('');
			
		}else{
			//table 에 추가한다
			
			let html_approvalSecondLine = $("td#approvalSecondLine").html();
			html_approvalSecondLine += `<span class="refer-list" id="referEmpId_` + empId + `" user_no="3855" node_id="2816" type="F">`
										+		empName 
										+   `<input type="hidden" name="referEmpId" value="` + empId + `" />`
										+	`<span class="icon file_delete js-approval-line-delete" onclick="deleteThis(referEmpId_` + empId + `)" style="display: inline-block;"></span>`
										+ `</span>`;
			
			$("td#approvalSecondLine").html(html_approvalSecondLine);
		}
	})
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 수신 + 버튼_입력시
	
	let prev_incAutoComplete = $("ul.income_autocomplete").html();
	$(document).on("focus", "input#inputApprovalThirdLine", function(){
		
		if($("input#inputApprovalThirdLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalThirdLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addIncEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.income_autocomplete").css('top','575px');
						$("ul.income_autocomplete").html(html);
						$("ul.income_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.income_autocomplete").show();
						
					}else{
						$("ul.income_autocomplete").html(prev_incAutoComplete);
						$("ul.income_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.income_autocomplete").html(prev_incAutoComplete);
			$("ul.income_autocomplete").hide();
		}
		
	})
	
	$(document).on("keyup", "input#inputApprovalThirdLine", function(){
		
		if($("input#inputApprovalThirdLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalThirdLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addIncEmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.income_autocomplete").css('top','575px');
						$("ul.income_autocomplete").html(html);
						$("ul.income_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.income_autocomplete").show();
						
					}else{
						$("ul.income_autocomplete").html(prev_incAutoComplete);
						$("ul.income_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.income_autocomplete").html(prev_incAutoComplete);
			$("ul.income_autocomplete").hide();
		}
		
	})
	
	// 입력하다가 다른 거 선택했을경우
	$(document).on("focusout", "input#inputApprovalThirdLine", function(){
		$("ul.income_autocomplete").html(prev_incAutoComplete);
		$("ul.income_autocomplete").hide();
	}) 
	
	// 드롭다운 리스트에서 선택했을 경우
	$(document).on("mousedown", "ul.income_autocomplete li", function() {
		
		let html = $("td#approvalThirdLine").html();
		let id = $(this).find('span:eq(0)').attr('id');
		let empId = id.substring(id.indexOf('_') +1,id.length);
		let empName = $(this).find('span:eq(0)').html();
		
		let isExist = false;
		
		if(empId == `${sessionScope.loginUser.empId}`){
			isExist = true;
		}
		
		<c:if test="${not empty requestScope.approvalDetail}">
			<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
				if(${procedure.empId} == empId){
					isExist = true;
					
				}
			</c:forEach>
		</c:if>
		
		$("tr#approvalProcedureData_empName td").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
			
		})
		
		$("td#approvalSecondLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalThirdLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalFourthLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		if(isExist){
			// 이미 존재한다면
			alert("이미 포함된 결재자는 중복으로 설정할 수 없습니다.")
			$("input#inputApprovalThirdLine").val('');
			
		}else{
			//table 에 추가한다
			
			let html_approvalThirdLine = $("td#approvalThirdLine").html();
			html_approvalThirdLine += `<span class="refer-list" id="incEmpId_` + empId + `" >`
										+ empName
										+   `<input type="hidden" name="incEmpId" value="` + empId + `" />`
										+ `<span class="icon file_delete js-approval-line-delete" onclick="deleteThis(incEmpId_` + empId + `)" style="display: inline-block;"></span>`
									 + `</span>`;
			
			$("td#approvalThirdLine").html(html_approvalThirdLine);
		}
	})
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 수신참조 + 버튼_입력시
	
	let prev_incRAutoComplete = $("ul.inR_autocomplete").html();
	$(document).on("focus", "input#inputApprovalFourthLine", function(){
		
		if($("input#inputApprovalFourthLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalFourthLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addIncREmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.inR_autocomplete").css('top','620px');
						$("ul.inR_autocomplete").html(html);
						$("ul.inR_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.inR_autocomplete").show();
						
					}else{
						$("ul.inR_autocomplete").html(prev_incRAutoComplete);
						$("ul.inR_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.inR_autocomplete").html(prev_incRAutoComplete);
			$("ul.inR_autocomplete").hide();
		}
		
	})
	
	$(document).on("keyup", "input#inputApprovalFourthLine", function(){
		
		if($("input#inputApprovalFourthLine").val().trim() != ''){
			// 검색값이 있을 경우
			
			
			$.ajax({
				url: "<%=ctxPath%>/approval/searchEmpName.gw",
				data: { "empName": $("input#inputApprovalFourthLine").val().trim()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(text) {
					
					let html = '';
					for(let i = 0 ; i < text.length ; i++){
						html += `<li class="ui-menu-item" id="ui-id-11" tabindex="-1"><div><span class="team-membername" id="addIncREmpId_` + text[i]['empId'] +`">` + text[i]['empName'] + `</span><span class="team-name">` + [text[i]['depName']] + ` ` +  text[i]['teamName'] + `</span><span style="display:none;">` + text[i]['positionName'] +`</span></div></li>`;
					}
					
					if(text.length > 0){
						$("ul.inR_autocomplete").css('top','620px');
						$("ul.inR_autocomplete").html(html);
						$("ul.inR_autocomplete li").hover(function(){
							$(this).css('background', '#e8ecee');
						},
						function(){
							$(this).css('background','');
						}
						);
						$("ul.inR_autocomplete").show();
						
					}else{
						$("ul.inR_autocomplete").html(prev_incRAutoComplete);
						$("ul.inR_autocomplete").hide();
					}
				},
				error: function(request, status, error) {
				}
			});
		}else{
			$("ul.inR_autocomplete").html(prev_incRAutoComplete);
			$("ul.inR_autocomplete").hide();
		}
		
	})
	
	// 입력하다가 다른 거 선택했을경우
	$(document).on("focusout", "input#inputApprovalFourthLine", function(){
		$("ul.inR_autocomplete").html(prev_incRAutoComplete);
		$("ul.inR_autocomplete").hide();
	}) 
	
	// 드롭다운 리스트에서 선택했을 경우
	$(document).on("mousedown", "ul.inR_autocomplete li", function() {
		
		let html = $("td#approvalFourthLine").html();
		let id = $(this).find('span:eq(0)').attr('id');
		let empId = id.substring(id.indexOf('_') +1,id.length);
		let empName = $(this).find('span:eq(0)').html();
		
		let isExist = false;
		
		if(empId == `${sessionScope.loginUser.empId}`){
			isExist = true;
		}
		
		<c:if test="${not empty requestScope.approvalDetail}">
			<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
				if(${procedure.empId} == empId){
					isExist = true;
					
				}
			</c:forEach>
		</c:if>
		
		$("tr#approvalProcedureData_empName td").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalSecondLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalThirdLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		$("td#approvalFourthLine > span").each(function(){
			// 리스트 안에도 있는 지 확인
			let sId = $(this).attr('id');
			
			if(typeof sId != "undefined" && sId != "" && sId != null){
				let sEmpId = sId.substring(sId.indexOf('_') +1, sId.length);
				
				if(sEmpId == empId){
					isExist = true;
					return false;
				}
			}
		})
		
		
		if(isExist){
			// 이미 존재한다면
			alert("이미 포함된 결재자는 중복으로 설정할 수 없습니다.")
			$("input#inputApprovalFourthLine").val('');
			
		}else{
			
			
		
		//table 에 추가한다
		
		let html_approvalFourthLine = $("td#approvalFourthLine").html();
		html_approvalFourthLine += `<span class="refer-list" id="incREmpId_` + empId + `" >`
									+ empName
									+   `<input type="hidden" name="incREmpId" value="` + empId + `" />`
									+ `<span class="icon file_delete js-approval-line-delete" onclick="deleteThis(incREmpId_` + empId + `)" style="display: inline-block;"></span>`
								+  `</span>`;
		
		$("td#approvalFourthLine").html(html_approvalFourthLine);
			
			
			
		}
	});
	
	
	
	
	
	
	
	
	
	
})




///////////////////////////////////////////////////////////////////////////////


/* 결재 + 버튼 눌렀을 경우 팝업 */
function approvalLineSetting(){
	
	$("input#inputApprovalLineSetting").val('');
	
	let html = '';
	<c:if test="${not empty requestScope.approvalDetail}">
		<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
			<c:if test="${procedure.procedureType eq '결재'}">
				
				html += `<li class="js-approval-line-setting unsortable" id ="approvalLineSettingEmpId_${procedure.empId}" style="cursor: auto;">`
				        + `<span>${procedure.empName}`
						+ `<span class="icon file_delete js-approval-line-setting-delete" onclick ="deleteThis(approvalLineSettingEmpId_${procedure.empId})"></span></span>`
						+ `<span style="display:none;">${procedure.positionName}</span></li>`;
				
			</c:if>
			
		</c:forEach>
	</c:if>
	
	<c:if test="${empty requestScope.approvalDetail}">
	
		
		$("tr#approvalProcedureData_empName td").each(function(i){
			
			 if($(this).html() != ''){
				// 값이 비어있지않다면 넣어준다
				let id = $(this).attr('id');
				let empId = id.substring(id.indexOf('_') +1,id.length);
				let empName = $(this).text();
				let positionName = $($("tr#approvalProcedureData_position td")[i]).text();
				
				
				html += `<li class="js-approval-line-setting unsortable" id ="approvalLineSettingEmpId_`+ empId + `" style="cursor: auto;">`
						+ `<span>` + empName;
				if(empId != ${sessionScope.loginUser.empId}){
					html += `<span class="icon file_delete js-approval-line-setting-delete" onclick ="deleteThis(approvalLineSettingEmpId_` + empId +`)"></span>`;
				}else{
					html += `<span class="icon file_delete js-approval-line-setting-delete" onclick ="deleteThis(approvalLineSettingEmpId_` + empId +`)" style="display:none"></span>`;
				}
				
				html += `</span><span style="display:none;">` + positionName + `</span></li>`;
			 }
			 
		 })
	
	</c:if>
	
	$("ul#sortApprovalLineSetting").html(html);
	
	$("div#layerApprovalLineSetting").show(); 

}



/* 결재 + 버튼 눌러 모달창에서 확인 버튼 눌렀을 경우 */
function updateApprovalLineSetting(){
	
	
	 if($("li.js-approval-line-setting").length > 0  && $("li.js-approval-line-setting").length <= 7){
		 
		 $("li.js-approval-line-setting").each(function(index){
			
			let id = $(this).attr('id');
			let empId = id.substring(id.indexOf('_') +1,id.length);
			let empName = $(this).find('span:eq(0)').text();
			let positionName = $(this).find('span:eq(2)').html();
			 // 결재 table태그에 이름 넣기
			 
			 
			 $($("tr#approvalProcedureData_position td")[index]).html(positionName)
			 $($("tr#approvalProcedureData_empName td")[index]).attr('id', 'approvalEmpId_'+empId)
			 $($("tr#approvalProcedureData_empName td")[index]).addClass('approvalEmpId_'+empId)
			 $($("tr#approvalProcedureData_empName td")[index]).html( `<input type="hidden" name="approvalEmpId" value="` + empId + `" />` + empName)
			 
		 })
		 
		for(i = 6; i >= $("li.js-approval-line-setting").length ; i--){
			let className = $($("tr#approvalProcedureData_empName td")[i]).attr("class");
			if(className.indexOf('approvalEmpId_') != -1){
				// 값이 있을 경우
				$($("tr#approvalProcedureData_empName td")[i]).removeClass(className.substring(className.indexOf('approvalEmpId_'), className.length));
			}
			
			
			 $($("tr#approvalProcedureData_position td")[i]).html('')
			 $($("tr#approvalProcedureData_empName td")[i]).attr('id', '')
			 $($("tr#approvalProcedureData_empName td")[i]).html('')
		}
		 $("div#layerApprovalLineSetting").hide(); 
		 
	 }else{
		 alert("결재자는 1명 이상 7명 이하로 설정해야합니다.")
	 }
	 
	 
} 



//////////////////////////////////////////////////////////////////////////////////////////////
// 결재 + 버튼_사원명 삭제 버튼 클릭시

// 결재 리스트에서 지우기
function deleteThis(id) {
	$(id).detach();
}



/////////////////////////////////////////////////////////////////////////////////////////////
function clickTriggerToFileApprovalAttachWriteForm() {
	$("input#fileApprovalAttachWriteForm").trigger("click");
}




</script>





<script type="text/javascript" src="<%=ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>



<div id="contents">
	<form name="writeDocumentFrm">
		<c:if test="${not empty requestScope.approvalDetail}">
			<input type="hidden" name="approvalId" value="${requestScope.approvalDetail.approvalId}" />
		</c:if>

		<!-- <form autocomplete="off" onsubmit="return false;"> -->
		<input type="submit" style="display: none;" onclick="return false;">
		<div class="content_title">
			<fieldset style="max-width: 969px;">
				<span class="detail_select" id="btnWriteSaveDocument">
					<button type="button" id="btnWrite">기안하기</button>
				</span>
				<span class="detail_select hide" id="btnTempSaveDocument" style="display: inline;">
					<button type="button" id="btnTemp">임시저장</button>
				</span>
			</fieldset>
		</div>
		<div class="content_inbox">
			<div class="cont_box write">
				<div class="approval-wrap write">
					<h4 style="display: inline-block">
						기본 설정
					</h4>
					<table class="tableType02">
						<caption>전자결재 기본 설정</caption>
						<colgroup>
							<col style="width: 12.15%;">
							<col style="width: 44.94%">
							<col style="width: 12.15%">
							<col style="width: 30.76%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">문서 종류</th>
								<td>
									<select name="formId" class="write-select" autocomplete="off">
										<option value="">선택</option>
										<c:forEach var="formVo" items="${requestScope.formList}">
											<c:if test="${formVo.formId eq 107}">
												<option value="${formVo.formId}" selected>${formVo.formName}</option>
											</c:if>
											<c:if test="${formVo.formId ne 107}">
												<option value="${formVo.formId}">${formVo.formName}</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
								<th scope="row">작성자</th>
								<td>
									<input type="hidden" name="empId" value="${sessionScope.loginUser.empId}">
									[${sessionScope.loginUser.depName}] ${sessionScope.loginUser.teamName}
									<span>${sessionScope.loginUser.positionName} ${sessionScope.loginUser.empName}</span>
								</td>
							</tr>
							<tr>
								<th scope="row">보존 연한</th>
								<td>${requestScope.preservationYear}년</td>
								<th scope="row">보안 등급</th>
								<td>
									<select name="security_level" class="fl write-select mgl_10 view">
										<option value="">보안 등급</option>

										<c:forEach var="securityVo" items="${requestScope.securityLevelList}">

											<c:if test="${not empty requestScope.approvalDetail}">
												<c:if test="${requestScope.approvalDetail.securityId eq securityVo.securityId}">
													<option value="${securityVo.securityId}" selected>${securityVo.securityLevel}등급</option>
												</c:if>


												<c:if test="${requestScope.approvalDetail.securityId ne securityVo.securityId}">
													<option value="${securityVo.securityId}">${securityVo.securityLevel}등급</option>
												</c:if>
											</c:if>

											<c:if test="${empty requestScope.approvalDetail}">
												<option value="${securityVo.securityId}">${securityVo.securityLevel}등급</option>
											</c:if>

										</c:forEach>
									</select>


								</td>
							</tr>
						</tbody>
					</table>












					<div class="after">
						<h4 class="fl mgr_20">결재선</h4>
					</div>

					<div id="approvalDocumentLine">
						<table class="cal_table1 approve-write js-approval-line">
							<caption>결재선</caption>
							<colgroup>
								<col style="width: 12.09%;">
								<col style="width: 87.91%;">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="sign">
										<div style="height: 162px; display: table-cell; width: 108px; vertical-align: middle; text-align: center;">
											결재
											<span class="spr-approval set" title="결재" onclick="approvalLineSetting();"></span>
										</div>
									</th>
									<td class="sign vt" id="approvalFirstLine">
										<table style="width: 100%; table-layout: fixed">
											<colgroup>
												<col>
												<col>
												<col>
												<col>
												<col>
												<col>
												<col>
											</colgroup>
											<tbody>
												<tr id="approvalProcedureData_position">
													<c:if test="${not empty requestScope.approvalDetail}">
														<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
															<c:if test="${procedure.procedureType eq '결재'}">
																<td class="team name">${procedure.positionName}</td>
															</c:if>
														</c:forEach>
													</c:if>

													<c:if test="${empty requestScope.approvalDetail}">
														<td class="team name"></td>
														<td class="team name"></td>
														<td class="team name"></td>
														<td class="team name"></td>
														<td class="team name"></td>
														<td class="team name"></td>
														<td class="team name"></td>
													</c:if>
												</tr>
												<tr id="approvalProcedureData_stamp">
													<c:if test="${not empty requestScope.approvalDetail}">
														<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
															<c:if test="${procedure.procedureType eq '결재'}">
																<td class="stamp"></td>
															</c:if>
														</c:forEach>
													</c:if>

													<c:if test="${empty requestScope.approvalDetail}">
														<td class="stamp"></td>
														<td class="stamp"></td>
														<td class="stamp"></td>
														<td class="stamp"></td>
														<td class="stamp"></td>
														<td class="stamp"></td>
														<td class="stamp"></td>
													</c:if>

												</tr>
												<tr id="approvalProcedureData_empName">
													<c:if test="${not empty requestScope.approvalDetail}">
														<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
															<c:if test="${procedure.procedureType eq '결재'}">
																<td class="name gt-position-relative approvalEmpId_${procedure.empId}" id="approvalEmpId_${procedure.empId}">
																	<input type="hidden" name="approvalEmpId" value="${procedure.empId}" />
																	${procedure.empName}
																</td>

															</c:if>
														</c:forEach>
													</c:if>

													<c:if test="${empty requestScope.approvalDetail}">
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
														<td class="name gt-position-relative"></td>
													</c:if>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="cal_table1  approve-write refer js-approval-line">
							<colgroup>
								<col style="width: 12.09%;">
								<col style="width: 87.91%;">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">참조</th>
									<td id="approvalSecondLine">
										<input type="text" class="refer-add js-complete ui-autocomplete-input" placeholder="클릭 후 입력" id="inputApprovalSecondLine" autocomplete="off">

										<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
											<c:if test="${procedure.procedureType eq '참조'}">
												<span class="refer-list" id="referEmpId_${procedure.empId}" user_no="3855" node_id="2816" type="F">
													${procedure.empName}
													<input type="hidden" name="referEmpId" value="${procedure.empId}" />
													<span class="icon file_delete js-approval-line-delete" onclick="deleteThis(referEmpId_${procedure.empId})" style="display: inline-block;"></span>
												</span>
											</c:if>
										</c:forEach>

									</td>
								</tr>
							</tbody>
						</table>
						<table class="cal_table1  approve-write reception js-approval-line">
							<colgroup>
								<col style="width: 12.09%;">
								<col style="width: 87.91%;">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">수신</th>
									<td id="approvalThirdLine">
										<input type="text" class="refer-add js-complete ui-autocomplete-input" placeholder="클릭 후 입력" id="inputApprovalThirdLine" autocomplete="off">

										<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
											<c:if test="${procedure.procedureType eq '수신'}">
												<span class="refer-list" id="incEmpId_${procedure.empId}">
													${procedure.empName}
													<input type="hidden" name="incEmpId" value="${procedure.empId}" />
													<span class="icon file_delete js-approval-line-delete" onclick="deleteThis(incEmpId_${procedure.empId})" style="display: inline-block;"></span>
												</span>
											</c:if>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>

						<table class="cal_table1  approve-write refer2 js-approval-line">
							<colgroup>
								<col style="width: 12.09%;">
								<col style="width: 87.91%;">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">(수신)참조</th>
									<td id="approvalFourthLine">
										<input type="text" class="refer-add js-complete ui-autocomplete-input" placeholder="클릭 후 입력" id="inputApprovalFourthLine" autocomplete="off">

										<c:forEach var="procedure" items="${requestScope.approvalDetail.apvo}">
											<c:if test="${procedure.procedureType eq '수신참조'}">
												<span class="refer-list" id="incREmpId_${procedure.empId}">
													${procedure.empName}
													<input type="hidden" name="incREmpId" value="${procedure.empId}" />
													<span class="icon file_delete js-approval-line-delete" onclick="deleteRefOrIncOrIncR(incREmpId_${procedure.empId})" style="display: inline-block;"></span>
												</span>
											</c:if>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="write_input js-approval-input hide mgt_50" style="display: block;">
						<h4 class="fl">제목</h4>
						<div class="txt file" style="margin-right: 2px;">
							<div>
								<input type="text" name="approval_document_title" id="approval_document_title" placeholder="제목을 입력해 주세요." value="${requestScope.approvalDetail.title}" tabindex="1" autocomplete="off">
							</div>
						</div>
					</div>
					<div class="write_input js-approval-input hide mgt_50" style="display: block;">
						<h4>본문</h4>
					</div>




					<div class="write_input" id="approvalEditorContent" style="display: block;">
						<div class="txt file">
							<div id="dexteditor_holder"></div>
							<textarea name="content" id="compose_contents" style="width: 100%; height: 612px;">${requestScope.approvalDetail.content}</textarea>
						</div>
					</div>





					<div class="write_input js-approval-input mgt_15 hide" style="display: block;">
						<label for="write_txt3" class="blind">파일 첨부</label>
						<div class="txt file">
							<div class="position">
								<div class="top">
									<p class="left"></p>
									<h4 class="fl mgr_20 gt-bold viewAttachedFileArea">별첨</h4>

									<button type="button" class="addfile viewAttachedFileArea" style="color: #779ec0;" onclick="clickTriggerToFileApprovalAttachWriteForm()">파일 첨부</button>
									<input type="file" style="overflow: hidden; width: 0px; height: 0px;" name="approval_attach" id="fileApprovalAttachWriteForm" multiple="">

									<p></p>
								</div>
								<div class="file-list mgt_15" id="dragZone" style="min-height: 66px;">

									<c:if test="${empty requestScope.approvalDetail.fvo}">
										<div class="center viewAttachedFileArea show" id="approvalAttachText">여기로 파일을 끌어놓으세요</div>
									</c:if>
									<c:if test="${not empty requestScope.approvalDetail.fvo}">
										<div class="center viewAttachedFileArea show" id="approvalAttachText" style="display: none;">여기로 파일을 끌어놓으세요</div>
									</c:if>
									<c:if test="${empty requestScope.approvalDetail.fvo}">
										<div class="list gt-mt-5" id="approvalAttachList" style="display: none;">
									</c:if>
									<c:if test="${not empty requestScope.approvalDetail.fvo}">
										<div class="list gt-mt-5" id="approvalAttachList">
									</c:if>

									<table id="tableApprovalRelated">
										<caption></caption>
										<colgroup>
											<col width="">
										</colgroup>
										<tbody></tbody>
									</table>
									<table id="tableApprovalAttach">
										<caption></caption>
										<colgroup>
											<col width="">
										</colgroup>
										<tbody>
											<c:forEach var="approvalFileVo" items="${requestScope.approvalDetail.fvo}">
												<tr>
													<td>
														<div class="filename js-approval-attach">
															<span>${approvalFileVo.fileRName}
																(
																<fmt:formatNumber value="${approvalFileVo.fileSize}" pattern="0.000" />
																MB)
																<input type="hidden" name="orgFiles" value="${approvalFileVo.approvalFileId}" />
																<button type="button" class="icon file_delete js-approval-attach-delete">
																	<span class="blind">파일 삭제</span>
																</button>
																<div></div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>