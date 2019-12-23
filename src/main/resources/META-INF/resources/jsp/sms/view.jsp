<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/sms/meeting" var="meeting"/>
<portlet:resourceURL id="/sms/status" var="status"/>


<head>
    <link rel="stylesheet" href="${basePath}/css/party_organization.css?v=5"/>
    <link rel="stylesheet" href="${basePath}/css/account_manage_1.css"/>
    <script src="${basePath}/js/pagination.js"></script>
    <style>
        .content-div {
            float: left;
            width: 44%;
            margin: 1% 3%;
        }
        .content_title{
            padding: 40px 15px 20px 15px!important;
        }
        .main_content {
            overflow: hidden;
            width: 200%;
        }
        .back_container {
            width: 50px;
            height: 50px;
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            left: 2%;
            display: none;
        }

        .content_table {
            width: 100%;
        }
        .meeting-theme {
            color: #428bca;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var meetingTable = $("#meeting tbody");
            var statusTable = $("#smsStatus tbody");
            var meeting;
            var statusList;
            var rendered = false;

            function getPage(type) {
                if (type === 'meeting') {
                    meetingTable.attr('current-page');
                } else if (type === 'status') {
                    statusTable.attr('current-page');
                }
            }

            function setPage(page, type) {
                if (type === 'meeting') {
                    meetingTable.attr('current-page', page);
                } else if (type === 'status') {
                    statusTable.attr('current-page', page);
                }
            }

            function gotoMeetingPage(page) {
                page = parseInt(page);
                var currentPage = getPage('meeting');
                if (page === currentPage) {
                    return
                }
                $.get("${meeting}", {page: page}, function (res) {
                    if (res.result) {
                        var content = res.data.list;
                        setPage(res.data.pageNow, 'meeting');
                        var tr = '';
                        for (var i in content) {
                            tr += "<tr>";
                            tr += "<td>" + content[i].org_name + "</td>";
                            tr += "<td>" + content[i].meeting_type + "</td>";
                            tr += "<td class='meeting-theme' meeting-id='" + content[i].meeting_id  + "'>" + content[i].meeting_theme + "</td>";
                            tr += "<td>" + content[i].start_time + "</td>";
                            tr += "<td>" + content[i].place + "</td>";
                            tr += "</tr>";
                        }
                        meetingTable.html(tr);
                        if(!rendered){
                            new Page({
                                num: res.data.totalPage, //页码数
                                startnum: 1, //指定页码
                                elem: $('#meeting-pager'), //指定的元素
                                callback: function(n) { //回调函数
                                    gotoMeetingPage(n);
                                }
                            });
                            rendered = true;
                        }
                    }
                })
            }

            function gotoStatusPage(page, meetingId, callback) {
                page = parseInt(page);
                var currentPage = getPage('status');
                if (meetingId === meeting) {
                    if (page === currentPage) {
                        return
                    }
                    goto();
                } else {
                    $.get("${status}", {meeting: meetingId}, function (res) {
                        if (res.result) {
                            statusList = res.data;
                            meeting = meetingId;
                            goto();
                            if(statusList.length !== 0){
                                new Page({
                                    num: Math.ceil(statusList.length / 10), //页码数
                                    startnum: 1, //指定页码
                                    elem: $('#status-pager'), //指定的元素
                                    callback: function(n) { //回调函数
                                        gotoStatusPage(n, meeting);
                                    }
                                });
                            }
                        }
                    })
                }

                function goto() {
                    var tr = '';
                    var maxPage = Math.ceil(statusList.length / 10);
                    if (page < 1) {
                        page = 1;
                    } else if (page > maxPage) {
                        page = maxPage;
                    }
                    var content = statusList.slice((page - 1) * 10, page * 10);
                    for (var i in content) {
                        tr += "<tr><td>" + content[i].receiver + "</td>";
                        tr += "<td>" + (content[i].status ? "已发送" : "未发送") + "</td>";
                        tr += "<td>" + content[i].errorMessage + "</td></tr>";
                    }
                    statusTable.html(tr);
                    if (callback) {
                        callback();
                    }
                }
            }

            function slideToStatusPage(meetingId) {
                var callback = function () {
                    $('.back_container').show();
                    $('#meeting').animate({marginLeft: '-100%'});
                };
                gotoStatusPage(1, meetingId, callback);
            }

            function slideBack() {
                $('#meeting').animate({marginLeft: '3%'});
            }

            meetingTable.on('click', '.meeting-theme', function(){
                var meetingId = $(this).attr('meeting-id');
                slideToStatusPage(meetingId);
            });

            $('.back_container').on('click', function () {
                $(this).hide();
                slideBack();
            });

            gotoMeetingPage(1);

        });
    </script>
</head>
<body>
<div style="overflow: hidden;">
    <div class="back_container"><img width="50px" src="${basePath}/images/left46.png"/></div>
    <div class="main_content full_screen">
        <div id="meeting" class="content-div content-left">
            <table class="content_table">
                <thead class="table_title">
                <tr>
                    <th>党组织</th>
                    <th>会议类型</th>
                    <th>会议主题</th>
                    <th>开始时间</th>
                    <th>会议地点</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <ul class="pagination" id="meeting-pager"></ul>

        </div>
        <div id="smsStatus" class="content-div content-right">
            <table class="content_table">
                <thead class="table_title">
                <tr>
                    <th>收信人</th>
                    <th>发送状态</th>
                    <th>错误信息</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <ul class="pagination" id="status-pager"></ul>
        </div>
    </div>
</div>
</body>