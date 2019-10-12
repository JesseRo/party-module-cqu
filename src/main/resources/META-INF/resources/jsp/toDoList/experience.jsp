<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<link rel="stylesheet" href="${basePath }/css/details.css" />
		<link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css" />
	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    	<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	</head>
	<body>
		
		
	    
		<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl" />
		<!-- 视频上传 -->
		<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl" />
		<!-- 附件上传 -->
		<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl" />
		
		
		<portlet:actionURL name="/party/experience" var="experienceUrl">
		</portlet:actionURL>
		<div class="details_container">
			<p class="details_title">${meetingTheme }</p>
			<div class="details_content">
			    <div class="details_content_title">
			        <p class="col-sm-6 col-xs-12"><span>会议类型</span>${type }</p>
					<p class="col-sm-6 col-xs-12"><span>开展时间</span>${time }</p>
				</div>
		    </div>
		</div>
		<div class="content_form">
			<!-- 提交数据存库 -->
			<form action="${experienceUrl }" method="post" id="experience_content" class="form-horizontal">
				<input type="hidden" value="${meetingId }" name="meetingId">
				<input type="hidden" value="${userId }" name="userId">
				<input type="hidden" value="${formId }" name="formId">
				<input type="hidden" value="${heart }" name="heart" id="heart">
				<%-- <input type="hidden" value="" name="heart" id="heart" dataUrl="${heart }"> --%>
				
				<div class="content_info1">
		             <div class="content_title hidden-xs" style="margin-bottom: 20px;">
		                	 上传心得
		             </div>
		         </div>
				<script type="text/javascript">
					var ueObj = UE.getEditor("experience");
					var heart = $("#heart").val();
// 					var heart = $("#heart").attr("dataUrl");
		         	ueObj.ready(function() {
		         		ueObj.setContent(heart);
					});
		
					UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
					UE.Editor.prototype.getActionUrl = function(action) {
					    if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
					    	return "${uploadimageUrl}";
					    } else if (action == 'uploadvideo') {//视频
					    	return "${uploadvideoUrl}";
					    } else if (action == 'uploadfile') {//附件
					    	return "${uploadfileUrl}";
					    } else {
					        return this._bkGetActionUrl.call(this, action);
					    }
					}
					
				</script> 
				<div>
					<script type="text/plain" id="experience" name="experience"></script>
				</div>
				<div class="btn_group" style="margin-top: 15px;">
					<button type="button" onclick="javascript:window.history.back(-1);" class="btn btn-default">取消</button>
					<span class="col-xs-1 visible-xs"></span>
					<button type="submit" class="btn btn_main right" style="color: white;">上传</button>
				</div>
			</form>
		</div>
	</body>
</html>
