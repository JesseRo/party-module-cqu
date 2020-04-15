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
        .layui-tab.layui-tab-card{
            height: calc(100% - 20px);
        }
        .table_content.bg_white_container.member_container{
            padding-bottom: 0;
        }
        .table_outer_box{
            height: calc(100% - 63px);
        }
        .party_manage_page .party_manage_content .party_table_container{
            width:100%;
        }
        .table_form_content .custom_table + .layui-table-view .layui-form-checked[lay-skin=primary] i{
            font-family: layui-icon!important;
        }
        table input[type=checkbox]{
            display:none !important;
        }
        .layui-laydate-content .layui-laydate-content thead tr {
            display: flex !important;
        }
        .layui-table-fixed.layui-table-fixed-l{
            height: calc(100% - 10px);
        }
        .layui-table-fixed.layui-table-fixed-r{
            height: calc(100% - 10px);
        }
       .table_form_content .custom_table + .layui-table-view .layui-table-box .layui-table-body tr{
            display:table-row !important;
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
                        renderButtons();
                    }else{
                        isHistory = true;
                        renderTable();
                        renderButtons();
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
                   var cols = [[
                       {type: 'checkbox',fixed: 'left'}
                       ,{field: 'member_name', title: '姓名', width:160}
                       ,{field: 'member_sex', title: '性别', width:120}
                       ,{field: 'member_identity', title: '公民身份证', minWidth:200}
                       ,{field: 'member_phone_number', title: '联系电话', width:160}
                       ,{field: 'member_address', title: '家庭住址', width:160}
                       ,{field: 'member_type', title: '党员类型', width:120}
                       ,{field: 'historic', title: '操作', width:250, align:'center', toolbar: '#tableTool'}
                   ]];
                    table.render({
                        elem: '#memberTable'
                        ,where: where
                        ,url: '${orgMember}'//数据接口
                        ,page: {
                                limit:10,   //每页条数
                                limits:[],
                                prev:'&lt;上一页',
                                next:'下一页&gt;',
                                groups:4}
                        ,cols: cols
                    });
                    //监听事件
                    table.on('tool(memberTable)', function(obj){
                        //var checkStatus = table.checkStatus(obj.config.id);
                        switch(obj.event){
                            case 'delete':
                                deleteMember(obj.data.member_identity);
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
                    var history = isHistory;
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
                function deleteMember(userId){
                    layer.confirm('您确认删除吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.ajax({
                            url: '${orgDeletePerson}',
                            data: {"userIds": userId},
                            dataType: "json",
                            success: function (succee) {
                                if (succee.state == true) {
                                    layer.msg("删除成功");
                                    orgMember(1, checkedNode.data.org_id, 'current_root');
                                } else {
                                    layer.msg("删除失败");
                                }
                            }
                        });
                    });
                }

                function orgMember(pageNow, orgId, history) {

                    $.post('${orgMember}', {orgId: orgId, pageNow: pageNow, history: history}, function (res) {
                        if (res.result) {
                            var members = res.data.list;
                            var isHis = 'historic_root' == history;
                            var trs = detail(members, isHis);

                            var pageTotal = parseInt(res.data.totalRow);//总页数
                            $('table.custom_table').html(trs);
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
                    $('#upload-block [name="orgId"]').val(checkedNode.data.org_id);
                    $('#upload-block [name="type"]').val('org');
                    $('#upload-block [type="file"]').click();
                }

                function orgExportProc() {
                    var ishistory = isHistory?1:0;
                    window.location.href = '${orgExport}&type=org&orgId=' + checkedNode.data.org_id + '&orgName=' + $('#title').text() + '&ishistory=' + ishistory;

                }

                function memberImportProc(button) {
                    $('#upload-block [name="orgId"]').val(checkedNode.data.org_id);
                    $('#upload-block [name="type"]').val('member');
                    $('#upload-block [type="file"]').click();
                }

                function memberExportPorc() {
                    var ishistory = isHistory?1:0;
                    window.location.href = '${orgExport}&type=member&orgId=' + checkedNode.data.org_id + '&orgName=' + $('#title').text() + '&ishistory=' + ishistory;
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

                /* 删除*/
                $("#delete").click(function () {
                    var checkStatus = table.checkStatus(obj.config.id);
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

                $("#addPerson").click(function () {
                    window.location.href = '/addperson?orgId=' + checkedNode.data.org_id;
                });
                $("#memberImport").click(function () {
                    alert("请先进行人员名册导出，在导出的人员信息表上进行人员信息编辑，然后再进行导入操作！");
                });
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
                                            <div class="layui-input-inline keyword">
                                                <input type="text" name="keyword"  placeholder="请输入名字、工号、身份证号关键字" class="layui-input">
                                            </div>
                                            <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                                                <button type="button" id="delete" class="layui-btn layui-btn layui-btn-danger">删除人员</button>
                                                <button type="button" id="addPerson" class="layui-btn layui-btn-primary">添加人员</button>
                                                <button type="button" id="orgImport" class="layui-btn layui-btn-primary">党组织导入</button>
                                                <button type="button" id="orgExport" class="layui-btn layui-btn-primary">党组织导出</button>
                                                <button type="button" id="memberImport" class="layui-btn layui-btn-primary">人员名册导入</button>
                                                <button type="button" id="memberExport" class="layui-btn layui-btn-primary">人员名单导出</button>
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
                                    <table id="memberTable" lay-filter="memberTable" class="custom_table"></table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
<%--</div>--%>
    <script type="text/html" id="tableTool">
        {{#  if(d.historic == false){ }}
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
        {{#  } }}
    </script>
</body>