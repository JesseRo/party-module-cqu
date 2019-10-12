
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- 保存新增站点 -->
 <%-- <portlet:actionURL name="${submitCommand }" var="submitForm"/> --%>
  <portlet:actionURL name="/hg/postSubmissions" var="submitForm"/> 
  
  <portlet:renderURL var="uploadMeetingNotesUrl">
	<portlet:param name="mvcRenderCommandName" value="/hg/uploadMeetingNotes"/>
  </portlet:renderURL>

<!-- 	附件下载 -->
<portlet:resourceURL id="/PartyImageDownCommand" var ="download">
</portlet:resourceURL>

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
		

		.buttons{
			position: relative;
		    top: 140px;
		    right: 50px;
		    }
		 
		 
		    
	    label.error{
           position: absolute;
		   color: #ce0000;
		   font-size: 12px;
		   font-weight: normal;
           }
        
         /* .control-label::before{
            content: "*";
            position: absolute;
            top: 0;
            left: -5px;
            color: #ce0000;
            line-height:40px;
        	} */

		    button.btn.btn_main.add_sure_btn.right {
               margin-right: 30%;
            }
            button.btn.btn-default {
             margin-left: 40%;
            }
	</style>
<%-- 	    <link rel="stylesheet" href="${basePath}/css/common.css" /> --%>
<%-- 	    <link rel="stylesheet" href="${basePath}/css/bootstrap.min.css" /> --%>
	    <link rel="stylesheet" href="${basePath}/css/assign.css" />
<%-- 	    	<script type="text/javascript" src="${basePath}/js/jquery-3.2.1.min.js"></script> --%>
			<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript" src="${basePath}/js/form.js?version=88"></script>
			<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
<%--     	<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script> --%>
		
		<script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/validation-message-zh.js?v=2"></script>
		
		<portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>
		<portlet:resourceURL id="/hg/checkMeetingTime" var="checkMeetingTimeUrl"/>
		
		<script type="text/javascript" >
            $(function() {	
               var form = ${design};
               var uploadUrls = {
               		file: '${uploadfileUrl}',
               		image: '${uploadimageUrl}',
               		video: '${uploadvideoUrl}'
               }

               var hgDoms = new HgDoms(form, uploadUrls, 'hg-form-container');
               var rules = {};
               console.log(JSON.stringify(form));
               for(var k in form.columns){
               	var column = form.columns[k];
               	if(column.validation || column.required){
               		var r = {};
               		if(column.validation){
               			var v = column.validateParam;
               			if(v){
                    			try{
   	            				v = eval(v);
   	            			}catch(e){
   	            				console.log(e);
   	            			}
               			}else{
               				v = true;
               			}
               			r[column.validation] = v;
               		}
               		if(column.required){
               			r.required = true;
               		}
               		rules[column.id] = r;
               	}
               }
               
               
               
               
               $(".infomessage").css("display","none");

              /*  alert("欢迎来到上传记录页面!");  */
               var meetingPlan=$(".meetingPlanStr").val();
               meetingPlan=meetingPlan.substring(0,meetingPlan.length);
	   	    	var j= JSON.parse(meetingPlan); 
	   	    	console.log(meetingPlan);
    	    	var start = j.start_time;
    	    	/*  alert(meetingPlan);  */
    	    	var end = j.end_time;
    	       $("input[name='secondary']").val(j.sname);
     	       $("input[name='branch']").val(j.bname);
     	       var orgType = j.orgType;
     	       if(orgType == "secondary"){
     	    	$("#hg-form-container").find(".col-sm-6.col-xs-12").eq(1).css("display","none");
     	    	$("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).find(".col-sm-2.col-xs-3.control-label").css("font-size","12px");
     	       }else{
     	    	$("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).css("display","none");
     	       }
    	       $("input[name='type']").val(j.meeting_type);
    	       $("input[name='theme']").val(j.meeting_theme);
    	       $("input[name='starttime']").val(start.substr(0,19));
    	       $("input[name='endtime']").val(end.substr(0,19));
    	       $("input[name='meetingId']").val(j.meeting_id);  
    	       $("input[name='groupId']").val(j.group_id); 
    	       $("input[name='shouldcount']").val(j.shouldCount); 
    	       /*控制附件上传 只传一个附件  */
    	       $("input[type='file']").removeAttr("multiple")
    	       $("input[name='attendance']").attr("readOnly","readOnly");
    	       $("input[name='shouldcount']").attr("readOnly","readOnly");
        });
            
            
            
          /*   function  formsubmit(){
	    	     var totalTime= $("input[name='timeLasts']").val();
	    	     var regu = /^[1-9]\d*$/;		    	     
	    	     if (!regu.test(totalTime)) { 
	    	    	 $.tip("请输入正确的数字！");
	    	    	 return;
	    	     }
	    	     var bool=showConfirmDate();
	    	     if(!bool){return;}
	    	     $.hgConfirm("提示","确定发布吗？");
	    	     $("#hg_confirm").modal("show");
	             $("#hg_confirm .btn_main").click(function(){
	             $("#hg_confirm").modal("hide");
	          });		    	
	     } */
            
            
            
            /* $("input[name='shouldcount']").blur(function(){
             var shouldcount = parseInt($("input[name='shouldcount']").val());
             var regu = /^[1-9]\d*$/;		    	     
    	     if (!regu.test(shouldcount)) { 
    	    	  $.tip("请输入正确的数字！"); 
    	    	 $.hgConfirm("提示","请输入正确的数字！");
	    	     $("#hg_confirm").modal("show");
	             $("#hg_confirm .btn_main").click(function(){
	             $("#hg_confirm").modal("hide");
	                 $('#submitFrom').click();		                
	          });	
    	    	 $("input[name='shouldcount']").focus(); 
    	     }
            }); */
            
            
            $("input[name='actualcount']").blur(function(){
            	var shouldcount1 = parseInt($("input[name='shouldcount']").val());
                var actualcount = parseInt($("input[name='actualcount']").val());
                if(actualcount<0){
                	showConfirm("实到人数不能小于0");
                	return;
                }
                if(!isNaN(actualcount)){
                	if(actualcount <= shouldcount1){
	                	var attendance = Math.round((actualcount/shouldcount1)*100)  + "%";
	                	$("input[name='attendance']").val(attendance);
                	}
                	else{
                		$("input[name='attendance']").empty();
                		showConfirm("请确认实到人数<=应到人数！");
                		$("input[name='actualcount']").focus();
                		
                	}
                }else{
                	$("input[name='attendance']").empty();
                	showConfirm("请输入正确的实到人数！");
                	$("input[name='actualcount']").focus(); 
                }
               });
		    
		    
		    function showConfirm(info){
		    	  $.hgConfirm("提示",info);
	              $("#hg_confirm").modal("show");
	              $("#hg_confirm .btn_main").click(function(){
	              $("#hg_confirm").modal("hide");
	                  return;            	              
	            });	
		     }
		     
		     function formsubmitgraft(){ 
		    	 var seconndaryShow = $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).css("display");
		    	 var branchShow = $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(1).css("display");
		    	 if(branchShow == 'none'){
		             window.location.href="/backlogtwo";
		    	 }else{
	            	window.location.href="/task";
		    	 }
		     }
		 
		     
		     function  formsubmit(){
		    		
		            var startDateStr=$("input[name='starttime']").val();
		            var endDateStr=$("input[name='endtime']").val();
		        	var timestampStartDate=Date.parse(new Date(startDateStr));
		        	var timestampEndDate=Date.parse(new Date(endDateStr));
		        	if(timestampEndDate<timestampStartDate){
		        		showConfirm("请选择正确时间！ 结束时间小于了开始时间？");	
		        		$("input[name='endtime']").focus();
		        		return;
		        		}
		        	
		        	var shouldcount1 = parseInt($("input[name='shouldcount']").val());
		        	var actualcount = parseInt($("input[name='actualcount']").val());
		        	 if(actualcount<=0){
		                	showConfirm("实到人数不能小于0");
		                	return;
		                }
		                if(!isNaN(actualcount)){
		                	if(actualcount > shouldcount1){
		                		showConfirm("请确认实到人数<=应到人数！");
		                		$("input[name='attendance']").empty();
		                		$("input[name='actualcount']").focus();
		                		return;
		                	}
		                }else{
		                	showConfirm("请输入正确的实到人数！");
		                	$("input[name='attendance']").empty();
		                	$("input[name='actualcount']").focus();
		                	return;
		                }
		        	
		                $.hgConfirm("提示","确定上传此记录吗");
			              $("#hg_confirm").modal("show");
			              $("#hg_confirm .btn_main").click(function(){
			              $("#hg_confirm").modal("hide");
			                 $('#submit').click();           	              
			            });	
		                
		                
	               /*  showConfirm("确定上传此记录吗？");
	                 */
		     } 
        
		      
		
		</script>
	</head>
	<body>
		 <input class="informDat" type="hidden" value='${informData }'> 
		<input type="hidden" class="meetingPlanStr" value='${meetingPlanStr }'>
		<div class="content_info111">
        	<div class="content_title">
           		${title }
        	</div>
        	<div class="content_form">
	        	<form class="form-horizontal" role="form" action="${uploadMeetingNotesUrl}" method="post" enctype="multipart/form-data">
	                <div id="hg-form-container" class="form-group">
	                <!-- <textarea rows="3" cols="20" name="content"> </textarea> -->
	                <input id="meetingId" type="hidden" name="meetingId" />
<!-- 	                    <input id="content_id" type="hidden" name="content"/> -->
	                    <input id="submit" type="submit" style="display:none;"/>  
						<input id="hiddenstate" type="hidden" name="state"/>
						<input id="hiddenPublicObject" type="hidden" name="publicObject"/>
<!-- 						<input id="infrom_id" type="hidden" name="infrom_id"/> -->
						<input id="from_uuid" type="hidden" name="from_uuid" value="${from_uuid }"/>
						<span class="infomessage" style="color:red"></span>
						<div class="table_info">
							<a class="template">
								会议记录模板下载
								<input type="hidden" class="imageNeme" value="/templates/MeetingTemplates.doc" name="imageNeme"/>
							</a>
						</div>
						<div class="btn_group col-sm-12">
	          	         	 <button type="button" class="btn btn-default" data-dismiss="modal" onclick="formsubmitgraft()">取消</button>
	                    	 <button type="button" class="btn btn_main add_sure_btn right" style="color:white" onclick="formsubmit()">确定</button>
          	         	</div>
          	         </div>
	            </form>
			</div>
		</div>
		
	</body>
	
	<script type="text/javascript">
		//附件下载
		$(".table_info").on("click",".template",function(){
			var name = $(this).find("input").val();
			if(confirm("模板下载 ")){
				$(this).attr("href","${download}&imageNeme="+name);
			}else{
				$(this).attr("href","#");
			}
			
		});
	</script>
</html>