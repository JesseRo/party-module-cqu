<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head>
		<style>
			.user_login{
				color:#000;
			}
			.gmb_login{
				padding: 5px 15px;
			    display: inline-block;
			    color: #fff;
			    background: #ce0000;
			    line-height: 14px;
			}
			a.gmb_login:hover, a.gmb_login:focus {
                color: white;
            }
            p.top_bar_right {
              height: 23px;
            }
     
           p.top_bar_left{
            line-height: 38px;
           }
           p.top_bar_right{
            margin: 6px 0!important;
           }
           .portlet-content, .portlet-minimized .portlet-content-container {
               padding: 0px 0px 0px;
          }
		</style>
	</head>
	<body>
		<portlet:resourceURL id="/hg/ajaxLogin" var="ajaxLogin"/>
		<div style="height: 38px;">
		<p class="top_bar_left">欢迎访问党务工作信息平台</p>
	    <p class="top_bar_right">
		<c:choose>
			<c:when test="${empty name }">
				<!-- <a class="gmb_login" href="/user-login">登录</a> -->
				<script>
					var url = window.location.href;
					var index = url .lastIndexOf("\/");  
					url  = url .substring(index + 1, url.length);
					if("" == url || null == url){
						window.location.href="/home";
					}else if("home" == url || "newscenter" == url || "user-login" == url || "userroles" == url){
						$(".top_bar_right").append('<a class="gmb_login" href="${casServer}">登录</a>');
					}else{
						var index1 = url .indexOf("\?");
						url = url.substring(0, index1);
						console.log("url2="+url);
						if("detail" == url || "home" == url || "newscenter" == url || "userroles" == url){
							$(".top_bar_right").append('<a class="gmb_login" href="${casServer}">登录</a>');
						}else{
							window.location.href="${casServer}";
						}
					}
				</script>
			</c:when>
			<c:otherwise>
				<span class="right_border">欢迎 ${role } ${name }</span>
            	<span class="login_out" onclick="exit()">退出</span>
			</c:otherwise>
		</c:choose>
		</p>
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
          		 		if(result=="4"){
							var domain = /^https?:\/\/.*?\//.exec(window.location.href);
							window.location.href = 'https://uaaap.swu.edu.cn/cas/logout?service=' + encodeURI(domain[0]);
          		 		}
           	  		}
				})
			}
		</script>
		</div>
	</body>
</html>