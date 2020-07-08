<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/hg/groupAndMember" var="groupsAndMembers"/>
<portlet:resourceURL id="/hg/group/page" var="getGroupList"/>
<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>

<portlet:resourceURL id="/hg/getPlace" var="getPlace"/>
<portlet:resourceURL id="/hg/member/getMember" var="getMember"/>
<portlet:resourceURL id="/hg/member/list" var="memberList"/>
<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl"/>
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl"/>
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl"/>
<!-- 通用文件上传 -->
<portlet:resourceURL id="/api/upload" var="uploadFileApi"/>
<portlet:resourceURL id="/hg/place/list" var="places"/>
<portlet:resourceURL id="/hg/place/add" var="addPlace"/>
<!-- 常用人员，组的增加，修改，删除 -->
<portlet:resourceURL id="/hg/paersonAndGroupAddDelete" var="personAndGroupAddDelete"/>
<portlet:resourceURL id="/hg/meetingPlan/save" var="saveMeetingPlan"/>
<html>
<head>
    <style type="text/css">
        .layui-btn-xs {
            height: 22px !important;
            line-height: 22px!important;
            padding: 0 5px!important;
            font-size: 12px!important;
        }

        .layui-form-label.layui-required:before {
            content: "*";
            color: red;
            top: 5px;
            right: 2px;
            position: relative;
        }
        .layui-form .layui-form-label{
            padding: 9px 15px !important;
            width: 160px !important;
            line-height: 20px !important;
        }
        .layui-form .layui-input-block {
            margin-left: 160px;
        }
        .layui-form .layui-form-item .layui-input-inline{
            width: 260px;
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
        #groupModal .layui-card-body{
            height:400px;
        }
        #groupModal #addGroupBtn{
            position: absolute;
            right: 20px;
            margin-top: 6px;
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
        .layui-table-box table thead, .layui-table-box tbody tr{
            display: table-row;
            width: 100%;
            table-layout: fixed;
        }
        .layui-layer-page.addGroupMemberModal-skin .layui-layer-content {
            overflow: visible;
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
        <div class="bg_white_container">
            <div class="content_form form_container">
                <form class="layui-form custom_form"  id="addMeetingPlanForm"
                      style="width: 960px;">
                    <input type="hidden" name="meetingId" value='${meetingPlan.meeting_id }'>
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
                                <input type="text" class="layui-input" name="subject"  lay-verify="required" autocomplete="on" value="${meetingPlan.meeting_theme }">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开始时间：</label>
                            <div class="layui-input-inline">
                                <input type="text" name="timeDuring" id="timeDuring" value="${meetingPlan.start_time }"
                                       class="layui-input start_date" lay-verify="required" autocomplete="off">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">计划时长：</label>
                            <div class="layui-input-inline">
                                <select  name="timeLasts" lay-verify="select">
                                    <option  value="" disabled>请选择</option>
                                    <c:forEach var="n" items="${timeLasts }">
                                        <c:if test="${meetingPlan.total_time == n/60 }">
                                            <option value="${n}" selected>${n/60}小时</option>
                                        </c:if>
                                        <c:if test="${meetingPlan.total_time != n/60 }">
                                            <option value="${n}">${n/60}小时</option>
                                        </c:if>
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
                                    <option value="">请选择</option>
                                    <c:forEach var="n" items="${campus }">
                                        <c:if test="${meetingPlan.campus ==n }">
                                            <option value="${n}" selected>${n}</option>
                                        </c:if>
                                        <c:if test="${meetingPlan.campus !=n }">
                                            <option value="${n}" >${n}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">开展地点：</label>
                            <div class="layui-input-inline">
                                <select  name="location" lay-verify="select" lay-filter="location">
                                </select>
                            </div>
                            <a href="javascript:void(0)" id="addPlace" ><i class="layui-icon layui-icon-addition"></i></a>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">主持人：</label>
                            <div class="layui-input-inline">
                                <select name="host" lay-search="" multiple="" lay-verify="select"  placeholder="可搜索可输入">
                                    <option value="">请选择</option>
                                    <c:forEach var="m" items="${members}">
                                        <option value="${m.member_identity}">${m.member_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">列席人员：</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="sit"  autocomplete="off" value="${meetingPlan.sit}">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">联系人：</label>
                            <div class="layui-input-inline">
                                <select name="contact" lay-search="" lay-verify="select" lay-filter="contact" placeholder="可搜索可输入">
                                    <option value="">请选择</option>
                                    <c:forEach var="m" items="${members}">
                                        <option value="${m.member_identity}" phone="${m.member_phone_number}">${m.member_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">联系电话：</label>
                            <div class="layui-input-inline">
                                <input name="phoneNumber" class="layui-input"  lay-verify="required" autocomplete="off" value="${meetingPlan.contact_phone}">
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
                            <div class="layui-input-inline" style="width:700px;display:none" id="memberGroup">
                                <p>
                                    常用分组:
                                    <button type="button" class="layui-btn layui-btn-xs layui-btn-warm" id="edit_group_member">编辑</button>
                                </p>
                                <div class="list_item_container" id="group_member_list">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">短信自动通知：</label>
                            <div class="layui-input-block">
                                <input type="checkbox" name="autoPhoneMsg" lay-skin="switch"  lay-filter="autoPhoneMsg" lay-text="是|否">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">计划内容：</label>
                            <div class="layui-input-inline meetingContent">
                                <script id="meetingContent" name="meetingContent" type="text/plain">
                                    ${meetingPlan.content}
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">附件上传：</label>
                            <div class="layui-input-block layui-upload-drag meetingContent" id="upload">
                                <i class="layui-icon"></i>
                                <p>点击上传，或将文件拖拽到此处</p>
                            </div>
                            <div class="layui-input-block  meetingContent" >
                                <table id="fileTable" lay-filter="fileTable"></table>
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
            </div>
        </div>
    </div>
    <div id="groupModal" style="display: none;padding: 10px 0px;">
        <div class="layui-row">
            <div class="layui-col-md9">
                <div class="layui-card">
                    <div class="layui-card-header">分组列表<button type="button" id="addGroupBtn" class="layui-btn layui-btn-warm layui-btn-sm">添加分组</button></div>
                    <div class="layui-card-body">
                        <table id="groupTable" lay-filter="groupTable"></table>
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="layui-card">
                    <div class="layui-card-header">组内成员</div>
                    <div class="layui-card-body">
                        <table id="groupMemberTable" lay-filter="groupMemberTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="addPlaceModal" style="display: none;padding: 10px 0px;" >
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" value="0">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">开展地点:</label>
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
    <div id="addGroupModal" style="display: none;padding: 10px 0px;">
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" value="1">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">分组名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="groupName" lay-verify="required" lay-reqtext="分组名称是必填项，不能为空。" placeholder="请输入分组名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="addGroupForm">确定</a>
                <a class="layui-layer-btn1">取消</a>
            </div>
        </form>
    </div>
    <div id="addGroupMemberModal" style="display: none;padding: 10px 0px;">
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" value="0" name="groupId">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">分组名称:</label>
                    <div class="layui-input-inline">
                        <label class="layui-form-label-text" name="groupName"></label>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">选择成员:</label>
                    <div class="layui-input-inline">
                        <select name="groupMember" multiple lay-search lay-verify="select"  placeholder="可搜索可输入">
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="addGroupMemberForm">确定</a>
                <a class="layui-layer-btn1">取消</a>
            </div>
        </form>
    </div>
    <div id="editGroupModal" style="display: none;padding: 10px 0px;" >
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" value="0" name="groupId" >
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">分组名称:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="groupName" lay-verify="required" lay-reqtext="分组名称是必填项，不能为空。" placeholder="请输入分组名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="editGroupForm">确定</a>
                <a class="layui-layer-btn1">取消</a>
            </div>
        </form>
    </div>

</div>
<script type="text/html" id="tableTool">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="addGroupMember">添加组内成员</a>
</script>
<script type="text/html" id="memberTableTool">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">移除</a>
</script>
<script type="text/html" id="fileTableTool">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
<script type="text/javascript">
    layui.config({
        base: '${basePath}/js/layui/module/'
    }).extend({
        checkbox: 'checkbox/checkbox'
    });
    layui.use(['laydate','layer','form','table','checkbox','upload'], function(){
        var laydate = layui.laydate,
            form = layui.form,
            layer = layui.layer,
            table = layui.table,
            upload = layui.upload,
            checkbox = layui.checkbox;
        var pageInfo = {
            page:1,
            size:10
        };
        var checkedGroup =  new Array();
        var fileData;
        var attachment = '${meetingPlan.attachment}';
        if(attachment ==''||attachment=='null'){
            fileData = new Array();
        }else{
            fileData =  eval("(" + attachment + ")");
        }
        renderEditor();
        renderUpload();
        renderDateSelect();
        renderMemberGroups();
        renderFilesTable();
        renderSelectData();
        if('${meetingPlan.place}'!='' && '${meetingPlan.place}' !='null' && '${meetingPlan.place}' > '0'){
            renderPlace('${meetingPlan.place}');
        }
        if('${meetingPlan.autoPhoneMsg}'!='' && '${meetingPlan.autoPhoneMsg}' !='null'){
            var autoMsg = '${meetingPlan.autoPhoneMsg}' == '1'?'on':'off';
            renderAutoPhoneMsg(autoMsg);
        }
        function renderAutoPhoneMsg(autoMsg){
            $("#addMeetingPlanForm input[name='autoPhoneMsg']").val(autoMsg);
            if(autoMsg){
                $("#addMeetingPlanForm input[name='autoPhoneMsg']").attr('checked', 'checked');
            }
            form.render('checkbox');
        }
        function renderSelectData(){
            var campus = '${meetingPlan.campus}';
            if(campus != '' && campus != 'null'){
                $('#addMeetingPlanForm  select[name="campus"]').val(campus);
            }
            var place = '${meetingPlan.place}';
            if(place != '' && place != 'null'){
                $('#addMeetingPlanForm  select[name="place"]').val(place);
            }
            var host= '${meetingPlan.host}';
            if(host != '' && host != 'null'){
                $('#addMeetingPlanForm  select[name="host"]').val(host);
            }
            var contact= '${meetingPlan.contact}';
            if(contact != '' && contact != 'null'){
                $('#addMeetingPlanForm  select[name="contact"]').val(contact);
            }
            if('${meetingplan.autoPhoneMsg}' == '1'){
                $("input[name='autoPhoneMsg']").val("on");
            }else{
                $("input[name='autoPhoneMsg']").val("off");
            }
            if('${participate}'!=''){
                var arr = '${participate}'.split(",");
                renderParticipate(arr);
            }
            form.render();
        }

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
            ueditor.ready(function() {
                ueditor.setHeight(400);
            });
        }
        function renderUpload(){
            //拖拽上传
            upload.render({
                elem: '#upload'
                ,url: '${uploadFileApi}&ableDelete=0&bucket=meetingPlanFiles' //改成您自己的上传接口
                ,size: 204800 //最大200M
                ,multiple: true
                ,accept: 'file'
                ,done: function(res){
                    if(res.code == 200){
                        if(fileData.length>6){
                            layer.msg('文件不能超过6个，如果数量过多请压缩后再上传。');
                        }else{
                            layer.msg('上传成功');
                            fileData.push({
                                name:res.data.name,
                                size:res.data.size,
                                status:true,
                                path:res.data.path
                            })
                            renderFilesTable();
                        }
                    }else{
                        layer.msg(res.message);
                    }

                }
            });
        }
        function renderPlace(placeId) {
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
                            if(placeId != null && parseInt(placeId)==res.data[i].id){
                                $('#addMeetingPlanForm  select[name="location"]').append('<option  value="'+res.data[i].id+'" selected>'+res.data[i].place+'</option>');
                            }else{
                                $('#addMeetingPlanForm  select[name="location"]').append('<option  value="'+res.data[i].id+'" >'+res.data[i].place+'</option>');
                            }

                        }
                        form.render();
                    } else {
                        layer.msg('获取地点失败.');
                    }
                },
                error: function () {
                    layer.msg('获取地点失败.');
                }
            });
        }
        function renderMemberGroups(){
            $("#group_member_list").empty();
            $.post('${groupsAndMembers}', function(res){
                if(res.result){
                    var nodes = new Array();
                    for(var i=0;res.data.length>0 && i<res.data.length;i++){
                        var tipsContentArr = new Array();
                        var memberArr = res.data[i].member;
                        var memberIdArr = new Array();
                        for(var j=0;j<memberArr.length &&memberArr.length>0;j++){
                            tipsContentArr.push(memberArr[j].name);
                            memberIdArr.push(memberArr[j].id);
                        }
                        var node = {
                            id:res.data[i].id,
                            name:res.data[i].name,
                            type:res.data[i].id,
                            memberIdArr:memberIdArr,
                            on:checkedGroup.indexOf(res.data[i].id)<0?false:true,
                            tipsContent:tipsContentArr.join(";")
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
                            if(node.on){
                                checkedGroup.push(node.id);
                            }else{
                                var index = checkedGroup.indexOf(node.id);
                                if (index > -1) {
                                    checkedGroup.splice(index, 1);
                                }
                            }
                            refreshParticipate(nodes);
                        }
                    });
                }else {
                    layer.msg(res.message);
                }
            })
        }
        function renderParticipate(arr){
            $('#addMeetingPlanForm  select[name="participate"]').val(arr);
            form.render('select');
        }
        function refreshParticipate(nodes){
            var arr = [];
            for(var i=0;i<nodes.length&&nodes.length>0;i++){
                var node = nodes[i];
                if(checkedGroup.indexOf(node.id)>=0){
                    for(var j=0;j<node.memberIdArr.length && node.memberIdArr.length>0;j++){
                        arr.push(node.memberIdArr[j]);
                    }
                }
            }
            renderParticipate(arr);
        }
        function renderFilesTable(){
            var cols = [[
                {field: 'name', align:'center', title: '文件名',templet:function(d){
                        return '<a href="javascript:void(0)" path="'+d.path+'" name="'+d.name+'" onclick="downloadFile()">'+d.name+'</a>';
                    }
                }
                ,{field: 'size', width:120, align:'center', title: '大小',templet:function(d){
                        return (d.size/(1024*1024)).toFixed(2)+"M";
                    }}
                ,{field: 'status', width:80, align:'center', title: '状态',templet:function(d){
                        if(d.status){
                            return '成功';
                        }else{
                            return '失败';
                        }
                    }
                }
                ,{field: 'name', title: '操作', width:80, align:'center',toolbar: '#fileTableTool'}
            ]];
            var ins = table.render({
                elem: '#fileTable'
                ,data: fileData
                ,cols: cols
            });
            //监听事件
            table.on('tool(fileTable)', function(obj){
                switch(obj.event){
                    case 'delete':
                        deleteFile(obj.data);
                        break;
                    case 'edit':
                        break;
                };
            });
        }
        function deleteFile(file){
           var  files = new Array();
           for(var i=0;i<fileData.length&&fileData.length>0;i++){
               if(file.path!=fileData[i].path){
                   files.push(fileData[i]);
               }
           }
           fileData = files;
           renderFilesTable();
        }
        function renderGroupTable(page,size){
            var  where = {};
            var cols = [[
                {field: 'id', align:'center', width:120, title: '编号'}
                ,{field: 'group_name', align:'center', title: '分组名字'}
                ,{field: 'id', title: '操作', width:240, align:'center',toolbar: '#tableTool'}
            ]];
            var ins = table.render({
                elem: '#groupTable'
                ,where: where
                ,height:450
                ,url: '${getGroupList}'//数据接口
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
            table.on('tool(groupTable)', function(obj){
                switch(obj.event){
                    case 'delete':
                        deleteGroup(obj.data);
                        break;
                    case 'edit':
                        editGroup(obj.data);
                        break;
                    case 'addGroupMember':
                        addGroupMember(obj.data);
                        break;
                };
            });
            table.on('row(groupTable)', function(obj){
                renderGroupMemberTable(obj.data.group_id);
            });
        }
        function renderGroupMemberTable(groupId){
            var cols = [[
                {field: 'member_name', align:'center', title: '姓名'}
                ,{field: 'group_member_id', title: '操作', width:80, align:'center',toolbar: '#memberTableTool'}
            ]];
            if(groupId == null){
                table.render({
                    elem: '#groupMemberTable'
                    ,height:450
                    ,page: false
                    ,data:[]
                    ,cols: cols
                });
            }else{
                $.ajax({
                    url: '${memberList}',
                    type: 'POST',
                    data: {groupId: groupId,isExist:true},
                    dataType: 'json',
                    async: false,
                    success: function (res) {
                        if(res.code==200){
                            table.render({
                                elem: '#groupMemberTable'
                                ,height:450
                                ,page: false
                                ,cols: cols
                                ,data:res.data
                            });
                        }
                    }
                });
            }
            //监听事件
            table.on('tool(groupMemberTable)', function(obj){
                switch(obj.event){
                    case 'delete':
                        deleteGroupMember(obj.data);
                        break;
                    case 'edit':
                        break;
                };
            });
        }
        form.on('select(campus)', function(data){
            if(data.value !=""){
                renderPlace(null);
            }
        });
        form.on('select(contact)', function(data){
            var phone = $('#addMeetingPlanForm  select[name="contact"] option:selected').attr("phone");
            $('#addMeetingPlanForm input[name="phoneNumber"]').val(phone);
        });
        form.on('submit(addPlaceForm)', function(data){
            $.post("${addPlace}", {place: data.field.place, campus: $('#addMeetingPlanForm  select[name="campus"]').val()}, function (res) {
                if (res.code ==200) {
                    layer.msg("添加成功。")
                    renderPlace(null);
                }
            },'json');
        });
        form.on('submit(addGroupForm)', function(data){
            $.ajax({
                url: '${personAndGroupAddDelete}',
                type: 'POST',
                data: {groupName: data.field.groupName, path: "addGroup"},
                dataType: 'json',
                async: false,
                success: function (res) {
                    if(res){
                        layer.msg("添加成功。")
                        renderMemberGroups();
                        renderGroupTable(1,pageInfo.size);
                    }
                }
            });
        });
        form.on('submit(editGroupForm)', function(data){
            $.ajax({
                url: '${personAndGroupAddDelete}',
                type: 'POST',
                data: {groupId:data.field.groupId,groupName: data.field.groupName, path: "editGroup"},
                dataType: 'json',
                async: false,
                success: function (res) {
                    if(res){
                        layer.msg("修改成功。")
                        renderMemberGroups();
                        renderGroupTable(pageInfo.page,pageInfo.size);
                    }
                }
            });
        });
        form.on('submit(addGroupMemberForm)', function(data){
            $.post("${personAndGroupAddDelete}", {groupId: data.field.groupId,groupMember:data.field.groupMember.join(","),path:'addGroupMember' }, function (res) {
                if (res) {
                    renderMemberGroups();
                    layer.msg("添加成功。")
                }
            },'json');
        });
        //监听指定开关
        form.on('switch(autoPhoneMsg)', function(data){
            $("input[name='autoPhoneMsg']").val(data.othis);
        });
        form.on('submit(meetingPlanSave)', function(data){
            var postData= data.field;
            postData.graft = true;
            postData.host =  postData.host.join(",");
            postData.attachment = JSON.stringify(fileData);
            postData.participate =  postData.participate.join(",");
            $.post("${saveMeetingPlan}", postData, function (res) {
                if (res.code==200) {
                    layer.msg("保存成功。");
                    setTimeout(function(){window.location.href = res.data}, 1000);
                }
            },'json');
            return false;
        });
        form.on('submit(meetingPlanRelease)', function(data){
            var postData= data.field;
            postData.graft = false;
            postData.host =  postData.host.join(",");
            postData.participate =  postData.participate.join(",");
            postData.attachment = JSON.stringify(fileData);
            $.post("${saveMeetingPlan}", postData, function (res) {
                if (res.code==200) {
                    layer.msg("发布成功。");
                    setTimeout(function(){window.location.href = res.data}, 1000);
                }
            },'json');
            return false;
        });
        //验证
        form.verify({
            select: function (value, item) {
                if (value == '' || value == null) {
                    return "此选项为必填项。";
                }
            },
            phoneNumber: function(value, item){
                var regPhone = /^1\d{10}$/;
                if(value == ''&& value == null || !regPhone.test(value)) {
                    return "请输入正确联系电话。";
                }
            }
        });
        $('#edit_group_member').on('click', function () {
            renderGroupTable(1,pageInfo.size);
            renderGroupMemberTable(null);
            layer.open({
                title:'常用分组列表',
                area: ['960px','560px'],
                type: 1,
                content: $("#groupModal")
            });
        });
        $('#addPlace').on('click', function () {
            var campus = $('#addMeetingPlanForm  select[name="campus"]').val();
            if (!campus) {
                layer.alert("请先选择校区");
            } else {
                layer.open({
                    type: 1,
                    area: ['520px','240px'],
                    title:'添加开展地点',
                    content: $("#addPlaceModal"),
                    shadeClose: true
                });
            }
        });
        $('#addMeetingPlanForm  select[name="campus"]').change(function(){
            renderPlace(null);
        });
        $('#addGroupBtn').on('click', function () {
            layer.open({
                type: 1,
                area: ['520px','240px'],
                title:'添加分组',
                content: $("#addGroupModal"),
                shadeClose: true
            });
        });
        function deleteGroup(group){
            layer.confirm('您确认删除吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    url: '${personAndGroupAddDelete}',
                    type: 'POST',
                    data: {groupId: group.group_id, path: "deleteGroup"},
                    dataType: 'json',
                    async: false,
                    success: function (res) {
                        if(res){
                            layer.msg("删除成功。")
                            renderMemberGroups();
                            renderGroupTable(pageInfo.page,pageInfo.size);
                        }
                    }
                });
            });
        }
        function editGroup(group){
            $('#editGroupModal  input[name="groupId"]').val(group.group_id);
            $('#editGroupModal  input[name="groupName"]').val(group.group_name);
            layer.open({
                type: 1,
                area: ['520px','240px'],
                title:'编辑分组',
                content: $("#editGroupModal"),
                shadeClose: true
            });
        }
        function addGroupMember(group){
            $.ajax({
                url: '${memberList}',
                type: 'POST',
                data: {groupId: group.group_id,isExist:false},
                dataType: 'json',
                async: false,
                success: function (res) {
                    if(res.code==200){
                        $('#addGroupMemberModal  input[name="groupId"]').val(group.group_id);
                        $('#addGroupMemberModal  label[name="groupName"]').text(group.group_name);
                        $('#addGroupMemberModal  select[name="groupMember"]').empty();
                        $('#addGroupMemberModal  select[name="groupMember"]').append('<option value="">请选择</option>');
                        for(var i=0;res.data.length>0&&i<res.data.length;i++){
                            $('#addGroupMemberModal  select[name="groupMember"]').append('<option value="'+res.data[i].member_identity+'">'+res.data[i].member_name+'</option>');
                        }
                        form.render();
                        layer.open({
                            type: 1,
                            skin: 'addGroupMemberModal-skin',
                            area: ['640px','300px'],
                            title:'添加分组成员',
                            content: $("#addGroupMemberModal"),
                            shadeClose: true
                        });
                    }
                }
            });
        }
        function deleteGroupMember(member){
            layer.confirm('您确认删除吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    url: '${personAndGroupAddDelete}',
                    type: 'POST',
                    data: {groupMemberId:member.group_member_id, path: "deleteGroupMember"},
                    dataType: 'json',
                    async: false,
                    success: function (res) {
                        if(res){
                            layer.msg("删除成功。")
                            renderGroupMemberTable(member.group_id);
                        }
                    }
                });
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
    function downloadFile(o){
        var path = $(o).attr("path");
        var name = $(o).attr("name");
        window.location.href="${downloadUrl}&filePath="+path+"&fileName="+name;
    }

</script>
</body>
</html>
