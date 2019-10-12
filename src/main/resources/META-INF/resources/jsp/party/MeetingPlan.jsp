<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>
<portlet:renderURL var="showExperienceDetailUrl">
		<portlet:param name="mvcRenderCommandName" value="/hg/showExperienceDetail"/>
</portlet:renderURL>


<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${ basePath}/css/assign.css" />

<!DOCTYPE html>
<html>
	<head>
		<title>二级党委查看活动进度</title>
		<style>
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
				
				li.delete_li {
                   background: #ce0000;
                   color: white;
               }
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
		<div class="content_title" style="margin-bottom:30px;">
        	<a href="/backlogtwo" style="color: #ce0000;">待办事项</a>>查看活动进度
        </div>
		<div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                        	<th>已读回执</th>
                            <th>党支部</th>
                            <th>开展主题</th>
                            <th>支部主题</th>
                            <th class="PublishTime" style="min-width: 160px;">开始时间</th>
                            <th>开展时长(分钟)</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人</th>
                            <th>联系人电话</th>
                            <th>任务状态</th>
                            <th>检查人</th>
                            <th>审核人</th>
                            <th>操作</th>
                            <th>现场照片</th>
                            <th>应到人数</th>
                            <th>实到人数</th>
                            <th>请假人员</th>
                            <th>出勤率</th>
                            <th>上传会议记录</th>
                            <th>评价得分</th>
                            <th>查看心得</th>
                            <th>是否异常</th>
                            <th>备注</th>
                        </tr>
                    </thead>
                    <tbody class="table_info" id="tBody">
                   	    <c:forEach items="${list}" var="c" varStatus="status">
	                        <tr>
	                        	<td data-label="已读回执" class="receipts">${c.read_status }</td>
	                            <td data-label="党支部" class="Party_name">${c.org_name }</td>
	                            <td data-label="开展主题">${c.theme_ }</td>
	                            <td data-label="支部主题">${c.meeting_theme_secondary }</td>
	                            <td data-label="开始时间" class="PublishTime"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${c.start_p }" /></td>
	                            <td data-label="开展时长">${c.total_time }</td>
	                            <td data-label="开展地点">${c.place }</td>
	                            <td data-label="主持人">${c.host }</td>
	                            <td data-label="联系人">${c.contact }</td>
	                            <td data-label="联系人电话">${c.contact_phone }</td>
	                            <td data-label="任务状态" class="TaskState">
	                            	<c:if test="${c.task_status == '1'}"> 
	                            		 已提交
									</c:if>
									<c:if test="${c.task_status == '2'}"> 
	                            		 已撤回
									</c:if>
									<c:if test="${c.task_status == '3'}"> 
	                            		 被驳回
									</c:if>
									<c:if test="${c.task_status == '4'}"> 
	                            		 已通过
									</c:if>
									<c:if test="${c.task_status == '5'}"> 
	                            		 已指派
									</c:if>
									<c:if test="${c.task_status == '6'}"> 
	                            		 未检查
									</c:if>
									<c:if test="${c.task_status == '7'}"> 
	                            		 已检查
									</c:if>
	                            </td>
	                            <td data-label="检查人">${c.check_person_us }</td>
	                            <td data-label="审核人">${c.auditor }</td>
	                            <td data-label="操作" class="table_content">
	                            	<c:if test="${not empty c.plan_id && c.task_status=='6'}">
			                           <button type="button" class="btn btn-default td_assign_btn">检查</button>
			                           <input class="assignAll" type="hidden" value="${c.plan_id }">  
			                        </c:if>
			                        <c:if test="${not empty c.plan_id && c.task_status=='5'}">
			                           <button type="button" class="btn btn-default td_assign_btn">修改检查</button>
			                           <input class="assignAll" type="hidden" value="${c.plan_id }">  
			                        </c:if>
	                            	
	                            </td>
	                            <td data-label="现场照片" class="img_td">
	                            	<input type="hidden" class="imageNemeOrg" value="${c.image }" name="imageNeme"/>
	                            </td>
	                            <td data-label="应到人数">${c.shoule_persons }</td>
	                            <td data-label="实到人数">${c.actual_persons }</td>
	                            <td data-label="请假人员">${c.leave_persons }</td>
	                            <td data-label="出勤率">${c.attendance }</td>
	                            <td data-label="上传会议记录">
	                            	<c:if test="${not empty c.attachment_n}"> 
	                           			<a class="meetingAnnex">${c.check_status }
		                            		<input type="hidden" class="annexName" value="${c.attachment_n }" name="annexName"/>
		                            	</a> 
									</c:if>  
									<c:if test="${empty c.attachment_n}"> 
									     ${c.check_status }                     
									</c:if>
	                            </td>
	                            <td data-label="评价得分">
	                            	<a href="/gradedetail?meetingId=${c.meeting}&branchId=${c.org_id_u}">
	                            		${c.evaluation_score }
	                            	</a>
	                            </td>
	                             <td data-label="查看心得">
								     <c:if test="${c.task_status gt 4}">
								     <a class="check_reply_state">查看</a>
				                     <input type="hidden" value="${c.inform_id }"/>
				                     <input type="hidden" value="${c.organization_id }"/>
				                     </c:if>
			                      </td>
	                            <td data-label="是否异常">${c.meeting_state }</td>
	                            <td data-label="检查情况">${c.remark }</td>
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
		    	//已读回执加色
		    	$(function(){
		    		$(".receipts").each(function(){
		    			var receipts_text = $(this).text();
		    			if(receipts_text == "未读"){
		    				$(this).addClass("color_blue_1");
		    			}
		    		})
		    	})
				
			</script>
			
			<!-- 模态框（Modal） -->
	        <div class="modal fade" id="keyAssign" tabindex="-1" role="dialog" aria-labelledby="keyAssignLabel" aria-hidden="true">
	            <div class="modal-dialog">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        &times;
	                    </button>
	                        <h4 class="modal-title" id="keyAssignLabel">默认分配</h4>
	                    </div>
	                    <div class="modal-body">点击确定系统将任务默认分配给排序在第一位的人员</div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                        <button type="button" class="btn btn_main confirm_keyAssign">确定</button>
	                    </div>
	                </div>
	                <!-- /.modal-content -->
	            </div>
	            <!-- /.modal -->
	        </div>
	        <!-- 模态框（Modal） -->
	        <div class="modal fade" id="Assign" tabindex="-1" role="dialog" aria-labelledby="assignLabel" aria-hidden="true">
	            <div class="modal-dialog">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                            &times;
	                        </button>
	                        <h4 class="modal-title" id="assignLabel">系统匹配最优人员</h4>
	                    </div>
	                    <div class="modal-body">
	                        <div class="assign_modal_container">
	                            <div class="assign_modal">
	                                <div class="assign_member">
	                                <input type="hidden" class="hiddenValue">
	                                <input type="hidden" class="assignId">
	                                <input type="hidden" class="assign_name">
	                                    <ul class="member_list member_list_append">
	                                      
	                                    </ul>
	                                </div>
	                                <div class="outof_assign_member">
	                                    <span><!-- 不可指派人员： --></span>
	                                    <ul class="outof_assign_list member_list outof_assign_list_append">
	                                             
	                                    </ul>
	                                </div>
	                            </div>
	                            <div class="operation_container">
	                                <div class="inner_operation">
	                                    <button type="button" class="btn btn-sm btn-default add_member_btn">
	                                        <img src="${ basePath}/images/assign_icon.png"/>
	                                                                                                                     添加
	                                    </button>
	                                    <button type="button" class="btn btn-sm btn-default btn_delete">
	                                        <img src="${ basePath}/images/assign_icon.png"/>
	                                                                                                                      删除
	                                    </button>
	                                    <div class="form-horizontal add_memeber_form" role="form">
	                                        <div class="form-group">
	                                            <div class="col-sm-12">
	                                                <input type="text" class="form-control add_member_input" placeholder="请输入姓名进行添加">
	                                            </div>
	                                        </div>
	                                        <div class="addPersons">
	                                        </div>
	                                        <div class="form-group">
	                                            <div class="col-sm-offset-2 col-sm-10 form_btn">
	                                                <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
	                                                <button type="button" class="btn btn-sm btn_main form_add_btn">确定</button>
	                                            </div>
	                                        </div>
	                                        
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                        <button type="button" class="btn btn_main confirm_assign">指派</button>
	                    </div>
	                </div>
	                <!-- /.modal-content -->
	            </div>
	            <!-- /.modal -->
	        </div>
	          <!--查看心得弹框  -->
        	
    <div class="modal fade check" id="check" tabindex="-1" role="dialog" aria-labelledby="checkLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                   </button>
                    <h4 class="modal-title" id="checkLabel">已读回执</h4>
                </div>
                <div class="modal-body">
                    <table class="col-sm-12 col-xs-12 replaytable">
                        <thead>
                            <tr>
                                <th style="text-align: left;">姓名</th>
                                <th class="select_state" style="text-align: left;">
                                    <!-- <select class="select_state">
                                        <option value="allState">已读/未读</option>
                                        <option value="read">已读</option>
                                        <option value="noRead">未读</option>
                                    </select> -->
                                                                                                             通知
                                    <input class="reply_infromId" type="hidden">
                                    <input class="reply_orgId" type="hidden">
                                  
                                </th>
<!--                                 <th>评分</th> -->
                                <th style="text-align: left;">是否已上传心得</th>
                            </tr>
                        </thead>
                        <tbody class="tbody_reply">
                          
                        </tbody>

                    </table>
                </div>
<!--                 <div class="modal-footer"> -->
<!--                     <button type="button" class="btn btn_main">确定</button> -->
<!--                 </div> -->
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
	    
        <!--查看心得弹框  -->
	    <portlet:resourceURL id="/hg/assigned" var="assigned"/>
	    <!--获取指派人员 列表 -->
	    <portlet:resourceURL id="/hg/getAssignPersons" var="getAssignPersons"/>
	    <!--指派人员  -->
	    <portlet:resourceURL id="/hg/hg/assignPersonCheck" var="assignPersonCheck"/>
	    <!--添加指派人员  -->
	    <portlet:resourceURL id="/hg/assignedAddPerson" var="assignedAddPerson"/>
	    <!--一键指派  -->
	    <portlet:resourceURL id="/hg/assignedAddPersonAll" var="assignedAddPersonAll"/>
	    
	    <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
	    
	    <script type="text/javascript">
	        //一键指派  弹窗 点击确定
	        $(".confirm_keyAssign").click(function() {
	            //  something do
	            	assignAll();
	            $("#keyAssign").modal("hide");
	            $(".modal-backdrop").css("display","none");
	        })
	
	        //指派弹窗 点击指派  
	        $(".confirm_assign").click(function() {      
	            	//指派人员
	            var userName=$(".assign_name").val();	
	        	var assignId=$(".assignId").val();
            	if(!assignId){alert("请选择人员!");return;}
            	    $.hgConfirm("提示",'确认指派?     被指派人 ('+userName+')');
					$("#hg_confirm").modal("show");
					$("#hg_confirm .btn_main").click(function(){
						$("#hg_confirm").modal("hide");
						assignPerson();
						/* $.tip("指派成功");
						window.location.reload();	 */
		            })
	            	
	                 
	            $("#Assign").modal("hide");
	        })
	
	        //人员获取和失去焦点
	        $(".member_list").find("li").click(function() {
	            $(this).toggleClass("mermber_on")
	        })
	
	        //添加指派人员  输入框置空 表单出现
	        $(".add_member_btn").click(function() {
	            $(this).siblings(".add_memeber_form").find(".add_member_input").val("");
	            $(this).siblings(".add_memeber_form").css("display", "block");
	        })
	
	        //点击取消  添加指派人员表单消失
	        $(".form_hide_btn").click(function() {
	            $(this).parents(".add_memeber_form").css("display", "none");
	        })
	
	        //点击确定  确认添加指派人员
	        $(".form_add_btn").click(function() {
	        	addPerson();
	        	$(".addPersons").empty();
	            if (!$(this).parents(".add_memeber_form").find(".add_member_input").val()) {
	                return;
	            } else {
	                //do something
	                $(this).parents(".add_memeber_form").css("display", "none");
	            }
	
	        })
	        //指派按钮
	        $(".td_assign_btn").click(function() {
	            $("#Assign .operation_container").find(".add_memeber_form").css("display", "none");
	            $("#Assign").modal("show");
	            var id=$(this).next().val();
	            getAssignPerson(id);
	           
	        })
	
	        //表单清空选项
	        $(".search_reset").click(function() {
	            $(this).parents(".btn_group").siblings(".form-group").find("select").each(function() {
	                var _target = $(this).find("option")[0];
	                $(this).val($(_target).html());
	            })
	        })
	
	        $(function() {
	            //重置宽度
	            var oldWidth = $(".file_input").width();
	            $(".file_input").width((oldWidth - 24) + "px");
	            getSconedParty();
	            
	            //获取会议类型
	            $.ajax({
	    	            url: '${getMeetingTypeAndTheme}',  
	    	            type: 'POST',  
	    	            data: "",
	    	            dataType:'json',
	    	            async: false,   
	    	            success: function (data) {  
	    	                    console.log(data)
	    	                    for(var i=0;i<data.length;i++){
	    	                         var c=data[i];
	    	                    	if(c.type=='meetingType'){
	    	                    		 $(".meetingType").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
	    	                    	}	
	    	                    	if(c.type=='news'){
	    	                    		 $("select[name='theme']").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
	    	                    	}	
	    	                    }
	    	            },  
	    	            error: function (data) {  
	    	                   alert("获取数据失败");  
	    	            }  
	           }); 
	        })
	
	        //上传文件 美化
	        $(".file_input").on("change", "input[type='file']", function() {
	            var _path = $(this).val();
	            $(this).siblings(".show_path").html(_path);
	        })
	        
	         function  getSconedParty(){
			   var url="${assigned}";
			   var data={paramType:"sconedParty"};
			   $.ajax({
				   url:url,
		 		   data:data,
		 		   dataType:"json",
		 		   success:function(result){
		 			          console.log(result);
		 			         for(var i=0;i<result.length;i++){
		 			        	 $(".sconed_party").append('<option value="'+result[i].org_id+'">'+result[i].org_name+'</option>')
		 			         }
		 		   }
			   });
		   } 
	     
		   $(".sconed_party").change(function(){
			   $(".party_branch").empty();
			   $(".party_branch").append('<option value="">--选择--</option>')
			   var url="${assigned}";
			   var pid=$(".sconed_party").val();
			   var data={paramType:"partyBranch",pid:pid};
	// 		   alert(pid);
			   $.ajax({
				   url:url,
		 		   data:data,
		 		   dataType:"json",
		 		   success:function(result){
		 			  console.log(result);
		 			  for(var i=0;i<result.length;i++){
				        	 $(".party_branch").append('<option value="'+result[i].org_id+'">'+result[i].org_name+'</option>')
				         }
		 		           }
		 		   });
		   });
		     //获取指派人员
		   function getAssignPerson(id){
	// 		   alert("指派会议id"+id);
			   $(".hiddenValue").val(id);
			   var url="${getAssignPersons}";
			   $(".member_list_append,.outof_assign_list_append").empty();
			   var data={id:id};
		       $.ajax({
					   url:url,
					   data:data,
					   dataType:"json",	
					   success:function(result){
						   console.log(result);
						   for(var i=0;i<result.length;i++){
							  /*  if(result[i].state=="0"){ */
								       var li=$('<li class="_assgin_person">'+result[i].assigne_name+'</li>');
								       $(".member_list_append").append(li);  
								       li.data("id",result[i].assigne_user_id);
			                		   li.data("name",result[i].assigne_name);
			                		  
			                		   console.log(li.data("id"));
							  /*  } */
							   /* if(result[i].state=="1"){
								   $(".outof_assign_list_append").append('<li onclick="getDataa();">'+result[i].assigne_name+'</li>');    
							   } */
						   }
						   change_icon();
					    }		   
			       });
			   }
		     function change_icon(){
				     $(".member_list ").on("click","._assgin_person",function(){
				    	 $(".member_list ").find(".delete_li").removeClass("delete_li");
				    	 $(this).addClass("delete_li");
				    	 var assignId=$(this).data("id");
				    	 var assign_name=$(this).data("name");
				    	  $(".assignId").val(assignId);
						  $(".assign_name").val(assign_name);
				     });
		    }
		  
		    /*  指派人员 */
		    function assignPerson(){
		    	 var id=$(".hiddenValue").val();
		    	 var assignId=$(".assignId").val();
		    	 var assign_name=$(".assign_name").val();
		    	 if(!assignId){alert("请选择人员!");return;}
		    	 var url="${assignPersonCheck}";
		    	 var data={id:id,assignId:assignId,assign_name:assign_name};
		    	  $.ajax({
		    		     url:url,
		    		     data:data,
		    		     dataType:"text",
		    		     success:function(result){
		    		    	 if(result=="SUCCESS"){
		    		    		location.reload();}
		    		     }
		    	     });
		     }
		
		     //添加指派人员
		     $(".add_member_input").on("change",function(){
		       
		             var url="${assignedAddPerson}";
		             var userName=$(".add_member_input").val();
		             var data={userName:userName,path:"find"};
		             $(".addPersons").empty();
		             $.ajax({
			    		     url:url,
			    		     data:data,
			    		     dataType:"json",
			    		     success:function(result){
			    		    	     console.log(result.state); 
					    		     if(result.state=='succeed'){
					    		    	 for(var i=0;i<result.data.length;i++){
					    		    		 var c=result.data[i];
					    		    		 $(".addPersons").append('<label><input name="Fruit" type="radio" value="'+c.user_id+'"/>'+c.user_name+':'+c.org_name+'</label>');
					    		    	  }
					    		      }
					    		    if(result.state=='fail'){
					    		    	    $(".addPersons").append("<p>"+result.message+"<p/>");
					    		          }
			    		       }
		    	           });
		     
		     });
		     
		     function addPerson(){
		    	 var name=$("input:checked").parent().text();
		    	 var assigne_user_id=$('input:radio:checked').val();
		    	 var url="${assignedAddPerson}";
		             var data={name:name,assigne_user_id:assigne_user_id,path:"add"};            
		             $.ajax({
		    		     url:url,
		    		     data:data,
		    		     dataType:"json",
		    		     success:function(result){
		    		    	 if("succeed"==result.state){
		    		    		  var li=$('<li class="_assgin_person">'+name+'</li>');
		   				          $(".member_list_append").append(li);  
		   				          li.data("id",assigne_user_id);
		             		      li.data("name",name);
		    		    	 }else{
		    		    		   alert(result.message);
		    		    	 }
		    		    	 
		    		     }
		             });
		     }
		   $(".btn_delete").click(function(){
			      var assignId=$(".assignId").val();
			      var url="${assignedAddPerson}";
			      var data={assignId:assignId,path:"delete"}; 
			      $.ajax({
		 		     url:url,
		 		     data:data,
		 		     dataType:"json",
		 		     success:function(result){
		 		    	    $(".delete_li").remove();
		 		     }
		          });
		   });
		     
	    </script>
	    <script type="text/javascript">
		  //图片下载
// 			$(".images").click(function(){
// 				var name = $(this).attr("dataUrl");
// 				if(confirm("下载 图片")){
// 					$(this).attr("href","${download}&imageNeme="+name);
// 				}else{
// 					$(this).attr("href","javascript:;");
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
			 
			//会议附件下载
			$(".table_info").on("click",".meetingAnnex",function(){
				var name = $(this).find("input").val();
				if(confirm("下载附件")){
					$(this).attr("href","${download}&imageNeme="+name);
				}else{
					$(this).attr("href","javascript:;");
				}
				
			});
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
	    	
	    	
		    /* 查看心得 */
		    
	         /*查看*/
   $(".table_info").on("click",".check_reply_state",function(){
   	$("#check").modal("show");
   	var infromId=$(this).next().val();
   	var orgId=$(this).next().next().val();
   	$(".reply_infromId").val(infromId);
   	$(".reply_orgId").val(orgId);
   	var url="${taskCheckReplyState}";
   	/* var url1="${showCommentDetailUrl}"; */
   	var url1="/showevaluation?meet=1";
   	var url2="${showExperienceDetailUrl}";
   	
       $.ajax({
   	      url:url,
   	      data:{infromId:infromId,orgId:orgId,path:"allState"},
   	      dataType:"json",
   	      async: false,
   	      success:function(data){ 
   	    	  $(".tbody_reply").empty();
       	    	 for(var i=0;i<data.length;i++){
   	    		  var c=data[i];
  	   	    		  if(!c.user_name){continue;}
   	    		  if(c.check_status == '未读'){
   	    			  if(c.comments_state == 1){
   	    				  if(c.upload_state == 1){
    	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;
   	    				  }else{
    	    					  /* $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	    				  }
   	    			  }else{
   	    				  if(c.upload_state == 1){
    	    					 /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
   	    				  }else{
    	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td>否</td> </tr>') ;  */
   	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
   	    				  }
   	    			  }
  	   	    		  }else{
		       	   	    		if(c.comments_state == 1){
			  	    				  if(c.upload_state == 1){
			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
			  	    				 	  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
			  	    				  }else{
			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
			  	    				  }
			  	    			  }else{
			  	    				  if(c.upload_state == 1){
			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
			  	    					  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ; 
			  	    				  }else{
			  	    					/*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td>否</td> </tr>') ;  */
			  	    					$(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已通知</td> <td>否</td> </tr>') ; 
			  	    				  }
			  	    			  }
  	   	    		  }
   	    		  
   	    		 /*  $(".seeExperienceDetail").attr("href",url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
   	    		  $(".seeScoreDetail").attr("href",url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
   	    		   */
  	   	    		  
   	    	    }
   	      	}
           });
       });
	    </script>	 
	</body>
</html>