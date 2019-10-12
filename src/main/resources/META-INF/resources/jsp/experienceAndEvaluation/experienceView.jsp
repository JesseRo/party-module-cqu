<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head>
		<link rel="stylesheet" href="${basePath }/css/details.css" />
	</head>
	<body>
		 <div class="content_title hidden-xs">
		    查看心得
		</div>
		<div class="details_container">
			<p class="details_title">${experience.meeting_theme }</p>
			<div class="details_content">
			    <div class="details_content_title">
			        <p class="col-sm-6 col-xs-12"><span>会议类型</span>${experience.meeting_type }</p>
					<p class="col-sm-6 col-xs-12"><span>上传时间</span>${experience.upload_time }</p>
				</div>
				<div class="details_content_info" style="word-wrap:break-word;padding: 0 0 200px 0;">${experience.experience_content }</div>
		        <div class="btn_group">
		        	<a href="/membertodolist">
		        		<button class="btn btn-default btn-lg main_color_btn">返回</button>
		        	</a>
		        </div>
		    </div>
		</div>
		
	</body>
</html>