<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<style type="text/css">
			form select,input{
				width: 300px;
			}
		</style>
	</head>
	<body>
		<portlet:resourceURL id="/navigation/role" var="navigationRoleUrl"/>
		<portlet:resourceURL id="/navigation/sort" var="navigationSortUrl"/>
		
		<portlet:actionURL name="/party/addNav" var="addNavUrl">
		</portlet:actionURL>
		<form action="${addNavUrl }" method="post">
			导航名字:<input class="navName" name="navigationName"><br>
			访问角色:
			<select class="choose_role" name="accessRole">
				<c:forEach items="${roles }" var="role">
					<option>${role }</option>
				</c:forEach>
			</select><br>
			显示位置:
			<select class="choose_location" name="showLocation">
				<c:forEach items="${location }" var="location">
					<option>${location }</option>
				</c:forEach>
			</select><br>
			父节点:
			<select id="parent_node" name="parent_node"></select><br>
			排序位置:
			<select id="sort_location" name="navSort"></select><br>
			转跳地址:<input class="address" name="hrefUrl"><br>
			<button type="button" onclick="javascript:window.history.back(-1);" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3">取消</button>
			<span class="col-xs-1 visible-xs"></span>
			<button type="submit" class="btn btn-default main_color_btn search_btn col-sm-1 col-xs-3">保存</button>
		</form>
		
		<script type="text/javascript">
			function roleChanged(){
				var role=$(".choose_role").val();
				var location=$(".choose_location").val();
				$.ajax({
					type:"post",
					data:{"role":role,
							"location":location
						},
					url:"${navigationRoleUrl}",
					async:false,
					success:function(data){
						var jsonObj =  JSON.parse(data);
						var str =  "";
						for(var i=0;i<jsonObj.length;i++){
							str += "<option>"+jsonObj[i].navigation_name+"</option>"
						}
						$("#parent_node").html(str);
					}
				})
				parentChanged();
			}
			
			/* 导航排序位置 */
			function parentChanged(){
				var parentNode = $("#parent_node").val();
				var role=$(".choose_role").val();
				var location=$(".choose_location").val();
				$.ajax({
					type:"post",
					data:{"parentNode":parentNode,
						"role":role,
						"location":location
						},
					url:"${navigationSortUrl}",
					async:false,
					success:function(data){
						var jsonObj =  JSON.parse(data);
						var str =  "";
						for(var i=0;i<jsonObj.length;i++){
							str += "<option>"+jsonObj[i].navigation_name+"</option>"
						}
						$("#sort_location").html(str);
					}
				})
			}
			
			
			$(function(){
				roleChanged();
			})
			$(".choose_role").change(function(){
				roleChanged();
			})
			$(".choose_location").change(function(){
				roleChanged();
			})
			$("#parent_node").change(function(){
				parentChanged();
			})
		</script>
	</body>
</html>