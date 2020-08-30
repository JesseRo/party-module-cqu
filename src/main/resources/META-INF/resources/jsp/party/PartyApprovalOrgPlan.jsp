<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
<link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
<!DOCTYPE html>
<html>
	<head>
		<title>指派录入(组织部)</title>
		<style>
			.col-xs-8 label{
				margin: 10px;
			}
			#tBody .color_blue{
				color:#CE0000;
			}
			#tBody .color_blue_1{
				color:#FF0000; 
			}
			
			@media(min-width:768px){
				.table_info .PublishTime{
					min-width:200px;
				}
				.table_info .LaunchTime{
					min-width:200px;
				}
			}
			
			 /* 录入弹窗样式 */
        
	        #input .form-group>div {
	            height: 50px;
	            margin-bottom: 10px;
	        }
	        
	        #input .col-sm-9,
	        #input .col-xs-8 {
	            padding: 0;
	        }
	        
	        .file_input {
	            height: 100px;
	            border: 1px dashed #d8d8d8;
	            border-radius: 4px;
	        }
	        
	        .file_input input {
	            width: 77px;
	            height: 77px;
	            opacity: 0;
	            position: absolute;
	            top: 50%;
	            left: 50%;
	            margin-left: -38.5px;
	            margin-top: -38.5px;
	            z-index: 5;
	        }
	        
	        .file_input .show_path {
	            width: 100%;
	            height: 100%;
	            position: absolute;
	            top: 0;
	            left: 0;
	            line-height: 32px;
	            text-indent: 1em;
	            text-align: center;
	            overflow-y: auto;
	        }
	        
	        .show_path img {
	            width: 77px;
	            margin: 10.5px 0;
	        }
	        
	        .show_path p {
	            text-align: left;
	            color: #999;
	            font-size: 13px;
	            line-height: 15px;
	        }
			.content_table thead tr{
				background: #F6F8FC;
				height: 48px;
				font-size: 16px;
			}
			.content_table thead th{
				padding: 5px 15px !important;
			}
			.content_table tr:nth-child(2n) {
				background: #FBFCFE;
			}
			.content_table td{
				min-width: 130px;
				padding: 5px 15px !important;
				height: 48px;
				font-size: 14px;
			}
			input {
				text-indent: 0;
			}
	        /* 录入弹窗样式 */			
		</style>
	</head>
	<body class="front">
		<!-- 		下载图片 -->
		<portlet:resourceURL id="/PartyImageDownCommand" var ="download">  
		</portlet:resourceURL>
		<!-- 		录入确定 -->
		<portlet:resourceURL id="/PartyWriteActionCommand" var="PartyWriteAction" />
		<div class="table_form_content">
			<!-- 右侧盒子内容 -->
			<div class="activity_manage_page">
				<div class="breadcrumb_group" style="margin-bottom: 20px;">
					当前位置：
					<span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">抽查录入</a>
                    </span>
				</div>
				<div class="bg_white_container">
					<div class="table_outer_box">
						<table class="layui-table custom_table">
							<thead class="table_title">
							<tr>
								<td>党委名称</td>
								<td>会议类型</td>
								<td>开展主题</td>
								<td>开始时间</td>
								<td>操作</td>
								<%--                            <th>开展时长(分钟)</th>--%>
								<td>开展地点</td>
								<%--                            <th>应到人数</th>--%>
								<%--                            <th>实到人数</th>--%>
								<%--                            <th>主持人</th>--%>
								<td>联系人</td>
								<%--                            <th>联系人电话</th>--%>
								<%--                            <th>任务状态</th>--%>
								<td>抽查状态</td>
								<%--                            <th>审核人</th>--%>
								<td>图片</td>
								<%--                            <th>备注</th>--%>
							</tr>
							</thead>
							<tbody class="table_info" id="tBody">
							<c:forEach items="${list}" var="info" varStatus="status">
								<tr>
									<td data-label="党委名称" class="Party_name">${info.org_name }</td>
									<td data-label="会议类型">${info.meeting_type }</td>
									<td data-label="开展主题">${info.meeting_theme }</td>
									<td data-label="开始时间" title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_p }" />"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_p }" /></td>
									<td data-label="操作">
										<c:if test="${info.task_status == '5' }">
											<a onclick="entry('${info.meeting}')" style="cursor: pointer;">
												录入</a>
										</c:if>
										<c:if test="${info.task_status == '7' }">
											<a onclick="entry('${info.meeting}')" style="cursor: pointer;">
												修改</a>
										</c:if>
									</td>
										<%--	                            <td data-label="开展时长">${info.total_time }</td>--%>
									<td data-label="开展地点" title="${info.campus }-${info.place_name}">${info.campus }-${info.place_name}</td>
										<%--	                            <td data-label="应到人数">${info.shoule_persons }</td>--%>
										<%--	                            <td data-label="实到人数">${info.actual_persons }</td>--%>
										<%--	                            <td data-label="主持人">${info.host }</td>--%>
									<td data-label="联系人">${info.contact_name }</td>
										<%--	                            <td data-label="联系人电话">${info.contact_phone }</td>--%>
										<%--	                            <td data-label="任务状态">--%>
										<%--	                            	<c:if test="${info.task_status == '1'}"> --%>
										<%--	                            		 已提交--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '2'}"> --%>
										<%--	                            		 已撤回--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '3'}"> --%>
										<%--	                            		 被驳回--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '4'}"> --%>
										<%--	                            		 已通过--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '5'}"> --%>
										<%--	                            		 已指派--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '6'}"> --%>
										<%--	                            		 未检查--%>
										<%--									</c:if>--%>
										<%--									<c:if test="${info.task_status == '7'}"> --%>
										<%--	                            		 已检查--%>
										<%--									</c:if>--%>
										<%--	                            </td>--%>
									<td data-label="抽查状态">
										<c:if test="${info.task_status == '5'}">
											已指派
										</c:if>
										<c:if test="${info.task_status == '6'}">
											未检查
										</c:if>
										<c:if test="${info.task_status == '7'}">
											已检查
										</c:if>
									</td>
										<%--	                            <td data-label="审核人">${info.auditor }</td>--%>
									<td data-label="图片" class="img_td">
										<input type="hidden" class="imageNemeOrg" value="${info.image }" name="imageNeme"/>
									</td>
										<%--	                            <td data-label="备注">${info.remarks_org }</td>--%>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>

 
   <!-- -----------------------分页------------------------- -->
				<div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${totalPage }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
						</portlet:actionURL>
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
				 </div>
				</div>
				 <script type="text/javascript">
					  $(document).ready(function() {
						 var pages = $(".total_page").html();
						var currentPage = $('.current_page').val();
						$("input[name='pageNo']").val($('.current_page').val());
						if(currentPage == 1){
							$('.page_next').removeClass('not_allow');
							$('.page_prev').addClass('not_allow');
							
						}else if(currentPage == pages){
							$('.page_prev').removeClass('not_allow');
							$('.page_next').addClass('not_allow');
							
						}else{
							
						};
						$("#jumpPageNo").change(function(){
							$("input[name='pageNo']").val($(this).val());
						})
				     Page({
				         num: pages, //页码数
				         startnum: currentPage, //指定页码
				         elem: $('#page'), //指定的元素
				         callback: function(n) { //回调函数
				             $("input[name='pageNo']").val(n);
// 				         	alert($("input[name='pageNo']").val());
				             $("#getPageNo").submit();
				             if (n == 1) {
				                 $('#page a').removeClass('not_allow');
				                 $('.page_prev').addClass('not_allow');
				             } else if (n >= $('.total_page').html()) {
				                 $('#page a').removeClass('not_allow');
				                 $('.page_next').addClass('not_allow')
				             } else {
				                 $('#page a').removeClass('not_allow');
				             }
				         }
				     });
				  }); 
					
		</script> 
			</div>
		</div>
		<!-- 录入模态框 -->
    
	    <!-- 模态框（Modal） -->
	     <div class="modal fade" id="input" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                    <h4 class="modal-title" id="inputLabel">录入详情</h4>
	                </div>
	                <div class="modal-body content_form">
	                    <form class="form-horizontal" role="form">
	                        <div class="form-group">
	                            <div class="col-sm-12 col-xs-12">
	                                <span class="col-sm-3 col-xs-4 control-label">备注信息：</span>
	                                <div class="col-sm-9 col-xs-8">
	                                    <input type="text" class="form-control" id="should_"/>
	                                </div>
	                            </div>
	                            <div class="col-sm-12 col-xs-12">
	                                <span class="col-sm-3 col-xs-4 control-label">检查结果：</span>
	                                <div class="col-sm-9 col-xs-8">
<!-- 	                                    <input type="text" class="form-control" id="actual_"/> -->
	                                    <label><input name="meeting_state" type="radio" value="正常" />正常 </label> 
										<label><input name="meeting_state" type="radio" value="异常" />异常 </label> 
	                                </div>
	                            </div>
	                            <div class="col-sm-12 col-xs-12">
	                                <span class="col-sm-3 col-xs-4 control-label">上传照片：</span>
		                            <div class="input link_form_line_image" style="margin-left: 120px;padding: 1px;">
										<portlet:resourceURL id="/PartyImageCommand" var="uploadAjaxFile" />
										<input type="file" name=imageUrlfile id="imageUrlfile" value="this.val()" style="display:inline;"/>
										<input type="hidden" name="imageUrl" id="ImageUrlid" value="" />
										<input type="hidden" name="upload_" id="upload_" value="" />
										<input type="button" name="ajaxUploadFile" id="ajaxUploadFile" value="上传" onClick="uploadFile()" style="width:58px;height:28px;margin-top: 10px;"/>
										
										<div style="margin-left: 10px;color: #aaa;" id="uploadAjaxFileDiv"></div>
											<script type="text/javascript">
											   $(".link_form_line_image input[name=imageUrlfile]").change(function() {
											        var filePath = $(this).val();
											        if(filePath){
											        	$("#ImageUrlid").val(filePath);
											        };
											    });
												function uploadFile(){
													if($("#imageUrlfile").val()==""){
														alert("上传文件不能为空!");
														return false;
													}
													//判断上传图片数量
													$("#upload_").val("1");
													if($("#uploadAjaxFileDiv div").length > 2){
														alert("请注意，页面只展示3张图片！");
													}
													
													$("#upload_").val("已上传");
												                                                                                                                                
												    $.ajaxFileUpload({
												        url:"${uploadAjaxFile}",
												        contentType:"multipart/form-data; text/xml;charset=utf-8",
												        secureuri:false,
												        cache: false,//防止缓存
												        fileElementId:'imageUrlfile',
												        dataType: 'json',
												        success: function (data) {
											        		$("#ajaxUploadFile").disabled="disabled";
											        		$("#uploadAjaxFileDiv").append("<div title='点击删除' dataurl='"+data.url+"' class='ajaxDelDiv'>"+data.url+"</div>");
															$("#ImageUrlid").attr("value",data.url);
												        },
												        error: function (data, status, e){
															//alert("fail");
															console.info("上传失败");
												        }
													});
												}
											</script>
										
									</div>
								</div>
	                        </div>
	                    </form>
	                </div>
	                <div class="modal-footer">
	                	<input type="hidden" id="entry_id" value="" />
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="button" class="btn btn_main" onclick="Write()">确定录入</button>
	                </div>
	            </div>
	        </div>
	    </div> 
	    
	     <!-- 模态框2（Moda2） -->
	     <div class="modal fade" id="input2" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                    <h4 class="modal-title" id="inputLabel">图片</h4>
	                </div>
	                <div class="modal-body content_form">
	                    <form class="form-horizontal" role="form">
	                        <div class="form-group" style="height: 500px; margin-left: 1px; text-align: center;">
	                            <img alt="录入图片" src="" id="imgEntry">
	                        </div>
	                    </form>
	                </div>
	                <div class="modal-footer">
	                	<input type="hidden" id="entry_id2" value="" />
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="button" class="btn btn_main" onclick="download()">下载图片</button> 
	                </div>
	            </div>
	        </div>
	    </div>
	    
	    <script type="text/javascript">
		  //点击录入
			function entry(meetingId){
				$('#entry_id').val(meetingId);
				$("#input").modal("show");
			}
			
			//录入保存
			function Write(){
				var text = $("input[name='meeting_state']:checked").val();//会议状态
				var meeting_id2 = $('#entry_id').val();//会议id
				var should_ = $('#should_').val(); //备注信息
// 				var actual_ = $('#actual_').val(); //实到人数
				var image = "";			//获取多个图片
				$("#uploadAjaxFileDiv div").each(function(){
					image += $(this).text()+",";
				})
				image = image.substring(0,image.length-1);
				
				if(should_ == ""){
					alert("请录入备注信息！");
					return;
				}
				if(text != "正常" & text != "异常"){
					alert("请选择会议状态！");
					return;
				}
				//判断是否点击上传
				if($("#imageUrlfile").val() != ""){
					if($("#upload_").val() == ""){
						alert("请点击上传！");
						return;
					}
				}
				
				var url = "${PartyWriteAction}";
				$.ajax({
					url:url,
					data:{meeting_id2:meeting_id2,should_:should_,text:text,image:image},
					dataType:'json',
					async:false,
					success:function(succee){
						$('#input').modal('hide');
						window.location.reload();
					}
				});
			}
			
			//图片下载
// 			$(".table_info").on("click",".images",function(){
				//var name = $(this).find("input").val();
// 				var name = $(".imageNemeOrg").val();
// 				var name = $(this).parent().find("input").val();
// 				var name = $(this).attr("dataUrl");
// 				if(confirm("下载 图片")){
// 					$(this).attr("href","${download}&imageNeme="+name);
// 				}else{
// 					$(this).attr("href","#");
// 				}
// 			});
			
			//图片下载弹框
			$(".table_info").on("click",".images",function(){
				var name = $(this).attr("dataUrl");
				$('#entry_id2').val(name);
				$('#imgEntry').attr('src',name);
				$("#input2").modal("show");
			});
			 //图片下载
			  function download(){
				  	var name = $("#entry_id2").val();
					if(confirm("下载 图片")){
						window.location.href="${download}&imageNeme="+name;
					}
			  }
			
	    </script>
	    <script type="text/javascript"> 
		    $(function(){
		    	//分割图片
		    	$(".img_td").each(function(){
		    		if($(this).find(".imageNemeOrg").val()){
		    			var imgArr = $(this).find(".imageNemeOrg").val().split(',');
		    			var aa = imgArr.length;
		    			if(aa > 3){
		    				aa = 3;
		    			}
		    			for(var i = 0;i<aa;i++){
				    		var img = '<a class="images" dataUrl='+imgArr[i]+'>图'+(i+1)+',</a>';
				    		$(this).append(img)
				    	}
		    		}
		    	})
		    })
		    
		     //删除图片
		    $(".modal-dialog").on("click",".ajaxDelDiv",function(){
		    	var id = $(this).attr("dataurl");
		    	if(confirm("确认删除该文件？")){
					$(this).remove();
					$.ajax({
						url : "${delUploadAjaxFile}",
						data : {"id":id},  
						async : false
					});
				}
		    })
	    </script>
	</body>
</html>