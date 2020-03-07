<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/hg/ajaxLogin" var="ajaxLogin"/>
<html>
	<head>
<%--		<style>--%>
<%--			#topNav{--%>
<%--				margin-bottom:0;--%>
<%--			}	--%>
<%--			#topNav li:hover{--%>
<%--				border-bottom: 5px solid #CE0000;--%>
<%--          		color: #ce0000;--%>
<%--			}--%>
<%--			#topNav li.page_on{--%>
<%--				border-bottom:5px solid #ce0000;--%>
<%--			}--%>
<%--			#topNav .page_on > a{--%>
<%--				color:#ce0000;--%>
<%--			}--%>
<%--			.gmb_login {--%>
<%--			    padding: 5px 15px;--%>
<%--			    display: inline-block;--%>
<%--			    color: #fff;--%>
<%--			    background: #ce0000;--%>
<%--			    line-height: 14px;--%>
<%--			    margin: 35.5px 0;--%>
<%--			    border-radius: 6px;--%>
<%--			}--%>
<%--		    a.gmb_login:hover, a.gmb_login:focus {--%>
<%--			    color: white;--%>
<%--			    text-decoration: underline;--%>
<%--			}--%>
<%--			--%>
<%-- /**************************** 角色切换新增css ************************************/--%>
<%--        .exit_box{--%>
<%--            height: 72px;--%>
<%--            line-height: 36px;--%>
<%--        }--%>
<%--        .exit_box > div:hover{--%>
<%--            background: #eee;--%>
<%--        }--%>
<%--        .change_role_box{--%>
<%--            position: relative;--%>
<%--        }--%>
<%--        .change_role_box .role_list{--%>
<%--            position: absolute;--%>
<%--            left: -125px;--%>
<%--            top: 0;--%>
<%--            background: #ce0000;--%>
<%--            width: 125px;--%>
<%--            color: #fff;--%>
<%--            margin: 0;--%>
<%--           display: none;--%>
<%--        }--%>
<%--        .change_role_box .role_list li{--%>
<%--            border-bottom: 1px solid rgba(211,76,76,1);--%>
<%--        }--%>
<%--        .change_role_box .role_list li:last-child{--%>
<%--            border-bottom: none;--%>
<%--        }--%>
<%--        .curr_role{--%>
<%--            position: relative;--%>
<%--        }--%>
<%--        .curr_role::after{--%>
<%--            display: inline-block;--%>
<%--            width: 13px;--%>
<%--            height: 9px;--%>
<%--            content: "";--%>
<%--            background: url('../images/curr-role.png');--%>
<%--            position: absolute;--%>
<%--            left: 10px;--%>
<%--            top: 12.5px;--%>
<%--        }--%>
<%--			</style>--%>
	<style>
	p{

	}
	</style>
		<script type="text/javascript">
		 	$(function(){
			 	var roless="${roles}"
				console.log("roles "+roless);
			 	var roles=roless.split(/[，,]/);
			 	if(roles.length>1){
				 	for (i=0;i<roles.length;i++ ){
					 	var str=' <li class="login">'+roles[i]+'</li>'+
						 	'<input type="hidden" value="'+roles[i]+'">'
						if(roles[i]!="${role }"){
							$(".role_list").append(str);
						}
					}
				 }

			$("#role-list").on("click","a",function(){
				var ro=	$(this).text().trim();
				var index = ro.lastIndexOf('管理员');
				if (index !== -1){
					ro = ro.substring(0, index);
				}
				var url="${ajaxLogin }";
					$.ajax({
						url:url,
						data:{"<portlet:namespace/>changType":"changeRole",
								  "<portlet:namespace/>role":ro=='学校'?'组织部':ro } ,
						 /*   type:"post", */
							dataType:"text",
							async:false,
							success:function(result){
							if(result.substring(0,1)==="3"){
								 window.location.href=result.substring(1);
							}
						}
					});
				});
			 });
		</script>
	</head>
	<body>
	<div style="position: relative; width: 100%; height: 7.4vh; position: relative;">
	<c:choose>
		<c:when test="${role == '组织部' or role == '二级党组织'}">
			<img class="left_logo" src="/images/logo.png" alt="" title="logo" style="left: 0;cursor: pointer;" onclick="window.location.href='/screen'"/>
		</c:when>
		<c:otherwise>
			<img class="left_logo" src="/images/logo.png" alt="" title="logo" style="left: 0;"/>
		</c:otherwise>
	</c:choose>
	<div class="user_info_group">
	<p class="avatar_info">
	<img src="/images/avatar.png" alt="" title="头像"/>
	<span>
	${name}
	<ul class="layui-nav user_nav_container">
	<li class="layui-nav-item">
	<c:choose>
		<c:when test="${role == '普通党员'}">
			<a href="javascript:;">[${role}]</a>
		</c:when>
		<c:otherwise>
			<a href="javascript:;">[${role=='组织部'?'学校':role}管理员]</a>
		</c:otherwise>
	</c:choose>
	<dl class="layui-nav-child" id="role-list"> <!-- 二级菜单 -->
	<c:forEach items="${roles}" var="r">
		<c:if test="${r != role}">
			<c:choose>
				<c:when test="${r == '普通党员'}">
					<dd><a href="javascript:;" value="${r}">${r}</a> </dd>
				</c:when>
				<c:otherwise>
					<dd><a href="javascript:;" value="${r}">${r=='组织部'?'学校':r}管理员</a></dd>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:forEach>
	</>
	</li>
	</ul>

	</span>
	</p>
	<p>
	<img src="/images/change-pwd.png" alt="" title="修改密码"/>
	<span onclick="window.location.href='/reset_password'">修改密码</span>
	</p>
	<p>
	<img src="/images/exit.png" alt="" title="退出系统"/>
	<span onclick="exit();">退出系统</span>
	</p>
	</div>
	</div>

		<!-- </p> -->
		<script type="text/javascript">
			function exit(){
				var url="${ajaxLogin }";
				var exit="exit";
				$.ajax({
					url:url,
					data:{"<portlet:namespace/>exit":exit},
       	           	type:"post",
            	   	dataType:"text",
            	   	async:false,
            	   	success:function(result){
          		 		if(result==="4"){
							// var domain = /^https?:\/\/.*?\//.exec(window.location.href);
							// window.location.href = 'https://uaaap.swu.edu.cn/cas/logout?service=' + encodeURI(domain[0]);
							window.location.href = '/home';
          		 		}
           	  		}
				})
			}
		</script>
		<!-- </div> -->
	</body>
	<script type="text/javascript">
	</script>
</html>