<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/hg/ajaxLogin" var="ajaxLogin"/>
<html>
	<head>
		<style>
			#topNav{
				margin-bottom:0;
			}	
			#topNav li:hover{
				border-bottom: 5px solid #CE0000;
          		color: #ce0000;
			}
			#topNav li.page_on{
				border-bottom:5px solid #ce0000;
			}
			#topNav .page_on > a{
				color:#ce0000;
			}
			.gmb_login {
			    padding: 5px 15px;
			    display: inline-block;
			    color: #fff;
			    background: #ce0000;
			    line-height: 14px;
			    margin: 35.5px 0;
			    border-radius: 6px;
			}
		    a.gmb_login:hover, a.gmb_login:focus {
			    color: white;
			    text-decoration: underline;
			}
			
 /**************************** 角色切换新增css ************************************/
        .exit_box{
            height: 72px;
            line-height: 36px;
        }
        .exit_box > div:hover{
            background: #eee;
        }
        .change_role_box{
            position: relative;
        }
        .change_role_box .role_list{
            position: absolute;
            left: -125px;
            top: 0;
            background: #ce0000;
            width: 125px;
            color: #fff;
            margin: 0;
           display: none;
        }
        .change_role_box .role_list li{
            border-bottom: 1px solid rgba(211,76,76,1);
        }
        .change_role_box .role_list li:last-child{
            border-bottom: none;
        }
        .curr_role{
            position: relative;
        }
        .curr_role::after{
            display: inline-block;
            width: 13px;
            height: 9px;
            content: "";
            background: url('../images/curr-role.png');
            position: absolute;
            left: 10px;
            top: 12.5px;
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
				 
				 $(".role_list").on("click",".login",function(){
					   var ro=	$(this).next().val();
			           var url="${ajaxLogin }";
			               $.ajax({url:url,
			        	           data:{"<portlet:namespace/>changType":"changeRole",
			        		              "<portlet:namespace/>role":ro } ,        	      
			        	         /*   type:"post", */
				            	   dataType:"text",
				            	   async:false,
				            	   success:function(result){
				            		  if(result.substring(0,1)=="3"){	            				          
			                             window.location.href=result.substring(1);
				            		 }
				            	  }
			              });
					}); 
				 
			 });
			
			 
			 
			</script>
	</head>
	<body>
	<div class="nav_bar full_screen hidden-xs">
            <div class="min_width_1200">
                <div class="logo_container">
                 <a href="/home"><img src="/images/index_logo.png" /></a>               
                 </div>
                 <div class="role_box">
                 	
                    <div class="role_info">
                     <c:if test="${not empty role}">
                        <img id="topNav_logo" src="/images/role-icon.png" />
                        <p class="role_name">${role } ${name }</p>
                     </c:if>
                    </div>
                    <c:if test="${not empty role}">
                    <div class="exit_box" style="z-index: 1000;">
                        <div class="change_role_box">
                            <div class="change_role">
                                <img src="/images/role-change.png" />
                                                                                     角色切换
                            </div>
                            <ul class="role_list">
                               <!--  <li class="curr_role">二级党组织</li>
                                <li class="login">党支部</li>
                                <li class="login">二级党组织</li>
                                <li class="login">党支部</li> -->
                            </ul>
                        </div>
                        <div onclick="exit();">
                            <img src="/images/exit.png" />
                                                                                退出
                        </div>
                    </div>
                     </c:if>
                </div>
                
                
                <div class="change_role"></div>
                	<ul id="topNav" class="nav_list">
					<c:forEach items="${lists }" var="lists" varStatus="status">
						<li>
							<a href="${lists.navigation_url}" id="${lists.remark}">
							  <c:choose>
							   <c:when test="${lists.navigation_name eq '网站首页'}">
							    <span>首页</span>
							   </c:when>
							   <c:otherwise>
							     <span>${lists.navigation_name}</span>
							   </c:otherwise>
							  </c:choose>
							</a>
							<input type="hidden" class="list_index" name="list_index" value="${status.index}"/>
						</li>
					</c:forEach>
					<input class="choosen_index" type="hidden" value=""/>
		            </ul>
                
            </div>
        </div>
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
						$(".role_info").prepend('<a class="gmb_login" href="${casServer}">登录</a>');
					}else{
						var index1 = url .indexOf("\?");
						url = url.substring(0, index1);
						console.log("url2="+url);
						if("detail" == url || "home" == url || "newscenter" == url || "userroles" == url){
							$(".role_info").prepend('<a class="gmb_login" href="${casServer}">登录</a>');
						}else{
							window.location.href="${casServer}";
						}
					}
				</script>
			</c:when>
			<c:otherwise>
			<script>
			/* $(".role_info").prepend( '<img src="/images/role-icon.png" />'+
                                     '<p class="role_name">${role } ${name }</p>'); */
			</script>
				<%-- <span class="right_border">欢迎 ${role } ${name }</span>
            	<span class="login_out" onclick="exit()">退出</span> --%>
			</c:otherwise>
		</c:choose>
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
          		 		if(result=="4"){
							var domain = /^https?:\/\/.*?\//.exec(window.location.href);
							window.location.href = 'https://uaaap.swu.edu.cn/cas/logout?service=' + encodeURI(domain[0]);
          		 		}
           	  		}
				})
			}
		</script>
		<!-- </div> -->
	</body>
	<script type="text/javascript">
		var organArr = [];
		var navArr = [];
		var _pathName = location.pathname;
		
		$(function(){
			//组织生活左侧导航的href集合
			$(".party_organization_list a").each(function(){
				organArr.push($(this).attr("href").toLocaleLowerCase());
			});
			//导航的href集合
			$("#topNav >li > a").each(function(){
				navArr.push($(this).attr("href").toLocaleLowerCase());
			});
			console.log(organArr)
			console.log(navArr)
			for(var i = 0;i<navArr.length;i++){
				if(_pathName == navArr[i] || _pathName.indexOf(navArr[i]) > 0){
					$("#topNav >li").eq(i).addClass("page_on");
				}
			};
			
			for(var i = 0;i<organArr.length;i++){
				if(_pathName == organArr[i] || _pathName.indexOf(organArr[i]) > 0){
					$("#topNav >li").eq(1).addClass("page_on");
				}
			}
		})
		
	   //点击角色  弹出退出框
        $(".role_info").click(function(){
            $(".exit_box").css("display","block");
        });

        $('.exit_box').click(function(){
            //退出登录
            $(this).css("display","none");
        });
        function stopPropagation(e) { 
            if (e.stopPropagation) 
            e.stopPropagation(); 
            else 
            e.cancelBubble = true; 
        };
        
        //点击页面其他地方关闭 退出框
          $(document).bind('click',function(){ 
              $('.exit_box').css('display','none'); 
          }); 

          $('.role_info').bind('click',function(e){ 
              stopPropagation(e); 
          });
          $('.exit_box').bind('click',function(e){ 
              stopPropagation(e); 
          });  
          //角色切换 hover
          $(".change_role_box").hover(function(){
              $(this).find(".role_list").stop().css("display","block");
          },function(){
              $(this).find(".role_list").stop().css("display","none");
          });

          //点击切换角色
          $(".role_list").on("click","li",function(){
              $(".role_list li").removeClass("curr_role");
              $(this).addClass("curr_role");
          }) 
          
          /*滚动时 角色切换时div消失*/
          $(window).scroll(function() {
	       if($(".exit_box").css("display") == "block"){
              $(".exit_box").css("display","none");
            }
          });

	</script>
</html>