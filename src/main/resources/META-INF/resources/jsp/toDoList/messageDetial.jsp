<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head></head>
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
				<div class="details_content_info">${content }</div>
		        <div class="btn_group">
		        	<a href="/todolist?meetingId=${meetingId }">
		        		<button class="btn btn-default btn-lg main_color_btn">确定</button>
		        	</a>
		        </div>
		    </div>
		</div>
		
	</body>
</html>