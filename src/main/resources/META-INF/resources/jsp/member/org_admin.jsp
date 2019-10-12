<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/memberGroup" var="candidate" />
<portlet:resourceURL id="/org/adminSave" var="adminSave" />
<portlet:resourceURL id="/org/admin/orgadmin" var="orgadmin" />

<head>
  <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5"/> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage.css"/>
    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>

<style>
@media ( min-width : 768px) {
	.main_content .min_width_1200 .nav_list .party_organization_list .height_auto
		{
		height: auto;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list>li {
		overflow: hidden;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li {
		cursor: pointer;
		height: 27px;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .first_menu
		{
		padding-bottom: 5px;
		border-bottom: 1px solid #e1e1e1;
		color: #666;
		font-size: 16px;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .dropdown_icon
		{
		width: 9px;
		margin: 5px 0;
		cursor: pointer;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li
		{
		margin: 12px 0;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a
		{
		padding-left: 24px;
		box-sizing: border-box;
		font-size: 14px;
		width: 100%;
		height: 100%;
		display: inline-block;
		text-decoration: none;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a:hover
		{
		color: #fff;
		background-color: #ce0000;
	}
	/*  .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .second_menu_on > a {
    color: #fff;
    background-color: #ce0000;
  }  */
	.main_content .min_width_1200 .content_info .operation_bar {
		height: 34px;
		margin-top: 36px;
		color: #333;
	}
	.main_content .min_width_1200 .content_info .operation_bar div {
		display: inline-block;
	}
	.main_content .min_width_1200 .content_info .operation_bar .select_choice
		{
		cursor: pointer;
		line-height: 34px;
	}
	.main_content .min_width_1200 .content_info .operation_bar .select_choice img
		{
		width: 18px;
	}
	.main_content .min_width_1200 .content_info .operation_bar .btn_group button
		{
		margin: 0 5px;
	}
	.main_content .min_width_1200 .content_info .operation_bar .time_select
		{
		float: right;
	}
	.main_content .min_width_1200 .content_info .table_info tr td:nth-child(1) img
		{
		width: 18px;
		margin-right: 40px;
		cursor: pointer;
	}
}

@media ( max-width : 768px) {
	body {
		background: #fff;
	}
	.small_header_container .mobile_header_title {
		margin-left: -25px;
	}
	.form-group {
		margin-top: 30px;
	}
	.select_choice {
		padding: 0 15px;
		height: 20px;
		margin-bottom: 10px;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list .height_auto
		{
		height: auto;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list>li {
		overflow: hidden;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li {
		cursor: pointer;
		height: 27px;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .first_menu
		{
		padding: 0 3px;
		padding-bottom: 5px;
		border-bottom: 1px solid #e1e1e1;
		color: #666;
		font-size: 13px;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .dropdown_icon
		{
		width: 9px;
		margin: 5px 0;
		cursor: pointer;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li
		{
		margin: 5px 0;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a
		{
		padding-left: 10px;
		box-sizing: border-box;
		font-size: 12px;
		width: 100%;
		height: 100%;
		display: inline-block;
		text-decoration: none;
		color: #999;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a:hover
		{
		color: #fff;
		background-color: #ce0000;
	}
	/*  .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .second_menu_on > a {
    color: #fff;
    background-color: #ce0000;
  }   */
}

.main_content .min_width_1200 .nav_list .party_organization_list .dropdown_up
	{
	width: 5px;
	margin: 6px 0;
}

.main_content .min_width_1200 .nav_list .party_organization_list .dropdown_down
	{
	width: 9px;
	margin: 8px 0;
}

@media ( min-width : 768px) {
	.main_content .min_width_1200 .nav_list .party_organization_list .second_menu .third_menu a
		{
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	.main_content.full_screen {
		background: #edf1f2;
		/*   padding-top: 20px; */
	}
	.portlet-content {
		padding: 0 0;
	}
}

.add_class {
	color: #fff;
	background-color: #ce0000;
}

.add_class a {
	color: #fff;
	background-color: #ce0000;
}

.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .add_class>a
	{
	color: #fff;
	background-color: #ce0000;
}

#model {
	background: #5cb85c;
	WIDTH: 500PX;
	HEIGHT: 300PX;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background: white;
}

#model_box {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.3);
	display: none;
}

.title_box {
	margin: 30px;
	border-bottom: 1px solid #e0dada;
}

.title {
	font-size: 30px;
}

.close {
	color: red;
	float: right;
	/*  border: 1px solid; */
	background: #d6c7c7;
	width: 50px;
	text-align: center;
}

.name_box {
	height: 100px;
	margin-left: 30px;
}

.name_box span {
	font-size: 25px;
}

button.cancal.btn.btn-default {
	margin: 30px;
}

button#add_submit {
	float: right;
	margin-top: 30px;
	margin-right: 50px;
}
.btn_group {
    display: none;
}
</style>
<script type="text/javascript">
	$(function() {
		/* $("#model_box").hide(); */
		var orgId;
		function orgMember(orgId, orgType) {
			var rootType = $('#current_root').attr('org-type');
			$('#save').attr('disabled', false);
			refresh(orgId);
			if ((rootType === 'organization' && orgType === 'branch')
					|| (rootType === orgType && rootType !== 'organization')) {
				console.log(rootType + ',' + orgType);
				$('#save').attr('disabled', true);
				setTimeout(function() {
					$('.dropdown-sin-2').data('dropdown').changeStatus(
							"readonly");
				}, 0);
			}
		}

		$(".first_menu").click(function() {
			$(this).parents("li").toggleClass("height_auto");
			var _target = $(this).find("img");
			if (_target.hasClass("dropdown_up")) {
				_target.removeClass("dropdown_up").addClass("dropdown_down");
				_target.attr("src", "${basePath}/images/dropdown_icon.png");
			} else {
				_target.removeClass("dropdown_down").addClass("dropdown_up");
				_target.attr("src", "${basePath}/images/second_menu_up.png");
			}
			orgId = $(this).parent().attr('org-id');
			var orgType = $(this).parent().attr('org-type');
			$('#title').text($(this).text());
			$('#title').next().val(orgId);
			$('#title').attr("org_type", orgType);
			console.log("一级党委选中" + orgId);
			$(".btn_group").show();
			$("#org_add").show();
			$("#org_edit").hide();
			$("#org_delete").hide();
			orgMember(orgId, orgType);
		});
		//二级菜单下拉
		$(".second_menu").on("click",">li>a",function() {
					if ($(this).siblings("ul").length == 0) {
						$(".third_menu li").removeClass("third_menu_on");
						$(".second_menu>li").removeClass("second_menu_on");
						$(this).parent("li").addClass("second_menu_on");
					}
					if ($(this).parent("li").hasClass("height_auto")) {
						$(this).parent("li").removeClass("height_auto")
								.removeClass("second_menu_on");
					} else if ($(this).siblings("ul").length > 0) {
						$(this).parent("li").addClass("height_auto").addClass(
								"second_menu_on");
					}
					var _target = $(this).find(".third_menu_icon");
					if (_target.hasClass("third_menu_up")) {
						_target.removeClass("third_menu_up").addClass(
								"third_menu_down");
					} else {
						_target.removeClass("third_menu_down").addClass(
								"third_menu_up");
					}
					orgId = $(this).parent().attr('id');
					var orgType = $(this).parent().attr('org-type');
					$('#title').text($(this).text());
					$('#title').next().val(orgId);
					$('#title').attr("org_type", orgType.trim());
					console.log("二级党委选中" + orgId);
					if ($("#current_root").attr("org-type") == 'secondary') {
						$(".second_menu").find(".add_class").removeClass(
								"add_class");
						$(this).parent("li").addClass("add_class");
					}
					$(".btn_group").show();
					$("#org_add").show();
					$("#org_edit").show();
					$("#org_delete").show();
					orgMember(orgId, orgType);

				});

		//三级菜单选中
		$(".second_menu").on("click", ".third_menu li", function() {
			$(".third_menu li").removeClass("third_menu_on");
			$(this).addClass("third_menu_on");
			$(".second_menu li").removeClass("second_menu_on");
			$(this).parent().parent().addClass("height_auto second_menu_on");
			orgId = $(this).attr('id');
			$('#title').text($(this).children('a').text());
			$('#title').next().val(orgId);
			$('#title').attr("org_type", 'branch');
			console.log("三级党委选中" + $(this).attr('id'));
			$(".btn_group").show();
		    $("#org_add").hide();
		    $("#org_edit").show();
			$("#org_delete").show()
			orgMember(orgId, 'branch');
		});

		//点击展开更多
		$(".slide_more").click(function() {
			//向后台请求更多数据
			$('.second_li').show();
		});

		//搜索回车事件
		$("#search").keydown(function(event) {
			if (event.keyCode === 13) {
				if ($(event.target).val()) {
					//执行搜索逻辑
					console.log($(event.target).val())
				}
			}
		});
		$('#upload-block [type="file"]').change(function() {
			$('#upload-block [type="submit"]').click();
		});

		var admin_dropdown = $('.dropdown-sin-2').dropdown({
			data : [ {
				name : '没有数据',
				disabled : true
			} ],
			input : '<input type="text" maxLength="20" placeholder="请输入搜索">',
			choice : function() {
			}
		});

		function refresh(org) {
			$.get('${candidate}', {
				orgId : org
			}, function(res) {
				if (res.result) {
					var admins = res.data.admins;
					var candidatesGroup = res.data.candidates;
					var candidates = [];
					var _admin = {};
					for ( var i in admins) {
						_admin[admins[i]] = admins[i];
					}
					for ( var group in candidatesGroup) {
						for ( var j in candidatesGroup[group]) {
							var member = candidatesGroup[group][j];
							candidates.push({
								id : member.member_identity,
								disabled : false,
								groupId : group,
								groupName : group,
								name : member.member_name,
								selected : false
							});
						}
					}
					if (candidates.length === 0) {
						candidates = [ {
							name : '没有数据',
							disabled : true
						} ];
					}
					$('.dropdown-sin-2').data('dropdown').changeStatus();
					$('.dropdown-sin-2').data('dropdown').update(candidates,true);
					$('.dropdown-sin-2').data('dropdown').choose(admins);
				}
			});
		}

		$('#save').on('click', function() {
			var admins = $('#admin').val();
			var adminStr = '';
			if (admins) {
				adminStr = admins.join(',')
			}
			$.post('${adminSave}', {
				orgId : orgId,
				admin : adminStr
			}, function(res) {
				if (res.result) {
					alert('保存成功');
				} else {
					alert(res.message);
				}
			})
		})

		/*添加按钮  */
		var option = "";
		$("#org_add").click(function() {
			option = "post";
			var org_type = $("#title").attr("org_type");
			if (!org_type) {
				alert("请选择一个节点");
				return;
			}
			if ("organization" == org_type) {
				$("#model .title").html("增加二级党委");
				$("#model_box").show();
			} else if ("secondary" == org_type) {
				$("#model .title").html("增加党支部 ");
				$("#model_box").show();
			} else {
				alert("请选择正确的节点");
			}

		});

		$(".cancal").click(function() {
			$("input[name='orgName']").val("");
			$("#model_box").hide();
		});
		$(".close").click(function() {
			$("input[name='orgName']").val("");
			$("#model_box").hide();
		});
		/*编辑*/
		$("#org_edit").click(function() {
			option = "edit";
			var org_type = $("#title").attr("org_type");
			if (!org_type) {
				alert("请选择一个节点");
				return;
			}
			$("#model .title").html("编辑 ");
			$("input[name='orgName']").val($("#title").text())
			$("#model_box").show();
		});
		/* 删除 */
		$("#org_delete").click(function() {
			var name = $("#title").text();
			if(!name){
				alert("请选择节点");
				return;
			}
			var bool = confirm("你确定删除" + name);
			if (bool) {
				option = "delete";
				_ajax(option);
			}
		});
		/*提交 */
		$("#add_submit").click(function() {
			_ajax(option);
		});


	function _ajax(_option) {
			var url = "${orgadmin}";
			var orgName = $("input[name='orgName']").val();
			if (!orgName && option != "delete") {
				alert("请输入名称");
				return;
			}
			var org_type = $("#title").attr("org_type");
			var orgId = $("#title").next().val();
			$.ajax({
				type : "post",
				url : url,
				data : {
					orgName : orgName,
					org_type : org_type,
					orgId : orgId,
					option : _option
				},
				dataType : "json",
				success : function(res) {
					alert(res.message);
					if ("ok" == res.state) {
						$("#model_box").hide();
						if (_option == "delete") {
							$(".party_organization_list li").each(function() {
										if ($(this).attr("id") == orgId) {
											$(this).remove();
											var candidates = [ {name : '没有数据',disabled : true} ];
											$('.dropdown-sin-2').data('dropdown').changeStatus();
											$('.dropdown-sin-2').data('dropdown').update(candidates, true);
											$('.dropdown-sin-2').data('dropdown').choose(candidates);
											$("#title").html('');
											$("#title").next().val('');
										}
									});
						} else if (_option == "edit") {
							$(".party_organization_list li").each(function() {
								if ($(this).attr("id") == orgId) {
									var a ;
									if(org_type=='secondary'){
										a =$('<a href="javascript:"><span class="third_menu_icon third_menu_down"></span>'+orgName+'</a>');
									}else{
										a =$('<a href="javascript:">'+orgName+'</a>');
									}
									$(this).html(a);
									$("#title").html(orgName);
								}
							});
						} else if (_option == "post") {
							var li;
							if(org_type=='secondary'){
								if($("#current_root").attr("org-type")=='secondary'){
									 li =$('<li id="'+res.uuid+'" class="second_li" org-type="branch "> <a href="javascript:"><span class="third_menu_icon third_menu_up"></span>'+orgName+'</a> </li>');
									 $(".second_menu").append(li);
								}else{
								$(".party_organization_list li").each(function() {
									if ($(this).attr("id") == orgId) {
										 li =$('<li title="'+orgName+'" id="'+res.uuid+'"><a href="javascript:">'+orgName+'</a></li>');
									         $(this).find("ul").append(li)
									}
								  });
								 }
							}else{
								 li =$('<li id="'+res.uuid+'" class="second_li" style="" org-type="secondary "> <a href="javascript:"><span class="third_menu_icon third_menu_up"></span>'+orgName+'</a> <ul class="third_menu">  </ul> </li>');
								     $(".second_menu").append(li)
							}																					
							 $("#title").html(orgName);
							 $("#title").attr("org_type",res.type);
							 $('#title').next().val(res.uuid);
							 orgMember(res.uuid, res.type);
						}
					}
				}
			});
		}
});
</script>
</head>
<body>
<div class="main_content full_screen">
    <div class="min_width_1200">
        <div class="nav_list">
            <p class="nav_list_title hidden-xs">操作中心</p>
            <%-- <div class="search_container">
                <input id="search" type="text" placeholder="请输入党组名称">
                <img src="${basePath}/images/search_icon.png"/>
            </div> --%>
            <ul class="party_organization_list">
                <li id="current_root" class="root" style="width: 300px;" org-id="${root.org_id}" org-type="${root.org_type}">
                    <div class="first_menu top_dropdown">
                        <span>${root.org_name }</span>
                        <img class="right dropdown_icon" src="${basePath}/images/second_menu_up.png"/>
                    </div>
                    <ul class="second_menu" id="current">
                        <c:forEach var="second" items="${tree[root.org_id]}" varStatus="status">
                            <c:choose>
                                <c:when test="${status.index le 4}">
                                    <li id="${second.org_id}" class="second_li" org-type="${second.org_type} ">
                                </c:when>
                                <c:otherwise>
                                    <li id="${second.org_id}" class="second_li" style="display: none;" org-type="${second.org_type} ">
                                </c:otherwise>
                            </c:choose><a href="javascript:"><span
                                    class="third_menu_icon third_menu_up"></span>${second.org_name}</a>
                                <c:if test="${not empty tree[second.org_id]}">
                                    <ul class="third_menu">
                                        <c:forEach var="third" items="${tree[second.org_id]}">
                                            <li title="${third.org_name}" id="${third.org_id}"><a
                                                    href="javascript:">${third.org_name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="slide_more">展开更多</div>
                </li>

            </ul>
        </div>
        <div class="content_form content_info">
            <div class="content_title hidden-xs">
                <span id="title" org_type=""></span>
                <input type="hidden">
                <div class="btn_group">
                 <button id="org_add" class="btn btn-default">添加组织</button>
                 <button id="org_edit" class="btn btn-default">编辑组织</button>
                 <button id="org_delete" class="btn btn-default">删除组织</button>
                </div>
            </div>
            <div style="margin-top: 15px;">
                <div class="col-sm-6 col-xs-12">
                    <span class="col-sm-2 col-xs-3 control-label" style="line-height: 32px;width: 20%;">管理员</span>
                    <div class="col-sm-10 col-xs-9">
                        <div class="dropdown-sin-2">
                            <select id="admin" style="display:none;" multiple placeholder="请选择"></select>
                        </div>
                    </div>
                </div>
                <button id="save" class="btn btn-default" style="margin-top: 32px;">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- 弹窗 -->
<div id="model_box">
  <div id="model">
	 <form action="">
	   <div class ="title_box"><span class="title"></span> <span class="close">×</span></div>
	   <div class="name_box">
	       <span>名称：</span>
	       <input type="text" class="org_name" name="orgName" style="width: 70%;height: 50%;">
	    </div>
	     <button type="button" class="cancal btn btn-default">取消</button>
	     <button type="button" id="add_submit" class="btn btn_main">提交</button>
	</form>
  </div>
</div>
</body>