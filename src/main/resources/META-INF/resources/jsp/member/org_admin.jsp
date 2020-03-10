<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/memberGroup" var="candidate" />
<portlet:resourceURL id="/org/adminSave" var="adminSave" />
<portlet:resourceURL id="/org/detail" var="detail" />
<portlet:resourceURL id="/org/edit" var="edit" />
<portlet:resourceURL id="/org/admin/orgadmin" var="orgadmin" />
<portlet:resourceURL id="/org/admin/orgTree" var="orgTreeUrl" />
<head>
  <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5"/> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>
    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
      <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/change-party-member.min.css" />

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
		height: 40px;
	}
    .layui-form-label.layui-required:after{
        content:"*";
        color:red;
        position: absolute;
        top:5px;
        left:15px;
    }
    .layui-input.disabled{
        background: #f5f5f5;
    }
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li
		{
		margin: 12px 0;
	}

    .search-org .layui-input-block .layui-input{
        border-radius: 0;border: none;
    }
    .search-org .layui-form-label{
        background: #ffab33;
        height: 38px;
        border: none;
        width: 48px;
        border-radius: 0;
        cursor: pointer;
        color:#fff;
    }
	/*.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a*/
    /*{*/
	/*	padding-left: 24px;*/
	/*	box-sizing: border-box;*/
	/*	font-size: 14px;*/
	/*	width: 100%;*/
	/*	height: 100%;*/
	/*	display: inline-block;*/
	/*	text-decoration: none;*/
    /*    white-space: nowrap;*/
    /*    text-overflow: ellipsis;*/
    /*    overflow: hidden;*/
    /*    color: #333;*/
	/*}*/
    .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li > a {
        padding-left: 44px;
        box-sizing: border-box;
        font-size: 14px;
        width: 100%;
        height: 100%;
        display: inline-block;
        text-decoration: none;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li {
        margin: 12px 0;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu a{
        font-size: 14px;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu a:hover{
        background-color: #fff;
        color: #333;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu_on a{
        background-color: #fff;
        color: #333;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li > a:hover .second_menu_on{
        border-left-color:#999;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li > a:hover {
        color: #333;
        background-color: transparent;
    }

    .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .second_menu_on > a {
        color: #333;
        background-color: #fff;
    }
    .main_content .min_width_1200 .nav_list .party_organization_list .second_menu .third_menu a {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        padding-left: 74px;
    }
    .second_menu > li > a{
        height: 20px;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .party_organization_list a{
        color: #333;
    }
	.main_content .min_width_1200 .content_info .operation_bar div {
		display: inline-block;
	}
	.main_content .min_width_1200 .content_info .operation_bar .select_choice img
		{
		width: 18px;
	}
	.main_content .min_width_1200 .content_info .operation_bar .btn_group button
		{
		margin: 0 5px;
	}
	.main_content .min_width_1200 .content_info .table_info tr td:nth-child(1) img
		{
		width: 18px;
		margin-right: 40px;
		cursor: pointer;
	}
}

@media ( max-width : 768px) {

}
	body {
		background: #fff;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list .height_auto
		{
		height: auto;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list>li {
		overflow: hidden;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li
		{
		margin: 5px 0;
	}
	.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu>li>a
		{
        padding-left: 44px;
        box-sizing: border-box;
        font-size: 14px;
        width: 100%;
        height: 34px;
        line-height: 34px;
        display: inline-block;
        text-decoration: none;
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
    color: #333;
    background-color: #fff;
}

.add_class a {
	color: #333;
	background-color: #fff;
}

.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .add_class>a
	{
    color: #333;
    background-color: #fff;
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

.main_content .min_width_1200 .nav_list {
    float: left;
    width: 14.16vw;
    height: 83.4vh;
    background: #EAEEF5;
    overflow-x: hidden;
    overflow-y: auto;
}
.btn_group {
    display: none;
}
.party_member_container.form_content .custom_form .layui-inline .layui-form-label {
    margin-bottom: 0;
}
.admin_set{
    margin: 48px 0 0 32px;
    font-size: 16px;
    height: 40px;
}
.admin_set .dropdown-display{
    height: 40px;
    line-height: 26px;
}
.admin_set .control-label{
    height: 100%;
    line-height: 40px;
    width: 80px;
    float: left;
}
.btn_container{
    clear: both;
    margin-left: 136px;
}
    .layui-layer-content form{
        padding:20px
    }
.layui-layer-page .layui-layer-content {
    position: relative;
    overflow: visible;
}
.layui-layer-page .layui-layer-content .layui-form-select{
    width: 100%;
}
.layui-form-label.orgTree-label{
    width: 120px;
}
.layui-input.orgTree{
    width: 400px;
}
</style>
<script type="text/javascript">
	$(function() {
        layui.config({
            base: '${basePath}/js/layui/module/'
        }).extend({
            treeSelect: 'treeSelect/treeSelect'
        });
        layui.use(['treeSelect','form'], function () {
            var treeSelect= layui.treeSelect;

            treeSelect.render({
                // 选择器
                elem: '#orgTree',
                // 数据
                data: '${orgTreeUrl}',
                // 异步加载方式：get/post，默认get
                type: 'get',
                // 占位符
                placeholder: '请选择',
                // 是否开启搜索功能：true/false，默认false
                search: true,
                // 点击回调
                click: function(d){
                    console.log(d);
                    if(d.current.isParent) return false;
                },
                // 加载完成后的回调函数
                success: function (d) {
                    console.log(d);
                }
            });
        });
		/* $("#model_box").hide(); */
		var orgId;
		function orgMember(orgId, orgType) {
			var rootType = $('#current_root').attr('org-type');
			$('#save').attr('disabled', false);
			refresh(orgId);
            // console.log(rootType + ',' + orgType);
            // $('#save').attr('disabled', true);
            // setTimeout(function() {
            //     $('.dropdown-sin-2').data('dropdown').changeStatus(
            //             "readonly");
            // }, 0);

		}
        var orgTypes = {
		    "branch": "党支部",
            "secondary": "党总支",
            "organization": "党委"
		}
		function orgDetail(){

		    $.post('${detail}', {id:$('#title').next().val()}, function (res) {
                if(res.result){
                    $('input[name="orgName"]').val(res.data.org_name);
                    $('input[name="orgType"]').val(orgTypes[res.data.org_type]);
                    $('input[name="address"]').val(res.data.org_address);
                    $('input[name="contactNumber"]').val(res.data.org_phone_number);
                    $('input[name="fax"]').val(res.data.org_fax);
                    $('input[name="secretary"]').val(res.data.org_secretary);
                    $('input[name="email"]').val(res.data.org_email);
                    $('input[name="contactor"]').val(res.data.org_contactor);
                    $('input[name="contactorNumber"]').val(res.data.org_contactor_phone);
                }else {
                    alert("未知错误");
                }
            })
        }

		$(".first_menu").click(function() {
			$(this).parents("li").toggleClass("height_auto");
			var _target = $(this).find("img");
			if (_target.hasClass("dropdown_up")) {
				_target.removeClass("dropdown_up").addClass("dropdown_down");
				<%--_target.attr("src", "${basePath}/images/dropdown_icon.png");--%>
			} else {
				_target.removeClass("dropdown_down").addClass("dropdown_up");
				<%--_target.attr("src", "${basePath}/images/second_menu_up.png");--%>
			}
			orgId = $(this).parent().attr('org-id');
			var orgType = $(this).parent().attr('org-type');

            $('#second_title').hide();
            $('#second_title').prev('span').hide();
            $('#third_title').hide();
            $('#third_title').prev('span').hide();
            $('#first_title').show().text($(this).text());

			$('#title').text($(this).text());
			$('#title').next().val(orgId);
			$('#title').attr("org_type", orgType);
			console.log("一级党委选中" + orgId);
			$(".btn_group").show();
			$("#org_add").show();
			$("#org_edit").hide();
			$("#org_delete").hide();
			orgMember(orgId, orgType);
			orgDetail();
		});
		//二级菜单下拉
		$(".second_menu").on("click",">li>a",function() {
            if ($(this).siblings("ul").length == 0) {
                $(".third_menu li").removeClass("third_menu_on");
                $(".second_menu>li").removeClass("second_menu_on");
                $(this).parent("li").addClass("second_menu_on");
                $(this).parent("li").siblings("li").removeClass("second_menu_on");
            }
            if ($(this).parent("li").hasClass("height_auto")) {
                $(this).parent("li").removeClass("height_auto")
                        .removeClass("second_menu_on");
            } else if ($(this).siblings("ul").length > 0) {
                $(this).parent("li").addClass("height_auto").addClass(
                        "second_menu_on");
                $(this).parent("li").siblings("li").removeClass("second_menu_on");
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
            $("#second_title").show().text($(this).text());
            $('#second_title').prev('span').show();
            $('#third_title').hide();
            $('#third_title').prev('span').hide();

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
            orgDetail();

        });

		//三级菜单选中
		$(".second_menu").on("click", ".third_menu li", function() {
			$(".third_menu li").removeClass("third_menu_on");
			$(this).addClass("third_menu_on");
			$(".second_menu li").removeClass("second_menu_on");
			$(this).parent().parent().addClass("height_auto second_menu_on");
			orgId = $(this).attr('id');

            $('#second_title').show().text($(this).parent().parent().children('a').text());
            $('#second_title').prev('span').show();
            $('#third_title').text($(this).children('a').text()).show();
            $('#third_title').prev('span').show();

			$('#title').text($(this).children('a').text());
			$('#title').next().val(orgId);
			$('#title').attr("org_type", 'branch');
			console.log("三级党委选中" + $(this).attr('id'));
			$(".btn_group").show();
		    $("#org_add").hide();
		    $("#org_edit").show();
			$("#org_delete").show()
			orgMember(orgId, 'branch');
            orgDetail();
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

        $('#current_root .first_menu').click();
		var path = window.location.pathname;
		if(path.indexOf('orgadmin') === -1 && path.indexOf('secondadmin') === -1){
		    $('#admin_set').hide();
		    $('#save').hide();
        }else {
		    $('#op_buttons').remove();
        }
        layui.use(['layer', 'form'], function(){
            var layer = layui.layer
                ,form = layui.form;
            //表单提交
            form.on('submit(partyForm)', function(data){
                // layer.alert(JSON.stringify(data.field), {
                //     title: '最终的提交信息'
                // });
                console.log(JSON.stringify(data.field));
                var postData = data.field;
                postData['orgId'] = $('#title').next().val();
                $.post("${edit}", postData, function (res) {
                    console.log(res.result)
                    if(res.result){
                        layer.msg("保存成功！");
                    }else {
                        layer.msg("未知错误");
                    }
                });
                return false;
            });
            form.verify({
                zuoji: function (value, item) {
                    if (!/^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$/.test(value)){
                        return '请填入正确的座机号';
                    }
                },
                fax: function (value, item) {
                    if (value != ''&& value != null && !/^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$/.test(value)){
                        return '请填入正确的传真号';
                    }
                },
                partyEmail: function(value, item){
                    var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                    if(value != ''&& value != null && !reg.test(value)){
                        return "邮箱格式正确";
                    }
                },
                contactNumber: function(value, item){
                    var regPhone = /^1\d{10}$/;
                    var reg = /^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$/;
                    if(value != ''&& value != null && !regPhone.test(value) && !reg.test(value)){
                        return "联系电话格式正确";
                    }
                }
            });

            //添加组织弹窗
            $("#org_add").click(function() {
                var org_type = $("#title").attr("org_type");
                if (!org_type) {
                    layer.msg("请选择一个节点");
                    return;
                }
                var title='';
                var content = '';
                if ("organization" == org_type) {
                    title = '增加二级党委';
                    content = $("#addSecondaryForm");
                } else if ("secondary" == org_type) {
                    title = '增加党支部';
                    content = $("#addBranchForm");
                }else{
                    layer.msg("请选择正确的节点");
                    return;
                }
                layer.open({
                    title:title,
                    type: 1,
                    content: content
                    ,btn: ['确定', '取消']
                    ,yes: function(index, layero){
                        //按钮【按钮一】的回调
                        var org_type = $("#title").attr("org_type");
                        if (!org_type) {
                            layer.msg("请选择一个节点");
                            return;
                        }
                        if ("organization" == org_type) {
                            _ajax('post','addSecondaryForm');
                        } else if ("secondary" == org_type) {
                            _ajax('post','addBranchForm');
                        }else{
                            layer.msg("请选择正确的节点");
                            return;
                        }
                    }
                    ,btn2: function(index, layero){
                        //按钮【按钮二】的回调
                    }
                    ,cancel: function(){
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });

            });
            /*编辑组织弹窗*/
            $("#org_edit").click(function() {
                var org_type = $("#title").attr("org_type");
                if (!org_type || "organization" == org_type) {
                    layer.msg("请选择一个节点");
                    return;
                }else {
                    var title = '编辑';
                    var content = $("#addBranchForm");
                    layer.open({
                        title: title,
                        type: 1,
                        content: content
                        , btn: ['修改', '取消']
                        , yes: function (index, layero) {
                            //按钮【按钮一】的回调
                            var org_type = $("#title").attr("org_type");
                            if (!org_type) {
                                layer.msg("请选择一个节点");
                                return;
                            }
                            if ("organization" != org_type) {
                                _ajax('edit', 'addBranchForm');
                            } else {
                                layer.msg("请选择正确的节点");
                                return;
                            }
                        }
                        , btn2: function (index, layero) {
                            //按钮【按钮二】的回调
                        }
                        , cancel: function () {
                            //右上角关闭回调
                            //return false 开启该代码可禁止点击该按钮关闭
                        }
                    });
                }
            });
            /* 删除组织弹窗 */
            $("#org_delete").click(function() {
                var name = $("#title").text();
                if(!name){
                    layer.msg("请选择节点");
                    return;
                }else {
                    var content = '你确定删除组织“'+name+'”吗？';
                    layer.confirm(content, {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        var org_type = $("#title").attr("org_type");
                        if (!org_type) {
                            layer.msg("请选择一个节点");
                            return;
                        }
                        if ("organization" != org_type) {//根节点不可删除
                            _ajax('delete', null);
                        } else {
                            layer.msg("请选择正确的节点");
                            return;
                        }
                    }, function(){
                    });
                }
            });
            function _ajax(_option,id) {
                var url = "${orgadmin}";
                var secondaryType='';
                var orgName = '';
                var org_type = $("#title").attr("org_type");
                var orgId = $("#title").next().val();
                if(id != null && id != ''){
                    orgName = $("#"+id+" input.org_name").val();
                    if (!orgName) {
                        layer.msg("请输入名称");
                        return;
                    }
                    if(id == 'addSecondaryForm'){
                        secondaryType = $("#"+id+" select.secondaryType").val();
                    }
                }
                $.ajax({
                    type : "post",
                    url : url,
                    data : {
                        orgName : orgName,
                        org_type : org_type,
                        orgId : orgId,
                        option : _option,
                        secondaryType: secondaryType
                    },
                    dataType : "json",
                    success : function(res) {
                        alert(res.message);
                        if ("ok" == res.state) {
                            $("#model_box").hide();
                            $("#model_box_s").hide();
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
});
</script>
</head>
<body>
<div>
    <div class="party_manage_container min_width_1200">
        <div class="party_manage_page">
            <div class="breadcrumb_group">
                当前位置：
                <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">基础数据管理</a>
                        <a href="javascript:;">党组信息管理</a>
                    </span>
            </div>
            <div class="party_manage_content content_form content_info">
                <div id="partySearchForm" class="party-search-form">
                    <form class="layui-form" >
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label orgTree-label">请选择组织:</label>
                                <div class="layui-input-inline" style="width: 200px;">
                                    <input type="text" name="orgTree" id="orgTree" placeholder="请选择组织" class="layui-input orgTree">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="content_form content_info content_table_container party_table_container party_manage_container" style="padding-top: 0;">
                    <div class="breadcrumb_group">
                        当前组织：
                        <span class="layui-breadcrumb" lay-separator=">" style="visibility: visible;">
                            <a id="first_title" href="javascript:;" style="display: none;">重庆大学党委</a>
                            <a href="javascript:;" id="second_title" style="display: none;">机关党委</a>
                            <a href="javascript:;" id="third_title" style="display: none;">机关党委</a>
                        </span>
                        <span id="title" org_type="branch" style="display: none;">中共重庆大学某学科第一支部委员会</span>
                        <input type="hidden">
                    </div>
                    <div class="table_content" id="op_buttons">

                        <div class="btn_group table_btns" >
                            <c:if test="${root.org_type != 'branch'}">
                                <button id="org_add" class="btn btn-default">添加组织</button>
                            </c:if>
                            <button id="org_edit" class="btn btn-default">编辑组织</button>
                            <button id="org_delete" class="btn btn-default">删除组织</button>
                        </div>
                        <div class="form_content party_member_container">
                            <div class="title_label" style="height: 286px;padding: 110px 16px;">
                                基本信息
                            </div>
                            <form class="layui-form custom_form" id="">
                                <div class="layui-inline" style="border-top: 1px solid #CCC;">
                                    <label class="layui-form-label layui-required">党组织名称：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="orgName" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline" style="border-top: 1px solid #CCC;">
                                    <label class="layui-form-label">党组织类型：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="orgType" maxlength="20" disabled autocomplete="off" class="layui-input disabled">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label layui-required">联系电话（座机）：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="contactNumber" lay-verify="zuoji" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">传真：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="fax" lay-verify="fax" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label layui-required">党组织书记：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="secretary" lay-verify="required" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">邮箱：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="email" maxlength="20" lay-verify="partyEmail"
                                               autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label layui-required">联系人：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="contactor"  lay-verify="required" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label">手机号码：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="contactorNumber" lay-verify="contactNumber" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline"  style="width: 100%;">
                                    <label class="layui-form-label" style="width: 25%;">地址：</label>
                                    <div class="layui-input-inline" style="width: 75%;">
                                        <input type="text" name="address" maxlength="20" autocomplete="off" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-inline btn_group">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-input-inline">
                                        <button type="submit" class="layui-btn" lay-submit="" lay-filter="partyForm">保存</button>
                                        <button type="reset" class="layui-btn layui-btn-primary">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-sm-9 admin_set" id="admin_set">
                        <span class="control-label">管理员</span>
                        <div class="col-sm-9">
                            <div class="dropdown-sin-2">
                                <select id="admin" style="display:none;" multiple placeholder="请选择"></select>
                            </div>
                        </div>
                    </div>
                    <div class="btn_container">
                        <button id="save" class="btn btn-default" style="margin-top: 32px;">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 弹窗 -->
<div style="display: none" id="addSecondaryForm">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">名称：</label>
            <div class="layui-input-block">
                <input type="text" name="org_name" lay-verify="required" lay-reqtext="名称是必填项，不能为空。" placeholder="请输入名称" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">类型：</label>
            <div class="layui-input-block">
                <select name="secondaryType" >
                    <option value="党委" selected>党委</option>
                    <option value="党总支">党总支</option>
                    <option value="党支部">党支部</option>
                </select>
            </div>
        </div>

    </form>
</div>
<div style="display: none" id="addBranchForm">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">名称：</label>
            <div class="layui-input-block">
                <input type="text" name="org_name" lay-verify="required" lay-reqtext="名称是必填项，不能为空。" placeholder="请输入名称" autocomplete="off" class="layui-input">
            </div>
        </div>
    </form>
</div>
</body>