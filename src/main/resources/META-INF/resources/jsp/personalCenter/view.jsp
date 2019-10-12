<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>个人中心</title>
    <style type="text/css">
    	.personal_center .col-sm-6.col-xs-12{
    		margin-bottom:20px;
    	}
    	.personal_center .form_btn{
    		margin:50px auto;
    	}
    	@media (min-width: 768px){
    			.container-fluid{
    				background:#edf1f2;
    			}
        		.main_content .min_width_1200{
        			background:#fff;
        		}
     }
     
     .link_container.full_screen {
         background-color: #edf1f2;
          }
    </style>
    <script>
	    $(function(){
	    	$(".main_content.full_screen .min_width_1200").height($(window).height()-285);
	    })
    </script>
</head>
<body>
			<div class="main_content full_screen">
				<div class="min_width_1200">
					<form class="form-horizontal personal_center" role="form" style="padding-top: 50px;">
		                <div class="form-group">
		                	<%-- <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">党支部</span>
		                        <div class="col-sm-10 col-xs-9">
		                            <input type="text" class="form-control" value="${ userInfo.org_name }" readonly="readonly"/>
		                        </div>
		                    </div> --%>
		                    
		                	<div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">身份证号</span>
		                        <div class="col-sm-10 col-xs-9">
		                            <input type="text" class="form-control" id="user_id" value="${ userInfo.user_id }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">姓名</span>
		                        <div class="col-sm-10 col-xs-9">
		                            <input type="text" class="form-control" value="${ userInfo.member_name }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">性别</span>
		                        <div class="col-sm-10 col-xs-9">
		                            <input type="text" class="form-control" value="${ userInfo.member_sex }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">年龄</span>
		                        <div class="col-sm-10 col-xs-9">
		                             <input type="text" class="form-control" value="${ userInfo.member_age }" readonly="readonly"/>
		                        </div>
		                    </div>
		                     <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">出生日期</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.member_birthday }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">联系方式</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control phone_number" value="${ userInfo.member_phone_number }" id="phone_number" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">邮箱</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control mailbox" value="${ userInfo.member_mailbox }" id="member_mailbox" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">登录密码</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="password" class="form-control password" value="${ userInfo.user_password }" id="user_password" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">入党时间</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.member_join_date }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">角色</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.user_role }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">民族</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.user_ethnicity }" readonly="readonly"/>
		                        </div>
		                    </div>
		                     <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">学历</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.member_degree }" readonly="readonly"/>
		                        </div>
		                    </div>
		                    <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">党内职务</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.member_party_position }" readonly="readonly"/>
		                        </div>
		                    </div>
		                     <div class="col-sm-6 col-xs-12">
		                        <span class="col-sm-2 col-xs-3 control-label">党费标准（元/月）</span>
		                        <div class="col-sm-10 col-xs-9">
		                              <input type="text" class="form-control" value="${ userInfo.member_major_title }" readonly="readonly"/>
		                        </div>
		                    </div>
		                   
		                    <div class="col-sm-12 col-xs-12 form_btn">
		                    	<c:if test="${ userInfo.user_id !=null }">
			                        <div class="col-sm-4 col-sm-offset-4" style="text-align: center;">
			                            <a style="position:relative;cursor:pointer;background-color: #ce0000;display: inline-block;patext-align: center;padding:5px 20px;" id="update_a" onclick="updatephone()">
			                            <font color="#fff" class="btn btn_main">修改</font></a>
			                        </div>
		                        </c:if>
		                        <div class="col-sm-3 col-sm-offset-3" id="reset_a" style="display: none;text-align: center;">
		                            <input onclick="javascript:window.history.back();" type="reset" class="btn btn-default" value="取消"/>
		                        </div>
		                        <div class="col-sm-3" id="save_a" style="display: none;text-align: center;">
		                            <a class="btn btn_main" onclick="savephone();">
		                            <font color="#fff" style="padding: 15px;height: 10px;">保存</font></a>
		                        </div>
		                    </div>
		                </div>
		            </form>
				</div>
			</div>
            
<portlet:actionURL name="/personalCenter/updatePhone" var="updatePhone" />
<form action="${ updatePhone }" method="post" id="updateform">
	<input type="hidden" name="user_id" id="userId"/>
	<input type="hidden" name="phone_number" id="phoneNumber"/>
	<input type="hidden" name="member_mailbox" id="memberMailbox"/>
	<input type="hidden" name="user_password" id="userPassword"/>
</form>
<script type="text/javascript" src="${basePath}/js/hgGlobal.js"></script>
<script type="text/javascript">
	function updatephone(){
		$('#phone_number').removeAttr("readonly");
		$('#member_mailbox').removeAttr("readonly");
		$('#user_password').removeAttr("readonly");
		document.getElementById("update_a").style.display="none";
		document.getElementById("reset_a").style.display="";
		document.getElementById("save_a").style.display="";
	}
	
	function savephone(){
		var phone_number = $(".phone_number").val();
		var mailbox = $(".mailbox").val();
		var password = $(".password").val();
		
		var Phone = global_isPoneAvailable(phone_number);
		var mail = global_isEmailAvailable(mailbox);
		var passwd = global_isPasswdAvailable(password);
		if(!Phone){
			alert("请输入正确电话！");
			return false;
		}else if(!mail){
			alert("请填正确邮箱！");
			return false;
		}else if(!passwd){
			alert("请填6-20密码!");
			return false;
		}else{
			var user_id = $('#user_id').val();
			var phone_number = $('#phone_number').val();
			var member_mailbox = $('#member_mailbox').val();
			var user_password = $('#user_password').val();
			$('#userId').val(user_id);
			$('#phoneNumber').val(phone_number);
			$('#memberMailbox').val(member_mailbox);
			$('#userPassword').val(user_password);
			$('#updateform').submit();
		}
		
	}
	
	
</script>

</body>
</html>