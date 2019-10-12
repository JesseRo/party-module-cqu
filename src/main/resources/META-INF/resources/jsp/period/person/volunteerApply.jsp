
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<portlet:actionURL name="/brunch/report/add" var="add"/>

<portlet:resourceURL id="/brunch/report/upload" var="upload"/>

<html>
	<head>
	<style type="text/css">
	    input#button1 {
		    float: right;
		}
		textarea {
		    width: 100%;
		    height: 100px;
		}

		input#button2 {
        float: left;
       }

       /* 常用人员弹窗样式 */
        #commonStaff thead {
            border-bottom: 1px solid #d8d8d8;
            background: #f5f5f5;
        }

        #commonStaff .modal-body {
            max-height: 400px;
        }

        #commonStaff .common_list {
            border-right: none;
        }

        #commonStaff .common_list tr td {
            cursor: pointer;
        }

        #commonStaff .common_list tr td:nth-child(1) {
            cursor: default;
        }

        #commonStaff table td {
            font-size: 13px;
        }

        #commonStaff th,
        #commonStaff .common_list td {
            border: none;
        }

        #commonStaff .modal-body>div {
            padding: 0;
        }

        #commonStaff .modal-body>div table {
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
        }

        #commonStaff .add_btn img {
            margin-right: 5px;
        }

        .table_container {
            height: 200px;
            overflow-y: auto;
            border: 1px solid #d8d8d8;
        }

        .common_list_container .table_container {
            border-right: none;
        }

        #commonStaff .table_title {
            text-align: center;
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
	</style>

	    <link rel="stylesheet" href="${basePath}/css/assign.css" />
        <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript" >
		    var _gNumber = 1;
			function clearGroup(){
				$('.report-data-group').html('');
				console.log('clear group');
				_gNumber = 1;
			}
			function addAll(group){
				for(var i in group){
					addOne(group[i]);
				}
				console.log('addAll');
			}
		    function addOne(data){
		        if(!data){
		            data = {
		                code: '',
		                name: '',
		                sex: '',
		                position: '',
		                party_age: '',
		                join_date: '',
		                should_fee: '',
		                paid_fee: '',
		                extra: ''
		            }
		        }
		        var reportHtml = '<div class="report-data form-group">'
		                        + '<div style="height: 35px;font-size: 20px;">数据' + _gNumber + '：</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">教学工号</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" name="code" value="' + data.code + '">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">党员姓名</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" name="name" value="' + data.name + '">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">党员性别</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" name="sex" value="' + data.sex + '">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">党员职位</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" name="position" value="' + data.position + '">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">党员党龄</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" type="number" name="party_age" value="' + data.party_age + '">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">入党日期</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="datetime form-control" name="join_date" value="' + data.join_date + '" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'})">'
                                + '    </div>'
                                + '</div>'
                                + '<div class="col-sm-6 col-xs-12">'
                                + '    <div class="col-sm-3 col-xs-3 ">'
                                + '        <span class="control-label form-label-required">应缴党费</span>'
                                + '    </div>'
                                + '    <div class="col-sm-9 col-xs-9">'
                                + '        <input class="form-control" type="number" name="should_fee" value="' + data.should_fee + '">'
                              + '      </div>'
                              + '  </div>'
                              + '  <div class="col-sm-6 col-xs-12">'
                              + '      <div class="col-sm-3 col-xs-3 ">'
                              + '          <span class="control-label form-label-required">实缴党费</span>'
                              + '      </div>'
                              + '      <div class="col-sm-9 col-xs-9">'
                              + '          <input class="form-control" type="number" name="paid_fee" value="' + data.paid_fee + '">'
                              + '      </div>'
                              + '  </div>'
                              + '  <div class="col-sm-6 col-xs-12">'
                              + '      <div class="col-sm-3 col-xs-3 ">'
                              + '          <span class="control-label form-label-required">备注信息</span>'
                              + '      </div>'
                              + '      <div class="col-sm-9 col-xs-9">'
                              + '          <input class="form-control" name="extra" value="' + data.extra + '">'
                              + '      </div>'
                              + '</div>'
                              + '</div>';
                $('.report-data-group').append(reportHtml);
                _gNumber = _gNumber + 1;
		    }
            $(function() {
            	$('#upload-block [type="file"]').change(function(){
               				$('#upload-block [type="submit"]').click();
               			})

                $('#import').on('click', function(){
                    $('#upload-block [type="file"]').click();
                });

                $('#send').on('click', function(){
                    var datas = [];
                    $('.report-data').each(function(i, e){
                        var inputs = $(e).find('.form-control');
                        var data = {};
                        inputs.each(function(k, input){
                            data[$(input).attr('name')] = $(input).val();
                        });
                        datas.push(data);
                    })
                    $('#json').val(JSON.stringify(datas));
                    $('#submit').click();
                });

                $('#plus').on('click', function(){
                    addOne(null);
                });

                var d = ${design};
                addAll(d);
            })


		</script>
	</head>
	<body>
        <div class="content_title">
            <label>数据上报</label>
            <c:if test="${type != 'detail'}">
            <img id="plus" src="/images/plus.png" style=" width: 35px; margin-left: 68%; ">
            <button id="import" type="button" class="btn btn-default col-sm-2 col-xs-4" style="float: right;">
                <img src="/images/import_icon.png"> 导入excel
            </button>
            </c:if>
            <div id="upload-block" style="display: none;">
                <form action="${upload}" method="post" target="uploadTarget" enctype="multipart/form-data">
                    <input type="file" name="excel">
                    <input type="submit">b
                    <iframe name="uploadTarget"></iframe>
                </form>
            </div>
        </div>
        <div class="content_form">
            <form class="form-horizontal" role="form" action="${add }" method="post">
                <div id="hg-form-container" class="form-group">
                    <div class="report-data-group form-group" style="margin-left: 15px;">
<!--                            <div class="report-data">-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">教学工号</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" name="code" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">党员姓名</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" name="name" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">党员性别</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <select class="form-control" name="sex">-->
<!--                                            <option disabled="" selected="">请选择</option>-->
<!--                                            <option value="男">男</option>-->
<!--                                            <option value="女">女</option>-->
<!--                                        </select>-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">党员职位</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" name="position" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">党员党龄</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" type="number" name="partyAge" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">入党日期</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="datetime form-control" name="joinDate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">应缴党费</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" type="number" name="should" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">实缴党费</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" type="number" name="paid" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div class="col-sm-6 col-xs-12">-->
<!--                                    <div class="col-sm-3 col-xs-3 ">-->
<!--                                        <span class="control-label form-label-required">备注信息</span>-->
<!--                                    </div>-->
<!--                                    <div class="col-sm-9 col-xs-9">-->
<!--                                        <input class="form-control" name="extra" value="">-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                            </div>-->
                    </div>
                    <input id="content_id" type="hidden" name="content"/>
                    <input id="submit" type="submit" style="display:none;"/>
                    <input id="json" type="hidden" name="json"/>
                    <input id="infrom_id" type="hidden" name="infrom_id"/>
                    <c:choose>
                    <c:when test="${type == 'detail'}">
                    <button id="return" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 40%; " onclick="window.location.href='/secondary_report';">返回 </button>
                    </c:when>
                    <c:otherwise>
                    <button id="send" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 12%; ">上报 </button>
                    <button id="cancel" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 45%;">取消 </button>
                    </c:otherwise>
                    </c:choose>
                 </div>
            </form>
        </div>
	</body>
</html>