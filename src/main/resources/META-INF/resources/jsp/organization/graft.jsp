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
    <link rel="stylesheet" href="${basePath }/pagination.css" />
    <script type="text/javascript" src="${basePath }/pagination.js"></script>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
     @media(min-width:768px) {
	            .more_text_container {
	                width: 75%;
	            }
	            .more_text_container .more_text {
                    display: inline-block;
                     width: 90%;
	                line-height: 15px;
	                height: 14px;
	                overflow:hidden;
	                position:relative;
                }
                .more_text_container .text_overflow:after{
                	content: "...";
				    font-weight: bold;
				    position: absolute;
				    bottom: 0;
				    right: 0;
				    padding: 0 20px 1px 45px;
				    background: url(http://css88.b0.upaiyun.com/css88/2014/09/ellipsis_bg.png) repeat-y;
                }
	            
	        }
.col-sm-6.col-xs-12 {
    float: right;
}
span.col-sm-3.control-label.col-xs-3 {
    line-height: 40px;
    text-align: right;
}
.operation_bar .btn_group .sure_send{
	color:#fff;
}
    </style>
</head> 
<body>
                <div class="content_title">
                                                    草稿箱
                </div>
                <div class="operation_bar">
                    <div class="select_choice">
                        <img class="select_all" src="/images/not_check_icon.png" />
                        <input type="hidden" />
                        <span >全选</span>
                    </div>
                    <div class="btn_group">
                        <button class="btn btn-default delete_graft">删除草稿</button>
                        <button class="btn btn_main sure_send">确认发送</button>
                    </div>
                      <div class="col-sm-6 col-xs-12" style="padding: 0;">
                            <span class="col-sm-3 control-label col-xs-3">发布时间</span>
                            <div class="col-sm-9 col-xs-9" style="padding: 0;">
	                           <select class="time_select form-control">
	                            <option value="">请选择时间</option>
	                            <option value="">全部</option>
	                            <option value="nowDate">本日</option>
	                            <option value="nowWeek">本周</option>
	                            <option value="more">更早</option>
	                          </select>
                            </div>
                       </div>
                </div>
                <table class="content_table">
                    <thead class="table_title">
                        <tr>
                            <th>会议类型</th>
                            <th>会议主题</th>
                            <th>发布时间</th>
                        </tr>
                    </thead>
                    <tbody class="table_info">
                        <c:forEach var="c" items="${grafts }"> 
                        <tr>
                            <td>
                                <input type="hidden" value="${c.inform_id }"/>
                                <img class="clickImg" src="/images/not_check_icon.png" />${c.meeting_type }
                            </td>
                            <td><a href="/newinfo?informId=${c.inform_id }">${c.meeting_theme }</a></td>
                            <td>${c.minutes }</td>
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
	         	//alert($("input[name='pageNo']").val());
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
    
    
    <portlet:resourceURL id="/hg/deleteGrafts" var="deleteGrafts"/>
    <portlet:resourceURL id="/hg/publicGrafts" var="publicGrafts"/>
    <script type="text/javascript">
            console.log("ok!");
        $(".select_all").click(function(){
        	$(this).attr("src","/images/checked_icon.png");
        	$(".clickImg").attr("src","/images/checked_icon.png");
        });
        
        
        $(".clickImg").click(function(){
        	var sum = 0;
        	if($(this).attr("src")=="/images/checked_icon.png"){
        		      $(this).attr("src","/images/not_check_icon.png");
       		  }else{
       			  $(this).attr("src","/images/checked_icon.png");
       		}
        	$(".clickImg").each(function(){
        		if($(this).attr("src") == "/images/checked_icon.png"){
            		sum += 1;
            	}
            })
        	if($(".clickImg").length == sum){
        		$(".select_all").attr("src","/images/checked_icon.png");
        	}else{
        		$(".select_all").attr("src","/images/not_check_icon.png");
        	}
        	
        });
        
          $(".select_against").click(function(){
        	  $(this).attr("src","/images/checked_icon.png");
           $(".table_info img[src='/images/checked_icon.png']").addClass("againstOn");
           $(".table_info img[src='/images/not_check_icon.png']").addClass("againstNot");
           $(".table_info .againstOn").attr("src","/images/not_check_icon.png");
           $(".table_info .againstNot").attr("src","/images/checked_icon.png");
           $(".table_info .againstOn").removeClass("againstOn");
           $(".table_info .againstNot").removeClass("againstNot");
        }); 
          //删除草稿
         $(".delete_graft").click(function(){
            var imgs=$(".table_info img[src='/images/checked_icon.png']");          
            var resourcesId = new Array("");
            for(var i=0;i<imgs.length;i++){
            	var resourceId=$(imgs[i]).prev().val();
            	resourcesId.push(resourceId);
                } 
            var resources = resourcesId.join(",").substring(1)+"";
            var url="${deleteGrafts}";
            if(!resources){
            	/* $.tip("请选择删除对象！"); */
            	showConfirm("请选择删除对象！");
            	return;
            }
            $.hgConfirm("提示","确定删除草稿吗？");
            $("#hg_confirm").modal("show");
            $("#hg_confirm .btn_main").click(function(){
            $("#hg_confirm").modal("hide");
                $.ajax({
          	      url:url,
          	      data:{"<portlet:namespace/>resourcesId":resources},
          	      dataType:"text",
          	      success:function(succee){ 
          	    	      if("succee"==succee){
          	    	    	   $(".table_info img[src='/images/checked_icon.png']").parent().parent().remove();
          	    	    	   /* $.tip("删除草稿成功！");   */
          	    	         }
          	              }
                    });                
              
            })
          
       }); 
        //确认发送
        $(".sure_send").click(function(){
            var imgs=$(".table_info img[src='/images/checked_icon.png']");          
            var resourcesId = new Array("");
            for(var i=0;i<imgs.length;i++){
            	var resourceId=$(imgs[i]).prev().val();
            	resourcesId.push(resourceId);
                } 
            var resources = resourcesId.join(",").substring(1)+"";           
            var url="${publicGrafts}";  
            if(!resources){
            	/* $.tip("请选择发布对象！"); */
            	showConfirm("请选择发布对象！");
            	return;
            }
             $.hgConfirm("提示","确定要发送吗");
             $("#hg_confirm").modal("show");
             $("#hg_confirm .btn_main").click(function(){
             $("#hg_confirm").modal("hide");
                $.ajax({
          	      url:url,
          	      data:{"<portlet:namespace/>resourcesId":resources},
          	      dataType:"text",
          	      success:function(succee){ 
          	    	      if("succee"==succee){
          	    	    	  $(".table_info img[src='/images/checked_icon.png']").parent().parent().remove();
          	    	    	 /*  $.tip("发布成功！"); */
          	    	        }
          	              }
                    });       
            })
            
       }); 
        
        //根据时间查询
          $(".time_select").change(function(){
        	  var date=$(".time_select").val();
        	  $("#date_date").val(date);
        	  $("#getPageNo").submit();
          });
          function showConfirm(info){
	    	  $.hgConfirm("提示",info);
              $("#hg_confirm").modal("show");
              $("#hg_confirm .btn_main").click(function(){
              $("#hg_confirm").modal("hide");
                  return;            	              
            });	
	     }
   
        </script>
</body>

</html>