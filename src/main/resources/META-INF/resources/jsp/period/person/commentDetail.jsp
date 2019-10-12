
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
        .form-group label{
            margin-right: 10%;
            width: 80px;
        }
        .form-group label input{
            margin-right: 10px;
        }
	</style>

	    <link rel="stylesheet" href="${basePath}/css/assign.css" />
        <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript" >
            $(function(){
                $('#send').on("click", function(){
                    setTimeout(function(){
                        alert("已提交申请！");
                        window.location.href = "/person_comment";
                    })
                })
            })
		</script>
	</head>
	<body>
        <div class="content_title">
            <label>熊辉俊年度工作民主测评</label>

        </div>
        <div class="content_form">
            <form class="form-horizontal" role="form" action="${add }" method="post">
                <div id="hg-form-container" class="form-group">
                    <div class="report-data-group form-group" style="margin-left: 15px;">
                            <div class="report-data form-group">
                                <div class="col-sm-12 col-xs-12">
                                    <div class="col-sm-3 col-xs-3 ">
                                        <span class="control-label form-label-required" style="float: none;">总体评价</span>
                                    </div>
                                    <br /> <br />
                                    <form>
                                    <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                    <label><input name="Fruit" type="radio" value="" />称职 </label>
                                    <label><input name="Fruit" type="radio" value="" />基本称职 </label>
                                    <label><input name="Fruit" type="radio" value="" />不称职 </label>
                                    </form>
                                </div>
                                <div class="col-sm-12 col-xs-12">
                                    <div class="col-sm-3 col-xs-3 ">
                                        <span class="control-label form-label-required" style="float: none;">德</span>
                                    </div>
                                     <br /> <br />
                                     <form>
                                     <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                     <label><input name="Fruit" type="radio" value="" />良好 </label>
                                     <label><input name="Fruit" type="radio" value="" />一般 </label>
                                     <label><input name="Fruit" type="radio" value="" />差</label>
                                     </form>
                                </div>
                                <div class="col-sm-12 col-xs-12">
                                    <div class="col-sm-3 col-xs-3 ">
                                        <span class="control-label form-label-required" style="float: none;">能</span>
                                    </div>
                                    <br /> <br />
                                    <form>
                                    <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                    <label><input name="Fruit" type="radio" value="" />良好 </label>
                                    <label><input name="Fruit" type="radio" value="" />一般 </label>
                                    <label><input name="Fruit" type="radio" value="" />差 </label>
                                    </form>
                                </div>
                                <div class="col-sm-12 col-xs-12">
                                    <div class="col-sm-3 col-xs-3 ">
                                       <span class="control-label form-label-required" style="float: none;">行</span>
                                    </div>
                                    <br /> <br />
                                    <form>
                                    <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                    <label><input name="Fruit" type="radio" value="" />良好 </label>
                                    <label><input name="Fruit" type="radio" value="" />一般 </label>
                                    <label><input name="Fruit" type="radio" value="" />差 </label>
                                    </form>
                                </div>
                              <div class="col-sm-12 col-xs-12">
                                  <div class="col-sm-3 col-xs-3 ">
                                      <span class="control-label form-label-required" style="float: none;">绩</span>
                                  </div>
                                <br /> <br />
                                <form>
                                    <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                    <label><input name="Fruit" type="radio" value="" />良好 </label>
                                    <label><input name="Fruit" type="radio" value="" />一般 </label>
                                    <label><input name="Fruit" type="radio" value="" />差 </label>
                                    </form>
                               </div>
                                <div class="col-sm-12 col-xs-12">
                                    <div class="col-sm-3 col-xs-3 ">
                                        <span class="control-label form-label-required" style="float: none;">廉</span>
                                   </div>
                                    <br /> <br />
                                    <form>
                                     <label><input name="Fruit" type="radio" value="" />优秀 </label>
                                     <label><input name="Fruit" type="radio" value="" />良好 </label>
                                     <label><input name="Fruit" type="radio" value="" />一般 </label>
                                     <label><input name="Fruit" type="radio" value="" />差 </label>
                                     </form>
                                </div>
                            </div>
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
                    <button id="send" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 12%; ">提交 </button>
                    <button id="cancel" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 45%;">取消 </button>
                    </c:otherwise>
                    </c:choose>
                 </div>
            </form>
        </div>
	</body>
</html>