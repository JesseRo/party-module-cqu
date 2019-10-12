<!-- 文章信息查询页面  zlm-->
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<head>
	<style>
			 .text_overflow p{
			    margin-top: 5px;
			    position: relative;
			    line-height: 20px;
			    height: 20px;
			    overflow: hidden;
			}
			.overflow_change p:after {
			    content: "...";
			    font-weight: bold;
			    position: absolute;
			    bottom: 0;
			    right: 0;
			    padding: 0 20px 1px 45px;
			    background: url(http://css88.b0.upaiyun.com/css88/2014/09/ellipsis_bg.png) repeat-y;
			} 
			a{
				color: black;
			}
			@media(min-width:768px){
				.table_info .operation_btn{
					min-width:135px;
				}
				.table_info .publish_time{
					min-width:170px;
				}
				.table_info .reviewState{
					min-width:140px;
				}
				
				.table_info .publish_time {
                  min-width: 200px;
                }
                  .table_info .operation_btn {
                  min-width: 200px;
                }
                  
			}
			
			th,td{
			      text-align: left;
			}
    </style>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<div class="content_title" style="margin-bottom:30px;">
		发布新闻
	</div>
	<div class="content_form">
		<portlet:actionURL var="findTitleURL" name="/contentReleaseFindTitleRender">
			<portlet:param name="contentPortletKey" value="${contentPortletKey }" />
		</portlet:actionURL>
		<form class="form-horizontal" role="form" action="${findTitleURL }" method="post">
			<div class="form-group">
				<div class="col-sm-6 col-xs-12">
                    <span class="col-sm-2 col-xs-4 control-label">文章标题</span>
                    <div class="col-sm-10 col-xs-8">
						<input name="title" type="text" id="title" placeholder="请输入文章标题"  class="form_datetime form-control" value="${title }" name="title"/>
                    </div>
                </div>
                <div class="col-sm-6 col-xs-12">
                    <span class="col-sm-2 col-xs-4 control-label">发布人</span>
                    <div class="col-sm-10 col-xs-8">
						<input name="publisher_id" type="text" id="publisher_id" placeholder="请输入发布人"  class="form_datetime form-control" value="${publisher_id }" name="publisher_id"/>
                    </div>
                </div>
                <div class="col-sm-6 col-xs-12">
                    <span class="col-sm-2 col-xs-3 control-label">发布时间</span>
                    <div class="col-sm-10 col-xs-9">
                        <input type="text" name="date" id="labCheckDate"  value="${date }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   class="form-control start_date">
                    </div>
                </div>
                <div class="btn_group col-sm-6 col-xs-12">
					<button class="btn btn-default main_color_btn search_btn col-xs-offset-1 col-sm-3 col-xs-3" type="submit">查询</button>
					<span class="col-xs-1 visible-xs"></span>
					<portlet:renderURL var="addInfoUrl">
						<portlet:param name="mvcRenderCommandName" value="/info/addInfo" />
						<portlet:param name="contentPortletKey" value="${contentPortletKey }" />
					</portlet:renderURL>
					<a href="${addInfoUrl}">
						<button class="btn btn-default main_color_btn search_btn col-sm-3 col-xs-3" id="new_table">新增</button>
					</a>
		        </div>
			</div>
	    	
		</form>
		<div class="content_table_container">
            <table class="content_table">
                <thead class="table_title">
                    <tr>
                        <th>标题</th>
                        <th>发布人</th>
                        <th>发布时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody class="table_info">
                	<c:forEach items="${infos}" var="info" varStatus="status">
                    <tr>
                        <td data-label="标题" class="inner_table_title"><a style="line-height:20px;" href="/detail?resources_id=${info.resources_id}" target="_blank">${info.content_title }</a></td>
                        <td data-label="发布人"> ${info.publisher_id }</td>
                        <td data-label="发布时间" class="publish_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.publish_time}" /></td>
                        <td data-label="操作" class="operation_btn">
                        	<%-- <c:if test="${info.publisher_id == user_name }">  --%>
								<portlet:renderURL var="addInfoUrl">
									<portlet:param name="mvcRenderCommandName" value="/info/addInfo" />
									<portlet:param name="resourceId" value="${info.resources_id}" />
									<portlet:param name="contentPortletKey" value="${contentPortletKey }" />
								</portlet:renderURL>
								<a href="${addInfoUrl}&resourceId=${info.resources_id }&contentPortletKey=${contentPortletKey }">
<%-- 									<a onclick="update('${info.resources_id }');"> --%>
									<button type="button" class="btn btn-default td_assign_btn">
										编辑&nbsp;
									</button>
								</a>
								<portlet:renderURL var="delInfoUrl">
									<portlet:param name="mvcRenderCommandName" value="/info/delInfoAndAttachment" />
									<portlet:param name="resourceId" value="${info.resources_id}" />
									<portlet:param name="contentPortletKey" value="${contentPortletKey }" />
								</portlet:renderURL>
								<a onclick="del('${info.resources_id }');">
									<button type="button" class="btn btn-default td_assign_btn">
										删除&nbsp;
									</button>
								</a>
								<script type="text/javascript">
									//删除
								    function del(id){
								    	$.hgConfirm("提示","确定删除吗？");
								    	$("#hg_confirm").modal("show");
								    	$("#hg_confirm .btn_main").click(function(){
								    		$.ajax({
												url:"${delInfoUrl }",
												data:{"resourceId":id,"contentPortletKey":"${contentPortletKey}"},
												success:function(){
													window.location.reload();
												},
												error:function(){
													$.tip("error");
												}
											})
							                $("#hg_confirm").modal("hide");
							                $.tip("删除成功");
							            })
									}
								</script>
							<%-- </c:if> --%>
						</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>     
        </div>
        <!-- ---------------------分页-------------------------- -->
       		<c:if test="${ not empty infos}">
               <div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${sum }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
			            	<portlet:param name="title" value="${title }"/>
			            	<portlet:param name="date" value="${date }"/>
			            	<portlet:param name="publisher_id" value="${publisher_id }"/>
			            	<portlet:param name="approve_state" value="${approve_state }"/>
			            	<portlet:param name="contentPortletKey" value="${contentPortletKey }"/>
			            </portlet:actionURL>
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <input type="hidden" id="total_page_" name="total_page_" value="${sum}"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
			    </div>
			    </c:if>
           </div>
			 <script type="text/javascript">
				 $(function(){
						$(".text_overflow").each(function(){
							var _target = $(this).find("p").html().length;
							if($(this).hasClass("inner_table_title")){
								if(_target > 27){
									$(this).addClass("overflow_change");
								}
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
						
					}
					$("#jumpPageNo").change(function(){
						$("input[name='pageNo']").val($(this).val());
					})
				    Page({
				         num: pages, //页码数
				         startnum: currentPage, //指定页码
				         elem: $('#page'), //指定的元素
				         callback: function(n) { //回调函数
				             $("input[name='pageNo']").val(n);
							 var pageNo = $("#pageNo").val();
// 							 $(window.parent.document).find("#myiframe").attr("src", "${pageNoUrl}&pageNo="+pageNo);//column_id="+column_id+"&site_id="+site_id+"&
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