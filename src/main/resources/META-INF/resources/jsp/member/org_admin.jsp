<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/memberGroup" var="candidate" />
<portlet:resourceURL id="/org/adminSave" var="adminSave" />
<portlet:resourceURL id="/org/detail" var="detail" />
<portlet:resourceURL id="/org/edit" var="edit" />
<portlet:resourceURL id="/org/admin/manage" var="manage" />
<portlet:resourceURL id="/org/admin/orgTree" var="orgTreeUrl" />
<head>
  <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5"/> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>
    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
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
.layui-input-inline.orgTree{
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
        layui.use(['treeSelect','form','layer',], function () {
            var treeSelect= layui.treeSelect,
                layer = layui.layer,
                form = layui.form;

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
                    $("#org-path").empty();
                    $("#org-path").append(getPathHtml(d.current));
                    renderOrgInfo(d.current.id);
                },
                // 加载完成后的回调函数
                success: function (d) {
                    treeSelect.checkNode('orgTree', d.data[0].id);
                    var root = '<a  href="javascript:;" >'+d.data[0].name+'</a>';
                    $("#org-path").append(root);
                }
            });
            function getPathHtml(node) {
                var pathHtml = '<a  href="javascript:;" >'+node.name+'</a>';
                if(node.parentTId != null){
                    var pNode = node.getParentNode();
                    pathHtml = getPathHtml(pNode)+'<span lay-separator="">></span>'+pathHtml;
                }
                return pathHtml;
            }
            function renderOrgInfo(id){
                $.ajax({
                    type : "post",
                    url : "${manage}",
                    data : {
                        id : id,
                        option : 'read',
                    },
                    dataType : "json",
                    success : function(res) {
                        layer.msg(res);
                    }
                });
            }
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
        });
        var orgTypes = {
		    "branch": "党支部",
            "secondary": "党总支",
            "organization": "党委"
		}



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
                                <div class="layui-input-inline orgTree">
                                    <input type="text" name="orgTree" id="orgTree" lay-filter="orgTree" placeholder="请选择组织" class="layui-input">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="content_form content_info content_table_container party_table_container party_manage_container" style="padding-top: 0;">
                    <div class="breadcrumb_group">
                        当前组织：
                        <span class="layui-breadcrumb"  style="visibility: visible;" id="org-path">
                        </span>
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