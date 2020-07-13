<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/adminSave" var="adminSave" />
<portlet:resourceURL id="/org/admin/query" var="findOrgAdmin" />
<portlet:resourceURL id="/org/users" var="findOrgUsers" />
<portlet:resourceURL id="/org/users/page" var="findOrgUsersPage" />
<portlet:resourceURL id="/org/tree" var="orgTreeUrl" />
<head>
  <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5"/> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
      <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/change-party-member.min.css" />

<style>
    /* 下拉多选样式 需要引用*/
    /* 多选样式开始*/
    select[multiple].layui-form-select>.layui-select-title>input.layui-input{ border-bottom: 0}
    select[multiple].layui-form-select dd{ padding:0;}
    select[multiple].layui-form-select .layui-form-checkbox[lay-skin=primary]{ margin:0 !important; display:block; line-height:36px !important; position:relative; padding-left:26px;}
    select[multiple].layui-form-select .layui-form-checkbox[lay-skin=primary] span{line-height:36px !important;padding-left: 10px; float:none;}
    select[multiple].layui-form-select .layui-form-checkbox[lay-skin=primary] i{ position:absolute; left:10px; top:0; margin-top:9px;}
    .multiSelect{ line-height:normal; height:auto; padding:4px 10px; overflow:hidden;min-height:38px; margin-top:-38px; left:0; z-index:99;position:relative;background:none;}
    .multiSelect a{ padding:2px 5px; background:#FFB800; border-radius:2px; color:#fff; display:block; line-height:20px; height:20px; margin:2px 5px 2px 0; float:left;}
    .multiSelect a span{ float:left;}
    .multiSelect a i {float:left;display:block;margin:2px 0 0 2px;border-radius:2px;width:8px;height:8px;padding:4px;position:relative;-webkit-transition:all .3s;transition:all .3s}
    .multiSelect a i:before, .multiSelect a i:after {position:absolute;left:8px;top:2px;content:'';height:12px;width:1px;background-color:#fff}
    .multiSelect a i:before {-webkit-transform:rotate(45deg);transform:rotate(45deg)}
    .multiSelect a i:after {-webkit-transform:rotate(-45deg);transform:rotate(-45deg)}
    .multiSelect a i:hover{ background-color:#545556;}
    .multiOption{display: inline-block; padding: 0 5px;cursor: pointer; color: #999;}
    .multiOption:hover{color: #5FB878}

    @font-face {font-family: "iconfont"; src: url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff')}
    .iconfont {font-family:"iconfont" !important;font-size:16px;font-style:normal;-webkit-font-smoothing: antialiased;-moz-osx-font-smoothing: grayscale;}
    .icon-fanxuan:before { content: "\e837"; }
    .icon-quanxuan:before { content: "\e623"; }
    .icon-qingkong:before { content: "\e63e"; }
    .layui-form-checked[lay-skin="primary"] i {
        border-color: #FFB800 !important;
        background-color: #FFB800;
        color: #fff;
    }
    .multiSelect a {
        line-height: 32px;
        height: 32px;
        margin-left: 5px;
    }
    .layui-form-select .layui-select-title .layui-anim div::-webkit-scrollbar-thumb {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
        background: #FFB800;
    }
    /* 多选样式结束*/

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
.party_member_container.form_content .custom_form .layui-inline:last-child{
    border-bottom: 1px solid #CCC;
}
.layui-treeSelect .ztree li span.button.root_close:before {
    content: "\e623";
}
.layui-treeSelect .ztree li span.button.root_open:before {
    content: "\e625";
}
.party_manage_page .party_manage_content .party_table_container {
    width: calc(100% - 8px);
}
.party_manage_container .layui-form-item{
    width:640px
}
.party_manage_container .layui-form .layui-form-label{
    width:120px;
}
.party_manage_container .layui-form .layui-input-inline{
    width:480px;
}
.party_manage_container .layui-form .layui-form-item.btn-save{
   text-align: center;
}
.party_manage_container .layui-form .layui-form-item.btn-save .layui-btn {
    width: 160px;
}

</style>
</head>
<body>
<div>
    <div class="party_manage_container min_width_1200">
        <div class="party_manage_page">
            <div class="breadcrumb_group">
                当前位置：
                <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">系统管理</a>
                        <a href="javascript:;">组织管理员设置</a>
                    </span>
            </div>
            <div class="party_manage_content content_form content_info">
                <div class="content_form content_info content_table_container party_table_container party_manage_container" style="padding-top: 0;">
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
                    <div class="breadcrumb_group">
                        当前组织：
                        <span class="layui-breadcrumb"  style="visibility: visible;" id="org-path">
                        </span>
                        <table id="adminMemberTable" lay-filter="adminMemberTable"></table>
                    </div>
                    <form class="layui-form" action=""  id="orgManager">
                        <div class="layui-block">
                            <label class="layui-form-label">管理员：</label>
                            <div class="layui-input-inline" id="manager-select">
                            </div>
                        </div>
                        <div class="layui-form-item btn-save">
                            <button  type="button" class="layui-btn layui-btn-radius layui-btn-warm" lay-submit="" lay-filter="orgManager" id="org_manager_save">保 存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.config({
        base: '${basePath}/js/layui/module/'
    }).extend({
        treeSelect: 'treeSelect/treeSelect'
    });
    layui.use(['treeSelect','form','layer','table'], function () {
        var treeSelect= layui.treeSelect,
            layer = layui.layer,
            table = layui.table,
            form = layui.form;
        var checkedNode = null;
        var pageInfo = {
            page:1,
            size:10
        };
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
                renderOrgManagers();
            },
            // 加载完成后的回调函数
            success: function (d) {
                checkedNode = d.data[0];
                treeSelect.checkNode('orgTree', checkedNode.id);
                $("#org-path").empty();
                $("#org-path").append(getPathHtml(checkedNode));
                renderOrgManagers();
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
        function renderAdminTable(page,size){
            var  where = {
                id: checkedNode.id
            };
            var cols = [[
                {field: 'member_name', align:'center', width:'40%',title: '姓名',templet: function(d) {
                        return '<a href="/memberDetail?userId='+d.member_identity+'" >' + d.member_name + '</a>';
                    }
                }
                ,{field: 'member_identity', align:'center', width:'40%',title: '公民身份证'}
                ,{field: 'historic', title: '操作', width:'20%', align:'center',toolbar: '#tableTool'}
            ]];
            var ins = table.render({
                elem: '#adminMemberTable'
                ,where: where
                ,height:450
                ,url: '${findOrgUsersPage}'//数据接口
                ,page: {
                    limit:size,   //每页条数
                    limits:[10,15,20],
                    prev:'&lt;上一页',
                    curr:page,
                    next:'下一页&gt;',
                    theme: '#FFB800',
                    groups:4
                }
                ,cols: cols
                ,done: function(res, curr, count){
                    pageInfo.page = curr;
                    pageInfo.size = ins.config.limit;
                    if(count<(pageInfo.page-1)*pageInfo.size){
                        renderTable(pageInfo.page-1,pageInfo.size);
                    }
                }
            });
            $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
            $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            //监听事件
            table.on('tool(adminMemberTable)', function(obj){
                switch(obj.event){
                    case 'delete':
                        //deleteAdminMember(obj.data.member_identity);
                        break;
                    case 'edit':
                        break;
                };
            });
        }
        function deleteAdminMember(){

        }
        function renderOrgManagers(){
            renderAdminTable(1,pageInfo.size);
            var postData = {
                id:checkedNode.id
            };
            $.post("${findOrgUsers}", postData, function (res) {
                if(res.code==200){
                    $('#manager-select').empty();
                    var selectHtml = '<select name="admin" multiple lay-search><option value="">请选择管理员</option>';
                    for(var i=0;res.data.length>0 && i<res.data.length;i++){
                        selectHtml = selectHtml + "<option value='"+res.data[i].user_id+"'>"+res.data[i].user_name+"("+res.data[i].user_id+")</option>";
                    }
                    selectHtml = selectHtml + '</select>';
                    $('#manager-select').append(selectHtml);
                    form.render();
                    $.post("${findOrgAdmin}", postData, function (res) {
                        if(res.code==200){
                            var  managerArr = new Array();
                            for(var i=0;res.data.length>0 && i<res.data.length;i++){
                                managerArr.push(res.data[i]['user_id']);
                            }
                            $('select[name="admin"]').val(managerArr);
                            form.render();
                        }else {
                            layer.msg(res.message);
                        }
                    },'json');
                }else {
                    layer.msg(res.message);
                }
            },'json');
        }
        //表单提交
        form.on('submit(orgManager)', function(data){
            var postData = {
                orgId:checkedNode.data.org_id,
                admin:data.field.admin.join(',')
            }
            $.post("${adminSave}", postData, function (res) {
                if(res.code==200){
                    layer.msg("保存成功！");
                }else {
                    layer.msg(res.message);
                }
            },'json');
            return false;
        });
    });
</script>
<script type="text/html" id="tableTool">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">移除管理员</a>
</script>
</body>
