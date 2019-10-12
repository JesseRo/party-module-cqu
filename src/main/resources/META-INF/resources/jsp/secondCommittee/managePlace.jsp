<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-配置活动地点</title>
    <style>
        .set_site_title {
            font-size: 16px;
            font-weight: 600;
            color: #333;
        }
        
        .common_site {
            border: 1px solid #d8d8d8;
            padding: 0;
            height: auto;
        }
        .common_site .table_container{
            max-height: 350px;
            overflow-y: auto;
        }
        .common_site table {
            width: 100%;
        }
        
        .common_site table thead {
            background: #f5f5f5;
        }
        
      th, .common_site table td {
            padding-left: 70px;
            text-align: left;
       }
        
        .common_site tbody tr {
            cursor: pointer;
        }
        
        .common_site tbody tr:hover {
            background: #f5f5f5;
            color: #999;
        }
        
        .bg_grey {
            background: #f5f5f5;
            color: #999;
        }
        .set_site_container{
            position: relative;
        }
        .set_site_container .operation_line{
            position: relative;
            bottom: 0;
            left: 0;
            height: auto;
            background: #f5f5f5;
            width: 100%;
            padding-left: 15px;
        }
        .operation_line button{
            margin: 15px 0;
        }
        .set_site_btn button img {
            margin-right: 5px;
        }
        
        .add_site_form {
            border: 1px solid #d8d8d8;
            box-sizing: border-box;
            height: auto;
            padding: 12px 6px;
            background: #fff;
            position: relative;
            display: none;
        }
        
        .add_site_form input[type='text'] {
            width: 60px;
            height: 30px;
            border: 1px solid #d8d8d8;
            margin-right: 5px;
            text-indent: 1em;
        }
        
        .add_site_form .form_btn {
            text-align: center;
            margin-top: 10px;
        }
        .table_container::-webkit-scrollbar {
                width:0;
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
            background: transparent;
        }
        @media(min-width:768px) {
            .set_site_container {
                margin-top: 40px;
            }
            .common_site {
                /* min-height: 480px; */
                margin-bottom: 20px;
            }
            .common_site thead tr {
                height: 40px;
            }
            .common_site tbody tr {
                height: 30px;
            }
            .add_site_form {
                margin-top: 20px;
                width: 45%;
                left: 30%;
                top: -60px;
            }
            .form-horizontal add_site_form{
            width:330px;
            }
        }
        
        @media(max-width:768px) {
            .content_info{
                width: 100%;
            }
            .common_site {
                /* min-height: 200px; */
            }
            .set_site_title {
                padding: 10px 0;
            }
            .add_site_form {
                width: 95%;
                bottom: 5px;
            }
            .set_site_btn {
                margin-top: 20px;
            }
        }
    </style>
</head>

<body>

	<portlet:actionURL name="/hg/addPlace" var="addPlaceUrl">
	</portlet:actionURL>
	
<!-- 	<portlet:actionURL name="/hg/deletePlace" var="deletePlaceUrl"> -->
<!-- 	</portlet:actionURL> -->
	
	  <portlet:renderURL var="deletePlaceUrl">
		<portlet:param name="mvcRenderCommandName" value="deletePlace"/>
	  </portlet:renderURL>
	  
	  <portlet:resourceURL id="/hg/checkUniquePlace" var="checkUniquePlaceUrl"/>
	  
    <div class="">
        <div class="content_title hidden-xs">
            配置活动地点
        </div>
        
        <div class="set_site_container">
            <div class="col-sm-12 col-xs-12">
                <div class="col-sm-2 col-xs-12 set_site_title">常用地点</div>
                <div class="col-sm-10 col-xs-12 common_site">
                    <div class="table_container">
                        <table>
	                        <thead>
	                            <tr>
	                                <th>选择</th>
	                                <th>序号</th>
	                                <th>名称</th>
	                            </tr>
	                        </thead>
	                        <tbody>
		                        <c:forEach var="c" items="${placeList}"  varStatus="status">
		                        	 <tr>
		                        		<td>
			                        		<input type="checkbox" name="place" class="place" value="${c.place_id}"><br />
		                                </td>
		                                <td>${status.index+1}</td>
		                                <td>${c.place}</td>
									</tr>
								</c:forEach>
	                        </tbody>
	                    </table>
	                    
						 <!--    分页              -->
					       <div class="pagination_container">
						        <ul class="pagination" id="page"></ul>
						           <div class="pageJump">
						        	<input class='current_page' type="hidden" value="${pageNo}"/>
						            <p>共<span class="total_page">${pages }</span>页</p>
						            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
									</portlet:actionURL>
						            <form action="${pageNoUrl }" id="getPageNo" method="post">
						                <input type="hidden" id="pageNo" name="pageNo" value=""/>
						                <input type="hidden" id="total_page_" name="total_page_" value="${pages}"/>
						                <span>跳转到第</span>
						                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
						                <span>页</span>
						                 <input label="站点id" name="Site"  value="${Site }" type="hidden"/>
						                <input label="栏目id" name="Column"  value="${Column }" type="hidden"/>
						                <button type="submit" class="button">确定</button>
						            </form>
						        </div>
					  	 	</div>

	                    
                    </div>
                    
                    <div class="operation_line">
                        <button type="button" class="btn btn-sm btn-default add_member_btn">
                                                                          添加
                        </button>
                        <button type="button" class="btn btn-sm btn-default" onclick="deleteplaces()">
                                                                         删除
                        </button>
                       <%--  <form class="form-horizontal add_site_form" role="form" action="${addPlaceUrl}">
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="text" name="classstr" class="classstr"/>教/楼
			                            <input type="text" name="floorstr" class="floorstr"/>层
			                            <input type="text" name="roomstr" class="roomstr" onblur="checkUnique()"/>教室
			                            <span class="infomsg"></span>
                                    </div>
                                    
                                    
                                    <div class="col-sm-12 form_btn">
                                        <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
                                        <button type="submit" class="btn btn-sm btn_main form_add_btn">确定</button>
                                    </div>
                                </div>
                            </form> --%>
                            
                            <form class="form-horizontal add_site_form" id="form_place" role="form" action="${addPlaceUrl}" style="height: 170px;">
                                <div class="col-sm-12 form-group">
                                    <div class="col-sm-4">
                                            <input type="radio" name="siteName" value="fix"/>输入教学楼地址
                                    </div>
                                    <div class="col-sm-8 fix_place" style="padding: 0;">
                                        <input type="text"  name="classstr" class="classstr" />教/楼
                                        <input type="text"  name="floorstr" class="floorstr"/>层
                                        <input type="text" name="roomstr" class="roomstr" />教室
                                        <span class="infomsg">${msg1 }</span>
                                    </div>
                                </div>
                                <div class="col-sm-12 form-group">
                                    <div class="col-sm-4">
                                        <input type="radio" name="siteName" value="custom"/>自定义地址
                                    </div>
                                    <div class="col-sm-8">
                                        <input type="text" placeholder="请输入地址" name="customPlace"  class="form-control custom_place" style="width:80%;display:inline-block;" />
                                        <span class="infomsg">${msg2 }</span>
                                    </div>
                                </div>
                                <div class="col-sm-12 form_btn">
                                    <button type="button" class="btn btn-sm btn-default form_hide_btn" style="margin-right: 20px;">取消</button>
                                    <button type="button" class="btn btn-sm btn_main form_add_btn">确定</button>
                                    <script type="text/javascript">
                                      $(".form_add_btn").click(function(){
                                    	var customPlace =  $("input[name='customPlace']").val();  
                                    	var classstr =  $("input[name='classstr']").val();  
                                    	var floorstr =  $("input[name='floorstr']").val();  
                                    	var roomstr =  $("input[name='roomstr']").val();  
                                    	  if(!customPlace && !classstr && !floorstr && !roomstr){
                                    		alert("请输入活动地点！"); 
                                    		return;
                                    	  }else{
                                    		$("#form_place").submit();  
                                    	  }
                                      });
                                    </script>
                                </div>
                            </form>
                            
                            
                    </div>
                </div>
            </div>
        </div>
        
        
           

    </div>
    <script>
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
        	$("#mt").val($("#meetingType").val());
        	$("#ts").val($("#taskStatus").val());
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
     
	$(".form-group .col-sm-4").click(function(){
		$(".form-group input[type='text']").attr("disabled",true);
		$(".form-group input[type='text']").val("");
		$(this).siblings(".col-sm-8").find("input[type='text']").attr("disabled",false);
	});
    
      /*   $(".common_site").on("click", "tbody tr", function() {
            $(this).toggleClass("bg_grey");
            var _target = $(this).find("img");
            var _src = _target.attr("src");
            if (_src.indexOf("on") > 0) {
                _target.attr("src", "/images/radio.png");
            } else {
                _target.attr("src", "/images/radio_on.png");
            }
        }); */

        //点击添加按钮  出现form
        $(".common_site").on("click",".add_member_btn",function(){
            $(this).siblings(".add_site_form").css({"display":"block","width":"65%"}); 
            $(".classstr").focus();
        })

        //点击form 的取消  form 消失
        $(".common_site").on("click",".add_site_form .form_hide_btn",function(){
            $(this).parents(".add_site_form").css("display","none");
        })
        
         /* $(".classstr").blur(function(){
        	  $(".floorstr").focus();
        	  $(".infomsg").empty();
        });
        
        
        $(".floorstr").blur(function(){
      	  $(".roomstr").focus();
      	 $(".infomsg").empty();
      }); */
        
        $(".roomstr").blur(function(){
        	  var pstr = $(".classstr").val()+"教/楼"+$(".floorstr").val()+"层"+$(".roomstr").val()+"室";
        	  var url="${checkUniquePlaceUrl}";
        	  
        	  if(pstr=='教/楼层室'){
        		  $(".infomsg").html("请输入正确的地址。").css("color","red");
    	    	  $(".classstr").focus();
        	  }
        	  else{
	              $.ajax({
	        	      url:url,
	        	      data:{pstr:pstr},
	        	      dataType:"text",
	        	      success:function(data){ 
	        	    	  console.log(data);
	        	    	  $(".infomsg").html(data).css("color","blank");
	        	    	  if(data == "地点已存在，请重新录入。"){
	        	    		  $(".infomsg").css("color","red");
	        	    		 /*  $(".classstr").focus(); */
	        	    	  }
	        	      }
	               });
        	  }
        });
        
        
        
        
        function deleteplaces(){
        	   $.hgConfirm("提示","确定删除该地点吗！");
	    	   $("#hg_confirm").modal("show");
	           $("#hg_confirm .btn_main").click(function(){
	        		var places = document.getElementsByName("place");
	            	var placestr ="";
	            	for (i=0;i<places.length;i++){
	    		        	if (places[i].checked){
	    		        		placestr=placestr + places[i].value + ","
	    		        	}
	    	        	}
	    	       	var url = "${deletePlaceUrl}&placesIds="+placestr;
	    	       	window.location.assign(url);
	            });
        }
        
        
    </script>
</body>

</html>