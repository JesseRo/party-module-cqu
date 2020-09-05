<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/member" var="orgMember"/>
<portlet:resourceURL id="/org/export" var="orgExport"/>
<portlet:resourceURL id="/org/import" var="orgImport"/>
<portlet:resourceURL id="/org/delete/user" var="orgDeletePerson"/>
<portlet:resourceURL id="/org/user/recovery" var="orgRecoveryPerson"/>
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
        .party_manage_page .party_manage_content .party_table_container{
            width:100%;
        }


        .table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        .layui-form-checked[lay-skin=primary] i {
            border-color: #FFB800 !important;
            background-color: #FFB800;
            color: #fff;
        }
        th, tr{
            text-align:center !important;
        }
        .layui-table-page-center{
            text-align: center;
        }
        #recoveryModal .layui-form-label{
            float: left;
            display: block;
            padding: 9px 0px;
            font-weight: 400;
            line-height: 20px;
            text-align: left;
        }
        #recoveryModal .layui-form-item .layui-input-inline{
            width: 280px;
            margin-right: 0px;
        }
        .layui-layer-page.recovery-modal-skin .layui-layer-content {
            overflow: visible;
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
                var recoveryCheckedNode = null;
                var recoveryModal;
                var isHistory = false;
                var pageInfo = {
                    page:1,
                    size:10
                };
                var recoveryId;
                element.on('tab(tabMemberType)', function(elem){
                    if(elem.index == 0){
                        isHistory = false;
                        renderTable(1,pageInfo.size);
                        renderButtons();
                    }else{
                        isHistory = true;
                       renderTable(1,pageInfo.size);
                        renderButtons();
                    }
                });

                renderTree();
                form.on('submit(searchForm)', function(data){
                    renderTable(1,pageInfo.size);
                })
                form.on('submit(recoveryForm)', function (data) {
                    var url = "${orgRecoveryPerson}";
                    $.ajax({
                        url:url,
                        data:{orgId:recoveryCheckedNode.data.org_id,userId:recoveryId},
                        dataType:'json',
                        async:false,
                        success:function(res){
                            if(res && res.code == 200){
                                layer.msg("恢复成功。");
                                renderTable(pageInfo.page,pageInfo.size);
                                layer.close(recoveryModal);
                            }else{
                                layer.msg("恢复失败。");
                            }
                        }
                    });
                    return false;
                });
                function renderTable(page,size){
                   var  where = {
                       id: checkedNode.id
                       , memberType: $("#searchForm select[name=memberType]").val()
                       , history:isHistory==false?'0':'1'
                       , keyword: $("#searchForm input[name=keyword]").val()
                   };
                   var cols = [[
                       {type: 'checkbox', width:'8%'}
                       ,{field: 'member_name', align:'center', width:'12%',title: '姓名',templet: function(d) {
                               return '<a href="/memberDetail?userId='+d.member_identity+'" >' + d.member_name + '</a>';
                           }
                           }
                       ,{field: 'member_sex', align:'center', width:'12%',title: '性别'}
                       ,{field: 'member_identity', align:'center', width:'12%',title: '公民身份证'}
                       ,{field: 'member_phone_number', align:'center', width:'12%',title: '联系电话'}
                       ,{field: 'member_join_date', align:'center', width:'12%', title: '入党时间'}
                       ,{field: 'member_type', align:'center', width:'12%', title: '党员类型'}
                       ,{field: 'historic', title: '操作', width:'20%', align:'center',toolbar: '#tableTool'}
                   ]];
                   var ins = table.render({
                        elem: '#memberTable'
                        ,where: where
                        ,height:450
                        ,url: '${orgMember}'//数据接口
                        ,id:"queryList"
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
                    table.on('tool(memberTable)', function(obj){
                        switch(obj.event){
                            case 'delete':
                                deleteMember(obj.data.member_identity);
                                break;
                            case 'edit':
                                window.location.href = '/addperson?userId=' + obj.data.member_identity;
                                break;
                            case 'recovery':
                                recoveryMember(obj.data.member_identity);
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
                            renderTable(1,pageInfo.size);
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
                            renderTable(1,pageInfo.size);
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
                    layer.confirm('您确定将此条记录移入历史档案库吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        layuiModal.prompt("备注", "", function (value) {
                            $.ajax({
                                url: '${orgDeletePerson}',
                                data: {"userIds": userId, extra: value},
                                dataType: "json",
                                success: function (succee) {
                                    if (succee.state) {
                                        layer.msg("移入历史档案库成功。");
                                        renderTable(pageInfo.page,pageInfo.size);
                                    } else {
                                        layer.msg("移入历史档案库失败！");
                                    }
                                }
                            });
                        })
                    });
                }
                function recoveryMember(userId){
                    recoveryId = userId;
                    renderRecoveryTree();
                }
                function renderRecoveryTree(){
                    treeSelect.destroy('recoveryTree');
                    treeSelect.render({
                        // 选择器
                        elem: '#recoveryTree',
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
                            recoveryCheckedNode = d.current;
                            if(d.current.check_Child_State == 0){
                                $("#recoveryTree-div .layui-treeSelect.layui-unselect.layui-form-select").addClass("layui-form-selected");
                                $("#recoveryTree-div .layui-treeSelect .layui-select-title input").val("");
                                $("#"+d.current.tId+"_switch").click();
                            }else{
                                $("#trecoveryTree-div .layui-treeSelect.layui-unselect.layui-form-select").removeClass("layui-form-selected");
                            }
                        },
                        // 加载完成后的回调函数
                        success: function (d) {
                          recoveryModal = layer.prompt({
                                type: 1,
                                btn: 0,
                                skin: 'recovery-modal-skin',
                                content: $("#recoveryModal"),
                            });
                           /* if(recoveryCheckedNode == null || recoveryCheckedNode == undefined ){
                                recoveryCheckedNode = d.data[0];
                            }
                            treeSelect.checkNode('recoveryTree', recoveryCheckedNode.id);*/
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

                function memberExportProc() {
                    var ishistory = isHistory?1:0;
                    window.location.href = '${orgExport}&type=member&orgId=' + checkedNode.data.org_id + '&orgName=' + $('#title').text() + '&ishistory=' + ishistory;
                }

                $('#searchForm').on('click', 'button', function () {
                    var $button = $(this);
                    switch ($button.attr('id')) {
                        case 'memberExport':
                            memberExportProc($button);
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
                    var selectData = layui.table.checkStatus('queryList').data;
                    if (selectData.length<1) {
                        layer.alert("请选择删除对象！");
                        return;
                    }
                    var userIds = new Array();
                    for(var i =0;i<selectData.length;i++){
                        userIds.push(selectData[i].member_identity);
                    }
                    deleteMember(userIds.join(","));
                });

                $("#addPerson").click(function () {
                    window.location.href = '/addperson?orgId=' + checkedNode.data.org_id;
                });
                // $("#memberImport").click(function () {
                //     alert("请先进行人员名册导出，在导出的人员信息表上进行人员信息编辑，然后再进行导入操作！");
                // });
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
                    <a href="javascript:;">党员管理</a>
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
                            <div class="table_content bg_white_container">
                                <form class="layui-form" id="searchForm">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <div class="layui-input-inline keyword">
                                                <input type="text" name="keyword"  placeholder="请输入名字、工号、身份证号关键字" class="layui-input">
                                            </div>
                                            <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                                                <button type="button" id="delete" class="layui-btn layui-btn layui-btn-danger">删除人员</button>
                                                <button type="button" id="addPerson" class="layui-btn layui-btn-primary">添加人员</button>
<%--                                                <button type="button" id="orgImport" class="layui-btn layui-btn-primary">党组织导入</button>--%>
                                                <button type="button" id="orgExport" class="layui-btn layui-btn-primary">党组织导出</button>
                                                <button type="button" id="memberImport" class="layui-btn layui-btn-primary">人员名册导入</button>
                                                <button type="button" id="memberExport" class="layui-btn layui-btn-primary">人员名单导出</button>

                                        </div>
                                    </div>
                                </form>
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
                                <table id="memberTable" lay-filter="memberTable"></table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <c:if test="${role=='organization'}">
        <div style="display: none" id="recoveryModal">
            <form class="layui-form" action="">
                <input type="hidden" class="layui-layer-input"  name="userID" value="1">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label layui-required">组织机构:</label>
                        <div class="layui-input-inline orgTree" id="recoveryTree-div">
                            <input type="text" name="recoveryTree" id="recoveryTree" lay-filter="recoveryTree" placeholder="请选择组织" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-layer-btn layui-layer-btn-">
                    <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="recoveryForm">确定</a>
                    <a class="layui-layer-btn1">取消</a>
                </div>
            </form>
        </div>
    </c:if>
    <script type="text/html" id="tableTool">
        {{#  if(d.historic == false){ }}
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">移入历史档案库</a>
        {{#  } }}
    <c:if test="${role=='organization'}">
        {{#  if(d.historic != false){ }}
        <a class="layui-btn layui-btn-xs" lay-event="recovery">恢复</a>
        {{#  } }}
    </c:if>
    </script>
</body>
