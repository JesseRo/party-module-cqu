<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<style>
			#topNav{
				position: absolute;
			    right: 0;
			    top: 0;
			}
			#topNav li.page_on{
				border-bottom:3px solid #ce0000;
			}
			#topNav .page_on > a{
				color:#ce0000;
			}

		</style>
	</head>
	<body>
		<ul id="topNav" class="nav_list">
			<c:forEach items="${lists }" var="lists" varStatus="status">
				<li>
					<a href="${lists.navigation_url}" id="${lists.remark}"><span>${lists.navigation_name}</span></a>
					<input type="hidden" class="list_index" name="list_index" value="${status.index}"/>
				</li>
			</c:forEach>
			<input class="choosen_index" type="hidden" value=""/>
		</ul>
	</body>
	<script type="text/javascript">
		var organArr = [];
		var navArr = [];
		var _pathName = location.pathname;
		
		$(function(){
			//组织生活左侧导航的href集合
			$(".party_organization_list a").each(function(){
				organArr.push($(this).attr("href").toLocaleLowerCase());
			});
			//导航的href集合
			$("#topNav >li > a").each(function(){
				navArr.push($(this).attr("href").toLocaleLowerCase());
			});
			console.log(organArr)
			console.log(navArr)
			for(var i = 0;i<navArr.length;i++){
				if(_pathName == navArr[i] || _pathName.indexOf(navArr[i]) > 0){
					$("#topNav >li").eq(i).addClass("page_on");
				}
			};
			
			for(var i = 0;i<organArr.length;i++){
				if(_pathName == organArr[i] || _pathName.indexOf(organArr[i]) > 0){
					$("#topNav >li").eq(1).addClass("page_on");
				}
			}
		})
		
		
		
	</script>
</html>