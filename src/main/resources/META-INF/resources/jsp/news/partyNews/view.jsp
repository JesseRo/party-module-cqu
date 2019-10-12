<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>党建首页</title>
    <style type="text/css">
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
        
        p.item_title {
		    line-height: 16px;
		}
    </style>
</head>

<body>
    <div class="party_affair_news full_screen">
        <div class="min_width_1200">
            <div class="news_title">
                <div class="news_title_left">
                    <div class="title_img hidden-xs">
                        <img src="/images/index_news.png" />
                    </div>
                    <div class="title_text">
                        <p class="mian_title">党务动态</p>
                        <p class="en_title hidden-xs"></p>
                    </div>
                </div>
                <div class="more_container">
                    <a href="/newscenter" class="get_more">更多&gt;</a>
                </div>
            </div>
            <div class="news_content">
                <div class="news_small_banner">
                    <ul class="banner_list">                
                        <c:forEach items="${url }" var="url" varStatus="status">
                    		<li>
	                           <a href="/detail?resources_id=${url.resources_id }"><img src="${url.attachment_url }" /></a>
	                        </li>
                    	</c:forEach>
                    </ul>
                    <div class="masklayer"></div>
                    <div class="banner_text">
                        <!-- <p class="show_text">学校成立大学生习近平新时代学校成立大学代学校成立大学生习近平新时代中国特色社中国特色社</p>
                        <p>义思想研讨会学校成立大学生习近平新时代中</p>
                        <p>色社会主义思想研讨会</p> -->
                        <c:forEach items="${url }" var="url" varStatus="state">
                    	 <c:choose>
						   <c:when test="${state.first}">  
						         <p class="show_text">${url.content_title }</p>
						   </c:when>
						   <c:otherwise> 
						         <p>${url.content_title }</p>
						   </c:otherwise>
				        </c:choose>
                    	</c:forEach> 
                    </div>
                    <ul class="news_focus_list">
                        <li class="focus_on"></li>
                        <li></li>
                        <li></li>
                    </ul>
                </div>
                
              
                
                <ul class="news_info">
                      <c:forEach items="${list }" var="list" varStatus="status">           
                      <li class="news_info_item">
                        <a href="/detail?resources_id=${list.resourceId }">
                            <div class="item_container">
                                <div class="news_info_content">
                                    <p class="item_title">
                                        <span class="news_trangle"></span>
                                      ${list.title }
                                    </p>
                                </div>
                                <span class="news_time right">${list.year }/${list.day }</span>
                            </div>
                        </a>
                    </li>
                    </c:forEach>

                </ul>
            </div>
        </div>
    </div>
    
    <script>
        var p = index = 0;
        var num = null;
        $(document).ready(function() {
        	
        	$(".news_time").each(function(){
                var t=$(this).html();
                    t=t.replace(/\//g,"-");
                    $(this).html(t);
              });
        	
            var num = $('.news_focus_list li').length;
            //banner 手动切换
            $(".news_focus_list li").click(function() {
                var i = $(this).index();
                index = i;
                $('.banner_list').animate({
                    'margin-left': '-100' * i + '%'
                }, 600);
                $('.news_focus_list li').eq(i).addClass('focus_on').siblings('li').removeClass('focus_on');
                $(".banner_text p").eq(i).addClass("show_text").siblings("p").removeClass("show_text");
            });

            p = setInterval("selectImg()", 3000);
            //banner hover 停止自动播放
            $('.news_small_banner').hover(function() {
                clearInterval(p);
            }, function() {
                p = setInterval("selectImg()", 3000);
            });
        });
        //banner 切换
        function selectImg() {
            index++;
            if (index > $('.news_focus_list li').length - 1) {
                index = 0;
            }
            $(".news_focus_list li").eq(index).trigger('click');
        };

        $(".item_container").hover(function() {
            $(this).addClass("item_container_on");
        }, function() {
            $(this).removeClass("item_container_on");
        });


        //新闻悬浮效果
        $(".news_info .news_info_item").hover(function(){
            $(".news_info .news_info_item").removeClass("news_item_on");
            $(this).stop().addClass("news_item_on");
        },function(){
            $(this).stop().removeClass("news_item_on");
        })



       /*  //点击角色  弹出退出框
        $(".role_info").click(function(){
            $(".exit_box").css("display","block");
        });

        $('.exit_box').click(function(){
            //退出登录
            $(this).css("display","none");
        });
 */
      /*   function stopPropagation(e) { 
            if (e.stopPropagation) 
            e.stopPropagation(); 
            else 
            e.cancelBubble = true; 
        };
 */
        //点击页面其他地方关闭 退出框
      /*   $(document).bind('click',function(){ 
            $('.exit_box').css('display','none'); 
        }); 

        $('.role_info').bind('click',function(e){ 
            stopPropagation(e); 
        });
        $('.exit_box').bind('click',function(e){ 
            stopPropagation(e); 
        });   */
        

        //页面跳转时清除计时器
 document.getElementById("topNav").addEventListener("click",function(){
		   window.clearInterval(p);
	   });
 
 document.getElementById("topNav_logo").addEventListener("click",function(){
		   window.clearInterval(p);
	   });
    </script>
</body>

</html>