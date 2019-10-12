<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 保存新增站点 -->
<portlet:resourceURL id="/form-configure/save" var="save"/>

<html>
	<head>
		    <link rel="stylesheet" href="${basePath}/css/assign.css" />
        <link rel="stylesheet" href="${basePath}/css/bootstrap-select.css">
	    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.dropdown.css?v=11">
		<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${basePath}/js/form.js?version=4"></script>
		<script type="text/javascript" src="${basePath}/js/jquery.dropdown.js"></script>
		<style>
/* 		.content-container{
		    display: -webkit-box;
		    display: -ms-flexbox;
		    -webkit-box-pack: justify;
		    -ms-flex-pack: justify;
		    justify-content: space-between;
		    -webkit-box-align: start;
		    -ms-flex-align: start;
		    align-items: flex-start;
		    padding-top: 30px;
	        border-top: 1px solid blue;
		}
		.title-container{
			height: 40px;
		    background-color: rgb(0, 66, 164);
		}
		.title-text{
			line-height: 40px;
		    margin-left: 20px;
		    color: white;
		    font-size: 18px;
	    }
	    .content-left{
	    	width: 50%;
	    }
	    .content-right{
	    	width: 50%;
	    }
	    .task-title{
	        height: 50px;
	    	text-align: center;
	    	font-size: 22px;
	    	line-height: 50px;
	    	margin-bottom: 30px;
	    }
	    .content-frame{
    	    margin: 20px;
		    border-width: 1px;
		    border-style: solid;
		    border-color: gray;
	    }
	    .task-form{
    	    margin-top: 30px;
	    }
	    .form-property{
	        margin-bottom: 30px;
	        line-height: 0px;
		    margin-left: 5%;
		    margin-right: 5%;
	    }
	    button.operation{
		    margin-left: 10%;
	    }
	    .form-property label{
		    width: 20%;
			text-align: justify;
		    color: black;
		    font-weight: bold;
		    font-size: 18px;
	    }
	    .form-property input,select{
	    	margin-left: 10px;
	    	height: 28px;
   			width: 65%;	
   			display: inline-block;
	    }
    	label.form-title:after{
    	  	content: " ";
		    display: inline-block;
		    width: 100%;
	    }
	    #submit{
    	    border-radius: 5px;
		    background-color: rgb(22, 155, 213);
		    border: none;
		    box-shadow: none;
		    color: white;
		    font-size: 14px;
		    height: 28px;
		    line-height: 28px;
		    width: 50px;
	    }
	    .submit{
	    	text-align: center;
	    } */
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
   		<script type="text/javascript" >
            $(function() {
		
            var form = ${design};

            var hgDoms = new HgDoms(form);
            
            var formValue = ${formValue};
            
          	var new_num = 1;
          	
          	var formAll = ${baseValue};
          	
          	var $baseLeft = $('#base-content');
            var baseLeft_html = hgDoms.generateHtml(function (column, index) {
                return index < 5;
            });
            $baseLeft.html(baseLeft_html + $baseLeft.html());

            
            $('#base-content [name="name"]').val(formAll.name);
            $('#base-content [name="submitCommand"]').val(formAll.submit_command);
            $('#base-content [name="view"]').val(formAll.render_jsp);
            $('#base-content [name="render"]').val(formAll.render_command);
            $('#base-content [name="description"]').val(formAll.description);
            
            var $left = $('#column-select-content');
            var left_html = hgDoms.generateHtml(function (column, index) {
                return 5 <= index && index < 6
            });
            
            var operations = "<button id='add' class='operation'>新增</button>" + 
            	"<button id='delete' class='operation'>删除</button>" + 
            	"<button id='up' class='operation'>&nbsp;↑&nbsp;</button>" + 
            	"<button id='down' class='operation'>&nbsp;↓&nbsp;</button>";
            $left.html(left_html + operations + $left.html());
            
            var right_html = hgDoms.generateHtml(function (column, index) {
                return index >= 6; 
            });
            var $right = $('#columns-content');              
            var $columns = $('select[name="column"]');
          	$columns.attr('multiple', 'multiple').attr('size', 10);
            $right.html(right_html + $right.html() );
            $('.dropdown-mul-2').dropdown({
                // limitCount: 5,
                searchable: false,
                choice: function() {
                    console.log('.dropdown-mul-2 picked')
                }
            });
            $('select[name="validation"]').parent().parent().after('<div id="validate-param-container"></div>');

      		var vP = $('#validate-param-container');

          	$('select[name="validation"]').change(function(){
          		var vType = $(this);
          		var inputField = "<div class=\"col-sm-6 col-xs-12\">\n" +
                "                                <span class=\"col-sm-2 col-xs-3 control-label\">{{title}}</span>\n" +
                "                                <div class=\"col-sm-10 col-xs-9\">                                \n" +
                "                                    <input class=\"form-control\" name=\"validateParam\" value=\"\">\n" +
                "                                </div>                            \n" +
                "                            </div>";
                var vTypeValue = vType.val();
                var vTypeName = vType.find(':selected').text();
                switch(vTypeValue){
	                case 'extension':
	                case 'maxlength':
	                case 'minlength':
	                case 'min':
	                case 'max':
	                    vP.html(inputField.replace("{{title}}", vTypeName));
	                    break;
	                case 'rangelength':
	                    vP.html(inputField.replace("{{title}}", "最小长度") + 
	                    		inputField.replace("{{title}}", "最大长度"));
	                    break;
	                case 'range':
	                    vP.html(inputField.replace("{{title}}", "最小值") + 
	                    		inputField.replace("{{title}}", "最大值"));
	                    break;
	                default:
	                	vP.html('');
	                    break;
                }
          	});
          	function refreshColumnList(){
            	var options = '';
              	for(var i in formValue.columns){
              		var col = formValue.columns[i];
              		options += "<option value='" + col.id + "'>" + col.name + "</option>"
              	}
              	$columns.html(options);
          	}
          	
          	refreshColumnList();
      
          	var $sel;
          	$columns.on('click', 'option', '', function(){
          		$sel = $(this);
          		var inputField = "<div class=\"col-sm-6 col-xs-12\">\n" +
                "                                <span class=\"col-sm-2 col-xs-3 control-label\">{{title}}</span>\n" +
                "                                <div class=\"col-sm-10 col-xs-9\">                                \n" +
                "                                    <input class=\"form-control\" name=\"validateParam\" value=\"{{value}}\">" +
                "                                </div>                            \n" +
                "                            </div>";
          		var vP = $('#validate-param-container');
          		vP.html('');
          		var vType = $('select[name="validation"]');
          		var vTypeValue, vTypeName, vOpt;
          		$right.find('select,input').val(null);
          		var selected = formValue.columns.filter(function(c){return c.id === $sel.val()});
          		if(selected.length > 0){
          			var sel = selected[0];
          		}
          		for(var key in sel){
          			var v, vOpt, vTypeName, vTypeValue;
      				v = sel[key];
          			if(!v)continue;
          			if (key === 'validateParam'){
          				try{
          					v = eval(v);
          				}catch(e){
          					console.log(e);
          				}
          				switch(sel['validation']){
	          			    case 'extension':
	    	                case 'maxlength':
	    	                case 'minlength':
	    	                case 'min':
	    	                case 'max':
	    	                	vOpt = vType.find('[value="' + sel['validation'] + '"]');
	    	                    vTypeName = vOpt.text();
	    	                    vP.html(inputField.replace("{{title}}", vTypeName).replace("{{value}}", v));	    	                    
	    	                    break;
	    	                case 'rangelength':
	    	                	try{
	    	                    	vP.html(inputField.replace("{{title}}", "最小长度").replace("{{value}}", v[0]) + 
	    	                    		inputField.replace("{{title}}", "最大长度").replace("{{value}}", v[1]));
	    	                    }catch(e){
	    	                    	console.log(e);
	    	                    	alert('字段验证参数出错');
	    	                    }

	    	                    break;
	    	                case 'range':
	    	                	try{
		    	                    vP.html(inputField.replace("{{title}}", "最小值").replace("{{value}}", v[0]) + 
		    	                    		inputField.replace("{{title}}", "最大值").replace("{{value}}", v[1]));
	    	                	}catch(e){
	    	                		console.log(e);
	    	                    	alert('字段验证参数出错');
	    	                	}
	    	                    break;
	    	                default:
	    	                	vP.html('');
	    	                    break;
          				
          				}
          			}else if (typeof(sel[key]) === 'object'){
          				for(var tk in sel[key]){
          					v = sel[key][tk];
          					if(!v){
          						continue;
          					}
          					
          					
          					if(tk === 'name'){
          						$right.find('[name="type"]').val(v);
          					}else{
          						if (typeof(sel[key][tk]) === 'object'){
          	              			v = JSON.stringify(v);
          						}else{
          	              			v = v.toString();
          						}
          						$right.find('[name="' + tk +'"]').val(v);
          					}
          				}
          			}else{
          				v = sel[key].toString();
              			$right.find('[name="' + key +'"]').val(v);
          			}
          		}
          	})

          	function chooseCol(col_id){
          		$columns.val(col_id);
          		$('option[value="' + col_id + '"]').click();
          	}
          	function colAdd(){
          		var id = 'new_' + new_num++;
          		formValue.columns.push({id: id, name: id});
          		refreshColumnList();
          		chooseCol(id);
          	}
          	function colIndex(col_id){
          		var selected = formValue.columns.filter(function(c){return c.id === col_id});
          		if(selected.length > 0){
          			var sel = selected[0];
              		return formValue.columns.indexOf(sel);
          		}else{
          			return -1;
          		}
          	}
          	function colDelete(){
          		var col_id = $sel.val()
          		var index = colIndex(col_id);
          		if(index !== -1){
          			formValue.columns.splice(index, 1);
          		}
          		refreshColumnList();
          		$right.find('select,input').val(null);
          	}
          	function colUp(){
          		var col_id = $sel.val()
				var index = colIndex(col_id);
				if(index > 0){
					var swap = formValue.columns[index - 1];
					formValue.columns[index - 1] = formValue.columns[index];
					formValue.columns[index] = swap;
				}
		 		refreshColumnList();
		 		chooseCol(col_id);
          	}
          	function colDown(){
          		var col_id = $sel.val()
        		var index = colIndex(col_id);
				if(index < formValue.columns.length - 1){
					var swap = formValue.columns[index + 1];
					formValue.columns[index + 1] = formValue.columns[index];
					formValue.columns[index] = swap;
				}
		 		refreshColumnList();
		 		chooseCol(col_id);
          	}
          	function colSave(){
          		var col_id = $sel.val()
        		var index = colIndex(col_id);
          		var sel = formValue.columns[index];
          		sel.name =  $right.find('[name="name"]').val();
          		sel.id =  $right.find('[name="id"]').val();
          		if(!('type' in sel)){
          			sel.type = {};
          		}
          		sel.type.name =  $right.find('[name="type"]').val();
          		sel.type.datePattern =  $right.find('[name="datePattern"]').val();
          		sel.type.options =  $right.find('[name="options"]').val();
          		sel.type.optionsKey =  $right.find('[name="optionsKey"]').val();
          		sel.type.optionSql =  $right.find('[name="optionSql"]').val();
          		sel.type.optionNameField =  $right.find('[name="optionNameField"]').val();
          		sel.type.optionValueField =  $right.find('[name="optionValueField"]').val();
          		sel.type.checker =  $right.find('[name="checker"]').val();
          		sel.required =  $right.find('[name="required"]').val();
          		sel.readonly =  $right.find('[name="readonly"]').val();
          		sel.display =  $right.find('[name="display"]').val();
          		sel.defaultValue =  $right.find('[name="defaultValue"]').val();
          		sel.validation = $right.find('[name="validation"]').val();
          		if(sel.validation){
          			var validateParam = $right.find('[name="validateParam"]');
          			
          			if(validateParam.length > 1){
          				sel.validateParam = JSON.stringify([$(validateParam[0]).val(), $(validateParam[1]).val()]);
          			}else if(validateParam.length === 1){
          				sel.validateParam = validateParam.val();
          			}
          		}
          		refreshColumnList();
		 		chooseCol(formValue.columns[index].id);
          	}
          	$('#up').on('click', function(){
          		colUp();
          	});
       	   	$('#down').on('click', function(){
       	   		colDown();
          	});
      	   	$('#add').on('click', function(){
      	   		colAdd();
          	});
      	   	$('#delete').on('click', function(){
      	   		colDelete();
          	})
          	$('#save').on('click', function(){
          		colSave();
          	})
          	$('#submit').on('click', function(){
          		$('#submit').attr('disabled', 'disabled');
          		formAll.design = JSON.stringify(formValue);
          		formAll.name = $('#base-content [name="name"]').val();
          		formAll.submit_command = $('#base-content [name="submitCommand"]').val();
          		formAll.render_jsp = $('#base-content [name="view"]').val();
          		formAll.render_command = $('#base-content [name="render"]').val();
          		formAll.description = $('#base-content [name="description"]').val();
                
          		$.post('${save}', {formDesign: JSON.stringify(formAll)}, function(res){
          			if(res.result){
          				alert('已保存');
          			}else{
          				alert('失败');
          			}
          		});
          		$('#submit').removeAttr('disabled');

          	})
     })
		
		</script>
	</head>
	<body>
		<div class="main-container" style="text-align: center;">
			<div class="title-container">
				<h4 class="title-text">我的实验</h4>
			</div>
			<div class="content-container" id="base-content" style="height:  200px;">
			</div>
			<div class="content-container" id="column-select-content" style="height:  250px;">
			</div>
			<div class="content-container" id="columns-content" style="height:  400px;">
			<div class="col-sm-12 col-xs-12" style="margin-top: 30px;">                
				<div>                               
					<button id="save">保存字段</button>
				</div>
			</div>
			</div>
			<div class="content-container" id="variables-content">
				<div class="content-left">
				</div>
				<div class="content-right">
				</div>
			</div>
			<button id="submit">提交</button>
		</div>
	</body>
</html>