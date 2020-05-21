function HgDoms(form, uploadUrls, container) {
		
	
				
	
                this.form = form;
                var context = this;
                this.chosen = "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +
                    "                                   <span class='control-label ${class}'>${name}</span>" +
                    "                                 </div>" +
                    "								 <div class='col-sm-9 col-xs-9'>" +
                    "                                	<select class='form-control ${class}' name=\"${id}\" ${extra}>\n" +
                    "                                    	${options}" +
                    "                                	</select>\n" +
                    "								 </div>" +
                    "                            </div>";
                
                this.chosen_multiple = "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +

                    "                                <span class='control-label ${class}'>${name}</span>\n" +
                    "                                 </div>" +

                    "								 <div class='col-sm-9 col-xs-9'>" +
                "									<div class=\"dropdown-mul-2\">" +
                "                                		<select style=\"display:none\" name=\"${id}\" multiple placeholder=\"请选择\" >\n" +
                "                                    	${options}" +
                "                                		</select>\n" +
                "									</div>" +
                "								 </div>" +
                "                            </div>";

                this.option = "<option value='${value}'>${name}</option>";

                this.chosenPlaceholder = "<option disabled selected>请选择</option>";
     
                this.str =  "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +
                    "                                <span class=' control-label ${class}'>${name}</span>\n" +
                    "                                 </div>" +
                    "								 <div class='col-sm-9 col-xs-9'>" +
                    "                                <input class='form-control' name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                    "								 </div>" +
                    "                            </div>";
                
                this.date =  "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +
                    "                                <span class='control-label ${class}'>${name}</span>\n" +
                    "                                 </div>" +
                    "								 <div class='col-sm-9 col-xs-9'>" +
                "                                <input class='datetime form-control' name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                "								 </div>" +
                "                            </div>";

                
                this.file = "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +
                    "                                <span class='control-label ${class}'>${name}</span>\n" +
                    "                                 </div>" +
                    "								 <div class='col-sm-9 col-xs-9'>" +
                "                                	<input type='file' class='form-control' style='text-indent: 0;' multiple name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                "								 </div>" +
                "                            </div>";
                
                this.richtext = '<div class="col-sm-12 col-xs-12" style="padding-left: 0;">' +
                    "     <div class='col-sm-6 col-xs-12'>\n" +
                    "                                <div class=\"col-sm-3 col-xs-3 \">\n" +
                    "                                <span class=' control-label ${class}'>${name}</span>\n" +
                    "                                 </div>" +
                    "								 <div class='col-sm-9 col-xs-9'>" +
                    '					<script id="column_id" name="column_id" type="text/plain"></script>' +
                    "								 </div>" +
                    "                            </div>" +
				'				</div>';
				
                this.richtext_id = null;
                this.ueditor = null;

                
                function generate_chosen(column) {
                    var col_template = context[column.type.name];
                    var option_template = context.option;
                    var options_html = context.chosenPlaceholder;
                    if (column.type.options) {
                        for (var i in column.type.options) {
                            var option = column.type.options[i];
                            options_html += option_template.replace('${value}', option.value).replace('${name}', option.name);
                        }
                    }
                    var col_html;
                    var extra = '';
                    if (column.readonly) {
                        extra += 'disabled ';
                    }
                    if (column.required) {
                        col_template = col_template.replace('${class}', "form-label-required");
                    }
                    if (!column.display) {
                        extra += 'style="display: none;" ';
                    }
                    col_html = col_template.replace('${name}', column.name).replace('${id}', column.id).replace('${extra}', extra).replace('${options}', options_html);
                    return col_html;
                }
                
                function generate_chosen_multiple(column){
                    var col_template = context[column.type.name];
                    var option_template = context.option;
                    var options_html = '';
                    if (column.type.options) {
                        for (var i in column.type.options) {
                            var option = column.type.options[i];
                            options_html += option_template.replace('${value}', option.value).replace('${name}', option.name);
                        }
                    }
                    var col_html;
                    var extra = '';
                    if (column.readonly) {
                        extra += 'disabled ';
                    }
                    if (column.required) {
                        col_template = col_template.replace('${class}', "form-label-required");
                    }
                    if (!column.display) {
                        extra += 'style="display: none;" ';
                    }
                    col_html = col_template.replace('${name}', column.name).replace('${id}', column.id).replace('${extra}', extra).replace('${options}', options_html);
                    return col_html;                
                }

                function generate_str(column) {
                    var col_html = context[column.type.name];
                    var extra = '';
                    if (column.readonly) {
                        extra += 'readonly ';
                    }
                    if (column.required) {
                    	col_html = col_html.replace('${class}', "form-label-required");
                    }
                    if (!column.display) {
                        extra += 'style="display: none;" ';
                    }
                    if (column.defaultValue) {
                        col_html = col_html.replace('${value}', column.defaultValue);
                    } else {
                        col_html = col_html.replace('${value}', '');
                    }
                    col_html = col_html.replace('${name}', column.name).replace('${id}', column.id).replace('${extra}', extra);
                    return col_html;
                }

                function generate_date(column) {
                	return generate_str(column);
                }

                function generate_file(column) {
                	return generate_str(column);

                }

                function generate_richtext(column) {
                    var col_html = context[column.type.name];
                    context.richtext_id = column.id;
                    return col_html.replace(/column_id/g, column.id).replace('${name}', column.name);
                }

                this.generateHtml = function (filter) {
                    var html = '';
                    for (var i in this.form.columns) {
                        var column = this.form.columns[i];
                        if(!filter){
                        	filter = function(){return true;};
                        }
                        if(filter(column, parseInt(i))){
                            var generator = eval('generate_' + column.type.name);
                            html += generator(column);
                        }
                    }
                    return html;
                }
                
                if(container){
            		this.container = container;
            		if(container.constructor === String){
            			this.container = $('#' + container);
            		}else{
            			this.container = container;
            		}
            		this.container.html(this.generateHtml() + this.container.html());
    	            $('.datetime').attr('onClick', "WdatePicker({dateFmt:'yyyy-MM-dd\u0020HH:mm:ss',onpicked:function(){$(this).change()}})");
    	            if(this.richtext_id){
    	            	var ueObj = this.ueditor = UE.getEditor(this.richtext_id, { initialFrameWidth:821});

        				UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        				UE.Editor.prototype.getActionUrl = function(action) {
        				    if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
        				    	return uploadUrls.image;
        				    } else if (action == 'uploadvideo') {//视频
        				    	return uploadUrls.video;
        				    } else if (action == 'uploadfile') {//附件
        				    	return uploadUrls.file;
        				    } else {
        				        return this._bkGetActionUrl.call(this, action);
        				    }
        				}
    	            }
            	}
                
            }