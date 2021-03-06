<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/hg/org/meetingStatistics" var="statistics"/>
<portlet:resourceURL id="/hg/org/meetingStatistics/sms" var="sms"/>


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

            .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu a {
                font-size: 14px;
                padding-left: 74px;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu a:hover {
                background-color: #fff;
                color: #333;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list .second_menu_on .third_menu_on a {
                background-color: #fff;
                color: #333;
            }

            .main_content .min_width_1200 .nav_list .party_organization_list li .second_menu > li > a:hover .second_menu_on {
                border-left-color: #999;
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

        .content_table th {
            font-size: 16px;
            padding: 10px;
        }

        .content_table tr:nth-child(2n) {
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

        .second_menu > li > a {
            height: 20px;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .party_organization_list a {
            color: #333;
        }

        #searchForm .layui-form-item .layui-inline .layui-form-label {
            width: 120px;
        }

        #treeForm .layui-form-item .layui-inline .layui-form-label {
            width: 120px;
        }

        #treeForm .layui-form-item .layui-inline .orgTree {
            width: 240px;
        }

        #searchForm .layui-form-item .layui-inline .memberType {
            width: 100px;
        }

        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }

        .party_manage_page .layui-tab-content {
            height: calc(100% - 40px);
            overflow-y: auto;
            background: #fff;
        }

        .party_manage_page .layui-tab-title {
            margin-bottom: 0px;
        }

        .layui-tab.layui-tab-card {
            height: calc(100% - 20px);
        }

        .party_manage_page .party_manage_content .party_table_container {
            width: 100%;
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

        th, tr {
            text-align: center !important;
        }

        .layui-table-page-center {
            text-align: center;
        }
    </style>
    <portlet:resourceURL id="/org/tree" var="orgTreeUrl"/>

    <script type="text/javascript">
        var startDate, endDate;
        $(function () {
            layui.config({
                base: '${basePath}/js/layui/module/'
            }).extend({
                treeSelect: 'treeSelect/treeSelect'
            });
            var statisticsTable;
            var checkedNode = null;
            layui.use(['treeSelect', 'table'], function () {
                var table = layui.table;
                var treeSelect = layui.treeSelect;

                renderTree();

                function renderTree() {
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
                        click: function (d) {
                            checkedNode = d.current;
                            $("#org-path").empty();
                            $("#org-path").append(getPathHtml(checkedNode));
                            renderOrgInfo();

                        },
                        // 加载完成后的回调函数
                        success: function (d) {
                            if (!checkedNode) {
                                checkedNode = d.data[0];
                            }
                            treeSelect.checkNode('orgTree', checkedNode.id);
                            $("#org-path").empty();
                            $("#org-path").append(getPathHtml(checkedNode));
                            renderOrgInfo();
                        }
                    });
                }

                function getPathHtml(node) {
                    var pathHtml = '<a  href="javascript:;" >' + node.name + '</a>';
                    if (node.parentTId != null) {
                        var pNode = node.getParentNode();
                        pathHtml = getPathHtml(pNode) + '<span lay-separator="">></span>' + pathHtml;
                    }
                    return pathHtml;
                }

                function renderOrgInfo() {
                    if (!statisticsTable) {
                        statisticsTable = table.render({
                            elem: '#memberTable',
                            url: '${statistics}', //数据接口
                            method: 'post',
                            where: {search: $('[name=keyword]').val(), orgId: checkedNode.id},
                            page: {
                                limit: 10,   //每页条数
                                limits: [],
                                prev: '&lt;上一页',
                                next: '下一页&gt;',
                                groups: 4,
                                theme: '#FFB800'
                            },
                            cols: [[ //表头
                                {field: 'org_id', title: 'id', hide: true},
                                {field: 'org_name', title: '组织名称', width: '15%'},
                                {field: 'org_secretary', title: '党委书记', width: '15%'},
                                {field: 'branch_count', title: '开展支部数', width: '14%'},
                                {field: 'plan_count', title: '开展会议次数', width: '14%'},
                                {field: 'member_count', title: '参会人数', width: '14%'},
                                {field: 'leader_count', title: '参会领导', width: '14%'},
                                {field: 'operate', title: '操作', width: '14%', toolbar: '#retentionBtns'}
                            ]],
                            parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
                                startDate = res.start;
                                endDate = res.end;
                                $('#date_range').val(startDate + " - " + endDate);
                                return res;
                            }
                        });
                        $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
                        $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
                    } else {
                        reloadTable()
                    }
                }
            });

            layui.use('laydate', function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#date_range'
                    , range: '-',
                    done: function (value, date, e) {
                        console.log(value); //得到日期生成的值，如：2017-08-18
                        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        startDate = toDateStr(date);
                        endDate = toDateStr(e);
                    }
                });
            })
            function toDateStr(date){
                if (date.year) {
                    return date.year + '-' + date.month + '-' + date.date;
                }else {
                    return "";
                }
            }

            function reloadTable() {
                statisticsTable.reload({
                    where: {search: $('[name=keyword]').val(), orgId: checkedNode.id, start: startDate, end: endDate},
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
                $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            }

            $('#transportSearchBtn').on('click', reloadTable);


        });
        function sms(e){
            var orgId = $(e).attr("bid");
            var num = $(e).attr("num");
            $.post("${sms}", {id: orgId, start: startDate, end: endDate, template: true, num: num}, function (res) {
                if (res.result) {
                    layuiModal.confirm("是否发送通知短信：\n" + res.data, function () {
                        $.post("${sms}", {id: orgId, start: startDate, end: endDate, num: num}, function (res) {
                            if (res.result) {
                                layuiModal.alert("已发送短信通知");
                            }
                        })
                    });
                }
            })
        }
    </script>
</head>
<script type="text/html" id="retentionBtns">
    <div class="operate_btns">
        <span bid="{{d.org_id}}" num="{{d.plan_count}}" onclick="sms(this)" class="blue_text sms_button" style="cursor: pointer;color: #5160FF; ">短信通知</span>
    </div>
</script>
<div>
    <div class="party_manage_container min_width_1200">
        <div class="party_manage_page">
            <div class="breadcrumb_group">
                当前位置：
                <span class="layui-breadcrumb" lay-separator=">">
                    <a href="javascript:;">数据统计</a>
                    <a href="javascript:;">组织生活统计</a>
                </span>
            </div>
            <div class="party_manage_content content_form content_info" style="padding-top: 0;">
                <div class="content_table_container party_table_container">
                    <div class="table_content bg_white_container" style="padding-top: 20px;">
                        <div class="layui-form">
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label orgTree-label" style="width: 120px;text-align: left;padding: 9px 0;">请选择组织:</label>
                                    <div class="layui-input-inline orgTree">
                                        <input type="text" name="orgTree" id="orgTree" lay-filter="orgTree"
                                               placeholder="请选择组织" class="layui-input">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="breadcrumb_group">
                            当前组织：
                            <span class="layui-breadcrumb" style="visibility: visible;" id="org-path"></span>
                        </div>
                        <form class="layui-form" id="searchForm">
                            <div class="layui-form-item" style="margin-top: 15px;">
                                <div class="layui-inline">
                                    <div class="layui-input-inline" style="margin-left: 20px;height: 40px;">
                                        <input type="text" class="layui-input" id="date_range" placeholder="日期范围">
                                    </div>
                                    <div class="layui-input-inline keyword">
                                        <input type="text" name="keyword" placeholder="搜索党组织"
                                               class="layui-input">
                                    </div>
                                    <button type="button" class="layui-btn layui-btn-warm" lay-submit=""
                                            lay-filter="searchForm" id="transportSearchBtn">
                                        <icon class="layui-icon layui-icon-search"></icon>
                                        搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                        <table id="memberTable" lay-filter="memberTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>