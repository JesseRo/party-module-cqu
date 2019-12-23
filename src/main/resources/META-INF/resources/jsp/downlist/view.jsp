<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>下拉列表</title>
   <style type="text/css">
     th,td{
         text-align: left;
     }
	 .content_table thead tr{
		 background: #F6F8FC;
		 height: 48px;
		 font-size: 16px;
	 }
	 .content_table thead th{
		 padding: 5px 15px !important;
	 }
	 .content_table tr:nth-child(2n) {
		 background: #FBFCFE;
	 }
	 .content_table td{
		 min-width: 130px;
		 padding: 5px 15px !important;
		 height: 48px;
		 font-size: 14px;
	 }
	   .dropdown_table_container.content_table_container{
		   height: calc(100% - 60px);
	   }
   </style>
</head>
<body>
<%--<div class="table_form_content activity_manage_container">--%>
	<!-- 右侧盒子内容 -->
<div class="table_form_content">
	<div class="activity_manage_page">
		<div class="breadcrumb_group">
			当前位置：
			<span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">系统管理</a>
                <a href="javascript:;">下拉值管理</a>
            </span>
		</div>
		<div class="bg_white_container">
			<portlet:actionURL name="/asset/delDevice" var="delDevie"></portlet:actionURL>
			<portlet:actionURL var="findTitleURL" name="/listfindtitlecommand">
			</portlet:actionURL>
			<form class="form-horizontal" role="form" action="${findTitleURL }" method="post">
				<div class="form-group">
					<div class="col-sm-3 col-xs-12">
						<div class="col-sm-12 col-xs-9" style="padding-left: 0;">
							<select name="title" type="text" id="title" placeholder="请输入下拉值类型"  class="form_datetime form-control"  name="title">
								<option disabled>请输入下拉值类型</option>
								<option value="">全部类型</option>
								<option value="meetingType">活动类型</option>
								<option value="reason">驳回原因</option>
								<option value="taskStatus">任务状态</option>
								<option value="positior">党内职务</option>
								<option value="room">学生宿舍园区</option>
							</select>
						</div>
					</div>

					<div class="btn_group">
						<button class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" type="submit" style="display: none;">查询</button>
							<span class="col-xs-1 visible-xs"></span>
							<portlet:renderURL var="addInfoUrl">
								<portlet:param name="mvcRenderCommandName" value="/asset/addDeviceRender" />
							</portlet:renderURL>
						<a href="${addInfoUrl}">
							<button class="layui-btn" id="new_table" style="padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;background-color: #FFAB33;border-radius: 4px;">新增</button>
						</a>
					</div>
				</div>
			</form>

			<div class="content_table_container dropdown_table_container">
				<div class="table_outer_box">
					<table class="layui-table custom_table">
						<thead>
							<tr>
								<td style="display: none;">id</td>
								<td>下拉值ID</td>
								<td>下拉值</td>
								<td>下拉值类型</td>
								<td>备注</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="list">
							<tr>
								<td class="data_id" hidden="hidden" >${list.id}</td>
								<td >${list.resources_key }</td>
								<td >${list.resources_value }</td>
								<td >${typeMapping[list.resources_type] }</td>
								<td >${list.remark }</td>
								<td class="operation">
									<portlet:renderURL var="updateDevice">
									   <portlet:param name="mvcRenderCommandName" value="/asset/addOrUpdateDeviceRender" />
									   <portlet:param name="id" value="${ list.id }"/>
									</portlet:renderURL>
		<%--							<c:if  test="${userId eq list.user_id}">--%>
									<a style="margin-right: 10%; color: #2E87FF; cursor: pointer;" onclick="window.location.href='${updateDevice}'">编辑</a>
									<input class="input_del" name="deldevie" value="${list.id} " type="hidden"/>
									<a onclick="del(${list.id});" style="cursor: pointer;color: #FE4D4D;">删除</a>
		<%--						  </c:if>--%>
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
						<p>共<span class="total_page">${sum }</span>页</p>
						<portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
							<portlet:param name="title" value="${title }"/>
						</portlet:actionURL>
						<form action="${pageNoUrl }" id="getPageNo" method="post">
							<input type="hidden" id="pageNo" name="pageNo" value=""/>
							<input type="hidden" name="Site" value="${Site }"/>
							<input type="hidden" name="Column" value="${Column }"/>
							<input type="hidden" id="total_page_" name="total_page_" value="${sum}"/>
							<span>跳转到第</span>
							<input type="text" id="jumpPageNo" name="jumpPageNo"/>
							<span>页</span>
							<button type="submit" class="button">确定</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
 <script>
 /* $(".del_btn").click(function(){
	 var _target = $(this).parents(".operation").siblings(".data_id");
	 var id = _target.text();
	 del(id);
 }) */
 	function del(id){
		if(confirm("确定删除吗？")){
			$.ajax({
				url:"${delDevie }",
				data:{"id":id},
				success:function(){
					window.location.reload();
				},
				error:function(){
					alert("error");
				}
			})
		}
	}
    	//分页
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
			$('#title').change(function () {
				$('.form-horizontal').submit();
			});
			$('#title').val('${title}');
	  });
    	
    	
    	
    </script>
    <script type="text/javascript">
		//查询
	    	function labQuery(){
	    		$("#formQuery").submit();
	    	}

	</script>	
</html>