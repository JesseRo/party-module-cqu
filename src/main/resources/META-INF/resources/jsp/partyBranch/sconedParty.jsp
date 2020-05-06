<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/hg/groupAndMember" var="groupsAndMembers"/>
<!-- 保存新增站点 -->
<%-- <portlet:actionURL name="${submitCommand }" var="submitForm"/> --%>
<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>

<portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>
<portlet:resourceURL id="/hg/getPlace" var="getPlace"/>

<portlet:resourceURL id="/hg/getGroup" var="getGroup"/>
<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl"/>
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl"/>
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl"/>

<portlet:resourceURL id="/org/memberGroup" var="candidate"/>
<portlet:resourceURL id="/hg/place/list" var="places"/>
<portlet:resourceURL id="/hg/place/add" var="addPlace"/>

<html>
<head>
    <style type="text/css">
        .layui-btn-xs {
            height: 22px !important;
            line-height: 22px!important;
            padding: 0 5px!important;
            font-size: 12px!important;
        }

        input, select {
            outline: none;
            text-indent: 0;
        }


        textarea {
            width: 100%;
            height: 100px;
        }

        input#button2 {
            float: left;
            margin-left: 12%;
            text-indent: 0;
            height: 36px;
            padding-left: 21px;
            padding-right: 21px;
        }

        /* 常用人员弹窗样式 */

        #commonStaff thead {
            border-bottom: 1px solid #d8d8d8;
        }

        #commonStaff .first_thead {
            background: #DEDEDE;
            font-size: 14px;
            height: 46px;
        }

        #commonStaff .modal-body {
            max-height: 500px;
        }

        #commonStaff .common_list {
            border-right: none;
        }

        #commonStaff .common_list tr td {
            cursor: pointer;
        }

        #commonStaff .common_list tr td:nth-child(2) {
            cursor: default;
        }

        #commonStaff table td {
            font-size: 13px;
        }

        #commonStaff th,
        #commonStaff .common_list td {
            border: none;
        }

        #commonStaff .modal-body > div {
            padding: 0;
        }

        #commonStaff .modal-body > div table {
            width: 100%;
        }

        #commonStaff .add_btn {
            height: auto;
            background: #f5f5f5;
            border: 1px solid #d8d8d8;
            border-top: none;
        }

        #commonStaff .add_btn button {
            margin: 15px;
            font-size: 16px;
        }

        #commonStaff .add_btn img {
            margin-right: 5px;
        }

        .layui-table-cell {
            text-align: left;
        }

        /*.table_container {*/
        /*    height: 200px;*/
        /*    overflow-y: auto;*/
        /*    border: 1px solid #d8d8d8;*/
        /*}*/

        .common_list_container .table_container {
            /* border-right: none; */
        }

        #commonStaff .table_title {
            text-align: center;
            font-size: 18px;
        }

        .add_btn form {
            width: 80%;
            background: #fff;
            border: 1px solid #d8d8d8;
            border-radius: 6px;
            padding: 10px 5px;
            padding-bottom: 0;
        }

        #commonStaff .add_btn form {
            margin: 0 0 10px 10px;
            display: none;
        }

        #commonStaff .add_btn form .btn {
            margin: 0;
        }

        #commonStaff .form-group {
            margin-bottom: 0;
        }

        .form_btn {
            text-align: right;
        }

        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }

        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }

        .member_title {
            height: 30px;
            line-height: 30px;
            background: #f5f5f5;
            color: #333;
            padding-left: 20px;
        }

        .member_content {
            padding: 18px 20px;
        }

        .member_content span {
            margin-top: 10px;
            margin-right: 10px;
            display: inline-block;
            padding: 5px 10px;
            border: 1px solid #D8D8D8;
            border-radius: 4px;
            font-size: 16px;
        }

        .member_content span img {
            vertical-align: baseline;
            cursor: pointer;
        }

        .all_member_list {
            max-height: 177px;
            min-height: 61px;
            height: auto;
            overflow-y: auto;
            background: #f5f5f5;
            padding: 15px;
            border: 1px solid #d8d8d8;
            border-top: none;
        }

        .all_member_list p {
            color: #333;
            display: inline-block;
        }

        .all_member_list > .list_item_box span {
            width: 16%;
            margin-top: 10px;
            display: inline-block;
            padding-top: 2px;
            padding-bottom: 2px;
            border: 1px solid #d8d8d8;
            text-align: center;
            margin-right: 2.5%;
            background: #fff;
        }

        #commonStaff .modal-footer {
            border-top: 0;
        }

        #commonStaff .modal-footer button {
            margin-top: 24px;
        }

        .all_member_list::-webkit-scrollbar {
            width: 4px;
            height: 4px;
        }

        .all_member_list::-webkit-scrollbar-thumb {
            border-radius: 5px;
            -webkit-box-shadow: inset 0 0 5px transparent;
            background: transparent;
        }

        .all_member_list::-webkit-scrollbar-track {
            -webkit-box-shadow: inset 0 0 5px transparent;
            border-radius: 0;
            background: #dedede;
        }

        .member_list_title div.right {
            height: 18px;
            display: inline-block;
            width: 110px;
        }

        .member_list_title div.right .select_choice {
            font-size: 12px;
            float: left;
        }

        .first_select_box {
            margin-right: 10px;
        }

        .divide_img {
            text-align: center;
            margin: 150px 0;
        }

        /*  */
        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }

        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }

        label.error {
            position: absolute;
            color: #ce0000;
            font-size: 12px;
            font-weight: normal;
        }

        /*
         .control-label::before{
            content: "*";
            position: absolute;
            top: 0;
            left: -5px;
            color: #ce0000;
            line-height:40px;
        	} */
        .member_content span {
            display: inline-block;
            padding: 5px 10px;
            border: 1px solid #D8D8D8;
            border-radius: 4px;
        }

        span.branch_person {
            width: 16%;
            margin-top: 10px;
            display: inline-block;
            padding-top: 2px;
            padding-bottom: 2px;
            border: 1px solid #d8d8d8;
            text-align: center;
            margin-right: 2.5%;
            background: #fff;
        }

        span.branch_person {
            width: auto;
            min-width: 16%;
        }

        span.branch_person.person_class {
            color: wheat;
        }

        tbody.use_group th, td {
            text-align: center;
            padding-top: 14px;
        }


        /*滚动条  */
        .table_container::-webkit-scrollbar {
            width: 4px;
            height: 4px;
        }

        .table_container::-webkit-scrollbar-thumb {
            border-radius: 5px;
            -webkit-box-shadow: inset 0 0 5px transparent;
            background: transparent;
        }

        .table_container::-webkit-scrollbar-track {
            -webkit-box-shadow: inset 0 0 5px transparent;
            border-radius: 0;
            background: #dedede;
        }

        .content_info .content_form .form-group > div {
            margin-bottom: 20px;
        }
        .layui-laydate .layui-laydate-content table tr{
            display:flex;
        }

        /*滚动条  */
        thead.first_thead th {
            text-align: center;
        }

        .table_title {
            padding-bottom: 10px;
        }

        .col-sm-offset-2.col-sm-10.form_btn {
            margin-top: 7px;
            margin-bottom: 7px;
        }

        thead.first_thead th {
            height: 30px;
        }

        div#hg-form-container {
            margin-top: 15px;
        }
        .layui-table{
            width: 100%!important;
        }
        .table_form_content .custom_table + .layui-table-view{
            padding-bottom: 60px;
        }
        .mult_input_container .form-control.focus, .mult_input_container .form-control.active{
            outline: none!important;
        }
        .mult_input_container .form-control .badge span{
            padding: 2px 4px;
        }
        .mult_input_container .form-control .badge{
            background-color: #FFAB33;
            padding: 3px;
            margin: 0 2px 3px 0;
        }
        .mult_input_container .form-control{
            box-shadow: none;
            border-radius: 0;
            border: none;
            border-bottom: 1px solid #ccc;
            max-height: 80px;
            overflow-y: auto;
            padding: 0;
            height: auto;
            min-height: 40px!important;
        }
        .mult_input_container .dropdown-menu{
            padding: 5px;
        }
        .mult_input_container .text-primary{
            color: #FFAB33!important;
        }
        .mult_input_container .expand_arrow + .list_container{
            display: block;
        }
        .edit_group_member{
            padding: 4px 10px!important;
            vertical-align: middle;
            margin-left: 10px;
        }
        .mult_input_container .list_container span{
            color: #FFF;
            background: #FFAB33;
            padding: 4px;
            border-radius: 4px;
            cursor: pointer;
            display: inline-block;
            margin-right: 5px;
            max-width: 80px;
            overflow-x: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .mult_input_container .list_container span:hover{
            background: rgba(225, 171, 51, 0.8)!important;
        }
        .mult_input_container .list_container .list_item_container{
            padding-top: 10px;
            max-height: 80px;
            overflow-y: auto;
        }
        .mult_input_container .layui-form-select, .mult_input_container .layui-form-checkbox{
            display: none;
        }
        .mult_input_container .list_container{
            display: none;
            margin-top: 10px;
        }
        .mult_input_container .mult_input{
            height: auto;
            border-bottom: 1px solid #ddd;
            outline: none;
            font-size: 14px;
            line-height: 38px;
            overflow-y: auto;
            max-height: 80px;
        }
        .mult_input_container{
            width: 100%!important;
            height: auto!important;
        }
        .mult_input_container .layui-input-inline .drop_arrow.expand_arrow{
            transform: rotate(180deg);
        }
        .mult_input_container .layui-input-inline .drop_arrow{
            width: 20px;
            height: 20px;
            display: inline-block;
            content: '';
            background: url("${basePath}/cqu/images/tree-arrow.png");
            position: absolute;
            top: 20px;
            right: -30px;
            cursor: pointer;
        }
        .mult_input_container .layui-input-inline{
            height: 100%;
            width: calc(100% - 30px)!important;
            position: relative;
        }
        .modal_content_container .left_list_container .custom_table+.layui-table-view .layui-table-body{
            max-height: 300px!important;
        }
        .modal_content_container >div .layui-table-page{
            overflow-x: auto;
        }
        .modal_content_container >div .layui-laypage-count, .modal_content_container >div .layui-laypage-skip{
            display: none;
        }
        .modal_content_container .left_list_container .custom_btn{
            margin: 10px 0;
        }
        .modal_content_container .left_list_container{
            width: 50%;
            margin-right: 5%;
        }
        .modal_content_container .right_member_container{
            width: 45%;
        }
        .member_modal .modal-footer{
            clear: both;
        }
        .member_modal button{
            outline: none;
        }
        .modal_content_container > div{
            display: inline-block;
            float: left;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .member_modal .modal-dialog{
            width: 800px!important;
        }
        .layui-table-cell + .layui-table-grid-down{
            display: none;
        }

        .layui-form-label.layui-required:before {
            content: "*";
            color: red;
            top: 5px;
            right: 2px;
            position: relative;
        }
        .table_form_content .custom_form .layui-form-label{
            padding: 0 10px;
            width: 160px;
        }
        #addPlaceModal .layui-form-label{
            width: 160px;
        }
        .table_form_content .layui-input-block {
            margin-left: 160px;
        }
        .table_form_content .custom_form .layui-form-item .layui-input-inline{
            width: 260px;
        }
        #addPlaceModal .layui-input-inline{
            width: 320px;
        }
        .layui-laydate-content table thead tr{
            display:flex;
        }
        .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 260px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        .layui-input, .layui-textarea {
             padding-left: 10px !important;
         }
        .table_form_content .custom_form .meetingContent{
            width: 700px !important;
        }
        .table_form_content .custom_form .layui-input{
            height: 38px;
        }
        body .layui-layer {
            min-width: 0px !important;
        }
    </style>
    <link rel="stylesheet" href="${basePath}/js/layui/css/modules/multiSelect/multi-select.css"/>
    <link rel="stylesheet" href="${basePath}/js/layui/css/modules/checkbox/checkbox.css?version=98"/>
    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=4"></script>
    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css"/>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>

</head>
<body>
<div class="table_form_content ">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">组织生活管理</a>
                <a href="javascript:;">拟定计划</a>
            </span>
        </div>
        <input type="hidden" class="mapValue" value='${mapNew }'>
        <input type="hidden" class="mapOld" value='${mapold }'>
        <input type="hidden" class="mapedit" value='${mapedit }'>
        <input type="hidden" class="planContent" value='${planContent }'>

        <div class="bg_white_container">
            <div class="content_form form_container">
                <form class="layui-form custom_form"  id="addMeetingPlanForm"
                      style="width: 960px;">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">党组织：</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${organization.org_name }</label>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">会议类型：</label>
                            <div class="layui-input-inline">
                                <select  name="conferenceType" lay-verify="select">
                                    <option  value="" disabled>请选择</option>
                                    <c:forEach var="n" items="${conferenceTypes }">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开展主题：</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="subject"  lay-verify="required" autocomplete="on">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开始时间：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="timeDuring" id="timeDuring" value="${info.timeDuring }"
                                       class="layui-input start_date" lay-verify="required" autocomplete="off">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">共计时长：</label>
                            <div class="layui-input-inline">
                                <select  name="timeLasts" lay-verify="select">
                                    <option  value="" disabled>请选择</option>
                                    <c:forEach var="n" items="${timeLasts }">
                                        <option value="${n}">${n/60}小时</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开展校区：</label>
                            <div class="layui-input-inline">
                                <select  name="campus" lay-verify="select" lay-filter="campus">
                                    <c:forEach var="n" items="${campus }">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开展地点：</label>
                            <div class="layui-input-inline">
                                <select  name="location" lay-verify="select">
                                </select>
                            </div>
                            <a href="javascript:void(0)" id="addPlace" ><i class="layui-icon layui-icon-addition"></i></a>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">主持人：</label>
                            <div class="layui-input-inline">
                                <select name="host" lay-search="" multiple="" lay-filter="select" placeholder="可搜索可输入">
                                    <option value="">请选择</option>
                                    <c:forEach var="m" items="${members}">
                                        <option value="${m.member_identity}">${m.member_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">列席人员：</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="sit"  lay-verify="required" autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">联系人：</label>
                            <div class="layui-input-inline">
                                <select name="contact" lay-search="" lay-filter="select" placeholder="可搜索可输入">
                                    <option >请选择</option>
                                    <c:forEach var="m" items="${members}">
                                        <option value="${m.member_identity}">${m.member_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">联系电话：</label>
                            <div class="layui-input-inline">
                                <input name="phoneNumber" class="layui-input"  lay-verify="required" autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">参会人员：</label>
                            <div class="layui-input-inline" style="width:700px">
                                <select name="participate" lay-filter="participate" multiple lay-search lay-case >
                                    <option value="">请选择</option>
                                    <c:forEach var="m" items="${members}">
                                        <option value="${m.member_identity}">${m.member_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline" style="width:40px">
                                <a href="javascript:void(0)" memberGroup="hide" onclick="showMemberGroup(this)"><i class="layui-icon layui-icon-user"></i></a>
                            </div>
                            <div class="layui-input-inline" style="display:none" id="memberGroup">
                                <p>
                                    常用分组:
                                    <button type="button" class="layui-btn layui-btn-xs layui-btn-warm" id="edit_group_member">编辑</button>
                                </p>
                                <p class="list_item_container" id="group_member_list">
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">计划内容：</label>
                            <div class="layui-input-inline meetingContent">
                                <script id="meetingContent" name="meetingContent" type="text/plain"></script>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button type="submit" class="layui-btn layui-btn-warm" lay-submit="" lay-filter="meetingPlanRelease">发 布</button>
                            <button type="submit" class="layui-btn layui-btn-warm" lay-submit="" lay-filter="meetingPlanSave">暂 存</button>
                            <button type="button" class="layui-btn layui-btn-primary" id="meetingPlanCancle">取 消</button>
                        </div>
                    </div>
                </form>
                <%--<form class="layui-form custom_form" role="form" action="${submitForm }" method="post"
                      enctype="multipart/form-data" style="max-width: 960px;">
                    <div id="hg-form-container" class="form-group">
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">党组织：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control ${class}" name="branch" disabled="disabled">
                                    <option value="${organization.org_id}">${organization.org_name}</option>
                                    <option disabled="" selected="">请选择</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">会议类型：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control ${class}" name="conferenceType">
                                    <option value="党委中心组学习">党委中心组学习</option>
                                    <option value="组织生活会">组织生活会</option>
                                    <option value="支委会">支委会</option>
                                    <option value="谈心谈话">谈心谈话</option>
                                    <option value="民主生活会">民主生活会</option>
                                    <option value="党小组会">党小组会</option>
                                    <option value="党课">党课</option>
                                    <option value="主题党日">主题党日</option>
                                    <option value="民主评议党员">民主评议党员</option>
                                    <option disabled="" selected="">请选择</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">共计时长：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control ${class}" name="timeLasts">
                                    <option disabled="" selected="">请选择</option>
                                    <option value="30">0.5小时</option>
                                    <option value="60">1小时</option>
                                    <option value="90">1.5小时</option>
                                    <option value="120">2小时</option>
                                    <option value="150">2.5小时</option>
                                    <option value="180">3小时</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">开展校区：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control ${class}" name="campus">
                                    <option disabled="" selected="">请选择</option>
                                    <option value="A区">A区</option>
                                    <option value="B区">B区</option>
                                    <option value="C区">C区</option>
                                    <option value="虎溪校区">虎溪校区</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">开展地点：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <div class="dropdown-mul-2 dropdown-multiple">
                                    <select style="display:none" name="location" multiple="" placeholder="请选择">
                                        <option disabled="" value="undefined">没有数据</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">主持人：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <div class="dropdown-mul-2 dropdown-multiple">
                                    <select style="display:none" name="host" multiple="" placeholder="请选择">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">联系人：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <div class="dropdown-mul-2 dropdown-multiple">
                                    <select style="display:none" name="contact" multiple="" placeholder="请选择">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class=" control-label ${class}">列席人员：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <input class="form-control" name="sit" value="">
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class=" control-label form-label-required">联系人电话：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <input class="form-control" name="phoneNumber" value="">
                            </div>
                        </div>
                        <div class="col-sm-12 col-xs-12">
                            <div class="col-sm-3 col-xs-3" style="width: 12.5%;margin-left: -3px;">
                                <span class=" control-label form-label-required">参会人员：</span>
                            </div>
                            <div class="col-sm-9 col-xs-9 mult_input_container" style="width: 87.5%!important;" >
                                <div class="layui-input-inline">
                                    <select name="participate" id="attend_member_select" class="mult_input" multiple="multiple" style="display: none;">
                                    </select>
                                    <div class="dashboardcode-bsmultiselect"><ul class="form-control btn border" style="cursor: text; display: flex; flex-wrap: wrap; align-items: center; margin-bottom: 0px; min-height: calc(2.25rem + 2px);"></ul></div>
                                    <i class="drop_arrow"></i>
                                    <div class="list_container">
                                        <p>
                                            常用列表
                                            <span class="edit_group_member">编辑</span>
                                        </p>
                                        <p class="list_item_container">
                                            <span id="group1" title="group1人员" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="bottom">group1</span>
                                            <span id="group2" title="group2人员" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="bottom">group2</span>
                                            <span id="group3" title="group3人员" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="bottom">group3</span>
                                            <span id="group4" title="group4人员" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="bottom">group4</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-xs-12">
                            <div class="col-sm-6 col-xs-12" style="padding-left: 0;">
                                <div class="col-sm-3 col-xs-3 ">
                                    <span class=" control-label form-label-required">计划内容：</span>
                                </div>
                                <div class="col-sm-9 col-xs-9">
                                    <script id="new_12" name="new_12" type="text/plain"></script>
                                </div>
                            </div>
                        </div>
                        <input id="content_id" type="hidden" name="content"/>
                        <input id="submitFrom" type="submit" style="display:none;"/>
                        <input id="hiddenstate" type="hidden" name="state"/>
                        <input id="graft" type="hidden" name="graft"/>
                        <input id="newAndOld" type="hidden" name="newAndOld"/>
                        <input id="meeting_id" type="hidden" name="meeting_id"/>
                        <input type="hidden" name="formId" value="${formId}"/>
                        <div class="col-sm-12 col-xs-12">
                            <div class="layui-inline btn_group"
                                 style="width: calc(50% - 120px);margin: 0;margin-top: 10px;">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-inline">
                                    <button type="button" onclick="formsubmit()" class="layui-btn" lay-submit=""
                                            lay-filter="partyMemForm"
                                            style="padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;background-color: #FFAB33;border-radius: 4px;">
                                        发布
                                    </button>
                                    <button type="button" onclick="formsubmitgraft()"
                                            class="layui-btn layui-btn-primary"
                                            style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                        暂存
                                    </button>
                                    <button type="button" onclick="window.history.back();"
                                            class="layui-btn layui-btn-primary"
                                            style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                        取消
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>--%>
            </div>
        </div>
    </div>
    <div id="memberModal" >
        <div class="left_list_container">
            <p class="">常用列表</p>
            <button class="layui-btn custom_btn add_group_btn" style="height: 32px;">添加</button>
            <div class="table_container">
                <table id="groupTable" lay-filter="groupTable" class="custom_table"></table>
            </div>
        </div>
        <div class="right_member_container">
            <p class="" id="member_title">常用人员</p>
            <form class="layui-form" action="">
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <select name="member" lay-search="" lay-filter="member" placeholder="可搜索可输入">
                            <option value="">添加人员</option>
                            <c:forEach var="m" items="${members}">
                                <option value="${m.member_identity}">${m.member_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </form>
            <div class="table_container">
                <table id="memberTable" lay-filter="memberTable" class="custom_table"></table>
            </div>
        </div>
    </div>

    <div id="addPlaceModal" >
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" value="1">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">开展地点</label>
                    <div class="layui-input-inline">
                        <input type="text" name="place" lay-verify="required" lay-reqtext="开展地点是必填项，不能为空。" placeholder="请输入开展地点" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="addPlaceForm">确定</a>
                <a class="layui-layer-btn1">取消</a>
            </div>
        </form>
    </div>

</div>
<!-- 常用人员，组的增加，修改，删除 -->
<portlet:resourceURL id="/hg/paersonAndGroupAddDelete" var="paersonAndGroupAddDelete"/>
<portlet:resourceURL id="/hg/assignedAddPerson" var="assignedAddPerson"/>
<portlet:resourceURL id="/hg/getBranchAllPersons" var="getBranchAllPersons"/>
<portlet:resourceURL id="/hg/deletePerson" var="deletePerson"/>
<script type="text/html" id="groupOperateBtns">
    <div class="operate_btns">
        <span class="red_text delete_td" title="删除" onclick="deleteTd(event,1)">删除</span>
    </div>
</script>
<script type="text/html" id="memberOperateBtns">
    <div class="operate_btns">
        <span class="red_text delete_td" title="删除" onclick="deleteTd(event,2)">删除</span>
    </div>
</script>
<script type="text/javascript">
    layui.config({
        base: '${basePath}/js/layui/module/'
    }).extend({
        checkbox: 'checkbox/checkbox'
    });
    layui.use(['laydate','layer','form','checkbox'], function(){
        var laydate = layui.laydate,
            form = layui.form,
            layer = layui.layer,
            checkbox = layui.checkbox;
        renderEditor();
        renderDateSelect();
        renderMemberGroups();
        function renderDateSelect() {
            laydate.render({
                elem: '#timeDuring'
                ,type: 'datetime'
                ,isInitValue: true
                //,trigger: 'click'
                ,done: function(value, date, endDate){
                }
                ,change: function(value, date, endDate){
                    this.elem.val(value)
                }
            });
        }

        function renderEditor(){
            var uploadUrls = {
                file: '${uploadfileUrl}',
                image: '${uploadimageUrl}',
                video: '${uploadvideoUrl}'
            };
            var ueditor = UE.getEditor("meetingContent", { initialFrameWidth:700,zIndex:100});

            UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
            UE.Editor.prototype.getActionUrl = function(action) {
                if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                    return uploadUrls.image;
                } else if (action == 'uploadvideo') {//视频
                    return uploadUrls.video;
                } else if (action == 'uploadfile') {//附件
                    return uploadUrls.file;
                } else {
                    return this._bkGetActionUrl.call(this, action);
                }
            };
        }

        function renderMemberGroups(){
            $("#group_member_list").empty();
            $.post('${groupsAndMembers}', function(res){
                if(res.result){
                    $("#group_member_list").children("li").remove();
                    var nodes = new Array();
                    for(var i=0;res.data.length>0 && i<res.data.length;i++){
                        var tipsContent = '';
                        if(res.data.member && res.data.member.length>0){
                            tipsContent = res.data.member
                        }
                        var node = {
                            id:res.data[i].id,
                            name:res.data[i].name,
                            type:res.data[i].id,
                            on:false,
                            tipsContent:tipsContent
                        }
                        nodes.push(node);
                    }
                    checkbox({
                        elem: "#group_member_list"
                        ,showDel:false
                        ,tips:{
                            show:true,
                            render:true
                        }
                        , nodes: nodes
                        , click: function (node) {
                            console.log( node);
                        }
                    });
                }else {
                    layer.msg(res.message);
                }
            })
        }
        form.on('select(campus)', function(data){
            renderPlace();
        });
        form.on('submit(addPlaceForm)', function(data){
            $.post("${addPlace}", {place: data.field.place, campus: $('#addMeetingPlanForm  select[name="campus"]').val()}, function (res) {
                if (res.code ==200) {
                    renderPlace();
                }
            },'json');
        });

        $('#edit_group_member').on('click', function () {
            var index = layer.open({
                title:'常用分组列表',
                type: 1,
                content: $("#memberModal")
            });
        });
        $('#addPlace').on('click', function () {
            var campus = $('#addMeetingPlanForm  select[name="campus"]').val();
            if (!campus) {
                layer.alert("请先选择校区");
            } else {
                layer.open({
                    type: 1,
                    area: ['500px','160px'],
                    title:'添加开展地点',
                    content: $("#addPlaceModal"),
                    shadeClose: true
                });
            }
        });
        $('#addMeetingPlanForm  select[name="campus"]').change(function(){
            renderPlace();
        });
        function renderPlace() {
            var campus = $('#addMeetingPlanForm  select[name="campus"]').val();
            if (!campus) {
                layer.alert("请先选择校区.");
            }
            $.ajax({
                url: '${places}',
                type: 'POST',
                data: {campus: campus},
                dataType: 'json',
                async: false,
                success: function (res) {
                    if (res.code==200) {
                        $('#addMeetingPlanForm  select[name="location"]').empty();
                        $('#addMeetingPlanForm  select[name="location"]').append('<option  value="" disabled>请选择</option>');
                        for (var i=0;res.data.length>0&&i<res.data.length;i++ ) {
                            $('#addMeetingPlanForm  select[name="location"]').append('<option  value="'+res.data[i].id+'" >'+res.data[i].place+'</option>');
                        }
                        form.render('select');
                    } else {
                        layer.msg('获取地点失败.');
                    }
                },
                error: function () {
                    layer.msg('获取地点失败.');
                }
            });
        }
    })
    function showMemberGroup(o){
        var memberGroup = $(o).attr("memberGroup");
        if(memberGroup == "hide"){
            $(o).attr("memberGroup","show");
            $("#memberGroup").show();
        }else{
            $(o).attr("memberGroup","hide");
            $("#memberGroup").hide();
        }
    }



    // 参会人员 组件
    var mockMember;


    var commonOpts = function(id, className, data, idField, nameField, toolbar){
        return {
            elem: id,
            data: data,
            page: {
                limit:5,
                prev:'&lt;上一页',
                next:'下一页&gt;'
            },
            width: '100%',
            cols: [[ //表头
                {field: idField, title: 'id',  unresize:true, hide: true},
                {field: nameField, title: '名字', unresize:true, templet: function(d){
                        return '<span class="'+ className +'" title="'+ d[nameField] +'" style="width:80px;display:inline-block" onclick="addMember(event)">'+ d[nameField] +'</span>'
                    }, width: '50%'},
                {field: 'operate', title: '操作', toolbar: toolbar, unresize:true, width: '50%'}
            ]]
        };
    };

    function renderGroup() {
        $.post('${getGroup}', function (res) {
            if (!groupTable){
                groupTable = table.render(commonOpts('#groupTable', 'group_td_name', res, 'group_id', 'group_name', '#groupOperateBtns'));
            }else {
                groupTable.reload({data: res})
            }
        })
    }

    function renderMember() {
        if (currentGroup){
            $.post('${paersonAndGroupAddDelete}', {groupId: currentGroup, path: "getGroupPersons"}, function (res) {
                res = JSON.parse(res);
                if (!memberTable){
                    memberTable = table.render(commonOpts('#memberTable', 'member_td_name', res, 'participant_id', 'member_name', '#memberOperateBtns'));
                }else {
                    memberTable.reload({data: res});
                }
            })
        }else {
            layuiModal.alert('请先选择一个常用组！');
        }

    }
    //删除按钮
    function deleteTd(e, type){
        console.log(e,'e');
        layuiModal.confirm("确认要删除吗？", function () {
            if(type === 1){
                var id = $(e.target).parent().parent().parent().parent().find("[data-field='group_id']").children().text();
                $.ajax({
                    url: '${paersonAndGroupAddDelete}',
                    type: 'POST',
                    data: {groupId: id, path: "deleteGroup"},
                    dataType: 'json',
                    async: false,
                    success: function (res) {
                        if(res){
                            renderGroup();
                            console.log(e,'e ');
                        }
                        groupsAndMembers()
                    }
                });
            }else if (type === 2){
                var url = "${deletePerson}";
                if(currentGroup) {
                    var id = $(e.target).parent().parent().parent().parent().find("[data-field='participant_id']").children().text();
                    var data = {groupId: currentGroup, user_id: id};
                    layuiModal.confirm("确定删除吗?", function () {
                        $.ajax({
                            url: url,
                            data: data,
                            dataType: "json",
                            success: function (result) {
                                if (result.state === "ok") {
                                    renderMember();
                                } else {
                                    showConfirm("删除失败！");
                                }
                                groupsAndMembers()
                            }
                        });
                    });
                }
            }
        });
    }
    var currentGroup, allMember = [];
    //点击组名 添加人员
    function addMember(e){
        //判断是否是常用列表 名字列
        if($(e.target).hasClass("group_td_name")){
            var id = $(e.target).parent().parent().parent().find("[data-field='group_id']").children().text();
            var name = $(e.target).parent().parent().parent().find("[data-field='group_name']").children().text();
            currentGroup = id;
            $('#member_title').text(name + '-人员列表');
            renderMember();
            console.log(e,'e ')
            groupsAndMembers()
        }
    }
    $(function () {
        var uploadUrls = {
            file: '${uploadfileUrl}',
            image: '${uploadimageUrl}',
            video: '${uploadvideoUrl}'
        };
        // var ueditor = UE.getEditor("new_12", { initialFrameWidth:821});
        //
        // UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        // UE.Editor.prototype.getActionUrl = function(action) {
        //     if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
        //         return uploadUrls.image;
        //     } else if (action == 'uploadvideo') {//视频
        //         return uploadUrls.video;
        //     } else if (action == 'uploadfile') {//附件
        //         return uploadUrls.file;
        //     } else {
        //         return this._bkGetActionUrl.call(this, action);
        //     }
        // };

        // $("#hg-form-container").parent().validate({
        //     submitHandler: function (form) {
        //         form.submit();
        //         return false;
        //     },
        //     rules: rules
        // });

        /*开展地点弹框  */

        $('.dropdown-mul-2').eq(0).dropdown({
            data: [{
                name: '没有数据',
                disabled: true
            }],
            searchNoData: '<li style="color:#ddd">查无数据</li>',
            limitCount: 1,
            choice: function () {

            }
        });
        $($('.dropdown-mul-2 .dropdown-main .dropdown-search')[0]).after('<button class="layui-btn layui-btn-sm layui-btn-warm" style="float:right" type="button" onclick="addPlace()">新增地点</button>');
        $('[name="campus"]').change(getPlace);

        var candidates = [];
        var dropArr = [];
        var memberArr = JSON.parse('${memberList}');
        if(memberArr.length<1){
            dropArr =  [{
                name: '没有数据',
                disabled: true
            }]
        }else{
            for(var i=0;i<memberArr.length;i++){
                dropArr.push({
                    value:memberArr[i].member_identity,
                    name:memberArr[i].member_name
                })
            }
        }
        $('.dropdown-mul-2').eq(1).dropdown({
            data:dropArr,
            input: '<input type="text" maxLength="20" placeholder="请输入搜索">',
            limitCount: 1,
            searchNoData: '<li style="color:#ddd">查无数据</li>',
            choice: function () {

            }
        });
        $('.dropdown-mul-2').eq(2).dropdown({
            data: dropArr,
            searchNoData: '<li style="color:#ddd">查无数据</li>',
            input: '<input type="text" maxLength="20" placeholder="请输入搜索">',
            limitCount: 1,
            choice: function () {
                var selectedContact = this.$select.val();
                if (selectedContact && selectedContact.length > 0) {
                    var phone = candidates
                        .filter(function (p) {
                            return p.id === selectedContact[0]
                        })[0]
                        .phone;
                    $('[name=phoneNumber]').val(phone);
                }
            }
        });

        function refresh(org, host, contact, participants) {
            $.get('${candidate}', {
                orgId: org
            }, function (res) {
                if (res.result) {
                    var admins = res.data.admins;
                    var candidatesGroup = res.data.candidates;
                    candidates = [];
                    var c2 = [];
                    var _admin = {};
                    for (var i in admins) {
                        _admin[admins[i]] = admins[i];
                    }
                    for (var group in candidatesGroup) {
                        for (var j in candidatesGroup[group]) {
                            var member = candidatesGroup[group][j];
                            c2.push({
                                id: member.member_identity,
                                disabled: false,
                                groupId: group,
                                groupName: group,
                                name: member.member_name,
                                selected: false,
                                phone: member.member_phone_number
                            });
                            candidates.push({
                                id: member.member_identity,
                                disabled: false,
                                groupId: group,
                                groupName: group,
                                name: member.member_name,
                                selected: false,
                                phone: member.member_phone_number
                            });
                            allMember.push({
                                id: member.member_identity,
                                name: member.member_name
                            })
                        }
                    }
                    if (candidates.length === 0) {
                        candidates = [{
                            name: '没有数据',
                            disabled: true
                        }];
                    }
                    $('.dropdown-mul-2').eq(1).data('dropdown').changeStatus();
                    $('.dropdown-mul-2').eq(2).data('dropdown').changeStatus();
                    $('.dropdown-mul-2').eq(1).data('dropdown').update(c2, true);
                    $('.dropdown-mul-2').eq(2).data('dropdown').update(candidates, true);

                    host && $('.dropdown-mul-2').eq(1).data('dropdown').choose(host);
                    contact && $('.dropdown-mul-2').eq(2).data('dropdown').choose(contact);

                    var selectOpts = '';
                    selectOpts += allMember.map(function(m){return "<option value='" + m.id + "'>" + m.name + "</option>"}).join('');
                    $("#attend_member_select").html(selectOpts);
                    //使用插件
                    $("#attend_member_select").bsMultiSelect({q:'e'}, {a:'x'});

                    if(participants){
                        participants.forEach(function (e) {
                            $('[data-option-id=' + e + ']').click();
                        })
                        $("select[name='participate']").attr("disabled", "disabled");
                    }
                }
            });
        }
        layui.use('layer', function() {
            var layer = layui.layer;
        });


        $('.close-modal').on('click', function () {
            layer.closeAll();
        });


        $(".mult_input_container .drop_arrow").on("click", '', function(e){
            $(e.target).hasClass("expand_arrow") ? $(e.target).removeClass("expand_arrow") : $(e.target).addClass("expand_arrow");
        })

        //点击组名 默认选中人员
        // $(".list_item_container").on("click", 'span', function(e){
        //     var groupId = $(e.target).attr('id');
        //     var originVal = $("#attend_member_select").val();
        //     var bsMultiSelect = $('#attend_member_select').BsMultiSelect();
        //     $('.dashboardcode-bsmultiselect .dropdown-menu li').each(function(idx, e){
        //         var val = $(e).attr("data-option-id");
        //         var selected = $("#attend_member_select").val() || [];
        //         (selected.indexOf(val) === -1 && mockMember
        //             .some(function(p){return p.id === groupId && p.member.map(function(m){return m.id}).indexOf(val) > -1;}))
        //         && $(e).click();
        //     });
        // });
        //常用列表 弹窗列表
        layui.use('table', function(){
            table = layui.table;
            renderGroup();
        });
        //常用列表添加按钮
        $(".add_group_btn").on("click", '', function(){
            layer.prompt({title: '添加列表', formType: 0}, function(text, index){
                $.ajax({
                    url: '${paersonAndGroupAddDelete}',
                    type: 'POST',
                    data: {groupName: text, path: "addGroup"},
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        layuiModal.alert("已添加常用组");
                        renderGroup();
                    }
                });
                layer.close(index);
                console.log(text, 'pass', index);
            });
        });

        //监听人员选择
        layui.use('form', function(){
            var form = layui.form;
            //监听人员选择框
            form.on('select(member)', function(data){
                if(currentGroup) {
                    $.ajax({
                        url: "${paersonAndGroupAddDelete}",
                        data: {participant_id: data.value, groupId: currentGroup, path: "addPerson"},
                        dataType: "json",
                        success: function (result) {
                            if ("ok" == result.state) {
                                layuiModal.alert("添加人员成功！");
                                renderMember();
                            } else {
                            }
                        }
                    });
                }else {
                    layuiModal.alert('请先选择一个常用组！');
                }

                console.log(data.elem); //得到select原始DOM对象
                console.log(data.value); //得到被选中的值
                console.log(data.othis); //得到美化后的DOM对象
            });
        });

        if ($(".mapValue").val()) {
            console.log("ok");
            var data = $(".mapValue").val();
            data = data.substring(0, data.length);
            console.log(data);
            var j = JSON.parse(data);
            console.log(j);
            for (var i in j) {
                $("select[name='conferenceType']").prepend('<option  value="' + j[i].resources_value + '">' + j[i].resources_value + '</option>');
            }
            $("select[name='branch']").prepend('<option  value="${org.org_id}">${org.org_name}</option>');
            $("select[name='branch']").val('${org.org_id}');
            $("select[name='branch']").attr("disabled", "disabled");
            refresh("ddddd");
        }
        if ($(".mapOld").val()) {
            console.log("ok");
            var data = $(".mapOld").val();
            data = data.substring(0, data.length);
            console.log(data);
            var j = JSON.parse(data);
            console.log(j);
            //   $("select[name='secondaryCommittee']").prepend('<option selected="selected" value="'+j.sconedParty+'">'+j.sconedParty+'</option>');
            $("select[name='branch']").prepend('<option  value="' + j.organization_id + '">' + j.org_name + '</option>');
            $("select[name='branch']").val(j.organization_id);
            // $("select[name='conferenceType']").prepend('<option  value="' + j.meeting_type + '">' + j.meeting_type + '</option>');
            $("select[name='conferenceType']").val(j.meeting_type);
            // $("select[name='subject']").prepend('<option  value="' + j.meeting_theme + '">' + j.meeting_theme + '</option>');
            $("input[name='subject']").val(j.meeting_theme_secondary);
            $("select[name='branch'],select[name='conferenceType'],select[name='subject']").attr("disabled", "disabled");
            $("input[name='timeDuring']").val(j.start_time);
            $("[name='timeLasts']").val(j.total_time);
            var participants = JSON.parse(j.participant_group);
            refresh("ddddd", [j.host], [j.contact], participants);
            $("select[name='campus']").val(j.campus);
            getPlace(j.place);
            // $("input[name='host']").val(j.host);
            // $("input[name='contact']").val(j.contact);
            $("input[name='phoneNumber']").val(j.contact_phone);
            $("input[name='sit']").val(j.sit);
            $("input[name='customTheme']").val(j.meeting_theme_secondary);
            if (j.attachment_name) {
                $("input[name='attachment']").parent().append("<span>" + j.attachment_name + "</span>");
            }
            $("#infrom_id").val(j.inform_id);
            $("#newAndOld").val(j.state);
            $("#meeting_id").val(j.meeting_id);
            ueditor.ready(function () {
                ueditor.setContent($(".planContent").val());
            });
            var participants = JSON.parse(j.participant_group);
            $(document).data("start_time", j.info_start);
            $(document).data("end_time", j.info_end);
            $(document).data("place", j.place);
            $(document).data("participant_group", j.participant_group);
            /*  $(document).data("organization_id",j.organization_id); */
        }
        /*计划编辑  */
        if ($(".mapedit").val()) {
            console.log("ok");
            var data = $(".mapedit").val();
            data = data.substring(0, data.length);
            console.log(data);
            var j = JSON.parse(data);
            console.log(j);
            //   $("select[name='secondaryCommittee']").prepend('<option selected="selected" value="'+j.sconedParty+'">'+j.sconedParty+'</option>');
            $("select[name='branch']").prepend('<option  value="' + j.organization_id + '">' + j.org_name + '</option>');
            $("select[name='branch']").val(j.organization_id);
            // $("select[name='conferenceType']").prepend('<option  value="' + j.meeting_type + '">' + j.meeting_type + '</option>');
            $("select[name='conferenceType']").val(j.meeting_type);
            // $("select[name='subject']").prepend('<option  value="' + j.meeting_theme + '">' + j.meeting_theme + '</option>');
            $("input[name='subject']").val(j.meeting_theme);
            $("select[name='branch'],select[name='conferenceType'],select[name='subject']").attr("disabled", "disabled");
            $("input[name='timeDuring']").val(j.start_time);
            $("[name='timeLasts']").val(j.total_time);
            $("select[name='campus']").val(j.campus);
            getPlace(j.place);
            var participants = JSON.parse(j.participant_group);
            refresh("ddddd", [j.host], [j.contact], participants);
            // $("input[name='host']").val(j.host);
            // $("input[name='contact']").val(j.contact);
            $("input[name='phoneNumber']").val(j.contact_phone);
            $("input[name='sit']").val(j.sit);
            $("input[name='customTheme']").val(j.meeting_theme_secondary);
            if (j.attachment_name) {
                $("input[name='attachment']").parent().append("<span>" + j.attachment_name + "</span>");
            }
            $("#infrom_id").val(j.inform_id);
            $("#newAndOld").val(j.state);
            $("#meeting_id").val(j.meeting_id);
            ueditor.ready(function () {
                ueditor.setContent($(".planContent").val());
            });
            $(document).data("start_time", j.info_start);
            $(document).data("end_time", j.info_end);
            $(document).data("place", j.place);
            $(document).data("participant_group", j.participant_group);
            $(document).data("organization_id", j.organization_id);
        }

        var branchId = $(document).data("organization_id");

    });
</script>
<script type="text/javascript">
    $("input[name='host']").blur(function () {
        var host = $("input[name='host']").val();
        var reg = /^[\u4E00-\u9FA5]{2,5}$/;
        if (!reg.test(host)) {
            layuiModal.alert("请输入正确人名");
        } else {
            $("input[name='contact']").focus();
        }
    });

    $("input[name='phoneNumber']").blur(function () {
        var phoneNumber = $("input[name='phoneNumber']").val();
        var mobile = /^\d{8,11}$/;
        if (!mobile.test(phoneNumber)) {
            layuiModal.alert("请输入正确电话号码");
        }
    });


    $("input[name='contact']").blur(function () {
        var host = $("input[name='host']").val();
        var reg = /^[\u4E00-\u9FA5]{2,5}$/;
        if (!reg.test(host)) {
            layuiModal.alert("请输入正确人名");
        } else {
            $("input[name='sit']").focus();
        }
    });


    function formsubmitgraft() {
        layuiModal.confirm("确定暂存吗？", function () {
            $("select[name='branch'],select[name='conferenceType'],select[name='subject'],select[name='participate']").removeAttr("disabled", "disabled");
            $('#graft').val("true");
            $('#submitFrom').click();
        });
    }

    var _pathName = window.location.pathname;

    function formsubmit() {
        var phoneNumber = $("input[name='phoneNumber']").val();
        var mobile = /^\d{8,11}$/;
        if (!mobile.test(phoneNumber)) {
            showConfirm("请输入正确电话号码。");
            return;
        }

        layuiModal.confirm("确定发布吗？", function () {
            $("select[name='branch'],select[name='conferenceType'],select[name='subject'],select[name='participate']").removeAttr("disabled", "disabled");
            $('#submitFrom').click();
        });
    }

    /*  常用人员弹窗显示 */
    $("#hg-form-container").on("click", "._commonStaff", function () {
        $(".chang_img").attr("src", "/images/radio.png");
        $(".add_btn > .btn").css("display", "block");
        $(".member_content").empty();
        $("#commonStaff").modal("show");
    });
    /* 常用人员窗口关闭 */
    $(".close_modal").click(function () {
        $("#commonStaff").modal("hide");
    });
    /* 常用人员 添加 */
    $(".add_btn").on("click", ">.btn", function () {
        $(this).css("display", "none");
        $(this).siblings("form").css("display", "inline-block");
    })

    /*  取消添加弹窗 */
    $(".add_btn").on("click", ".form_hide_btn", function () {
        $(this).parents("form").css("display", "none");
        $(this).parents("form").siblings(".btn").css("display", "inline-block");
        $(this).parents(".form-group").find(".add_member_input").val("");
    })

    /* 常用人员  添加按钮消失 */
    $(".use_group").on("click", ".check_td", function () {
        if ($(this).children("img").attr("src").indexOf("_on") > -1) {
            $(this).children("img").attr("src", "/images/radio.png");
            /* $(this).parents(".table_container").siblings(".add_btn").css("display","block"); */
        } else {
            $(".check_td img").each(function () {
                // console.log($(this).attr("src"));
                $(this).attr("src", "/images/radio.png");
            })
            $(this).children("img").attr("src", "/images/radio_on.png");
            /* $(this).parents(".table_container").siblings(".add_btn").css("display","none"); */
        }
        $(".member_content").empty();
        var groupId = $(this).find("input").val();
        <%--$.ajax({--%>
        <%--    url: '${paersonAndGroupAddDelete}',--%>
        <%--    type: 'POST',--%>
        <%--    data: {groupId: groupId, path: "getGroupPersons"},--%>
        <%--    dataType: 'json',--%>
        <%--    async: false,--%>
        <%--    success: function (data) {--%>
        <%--        console.log("图片变换" + data);--%>
        <%--        /* $(".member_content").empty(); */--%>
        <%--        for (var i = 0; i < data.length; i++) {--%>
        <%--            var c = data[i];--%>
        <%--            var tr = $('<span>' + c.member_name + '<img class="delete_img" src="/images/X-icon.png"/>' +--%>
        <%--                '<input type="hidden" value="' + c.participant_id + '"/> ' +--%>
        <%--                '</span>');--%>
        <%--            $(".member_content").append(tr);--%>
        <%--        }--%>
        <%--    }--%>
        <%--});--%>

    });

    /* 删除小组 */
    $(".use_group").on("click", ".delete_gruop", function () {
        var self = this;
        var groupId = $(this).next().val();

        layuiModal.confirm("确认要删除吗？", function () {
            // $("#hg_confirm").modal("hide");
            $(self).parent().parent().remove();
            $.ajax({
                url: '${paersonAndGroupAddDelete}',
                type: 'POST',
                data: {groupId: groupId, path: "deleteGroup"},
                dataType: 'json',
                async: false,
                success: function (data) {
                    $("select[name='participate'] option").each(function () {
                        if ($(this).attr("value") == groupId) {
                            $(this).remove();
                        }
                    });
                }
            });
        });

    });
    /*  添加小组 */
    $(".addGroup").click(function () {
        var groupName = $(".groupName").val();
        if (!groupName) {
            showConfirm("小组名不能为空！");
            return;
        }
        var branchId = $("select[name='branch']").val();
        $(this).parents(".form-horizontal").siblings("button.btn").css("display", "inline-block");
        $(this).parents(".form-horizontal").css("display", "none");
        $.ajax({
            url: '${paersonAndGroupAddDelete}',
            type: 'POST',
            data: {groupName: groupName, branchId: branchId, path: "addGroup"},
            dataType: 'json',
            async: false,
            success: function (data) {
                var tr = $(' <tr> ' +
                    '<td class="check_td"> ' +
                    '<img class="chang_img" src="/images/radio.png" />' +
                    '<input type="hidden" value="' + data.uuid + '"/> ' +
                    '</td> ' +
                    ' <td>' + groupName + '</td> ' +
                    '<td> ' +
                    '<img class="delete_gruop" src="/images/delete_icon.png"/> ' +
                    '<input type="hidden" value="' + data.uuid + '"/> ' +
                    ' </td> ' +
                    '</tr>');
                $(".use_group").append(tr);
                $(".groupName").val("");
                $("select[name='participate']").append('<option value="' + data.uuid + '">' + groupName + '</option>');

            }
        });
    });

    //添加指派人员
    $(".add_person").on("change", function () {
        var url = "${assignedAddPerson}"
        var userName = $(".add_person").val();
        var data = {userName: userName, path: "find"};
        $(".addPersons").empty();
        $.ajax({
            url: url,
            data: data,
            dataType: "json",
            success: function (result) {
                console.log(result);
                if (result.state == 'succeed') {
                    for (var i = 0; i < result.data.length; i++) {
                        var c = result.data[i];
                        $(".addPersons").append('<label><input name="Fruit" type="radio" value="' + c.user_id + '"/>' + c.user_name + '</label>');
                    }
                }
                if (result.state == 'fail') {
                    $(".addPersons").append('<p style="color:red;">' + result.message + '</p>');
                }
            }
        });

    });
    /*添加人员  */
    $(".sure_add").click(function () {
        var groupId = $("img[src='/images/radio_on.png']").next().val();
        var assigne_user_id = $('input:radio:checked').val();

        var userIds = $(".person_class");
        var resourcesId = new Array("");
        for (var i = 0; i < userIds.length; i++) {
            var resourceId = $(userIds[i]).next().val();
            resourcesId.push(resourceId);
        }
        var assigne_user_id = resourcesId.join(",").substring(1) + "";
        if (!groupId) {
            showConfirm("请先选择小组！");
            return;
        }
        if (!assigne_user_id) {
            showConfirm("请选择人员 ！");
            return;
        }
        var url = "${paersonAndGroupAddDelete}"
        var data = {participant_id: assigne_user_id, groupId: groupId, path: "addPerson"};
        $.ajax({
            url: url,
            data: data,
            dataType: "json",
            success: function (result) {
                if ("ok" == result.state) {
                    var arr = [];
                    $(".member_content input").each(function () {
                        var id = $(this).val();
                        arr.push(id);
                    })
                    var arr2 = [];
                    $(".person_class").each(function () {
                        var name = $(this).html();
                        var id = $(this).next().val();
                        arr2.push({"name": name, "id": id})
                    })

                    for (var i = 0; i < arr2.length; i++) {
                        var repeat = false;
                        for (var j = 0; j < arr.length; j++) {
                            if (arr2[i].id == arr[j]) {
                                repeat = true;
                                break;
                            }
                        }

                        if (!repeat) {
                            var tr = $('<span>' + arr2[i].name + '<img class="delete_img" src="/images/X-icon.png"/>' +
                                '<input type="hidden" value="' + arr2[i].id + '"/> ' +
                                '</span>');
                            $(".member_content").append(tr);
                        }
                    }
                    showConfirm("添加人员成功！")
                } else {
                }
            }
        });

    });


    function showConfirmDate() {
        var startTimeLong = new Date($("input[name='timeDuring']").val().replace(" ", "T") + '+08:00').getTime();
        var endTimeLong = ($("[name='timeLasts']").val()) * 60 * 1000 + startTimeLong;
        var sDate = getDateByLong($(document).data("start_time"));
        var eDate = getDateByLong($(document).data("end_time"));
        console.log(startTimeLong + " ;" + endTimeLong + " ;" + sDate + " ;" + eDate);
        var boolem = true;
        if (!startTimeLong) {
            boolem = false;
            showConfirm("请选择在规定时间范围内：   " + sDate + " —— " + eDate);
        }
        if (startTimeLong > $(document).data("end_time")) {
            boolem = false;
            showConfirm("请选择在规定时间范围内：   " + sDate + " —— " + eDate);
        } else if (endTimeLong > $(document).data("end_time")) {
            boolem = false;
            showConfirm("请选择在规定时间范围内：   " + sDate + " —— " + eDate);
        } else if (startTimeLong < $(document).data("start_time")) {
            boolem = false;
            showConfirm("提示", "请选择在规定时间范围内：   " + sDate + " —— " + eDate);
        }

        return true;
    }

    function showConfirm(info) {
        layuiModal.alert(info);
    }


    //格式化日期
    function getDateByLong(dateLong) {
        var date = new Date(dateLong);
        var moth = date.getMonth() + 1;
        if (moth < 10) {
            moth = "0" + moth;
        }
        var day = date.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        var hour = date.getHours();
        if (hour < 10) {
            hour = "0" + hour;
        }
        var minuts = date.getMinutes();
        if (minuts < 10) {
            minuts = "0" + minuts;
        }
        var ss = date.getSeconds();
        if (ss < 10) {
            ss = "0" + ss;
        }
        var defaultTime = date.getFullYear() + '-' + moth + '-' + day + ' ' + hour + ':' + minuts + ':' + ss;
        return defaultTime;
    }

    /* 支部人员点击效果切换 */
    $(".list_item_box").on("click", ".branch_person", function () {
        if ($(this).hasClass("person_class")) {
            $(this).css("background", "white");
            $(this).removeClass("person_class");
        } else {
            $(this).css("background", "#CE0000");
            $(this).addClass("person_class");
        }
    });
    /* 删除常用组人员 */
    $(".member_content").on("click", ".delete_img", function () {
        var self = this;
        var groupId = $("img[src='/images/radio_on.png']").next().val();
        var user_id = $(this).next().val();
        if (!groupId) {
            showConfirm("请先选择小组！");
            return;
        }
        if (!user_id) {
            showConfirm("请选择人员 ！");
            return;
        }
        var url = "${deletePerson}";
        var data = {groupId: groupId, user_id: user_id};
        layuiModal.confirm("确定删除吗?", function () {
            $.ajax({
                url: url,
                data: data,
                dataType: "json",
                success: function (result) {
                    if (result.state === "ok") {
                        $(self).parent().remove();
                    } else {
                        showConfirm("删除失败！");
                    }
                }
            });
        });

    });
    /*全选*/
    $(".choice_all").click(function () {
        $(this).attr("src", "/images/checked_icon.png");
        $(".branch_person").css("background", "#CE0000");
        $(".list_item_box").find(".person_class").removeClass("person_class");
        $(".branch_person").addClass("person_class");
        $(".against_choice").attr("src", "/images/not_check_icon.png");

    });
    /* 反选 */
    $(".against_choice").click(function () {
        $(this).attr("src", "/images/checked_icon.png");
        $(".branch_person").each(function () {
            if ($(this).hasClass("person_class")) {
                $(this).css("background", "white");
                $(this).removeClass("person_class");
            } else {
                $(this).css("background", "#CE0000");
                $(this).addClass("person_class");
            }
        });
        $(".choice_all").attr("src", "/images/not_check_icon.png");
    });
</script>
</body>
</html>