package core.kendo;

import java.util.ArrayList;

public class KendoReportModelMainGrid {
	// VARIABLES
	public ArrayList<String> toolbar;
	public KendoReportModelExcel excel;
	public boolean sortable;
	public boolean groupable;
	public boolean scrollable;
	public boolean pageable;
	public boolean filterable;
	public KendoReportModelDataSource dataSource;
	public ArrayList<KendoReportModelColumn> columns;

	// CONSTRUCTOR
	public KendoReportModelMainGrid() {
		toolbar = new ArrayList<String>();
		toolbar.add("excel");
		toolbar.add("pdf");
		excel = new KendoReportModelExcel();
		sortable = true;
		groupable = true;
		scrollable = true;
		pageable = false;
		filterable = true;
		dataSource = new KendoReportModelDataSource();
		columns = new ArrayList<KendoReportModelColumn>();
	}

	// GETTERS AND SETTERS
	public ArrayList<String> getToolbar() {
		return toolbar;
	}

	public void setToolbar(ArrayList<String> toolbar) {
		this.toolbar = toolbar;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isGroupable() {
		return groupable;
	}

	public void setGroupable(boolean groupable) {
		this.groupable = groupable;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public ArrayList<KendoReportModelColumn> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<KendoReportModelColumn> columns) {
		this.columns = columns;
	}

	public KendoReportModelExcel getExcel() {
		return excel;
	}

	public void setExcel(KendoReportModelExcel excel) {
		this.excel = excel;
	}

	public KendoReportModelDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(KendoReportModelDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
