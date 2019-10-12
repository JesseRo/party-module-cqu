function HgDoms(form) {
	
	
	
                this.form = form;
                var context = this;
                this.chosen = "     <div class=\"form-property\">\n" +
                    "                                <label class='form-title'>${name}</label>\n" +
                    "                                <select name=\"${id}\" ${extra}>\n" +
                    "                                    ${options}" +
                    "                                </select>\n" +
                    "                            </div>";

                this.option = "<option value='${value}'>${name}</option>";

                this.chosenPlaceholder = "<option disabled selected>请选择</option>";

                this.str = "           <div class=\"form-property\">\n" +
                    "                                <label class='form-title'>${name}</label>\n" +
                    "                                <input name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                    "                            </div>";
                
                this.date = "           <div class=\"form-property\">\n" +
                "                                <label class='form-title'>${name}</label>\n" +
                "                                <input class='datetime' name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                "                            </div>";
                
                this.file = " <div class=\"form-property\">\n" +
                "                                <label class='form-title'>${name}</label>\n" +
                "                                <input type='file' multiple name=\"${id}\" value=\"${value}\" ${extra}/>\n" +
                "                            </div>";

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
                        //extra += 'required ';
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
                        //extra += 'required ';
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

                }

                this.generateHtml = function (filter) {
                    var html = '';
                    for (var i in this.form.columns) {
                        var column = this.form.columns[i];
                        if(filter(column, parseInt(i))){
                            var generator = eval('generate_' + column.type.name);
                            html += generator(column);
                        }
                    }
                    return html;
                }
            }