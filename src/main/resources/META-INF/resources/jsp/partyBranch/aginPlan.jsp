
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- 保存新增站点 -->
 <%-- <portlet:actionURL name="${submitCommand }" var="submitForm"/> --%>
  <portlet:actionURL name="/hg/postSubmissions" var="submitForm"/> 

 <portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>
 
 <portlet:resourceURL id="/hg/getGroup" var="getGroup"/>
 <portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl" />
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl" />
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl" />
<html>
	<head>
	<style type="text/css">
	    input#button1 {
		    float: right;
		}
		textarea {
		    width: 100%;
		    height: 100px;
		}
		
		input#button2 {
        float: left;
       }
       
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
<%-- 	    <link rel="stylesheet" href="${basePath}/css/common.css" /> --%>
<%-- 	    <link rel="stylesheet" href="${basePath}/css/bootstrap.min.css" /> --%>
	    <link rel="stylesheet" href="${basePath}/css/assign.css" />
<%-- 	    	<script type="text/javascript" src="${basePath}/js/jquery-3.2.1.min.js"></script> --%>
			<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript" src="${basePath}/js/form.js?version=99"></script>
			<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
		
		<script type="text/javascript" >
            $(function() {	
            	
           
            /* var form = ${design};
            var hgDoms = new HgDoms(form);
            var formGroup = $('#hg-form-container');
            formGroup.html(hgDoms.generateHtml() + formGroup.html());
            $('.datetime').attr('onClick', "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"); */
            var form = ${design};
            var uploadUrls = {
            		file: '${uploadfileUrl}',
            		image: '${uploadimageUrl}',
            		video: '${uploadvideoUrl}'
            }

            var hgDoms = new HgDoms(form, uploadUrls, 'hg-form-container');
            $.ajax({  
                url: '${getPublicObject}',  
                type: 'POST',  
                data: "",
                dataType:'json',
                async: false,   
                success: function (returndata) {  
	                    console.log(returndata)
	                    for(var i=0;i<returndata.length;i++){
	                    	 $("select[name='publicObject']").append('<option value="'+returndata[i].id+'">'+returndata[i].party_organization_name+'</option>');
	                    }
                },  
                error: function (returndata) {  
                    alert("cuowu");  
                }  
           });
            
            
        	alert("ok");
            var data=$(".mapValue").val();
	    	data=data.substring(0,data.length);
	    	alert(data);
	    	var j= JSON.parse(data); 
	    	console.log(j.sconedId);
	    	alert(j);
	       $("select[name='secondaryCommittee']").prepend('<option selected value="'+j.sconedId+'">'+j.sconedName+'</option>');      
	       $("select[name='branch']").prepend('<option selected value="'+j.branchId+'">'+j.branchName+'</option>');
	       $("select[name='conferenceType']").prepend('<option selected value="'+j.meetingType+'">'+j.meetingType+'</option>');
	       $("select[name='subject']").prepend('<option selected value="'+j.meetingTheme+'">'+j.meetingTheme+'</option>');
	       $("#infrom_id").val(j.infromid);
	       
	       var div='<div class="col-sm-6 col-xs-12"> '+
           '<span class="col-sm-2 col-xs-3 control-label">参会人员</span> '+
			 '<div class="col-sm-10 col-xs-9"> '+                               
			   '<select class="form-control ${class}" name="participate"></select> '+
              '<a href="javascript:;" data-toggle="modal" data-target="#commonStaff">常用人员</a> '+
			 '</div> '+                            
			'</div>';                     	
                $("#hg-form-container > div").eq(6).replaceWith(div);
	       
	       
	       
	       $.ajax({  
               url: '${getGroup}',  
               type: 'POST',  
               data: {orgId:j.branchId},
               dataType:'json',
               async: false,   
               success: function (returndata) {  
	                    console.log(returndata)
	                    for(var i=0;i<returndata.length;i++){
	                    	 $("select[name='participate']").append('<option value="'+returndata[i].group_id+'">'+returndata[i].group_name+'</option>');
	                    	
	                    	 var tr=$('<tr> '+
		                            '<td>'+returndata[i].group_name+'</td> '+
		                            '<td class="delete_group">删除</td> '+
		                            '<td> '+
		                                '<img src="/images/radio.png" /> '+
		                                '<input type="hidden" value="'+returndata[i].group_id+'"/> '+
		                            '</td> '+
		                           '</tr>');
		                           
	                    	 $(".use_group").append(tr);
	                     
	                    }
               },  
               error: function (returndata) {  
                   alert("cuowu");  
               }  
          });
	      
	       
	    
	      
	      
	      
        })
        
		
		</script>
	</head>
	<body>
		<input type="hidden" class="mapValue" value='${map }'>
		<div class="content_info">
        	<div class="content_title">
           		${title }
        	</div>
        	<div class="content_form">
	        	<form class="form-horizontal" role="form" action="${submitForm }" method="post">
	                <div id="hg-form-container" class="form-group">
	                <!-- <textarea rows="3" cols="20" name="content"> </textarea> -->
	                    <input id="content_id" type="hidden" name="content"/>
	                    <input id="submit" type="submit" style="display:none;"/>  
						<input id="hiddenstate" type="hidden" name="state"/>
						<input id="infrom_id" type="hidden" name="infrom_id"/>
						<input id="button2" type="button" value="取消" onclick="formsubmitgraft();"/>
						<input id="button1" type="button" value="发布" onclick="formsubmit();"/>
          	         </div>
	            </form>
			</div>
		</div>
		
		  <!--   <a href="javascript:;" data-toggle="modal" data-target="#commonStaff">常用人员</a> -->
    <!-- 模态框（Modal） -->
    <div class="modal fade check" id="commonStaff" tabindex="-1" role="dialog" aria-labelledby="commonStaffLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                      &times;
                  </button>
                    <h4 class="modal-title" id="commonStaffLabel">常用人员</h4>
                </div>
                <div class="modal-body">
                    <div class="common_list_container col-sm-5 col-xs-12 ">
                        <div class="table_title">常用列表</div>
                        <div class="table_container">
                            <table class="common_list">
                                <thead>
                                    <tr>
                                        <th>名称</th>
                                        <th> 操作</th>
                                        <th> 选择</th>
                                    </tr>
                                </thead>
                                <tbody class="use_group">
                                   
                                </tbody>
                            </table>
                        </div>

                        <div class="add_btn">
                            <button class="btn btn-sm btn-default">
                                <img src="/images/add_icon.png"/>添加
                            </button>
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="text" class="form-control add_member_input groupName" placeholder="请输入新建小组名字">
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-10 form_btn">
                                        <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
                                        <button type="button" class="btn btn-sm btn_main form_add_btn addGroup">确定</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="common_member_container col-sm-7 col-xs-12">
                        <div class="table_title">常用人员</div>
                        <div class="table_container">
                            <table class="common_member_list">
                                <thead>
                                    <tr>
                                        <th>小组名</th>
                                        <th> 姓名</th>
                                    </tr>
                                </thead>
                                <tbody class="group_members">
                                  <!--   <tr>
                                        <td>组织混乱</td>
                                        <td>张三</td>
                                    </tr>
                                    <tr>
                                        <td>组织混乱</td>
                                        <td>张三</td>
                                    </tr>
                                    <tr>
                                        <td>组织混乱</td>
                                        <td>张三</td>
                                    </tr>
                                    <tr>
                                        <td>组织混乱</td>
                                        <td>张三</td>
                                    </tr> -->
                                </tbody>

                            </table>
                        </div>
                        <div class="add_btn">
                            <button class="btn btn-sm btn-default">
                                    <img src="/images/add_icon.png"/>添加
                                </button>
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="text" class="form-control add_member_input" placeholder="请输入姓名进行添加">
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-10 form_btn">
                                        <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
                                        <button type="button" class="btn btn-sm btn_main form_add_btn">确定</button>
                                    </div>
                                </div>
                                 <div class="addPersons">
                                         <!-- <label><input name="Fruit" type="checkbox" value="" />李笑笑</label> --> 
                                 </div>
                            </form>
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn_main">确定</button>
                </div>
            </div>
        </div>
    </div>
	<!-- 常用人员 -->
	
	<!-- 常用人员，组的增加，修改，删除 -->
	 <portlet:resourceURL id="/hg/paersonAndGroupAddDelete" var="paersonAndGroupAddDelete"/>
	 <portlet:resourceURL id="/hg/assignedAddPerson" var="assignedAddPerson"/>
			<script type="text/javascript">
		     function formsubmitgraft(){
		    	$("#hiddenstate").val(0); 
		    	history.back();
		    	//$('#submit').click();
		     }
		 
		     
		     function  formsubmit(){
		    	 
		    	 //var content= ueObj.getContent();
		    	// $("#content_id").val(content); 
		    	// console.log(content);
		    	 $('#submit').click();
		     }
		     
		       //常用人员 添加
		        $(".add_btn").on("click", ">.btn", function() {
		        	addPerson();
		            $(this).siblings("form").css("display", "inline-block");
		        })

		        //取消添加弹窗
		        $(".add_btn").on("click", ".form_hide_btn", function() {
		            $(this).parents("form").css("display", "none");
		            $(this).parents(".form-group").find(".add_member_input").val("");
		        })
		//选择按钮图片变化        
		$(".use_group").on("click","img",function(){
			 $(".use_group img").attr("src","/images/radio.png");  
			 $(this).attr("src","/images/radio_on.png");
			 var groupId=$(this).next().val();
			  $.ajax({  
		               url: '${paersonAndGroupAddDelete}',  
		               type: 'POST',  
		               data: {groupId:groupId,path:"getGroupPersons"},
		               dataType:'json',
		               async: false,   
		               success: function (data) {
		            	   $(".group_members").empty();
		            	   for(var i=0;i<data.length;i++){
		            		   var c=data[i];		
		            		   var tr=$('<tr><td>'+c.group_name+'</td><td>'+c.member_name+'</td></tr>');
		                       $(".group_members").append(tr);
		            	   }
		               }
			  })
        });
		       
		 //删除小组
		 $(".use_group").on("click",".delete_group",function(){			
        	var groupId=$(this).next().find("input").val();
        	$.ajax({  
	               url: '${paersonAndGroupAddDelete}',  
	               type: 'POST',  
	               data: {groupId:groupId,path:"deleteGroup"},
	               dataType:'json',
	               async: false,   
	               success: function (data) {
	            	   $(this).parent().remove();
	               }
        	});   
        });
		 //添加小组
		   $(".addGroup").click(function(){
        	var groupName=$(".groupName").val();
        	var branchId=$("select[name='branch']").val();
         	$.ajax({  
	               url: '${paersonAndGroupAddDelete}',  
	               type: 'POST',  
	               data: {groupName:groupName,branchId:branchId,path:"addGroup"},
	               dataType:'json',
	               async: false,   
	               success: function (data) {
	            	        console.log(data);
	            	        var tr=$('<tr> '+
	                                '<td>'+groupName+'</td> '+
	                                '<td class="delete_group">删除</td> '+
	                                '<td> '+
	                                    '<img src="/images/radio.png" /> '+
	                                    '<input type="hidden" value="'+data.uuid+'"/> '+
	                                '</td> '+
	                               '</tr>');	                               
	                    	 $(".use_group").append(tr);
	                        }
		         })
        }); 
		 
		 
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
					    		    		 $(".addPersons").append('<label><input name="Fruit" type="radio" value="'+c.member_Ethnicity+'"/>'+c.member_name+'</label>');
					    		    	  }
					    		      }
					    		    if(result.state=='fail'){
					    		    	    $(".addPersons").append("<p>查无此人<p/>")
					    		          }
			    		       }
		    	           });
		     
		     });
		     
		     function addPerson(){
		    	 var branchId=$("select[name='branch']").val();
		    	 var assigne_user_id=$('input:radio:checked').val();
		    	 var url="${paersonAndGroupAddDelete}"
		         var data={participant_id:assigne_user_id,branchId:branchId,path:"addPerson"};            
		             $.ajax({
		    		     url:url,
		    		     data:data,
		    		     dataType:"json",
		    		     success:function(result){
		    		    	 var name=$("input:checked").parent().text();
		    		         var groupName=$("src","/images/radio_on.png").parent().prev().prev().html();
		    		    	 var tr=$('<tr><td>'+groupName+'</td><td>'+name+'</td></tr>');
		                     $(".group_members").append(tr);
		    		     }
		             });
		     }
		</script>
	</body>
</html>