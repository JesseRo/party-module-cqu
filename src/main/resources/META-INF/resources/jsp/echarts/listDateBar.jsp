<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	    <title>出勤率bar详细</title>
   		<%-- <script src="${basePath }/js/jquery-3.2.1.js"></script> --%>
   		
		<style type="text/css">
			.overflowTable{
				table-layout: fixed;  
			}
			.overflowTd{
				white-space: nowrap;     
				overflow: hidden;         
				text-overflow: ellipsis;  
			}
			.title_date{
				text-align: center;
    			font-size: 17px;
    			font-weight: bold;
			}
			
		</style>
	</head>
	
	<body>
		<div class="content_title hidden-xs">
	            统计报表
        </div>
		<div class="title_date">${keyname }</div>
		<div class="content_table_container">
                <table class="content_table">
                    <thead class="table_title">
						<tr>
							<th>支部名称</td>
							<th>出勤率</td>
						</tr>
                    </thead>
                    <tbody class="table_info" id="tBody">
						<c:forEach var="info" items="${list }">
							<tr>
								<td class="overflowTd">${info.keyname }</td>
								<td class="overflowTd">${info.valname }</td>
							</tr>
						</c:forEach>
                    </tbody>
                </table>
            </div>
            
        <!-- -----------------------分页------------------------- -->
				<div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${totalPage }</span>页</p>
			            
			            <!-- <portlet:actionURL name="/eChart/gotoListHtml/bySql" var="pageNoUrl">
						</portlet:actionURL> -->
						<portlet:renderURL var="pageNoUrl">
						    <portlet:param name="mvcRenderCommandName" value="/EchartDetailedBarCommand" />
						</portlet:renderURL>
						
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
			                <input type="hidden" id="keyname" name="keyname" value="${keyname}"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
				 </div>
		
				 <script type="text/javascript">
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
// 				         	alert($("input[name='pageNo']").val());
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