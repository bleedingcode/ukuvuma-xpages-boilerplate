package core.kendo;


public class KendoReportModelMain {
	// VARIABLES
	public int reportType;// 1 - Grid ; 2 - Bar Chart
	public KendoReportModelMainGrid grid;
	public KendoReportModelMainBarChart barChart;

	// CONSTRUCTOR
	public KendoReportModelMain() {
		reportType = 1;
		barChart = new KendoReportModelMainBarChart();
		grid = new KendoReportModelMainGrid();
	}

	// GETTERS AND SETTERS
	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public KendoReportModelMainGrid getGrid() {
		return grid;
	}

	public void setGrid(KendoReportModelMainGrid grid) {
		this.grid = grid;
	}

	public KendoReportModelMainBarChart getBarChart() {
		return barChart;
	}

	public void setBarChart(KendoReportModelMainBarChart barChart) {
		this.barChart = barChart;
	}
}
