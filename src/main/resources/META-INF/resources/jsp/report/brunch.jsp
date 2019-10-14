
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
            $(function() {
                $('#send').on('click', function(){
                    var inputs = $('input.form-control');
                    for(var i = 0; i < inputs.length; i++){
                        var value = $(inputs[i]).val();
                        if(!value){
                            alert("文件未上传")
                        }
                    }
                    $('#submit').click();
                });
            });
		</script>
	</head>
	<body>
        <div class="content_title">
            <label>数据上报</label>
        </div>
        <div class="content_form">
            <iframe name="forUpload" style="display: none;"></iframe>
            <form class="form-horizontal" role="form" action="${upload }" method="post" target="forUpload" enctype="multipart/form-data">
                <div id="hg-form-container" class="form-group">
                    <c:forEach var="f" items="${fileViews}" varStatus="varStatus">
                        <div class="col-sm-12 col-xs-12">
                            <div class="col-sm-6 col-xs-12">
                                <div class="col-sm-3 col-xs-3 ">
                                    <span class="control-label form-label-required">文档${varStatus.count}：</span>
                                </div>
                                <div class="col-sm-9 col-xs-9">
                                    <input class="form-control" name="files" type="file">
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <input id="taskId" type="hidden" name="taskId" value="${taskId}"/>
                    <input id="submit" type="submit" style="display:none;"/>
                    <input id="formId" type="hidden" name="formId" value="${formId}"/>

                    <button id="send" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 12%; ">上报 </button>
                    <button id="cancel" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 45%;">取消 </button>
                 </div>
            </form>
        </div>
	</body>
</html>