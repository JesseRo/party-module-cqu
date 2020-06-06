<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<title>通知信息</title>
<style>
	.content_info .content_form .form-group > div {
		margin-bottom: 20px;
	}
	.layui-form-label.layui-required:before{
		content: "*";
		color: red;
		top: 5px;
		right: 2px;
		position: relative;
	}
	.table_form_content .custom_form .layui-form-label{
		padding: 0 10px;
		width: 160px;
	}
	.layui-form-item .layui-input-inline{
		width: 260px;
	}
	.layui-form-label-text{
		float: left;
		display: block;
		padding: 0 10px;
		width: 260px;
		font-weight: 400;
		line-height: 40px;
		font-size: 16px;
		text-align: left;
	}
	#info-detail .layui-form-item{
		margin-bottom: 0px;
	}
	#info-detail .layui-inline{
		margin-bottom: 0px;
	}
	#info-detail .layui-form-label{
		font-weight: bold;
	}
	#info-detail .layui-btn-radius{
		border-radius:100px;
	}
	#info-detail .line-fill{
		width:720px;
	}
	.org-path{
		background-color: #ffab33;
		font-weight: bold;
	}
</style>
<body>
	<div class="table_form_content">
		<div class="activity_manage_page">
			<div class="breadcrumb_group" style="margin-bottom: 20px;">
				当前位置：
				<span class="layui-breadcrumb" lay-separator=">">
					<a href="javascript:;">组织生活管理</a>
					<a href="javascript:;">通知信息</a>
            </span>
			</div>
			<div class="bg_white_container">
				<div class="content_form form_container">
					<div class="layui-form custom_form"  id="info-detail"
						 style="width: 960px;">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">会议主题:</label>
								<div class="layui-input-inline">
									<label class="layui-form-label-text">${informDetail.meeting_theme }</label>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">开始时间:</label>
								<div class="layui-input-inline">
									<label class="layui-form-label-text">
										<fmt:formatDate value="${informDetail.start_time }" type="both"></fmt:formatDate>
									</label>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">通知组织:</label>
								<div class="layui-input-inline">
									<ul>
										<c:forEach var="org" items="${orgList}">
											<li>${org.org_name}</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">附            件:</label>
								<div class="layui-input-inline" >
									<a href="javascript:void(0)"  path="${attachFile.attachment_url}" name="${attachFile.attachment_name}" onclick="downloadFile(this)">${attachFile.attachment_name}</a>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">发布内容:</label>
								<div class="layui-input-inline line-fill">
									${informDetail.content }
								</div>
							</div>
						</div>

						<div class="layui-form-item" style="text-align: center;margin-top: 40px;">
							<button type="button"  class="layui-btn layui-btn-radius layui-btn-warm" onclick="window.history.back();">返回</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function downloadFile(o){
		var path = $(o).attr("path");
		var name = $(o).attr("name");
		window.location.href="${downloadUrl}&filePath="+path+"&fileName="+name;
	}
</script>
</html>