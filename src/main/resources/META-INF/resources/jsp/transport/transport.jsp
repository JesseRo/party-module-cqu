<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/transport/org" var="brunches"/>
<portlet:resourceURL id="/transport/save" var="save"/>
<portlet:resourceURL id="/transport/receipt" var="receipt"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/organ-relation-transfer.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <%--    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>--%>
    <%--    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>--%>
    <style>
        .organ_relation_container .organ_relation_page .form_content .custom_form {
            width: 36.24%;
        }

        .organ_relation_container .organ_relation_page .form_content .tips_container {
            width: 60%;
        }
    </style>
    <style type="text/css">
    </style>
    <portlet:resourceURL id="/org/tree" var="orgTreeUrl"/>

    <script type="text/javascript">
        $(function () {
            layui.config({
                base: '${basePath}/js/layui/module/'
            }).extend({
                treeSelect: 'treeSelect/treeSelect'
            });
            var data = ${transportJson};
            var checkedNode = null;

            layui.use(['form', 'treeSelect'], function () {
                var form = layui.form;
                var treeSelect = layui.treeSelect;

                renderTree();

                function renderTree() {
                    treeSelect.destroy('orgTree');
                    treeSelect.render({
                        // 选择器
                        elem: '#orgTree',
                        // 数据
                        data: '${orgTreeUrl}&isFilter=false',
                        // 异步加载方式：get/post，默认get
                        type: 'get',
                        // 占位符
                        placeholder: '请选择',
                        // 是否开启搜索功能：true/false，默认false
                        search: true,
                        // 点击回调
                        click: function (d) {
                            checkedNode = d.current;
                        },
                        // 加载完成后的回调函数
                        success: function (d) {
                            if (!checkedNode) {
                                checkedNode = d.data[0];
                            }
                            treeSelect.checkNode('orgTree', checkedNode.id);
                        }
                    });
                }
                //表单提交
                form.on('submit(organRelaForm)', function (data) {
                    // layer.alert(JSON.stringify(data.field), {
                    //     title: '最终的提交信息'
                    // })
                    return false;
                });
                form.on('select(transport_type)', function (data) {
                    var type = data.value;
                    if (type === '0') {
                        $('#org_name').hide();
                        $('#org_all').hide();
                        $('#org_brunch').show();
                        $('#transport_title').hide();
                    } else if (type === '1') {
                        $('#org_name').hide();
                        $('#org_brunch').hide();
                        $('#org_all').show();
                        $('#transport_title').hide();
                    } else {
                        $('#org_name').show();
                        $('#org_brunch').hide();
                        $('#org_all').hide();
                        if (type === '2') {
                            $('#transport_title').hide();
                        } else if (type === '3') {
                            $('#transport_title').show();
                        }
                    }
                    form.val("organRelaForm", {
                        transport_form: type === '3' ? "纸质" : "电子"
                    });
                    console.log(data);
                });
                form.on('select(transport_reason)', function (data) {
                    if (data.value === '其他') {
                        layuiModal.prompt("请输入原因", "", function (d) {
                            $('[name=transport_reason]').next().find('input').val(d);
                        })
                    }
                });
                if (data != null) {
                    form.val("organRelaForm", {
                        transport_type: data.type,
                        org: data.to_org_name,
                        org_name: data.to_org_id,
                        transport_form: data.type === '3' ? "纸质" : "电子",
                        transport_title: data.to_org_title,
                        transport_reason: data.reason
                    });
                }
            });
            //时间选择器
            layui.use('laydate', function () {
                var laydate = layui.laydate;
                //执行一个laydate实例
                laydate.render({
                    elem: '#date', //指定元素
                });
            });

            var groups = null;

            $('._submit').on('click', function (e) {
                if (${already} >0){
                    return
                }
                var type = $('[name=transport_type]').val();
                var org;
                if (type === '0') {
                    org = $('#org_brunch_select').val();
                } else if (type === '1') {
                    org = $('#org_all_select').val();
                } else {
                    org = $('#org').val();
                }
                var form = $('[name=transport_form]').val();
                var title = $('[name=transport_title]').val();
                var reason = $('[name=transport_reason]').val();
                if(reason === '其他'){
                    var otherReason = $('[name=transport_reason]').next().find('input').val();
                    otherReason && (reason = otherReason);
                }
                var isResubmit = $('[name=isResubmit]').val();
                $.post('${save}', {
                    type: type,
                    org: org,
                    form: form,
                    title: title,
                    reason: reason,
                    isResubmit: isResubmit
                }, function (res) {
                    if (res.result) {
                        layuiModal.alert('提交成功', function () {
                            window.location.href = '/transport_out';
                        });
                    } else {
                        layuiModal.alert("失败");
                    }
                })
            });

            if (false) {
                $('.dropdown-sin-2').data('dropdown').choose([]);

            }
            $('#upload-block [type="file"]').change(function () {
                $('#upload-block [type="submit"]').click();
            })
            $('#uploadReceipt').on('click', function () {
                $('#upload-block [type="file"]').click();
            })

            $('#resubmit').on('click', function () {
                window.location.href = "/transport_out?resubmit=1"
            })


        });
    </script>
</head>
<body>
<div class="organ_relation_container table_form_content">
    <div class="organ_relation_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">组织关系转出</a>
                    </span>
        </div>
        <div class="form_content">
            <form class="layui-form custom_form" id="organRelaForm" lay-filter="organRelaForm">
                <div class="layui-form-item">
                    <label class="layui-form-label">转出类型：</label>
                    <div class="layui-input-block">
                        <c:choose>

                            <c:when test="${already > 0 }">
                                <select name="transport_type" disabled lay-filter="transport_type"
                                        class="organRelaForm">
                                    <option value="0">院内</option>
                                    <option value="1">校内</option>
                                    <option value="2">重庆市内</option>
                                    <option value="3" selected="">重庆市外</option>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <select name="transport_type" lay-filter="transport_type" class="organRelaForm">
                                    <option value="0">院内</option>
                                    <option value="1">校内</option>
                                    <option value="2">重庆市内</option>
                                    <option value="3" selected="">重庆市外</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <input name="isResubmit" value="${isResubmit}" type="hidden">
                <div class="layui-form-item">
                    <label class="layui-form-label">去往单位：</label>
                    <div class="layui-input-block" id="org_name">
                        <c:choose>
                            <c:when test="${already > 0 }">
                                <input type="text" disabled id="org" placeholder="按照转入单位要求填转入往单位" name="org"
                                       autocomplete="off" class="layui-input">
                            </c:when>
                            <c:otherwise>
                                <input type="text" id="org" placeholder="按照转入单位要求填转入往单位" name="org" autocomplete="off"
                                       class="layui-input">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <%--                <div class="dropdown-sin-2" style="display: none;" id="org_id">--%>
                    <%--                    <select name="org" style="display:none;" multiple placeholder="请选择" id="org_select"></select>--%>
                    <%--                </div>--%>
                    <div class="layui-input-block" id="org_brunch" style="display: none;">
                        <select name="org_name" id="org_brunch_select">
                            <option value="">请选择</option>
                            <c:forEach var="group" items="${brunchGroup}">
                                <optgroup label="${group.key}">
                                    <c:forEach var="brunch" items="${group.value}">
                                        <option value="${brunch.org_id}">${brunch.org_name}</option>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="layui-input-block" id="org_all" style="display: none;">
                        <div class="layui-form-item" style="clear: none;">
                            <div class="layui-inline" style="width: 100%;">
                                <div class="layui-input-inline orgTree" style="width: 100%;">
                                    <input type="text" name="orgTree" id="orgTree" lay-filter="orgTree"
                                           placeholder="请选择组织" class="layui-input">
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">转出形式：</label>
                    <div class="layui-input-block">
                        <select name="transport_form" lay-filter="organRelaForm" disabled>
                            <option value="纸质" selected="">纸质</option>
                            <option value="电子">电子</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text" id="transport_title">
                    <label class="layui-form-label">介绍信抬头：</label>
                    <div class="layui-input-block">
                        <c:choose>
                            <c:when test="${already > 0 }">
                                <textarea maxlength="200" disabled name="transport_title"
                                          placeholder="转入/挂靠党组织名称（详见注意事项）" class="layui-textarea"></textarea>
                            </c:when>
                            <c:otherwise>
                                <textarea maxlength="200" name="transport_title" placeholder="转入/挂靠党组织名称（详见注意事项）"
                                          class="layui-textarea"></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label" id="reason_title">转出原因：</label>
                    <div class="layui-input-block">
                        <c:choose>
                            <c:when test="${already > 0 }">
                                <input type="text" disabled name="transport_reason" autocomplete="off" class="layui-input" lay-filter="transport_reason">
                            </c:when>
                            <c:otherwise>
                                <select name="transport_reason" lay-filter="transport_reason">
                                    <option value="升学" selected="">升学</option>
                                    <option value="工作">工作</option>
                                    <option value="其他">其他</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <c:if test="${already == 0 }">
                    <div class="layui-form-item btn_group">
                        <div class="layui-input-block">
                            <button type="button" class="layui-btn _submit" lay-submit="" lay-filter="organRelaForm">
                                提交
                            </button>
                        </div>
                    </div>
                </c:if>
                <c:if test="${already > 0 }">
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">转出状态：</label>
                        <div class="layui-input-block">
                            <c:choose>
                                <c:when test="${transport.status eq 0}">
                                    <p style="float: left;line-height: 35px;color: red;"
                                       id="status">${currentOrg} ${statusList[transport.status]}</p>
                                </c:when>
                                <c:otherwise>
                                    <p style="float: left;line-height: 35px;color: red;"
                                       id="status">${statusList[transport.status]}</p>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${(transport.status gt 1 and transport.type eq 3) or transport.status eq 2}">
                                <button id="resubmit" type="button" class="layui-btn layui-btn-primary"
                                        style="margin-left: 15px; float:right;">
                                    重拟申请
                                </button>
                            </c:if>
                            <c:if test="${transport.status eq 1 and transport.type eq 3}">
                                <button type="button" class="layui-btn layui-btn-primary" id="uploadReceipt"
                                        style="float: right;">上传回执
                                </button>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </form>
            <div id="upload-block" style="display: none;">
                <form action="${receipt}" method="post" target="uploadTarget"
                      enctype="multipart/form-data">
                    <input type="file" name="receipt">
                    <input type="submit">
                    <iframe name="uploadTarget"></iframe>
                </form>
            </div>
            <div class="tips_container">
                <p class="tips_title">注意事项</p>
                <p>1.请与转入单位党务工作部门（非人事）落实；</p>
                <p> 2.市外原则上需县级以上党组织，不应出现支部、街道等；</p>
                <p>3.市外则通过12371平台进行转接，抬头需落实到支部；</p>
                <p>
                    4.以转入“重庆大学外国语学院”为例，【由市外转入】去往单位：重庆大学外国语学院，介绍信抬头：重庆市委教育工委；【由市内转入】去往单位：重庆大学外国语学院，介绍信抬头：市委教工委-重庆大学党委-外语学院党委-XX支部</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>