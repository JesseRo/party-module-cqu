package party.portlet.form.Entity;

import java.util.List;

public class ColumnType {
	
	public final static String TYPE_FILE = "file";
	public final static String TYPE_STRING = "str";
	public final static String TYPE_DATE = "date";
	public final static String TYPE_CHOSEN = "chosen";
	public final static String TYPE_CHOSEN_MULTIPLE = "chosen_multiple";
	public final static String TYPE_RICHTEXT = "richtext";

    private String name;
    
    private String checker;

    private String datePattern;

    private Object options;

    private String optionsKey;
    
    private String optionSql;

	private String optionNameField;
	
	private String optionValueField;
	
	public String getOptionSql() {
		return optionSql;
	}

	public void setOptionSql(String optionSql) {
		this.optionSql = optionSql;
	}

	public String getOptionNameField() {
		return optionNameField;
	}

	public void setOptionNameField(String optionNameField) {
		this.optionNameField = optionNameField;
	}

	public String getOptionValueField() {
		return optionValueField;
	}

	public void setOptionValueField(String optionValueField) {
		this.optionValueField = optionValueField;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getOptionsKey() {
        return optionsKey;
    }

    public void setOptionsKey(String optionsKey) {
        this.optionsKey = optionsKey;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

	public Object getOptions() {
		return options;
	}

	public void setOptions(Object options) {
		this.options = options;
	}

  
}
