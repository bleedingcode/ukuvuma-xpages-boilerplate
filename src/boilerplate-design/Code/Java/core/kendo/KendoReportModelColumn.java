package core.kendo;

import java.util.ArrayList;

import com.ibm.commons.util.io.json.JsonJavaObject;

public class KendoReportModelColumn {
	// VARIABLES
	public String field;
	public String title;
	public String format;
	public JsonJavaObject headerAttributes;
	public JsonJavaObject filterable;
	public ArrayList<String> aggregates;
	public String footerTemplate;
	public String groupFooterTemplate;

	// CONSTRUCTOR
	public KendoReportModelColumn() {
		field = "";
		title = "";
		format = "";

		headerAttributes = new JsonJavaObject();
		headerAttributes.put("style", "vertical-align:top");

		filterable = new JsonJavaObject();
		aggregates = new ArrayList<String>();
		footerTemplate = "";
		groupFooterTemplate = "";
	}

	// GETTERS AND SETTERS
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JsonJavaObject getHeaderAttributes() {
		return headerAttributes;
	}

	public void setHeaderAttributes(JsonJavaObject headerAttributes) {
		this.headerAttributes = headerAttributes;
	}

	public JsonJavaObject getFilterable() {
		return filterable;
	}

	public void setFilterable(JsonJavaObject filterable) {
		this.filterable = filterable;
	}

	public ArrayList<String> getAggregates() {
		return aggregates;
	}

	public void setAggregates(ArrayList<String> aggregates) {
		this.aggregates = aggregates;
	}

	public String getFooterTemplate() {
		return footerTemplate;
	}

	public void setFooterTemplate(String footerTemplate) {
		this.footerTemplate = footerTemplate;
	}

	public String getGroupFooterTemplate() {
		return groupFooterTemplate;
	}

	public void setGroupFooterTemplate(String groupFooterTemplate) {
		this.groupFooterTemplate = groupFooterTemplate;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
