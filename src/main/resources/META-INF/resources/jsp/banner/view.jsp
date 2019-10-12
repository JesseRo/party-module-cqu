<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=gb2312" %>

<head>
    <meta charset='utf-8' />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<%--     <link rel="stylesheet" href="${basePath}/css/common.css"> --%>
    <title>西南大学党务工作信息平台</title>
</head>

<body>
	<div class="index_banner full_screen hidden-xs">
		<img class="hidden-xs" src="${list.chaining_image_url }">
    </div>
        <%-- <ul class="banner_content">
           	<c:forEach items="${ list }" var="list" varStatus="status">
              		<li>
              			<img class="hidden-xs" src="${list.chaining_image_url }">
              		</li>
               </c:forEach>
           </ul>
           <ul class="focus">
               <c:forEach items="${ list }" var="list" varStatus="status">
                <li></li>
               </c:forEach>
           </ul>
    <script>
	    var p = index =  0;
	    var num = null;
	    $(document).ready(function() {
	        var num = $('.focus li').length;
	        //banner 手动切换
	        $(".focus li").click(function() {
	            var i = $(this).index();
	            index = i;
	            $('.banner_content').animate({
	                'margin-left': '-100' * i + '%'
	            }, 600);
	            $('.focus li').eq(i).addClass('on').siblings('li').removeClass('on');
	        });
	 
	        p = setInterval("selectImg()", 3000);
	        //banner hover 停止自动播放
	        $('.index_banner').hover(function() {
	            clearInterval(p);
	        }, function() {
	            p = setInterval("selectImg()", 3000);
	        });
	    });
	    //banner 切换图片数量
	    function selectImg() {
	        index++;
	        if (index > ${list.size() }) {
	            index = 0;
	        }
	        $(".focus li").eq(index).trigger('click');
	    };
    </script> --%>
</body>
