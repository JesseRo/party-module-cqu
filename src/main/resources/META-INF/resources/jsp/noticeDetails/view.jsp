<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head>
		 <link rel="stylesheet" href="${basePath }/css/details.css" />
	</head>
	<body>
		 <div class="content_title hidden-xs">
		    消息详情
		</div>
		<div class="details_container">
			<p class="details_title">${meetingTheme }</p>
			<div class="details_content">
			    <div class="details_content_title">
			        <p class="col-sm-6 col-xs-12"><span>会议类型</span>${type }</p>
					<p class="col-sm-6 col-xs-12"><span>开展时间</span>${time }</p>
				</div>
				<div class="details_content_info" style="word-wrap:break-word;padding: 15px 0 200px 0;">${content }</div>
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
		        	<a href="/membertodolist?meetingId=${meetingId }">
		        		<button class="btn btn-default btn-lg main_color_btn">确定</button>
		        	</a>
		        	
		        </div>
		    </div>
		</div>
	</body>
</html>