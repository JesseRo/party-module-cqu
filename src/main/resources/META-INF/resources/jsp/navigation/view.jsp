<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
        <style type="text/css">
<%--        .message_modal .modal-content,--%>
<%--        .message_modal .modal-dialog {--%>
<%--          width: 851px;--%>
<%--        }--%>
<%--        .message_modal .content_title {--%>
<%--          font-size: 20px;--%>
<%--          color: #333;--%>
<%--          text-align: center;--%>
<%--          padding: 38px 0 58px 0;--%>
<%--        }--%>
<%--        .message_modal .modal-body {--%>
<%--          padding: 0 34px;--%>
<%--        }--%>
<%--        .message_modal .message_box {--%>
<%--          padding: 0 50px;--%>
<%--          height: 220px;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item {--%>
<%--          width: 195px;--%>
<%--          height: 220px;--%>
<%--          background: #f5f5f5;--%>
<%--          border: 1px solid #bbb;--%>
<%--          border-radius: 6px;--%>
<%--          float: left;--%>
<%--          margin-right: 48px;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item:last-child {--%>
<%--          margin-right: 0;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item .img_box {--%>
<%--          width: 73px;--%>
<%--          height: 82px;--%>
<%--          margin: 43px 61px 26px 61px;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item p {--%>
<%--          font-size: 16px;--%>
<%--          color: #333;--%>
<%--          text-align: center;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item p .bold_num {--%>
<%--          font-size: 28px;--%>
<%--          font-weight: bold;--%>
<%--        }--%>
<%--        .message_modal .message_box .message_item .item_name {--%>
<%--          line-height: 30px;--%>
<%--        }--%>
<%--        .message_modal .btn_box {--%>
<%--          text-align: center;--%>
<%--        }--%>
<%--        .message_modal .btn_box a {--%>
<%--          margin: 70px auto 50px auto;--%>
<%--          display: inline-block;--%>
<%--          font-size: 20px;--%>
<%--          color: #333;--%>
<%--          padding: 13px 75px;--%>
<%--          box-sizing: border-box;--%>
<%--          border: 1px solid #666;--%>
<%--          border-radius: 23px;--%>
<%--        }--%>
<%--        --%>
<%--        @media (min-width: 768px){--%>
<%--        .main_content .min_width_1200 .nav_list li a {--%>
<%--            text-decoration: none;--%>
<%--            font-size: 16px;--%>
<%--            color: #333;--%>
<%--            line-height: 28px;--%>
<%--        }--%>
<%--        --%>
<%--        --%>
<%--                /*欢迎弹窗样式*/--%>
<%--                #greetModal .modal-body{--%>
<%--                    padding: 0;--%>
<%--                    background-color: transparent;--%>
<%--                }--%>
<%--                #greetModal .greet_box{--%>
<%--                    position: relative;--%>
<%--                }--%>
<%--                #greetModal .modal-content{--%>
<%--                    background: transparent;--%>
<%--                    box-shadow: none;--%>
<%--                    border: none;--%>
<%--                }--%>
<%--                #greetModal .greet_bg{--%>
<%--                    width: 100%;--%>
<%--                }--%>
<%--                #greetModal .greet_content{--%>
<%--                    position: absolute;--%>
<%--                    bottom: 64px;--%>
<%--                    width: 100%;--%>
<%--                    text-align:center;--%>
<%--                }--%>
<%--                #greetModal .greet_content button{--%>
<%--                    width: 312px;--%>
<%--                    height: 56px;--%>
<%--                    margin-top: 84px;--%>
<%--                    border: none;--%>
<%--                    background: ffff;--%>
<%--                    background-color: #fff;--%>
<%--                    font-size: 22px;--%>
<%--                    color: #000;--%>
<%--                    letter-spacing: 1px;--%>
<%--                    border-radius: 10px;--%>
<%--                }--%>
<%--                .greet_text{--%>
<%--                    font-size: 24px;--%>
<%--                    background:url('/images/greet_text_bg.png');--%>
<%--                    -webkit-text-fill-color:transparent;--%>
<%--                    -webkit-background-clip:text;--%>
<%--                    -webkit-animation-name:masked-animation;--%>
<%--                    -webkit-animation-duration:10s;--%>
<%--                    -webkit-animation-iteration-count:infinite;--%>
<%--                    -webkit-animation-timing-function:linear;--%>
<%--                    color:#fff;--%>
<%--                }--%>
<%--                @-webkit-keyframes masked-animation {--%>
<%--                    0% {background-position:left bottom;}--%>
<%--                    100% {background-position:right bottom;}--%>
<%--                }--%>
<%--                   /*欢迎弹窗样式*/--%>
<%--           --%>
<%--        --%>
        </style>
        <script type="text/javascript">

        </script>
	</head>
	<body>
        <ul class="layui-nav layui-nav-tree menu_tree" id="menu_tree" lay-filter="menuTree" style="height: 100vh;">
			<c:forEach items="${lists }" var="navigations">
                <c:choose>
                    <c:when test="${not empty groups[navigations.navigation_id] }">
                        <li class="layui-nav-item" title="${navigations.navigation_name}">
                            <a href="javascript:;">
                                <img class="menu_icon" src="${navigations.navigation_icon }">${navigations.navigation_name}
                                <span class="layui-nav-more"></span>
                            </a>
                            <ul class="layui-nav-child">
                                <c:forEach items="${groups[navigations.navigation_id]}" var="secondaryPage">
                                    <li title="${secondaryPage.navigation_name}">
                                    <style>
                                        .layui-this #id${secondaryPage.navigation_id} .menu_icon{
                                           background-image: url("${navigations.navigation_icon_in }")
                                        }
                                        #id${secondaryPage.navigation_id} .menu_icon{
                                            background-image: url("${navigations.navigation_icon }")
                                        }
                                        #id${secondaryPage.navigation_id}:hover .menu_icon{
                                            background-image: url("${navigations.navigation_icon_in }")
                                        }
                                    </style>
                                        <a id="id${secondaryPage.navigation_id}" style="cursor: pointer;" _href="${secondaryPage.navigation_url}" onclick="window.location.href = '${secondaryPage.navigation_url}';">
                                        <span class="menu_icon"></span>${secondaryPage.navigation_name}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="layui-nav-item" navigations.navigation_name>
                            <a style="cursor: pointer;" _href="${navigations.navigation_url}" onclick="window.location.href = '${navigations.navigation_url}';">
                                <img class="menu_icon" src="${navigations.navigation_icon }"/>
                                <span>${navigations.navigation_name}</span>
<%--                                    <c:if test="${navigations.navigation_message eq 't'}">--%>
<%--                                        <img class="messageCount_img" src="/images/navigationmessage.png" />--%>
<%--                                        <input type="hidden" value="${navigations.messageCount }">--%>
<%--                                    </c:if>--%>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>

			</c:forEach>
		</ul>
		
		<!-- 消息提示弹窗 -->
<%--    <div class="modal fade message_modal" id="message_modal" tabindex="-1" role="dialog" aria-labelledby="confirmLabel" aria-hidden="true">--%>
<%--        <div class="modal-dialog">--%>
<%--            <div class="modal-content">--%>
<%--                <div class="modal-header">--%>
<%--                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">--%>
<%--                    &times;--%>
<%--                </button>--%>
<%--                    <h4 class="modal-title" id="confirmLabel">消息通知</h4>--%>
<%--                </div>--%>
<%--                <div class="modal-body">--%>
<%--                    <p class="content_title">${role } ${user_name }, 欢迎您访问党务工作信息平台!</p>--%>
<%--                    <div class="message_box">--%>
<%--                    </div>--%>
<%--                    <div class="btn_box">--%>
<%--                            <a class="todo_look" href="javascript:;">前往查看</a>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

    <!-- 欢迎弹窗 -->

<%--     <div class="modal" id="greetModal" tabindex="-1" role="dialog" aria-labelledby="greetLabel" aria-hidden="true">--%>
<%--        <div class="modal-dialog">--%>
<%--            <div class="modal-content">--%>
<%--                <div class="modal-body">--%>
<%--                    <div class="greet_box">--%>
<%--                        <img class="greet_bg" src="/images/greet-bg.png" />--%>
<%--                        <div class="greet_content">--%>
<%--                            <p class="greet_text">欢迎您进入"西南大学党务工作信息平台"</p>--%>
<%--                            <button onclick="closeMod();">点击进入</button>--%>
<%--                            <script type="text/javascript">--%>
<%--                             function closeMod(){--%>
<%--                            	 $("#greetModal").modal("hide");--%>
<%--                             }--%>
<%--                            </script>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

	</body>
</html>