package core.demo.app1;

import java.io.Serializable;

import core.FormHeaderModel;

public class App1Model implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public App1Model() {
		_InitModel();
	}

	// VARIABLES
	public FormHeaderModel header;
	public String field1;
	public String field2;

	// PRIVATE METHODS
	private void _InitModel() {
		header = new FormHeaderModel();
		field1 = "";
		field2 = "";
	}

	// GETTERS AND SETTERS
	public FormHeaderModel getHeader() {
		return header;
	}

	public void setHeader(FormHeaderModel header) {
		this.header = header;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}
}
