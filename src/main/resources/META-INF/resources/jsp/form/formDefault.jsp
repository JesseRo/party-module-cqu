<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 保存新增站点 -->
<portlet:actionURL name="${submitCommand }" var="submitForm"/>
<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl" />
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl" />
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl" />
<html>
	<head>
<%-- 	    <link rel="stylesheet" href="${basePath}/css/common.css" /> --%>
<%-- 	    <link rel="stylesheet" href="${basePath}/css/bootstrap.min.css" /> --%>
	    <link rel="stylesheet" href="${basePath}/css/assign.css?v=33" />
        <link rel="stylesheet" href="${basePath}/css/bootstrap-select.css">
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.dropdown.css?v=11">
		<style>
        body {
            padding-top: 70px;
        }
        
        .default_icon {
            width: 18px;
            height: 18px;
            margin-right: 5px;
            vertical-align: sub;
            display: inline-block;
            background-image: url('./images/not_check_icon.png');
        }
        
        .selected .checked_icon {
            background-image: url('./images/checked_icon.png');
        }
    	</style>
	</head>
	<body>
		
		<div class="content_info">
        	<div class="content_title">
           		${title }
        	</div>
        	<div class="content_form">
	        	<form action="${submitForm }" class="form-horizontal" role="form" method="post">
	                <div id="hg-form-container" class="form-group">
        	        </div>
	            </form>
			</div>
		</div>
		
		<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${basePath}/js/form.js?version=27"></script>
	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    	<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
   		<script type="text/javascript" src="${basePath}/js/jquery.dropdown.js"></script>
   		<script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/validation-message-zh.js?v=2"></script>
		<script type="text/javascript" >
            $(function() {
		
	            var form = ${design};
	            var uploadUrls = {
	            		file: '${uploadfileUrl}',
	            		image: '${uploadimageUrl}',
	            		video: '${uploadvideoUrl}'
	            }
	
	            var hgDoms = new HgDoms(form, uploadUrls, 'hg-form-container');
	            $.fn.hgDoms = hgDoms;
	            $('.dropdown-mul-2').dropdown({
	                // limitCount: 5,
	                searchable: false,
	                choice: function() {
	                    console.log('.dropdown-mul-2 picked')
	                }
	            });
	
	            var rules = {};
	            console.log(JSON.stringify(form));
	            for(var k in form.columns){
	            	var column = form.columns[k];
	            	if(column.validation || column.required){
	            		var r = {};
	            		if(column.validation){
	            			var v = column.validateParam;
	            			if(v){
	                 			try{
		            				v = eval(v);
		            			}catch(e){
		            				console.log(e);
		            			}
	            			}else{
	            				v = true;
	            			}
	            			r[column.validation] = v;
	            		}
	            		if(column.required){
	            			r.required = true;
	            		}
	            		rules[column.id] = r;
	            	}
	            }
	            $("#hg-form-container").parent().validate({
	                // focusInvalid:false,
                    submitHandler: function(form) {
                        form.submit();
				    },
	                rules: rules
	            });

	        })
		</script>
	</body>
</html>