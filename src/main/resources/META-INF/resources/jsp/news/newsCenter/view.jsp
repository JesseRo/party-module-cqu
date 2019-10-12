<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<style type="text/css">
			 ul {
	            list-style: none;
	            padding: 0;
	            margin: 0;
	        }
	        
	        .pagination {
	            display: inline-block;
	            padding-left: 0;
	            margin: 20px 0;
	            border-radius: 4px
	        }
	        
	        .pagination>li {
	            display: inline;
	            
	        }
	        
	        .pagination>li>a,
	        .pagination>li>span {
	            position: relative;
	            float: left;
	            padding: 6px 12px;
	            line-height: 1.42857143;
	            color: #4d4d4d;
	            text-decoration: none;
	            background-color: #f3f3f3;
	            border: 1px solid #ddd;
	            cursor: pointer;
	            -webkit-touch-callout: none;
	            -webkit-user-select: none;
	            -khtml-user-select: none;
	            -moz-user-select: none;
	            -ms-user-select: none;
	            user-select: none;
	            margin: 0 4px;
	        }
	        
	        .pagination>li:first-child>a,
	        .pagination>li:first-child>span {
	            margin-left: 0;
	            border-top-left-radius: 4px;
	            border-bottom-left-radius: 4px;
	        }
	        
	        .pagination>li:last-child>a,
	        .pagination>li:last-child>span {
	            border-top-right-radius: 4px;
	            border-bottom-right-radius: 4px;
	        }
	        
	        .pagination>li>a:focus,
	        .pagination>li>a:hover,
	        .pagination>li>span:focus,
	        .pagination>li>span:hover {
	            z-index: 2;
	            color: #fff;
	            border-color: #ff7100;
	            background-color: #ff7100;
	        }
	        
	        .pagination>.active>a,
	        .pagination>.active>a:focus,
	        .pagination>.active>a:hover,
	        .pagination>.active>span,
	        .pagination>.active>span:focus,
	        .pagination>.active>span:hover {
	            z-index: 3;
	            color: #ff7100;
	            cursor: default;
	            background-color: #fff;
	            border-color: #fff;
	        }
	        
	        .pagination>.disabled>a,
	        .pagination>.disabled>a:focus,
	        .pagination>.disabled>a:hover,
	        .pagination>.disabled>span,
	        .pagination>.disabled>span:focus,
	        .pagination>.disabled>span:hover {
	            color: #777;
	            cursor: not-allowed;
	            background-color: #fff;
	            border-color: #ddd;
	        }
	        
	        .pagination-lg>li>a,
	        .pagination-lg>li>span {
	            padding: 10px 16px;
	            font-size: 18px;
	            line-height: 1.3333333;
	        }
	        
	        .pagination-lg>li:first-child>a,
	        .pagination-lg>li:first-child>span {
	            border-top-left-radius: 6px;
	            border-bottom-left-radius: 6px;
	        }
	        
	        .pagination-lg>li:last-child>a,
	        .pagination-lg>li:last-child>span {
	            border-top-right-radius: 6px;
	            border-bottom-right-radius: 6px;
	        }
	        
	        .pagination-sm>li>a,
	        .pagination-sm>li>span {
	            padding: 5px 10px;
	            font-size: 12px;
	            line-height: 1.5;
	        }
	        
	        .pagination-sm>li:first-child>a,
	        .pagination-sm>li:first-child>span {
	            border-top-left-radius: 3px;
	            border-bottom-left-radius: 3px;
	        }
	        
	        .pagination-sm>li:last-child>a,
	        .pagination-sm>li:last-child>span {
	            border-top-right-radius: 3px;
	            border-bottom-right-radius: 3px;
	        }
	        
	        .pager {
	            padding-left: 0;
	            margin: 20px 0;
	            text-align: center;
	            list-style: none;
	        }
	        
	        .pager li {
	            display: inline;
	        }
	        
	        .pager li>a,
	        .pager li>span {
	            display: inline-block;
	            padding: 5px 14px;
	            background-color: #fff;
	            border: 1px solid #ddd;
	            border-radius: 15px;
	        }
	        
	        .pager li>a:focus,
	        .pager li>a:hover {
	            text-decoration: none;
	            background-color: #eee;
	        }
	        
	        .pager .next>a,
	        .pager .next>span {
	            float: right;
	        }
	        
	        .pager .previous>a,
	        .pager .previous>span {
	            float: left;
	        }
	        
	        .pager .disabled>a,
	        .pager .disabled>a:focus,
	        .pager .disabled>a:hover,
	        .pager .disabled>span {
	            color: #777;
	            cursor: not-allowed;
	            background-color: #fff;
	        }
	        
	        .pageJump {
	            display: inline-block;
	            padding-left: 0;
	            margin: 20px 10px;
	            border-radius: 4px;
	            vertical-align: top;
	        }
	        
	        .pageJump p {
	            display: inline-block;
	            margin: 0;
	        }
	        
	        .pageJump .button,
	        .pageJump input {
	            font-size: 16px;
	            padding: 6px 12px;
	            margin-left: -1px;
	            line-height: 1.42857143;
	            color: #4d4d4d;
	            text-decoration: none;
	            background-color: #fff;
	            border: 1px solid #ddd
	        }
	        
	        .pageJump input {
	            width: 38px;
	            height: 38px;
	            border: 1px solid #ddd;
	            text-align: center;
	            background-color: #fff;
	            padding: 0;
	        }
	        
	        .pageJump-lg .button,
	        .pageJump-lg input {
	            padding: 10px 16px;
	            font-size: 18px;
	            line-height: 1.3333333;
	        }
	        
	        .pageJump-sm .button,
	        .pageJump-sm input {
	            padding: 5px 10px;
	            font-size: 12px;
	            line-height: 1.5;
	        }
	        
	        #page .not_allow {
	            color: #ddd;
	            border-color: #ddd;
	            cursor: not-allowed;
	        }
	        
	        .pageJump .button {
	            background-color: #f3f3f3;
	        }
	        
	        .pageJump form {
	            display: inline-block;
	        }
	        
	        .button:hover {
	            cursor: pointer;
	            color: #fff;
	            border-color: #ff7100;
	            background-color: #ff7100;
	        }
	        
	        #page .page_prev {
	            background-color: #fff;
	            width: 74px;
	        }
	        
	        #page .page_next {
	            background-color: #fff;
	            width: 74px;
	        }
	        
	        #page .page_prev:hover {
	            background-color: #ff7100;
	        }
	        
	        #page .page_next:hover {
	            background-color: #ff7100;
	        }
	        
	        .pagination_container {
	            text-align: center;
	            padding-top: 40px;
	        }
	        
	        .messge_container {
	            width: 1200px;
	            margin: 0 auto;
	        }
	        
	        .message_title {
	            font-size: 16px;
	            font-weight: bold;
	            padding-bottom: 5px;
	            border-bottom: 2px solid #4b4b4b;
	            margin: 0;
	        }
	        
	        .messge_container ul {
	            width: 100%;
	        }
	        
	        .messge_container li {
	            list-style: none;
	            width: 100%;
	            text-align: left;
	            height: 39px;
	            border-bottom: 1px solid #333;
	        }
	        
	        .messge_container li a {
	            color: #4b4b4b;
	            text-decoration: none;
	            line-height: 39px;
	        }
	        
	        .messge_container li span {
	            display: inline-block;
	        }
	        
	        .name {
	            width: 120px;
				vertical-align: top;
				text-align: center;
	        }
	        
	        .mian_info {
	            width: 900px;
	            overflow: hidden;
	            text-overflow: ellipsis;
	            white-space: nowrap;
	        }
	        
	        .mian_info a:hover {
	            color: #333;
	        }
	        
	        .date {
	            float: right;
	        }
			
			
			.table_info .party_title{
				text-align: left;
				padding-left: 10px;
			}
			.table_title th{
				text-align: left;
			}
			
			 @media(min-width:768px) {
	            .more_text_container {
	                width: 75%;
	            }
	            .more_text_container .more_text {
                    display: inline-block;
                     width: 90%;
	                line-height: 15px;
	                height: 14px;
	                overflow:hidden;
	                position:relative;
                }
                .more_text_container .text_overflow:after{
                	content: "...";
				    font-weight: bold;
				    position: absolute;
				    bottom: 0;
				    right: 0;
				    padding: 0 20px 1px 45px;
				    background: url(http://css88.b0.upaiyun.com/css88/2014/09/ellipsis_bg.png) repeat-y;
                }
	            
	        }
			th,td{
			    text-align: left;
			}
		</style>
		<%-- <script src="${basePath }/js/pagination.js"></script> --%>
	</head>
	<body>
		
		<div class="content_title hidden-xs">
		    	新闻中心
		</div>
		<div class="content_table_container">
		    <table class="content_table">
		    	<thead class="table_title">
		            <tr>
		                <th>会议标题</th>
		                <th>发布人</th>
		                <th>发布时间</th>
		            </tr>
		        </thead>
		        <tbody class="table_info">
		      		<c:forEach items="${list }" var="list"  varStatus="j">
				      		<!-- ${j.count}即打印循环次数 -->
				       		<tr>
				                <td data-label="会议标题" class="party_title more_text_container">
				               		<a class="more_text" href="/detail?resources_id=${list.resourcesId }" target="_blank">${list.content_title }</a>
				               	</td>
				                <td data-label="发布人">${list.publisher }</td>
				                <td data-label="发布时间">${list.time }前</td>
				           	</tr>
					</c:forEach>
	              </tbody>
	          </table>
	      </div>
		<div class="pagination_container">
	        <ul class="pagination" id="page"></ul>
	        <div class="pageJump">
	        	<input class='current_page' type="hidden" value="${pageNo}"/>
	            <p>共<span class="total_page">${pages }</span>页</p>
	            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
				</portlet:actionURL>
	            <form action="${pageNoUrl }" id="getPageNo" method="post">
	                <input type="hidden" id="pageNo" name="pageNo"/>
	                <input type="hidden" id="total_page_" name="total_page_" value="${pages}"/>
	                <span>跳转到第</span>
	                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
	                <span>页</span>
	                <button type="submit" class="button">确定</button>
	            </form>
	        </div>
	    </div>
	    <script type="text/javascript">
	    	//单行文本溢出隐藏
	    	$(function(){
	    		$(".more_text").each(function(){
	    			var _length = $(this).html().length;
	    			if(_length > 41){
	    				$(this).addClass("text_overflow");
	    			}
	    		})
	    		
	    	})
	    	
			 $(document).ready(function() {
				var pages = $(".total_page").html();
				var currentPage = $('.current_page').val();
				$("input[name='pageNo']").val($('.current_page').val());
				if(currentPage == 1){
					$('.page_next').removeClass('not_allow');
					$('.page_prev').addClass('not_allow');
					
				}else if(currentPage == pages){
					$('.page_prev').removeClass('not_allow');
					$('.page_next').addClass('not_allow');
					
				}else{
					
				};
				$("#jumpPageNo").change(function(){
					$("input[name='pageNo']").val($(this).val());
				})
			    Page({
			        num: pages, //页码数
			        startnum: currentPage, //指定页码
			        elem: $('#page'), //指定的元素
			        callback: function(n) { //回调函数
			            $("input[name='pageNo']").val(n);
			            $("#getPageNo").submit();
			            if (n == 1) {
			                $('#page a').removeClass('not_allow');
			                $('.page_prev').addClass('not_allow');
			            } else if (n >= $('.total_page').html()) {
			                $('#page a').removeClass('not_allow');
			                $('.page_next').addClass('not_allow')
			            } else {
			                $('#page a').removeClass('not_allow');
			            }
			        }
			    });
		  });
	 </script>
	</body>
</html>