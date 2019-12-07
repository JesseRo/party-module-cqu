<!-- 文章信息查询页面  zlm-->
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
    </style>

<body>
	<div class="content_form">
		<portlet:actionURL var="findTitleURL" name="/navigation/findRole">
		</portlet:actionURL>
		<form class="form-horizontal" role="form" action="${findTitleURL }" method="post">
			<div class="form-group">
				<div class="col-sm-6 col-xs-12">
                    <span class="col-sm-2 col-xs-4 control-label">角色权限</span>
                    <div class="col-sm-10 col-xs-8">
						<input name="title" type="text" id="title" placeholder="请输入角色"  class="form_datetime form-control" value="${title }" name="title"/>
                    </div>
                </div>
                <div class="btn_group col-sm-6 col-xs-12">
					<button class="btn btn-default main_color_btn search_btn col-xs-offset-1 col-sm-3 col-xs-3" type="submit">查询</button>
					<span class="col-xs-1 visible-xs"></span>
					<portlet:renderURL var="addNavUrl">
						<portlet:param name="mvcRenderCommandName" value="/navigation/addNavigation" />
					</portlet:renderURL>
					<!-- 需要重新写rendercommand -->
					<a href="${addNavUrl}">
						<button class="btn btn-default main_color_btn search_btn col-sm-3 col-xs-3" id="new_table">新增</button>
					</a>
					
		        </div>
			</div>
	    	
		</form>
		<div class="content_table_container">
            <table class="content_table">
                <thead class="table_title">
                    <tr>
                        <th>导航名称</th>
                        <th>导航等级</th>
                        <th>显示位置</th>
                        <th>角色权限</th>
						<th>顺序</th>
						<th>操作</th>
                    </tr>
                </thead>
                <tbody class="table_info">
                	<c:forEach items="${list}" var="info" varStatus="status">
                    <tr>
                        <td data-label="导航名称" class="inner_table_title text_overflow"><p title="${info.name }">${info.name }</p></td>
                        <td data-label="导航等级"> ${info.level }</td>
                        <td data-label="显示位置">${info.location }</td>
                        <td data-label="角色权限">${info.role }</td>
						<td data-label="角色权限">${info.sort }</td>
                      <td data-label="操作">
							<portlet:renderURL var="updateNavigationUrl">
								<portlet:param name="mvcRenderCommandName" value="/navigation/updateNavigation" />
								<portlet:param name="navigationId" value="${info.navigationId}" />
							</portlet:renderURL>
							<a href="${updateNavigationUrl}&navigationId=${info.navigationId }">
								<button type="button" class="btn btn-default td_assign_btn">
									编辑&nbsp;
								</button>
							</a>
							<portlet:renderURL var="delNavigationUrl">
								<portlet:param name="mvcRenderCommandName" value="/navigation/delNavigation" />
								<portlet:param name="navigationId" value="${info.navigationId}" />
							</portlet:renderURL>
							<a onclick="del('${info.navigationId }');">
								<button type="button" class="btn btn-default td_assign_btn">
									删除&nbsp;
								</button>
							</a>
							<script type="text/javascript">
								//删除
							    function del(id){
									if(confirm("确定删除吗？")){
										$.ajax({
											url:"${delNavigationUrl }",
											data:{"navigationId":id},
											success:function(){
												window.location.reload();
											},
											error:function(){
												alert("error");
											}
										})
									}
								}
							</script>
						</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
       <!-- ---------------------分页-------------------------- -->
       			<c:if test="${ not empty list}">
               <div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${sum }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
			            	<portlet:param name="title" value="${title }"/>
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