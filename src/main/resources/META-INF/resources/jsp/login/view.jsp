<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>登录</title>
   <%--  <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/login.css" />
   <%--  <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
   <style type="text/css">
   .col-sm-4 {
    margin-top: 10px;
    }
    img.form-groupimg {
    margin-right: 6px;
    }
   </style>
</head>

<body>
     <div class="mian_content col-xs-12">
        <img class="login_icon hidden-xs" src="/images/login_icon.png" />
        <img class="mian_bg hidden-xs" src="/images/login_bg.jpg" />
        <img class="mian_bg visible-xs" src="/images/mobile_bg.png" />
        <div class="login_container">
            <div class="login_container_bg visible-xs"></div>
            <div class="type_change">
                <%-- <a href="${ urlAddress }"><img title="切换至统一身份认证登录" class="hidden-xs" src="/images/type_change_icon.png" /></a> --%>
            </div>
            <!-- <img class="mobile_icon visible-xs" src="/images/mobile_logo.png"> -->
            <p class="login_title">西南大学党务工作信息平台</p>
            <form class="form-horizontal" role="form">
                <div class="form-group login_input">
                    <div class="col-sm-12">
                        <img src="/images/account_icon.png" />
                        <input type="text" class="form-control account_id" id="gmb_form-control" placeholder="身份证账号">
                    </div>
                </div>
                <div class="form-group login_input">
                    <div class="col-sm-12">
                        <img src="/images/pwd_icon.png" />
                        <input type="password" class="form-control" id="gmb_password" placeholder="登录密码">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12 select_role">
                     
                    </div>
                </div>
                <div class="form-group login_btn">
                    <div class="col-sm-12">
                        <button type="button" onclick="ajaxLogin();" class="btn btn-default form-control">登录</button>
                    </div>
                </div>
                <div class="form-group forget_pwd right">
                    <div class="col-sm-12">
                      	<a href="javascript:;">忘记密码</a>
                    </div>
                </div>
                <%-- <div class="form-group login_btn">
                    <div class="col-sm-12">
                        <a href="${ urlAddress }">切换至统一身份认证登录</a>
                    </div>
                </div> --%>
            </form>
        </div>
    </div>
    <portlet:resourceURL id="/hg/forgetPasswd" var="forgetPasswd"/>
    <div class="modal fade" id="input" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
        <div class="modal-dialog" style="margin-left: auto; margin-top: 200px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                    <h4 class="modal-title" id="inputLabel">找回密码</h4>
                </div>
                <div class="modal-body content_form">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-sm-12 col-xs-12" style="margin: 5px;">
                                <span class="col-sm-3 col-xs-4 control-label">登录账号</span>
                                <div class="col-sm-9 col-xs-8">
                                    <input type="text" class="form-control" id="user_id"/>
                                </div>
                            </div>
                            <div class="col-sm-12 col-xs-12" style="margin: 5px;">
                                <span class="col-sm-3 col-xs-4 control-label">姓名</span>
                                <div class="col-sm-9 col-xs-8">
                                    <input type="text" class="form-control" id="user_name"/>
                                </div>
                            </div>
                            <div class="col-sm-12 col-xs-12" style="margin: 5px;">
                                <div class="col-xs-6 col-xs-offset-3" id="messg" style="color: red;text-align: center;"></div>
                            </div>
                            <div class="col-sm-12 col-xs-12" style="margin: 5px;">
                                <div class="col-xs-4 col-xs-offset-4" style="text-align: center;">
                                    <a class="form-control" style="cursor:pointer;background-color: #ce0000;" onclick="resetPasswd();"><font color="#fff">申请重置密码</font></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
    <portlet:resourceURL id="/hg/ajaxLogin" var="ajaxLogin"/>
    <portlet:resourceURL id="/hg/getRole" var="getRole"/>
    <script>
    	//忘记密码弹窗显示
    	$(".forget_pwd").on("click","a",function(){
    		$("#input").modal("show");
    	})
        $(function() {
            var Height = $(window).height();
            var newHeight = (Height - 150) + "px";
            $(".mian_content").css("height", newHeight); 
      
        });
        
        function resetPasswd(){
        	var user_id = $("#user_id").val().replace(/\s/g, "");
        	var user_name = $("#user_name").val().replace(/\s/g, "");
        	if(user_id=="" || user_id ==null){
        		document.getElementById('messg').innerHTML = "请输入账号!";
        		return ;
        	}
        	if(user_name=="" || user_name ==null){
        		document.getElementById('messg').innerHTML = "请输入姓名!";
        		return ;
        	}
    		var url = "${ forgetPasswd }";
    		/* var org_name=$("#org_name").val();
    		var org_type=$("#org_type").val(); */
    		var data = {"user_id":user_id,"user_name":user_name};
    		$.ajax({
      			 url:url,
      		     data:data, 
      		     dataType:"json", 
      		     success:function(result){
      		    	document.getElementById('messg').innerHTML = result.status; 
	          	 }
	     	})
    	}
        
        $(".account_id").on("change",function(){
       	 var core=$("#gmb_form-control").val();
       	 var url="${getRole}";
       	 var data={"<portlet:namespace/>core":core};
       	 var input;
       	 $.ajax({
       			 url:url,
       		     data:data, 
       		     dataType:"text", 
       		     success:function(result){
		        		  if(result.length>1){
		       		    	var roles=result.split(/[，,]/); 
		       		    	$(".select_role").empty();
		       		    	$(".select_role").append("<p>检测到该账号下存在如下角色，请选择登录</p>"); 
		       		        for (i=0;i<roles.length;i++ ){
			       		    	 var str='<div class="col-sm-4">'+
	                                     '<span class="span_box"><input type="hidden" value="'+roles[i]+'"/>'+
	                                     '<img class="form-groupimg" src="/images/not_check_icon.png" />'+roles[i]+''+
	                                     '</span></div>'; 
			       		    	input=$(str);
			       		    	$(".select_role").append(input); 
			       		     } 
				         }else{
				   		  $(".select_role").empty();
						  input=$("<span>账号错误</span>");
						  $(".select_role").append(input); 
				          }
		        		  change_icon();
	              }
	           });
             });
      
       function ajaxLogin(){
	       var userName= $("#gmb_form-control").val();
           var password=$("#gmb_password").val();
           var role=$("img[src='/images/checked_icon.png']").prev().val();
           if(!role){
        	   showConfirmLogin("请选择角色进行登录！");}
           var url="${ajaxLogin }";
           $.ajax({url:url,
        	       data:{"<portlet:namespace/>userName":userName,
        		         "<portlet:namespace/>password":password,
        		         "<portlet:namespace/>role":role } ,        	      
        	           type:"post",
	            	   dataType:"text",
	            	   async:false,
	            	   success:function(result){
	            		 if(result=="1"){
	            			 showConfirmLogin("用户不存在");
	            			 }
	            		 else if(result=="2"){
	            			 showConfirmLogin("密码错误");
	            			 }
	            		 else if(result.substring(0,1)=="3"){	            				          
                             window.location.href=result.substring(1);
	            		 }
	            	  }
              });
           }
        //提示框
        function showConfirmLogin(str){
        	    $.hgConfirm("提示",str);
	            $("#hg_confirm").modal("show");
	            $("#hg_confirm .btn_main").click(function(){
	            $("#hg_confirm").modal("hide");	 
	            return;
	            });

        }
        function change_icon(){
        	$(".span_box").on("click",function(){
            	$(".form-groupimg").attr('src','/images/not_check_icon.png');
            	$(this).children(".form-groupimg").attr('src','/images/checked_icon.png');
                 
            });
        }
    </script>
</body>

</html>
