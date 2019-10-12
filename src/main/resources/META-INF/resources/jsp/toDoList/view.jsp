<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<html>
	<head>
		<style type="text/css">
			#tBody .color_blue{
				color:#CE0000;
			}
			#tBody .color_blue_1{
				color:#FF0000; 
			}
			
			@media (min-width: 768px){
                 .content_table td, .content_table th {
                   min-width: 210px;
                   padding: 0 25px;
            }
		</style>
	</head>
	<body>
		<div class="content_title hidden-xs">
		    	待办事项
		</div>
		<div class="content_table_container">
		    <table class="content_table">
		        <thead class="table_title">
		            <tr>
		            	<th>查看状态</th>
		                <th>所属组织</th>
		                <th>党支部</th>
		                <th>会议类型</th>
		                <th>开展主题</th>
		                <th>开展时间</th>
		                <th>开展地点</th>
		                <th style="min-width:300px;">操作</th>
		            </tr>
		        </thead>
		        <portlet:renderURL var="uploadExperienceUrl">
					<portlet:param name="mvcRenderCommandName" value="/party/uploadExperience" />
				</portlet:renderURL>
		        <portlet:renderURL var="evaluationUrl">
					<portlet:param name="mvcRenderCommandName" value="/party/evalution" />
				</portlet:renderURL>
		        <tbody class="table_info" id="tBody">
		        	<c:forEach items="${list }" var="list" varStatus="j">
		   				<!-- ${j.count}即打印循环次数 -->
		        		<tr>
		        			
		        			<td data-label="查看状态" class="receipts">
		        				<c:choose>
									<c:when test="${list.status eq '未读'}">
									 	<span style="color:red;">未读</a>
									</c:when>
									<c:otherwise>
										已查看
									</c:otherwise>
								</c:choose>
		        			</td>
							<td data-label="所属组织">${org }</td>
							<td data-label="党支部">${group }</td>
							<td data-label="会议类型">${list.meeting_type }</td>
							<td data-label="开展主题">
								<a href="/noticedetails?meetingId=${list.meeting_id }">${list.meeting_theme }</a>
							</td>
							<td data-label="开展时间">${list.time }</td>
							<td data-label="开展地点">${list.place }</td>
							<td data-label="操作" class="btn_group">
								<c:choose>
									<c:when test="${not empty list.experienceState }">
										<%-- <a href="/showexperience?meetingId=${list.meeting_id }&userId=${nameId}"> --%>
										<a href="${uploadExperienceUrl }&meetingId=${list.meeting_id }&userId=${nameId}">
											<button class="btn btn-default">查看学习心得</button>
										</a>
									</c:when>
									<c:otherwise>
										<c:if test="${list.timp == 'f'}">
											<button class="btn btn-default" onclick="disableEvaluation('会议未结束，不能上传学习心得！')">上传学习心得</button>
										</c:if>
										<c:if test="${list.timp == 't'}">
											<a href="${uploadExperienceUrl }&meetingId=${list.meeting_id }&userId=${nameId}">
												<button class="btn btn-default">上传学习心得</button>
											</a>
										</c:if>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty list.enableComment }">
										<c:choose>
											<c:when test="${not empty list.commentState }">
												<a href="/showevaluation?meetingId=${list.meeting_id }&userId=${nameId}">
													<button class="btn btn-default">查看评价</button>
												</a>
											</c:when>
											<c:otherwise>
												<c:if test="${list.timp == 'f'}">
													<button class="btn btn-default" onclick="disableEvaluation('会议未结束，不能评价！')">评价</button>
												</c:if>
												<c:if test="${list.timp == 't'}">
													<a href="${evaluationUrl }&meetingId=${list.meeting_id }&userId=${nameId}">
														<button class="btn btn-default">评价</button>
													</a>
												</c:if>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<button class="btn btn-default" onclick="disableEvaluation('该会议禁用了评价!')">评价</button>
									</c:otherwise>
								</c:choose>
							</td>
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
			
			/* function uploadExperience(input,meetingId,nameId,endTime){
				var date = new Date();
				if(date>=endTime){
					window.location.href = "${uploadExperienceUrl }&meetingId="+meetingId+"&userId="+nameId;
				}else{
					alert("会议还没开始,请结束后再评价!");
				}
			}
		 */
		 	$(function(){
		 		var td = document.getElementById("")
		 	});
			function disableEvaluation(str){
				$.tip(str);
				return;
			}
			
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
			
			//回执加色
			$(function(){
				$(".receipts").each(function(){
					var receipts_text = $(this).text();
					if(receipts_text == "未读"){
						$(this).addClass("color_blue_1");
					}
				})
			})
		</script>
	</body>
</html>