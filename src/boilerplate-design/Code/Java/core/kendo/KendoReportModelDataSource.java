package core.kendo;

import java.util.ArrayList;

import com.ibm.commons.util.io.json.JsonJavaObject;

public class KendoReportModelDataSource {

	// VARIABLES
	public JsonJavaObject data;
	public boolean serverPaging;
	public boolean serverSorting;
	public boolean serverFiltering;
	public KendoReportModelDataSourceSchema schema;
	public ArrayList<JsonJavaObject> aggregate;

	// CONSTRUCTOR
	public KendoReportModelDataSource() {
		data = new JsonJavaObject();
		serverPaging = false;
		serverSorting = false;
		serverFiltering = false;
		schema = new KendoReportModelDataSourceSchema();
		aggregate = new ArrayList<JsonJavaObject>();
	}

	// GETTERS AND SETTERS
	public JsonJavaObject getData() {
		return data;
	}

	public void setData(JsonJavaObject data) {
		this.data = data;
	}

	public boolean isServerPaging() {
		return serverPaging;
	}

	public void setServerPaging(boolean serverPaging) {
		this.serverPaging = serverPaging;
	}

	public boolean isServerSorting() {
		return serverSorting;
	}

	public void setServerSorting(boolean serverSorting) {
		this.serverSorting = serverSorting;
	}

	public boolean isServerFiltering() {
		return serverFiltering;
	}

	public void setServerFiltering(boolean serverFiltering) {
		this.serverFiltering = serverFiltering;
	}

	public KendoReportModelDataSourceSchema getSchema() {
		return schema;
	}

	public void setSchema(KendoReportModelDataSourceSchema schema) {
		this.schema = schema;
	}

}
