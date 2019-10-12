<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>组织部子页面主题</title>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
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
    </style>
</head>
<body>
                <div class="content_title">
                                                    预备党员转正审批
                </div>

               <!-- <div class="col-sm-6 col-xs-12"  style="margin-bottom:20px;">
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
                </div> -->
                <table class="content_table" style="border:1px solid #dedede;">
                    <thead class="table_title">
                        <tr>
                            <c:forEach var="h" items="${title}">
                                <th>${h}</th>
                            </c:forEach>
                            <th>操作</th>
                            <th>支部大会讨论结果</th>
                            <th style="display: none;">查看详情</th>
                        </tr>
                    </thead>
                    <tbody class="table_info">
                        <c:forEach var="c" items="${content }">
                        <tr>
                            <c:forEach var="ct" items="${c}" varStatus="vs">
                                <c:choose>
                                    <c:when  test="${vs.last}">
                                    <td>
                                        <c:if test="${ct == 'true'}">
                                            <div style="display: none;">
                                                <button id="import" type="button" class="btn btn-default col-xs-4">
                                                    <img src="/images/import_icon.png"> 上传
                                                </button>
                                                <div id="upload-block" style="display: none;">
                                                    <form action="#" method="post" target="uploadTarget" enctype="multipart/form-data">
                                                        <input type="file" name="excel">
                                                        <input type="submit">b
                                                        <iframe name="uploadTarget"></iframe>
                                                    </form>
                                                </div>
                                                <a id="download" href="/images/test.doc" style="ine-height: 30px; display:none;">下载谈话记录</a>
                                            </div>
                                            <div>
                                                <div class="approve">
                                                    <a class="approval" style="cursor: pointer;">通过</a>
                                                    <a class="reject" style="cursor: pointer;">驳回</a>
                                                </div>
                                                <div class="pass" style="display: none;">已通过</div>
                                                <div class="ban" style="display: none;">已驳回</div>
                                            </div>
                                        </c:if>
                                    </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="padding-left: 25px;">${ct}</td>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                            <td style="display: none;">
                                <a href="/secondaryReportDetail">查看详情</a>
                            </td>
                            <td>
                                <a href="/images/test.doc">下载下载支部大会讨论结果</a>
                            </td>
                        </tr>　
　　                     </c:forEach>
                    </tbody>
                </table>

                 <!--    分页              -->

	<div class="pagination_container">
	        <ul class="pagination" id="page"></ul>
	        <div class="pageJump">
	        	<input class='current_page' type="hidden" value="${pageNo}"/>
	            <p>共<span class="total_page">${totalPage }</span>页</p>
	            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
				</portlet:actionURL>
	            <form action="#" id="getPageNo" method="post">
	                <input type="hidden" id="pageNo" name="pageNo" value=""/>
	                <input type="hidden" id="total_page_" name="total_page_" value="${totalPage}"/>
	                <span>跳转到第</span>
	                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
	                <span>页</span>
	                <%--  <input label="站点id" name="Site"  value="${Site }" type="hidden"/>
	                <input label="栏目id" name="Column"  value="${Column }" type="hidden"/> --%>
	                <input type="hidden" id="date_date" name="date" value=""/>
	                <button type="submit" class="button">确定</button>
	            </form>
	        </div>
	   </div>
	 <script type="text/javascript">
		 $(document).ready(function() {

            $('.approval').on('click', function(){
                alert('操作成功！');
                $(this).parent().hide();
                $(this).parent().parent().find('.pass').show();
            });
            $('.reject').on('click', function(){
                alert('操作成功！');
                $(this).parent().hide();
                $(this).parent().parent().find('.ban').show();
             });

			 var pages = $(".total_page").html();
			 var currentPage = $('.current_page').val();
			 $("input[name='pageNo']").val($('.current_page').val());
			if(currentPage == 1){
				$('.page_next').removeClass('not_allow');
				$('.page_prev').addClass('not_allow');

			}else if(currentPage == pages){
				$('.page_prev').removeClass('not_allow');
				$('.page_next').addClass('not_allow');

			}else{

			};
			$("#jumpPageNo").change(function(){
				$("input[name='pageNo']").val($(this).val());
			})
	     Page({
	         num: pages, //页码数
	         startnum: currentPage, //指定页码
	         elem: $('#page'), //指定的元素
	         callback: function(n) { //回调函数
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
	  });
	 </script>
	 </div>
	 <!-- 分页 -->


</body>

</html>