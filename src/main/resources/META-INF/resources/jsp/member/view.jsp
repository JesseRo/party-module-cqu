<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/member" var="orgMember"/>
<portlet:resourceURL id="/org/export" var="orgExport"/>
<portlet:resourceURL id="/org/import" var="orgImport"/>
<portlet:resourceURL id="/org/delete/user" var="orgDeletePerson"/>
<portlet:resourceURL id="/hg/org/move/object" var="moveObject"/>
<portlet:resourceURL id="/hg/org/move/org" var="moveObjectorg"/>
<portlet:resourceURL id="/org/tree" var="orgTreeUrl" />

<head>
    <%--     <link rel="stylesheet" href="${basePath}/css/common.css?v=5"/> --%>
    <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5" /> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>
    <%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.min.js"></script>--%>
    <%--     <script src="${basePath}/js/common.js?v=5"></script> --%>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>

<%--    <script src="${basePath}/js/pagination.js"></script>--%>

    <style>

        @media (min-width: 768px) {
            .main_content .min_width_1200 .nav_list .party_organization_list .height_auto {
                height: auto;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list > li {
                overflow: hidden;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li {
                cursor: pointer;
                height: 40px;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li .first_menu {
                /*padding-bottom: 5px;*/
                border-bottom: none;
                color: #333;
                font-size: 16px;
                line-height: 48px;
                padding: 0 20px;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li .dropdown_icon {
                width: 9px;
                margin: 5px 0;
                cursor: pointer;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li {
                margin: 12px 0;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li > a {
                padding-left: 44px;
                box-sizing: border-box;
                font-size: 14px;
                width: 100%;
                height: 34px;
                line-height: 34px;
                display: inline-block;
                text-decoration: none;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu a{
                font-size: 14px;
                padding-left: 74px;
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
            .main_content .min_width_1200 .content_info .operation_bar {
                height: 34px;
                margin-top: 36px;
                color: #333;
            }

            .main_content .min_width_1200 .content_info .operation_bar div {
                display: inline-block;
            }

            .main_content .min_width_1200 .content_info .operation_bar .select_choice {
                cursor: pointer;
                line-height: 34px;
            }

            .main_content .min_width_1200 .content_info .operation_bar .select_choice img {
                width: 18px;
            }

            .main_content .min_width_1200 .content_info .operation_bar .btn_group button {
                margin: 0 5px;
            }

            .main_content .min_width_1200 .content_info .operation_bar .time_select {
                float: right;
            }

            .main_content .min_width_1200 .content_info .table_info tr td:nth-child(1) img {
                width: 18px;
                margin-right: 40px;
                cursor: pointer;
            }
        }


        .main_content .min_width_1200 .nav_list .party_organization_list .dropdown_up {
            transform: rotate(-90deg);
        }

        .main_content .min_width_1200 .nav_list .party_organization_list .dropdown_down {
            transform: none;
        }

        .main_content .min_width_1200 .nav_list {
            float: left;
            width: 14.16vw;
            height: 83.4vh;
            background: #EAEEF5;
            overflow-x: hidden;
            overflow-y: auto;
        }
        .main_content .min_width_1200 .nav_list .party_organization_list .silde_more


        @media (min-width: 768px) {
            .main_content .min_width_1200 .nav_list .party_organization_list .second_menu .third_menu a {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                padding-left: 74px;
            }

            #title {
                max-width: 350px;
                display: inline-block;
                line-height: 24px;
            }

            .content_title {
                padding: 40px 15px 20px 15px !important;
            }

            .main_content.full_screen {
                background: #edf1f2;
                /*     padding-top: 20px; */
            }

            .portlet-content {
                padding: 0 0;
            }

            .main_content .min_width_1200 .content_info .table_info tr td:nth-child(1) img {
                width: 18px;
                margin-right: 10px;
                cursor: pointer;
            }

            .content_table td:nth-child(n+2), .content_table th:nth-child(n+2) {
                /*min-width: 130px;*/
                /* font-size: 18px; */
                padding: 5px 15px !important;
                height: 48px;
            }
        }
        .content_table th{
            font-size: 16px;
            padding: 10px;
        }
        .content_table tr:nth-child(2n){
            background: #FBFCFE;
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
            z-index: 100000;
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
            margin-right: 20px;
            line-height: 33px;
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
        .second_menu > li > a{
            height: 20px;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .party_organization_list a{
            color: #333;
        }
        .member_container.bg_white_container{
            overflow: hidden;
        }
        .member_container .table_outer_box{
            height: calc(100% - 118px);
        }

        #searchForm .layui-form-item .layui-inline .layui-form-label{
            width:120px;
        }
        #treeForm .layui-form-item .layui-inline .layui-form-label{
            width:120px;
        }
        #treeForm .layui-form-item .layui-inline .orgTree{
            width:240px;
        }
        #searchForm .layui-form-item .layui-inline .memberType{
            width:100px;
        }
        #searchForm .layui-form-item .layui-inline .keyword{
            width:300px;
            margin-right: 0px;
        }
        .party_manage_page .layui-tab-content{
            height: calc(100% - 40px);
            overflow-y: auto;
            background: #fff;
        }
        .party_manage_page .layui-tab-title{
            margin-bottom: 0px;
        }
        .table_outer_box > table thead, tbody tr{
            width:auto;
        }
        .table_outer_box .layui-table td .laytable-cell-checkbox input{
            display: none;
        }
        .table_outer_box .layui-table td .laytable-cell-checkbox .layui-form-checkbox i{
            font-family: -webkit-body !important;
        }
    </style>
    <script type="text/javascript">

        $(function () {
            layui.config({
                base: '${basePath}/js/layui/module/'
            }).extend({
                treeSelect: 'treeSelect/treeSelect'
            });
            layui.use(['treeSelect','form','layer','table','element'], function () {
                var treeSelect= layui.treeSelect,
                    layer = layui.layer,
                    form = layui.form,
                    table = layui.table,
                    element = layui.element;
                var checkedNode = null;
                var isHistory = false;
                element.on('tab(tabMemberType)', function(elem){
                    if(elem.index == 0){
                        isHistory = false;
                        renderTable();
                    }else{
                        isHistory = true;
                        renderTable();
                    }
                });

                renderTree();
                form.on('submit(searchForm)', function(data){
                    renderTable()
                })
                function renderTable(){
                   var  where = {
                       id: checkedNode.id
                       , memberType: $("#searchForm select[name=memberType]").val()
                       , history:isHistory==false?'0':'1'
                       , keyword: $("#searchForm input[name=keyword]").val()
                   };
                    table.render({
                        elem: '#memberTable'
                        ,height: 560
                        ,where: where
                        ,url: '${orgMember}'//数据接口
                        ,page: true //开启分页
                        ,cols: [[
                            {type: 'checkbox',fixed: 'left'}
                            ,{field: 'member_name', title: '姓名', width:120}
                            ,{field: 'member_sex', title: '性别', width:120}
                            ,{field: 'member_identity', title: '公民身份证', minWidth:240}
                            ,{field: 'member_phone_number', title: '联系电话', width:160}
                            ,{field: 'member_type', title: '党员类型', width:120}
                            ,{field: 'historic',fixed: 'right', title: '操作', width:150, align:'center', toolbar: '#tableTool'}
                        ]]
                    });
                    //监听事件
                    table.on('tool(memberTable)', function(obj){
                        //var checkStatus = table.checkStatus(obj.config.id);
                        switch(obj.event){
                            case 'detail':
                                //layer.msg('添加');
                                break;
                            case 'delete':
                                //layer.msg('删除');
                                break;
                            case 'edit':
                                window.location.href = '/addperson?userId=' + obj.data.member_identity;
                                break;
                        };
                    });
                }
                function renderTree(){
                    treeSelect.destroy('orgTree');
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
                            checkedNode = d.current;
                            $("#org-path").empty();
                            $("#org-path").append(getPathHtml(checkedNode));
                            renderTable();
                            renderButtons();

                        },
                        // 加载完成后的回调函数
                        success: function (d) {
                            if(checkedNode == null || checkedNode == undefined ){
                                checkedNode = d.data[0];
                            }
                            treeSelect.checkNode('orgTree', checkedNode.id);
                            $("#org-path").empty();
                            $("#org-path").append(getPathHtml(checkedNode));
                            renderTable();
                            renderButtons();
                        }
                    });
                }
                function renderButtons(){
                    var history = $("#searchForm select[name=history]").val();
                    if(history){
                        $("#orgImport").hide();
                        $("#orgExport").hide();
                        $("#addPerson").hide();
                        $("#delete").hide();
                        $("#memberImport").hide();
                        $("#memberExport").hide();
                    }else{
                        var org_type = checkedNode.data.org_type;
                        if ('branch' == org_type) {
                            $("#orgImport").hide();
                            $("#orgExport").hide();
                            $("#addPerson").show();
                            $("#delete").show();
                            $("#memberImport").show();
                            $("#memberExport").show();
                        } else {
                            $("#orgImport").hide();
                            $("#orgExport").hide();
                            $("#addPerson").hide();
                            $("#delete").hide();
                            $("#memberImport").show();
                            $("#memberExport").show();
                        }
                    }
                }
                function getPathHtml(node) {
                    var pathHtml = '<a  href="javascript:;" >'+node.name+'</a>';
                    if(node.parentTId != null){
                        var pNode = node.getParentNode();
                        pathHtml = getPathHtml(pNode)+'<span lay-separator="">></span>'+pathHtml;
                    }
                    return pathHtml;
                }

                function orgMember(pageNow, orgId, history) {

                    $.post('${orgMember}', {orgId: orgId, pageNow: pageNow, history: history}, function (res) {
                        if (res.result) {
                            var members = res.data.list;
                            var isHis = 'historic_root' == history;
                            var trs = detail(members, isHis);

                            var pageTotal = parseInt(res.data.totalRow);//总页数
                            $('table.custom_table').html(trs);
                            if (isHis) {
                                $('.table_content > .btn_group').hide();
                            }else{
                                $('.table_content > .btn_group').show();
                            }
                            if ('secondary' != '${org_type}') {
                                $('.changePerson').hide();
                            }
                            $('.select_all').attr("src", "/images/not_check_icon.png");
                            new Page({
                                num: Math.ceil(pageTotal / 10), //页码数
                                startnum: pageNow, //指定页码
                                elem: $('#status-pager'), //指定的元素
                                callback: function (n) { //回调函数
                                    orgMember(n, orgId, history);
                                }
                            });
                        } else {
                            /* alert('!?'); */
                        }
                    });
                }

                $('#upload-block [type="file"]').change(function () {
                    $('#upload-block [type="submit"]').click();
                })

                function orgImportProc(button) {
                    if (!orgId) {
                        alert('必须指定一个组织结点');
                        return;
                    }
                    $('#upload-block [name="orgId"]').val(orgId);
                    $('#upload-block [name="type"]').val('org');
                    $('#upload-block [type="file"]').click();
                }

                function orgExportProc() {
                    if (!orgId) {
                        alert('必须指定一个组织结点');
                        return;
                    }
                    window.location.href = '${orgExport}&type=org&orgId=' + orgId + '&orgName=' + $('#title').text() + '&ishistory=' + ishistory;

                }

                function memberImportProc(button) {
                    if (!orgId) {
                        alert('必须指定一个组织结点');
                        return;
                    }
                    $('#upload-block [name="orgId"]').val(orgId);
                    $('#upload-block [name="type"]').val('member');
                    $('#upload-block [type="file"]').click();
                }

                function memberExportPorc() {
                    if (!orgId) {
                        alert('必须指定一个组织结点');
                        return;
                    }
                    window.location.href = '${orgExport}&type=member&orgId=' + orgId + '&orgName=' + $('#title').text() + '&ishistory=' + ishistory;
                }

                $('#searchForm').on('click', 'button', function () {
                    var $button = $(this);
                    switch ($button.attr('id')) {
                        case 'memberExport':
                            memberExportPorc($button);
                            break;
                        case 'memberImport':
                            memberImportProc($button);
                            break;
                        case 'orgExport':
                            orgExportProc($button);
                            break;
                        case 'orgImport':
                            orgImportProc($button);
                            break;
                        default:
                            break;
                    }
                });
                /* 单击选择按钮
                $(".clickImg").click(function(){*/
                $("table.custom_table").on("click", ".clickImg", function () {
                    var sum = 0;
                    if ($(this).attr("src") == "/images/checked_icon.png") {
                        $(this).attr("src", "/images/not_check_icon.png");
                    } else {
                        $(this).attr("src", "/images/checked_icon.png");
                    }
                    $(".clickImg").each(function () {
                        if ($(this).attr("src") == "/images/checked_icon.png") {
                            sum += 1;
                        }
                    })
                    if ($(".clickImg").length == sum) {
                        $(".select_all").attr("src", "/images/checked_icon.png");
                    } else {
                        $(".select_all").attr("src", "/images/not_check_icon.png");
                    }

                });

                $(".custom_table").on('click', ".select_all", function () {
                    $(this).attr("src", "/images/checked_icon.png");
                    $(".clickImg").attr("src", "/images/checked_icon.png");
                });
                /* 删除*/
                $(".delete_graft").click(function () {
                    var imgs = $(".table_info img[src='/images/checked_icon.png']");
                    var resourcesId = new Array("");
                    for (var i = 0; i < imgs.length; i++) {
                        var resourceId = $(imgs[i]).prev().val();
                        resourcesId.push(resourceId);
                    }
                    var resources = resourcesId.join(",").substring(1) + "";
                    var url = "${orgDeletePerson}";
                    if (!resources) {
                        /* showConfirm("请选择删除对象！"); */
                        alert("请选择删除对象！");
                        return;
                    }
                    $.hgConfirm("提示", "确定删除吗？");
                    $("#hg_confirm").modal("show");
                    $("#hg_confirm .btn_main").click(function () {
                        $("#hg_confirm").modal("hide");
                        var orgId = $(".third_menu_on").attr("id");
                        $.ajax({
                            url: url,
                            data: {"<portlet:namespace/>userIds": resources, "<portlet:namespace/>orgId": orgId},
                            dataType: "json",
                            success: function (succee) {
                                if (succee.state == true) {
                                    $(".table_info img[src='/images/checked_icon.png']").parent().parent().remove();
                                    alert("删除成功");
                                    orgMember(1, orgId, 'current_root');
                                } else {
                                    alert("删除失败");
                                }
                            }
                        });

                    })

                });
                $("table.custom_table").on("click", ".changePerson", function () {
                    var personId = $(this).parent().parent().prev().prev().prev().html()
                    var personName = $(this).parent().parent().prev().prev().prev().prev().prev().text()
                    var $this = $(this);
                    var con = confirm("你确定转移人员 ")
                    var userId = personId;
                    /*   var orgId = $(".third_menu_on").attr("id");   */
                    var orgId = $(this).next().next().val();
                    if (con) {
                        $("#model_box").show();
                        $(".personName").text("姓名:" + personName);
                        $(".personName").next().val(personId);
                        $.ajax({
                            url: "${moveObject}",
                            data: {"<portlet:namespace/>orgId": orgId},
                            dataType: "json",
                            success: function (res) {
                                $("#objectGroup").empty();
                                for (n in res) {
                                    var option = $('<option value="' + res[n].org_id + '">' + res[n].org_name + '</option>');
                                    $("#objectGroup").append(option)
                                }
                            }
                        });
                    }
                });
                $(".cancal").click(function () {
                    $(".personName").text("");
                    $(".personName").next().val("");
                    $("#model_box").hide();
                });
                $(".close").click(function () {
                    $(".personName").text("");
                    $(".personName").next().val("");
                    $("#model_box").hide();
                });

                $("#add_submit").click(function () {
                    var userId = $(".personName").next().val();
                    var moveToOrgId = $("#objectGroup").val();
                    var orgId = $(".third_menu_on").attr("id");
                    $.ajax({
                        url: "${moveObjectorg}",
                        data: {"<portlet:namespace/>moveToOrgId": moveToOrgId, userId: userId},
                        dataType: "json",
                        success: function (res) {
                            if (res.state == true) {
                                orgMember(1, orgId, 'current_root');
                                $(".personName").text("");
                                $(".personName").next().val("");
                                $("#model_box").hide();
                                alert("移动成功");
                            } else {
                                alert("移动失败");
                            }
                        }
                    });
                });

                $('#current_root .first_menu').click();
            })
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
                    <a href="javascript:;">党员信息管理</a>
                </span>
            </div>
            <div class="party_manage_content content_form content_info">
                <div class="content_table_container party_table_container">
                    <div class="layui-tab layui-tab-card" lay-filter="tabMemberType">
                        <ul class="layui-tab-title">
                            <li class="layui-this">在校党员</li>
                            <li>历史党员</li>
                        </ul>
                        <div class="layui-tab-content">
                            <form class="layui-form" id="treeForm">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label">请选择组织:</label>
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
                            </div>
                            <div class="table_content bg_white_container member_container">
                                <form class="layui-form" id="searchForm">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <%--                                    <label class="layui-form-label">党员类型:</label>
                                                                                <div class="layui-input-inline memberType">
                                                                                    <select type="text" name="memberType" >
                                                                                        <option value="" selected>全部</option>
                                                                                        <option value="正式党员">正式党员</option>
                                                                                        <option value="预备党员">预备党员</option>
                                                                                    </select>
                                                                                </div>--%>
                                            <div class="layui-input-inline keyword">
                                                <input type="text" name="keyword"  placeholder="请输入名字、工号、身份证号关键字" class="layui-input">
                                            </div>
                                            <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                                                <button id="delete" class="layui-btn layui-btn layui-btn-danger">删除人员</button>
                                                <button id="addPerson" class="layui-btn layui-btn-primary">添加人员</button>
                                                <button id="orgImport" class="layui-btn layui-btn-primary">党组织导入</button>
                                                <button id="orgExport" class="layui-btn layui-btn-primary">党组织导出</button>
                                                <button id="memberImport" class="layui-btn layui-btn-primary">人员名册导入</button>
                                                <button id="memberExport" class="layui-btn layui-btn-primary">人员名单导出</button>
                                                <div id="upload-block" style="display: none;">
                                                    <form action="${orgImport}" method="post" target="uploadTarget"
                                                          enctype="multipart/form-data">
                                                        <input type="file" name="excel">
                                                        <input name="orgId">
                                                        <input name="type">
                                                        <input type="submit">
                                                        <iframe name="uploadTarget"></iframe>
                                                    </form>
                                                </div>
                                        </div>
                                    </div>
                                </form>
                                <div class="table_outer_box" style="margin-top: 20px;">
                                    <table id="memberTable" lay-filter="memberTable"></table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="tableTool">
    <%--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>--%>
    {{#  if(d.historic == false){ }}
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    {{#  } }}
</script>
<script type="text/javascript">
    $(".custom_table").on("click", "button.delete", function () {
        console.log($(this).html());
        var personId = $(this).parent().parent().prev().prev().prev().html()
        var $this = $(this);
        var con = confirm("你确定删除人员 ：" + personId)
        var userId = personId;
        var orgId = $(".third_menu_on").attr("id");
        if (con) {
            $.ajax({
                url: "${orgDeletePerson}",
                data: {userId: userId, orgId: orgId},
                dataType: "json",
                success: function (res) {
                    if (res.state == true) {
                        $this.parent().parent().parent().remove();
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        }
    });

    $("#addPerson").click(function () {
        var seconedName = $(".second_menu_on > a").text();
        var orgId = $(".third_menu_on").attr("id");
        var orgName = $(".third_menu_on > a").text();
        if (seconedName && orgId && orgName) {
            window.location.href = '/addperson?seconedName=' + seconedName + '&orgId=' + orgId + '&orgName=' + orgName;
        } else {
            alert('必须指定一个支部结点');
            return;
        }
    });
    $("#memberImport").click(function () {
        alert("请先进行人员名册导出，在导出的人员信息表上进行人员信息编辑，然后再进行导入操作！");
    });
</script>
<!-- 弹窗 -->
<div id="model_box">
    <div id="model">
        <form action="">
            <div class="title_box">
                <span class="title">组织关系转移</span>
                <span class="close">×</span>
            </div>
            <div class="name_box">
                <div>
                    <span class="personName">姓名：</span>
                    <input type="hidden">
                </div>
                <div>
                    <span>转移组织：</span>
                    <select class="form-control" name="objectGroup" id="objectGroup" style="width: 70%; height: 50%;">
                        <option value="">-请选择-</option>

                    </select>
                </div>
            </div>
            <button type="button" class="cancal btn btn-default">取消</button>
            <button type="button" id="add_submit" class="btn btn_main">提交</button>
        </form>
    </div>
</div>
</body>