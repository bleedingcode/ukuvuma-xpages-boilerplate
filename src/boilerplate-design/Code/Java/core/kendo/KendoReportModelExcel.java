package core.kendo;

public class KendoReportModelExcel {
	// VARIABLES
	public boolean filterable;
	public String fileName;
	public String proxyURL;

	public KendoReportModelExcel() {
		filterable = true;
		fileName = "";
		proxyURL = "coreKendoExportProxy.xsp";
	}

	// GETTERS AND SETTERS
	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProxyURL() {
		return proxyURL;
	}

	public void setProxyURL(String proxyURL) {
		this.proxyURL = proxyURL;
	}
}
