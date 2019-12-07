<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>组织部子页面主题</title>
    <link rel="stylesheet" href="${basePath }/css/party_member.css"/>
    <link rel="stylesheet" href="${basePath }/css/party_organization.css"/>
    <style type="text/css">
        .col-sm-6.col-xs-12 {
            float: right;
        }

        .export_excel {
            font-size: 13px;
            /* margin: 10px 0; */
            padding: 5px 0;
            display: inline-block;
        }
        .content_table thead tr{
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }
        .content_table thead th{
            padding: 5px 15px !important;
        }
        .content_table tr:nth-child(2n) {
            background: #FBFCFE;
        }
        .content_table td{
            min-width: 130px;
            padding: 5px 15px !important;
            height: 48px;
            font-size: 14px;
        }
        .passpublic_table_container.content_table_container{
            height: calc(100% - 60px);
        }
        .passpublic_table_container .table_scroll_box{
            position: relative;
        }.passpublic_table_container .table_scroll_box .content_table{
            position: absolute;
            top: 0;
            left: 0;
        }
    </style>
</head>
<body>
<%--<div class="table_form_content activity_manage_container">--%>
    <div class="table_form_content ">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">组织生活管理</a>
                <a href="javascript:;">活动管理</a>
            </span>
        </div>
        <div class="bg_white_container">
    <portlet:resourceURL id="/hg/deleteGrafts" var="deleteGrafts"/>
    <portlet:resourceURL id="/hg/export" var="export"/>
    <a href="javascript:;" onclick="window.location.href = '/newinfo'">
        <button type="button" class="layui-btn custom_btn publish_acti_btn">
             发布活动
        </button>
    </a>
    <div class="col-sm-6 col-xs-12" style="margin-bottom:20px;">
        <span class="col-sm-4 control-label col-xs-3" style="height: 34px;line-height: 34px;text-align: right;">发布时间</span>
        <div class="col-sm-8 col-xs-9">
            <select class="time_select form-control">
                <option value="">请选择时间</option>
                <option value="">全部</option>
                <option value="nowDate">本日</option>
                <option value="nowWeek">本周</option>
                <option value="more">更早</option>
            </select>
        </div>
    </div>
    <div class="content_table_container passpublic_table_container">
        <div class="table_outer_box">
            <ul class="fixed_thead">
                <li style="width:20%">活动名称</li>
                <li style="width:20%">发布时间</li>
                <li style="width:30%">发布内容</li>
                <li style="width:30%">操作</li>
            </ul>
            <div class="table_scroll_box">
                <table class="content_table" style="width: 100%;">
                    <tbody class="table_info">
                    <c:forEach var="c" items="${grafts }">
                        <tr id="${c.inform_id }">
                            <td style="display: none;">
                                <input type="hidden" value="${c.inform_id }"/>
                                <input type="hidden" value="${c.id }"/>
                                    ${c.meeting_type }
                            </td>
                            <td>
            <%--                    <a href="/checkdetail?informId=${c.inform_id }">${c.meeting_theme }</a>--%>
                                ${c.meeting_theme }
                            </td>
                            <td>${c.release_time }</td>
                            <td>${c.content }</td>
            <%--                <td>--%>
            <%--                    <c:if test="${orgType eq 'organization'}">--%>
            <%--                        <a href="/sconedPartyDetail?inform_id=${c.inform_id }">查看进度</a>--%>
            <%--                    </c:if>--%>
            <%--                    <c:if test="${orgType eq 'secondary'}">--%>
            <%--                        <a href="/branchview?inform_id=${c.inform_id }">查看进度</a>--%>
            <%--                    </c:if>--%>
            <%--                </td>--%>
                            <td>
                                <a onclick="window.location.href='/newinfo?informId=${c.inform_id }&orgEdit=orgEdit'" href="javascript:;" style="margin-right: 10%; color: #2E87FF">编辑</a>
                                <a id="deleteInform" style="cursor: pointer;color: #FE4D4D;">删除</a>
                            </td>
                        </tr>
                        　
                        　　 </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!--    分页              -->
        <div class="pagination_container">
            <ul class="pagination" id="page"></ul>
            <div class="pageJump">
                <input class='current_page' type="hidden" value="${pageNo}"/>
                <p>共<span class="total_page">${totalPage }</span>页</p>
                <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
                </portlet:actionURL>
                <form action="${pageNoUrl }" id="getPageNo" method="post">
                    <input type="hidden" id="pageNo" name="pageNo" value=""/>
                    <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
                    <span>跳转到第</span>
                    <input type="text" id="jumpPageNo" name="jumpPageNo"/>
                    <span>页</span>
                    <%--  <input label="站点id" name="Site"  value="${Site }" type="hidden"/>
                    <input label="栏目id" name="Column"  value="${Column }" type="hidden"/> --%>
                    <input type="hidden" id="date_date" name="date" value="${date }"/>
                    <button type="submit" class="button">确定</button>
                </form>
            </div>
        </div>
        </div>
     </div>

    <script type="text/javascript">
        $(document).ready(function () {
            var pages = $(".total_page").html();
            var currentPage = $('.current_page').val();
            $("input[name='pageNo']").val($('.current_page').val());
            if (currentPage == 1) {
                $('.page_next').removeClass('not_allow');
                $('.page_prev').addClass('not_allow');

            } else if (currentPage == pages) {
                $('.page_prev').removeClass('not_allow');
                $('.page_next').addClass('not_allow');

            } else {

            }
            ;
            $("#jumpPageNo").change(function () {
                $("input[name='pageNo']").val($(this).val());
            })
            Page({
                num: pages, //页码数
                startnum: currentPage, //指定页码
                elem: $('#page'), //指定的元素
                callback: function (n) { //回调函数
                    $("input[name='pageNo']").val(n);
                    //	alert($("input[name='pageNo']").val());
                    $("#getPageNo").submit();
                    if (n == 1) {
                        $('#page a').removeClass('not_allow');
                        $('.page_prev').addClass('not_allow');
                    } else if (n >= $('.total_page').html()) {
                        $('#page a').removeClass('not_allow');
                        $('.page_next').addClass('not_allow')
                    } else {
                        $('#page a').removeClass('not_allow');
                    }
                }
            });

            $('#deleteInform').on('click',function () {
                var $this = $(this);
                var id = $this.parent().parent().attr('id');
                $.ajax({
                    url: "${deleteGrafts}",
                    data:{"<portlet:namespace/>resourcesId":id},
                    dataType:"text",
                    success:function(succeed){
                        if("succee" === succeed){
                            window.location.reload();
                        }else{
                            alert("删除失败");
                        }
                    }
                });
            });
        });
    </script>
    </div>
</div>
    <!-- 分页 -->


    <script>
        //根据时间查询
        $(".time_select").change(function () {
            //  alert($(".time_select").val());
            var date = $(".time_select").val();
            $("#date_date").val(date);
            $("#getPageNo").submit();
        });
    </script>
</body>

</html>