<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>指派人员</title>
    <link rel="stylesheet" href="${ basePath}/css/assign.css" />
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
 th, tr, td {
 border: 1px solid;
}
    </style>
</head>

<body>
        <div class="content_title hidden-xs">
                            指派人员
        </div>
        <div class="content_form">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <div class="col-sm-6 col-xs-12">
                        <span class="col-sm-2 col-xs-3 control-label">二级党组织</span>
                        <div class="col-sm-10 col-xs-9">
                            <select class="form-control sconed_party">
                                          <option value="">请选择</option>
                            </select>           
                        </div>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <span class="col-sm-2 control-label col-xs-3">会议类型</span>
                        <div class="col-sm-10 col-xs-9">
                            <select class="form-control meetingType">
                                    <!-- <option>主题党日</option>
                                    <option>三课一会</option> -->
                                </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <span class="col-sm-2 col-xs-3 control-label">各子支部</span>
                        <div class="col-sm-10 col-xs-9">
                            <select class="form-control party_branch"></select>                                                              
                        </div>
                    </div>
                    <div class="col-sm-6 col-xs-12">
                        <span class="col-sm-2 col-xs-3 control-label">开展时间</span>
                        <div class="col-sm-10 col-xs-9">
                            <input size="16" type="text" name="lab_date" id="labCheckDate" 
                             onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   class="form_datetime form-control start_date">
                        </div>
                    </div>               
                </div>
                <div class="btn_group">
                    <button type="button" onclick="queryInfromation();" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3">查询</button>
                    <span class="col-xs-1 visible-xs"></span>
                    <button type="button" class="btn btn-default search_reset col-sm-1 col-xs-3 clern_table">清空</button>
                    <span class="col-xs-1 visible-xs"></span>
                    <button type="button" class="btn btn-default main_color_btn key_assign col-sm-2 col-xs-4" data-toggle="modal" data-target="#keyAssign">
                            <img src="${ basePath}/images/assign_icon.png"/>
                                                                               一键指派
                        </button>
                </div>
            </form>
            <div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                            <th>党委名称</th>
                            <th>会议类型</th>
                            <th>开展主题</th>
                            <th>开展时间</th>
                            <th>开展地点</th>
                            <th>主持人</th>
                            <th>联系人及电话</th>
                            <th><select><option>抽查状态</option></select></th>
                            <th>操作</th>
                            <th>参会人数</th>
                            <th>现场照片</th>
                            <th>抽查人</th>
                        </tr>
                    </thead>
                    <tbody class="table_info">
                     <c:forEach var="c" items="${list }"> 
                        <tr>
                            <td>${c.org_name }</td>
                            <td>${c.meeting_type }</td>
                            <td>${c.meeting_theme }</td>
                            <td>${c.time }</td>
                            <td>${c.place }</td>
                            <td>${c.host }</td>
                            <td>${c.contact}${c.contact_phone }</td>
                            <td>${c.state }</td>            
	                         <td>
	                      <c:if test="${not empty c.plan_id && c.state=='未检查'}">
	                           <button type="button" class="btn btn-default td_assign_btn " data-toggle="modal" data-target="#Assign">指派</button>
	                           <input class="assignAll" type="hidden" value="${c.plan_id }">  
	                      </c:if>
	                         </td>
	                                          
                            <th>${c.count }</th>
                            <th>
                              <c:if test="${not empty c.url}">
                                <a href="${c.url }">查看</a>
                              </c:if>
                            </th>
                            <th>${c.member_name }</th>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
                                <div class="assign_member">
                                <input type="hidden" class="hiddenValue">
                                <input type="hidden" class="assignId">
                                <input type="hidden" class="assign_name">
                                    <ul class="member_list member_list_append">
                                      
                                    </ul>
                                </div>
                                <div class="outof_assign_member">
                                    <span>不可指派人员：</span>
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
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10 form_btn">
                                                <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
                                                <button type="button" class="btn btn-sm btn_main form_add_btn">确定</button>
                                            </div>
                                        </div>
                                        <div class="addPersons">
                                         <!-- <label><input name="Fruit" type="checkbox" value="" />李笑笑</label> --> 
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
    <script>
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
            	//alert("confirm_assign");
                 assignPerson();
            $("#Assign").modal("hide");
        })

        //人员获取和失去焦点
      /*   $(".member_list").find("li").click(function() {
            $(this).toggleClass("mermber_on")
        }) */

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
        $(".table_info").on("click",".td_assign_btn",function(){
        	  $("#Assign .operation_container").find(".add_memeber_form").css("display", "none");
              var id=$(this).next().val();
              getAssignPerson(id);
        });
    /*     $(".td_assign_btn").click(function() {
          
           
        }) */

        //表单清空选项
        $(".search_reset").click(function() {
            $(this).parents(".btn_group").siblings(".form-group").find("select").each(function() {
                var _target = $(this).find("option")[0];
                $(this).val($(_target).html());
            })
        })
      /*   $(".form_datetime").datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true
        }); */

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
					   if(result[i].state=="0"){
						       var li=$('<li>'+result[i].assigne_name+'</li>');
						       $(".member_list_append").append(li);  
						       li.data("id",result[i].assigne_user_id);
	                		   li.data("name",result[i].assigne_name);
	                		  
	                		   console.log(li.data("id"));
					   }
					   if(result[i].state=="1"){
						   $(".outof_assign_list_append").append('<li onclick="getDataa();">'+result[i].assigne_name+'</li>');    
					   }
				   }
				   change_icon();
			    }		   
	       });
	   }
     function change_icon(){
		         $(".member_list ").on("click","li",function(){
		         $(this).toggleClass("mermber_on")
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
    	 if(!assignId){alert("请选择指派人员");return;}
    	 var url="${assignPersonCheck}";
    	 var data={id:id,assignId:assignId,assign_name:assign_name};
    	  $.ajax({
    		     url:url,
    		     data:data,
    		     dataType:"text",
    		     success:function(result){
    		    	 if(result=="SUCCESS"){
    		    		location.reload();
    		    		}
    		     }
    	     });
     }

     //查询
     function queryInfromation(){
    	 var url="${assigned}";
    	// var sconed_party=$(".sconed_party").val();
    	 var meetingType=$(".meetingType").val();
    	 var start_date=$(".start_date").val();
    	 var party_branch=$(".party_branch").val();
    	 var data={meetingType:meetingType,party_branch:party_branch,start_date:start_date};
    	console.log(data);
    	 $.ajax({
    		     url:url,
    		     data:data,
    		     dataType:"json",
    		     success:function(result){
    		    	 console.log(result);  
    		    	 $(".table_info").empty();
    		    	 for(var i=0;i<result.length;i++){
    		    	   var c=result[i];
    		    	   var tp='<tr><td>'+(c.org_name == undefined ? "":c.org_name)+'</td> '+
                       '<td>'+c.meeting_type +'</td> '+
                       '<td>'+c.meeting_theme +'</td>'+
                       '<td>'+c.start_time +'</td>'+
                       '<td>'+c.place +'</td>'+
                       '<td>'+c.host +'</td>'+
                       '<td>'+c.contact + c.contact_phone +'</td>'+
                       '<td>'+c.task_status +'</td> '+         
                       '<td>'+
                          '<button  type="button" class="btn btn-default td_assign_btn" data-toggle="modal" data-target="#Assign">指派</button> '+
                          '<input class="assignAll" type="hidden" value="'+c.id+'">'+                          
                        '</td>'+                                          
                       '<td>'+c.count+'</td>'+
                       '<td><a href="'+c.image+'"></a></td>'+
                       '<td>'+(c.user_name == undefined ? "":c.user_name)+'</td></tr>';  
                       var tr=$(tp);
                       $(".table_info").append(tr);
    		    	 }
    		    
    		     }
    	 });
     }
     //查询添加人员
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
			    		    		 $(".addPersons").append('<label><input name="Fruit" type="radio" value="'+c.user_id+'"/>'+c.user_name+'</label>');
			    		    	  }
			    		      }
			    		    if(result.state=='fail'){
			    		    	    $(".addPersons").append("<p>"+result.message+"</P>")
			    		          }
	    		       }
    	           });
     
     });
     
     
     
     //添加指派人员
     function addPerson(){
    	 var name=$("input:checked").parent().text();
    	 var assigne_user_id=$('input:radio:checked').val();
    	 var url="${assignedAddPerson}"
             var data={name:name,assigne_user_id:assigne_user_id,path:"add"};            
             $.ajax({
    		     url:url,
    		     data:data,
    		     dataType:"json",
    		     success:function(result){
    		    	 if("succeed"==result.state){
    		    		  var li=$('<li>'+name+'</li>');
   				          $(".member_list_append").append(li);  
   				          li.data("id",assigne_user_id);
             		      li.data("name",name);
    		    	 }else{
    		    		   alert(result.message);
    		    	 }
    		    	 
    		     }
             });
     }
     //删除
   $(".btn_delete").click(function(){
	      var assignId=$(".assignId").val();
	      var url="${assignedAddPerson}"
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
     
     //一键指派
     function assignAll(){
    	 var idss=$(".assignAll");          
         var resourcesId = new Array("");
         for(var i=0;i<idss.length;i++){
         	var resourceId=$(idss[i]).val();
         	resourcesId.push(resourceId);} 
         var ids = resourcesId.join(",").substring(1)+"";
         $.ajax({
        	     url:'${assignedAddPersonAll}',
        	     data:{ids:ids},
        	     dataType:"json",
     		     success:function(result){ location.reload();}
         });
     }
    </script>
</body>

</html>