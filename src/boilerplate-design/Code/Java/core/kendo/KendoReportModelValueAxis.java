package core.kendo;

import com.ibm.commons.util.io.json.JsonJavaObject;

public class KendoReportModelValueAxis {
	// VARIABLES
	public JsonJavaObject labels;
	public JsonJavaObject line;
	public int axisCrossingValue;
	public double min;

	// CONSTRUCTOR
	public KendoReportModelValueAxis() {
		min = 0;
		labels = new JsonJavaObject();
		labels.put("format", "{0}");

		line = new JsonJavaObject();
		line.putBoolean("visible", false);
		axisCrossingValue = 0;
	}

	// GETTERS AND SETTERS
	public JsonJavaObject getLabels() {
		return labels;
	}

	public void setLabels(JsonJavaObject labels) {
		this.labels = labels;
	}

	public JsonJavaObject getLine() {
		return line;
	}

	public void setLine(JsonJavaObject line) {
		this.line = line;
	}

	public int getAxisCrossingValue() {
		return axisCrossingValue;
	}

	public void setAxisCrossingValue(int axisCrossingValue) {
		this.axisCrossingValue = axisCrossingValue;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

}
