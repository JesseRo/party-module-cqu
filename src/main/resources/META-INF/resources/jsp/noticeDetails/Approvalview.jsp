<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head>
		 <link rel="stylesheet" href="${basePath }/css/details.css" />
		 <style type="text/css">
		 	.details_content_title >p{
		 		text-align:left!important;
		 	}
		 </style>
	</head>
	<body>
		 <div class="content_title hidden-xs">
		   <c:if test="${orgType == 'secondary'}" >
        		<a href="/approvalplanone" style="color: #ce0000;">审批计划</a>>详细
        	</c:if>
        	<c:if test="${orgType == 'branch'}" >
        		<a href="/approvalplantwo" style="color: #ce0000;">审批计划</a>>详细
        	</c:if>
		</div>
		<div class="details_container">
			<p class="details_title">${meetingTheme }</p>
			<div class="details_content">
			    <div class="details_content_title" style="border-bottom: 0px solid #e1e1e1;">
			        <p class="col-sm-6 col-xs-12"><span>会议类型</span>${type }</p>
					<p class="col-sm-6 col-xs-12"><span>开展时间</span>${time }</p>
				</div>
				<div class="details_content_title" style="border-bottom: 0px solid #e1e1e1;">
			        <p class="col-sm-6 col-xs-12"><span>开展地点：</span>${meetingPlan.place }</p>
					<p class="col-sm-6 col-xs-12"><span>参会人员：</span>${meetingUserName }</p>
				</div>
				<div class="details_content_title" style="border-bottom: 0px solid #e1e1e1;">
					<p class="col-sm-6 col-xs-12"><span>主持人：</span>${meetingPlan.host }</p>
					<p class="col-sm-6 col-xs-12"><span>列席人员：</span>${meetingPlan.sit }</p>
				</div>
				<div class="details_content_title">
					<p class="col-sm-6 col-xs-12"><span>联系人：</span>${meetingPlan.contact }</p>
					<p class="col-sm-6 col-xs-12"><span>联系人电话：</span>${meetingPlan.contact_phone }</p>
				</div>
				<div class="details_content_info" style="word-wrap:break-word">${content }</div>
		        <h2 style="color: #47647a;font-size:14px;">
		        	<u>
		        		<portlet:resourceURL id="/dowloadResourceCommand" var ="download">  
		     				<portlet:param name="attachment_url" value="${attachment }"/>
		     			</portlet:resourceURL>
		     			<c:if test="${ ! empty attachment }">
			        		<span class="glyphicon glyphicon-chevron-right" style="color: #47647a;"></span>&nbsp;&nbsp;附件下载:&nbsp;
			        		<a href="${download}">《${ attName }》</a>
		        		</c:if>
		        	</u>
		        </h2>
		        <div class="btn_group">
		        	<c:if test="${orgType == 'secondary'}" >
		        		<a href="/approvalplanone">
			        		<button class="btn btn-default btn-lg main_color_btn">返回</button>
			        	</a>
		        	</c:if>
		        	<c:if test="${orgType == 'branch'}" >
		        		<a href="/approvalplantwo">
			        		<button class="btn btn-default btn-lg main_color_btn">返回</button>
			        	</a>
		        	</c:if>
		        	
		        </div>
		    </div>
		</div>
	</body>
</html>