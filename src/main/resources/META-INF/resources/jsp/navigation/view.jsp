<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
<style type="text/css">
.message_modal .modal-content,
.message_modal .modal-dialog {
  width: 851px;
}
.message_modal .content_title {
  font-size: 20px;
  color: #333;
  text-align: center;
  padding: 38px 0 58px 0;
}
.message_modal .modal-body {
  padding: 0 34px;
}
.message_modal .message_box {
  padding: 0 50px;
  height: 220px;
}
.message_modal .message_box .message_item {
  width: 195px;
  height: 220px;
  background: #f5f5f5;
  border: 1px solid #bbb;
  border-radius: 6px;
  float: left;
  margin-right: 48px;
}
.message_modal .message_box .message_item:last-child {
  margin-right: 0;
}
.message_modal .message_box .message_item .img_box {
  width: 73px;
  height: 82px;
  margin: 43px 61px 26px 61px;
}
.message_modal .message_box .message_item p {
  font-size: 16px;
  color: #333;
  text-align: center;
}
.message_modal .message_box .message_item p .bold_num {
  font-size: 28px;
  font-weight: bold;
}
.message_modal .message_box .message_item .item_name {
  line-height: 30px;
}
.message_modal .btn_box {
  text-align: center;
}
.message_modal .btn_box a {
  margin: 70px auto 50px auto;
  display: inline-block;
  font-size: 20px;
  color: #333;
  padding: 13px 75px;
  box-sizing: border-box;
  border: 1px solid #666;
  border-radius: 23px;
}

@media (min-width: 768px){
.main_content .min_width_1200 .nav_list li a {
    text-decoration: none;
    font-size: 16px;
    color: #333;
    line-height: 28px;
}


        /*欢迎弹窗样式*/
        #greetModal .modal-body{
            padding: 0;
            background-color: transparent;
        }
        #greetModal .greet_box{
            position: relative;
        }
        #greetModal .modal-content{
            background: transparent;
            box-shadow: none;
            border: none;
        }
        #greetModal .greet_bg{
            width: 100%;
        }
        #greetModal .greet_content{
            position: absolute;
            bottom: 64px;
            width: 100%;
            text-align:center;
        }
        #greetModal .greet_content button{
            width: 312px;
            height: 56px;
            margin-top: 84px;
            border: none;
            background: ffff;
            background-color: #fff;
            font-size: 22px;
            color: #000;
            letter-spacing: 1px;
            border-radius: 10px;
        }
        .greet_text{
            font-size: 24px;
            background:url('/images/greet_text_bg.png');
            -webkit-text-fill-color:transparent;
            -webkit-background-clip:text;
            -webkit-animation-name:masked-animation;
            -webkit-animation-duration:10s;
            -webkit-animation-iteration-count:infinite;
            -webkit-animation-timing-function:linear;
            color:#fff;
        }
        @-webkit-keyframes masked-animation {
            0% {background-position:left bottom;}
            100% {background-position:right bottom;}
        }
		   /*欢迎弹窗样式*/
   

</style>
<script type="text/javascript">
		$(function(){
			var firstLogin = "${firstLogin}";
			console.log("firstLogin "+firstLogin);
			if("false" === firstLogin){
				 $("#greetModal").modal("show");
			}
			var messages='';
			$(".messageCount_img").each(function(){
	            var name=$(this).prev().html();
	            var count=$(this).next().val();
	                messages +=name+":"+count+"条  "; 
	                var img_="/images/todo-icon.png";
	                if("发文审批"==name){
	                	img_='/images/todo-approval.png';
	                }else if("审批计划"==name){
	                	img_='/images/todo-deploy.png';
	                }
	                var href=$(this).parent().attr("href");
	                var template= '<a href="'+href+'"> '+
	                                '<div class="message_item"> '+
				                      '<div class="img_box"> '+
				                      '<img src="'+img_+'"/> '+
				                      '</div> '+
				                      '<p> '+
				                      '<span class="bold_num">'+count+'</span>条 '+
				                      '</p> '+
				                      '<p class="item_name">'+name+'</p> '+
				                     '</div>'+
			                      '</a>';
			                      $(".message_box").append(template);
			                      var href_=$(this).parent().attr("href");
			                      $(".todo_look").attr("href",href_);
	             });
			   var n="${login_Count}";
			   if(messages&&n == 1){
				  $("#message_modal").modal("show");
			   }
			    var itemLen = $(".message_box").find(".message_item").length;
		        console.log(itemLen)
		        if(itemLen == 1){
		            $("#message_modal .modal-dialog").css("width","580px");
		            $("#message_modal .modal-content").css("width","580px");
		            $(".message_item").css("margin","0 108.5px");
		        }else if(itemLen == 2){
		            $("#message_modal .modal-dialog").css("width","580px");
		            $("#message_modal .modal-content").css("width","580px");
		            $(".message_item").css("marginRight",0);
		            $(".message_item").eq(1).css("float","right");
		        }
		});
		</script>
	</head>
	<body>
		<ul class="party_organization_list overflow_hidden">
			<c:forEach items="${lists }" var="navigations">
				<li class=""> 
					<c:choose>
						<c:when test="${not empty groups[navigations.navigation_id] }">
							<div class="first_menu">
				                <img class="front_icon" src="${navigations.navigation_icon }" />
				                <a href="${navigations.navigation_url }">
				                	<span>${navigations.navigation_name}</span>	                
				                </a>
				                <img class="right first_drop dropdown_up" src="/images/second_menu_up.png" />
				            </div>
				            <ul class="second_menu">
				            	<c:forEach items="${groups[navigations.navigation_id]}" var="secondaryPage">
			            			<li>
										<a href="${secondaryPage.navigation_url}"><span>${secondaryPage.navigation_name}</span></a>
									</li>
				            	</c:forEach>
				            </ul>
						</c:when>
						<c:otherwise>
							<a href="${navigations.navigation_url}">
	                            <img class="front_icon" src="${navigations.navigation_icon }"/>
	                            <img class="front_icon hided" src="${navigations.navigation_icon_in }"/>
<%-- 	                            <input type="hidden" id="icon_no" name="icon_no" value="${navigations.navigation_icon_in }"/> --%>
	                            <span>${navigations.navigation_name}</span>
	                            	<c:if test="${navigations.navigation_message eq 't'}">
				                		<img class="messageCount_img" src="/images/navigationmessage.png" />
				                		<input type="hidden" value="${navigations.messageCount }">
				                	</c:if>
	                        </a>
						</c:otherwise>
					</c:choose>
		            
				</li>
			</c:forEach>
		</ul>
		
		<!-- 消息提示弹窗 -->
    <div class="modal fade message_modal" id="message_modal" tabindex="-1" role="dialog" aria-labelledby="confirmLabel" aria-hidden="true">
        <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                        <h4 class="modal-title" id="confirmLabel">消息通知</h4>
                    </div>
                    <div class="modal-body">
                        <p class="content_title">${role } ${user_name }, 欢迎您访问党务工作信息平台!</p>
                        <div class="message_box">
                        </div>
                        <div class="btn_box">
                                <a class="todo_look" href="javascript:;">前往查看</a>
                        </div>
                    </div>
                </div>
            </div>
    </div>
    
    <!-- 欢迎弹窗 -->
    
     <div class="modal" id="greetModal" tabindex="-1" role="dialog" aria-labelledby="greetLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="greet_box">
                        <img class="greet_bg" src="/images/greet-bg.png" />
                        <div class="greet_content">
                            <p class="greet_text">欢迎您进入"西南大学党务工作信息平台"</p>
                            <button onclick="closeMod();">点击进入</button>
                            <script type="text/javascript">
                             function closeMod(){
                            	 $("#greetModal").modal("hide");
                             }
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
		
	</body>
</html>