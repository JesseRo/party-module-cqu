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
                <div class="content_title" style="margin-bottom:30px;">
                                                    已发布
                </div> 
                <portlet:resourceURL id="/hg/export" var="export"/>
                 <a href="${export }" > 
		                 <button type="button" class="btn btn-default col-sm-2 col-xs-4">
								<img src="/images/import_icon.png"/>
								导出到excel
		                </button>
                  </a>
               <div class="col-sm-6 col-xs-12"  style="margin-bottom:20px;">
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
                <table class="content_table" style="border:1px solid #dedede;">
                    <thead class="table_title">
                        <tr>
<%--                            <th>会议类型</th>--%>
                            <th>会议主题</th>
                            <th>发布时间</th>
                            <th>操作</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody class="table_info">
                        <c:forEach var="c" items="${grafts }"> 
                        <tr>
                            <td style="display: none;">
                                <input type="hidden" value="${c.inform_id }"/>
                                <input type="hidden" value="${c.id }"/>
                                  ${c.meeting_type }
                            </td>
                            <td>
                             <a href="/checkdetail?informId=${c.inform_id }">${c.meeting_theme }</a>      
                            </td>
                            <td>${c.release_time }</td>
                            <td>           
							<c:if test="${orgType eq 'organization'}">
							     <a href="/sconedPartyDetail?inform_id=${c.inform_id }">查看进度</a>
							</c:if>
							 <c:if test="${orgType eq 'secondary'}">
							    <a href="/branchview?inform_id=${c.inform_id }">查看进度</a>
							</c:if>
                            </td>
                            <td> 
                            	<a href="/newinfo?informId=${c.inform_id }&orgEdit='orgEdit'">编辑</a>
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
	 <script type="text/javascript">
		 $(document).ready(function() {
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
     

    <script>
        //根据时间查询
        $(".time_select").change(function(){
      	//  alert($(".time_select").val());
      	  var date=$(".time_select").val();
      	  $("#date_date").val(date);
      	  $("#getPageNo").submit();
        });
    </script>
</body>

</html>