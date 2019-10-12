<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.min.js"></script> --%>
<script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>

<!DOCTYPE html>
<html>
	<head>
		<title>二级党委查看活动进度test</title>
		<style>
			#tBody .color_blue{
				color:#CE0000;
			}
			#tBody .color_blue_1{
				color:#FF0000;
			}
			.table_title th{
				text-align: center;
			}
			@media(min-width:768px){
				.table_info .PublishTime{
					min-width:155px;
				}
				.table_info .LaunchTime{
					min-width:155px;
				}
			}
			 /* 录入弹窗样式 */
        
        #input .form-group>div {
            height: 34px;
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
        /* 录入弹窗样式 */
       
        /* 查看弹窗样式 */
        
        .check .modal-body {
            max-height: 300px;
            overflow-y: auto;
            color: #666;
        }
        
        .check .modal-body td,
        .check .modal-body th {
            text-align: center;
            border: 1px solid #d8d8d8;
            padding: 5px 0;
        }
        
        .check .modal-body th select {
            height: 24px;
        }
        
        .check .form-group>div {
            height: 34px;
            margin-bottom: 10px;
        }
        /* 查看弹窗样式 */
        /* 常用人员弹窗样式 */
        
        #commonStaff thead {
            border-bottom: 1px solid #d8d8d8;
            background: #f5f5f5;
        }
        
        #commonStaff .modal-body {
            max-height: 400px;
        }
        
        #commonStaff .common_list {
            border-right: none;
        }
        
        #commonStaff .common_list tr td {
            cursor: pointer;
        }
        
        #commonStaff .common_list tr td:nth-child(1) {
            cursor: default;
        }
        
        #commonStaff table td {
            font-size: 13px;
        }
        
        #commonStaff th,
        #commonStaff .common_list td {
            border: none;
        }
        
        #commonStaff .modal-body>div {
            padding: 0;
        }
        
        #commonStaff .modal-body>div table {
            width: 100%;
        }
        
        #commonStaff .add_btn {
            height: auto;
            background: #f5f5f5;
            border: 1px solid #d8d8d8;
            border-top: none;
        }
        
        #commonStaff .add_btn button {
            margin: 15px;
        }
        
        #commonStaff .add_btn img {
            margin-right: 5px;
        }
        
        .table_container {
            height: 200px;
            overflow-y: auto;
            border: 1px solid #d8d8d8;
        }
        
        .common_list_container .table_container {
            border-right: none;
        }
        
        #commonStaff .table_title {
            text-align: center;
        }
        
        .add_btn form {
            width: 80%;
            background: #fff;
            border: 1px solid #d8d8d8;
            border-radius: 6px;
            padding: 10px 5px;
            padding-bottom: 0;
        }
        
        #commonStaff .add_btn form {
            margin: 0 0 10px 10px;
            display: none;
        }
        
        #commonStaff .add_btn form .btn {
            margin: 0;
        }
        
        #commonStaff .form-group {
            margin-bottom: 0;
        }
        
        .form_btn {
            text-align: right;
        }
        
        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }
        
        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }
		</style>
	</head>
	<body class="front">
		<!-- 		下载图片 -->
		<portlet:resourceURL id="/PartyImageDownCommand" var ="download">
		</portlet:resourceURL>
		<portlet:resourceURL id="/PartyPartCommand" var="delDevie" />
		<portlet:resourceURL id="/PartyPartActionCommand" var="PartyPartAction" />
		<portlet:resourceURL id="/PartyWriteActionCommand" var="PartyWriteAction" />
		<portlet:resourceURL id="/PartyRemindersCommand" var="partyRemindersUrl" />
	
		<div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                        	<th>已读回执</th>
                            <th>党支部</th>
                            <th>开展主题</th>
                            <th class="PublishTime">开展时间</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人</th>
                            <th>联系人电话</th>
                            <th>任务状态</th>
                            <th>审核人</th>
                            <th>操作</th>
                            <th>现场照片</th>
                            <th>参会人数</th>
                            <th>请假人员</th>
                            <th>出勤率</th>
                            <th>上传会议记录</th>
                            <th>评价得分</th>
                            <th>备注</th>
                        </tr>
                    </thead>
                    <tbody class="table_info" id="tBody">
                   	    <c:forEach items="${list}" var="info" varStatus="status">
	                        <tr>
	                        	<td data-label="已读回执">${info.read_status }</td>
	                            <td data-label="党支部" class="Party_name">${info.org_name }</td>
	                            <td data-label="开展主题">${info.theme_ }</td>
	                            <td data-label="开展时间" class="PublishTime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.start_time }" />-<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.end_time }" /></td>
	                            <td data-label="开展地点">${info.place }</td>
	                            <td data-label="主持人">${info.host }</td>
	                            <td data-label="联系人">${info.contact }</td>
	                            <td data-label="联系人电话">${info.contact_phone }</td>
	                            <td data-label="任务状态" class="TaskState">${info.task_status }</td>
	                            <td data-label="审核人">${info.auditor }</td>
	                            <td data-label="操作" class="table_content">
	                            	<c:if test="${info.task_status == '已提交'}">    
										<a data-toggle="modal" 
											data-target="#destribute" 
											style="cursor: pointer;" 
											onclick="distributionSend('${ info.organization_id }','${info.meeting }');">
										分配</a>
										
									</c:if> 
									<c:if test="${info.task_status == '待审批'}">    
										<a href="" data-toggle="modal" data-target="#reminder">
										<input type="hidden" value="${info.meeting }" class="meeting_id3" />
										催办</a>
									</c:if>
	                            </td>
	                            <td data-label="现场照片">
	                            	<a id="images">${info.image }
	                            		<input type="hidden" id="imageNeme" value="${info.image }" name="imageNeme"/>
	                            	</a>
	                            </td>
	                            <td data-label="参会人数">${info.actual_persons_s }</td>
	                            <td data-label="请假人员">${info.leave_persons }</td>
	                            <td data-label="出勤率">${info.attendance }</td>
	                            <td data-label="上传会议记录">${info.check_status }</td>
	                            <td data-label="评价得分">${info.evaluation_score }</td>
	                            <td data-label="备注">${info.remark }</td>
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
			                <input type="hidden" name="informId" value="${informId }"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
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
					 
				//任务状态加颜色
				$(function(){
						$(".TaskState").each(function(){
							var _text = $(this).text();
							if(_text == "被驳回"){
								$(this).addClass("color_blue");
							}else if(_text == "未检查"){
								$(this).addClass("color_blue_1");
							}else{}
							
						})
			    	})
		    
			 </script> 
			 
		
		 <!-- 分配模态框 -->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="destribute" tabindex="-1" role="dialog" aria-labelledby="destributeLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="destributeLabel">请选择分配任务的接收人</h4>
                </div>
                <div class="modal-body">
                    <div class="select_choice all_select col-sm-4 col-xs-4">
                        <img src="/images/not_check_icon.png" class="zzy_image"/>
                        <input type="hidden" value="" class="zzy" />
                        <span>组织员</span>
                    </div>
                    <div class="select_choice oppsite_select col-sm-4 col-xs-4">
                        <img src="/images/not_check_icon.png" class="zzy_image"/>
                        <input type="hidden" class="sj"/>
                        <span>书记</span>
                    </div>
                    <div class="select_choice oppsite_select col-sm-4 col-xs-4">
                        <img src="/images/not_check_icon.png" class="zzy_image"/>
                        <input type="hidden" />
                        <span>副书记</span>
                    </div>
                </div>
                <div class="modal-footer">
                	<input type="hidden" id="distributionSureId" value=""/>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn_main" onclick="sjId()">确定</button>
                </div>
            </div>
        </div>
    </div>
		<script type="text/javascript">
			//点击分配
			function distributionSend(organization_id, meeting){
				
				$("#distributionSureId").val(meeting);
				console.info("-----------organization_id=" + organization_id);
					var url="${delDevie}";
					$.ajax({
						    url:url,
						    data:{"organization_id":organization_id},
						    dataType:'json',
						    success:function(succee){ 
						    	console.log(succee[0].id);
						    	$(".zzy").val(succee[0].org_contactor);
						    	$(".sj").val(succee[0].org_secretary);
						    }
					});
			}
			//图片选中
			$(".zzy_image").click(function(){
				      $(".zzy_image").attr("src","/images/not_check_icon.png");
					  $(this).attr("src","/images/checked_icon.png");
			})
	
			//分配确定保存
			function sjId(){
				 var orgName = $("img[src='/images/checked_icon.png']").next().val();
				 if("" == orgName){
					 alert("请选择");
					 return false;
				 }
				 var url = "${PartyPartAction}";
				 var meeting_id = $("#distributionSureId").val();
				 $.ajax({
					 url:url,
					 data:{orgName:orgName,meeting_id:meeting_id},
					 dataType:'json',
					 async:false,
					 success:function(succee){
						 $('#destribute').modal('hide');
						 window.location.reload();
					 }
				 });
			}
		</script>
		
	    <!-- 录入模态框 -->
    
    <!-- 模态框（Modal） -->
     <div class="modal fade" id="input" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="inputLabel">添加活动照片</h4>
                </div>
                <div class="modal-body content_form">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <div class="col-sm-12 col-xs-12">
                                <span class="col-sm-3 col-xs-4 control-label">应到人数：</span>
                                <div class="col-sm-9 col-xs-8">
                                    <input type="text" class="form-control" id="should_"/>
                                </div>
                            </div>
                            <div class="col-sm-12 col-xs-12">
                                <span class="col-sm-3 col-xs-4 control-label">实到人数：</span>
                                <div class="col-sm-9 col-xs-8">
                                    <input type="text" class="form-control" id="actual_"/>
                                </div>
                            </div>
                            <div class="col-sm-12 col-xs-12">
                                <span class="col-sm-3 col-xs-4 control-label">上传照片：</span>
<!--                                 <div class="col-sm-9 col-xs-8 file_input"> -->
<!--                                     <input type="file" multiple> -->
<!--                                     <div class="show_path"> -->
<!--                                         <img src="/images/add_img.png" /> -->
<!--                                     </div> -->
<!--                                 </div> -->
                            </div>
                            
                            <div class="input link_form_line_image" style="display:inline-block; padding-left: 140px;">
								<portlet:resourceURL id="/PartyImageCommand" var="uploadAjaxFile" />
								<input type="file" name=imageUrlfile id="imageUrlfile" value="this.val()" style="display:inline;"/>
								<input type="hidden" name="imageUrl" id="ImageUrlid" value="" />
								<div style="margin-left: 10px;color: #aaa;">
									
								</div>
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
									                                                                                                                                
									    $.ajaxFileUpload({
									        url:"${uploadAjaxFile}",
									        contentType:"multipart/form-data; text/xml;charset=utf-8",
									        secureuri:false,
									        cache: false,//防止缓存
									        fileElementId:'imageUrlfile',
									        dataType: 'json',
									        success: function (data) {
								        		$("#ajaxUploadFile").disabled="disabled";
								        		$(".link_form_line_image div").html("");
								        		$(".link_form_line_image div").html(data.url);
												$("#ImageUrlid").attr("value",data.url);
												alert("上传成功");
									        },
									        error: function (data, status, e){
												//alert("fail");
												console.info("上传失败");
									        }
										});
									}
								</script>
								<input type="button" name="ajaxUploadFile" id="ajaxUploadFile" value="上传" onClick="uploadFile()" style="width:58px;height:28px;"/>
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
    
    <!-- 催办模态框 -->
	    
    <!-- 模态框（Modal） -->
	     <div class="modal fade" id="reminder" tabindex="-1" role="dialog" aria-labelledby="reminderLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                    <h4 class="modal-title" id="reminderLabel">催办</h4>
	                </div>
	                <div class="modal-body">
	                	确定通过邮件通知审核人
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn_main" onclick="remindersOK()">确定</button>
	                </div>
	            </div>
	        </div>
	    </div> 
	    <script type="text/javascript">
		  //催办确定
			function remindersOK(){
				var meeting_id = $('.meeting_id3').val();
// 				alert(meeting_id);
				var url = "${partyRemindersUrl}";
				$.ajax({
					url:url,
					data:{meeting_id:meeting_id},
					dataType:'json',
					async:false, 
					success:function(data){
						console.info("----data:" + data);
						if(data.succee == true){
							alert("发送成功");
						}else{
							alert("发送失败");
						}
					}
				});
				
				$('#reminder').css('display','none');
				 setTimeout(
					function(){
					 window.location.reload();
				 	}
					,50);
				 
			}
	    
	    </script>
    
			<script type="text/javascript">
				//点击录入
				function entry(meetingId){
					$('#entry_id').val(meetingId);
				}
				
				//录入保存
				function Write(){
					var meeting_id2 = $('#entry_id').val();//会议id
					var should_ = $('#should_').val(); //应到人数
					var actual_ = $('#actual_').val(); //实到人数
					var image = $(".link_form_line_image div").html(); //图片名字
// 					alert("会议id="+meeting_id2+"人数="+should_+actual_+"image="+image);
					var url = "${PartyWriteAction}";
					$.ajax({
						url:url,
						data:{meeting_id2:meeting_id2,should_:should_,actual_:actual_,image:image},
						dataType:'json',
						async:false,
						success:function(succee){
							$('#input').modal('hide');
							window.location.reload();
						}
					});
				}
				
				//图片下载
				document.getElementById('images').onclick=function(){
				 	var name = $('#imageNeme').val();
					var a = document.getElementById("images");
					if(confirm("下载 图片")){
						a.setAttribute("href","${download}&imageNeme="+name);
					}else{
						a.setAttribute("href","#");
					}
				};
			</script>	 
	</body>
</html>