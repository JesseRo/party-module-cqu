<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/manage" var="manage" />
<portlet:resourceURL id="/org/tree" var="orgTreeUrl" />
<portlet:resourceURL id="/org/export" var="orgExport" />
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
    .no-authority  .layui-form.custom_form{
        display: table;
        margin: 0 auto;
        height: 286px;
    }
    .no-authority  .layui-form.custom_form .no-authority-msg{
        display: table-cell;
        vertical-align: middle;
        text-align: center;
    }
    .no-authority  .layui-form.custom_form .no-authority-msg i{
        font-size: 30px;
        color: #f93c3c;
    }
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
    .layui-form-label.layui-required:before{
        content: "*";
        color: red;
        top: 5px;
        right: 2px;
        position: relative;
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


.add_class a {
	color: #333;
	background-color: #fff;
}

.main_content .min_width_1200 .nav_list .party_organization_list li .second_menu .add_class>a
	{
    color: #333;
    background-color: #fff;
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
.party_member_container.form_content .custom_form .layui-inline:last-child{
    border-bottom: 1px solid #CCC;
}
.layui-treeSelect .ztree li span.button.root_close:before {
    content: "\e623";
}
.layui-treeSelect .ztree li span.button.root_open:before {
    content: "\e625";
}
#orgInfo .layui-form .edit-close{
    border: none;
}
.party_manage_page .party_manage_content .party_table_container {
    width: calc(100% - 8px);
}
    #org_update{
        text-align: center;
        width: 100%;
        border: none;
        padding: 20px;
    }
#addOrgForm .layui-form-label {
    width: 175px;
}
#addOrgForm .layui-form-label.layui-required:before {
    content: "*";
    color: red;
    top: 5px;
    right: 2px;
    position: relative;
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
            var checkedNode = null;
            renderTree();
            function renderTree(){
                treeSelect.destroy('orgTree');
                treeSelect.render({
                    // 选择器
                    elem: '#orgTree',
                    // 数据
                    data: '${orgTreeUrl}&isFilter=1',
                    // 异步加载方式：get/post，默认get
                    type: 'get',
                    // 占位符
                    placeholder: '请选择',
                    // 是否开启搜索功能：true/false，默认false
                    search: true,
                    // 点击回调
                    click: function(d){
                        checkedNode = d.current;
                        $("#org-path").empty();
                        $("#org-path").append(getPathHtml(checkedNode));
                        renderOrgInfo(checkedNode.id);

                    },
                    // 加载完成后的回调函数
                    success: function (d) {
                        if(checkedNode == null || checkedNode == undefined ){
                            checkedNode = d.data[0];
                        }
                        treeSelect.checkNode('orgTree', checkedNode.id);
                        $("#org-path").empty();
                        $("#org-path").append(getPathHtml(checkedNode));
                        renderOrgInfo( checkedNode.id);
                    }
                });
            }

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
                        if(res.code == 200){
                            $("#orgInfoNoAuthority").hide();
                            $('#orgInfo input[name="orgName"]').val(res.data.organization.org_name);
                            $('#orgInfo input[name="address"]').val(res.data.organization.org_address);
                            $('#orgInfo input[name="contactNumber"]').val(res.data.organization.org_phone_number);
                            $('#orgInfo input[name="fax"]').val(res.data.organization.org_fax);
                            $('#orgInfo input[name="secretary"]').val(res.data.organization.org_secretary);
                            $('#orgInfo input[name="email"]').val(res.data.organization.org_email);
                            $('#orgInfo input[name="contactor"]').val(res.data.organization.org_contactor);
                            $('#orgInfo input[name="contactorNumber"]').val(res.data.organization.org_contactor_phone);
                            var descType = res.data.organization.desc_type;
                            if(descType == null || descType == ''){
                                descType = 1;
                            }
                            $('#orgInfo .layui-form select').val(descType);
                            $("#orgInfo .layui-form input").each(function(){
                                $(this).attr("disabled",true);
                                $(this).addClass("edit-close");
                            })
                            $('#orgInfo .layui-form select').attr('disabled', 'disabled');
                            form.render('select');
                            renderBtn(res.data.permissions)
                            $("#orgInfo").show();
                        }else if(res.code == 402){
                            $("#orgInfo").hide();
                            $("#orgInfoNoAuthority").show();
                        }else if(res){
                            layer.msg(res.message, {icon:7});
                        }else{
                            layer.msg("请刷新后再试。", {icon:7});
                        }
                    }
                });
            }
            //根据权限展示操作按钮
            function renderBtn(permissions){
                if(permissions.indexOf("create")>-1){
                    if('${role}' == 'organization' || '${role}' == 'secondary'){
                        $("#org_add").show();
                    }
                }else{
                    $("#org_add").hide();
                }
                if(permissions.indexOf("delete")>-1){
                    if('${role}' == 'organization' || '${role}' == 'secondary') {
                        $("#org_delete").show();
                    }
                }else{
                    $("#org_delete").hide();
                }
                if(permissions.indexOf("update")>-1){
                    $("#org_update_edit").show();
                    $("#org_update_save").hide();
                    $("#org_update_cancel").hide();
                    $("#org_update").show();
                }else{
                    $("#org_update").hide();
                }

            }
            //修改组织提交
            form.on('submit(partyForm)', function(data){
                var postData = data.field;
                postData['option'] ='update';
                postData['id'] = checkedNode.id;
                $.post("${manage}", postData, function (res) {
                    if(res.code==200){
                        layer.msg(res.message);
                        $("#org_update_edit").show();
                        $("#org_update_save").hide();
                        $("#org_update_cancel").hide();
                    }else if(res.code == 402) {
                        layer.msg(res.message);
                    }else if(res){
                        layer.msg(res.message);
                    }else{
                        layer.msg("请刷新后再试。", {icon:7});
                    }
                },"json");
                return false;
            });
            //添加组织提交
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
                        return "邮箱格式不正确.";
                    }
                },
                contactNumber: function(value, item){
                    var regPhone = /^1\d{10}$/;
                    var reg = /^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$/;
                    if(value == ''&& value == null || (!regPhone.test(value) && !reg.test(value))){
                        return "联系电话格式不正确";
                    }
                }
            });

            //弹出添加、修改组织弹窗
            $("#org_add").click(function() {
                var id =checkedNode.id;
                var org_type = checkedNode.data.org_type;
                var title='';
                if ("organization" == org_type) {
                    title = '增加二级党委';
                    $('#addOrgForm .layui-form select[name="descType"]').empty();
                    $('#addOrgForm .layui-form select[name="descType"]').append(' <option value="1">党委</option><option value="0">党工委</option><option value="2">党总支</option>');
                    $('#addOrgForm .layui-form select[name="descType"]').val(1);
                    form.render('select');
                } else if ("secondary" == org_type) {
                    title = '增加党支部';
                    $('#addOrgForm .layui-form select[name="descType"]').empty();
                    $('#addOrgForm .layui-form select[name="descType"]').append('<option value="2">党总支</option><option value="3">党支部</option>' +
                        '                                            <option value="4">教工党支部</option>\n' +
                        '                                            <option value="5">专任教师支部</option>\n' +
                        '                                            <option value="6">博士生党支部</option>\n' +
                        '                                            <option value="7">硕士生党支部</option>\n' +
                        '                                            <option value="8">本科生党支部</option>\n' +
                        '                                            <option value="9">离退休党支部</option>');
                    $('#addOrgForm .layui-form select[name="descType"]').val(3);
                    //$('#addOrgForm .layui-form select[name="descType"]').attr('disabled', 'disabled');
                    form.render('select');
                }else{
                    layer.msg("请选择正确的节点");
                    return;
                }
                var index = layer.prompt({
                    title:title,
                    type: 1,
                    btn: 0,
                    content: $("#addOrgForm")
                });
                form.on('submit(addOrgForm)', function(data){
                    var postData = data.field;
                    postData["option"] = 'create';
                    postData["id"] = checkedNode.id;
                    $.post("${manage}", postData, function (res) {
                        if(res.code==200){
                            layer.msg(res.message);
                            renderTree();
                            layer.close(index);
                        }else if(res.code == 402) {
                            layer.msg(res.message);
                        }else if(res){
                            layer.msg(res.message);
                        }else{
                            layer.msg("请刷新后再试。", {icon:7});
                        }
                    },"json");
                    return false;
                });
            });
            /* 删除组织弹窗 */
            $("#org_delete").click(function() {
                var id =checkedNode.id;
                var orgName = checkedNode.data.org_name;
                if(!id){
                    layer.msg("请选择组织。");
                    return;
                }else {
                    var content = '你确定删除组织“'+orgName+'”吗？';
                    layer.confirm(content, {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        var postData = {
                            id:checkedNode.id,
                            option:"delete"
                        };
                        $.post("${manage}", postData, function (res) {
                            if(res.code==200){
                                layer.msg(res.message);
                                checkedNode = checkedNode.getParentNode();
                                renderTree();
                            }else if(res.code == 402) {
                                layer.msg(res.message);
                            }else if(res){
                                layer.msg(res.message);
                            }else{
                                layer.msg("请刷新后再试。", {icon:7});
                            }
                        },"json");
                    }, function(){
                    });
                }
            });
            /*点击编辑按钮*/
            $("#org_update_edit").click(function() {
                $("#org_update_edit").hide();
                $("#org_update_save").show();
                $("#org_update_cancel").show();
                $("#orgInfo .layui-form input").each(function () {
                    $(this).removeAttr("disabled");
                    $(this).removeClass("edit-close");
                })
                $('#orgInfo .layui-form select').removeAttr('disabled');
                form.render('select');
            });
            /*点击取消按钮*/
            $("#org_update_cancel").click(function() {
                $("#org_update_edit").show();
                $("#org_update_save").hide();
                $("#org_update_cancel").hide();
                $("#orgInfo .layui-form input").each(function(){
                    $(this).attr("disabled",true);
                    $(this).addClass("edit-close");
                })
                $('#orgInfo .layui-form select').attr('disabled', 'disabled');
                form.render('select');
            });
            /* 导出组织弹窗 */
            $("#org_export").click(function() {
                var id =checkedNode.id;
                var orgName = checkedNode.data.org_name;
                var orgId = checkedNode.data.org_id;
                if(!id){
                    layer.msg("请选择组织。");
                    return;
                }else {
                    window.open("${orgExport}&type=org&orgId=" + orgId + "&orgName=" + orgName  ,"_blank")
                }
            });
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
                        <a href="javascript:;">组织信息管理</a>
                    </span>
            </div>
            <div class="party_manage_content content_form content_info">
                <div class="content_form content_info content_table_container party_table_container party_manage_container" style="padding-top: 0;">
                    <div class="layui-form" >
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label orgTree-label">请选择组织:</label>
                                <div class="layui-input-inline orgTree">
                                    <input type="text" name="orgTree" id="orgTree" lay-filter="orgTree" placeholder="请选择组织" class="layui-input">
                                </div>
                                <button type="button" id="org_add" class="layui-btn layui-btn-warm" style="display:none">添加组织</button>
                                <button type="button" id="org_delete" class="layui-btn layui-btn-danger" style="display:none">删除组织</button>
                                <button type="button" id="org_import" class="layui-btn " style="">导入组织</button>
                                <button type="button" id="org_export" class="layui-btn " style="">导出组织</button>
                            </div>
                        </div>
                    </div>
                    <div class="breadcrumb_group">
                        当前组织：
                        <span class="layui-breadcrumb"  style="visibility: visible;" id="org-path">
                        </span>
                    </div>
                    <div class="table_content" id="op_buttons">

                        <div class="form_content party_member_container" id="orgInfo">
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
                                    <label class="layui-form-label layui-required">党组织类型：</label>
                                    <div class="layui-input-inline">
                                        <select name="descType" lay-verify="required" autocomplete="off"class="layui-input"> >
                                            <option value="1" selected>党委</option>
                                            <option value="0">党工委</option>
                                            <option value="2">党总支</option>
                                            <option value="3">党支部</option>
                                            <option value="4">教工党支部</option>
                                            <option value="5">专任教师支部</option>
                                            <option value="6">博士生党支部</option>
                                            <option value="7">硕士生党支部</option>
                                            <option value="8">本科生党支部</option>
                                            <option value="9">离退休党支部</option>
                                        </select>
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
                                    <label class="layui-form-label layui-required">手机号码：</label>
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
                                <div class="layui-inline" id="org_update" style="display:none">
                                    <button  type="button"  class="layui-btn layui-btn-warm" id="org_update_edit">编 辑</button>
                                    <button  type="button" class="layui-btn layui-btn-warm" lay-submit="" lay-filter="partyForm" id="org_update_save">保 存</button>
                                    <button  type="button" class="layui-btn layui-btn-primary" id="org_update_cancel">取 消</button>
                                </div>
                            </form>
                        </div>
                        <div class="form_content party_member_container no-authority" style="display:none" id="orgInfoNoAuthority">
                            <div class="title_label" style="height: 286px;padding: 110px 16px;">
                                基本信息
                            </div>
                            <div class="layui-form custom_form">
                                <div class="no-authority-msg">
                                    <i class="layui-icon layui-icon-tips" > 你没有权限查看该组织信息。</i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 弹窗 -->
<div style="display: none" id="addOrgForm">
    <form class="layui-form" action="">
        <input type="hidden" class="layui-layer-input" value="1">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label layui-required">名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="orgName" lay-verify="required" lay-reqtext="名称是必填项，不能为空。" placeholder="请输入名称" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label layui-required">类型</label>
                <div class="layui-input-inline">
                    <select name="descType" >
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label layui-required">联系电话（座机）</label>
                <div class="layui-input-inline">
                    <input type="text" name="contactNumber" lay-verify="zuoji" maxlength="20" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">传真</label>
                <div class="layui-input-inline">
                    <input type="text" name="fax" lay-verify="fax" maxlength="20" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label layui-required">党组织书记</label>
                <div class="layui-input-inline">
                    <input type="text" name="secretary" lay-verify="required" maxlength="20" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="email" maxlength="20" lay-verify="partyEmail"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label layui-required">联系人</label>
                <div class="layui-input-inline">
                    <input type="text" name="contactor"  lay-verify="required" maxlength="20" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label layui-required">手机号码</label>
                <div class="layui-input-inline">
                    <input type="text" name="contactorNumber" lay-verify="contactNumber" maxlength="20" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" >地址</label>
            <div class="layui-input-inline" style="width: 580px;" >
                <input type="text" name="address" maxlength="20" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-layer-btn layui-layer-btn-">
            <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="addOrgForm">确定</a>
            <a class="layui-layer-btn1">取消</a>
        </div>
    </form>
</div>
</body>