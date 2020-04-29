<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<portlet:resourceURL id="/part/meeting/check/Page" var="PartyMeetingCheckPage" />
<portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
</portlet:actionURL>
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

<!-- 		下载图片 -->
<portlet:resourceURL id="/PartyImageDownCommand" var ="download"></portlet:resourceURL>
<script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${ basePath}/css/assign.css" />
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>组织部查看活动进度(二级党委)</title>
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
			.table_outer_box > table thead, tbody tr {
				display: table-row !important;
				width: 100%;
				table-layout: fixed;
			}
			#searchForm .layui-form-item .layui-inline .keyword {
				width: 300px;
				margin-right: 0px;
			}
			#personInfo .layui-form-item .layui-input-inline{
				width:200px
			}
			#personInfo .layui-form-label{
				width:140px;
				font-weight:bold;
			}
			#personInfo .layui-form-label-text{
				float: left;
				display: block;
				padding: 0 10px;
				width: 200px;
				font-weight: 400;
				line-height: 40px;
				font-size: 16px;
				text-align: left;
			}
			th, tr{
				text-align:center !important;
			}
			.layui-table-page-center{
				text-align: center;
			}
		</style>
	</head>
	<body class="front">
		<div class="table_form_content">
			<!-- 右侧盒子内容 -->
			<div class="activity_manage_page">
				<div class="breadcrumb_group" style="margin-bottom: 20px;">
					当前位置：
					<span class="layui-breadcrumb" lay-separator=">">
							<a href="javascript:;">组织生活管理</a>
							<a href="javascript:;">活动抽查</a>
						</span>
				</div>
				<div class="bg_white_container">
					<form class="layui-form" id="searchForm">
						<div class="layui-form-item">
							<div class="layui-inline">
								<div class="layui-input-inline keyword">
									<input type="text" name="keyword"  placeholder="请输入组织名称、主题关键字" class="layui-input">
								</div>
								<button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
							</div>
						</div>
					</form>
					<table id="meetingCheckTable" lay-filter="meetingCheckTable"></table>
				</div>
			</div>
		</div>
			
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
                                <div class="assign_member" style="height: 250px;">
                                <input type="hidden" class="hiddenValue">
                                <input type="hidden" class="assignId">
                                <input type="hidden" class="assign_name">
                                    <ul class="member_list member_list_append">
                                      
                                    </ul>
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
		<script type="text/html" id="meetingCheckTableBtns">
			{{#  if(d.id != '' && d.id != null && d.task_status == "6"){ }}
			<a class="layui-btn layui-btn-xs" lay-event="check">抽查</a>
			{{#  } }}
			{{#  if(d.id != '' && d.id != null && d.task_status == '5'){ }}
			<a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
			{{#  } }}
		</script>
    <script type="text/javascript">
		layui.use(['table','layer','form'], function() {
			var table = layui.table,
					layer = layui.layer,
					form = layui.form;
			var pageInfo = {
				page:1,
				size:10
			};
			renderTable(1,pageInfo.size);
			form.on('submit(searchForm)', function (data) {
				renderTable(1,pageInfo.size);
			})
			function renderTable(page,size){
				var  where = {
					keyword: $("#searchForm input[name=keyword]").val()
				};
				var ins = table.render({
					elem: '#meetingCheckTable',
					where: where,
					height:560,
					url: '${PartyMeetingCheckPage}', //数据接口
					method: 'post',
					page: {
						limit:size,   //每页条数
						curr:page,
						limits:[10,15,20],
						prev:'&lt;上一页',
						next:'下一页&gt;',
						theme: '#FFB800',
						groups:4
					},
					cols: [[ //表头
						{field: 'org_name', align:'center',width:320, title: '党组织'},
						{field: 'meeting_theme', align:'center',width:320, title: '开展主题',templet:function(d){
								return '<a  href="/approvaldetails?meetingId='+d.meeting_id+'&orgType=secondary">'+d.meeting_theme+'</a>';
							}},
						{field: 'start_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
						{field: 'total_time', align:'center', title: '时长',width:100,templet: function(d){return d.total_time/60;}},
						{field: 'campus', align:'center', title: '开展地点',templet: function(d){
								return d.campus+" "+d.placename;
							}},
						{field: 'task_status', align:'center', title: '抽查状态',width:120,templet: function(d){
								var status = '';
								switch(parseInt(d.task_status)){
									case 1:status = '待审核';break;
									case 2:status = '已撤回';break;
									case 3:status = '已驳回';break;
									case 4:status = '已通过';break;
									case 5:status = '已指派';break;
									case 6:status = '未检查';break;
									case 7:status = '已检查';break;
								}
								return status;
							}},
						{field: 'checker', align:'center', title: '抽查人'},
						{field: 'operation', align:'center', title: '操作',width:200,toolbar: '#meetingCheckTableBtns'},

					]],
					done: function(res, curr, count){
						pageInfo.page = curr;
						pageInfo.size = ins.config.limit;
					}
				});
				$(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
				$(".layui-table-view .layui-table-page").removeClass("layui-table-page");
				//监听事件
				table.on('tool(meetingCheckTable)', function(obj){
					switch(obj.event){
						case 'check':
							renderCheck(obj);
							break;
						case 'edit':
							renderCheck(obj);
							break;
					};
				});
			}
		});
		Date.prototype.format = function (fmt) {
			var o = {
				"M+": this.getMonth() + 1, //月份
				"d+": this.getDate(), //日
				"h+": this.getHours(), //小时
				"m+": this.getMinutes(), //分
				"s+": this.getSeconds(), //秒
				"q+": Math.floor((this.getMonth() + 3) / 3), //季度
				"S": this.getMilliseconds() //毫秒
			};
			if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		}
		//指派按钮
		function renderCheck(obj) {
			$("#Assign .operation_container").find(".add_memeber_form").css("display", "none");
			$("#Assign").modal("show");
			getAssignPerson(obj.id);
		}
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
        	if(!assignId){alert("请选择人员!   "+userName);return;}
        	    $.hgConfirm("提示",'确认指派?     被指派人('+userName+')');
				$("#hg_confirm").modal("show");
				$("#hg_confirm .btn_main").click(function(){
					$("#hg_confirm").modal("hide");
					assignPerson();
					/* $.tip("指派成功");
					window.location.reload(); */	
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
						   var name = result[i].member_name + ":" + result[i].org_name;
						   var li=$('<li class="_assgin_person">'+ name + '</li>');
						   $(".member_list_append").append(li);
						   li.data("id",result[i].user_id);
						   li.data("name", name);
						   console.log(li.data("id"));
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
	  
	     //指派人员
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
	       
	             var url="${assignedAddPerson}"
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
// 			$(".table_info").on("click",".images",function(){
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
				    		var img = '<a class="images" dataUrl="'+imgArr[i]+'" href="' + imgArr[i] +'">图'+(i+1)+',</a>';
				    		$(this).append(img)
				    	}
		    		}
		    	})
		    })
	    </script>
	</body>
</html>