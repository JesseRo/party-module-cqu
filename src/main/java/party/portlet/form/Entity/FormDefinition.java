package party.portlet.form.Entity;

import java.util.List;

public class FormDefinition {
    private List<Variable> variables;

    private List<Column> columns;

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

}
