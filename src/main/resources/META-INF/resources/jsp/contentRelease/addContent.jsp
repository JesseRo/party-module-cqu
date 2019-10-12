<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css" />
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/ueditor.config.js?1=1"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/ueditor.all.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	    <title>新增新闻</title>
		<style type="text/css">
	        .ajaxDelDiv{
	            cursor:pointer;
	            float: left;
	            margin: 5px;
	            color: red;
	        }
	        button{
	        	color:rgb(0,136,219);
	        }
	        /* .col-sm-2{
	        	width:10%!important;
	        	padding-left:10px!important;
	        } */
	    </style>
</head>
<body>
		<!-- 图片上传 -->
		<portlet:resourceURL id="/ueditor/uploadImage" var="uploadimageUrl" />
		<!-- 视频上传 -->
		<portlet:resourceURL id="/ueditor/uploadVideo" var="uploadvideoUrl" />
		<!-- 附件上传 -->
		<portlet:resourceURL id="/ueditor/uploadFile" var="uploadfileUrl" />
	    <!-- 保存info信息 -->
		<portlet:renderURL var="saveInfoRenderUrl">
		    <portlet:param name="mvcRenderCommandName" value="/info/saveInfo" />
		    <portlet:param name="resourceId" value="${list.resources_id}" />
		    <portlet:param name="contentPortletKey" value="${contentPortletKey}" />
		</portlet:renderURL>
		<!-- 返回列表页面 -->
		<%-- <portlet:renderURL var="InfoListUrl">
			<portlet:param name="mvcRenderCommandName" value="/info/InfoList" />
		</portlet:renderURL> --%>
		
		<!-- 实例化编辑器 -->
		<script type="text/javascript">
			var ueObj = UE.getEditor('container',{
		        initialFrameWidth : 846,
		        initialFrameHeight: 400
			});
			//对编辑器的操作最好在编辑器ready之后再做
			ueObj.ready(function() {
			    //设置编辑器的内容
			    //ueObj.setContent("${content_body}");
			    //获取html内容，返回: <p>hello</p>
			    //var html = ue.getContent();
			    //获取纯文本内容，返回: hello
			    //var txt = ue.getContentTxt(); 
			});

			UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
			UE.Editor.prototype.getActionUrl = function(action) {
			    if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
			    	return '${uploadimageUrl}';
			    } else if (action == 'uploadvideo') {//视频
			    	return "${uploadvideoUrl}";
			    } else if (action == 'uploadfile') {//附件
			    	return "${uploadfileUrl}";
			    } else {
			        return this._bkGetActionUrl.call(this, action);
			    }
			}
			//保存
			function saveInfo(){
				$.hgConfirm("提示","确认进行保存吗？");
				$("#hg_confirm").modal("show");
				$("#hg_confirm .btn_main").click(function(){
					var bool = true;
				    //获取html内容，返回: <p>hello</p>
				    var contentBodyHtml = ueObj.getContent();
					$("#content_body").val(contentBodyHtml);
					var content_title1 = $("#content_title").val().replace(/(^\s*)|(\s*$)/g, "");
					if("" == content_title1){
						bool = false;
						alert("请填写标题");
						return;
					}
					//选择图文必须上传图片
					var imgtext = $("#content_type").val();
					var imgs = $("#uploadAjaxFileDiv").children("div").length;
					if(imgtext == "2"){
						if(imgs < 1){
							alert("请上传图片！");
							return;
						}
					}
					//判断是否上传
					var img = $("#ajaxFileName").val();
					if(img != ""){
						if(imgs < 1){
							alert("请点击上传附件！");
							return;
						}
					}
					
					if(bool){
						var content_title = $("#content_title").val();
						var attValue = $("#attValue").val();
						var content_type = $("#content_type").val();
// 						var uploadAjaxFileDiv = $("#uploadAjaxFileDiv").val();
						var content_body = ueObj.getContent();
						var uploadAjaxFileInput = $("#uploadAjaxFileInput").val();
						$.ajax({
							url:"${saveInfoRenderUrl }",
							data:{"content_title":content_title,
		        		         "attValue":attValue,
		        		         "content_type":content_type,
// 		        		         "uploadAjaxFileDiv":uploadAjaxFileDiv,
		        		         "content_body":content_body,
		        		         "uploadAjaxFileInput":uploadAjaxFileInput
		        		         } ,        	      
							type:"post",
		            	    dataType:"text",
		            	    async:false,
		            	    success:function(){
		            	    	$("#hg_confirm").modal("hide");
				                $.tip("保存成功");
				                window.location.href = window.location.pathname;
			            	},
			            	error:function(){
			            		alert("error");
			            	}
			            });

// 						$("#saveInfoForm").submit();
// 						$("#hg_confirm").modal("hide");
// 		                $.tip("保存成功");
					}
	            })
	        }
			//预览
			function showInfo(){
				var bool = true;
				var content_title = $("#content_title").val().replace(/(^\s*)|(\s*$)/g, "");
			    var content_body = ueObj.getContent();
				if("" == content_title){
					bool = false;
					$.tip("请填写标题");
				}
				if(bool){
					if("" == content_body){
						bool = false;
						$.tip("请填写正文");
					}
				}
				if(bool){
					$("#content_title_show").val(content_title);
					$("#content_body_show").val(content_body);
					$("#showContentForm").submit(); 
				}
			}
		</script>
		<!-- 预览编辑文本页面 -->
	<div class="content_title" style="margin-bottom:30px;">
		新增新闻
	</div>	
	
	<portlet:renderURL var="postNewPageUrl">
	    <portlet:param name="mvcRenderCommandName" value="/info/showContent" />
	</portlet:renderURL>
    <div class="content_form">
		<form action="${postNewPageUrl }" id="showContentForm" method="post" target="_blank">
			<input type="hidden" id="content_title_show" name="<portlet:namespace/>content_title_show" value=""/>
			<input type="hidden" id="content_body_show" name="<portlet:namespace/>content_body_show" value=""/>
		</form>
		
<%-- 		<form class="form-horizontal" role="form" action="${saveInfoRenderUrl }" id="saveInfoForm" method="post" >  --%>
		<form class="form-horizontal" role="form" > 
			<div class="form-group">
			 <%-- <div class="col-sm-6 col-xs-12">
				<span for="content_title" class="col-sm-2 control-label col-xs-3">新闻主题</span>
				<div class="col-sm-10 col-xs-9">
					<select class="form-control"  name="<portlet:namespace/>attValue" id="attValue">
						<c:forEach var="attValue" items="${attValues}" >
							<c:if test="${attValue == list.remark}">
								<option selected="selected">${attValue }</option>
							</c:if>
							<c:if test="${attValue != list.remark}">
								<option>${attValue }</option>
							</c:if>
						</c:forEach> 
                    </select>
				</div>
			 </div> --%>
			 <div class="col-sm-6 col-xs-12">
                <span for="content_description" class="col-sm-2 col-xs-3 control-label">新闻类型</span>
                <div class="col-sm-10 col-xs-9">
                    <select name="<portlet:namespace/>content_type" id="content_type" class="form-control">
						<c:forEach var="contentTypeObj" items="${contentTypes}" >
							<c:if test="${list.content_type == contentTypeObj.id}">
								<option selected="selected" value="${contentTypeObj.id }">${contentTypeObj.mark }</option>
							</c:if>
							<c:if test="${list.content_type != contentTypeObj.id}">
								<option value="${contentTypeObj.id }">${contentTypeObj.mark }</option>
							</c:if>
						</c:forEach> 
					</select>
                </div>
            </div>
			<div class="col-sm-6 col-xs-12">
                <span for="content_description" class="col-sm-2 col-xs-3 control-label">新闻标题</span>
                <div class="col-sm-10 col-xs-9">
                    <input type="text" class="form-control" placeholder="5-30个字" name="<portlet:namespace/>content_title" id="content_title" value="${list.content_title }"/>
                </div>
            </div>
			<div class="col-sm-12 col-xs-12">
				<span for="content_description" class="col-sm-1 col-xs-3 control-label">附件</span>
				<div class="col-sm-11 col-xs-9">
					<portlet:resourceURL id="/ueditor/uploadAjaxFile" var="uploadAjaxFile" />
					<!-- 删除文件 -->
					<portlet:resourceURL id="/ueditor/delUploadAjaxFile" var="delUploadAjaxFile" />
					<input type="file" name="ajaxFileName" id="ajaxFileName" value="this.val()" style="display:inline;"/>
					<input type="button" name="fileLoad1" id="fileLoad1" value="上传附件" onClick="fileupload1()"/>
					<div id="uploadAjaxFileDiv">
						<div onclick="delAjaxFile(${list.att_id })" title='点击删除' class='ajaxDelDiv' id="ajaxDelDiv_${list.att_id }">
							${list.attachment_url }
						</div>
					</div>
					<input type="hidden" id="uploadAjaxFileInput" name="<portlet:namespace/>uploadAjaxFileInput"/>
					<script type="text/javascript">
						function fileupload1(){
							if($("#ajaxFileName").val()==""){
								$.tip("上传文件不能为空!");
								return false;
							}
						    var file = $("#ajaxFileName").val();
						    var pos=file.lastIndexOf("\\");
							var fileName=file.substring(pos+1);//获得文件名字
						                                                                                                                                
						    $.ajaxFileUpload({
						        url:"${uploadAjaxFile}",
						        contentType:"multipart/form-data; text/xml;charset=utf-8",
						        secureuri:false,
						        cache: false,//防止缓存
						        fileElementId:'ajaxFileName',
						        dataType: 'json',
						        success: function (data) {
						        	if("fail" == data.state){
						        		$.tip("上传失败!");
						        	}else{
										$("#uploadAjaxFileDiv").append("<div title='点击删除' id='ajaxDelDiv_"+data.id+"' class='ajaxDelDiv' onclick='delAjaxFile("+data.id+")'>&ensp;"+data.sourceFileName+"&ensp;删除;</div>");
										var uploadAjaxFileInputVal = $("#uploadAjaxFileInput").val();
										uploadAjaxFileInputVal += "@"+data.id+"@,";
										$("#uploadAjaxFileInput").val(uploadAjaxFileInputVal);
						        	}
						        },
						        error: function (data, status, e){
									//$.tip("fail");
									console.info("上传失败");
									console.info("data.state="+data.state);
									console.info("data.url="+data.url);
						        }
							});
						}
						function delAjaxFile(id){
							/* $.hgConfirm("提示","确认删除该文件吗？");
							$("#hg_confirm .btn_main").click(function(){
								var uploadAjaxFileInputVal = $("#uploadAjaxFileInput").val();
								uploadAjaxFileInputVal = uploadAjaxFileInputVal.replace("@"+id+"@,", "");
								$("#uploadAjaxFileInput").val(uploadAjaxFileInputVal);
								$("#ajaxDelDiv_"+id).remove();
								$.ajax({
									url : "${delUploadAjaxFile}",
									data : {"<portlet:namespace/>id":id},  
									async : false
								});
				                $("#hg_confirm").modal("hide");
				                $.tip("删除成功");
				            }) */

							if(confirm("确认删除该文件？")){
								var uploadAjaxFileInputVal = $("#uploadAjaxFileInput").val();
								uploadAjaxFileInputVal = uploadAjaxFileInputVal.replace("@"+id+"@,", "");
								$("#uploadAjaxFileInput").val(uploadAjaxFileInputVal);
								$("#ajaxDelDiv_"+id).remove();
								$.ajax({
									url : "${delUploadAjaxFile}",
									data : {"<portlet:namespace/>id":id},  
									async : false
								});
							} 
						}
					</script>
				</div>
			</div>
			<div class="col-sm-12 col-xs-12">
				<script id="container" name="content" type="text/plain" >${list.content_body }</script>
				<input type="hidden" name="<portlet:namespace/>content_body" id="content_body" value=""/>
			</div>
			</div>
			<div class="btn_group">
                <button type="button" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" onclick="saveInfo();"/>保存</button>
                <span class="col-xs-1 visible-xs"></span>
                <button type="button" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" onclick="history.go(-1)">返回</button>
                <span class="col-xs-1 visible-xs"></span>
                <button type="button" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" onclick="showInfo();">预览</button>
            </div>
		</form>
	</div>
	<!-- <script>       
		function fresh(){  
			var _location = location.href;
			if(_location.indexOf("?")>0 && _location.indexOf("#reload")<0){
				window.location.reload();
			    location.href+="#reload"; 
			 }  
		};
		setTimeout("fresh()",10);
	</script> -->
</body>	
		