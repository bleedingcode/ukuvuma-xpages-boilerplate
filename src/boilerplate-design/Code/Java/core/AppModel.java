package core;

import java.io.Serializable;
import java.util.ArrayList;

public class AppModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public AppModel() {
		_InitModel();
	}

	// VARIABLES
	public String pageName;
	public String portalFilePath;
	public String navBarTitle;
	public String sectionName;
	public boolean canCreateDoc;

	public String dataDbFilePath;
	public String viewPreviewTitle;
	public String viewPreviewName;
	public ArrayList<String> viewPreviewList;
	public boolean isSmartphone;
	public boolean isTablet;
	public boolean isMobile;
	public String tableRowLabelPosition;

	// PRIVATE METHODS
	private void _InitModel() {
		pageName = "";// XPage being loaded
		portalFilePath = "";// File path of Portal Design (up to .nsf and
		// excluding host)
		navBarTitle = "";// For the Top Nav Bar
		sectionName = "";// Which section of your app is active
		canCreateDoc = false;// You might need more of these if there are many
		// Forms users can create

		// These variables might need to be duplicated depending on the number
		// of active Views
		dataDbFilePath = "";// File Path to Data Store
		viewPreviewTitle = "";
		viewPreviewName = "";// Default View to load
		viewPreviewList = new ArrayList<String>();// For View List Selections
		isSmartphone = false;
		isTablet = false;
		isMobile = false;
		tableRowLabelPosition = "left";
	}

	// GETTERS AND SETTERS
	public String getNavBarTitle() {
		return navBarTitle;
	}

	public void setNavBarTitle(String navBarTitle) {
		this.navBarTitle = navBarTitle;
	}

	public boolean isCanCreateDoc() {
		return canCreateDoc;
	}

	public void setCanCreateDoc(boolean canCreateDoc) {
		this.canCreateDoc = canCreateDoc;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPortalFilePath() {
		return portalFilePath;
	}

	public void setPortalFilePath(String portalFilePath) {
		this.portalFilePath = portalFilePath;
	}

	public String getDataDbFilePath() {
		return dataDbFilePath;
	}

	public void setDataDbFilePath(String dataDbFilePath) {
		this.dataDbFilePath = dataDbFilePath;
	}

	public ArrayList<String> getViewPreviewList() {
		return viewPreviewList;
	}

	public void setViewPreviewList(ArrayList<String> viewPreviewList) {
		this.viewPreviewList = viewPreviewList;
	}

	public String getViewPreviewTitle() {
		return viewPreviewTitle;
	}

	public void setViewPreviewTitle(String viewPreviewTitle) {
		this.viewPreviewTitle = viewPreviewTitle;
	}

	public String getViewPreviewName() {
		return viewPreviewName;
	}

	public void setViewPreviewName(String viewPreviewName) {
		this.viewPreviewName = viewPreviewName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public boolean isSmartphone() {
		return isSmartphone;
	}

	public void setSmartphone(boolean isSmartphone) {
		this.isSmartphone = isSmartphone;
	}

	public boolean isTablet() {
		return isTablet;
	}

	public void setTablet(boolean isTablet) {
		this.isTablet = isTablet;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	public String getTableRowLabelPosition() {
		return tableRowLabelPosition;
	}

	public void setTableRowLabelPosition(String tableRowLabelPosition) {
		this.tableRowLabelPosition = tableRowLabelPosition;
	}
}
