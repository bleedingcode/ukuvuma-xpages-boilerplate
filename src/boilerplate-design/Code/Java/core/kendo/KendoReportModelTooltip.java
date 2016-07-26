package core.kendo;

public class KendoReportModelTooltip {
	// VARIABLES
	public boolean visible;
	public String format;
	public String template;

	// CONSTRUCTOR
	public KendoReportModelTooltip() {
		visible = true;
		format = "{0}";
		template = "#= series.name #: #= value #";
	}

	// GETTERS AND SETTERS
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
