<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>	
	<%-- <link rel="stylesheet" href="${basePath }/css/common.css" />
    <link rel="stylesheet" href="${basePath }/css/index.css" />	 --%>
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
                        <p class="mian_title">党务新闻</p>
                        <p class="en_title hidden-xs">Party affairs news</p>
                    </div>
                </div>
                <div class="more_container">
                    <a href="/newscenter" class="get_more">更多&gt;</a>
                </div>
            </div>
            <div class="news_content">
                <div class="news_small_banner">
                    <ul class="banner_list">
                        <!-- <li>
                            <img src="/images/mobile_banner.png" />
                        </li>
                        <li>
                            <img src="/images/mobile_banner.png" />
                        </li>
                        <li>
                            <img src="/images/mobile_banner.png" />
                        </li> -->
                        <c:forEach items="${url }" var="url" varStatus="status">
                    		<li>
	                           <a href="/detail?resources_id=${url.resources_id }"><img src="${url.attachment_url }" /></a>
	                        </li>
                    	</c:forEach>
                    </ul>
                    <div class="masklayer"></div>
                    <div class="banner_text">
                       <!--  <p class="show_text">学校成立大学生习近平新时代学校成立大学代学校成立大学生习近平新时代中国特色社中国特色社</p>
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
               <c:choose>
				   <c:when test="${status.first}">  
				        <li class="news_info_item news_item_hover">    
				   </c:when>
				   <c:otherwise> 
				        <li class="news_info_item">
				   </c:otherwise>
				</c:choose>
                        <a href="/detail?resources_id=${list.resourceId }">
                            <div class="item_container">
                                    <div class="news_info_content">
                                        <p class="item_title">
                                            <span class="news_trangle"></span>
                                        ${list.title }
                                        </p>
                                        <div class="item_details">
                                            <p class="details_info">
                                            ${list.body }
                                                <span class="details_more">【详情】</span>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="news_info_date">
                                        <span class="time_arrow"></span>
                                        <div class="date_day">${list.day }</div>
                                        <div class="date_year">${list.year }</div>
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

            p = window.setInterval("selectImg()", 3000);
           	
          	//banner hover 停止自动播放
            $('.news_small_banner').hover(function() {
                window.clearInterval(p);
            }, function() {
            	window.clearInterval(p);
                p = window.setInterval("selectImg()", 3000);
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
            $(".news_info .news_info_item").removeClass("news_item_hover");
            $(this).stop().addClass("news_item_hover");
        },function(){
            $(this).stop().removeClass("news_item_hover");
            $(".news_info .news_info_item").eq(0).stop().addClass("news_item_hover");
        });
        
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