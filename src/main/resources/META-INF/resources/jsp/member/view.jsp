<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/member" var="orgMember"/>
<portlet:resourceURL id="/org/export" var="orgExport"/>
<portlet:resourceURL id="/org/import" var="orgImport"/>
<portlet:resourceURL id="/org/delete/user" var="orgDeletePerson"/>
<portlet:resourceURL id="/hg/org/move/object" var="moveObject"/>
<portlet:resourceURL id="/hg/org/move/org" var="moveObjectorg"/>

<head>
    <%--     <link rel="stylesheet" href="${basePath}/css/common.css?v=5"/> --%>
    <%--   <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5" /> --%>
    <link rel="stylesheet" href="${basePath}/css/account_manage.css"/>
    <%-- <script type="text/javascript" src="${basePath}/js/jquery-3.2.1.min.js"></script>--%>
    <%--     <script src="${basePath}/js/common.js?v=5"></script> --%>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/party-info-manage.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>

    <script src="${basePath}/js/pagination.js"></script>

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
    </style>
    <script type="text/javascript">

        $(function () {

            var a;
            var root = ${root};
            var secondaries = ${secondaries};
            var historicSecondaries = ${historicSecondaries};
            var branches = ${branches};
            var historicBranches = ${historicBranches};
            $("#addPerson").hide();
            $(".select_choice").hide();
            $("#delete").hide();
            console.log(root);
            if ('branch' == '${org_type}' || 'secondary' == '${org_type}') {
                $('#historic_root').hide();
                $('.silde_more').hide();
            }

            $("#orgImport").css("display", "none");
            $("#orgExport").css("display", "none");
            $("#memberImport").css("display", "none");
            $("#memberExport").css("display", "none");
            var orgId;
            var ishistory;

            function dd(sad) {

            }

            function generateOrg(secondaries, branchGroups, number) {
                $('#historic_root').attr('org-id', root.org_id);
                $("#current_root .first_menu").find("span").html(root.org_name);
                $('#current_root').attr('org-id', root.org_id);
                if (number > secondaries.length) {
                    number = secondaries.length;
                }
                if (!number) {
                    number = secondaries.length;
                }
                var html = "";
                var i;
                for (i = 0; i < number; i++) {
                    var secondary = secondaries[i];
                    var li;
                    if (secondary.org_id in branchGroups) {
                        li = "<li id='" + secondary.org_id + "'><a href=\"javascript:;\"><span class=\"third_menu_icon third_menu_up\"></span>" + secondary.org_name + "</a><ul class=\"third_menu\">{{branches}}</ul></li>";
                        var branch_lis = "";
                        for (var j in branchGroups[secondary.org_id]) {
                            var branch = branchGroups[secondary.org_id][j];
                            branch_lis += "<li title=" + branch.org_name + " id='{{id}}'><a href=\"javascript:;\">{{name}}</a></li>".replace('{{id}}', branch.org_id).replace('{{name}}', branch.org_name);
                        }
                        li = li.replace('{{branches}}', branch_lis);
                    } else {
                        li = "<li id='" + secondary.org_id + "'><a href=\"javascript:;\">" + secondary.org_name + "</a></li>";

                    }
                    html += li;
                }
                return html;
            }


            function detail(members) {
                var html = "";
                for (var i in members) {
                    var member = members[i];
                    html += " <tr>\n" +
                        "                                <td style=\"text-align:left;padding-left:10px;\">" +
                        "<input type=\"hidden\" value=\"" + member.member_identity + "\"> " +
                        "<img class=\"clickImg\" src=\"/images/not_check_icon.png\"></td>" +
                        "                                <td>" + member.member_name + "</td>\n" +
                        "                                <td>" + member.member_sex + "</td>\n" +
                        "                                <td>" + member.member_identity + "</td>\n" +
                        "                                <td>" + member.member_phone_number + "</td>\n" +
                        "                                <td>" + member.member_type + "</td>\n" +
                        "                                <td>\n" +
                        "                                    <div class=\"btn_group\">\n" +
                        "                                        <button class=\"btn changePerson btn-default\" >组织转移</button>\n" +
                        "                                        <button class=\"btn edit btn-default\" >编辑</button>\n" +
                        "                                        <input type=\"hidden\" value=\"" + member.member_org + "\"> \n" +
                        "                                    </div>\n" +
                        "                                </td>\n" +
                        "                            </tr>";
                }
                return html;
            }

            function orgMember(pageNow, orgId, history) {

                $.post('${orgMember}', {orgId: orgId, pageNow: pageNow, history: history}, function (res) {
                    if (res.result) {
                        var members = res.data.list;
                        var trs = detail(members);

                        var pageTotal = parseInt(res.data.totalRow);//总页数
                        $('tbody.table_info').html(trs);
                        if ('historic_root' == history) {
                            $("tbody.table_info .btn_group").hide();
                            $(".clickImg").hide();
                            $("#delete").hide();
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


            $(".first_menu").click(function () {
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
                $('#second_title').hide();
                $('#second_title').prev('span').hide();
                $('#third_title').hide();
                $('#third_title').prev('span').hide();
                $('#first_title').show().text($(this).text());
                console.log("西南大学党委");
                var orgName = "西南大学党委";
                var history = $(this).parent().attr("id");
                ishistory = history;
                if ('branch' == '${org_type}' || 'secondary' == '${org_type}') {
                    $("#orgImport").css("display", "none");
                    $("#orgExport").css("display", "none");
                    $("#memberImport").css("display", "none");
                    $("#memberExport").css("display", "none");
                } else {
                    orgMember(1, orgName, history);
                    // $("#orgImport").css("display", "inline-block");
                    // $("#orgExport").css("display", "inline-block");
                    $("#orgImport").css("display", "none");
                    $("#orgExport").css("display", "none");
                    $("#memberImport").css("display", "inline-block");
                    $("#memberExport").css("display", "inline-block");
                }
                $("#addPerson").hide();
                $(".select_choice").hide();
                $("#delete").hide();

            });
            //二级菜单下拉
            $(".second_menu").on("click", ">li>a", function () {
                if ($(this).siblings("ul").length == 0) {
                    $(".third_menu li").removeClass("third_menu_on");
                    $(".second_menu>li").removeClass("second_menu_on");
                    $(this).parent("li").addClass("second_menu_on");
                }
                if ($(this).parent("li").hasClass("height_auto")) {
                    $(this).parent("li").removeClass("height_auto").removeClass("second_menu_on");
                } else if ($(this).siblings("ul").length > 0) {
                    $(this).parent("li").addClass("height_auto").addClass("second_menu_on");
                }
                var _target = $(this).find(".third_menu_icon");
                if (_target.hasClass("third_menu_up")) {
                    _target.removeClass("third_menu_up").addClass("third_menu_down");
                } else {
                    _target.removeClass("third_menu_down").addClass("third_menu_up");
                }
                orgId = $(this).parent().attr('id');
                var orgName = $(this).text();
                console.log("orgName=" + orgName);
                $('#second_title').show().text($(this).text());
                $('#second_title').prev('span').show();
                $('#third_title').hide();
                $('#third_title').prev('span').hide();
                $('#first_title').show();
                var history = $(this).parent().parent().parent().attr("id");
                ishistory = history;
                if ('branch' == '${org_type}') {
                    $("#memberImport").css("display", "none");
                    $("#memberExport").css("display", "none");
                } else {
                    orgMember(1, orgId, history);
                    $("#memberImport").css("display", "inline-block");
                    $("#memberExport").css("display", "inline-block");
                }
                $(".select_choice").hide();
                $("#delete").hide();
                $("#addPerson").hide();
                $("#orgImport").css("display", "none");
                $("#orgExport").css("display", "none");
                if ('secondary' == '${org_type}') {
                    $("#memberImport").css("display", "none");
                }
            });

            //三级菜单选中
            $(".second_menu").on("click", ".third_menu li", function () {
                $(".third_menu li").removeClass("third_menu_on");
                $(this).addClass("third_menu_on");
                $(".second_menu li").removeClass("second_menu_on");
                $(this).parent().parent().addClass("height_auto second_menu_on");
                orgId = $(this).attr('id');

                $('#second_title').show().text($(this).parent().parent().children('a').text());
                $('#second_title').prev('span').show();
                $('#third_title').text($(this).children('a').text()).show();
                $('#third_title').prev('span').show();
                $('#first_title').show();
                var history = $(this).parent().parent().parent().parent().attr("id");
                ishistory = history;
                orgMember(1, orgId, history);
                $("#addPerson").show();
                $(".select_choice").show();
                $("#delete").show();
                $("#orgImport").css("display", "none");
                $("#orgExport").css("display", "none");
                $("#memberImport").css("display", "inline-block");
                $("#memberExport").css("display", "inline-block");
            });

            //点击展开更多
            $(".silde_more").click(function () {
                //向后台请求更多数据
                var $ul = $(this).siblings(".second_menu");
                if ($ul.attr('id') === 'current') {
                    $ul.html(generateOrg(secondaries, branches));
                } else if ($ul.attr('id') === 'historic') {
                    $ul.html(generateOrg(historicSecondaries, historicBranches));
                }
            });

            //搜索回车事件
            $("#search").keydown(function (event) {
                if (event.keyCode === 13) {
                    if ($(event.target).val()) {
                        //执行搜索逻辑
                        console.log($(event.target).val())
                    } else {
                        return
                    }
                }
            });
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

            $('.btn_group').on('click', 'button', function () {
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


            var org = "${org}";
            if (org) {
                $('#current').html(generateOrg(secondaries, branches));
                $('#current_root').addClass("height_auto");
                $("#current_root li").each(function () {
                    if ($(this).attr('id') == org) {
                        $(this).addClass('third_menu_on');
                        $(this).parent().parent().addClass("height_auto second_menu_on");
                        $(this).parent().parent().find('span').removeClass('third_menu_up');
                        $(this).parent().parent().find('span').addClass('third_menu_down');
                    }
                });
                orgMember(1, org, 'current_root');
            } else {
                $('#current').html(generateOrg(secondaries, branches, 7));
            }
            $('#historic').html(generateOrg(historicSecondaries, historicBranches, 7));
            /* 单击选择按钮
            $(".clickImg").click(function(){*/
            $("tbody.table_info").on("click", ".clickImg", function () {
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

            $(".select_all").click(function () {
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
            $("tbody.table_info").on("click", ".changePerson", function () {
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
                <div class="nav_list">
                    <div class="search_container" style="display: none;">
                        <input id="search" type="text" placeholder="请输入党组名称">
                        <img src="${basePath}/images/search_icon.png"/>
                    </div>
                    <ul class="party_organization_list">
                        <li id="current_root" class="root" style="width: 100%;">
                            <div class="first_menu top_dropdown" style="text-overflow: ellipsis;white-space: nowrap;">
                                <img style="width: auto" class="right dropdown_icon dropdown_up" src="${basePath}/images/tree-arrow.png"/>
                                <span style="font-size: 16px;font-weight: 600;color: #333;">西南大学委员会</span>
                            </div>
                            <ul class="second_menu" id="current">

                            </ul>
                            <div class="silde_more">展开更多</div>
                        </li>
                        <li id="historic_root" class="root" style="width: 100%;">
                            <div class="first_menu top_dropdown" style="text-overflow: ellipsis;white-space: nowrap;">
                                <img style="width: auto" class="right dropdown_icon dropdown_up" src="${basePath}/images/tree-arrow.png"/>
                                <span style="font-size: 16px;font-weight: 600;color: #333;">中共重庆大学委员会(历史)</span>
                            </div>
                            <ul class="second_menu" id="historic">

                            </ul>
                            <div class="silde_more">展开更多</div>
                        </li>
                    </ul>
                </div>
                <div class="content_table_container party_table_container">
                    <div class="breadcrumb_group">
                        当前组织：
                        <span class="layui-breadcrumb" lay-separator=">" style="visibility: visible;">
                            <a id="first_title" href="javascript:;" style="display: none;">重庆大学党委</a>
                            <a href="javascript:;" id="second_title" style="display: none;">机关党委</a>
                            <a href="javascript:;" id="third_title" style="display: none;">机关党委</a>
                        </span>
                    </div>
                    <div class="table_content bg_white_container member_container">
                        <div class="btn_group table_btns">
                            <button id="delete" class="btn btn-default delete_graft">删除人员</button>
                            <button id="addPerson" class="btn btn-default">添加人员</button>
                            <button id="orgImport" class="btn btn-default">党组织导入</button>
                            <button id="orgExport" class="btn btn-default">党组织导出</button>
                            <button id="memberImport" class="btn btn-default">人员名册导入</button>
                            <button id="memberExport" class="btn btn-default">人员名单导出</button>
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
                        <div class="table_outer_box">

                        <table class="layui-table custom_table">
                            <thead>
                                <tr>
                                    <td>
                                        <img class="select_all" src="/images/not_check_icon.png"/>
                                        <input type="hidden"/>
                                    </td>
                                    <td>姓名</td>
                                    <td>性别</td>
                                    <td>公民身份证</td>
                                    <td>联系电话</td>
                                    <td>党员类型</td>
                                    <td>操作</td>
                                </tr>
                            </thead>
                            <tbody class="table_info">

                            </tbody>
                        </table>
                        </div>
                        <div style="text-align: center;">
                            <ul class="pagination" id="status-pager"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".table_info").on("click", "button.delete", function () {
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
    $(".table_info").on("click", "button.edit", function () {
        console.log($(this).html());
        var personId = $(this).parent().parent().prev().prev().prev().html()
        var $this = $(this);
        var userId = personId;
        var orgId = $(".third_menu_on").attr("id");
        var seconedName = $(".second_menu_on > a").text();
        var orgName = $(".third_menu_on > a").text();
        if (seconedName && orgId && orgName && userId) {
            window.location.href = '/addperson?seconedName=' + seconedName + '&orgId=' + orgId + '&orgName=' + orgName + '&userId=' + userId;
        } else {
            alert('必须指定一个支部结点');
            return;
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