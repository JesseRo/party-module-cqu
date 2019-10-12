<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-配置活动地点</title>
<%--     <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
    
    
     <style>
        .set_site_title {
            font-size: 16px;
            font-weight: 600;
            color: #333;
        }
		
		
		
        
        .common_site {
            overflow-y: auto;
            border: 1px solid #d8d8d8;
            padding: 0;
        }
        
        .common_site table {
            width: 100%;
        }
        
        .common_site table thead {
            background: #f5f5f5;
        }
        
        .common_site table td {
            text-align: center;
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
        
        .set_site_btn button img {
            margin-right: 5px;
        }
        
        .add_site_form {
            border: 1px solid #d8d8d8;
            padding: 10px;
            width:65%;
/*                 margin-top: 20px; */
/*                 display:none; */
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
        
        @media(min-width:768px) {
            .set_site_container {
                margin-top: 40px;
            }
            .common_site {
                height: 480px;
            }
            .common_site thead tr {
                height: 40px;
            }
            .common_site tbody tr {
                height: 30px;
            }
        }
        
        @media(max-width:768px) {
            .common_site {
                height: 200px;
            }
            .set_site_title {
                padding: 10px 0;
            }
            .add_site_form {
                margin-top: 10px;
            }
            .set_site_btn {
                margin-top: 20px;
            }
        }
    </style>
    
</head>
<body>

	<portlet:actionURL name="/hg/addPlace" var="managePlaceUrl">
	</portlet:actionURL>
	
<!-- 	<portlet:actionURL name="/hg/deletePlace" var="deletePlaceUrl"> -->
<!-- 	</portlet:actionURL> -->
	
	  <portlet:renderURL var="deletePlaceUrl">
		<portlet:param name="mvcRenderCommandName" value="deletePlace"/>
	  </portlet:renderURL>
	
    <div class="content_info">
        <div class="content_title hidden-xs">
            配置活动地点
        </div>
        <div class="set_site_container">
            <div class="col-sm-7 col-xs-12">
                <div class="col-sm-3 col-xs-12 set_site_title">常用地点</div>
                <div class="col-sm-9 col-xs-12 common_site">
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
                </div>
            </div>
            <div class="col-sm-5 col-xs-12 set_site_btn">
                <button class="btn btn-default btn-sm" value="" onclick="show()">添加</button>
                <button class="btn btn-default btn-sm deleteplace" value="" onclick="deleteplaces()">删除</button>
                <form class="form-horizontal add_site_form" role="form" action="${managePlaceUrl}" >
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" name="classstr" id="classstr"/>教/楼
                            <input type="text" name="floorstr" name="floor"/>层
                            <input type="text" name="roomstr" name="room"/>教室
                        </div>
                        <div class="col-sm-12 form_btn">
                            <button type="reset" class="btn btn-sm btn-default form_hide_btn">取消</button>
                            <button type="submit" class="btn btn-sm btn_main form_add_btn" >确定</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script>
        $(".common_site").on("click", "tbody tr", function() {
            $(this).toggleClass("bg_grey");
            var _target = $(this).find("img");
            var _src = _target.attr("src");
        })
        
        function deleteplaces(){
        	var places = document.getElementsByName("place");
        	var placestr ="";
        	for (i=0;i<places.length;i++){
		        	if (places[i].checked){
		        		placestr=placestr + places[i].value + ","
		        	}
	        	}
	       	var url = "${deletePlaceUrl}&placesIds="+placestr;
	       	window.location.assign(url);
        }
		
    </script>
</body>

</html>