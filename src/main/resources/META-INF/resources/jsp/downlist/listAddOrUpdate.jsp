<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="${basePath }/js/My97DatePicker/WdatePicker.js"></script>
<%-- <%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%> --%>

<script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	    <title>新增/编辑</title>
	    
	    <style type="text/css">
	    	.input{
	    		border:1px solid #aaa;
	    		width:500px;
	    		margin: 10px;
	    	}
	    	.remark{
	    		height: 100px;
    			width: 400px;
	    	}
	    	
	    	.col-sm-6.col-xs-12 {
            margin-bottom: 15px;
          }
          input.btn.btn-default.td_assign_btn.main_color_btn {
                margin-left: 40%;
                margin-right: 10%;
         }
         form#form {
                margin-top: 20px;
         }
	    </style>
	</head>
	
	<body>
		<div class="content_title" style="font-size:16px;">新增/编辑</div>
		<portlet:actionURL name="/asset/addOrUpdateDeviceAction" var="addOrUpdateDevice"/>
		<div class="content_form">
		<form class="form-horizontal" action="${addOrUpdateDevice }" id="form" method="post">
			
				<div class="col-sm-6 col-xs-12" hidden="hidden">
					<span class="col-sm-3 col-xs-3 control-label" >编号</span>
					<input class="input" type="text" id="id" name="id"  value="${asset_Attributes.id }"  readonly="readonly"/>
				</div>	
				
				<div class="col-sm-6 col-xs-12">
					<span class="col-sm-3 col-xs-3 control-label">&nbsp;下拉值ID</span>
						<div class="col-sm-9 col-xs-9">
							<input size="16" type="text" class="form_datetime form-control" id="resources_key" name="resources_key"  value="${asset_Attributes.resources_key }" />
						</div>			
				</div>
				<div class="col-sm-6 col-xs-12">
					<span class="col-sm-3 col-xs-3 control-label">&nbsp;下拉值</span>
						<div class="col-sm-9 col-xs-9">
							<input size="16" type="text" class="form_datetime form-control" id="resources_value" name="resources_value" value="${asset_Attributes.resources_value }" />
						</div>
				</div>
				<div class="col-sm-6 col-xs-12">
					<span class="col-sm-3 col-xs-3 control-label">&nbsp;下拉类型</span>
						<div class="col-sm-9 col-xs-9">
							<%-- <input size="16" type="text" class="form_datetime form-control" id="resources_type" name="resources_type" value="${asset_Attributes.resources_type }"/> --%>
						<input type="hidden" class="meeting_type_value"  value="${asset_Attributes.resources_type }">
						<select class="form-control meeting_type" name="resources_type"  id="resources_type" >
								<option value="meetingType">会议类型</option>
								<option value="reason">驳回原因</option>
								<option value="sendpath">发送途径</option>
								<option value="positior">党内职务</option>
								<option value="room">学生宿舍园区</option>
						</select>
						</div>
				</div>
				<div class="col-sm-6 col-xs-12">
					<span class="col-sm-3 col-xs-3 control-label">&nbsp;备注</span>
						<div class="col-sm-9 col-xs-9">
							<input size="16" type="text" class="form_datetime form-control" id="remark" name="remark"  value="${asset_Attributes.remark }"/>
						</div>			
				</div>
			</form>
			<input class="btn btn-default td_assign_btn main_color_btn" data-toggle="modal" type="submit" value="保存" onclick="send();" />
			<input class="btn btn-default td_assign_btn" data-toggle="modal" type="reset" value="取消"  onclick="back();"/>
		
		</div>
		
		<!-- ajx提交 --> 
		<portlet:resourceURL id="/ListUpdateAjxCommand" var="ListUpdateAjx" />
	<script type="text/javascript">
	
	
	$(function(){
        var type=$(".meeting_type_value").val();
		$(".meeting_type option").each(function(){
			if($(this).attr("value") == type){
			   $(this).attr("selected","selected");
			}
		});
	});
		//提交
		function send(){
			//输入框格式判断
			if($("#resources_key").val()==""){
				alert("下拉值ID不能为空");
				return false;
			}else if($("#resources_value").val()==""){
				alert("下拉值不能为空");
				return false;
			}else if($("#resources_type").val()==""){
				alert("下拉类型不能为空");
				return false;
			}else{
				var id = $("#id").val();
				var key = $("#resources_key").val();
				var val = $("#resources_value").val();
				var type = $("#resources_type").val();
				var remark = $("#remark").val();
				$.ajax({
					url:"${ListUpdateAjx }",
					data:{"id":id,"key":key,"val":val,"type":type,"remark":remark},
					success:function(ac){
						if(ac != ""){
							alert(ac);
						}else{
							window.location.href="/dropdown";
						}
					},
					error:function(){
						alert("error");
					}
				})
				/* $("#form").submit(); */
			}
		}
		
		//返回
		function back(){
			  window.history.back(); 	
		}
		
	</script>
	</body>
</html>

