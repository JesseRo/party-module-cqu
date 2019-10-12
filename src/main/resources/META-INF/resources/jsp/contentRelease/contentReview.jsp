<!-- 组织部审批新闻  zlm-->
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<style>
			.filter_btns > form {
			    display: inline-block;
			}
			 .text_overflow p{
			    margin-top: 5px;
			    position: relative;
			    line-height: 20px;
			    height: 20px;
			    overflow: hidden;
			}
			.overflow_change p:after {
			    content: "...";
			    font-weight: bold;
			    position: absolute;
			    bottom: 0;
			    right: 0;
			    padding: 0 20px 1px 45px;
			    background: url(http://css88.b0.upaiyun.com/css88/2014/09/ellipsis_bg.png) repeat-y;
			} 
			a{
				color: black;
			}
			@media(min-width:768px){
				.table_info .operation_btn{
					min-width:135px;
				}
				.table_info .publish_time{
					min-width:170px;
				}
			}
			
		/* 录入弹窗样式 */
        #input .form-group>div {
            height: 34px;
            margin-bottom: 10px;
        }
        
        #input .col-sm-9,
        #input .col-xs-8 {
            padding: 0;
        }
        
        .file_input {
            height: 100px;
            border: 1px dashed #d8d8d8;
            border-radius: 4px;
        }
        
        .file_input input {
            width: 77px;
            height: 77px;
            opacity: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -38.5px;
            margin-top: -38.5px;
            z-index: 5;
        }
        
        .file_input .show_path {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            line-height: 32px;
            text-indent: 1em;
            text-align: center;
            overflow-y: auto;
        }
        
        .show_path img {
            width: 77px;
            margin: 10.5px 0;
        }
        
        .show_path p {
            text-align: left;
            color: #999;
            font-size: 13px;
            line-height: 15px;
        }
        /* 录入弹窗样式 */			
    </style>

<body>
	<div class="content_title" style="margin-bottom:30px;">
		发文审批
	</div>
	<div class="filter_btns">
		<portlet:actionURL name="/OrgCheckSeconed" var="OrgCheckSeconed"></portlet:actionURL>
		<form action="${OrgCheckSeconed }" id="dengDai" method="post">
			<button class="btn main_color_btn" onclick="dengDai();">待审批</button>
			<input type="hidden" name="already" value="待审批"/>
		</form>
		<form action="${OrgCheckSeconed }" id="already" method="post">
			<button class="btn main_color_btn" onclick="already();">已审批</button>
		  	<input type="hidden" name="already" value="已审批"/>
		</form>
		<script type="text/javascript">
			function dengDai(){
				$("#dengDai").submit();
			}
			function already(){
				$("#already").submit();
			}
		</script>
	</div>
	<div class="content_form">
		<div class="content_table_container">
            <table class="content_table">
                <thead class="table_title">
                    <tr>
                        <th>标题</th>
                        <th>发布人</th>
                        <th>发布时间</th>
                        <th>审核状态</th>
                        <th>审核人</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody class="table_info">
                	<c:forEach items="${infos}" var="info" varStatus="status">
                    <tr>
                        <td data-label="标题" class="inner_table_title"><a href="/detail?resources_id=${info.resources_id}" target="_blank">${info.content_title }</a></td>
                        <td data-label="发布人"> ${info.publisher_id }</td>
                        <td data-label="发布时间" class="publish_time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.publish_time}" /></td>
                        <td data-label="审核状态" class="reviewState"> 
                        	<c:forEach var="state" items="${states}" >
								<c:if test="${state.id == info.approve_state}">
									${state.mark }
								</c:if>
							</c:forEach>
                        </td>
                        <td data-label="审核人">${info.second_approve_id }</td>
                        <td data-label="备注">${info.second_dismissal }</td>
                        <td data-label="操作" class="operation_btn">
                        	<c:if test="${info.approve_state == 1}">
								<portlet:resourceURL id="/content/agreeIt" var="agreeIt" />
								<a onclick="agreeIt('${info.resources_id }');">
									<button type="button" class="btn btn-default td_assign_btn">
										通过&nbsp;
									</button>
								</a>
								<script type="text/javascript">
									//通过
								    function agreeIt(id){
								    	$.hgConfirm("提示","确定通过吗？");
								    	$("#hg_confirm").modal("show");
							            $("#hg_confirm .btn_main").click(function(){
							            	$.ajax({
												url:"${agreeIt }",
												data:{"resourceId":id,"contentPortletKey":"${contentPortletKey}"},
												success:function(){
													window.location.reload();
												},
												error:function(){
													$.tip("error");
												}
											});
							                $("#hg_confirm").modal("hide");
							                $.tip("已通过");
							            })
									}
								</script>
								<portlet:resourceURL id="/content/disagreeIt" var="disagreeIt" />
								<a onclick="disagreeIt('${info.resources_id }');">
									<button type="button" class="btn btn-default td_assign_btn">
										驳回&nbsp;
									</button>
								</a>
							</c:if>
						</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
       <!-- ---------------------分页-------------------------- -->
			<c:if test="${ not empty infos}">
                <div class="pagination_container">
			        <ul class="pagination" id="page"></ul>
			        <div class="pageJump">
			        	<input class='current_page' type="hidden" value="${pageNo}"/>
			            <p>共<span class="total_page">${sum }</span>页</p>
			            <portlet:actionURL name="/PageNoMVCActionCommand" var="pageNoUrl">
			            	<portlet:param name="title" value="${title }"/>
			            </portlet:actionURL>
			            <form action="${pageNoUrl }" id="getPageNo" method="post">
			                <input type="hidden" id="pageNo" name="pageNo" value=""/>
			                <input type="hidden" id="already" name="already" value="${already }"/>
			                <input type="hidden" id="total_page_" name="total_page_" value="${sum}"/>
			                <span>跳转到第</span>
			                <input type="text" id="jumpPageNo" name="jumpPageNo"/>
			                <span>页</span>
			                <button type="submit" class="button">确定</button>
			            </form>
			        </div>
			    </div>
			</c:if>
    </div>
			<script type="text/javascript">
				 $(function(){
						$(".text_overflow").each(function(){
							var _target = $(this).find("p").html().length;
							if($(this).hasClass("inner_table_title")){
								if(_target > 27){
									$(this).addClass("overflow_change");
								}
							}
						})
					})
				
				
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
						
					}
					$("#jumpPageNo").change(function(){
						$("input[name='pageNo']").val($(this).val());
					})
				    Page({
				         num: pages, //页码数
				         startnum: currentPage, //指定页码
				         elem: $('#page'), //指定的元素
				         callback: function(n) { //回调函数
				             $("input[name='pageNo']").val(n);
							 var pageNo = $("#pageNo").val();
// 							 $(window.parent.document).find("#myiframe").attr("src", "${pageNoUrl}&pageNo="+pageNo);//column_id="+column_id+"&site_id="+site_id+"&
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
    
    <!-- 录入模态框 -->
    
	    <!-- 模态框（Modal） -->
	     <div class="modal fade" id="reject" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                    <h4 class="modal-title" id="inputLabel">驳回备注信息</h4>
	                </div>
	                <div class="modal-body content_form">
	                    <form class="form-horizontal" role="form">
	                        <div class="form-group">
	                            <div class="col-sm-12 col-xs-12">
	                                <span class="col-sm-3 col-xs-4 control-label">驳回备注：</span>
	                                <div class="col-sm-9 col-xs-8">
	                                    <input type="text" class="form-control" id="should_"/>
	                                </div>
	                            </div>
	                            
	                        </div>
	                    </form>
	                </div>
	                <div class="modal-footer">
	                	<input type="hidden" id="entry_id" value="" />
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="button" class="btn btn_main" onclick="Write()">确定</button>
	                </div>
	            </div>
	        </div>
	    </div> 
	    <script>
			//驳回
		    function disagreeIt(id){
		    	$('#entry_id').val(id);
		    	$("#reject").modal("show");
			}
			//驳回保存
			function Write(){
				var resourceId = $('#entry_id').val();//resourceId
				var should_ = $('#should_').val(); //驳回备注
				var url = "${disagreeIt}";
				$.ajax({
					url:url,
					data:{"resourceId":resourceId,"dismissal":should_,"contentPortletKey":"${contentPortletKey }"},
// 					dataType:'json',
// 					async:false,
					success:function(){
						$('#reject').modal('hide');
						window.location.reload();
					}
				});
			}
	    </script>
 </body>