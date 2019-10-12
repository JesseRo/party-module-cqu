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
     .form-group {
         margin-top: 20px;
     }
   </style>
</head>
<body>
	<portlet:actionURL name="/asset/delDevice" var="delDevie"></portlet:actionURL>
		<div class="content_form">
				<portlet:actionURL var="findTitleURL" name="/listfindtitlecommand">
				</portlet:actionURL>
			<form class="form-horizontal" role="form" action="${findTitleURL }" method="post">
				<div class="content_title hidden-xs">
    			      下拉值维护
      			</div>
            	<div class="form-group">
				  <div class="col-sm-6 col-xs-12">
                   	<div class="col-sm-10 col-xs-9">
						<input name="title" type="text" id="title" placeholder="请输入下拉值类型"  class="form_datetime form-control" value="${title }" name="title"/>
                    </div>
               	  </div>
			    
	    		<div class="btn_group">
					<button class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" type="submit">查询</button>
						<span class="col-xs-1 visible-xs"></span>
						<portlet:renderURL var="addInfoUrl">
							<portlet:param name="mvcRenderCommandName" value="/asset/addDeviceRender" />
						</portlet:renderURL>
					<a href="${addInfoUrl}">
						<button class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3" id="new_table">新增</button>
					</a>
	      	  	</div>
	      	  	</div>
          	 </form>   
		<div class="content_table_container ">
			<table class="content_table" >
				<thead class="table_title">
					<tr>
						<th hidden="hidden">id</th>
						<th>下拉值ID</th>
						<th>下拉值</th>
						<th>下拉值类型</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				
				 <tbody class="table_info">
                	<c:forEach items="${list}" var="list">
					<tr>
						    <td class="data_id" hidden="hidden" >${list.id}</td>
	                        <td >${list.resources_key }</td>
	                        <td >${list.resources_value }</td>
	                        <td >${list.resources_type }</td>
	                        <td >${list.remark }</td>
	                        <td class="operation">
								<portlet:renderURL var="updateDevice">
								   <portlet:param name="mvcRenderCommandName" value="/asset/addOrUpdateDeviceRender" />
								   <portlet:param name="id" value="${ list.id }"/>
								</portlet:renderURL>
								<c:if  test="${userId eq list.user_id}">
								<a href="${updateDevice}">
									<button type="button" class="btn btn-default td_assign_btn" data-toggle="modal"><span class="glyphicon glyphicon-pencil" ></span>&nbsp;
										编辑
									</button>
								</a>
									<input class="input_del" name="deldevie" value="${list.id} " type="hidden"/>
									<button type="button" class="btn btn-default td_assign_btn del_btn" data-toggle="modal" onclick="del(${list.id});">
										&nbsp;删除
									</button>
						   	  </c:if>
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
	  });
    	
    	
    	
    </script>
    <script type="text/javascript">
		//查询
	    	function labQuery(){
	    		$("#formQuery").submit();
	    	}

	</script>	
</html>