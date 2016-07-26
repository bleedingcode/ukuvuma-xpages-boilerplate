package core.kendo;

import com.ibm.commons.util.io.json.JsonJavaObject;

public class KendoReportModelDataSourceSchema {

	// VARIABLES
	public JsonJavaObject model;
	public String data;
	public String total;

	// CONSTRUCTOR
	public KendoReportModelDataSourceSchema() {
		JsonJavaObject tempJson = new JsonJavaObject();

		model = new JsonJavaObject();
		model.put("fields", tempJson);
		data = "ReportData";
		total = "Count";
	}

	// GETTERS AND SETTERS
	public JsonJavaObject getModel() {
		return model;
	}

	public void setModel(JsonJavaObject model) {
		this.model = model;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
