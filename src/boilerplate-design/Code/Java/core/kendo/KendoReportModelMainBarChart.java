package core.kendo;

import java.util.ArrayList;

import com.ibm.commons.util.io.json.JsonJavaObject;

public class KendoReportModelMainBarChart {
	// VARIABLES
	public JsonJavaObject title;
	public JsonJavaObject legend;
	public JsonJavaObject seriesDefaults;
	public ArrayList<JsonJavaObject> series;
	public KendoReportModelValueAxis valueAxis;
	public JsonJavaObject categoryAxis;
	public KendoReportModelTooltip tooltip;

	// CONSTRUCTOR
	public KendoReportModelMainBarChart() {
		title = new JsonJavaObject();
		title.put("text", "Bar Chart");

		legend = new JsonJavaObject();
		legend.put("position", "bottom");

		seriesDefaults = new JsonJavaObject();
		seriesDefaults.put("type", "column");

		series = new ArrayList<JsonJavaObject>();
		valueAxis = new KendoReportModelValueAxis();
		categoryAxis = new JsonJavaObject();
		tooltip = new KendoReportModelTooltip();
	}

	// GETTERS AND SETTERS
	public JsonJavaObject getLegend() {
		return legend;
	}

	public void setLegend(JsonJavaObject legend) {
		this.legend = legend;
	}

	public JsonJavaObject getSeriesDefaults() {
		return seriesDefaults;
	}

	public void setSeriesDefaults(JsonJavaObject seriesDefaults) {
		this.seriesDefaults = seriesDefaults;
	}

	public ArrayList<JsonJavaObject> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<JsonJavaObject> series) {
		this.series = series;
	}

	public JsonJavaObject getCategoryAxis() {
		return categoryAxis;
	}

	public void setCategoryAxis(JsonJavaObject categoryAxis) {
		this.categoryAxis = categoryAxis;
	}

	public KendoReportModelValueAxis getValueAxis() {
		return valueAxis;
	}

	public void setValueAxis(KendoReportModelValueAxis valueAxis) {
		this.valueAxis = valueAxis;
	}

	public JsonJavaObject getTitle() {
		return title;
	}

	public void setTitle(JsonJavaObject title) {
		this.title = title;
	}

	public KendoReportModelTooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(KendoReportModelTooltip tooltip) {
		this.tooltip = tooltip;
	}
}
