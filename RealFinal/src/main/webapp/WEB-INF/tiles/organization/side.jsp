<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String ctxPath = request.getContextPath();
%>




<style>
.router-link-exact-active {
    background-color: #dae8f8 !important;
    color: #056ac9 !important;
    font-weight: 600 !important;
}
[data-v-3c987196] .router-link-active:not(.main-button), [data-v-3c987196] .router-link-exact-active:not(.main-button) {
    background-color: #dae8f8 !important;
}    
</style>

<script type="text/javascript">

$(document).ready(function(){

	if('${requestScope.sideType}' != ''){
		$('a#' + '${requestScope.sideType}').addClass('router-link-exact-active');
	}
	
});// end of $(document).ready(function(){})-------


// Function Declaration


</script>





<div class="split-item left" style="width: 276px;">
	<div data-v-3c987196="" class="sidebar" style="f616: #f7faff; --3 fa7187a: rgba(41, 133, 219, 0.1); - -be93eab8: #056ac9;">


		<div data-v-3c987196="" class="vb vb-visible" style="width: auto; height: calc(100% - 135px); position: relative; overflow: hidden;">
			<div class="vb-content" style="display: block; overflow: hidden scroll; height: 100%; width: calc(100% + 8px);">
				<nav data-v-3c987196="" class="menu-container">

					<a data-v-3c987196="" href="<%= ctxPath%>/organization/empInfoViewPage.gw" class="menu-item mt-8" id="empInfo">
						<i data-v-3c987196="" class="gis gi-chart-hr link-prefix-icon"></i>
						<span data-v-3c987196="" class="collapse-title">임직원정보</span>
					</a>


					<hr data-v-3c987196="" class="mt-18 mb-18">
					
					<c:if test="${not empty sessionScope.loginUser && (sessionScope.loginUser.adminType eq 'Personnel' || sessionScope.loginUser.adminType eq 'All')}">
					<div data-v-3c987196="" role="tablist" aria-multiselectable="true" class="el-collapse">
						<div data-v-318760de="" data-v-3c987196="" class="el-collapse-item mt-8 is-active">
							<div role="tab" aria-expanded="true" aria-controls="el-collapse-content-8720" aria-describedby="el-collapse-content-8720">
								<div role="button" id="el-collapse-head-8720" tabindex="0" class="el-collapse-item__header is-active">
									<span data-v-318760de="" class="collapse-title-wrapper menu-item">
										<i data-v-318760de="" class="fal fa-chevron-down collapse-prefix-icon"></i>
										<span data-v-318760de="" class="collapse-title">조직/임직원관리</span>
									</span>
									<i class="el-collapse-item__arrow el-icon-arrow-right is-active"></i>
								</div>
							</div>
							<div role="tabpanel" aria-labelledby="el-collapse-head-8720" id="el-collapse-content-8720" class="el-collapse-item__wrap">
								<div class="el-collapse-item__content">
									<a data-v-318760de="" href="<%= ctxPath%>/organization/organizationManage.gw" class="menu-item sub" id="organizationManage">
										<i data-v-318760de="" class="gis  gi-user-group link-prefix-icon"></i>
										<span data-v-318760de="">조직관리</span>
									</a>
									<a data-v-318760de="" href="<%= ctxPath%>/organization/positionManage.gw" class="menu-item sub" id="positionManage">
										<i data-v-318760de="" class="gis gi-hr-menu link-prefix-icon"></i>
										<span data-v-318760de="">직위/직무설정</span>
									</a>
								</div>
							</div>
						</div>
					</div>
					</c:if>
					
				</nav>
			</div>
		</div>
	</div>
</div>