package core.preferences;

import java.io.Serializable;

import core.FormHeaderModel;

public class AdminPreferencesModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public AdminPreferencesModel() {
		_InitModel();
	}

	// VARIABLES
	public FormHeaderModel header;
	public String urlHostName;
	public String namesDbFilePath;
	public int attachmentsExpireDays;// Belongs to File Attachments Module
	public String helpdeskEmail;// Belongs to User Module
	public String portalTitle;
	public String navDrawerTitle;

	// PRIVATE METHODS
	private void _InitModel() {
		header = new FormHeaderModel();
		urlHostName = "localhost";
		namesDbFilePath = "names.nsf";
		attachmentsExpireDays = 5;// Default to 5 Days
		helpdeskEmail = "";
		portalTitle = "Ukuvuma XPages Boilerplate";
		navDrawerTitle = "Navigation Menu";
	}

	// GETTERS AND SETTERS
	public FormHeaderModel getHeader() {
		return header;
	}

	public void setHeader(FormHeaderModel header) {
		this.header = header;
	}

	public String getHelpdeskEmail() {
		return helpdeskEmail;
	}

	public void setHelpdeskEmail(String helpdeskEmail) {
		this.helpdeskEmail = helpdeskEmail;
	}

	public int getAttachmentsExpireDays() {
		return attachmentsExpireDays;
	}

	public void setAttachmentsExpireDays(int attachmentsExpireDays) {
		this.attachmentsExpireDays = attachmentsExpireDays;
	}

	public String getNamesDbFilePath() {
		return namesDbFilePath;
	}

	public void setNamesDbFilePath(String namesDbFilePath) {
		this.namesDbFilePath = namesDbFilePath;
	}

	public String getPortalTitle() {
		return portalTitle;
	}

	public void setPortalTitle(String portalTitle) {
		this.portalTitle = portalTitle;
	}

	public String getNavDrawerTitle() {
		return navDrawerTitle;
	}

	public void setNavDrawerTitle(String navDrawerTitle) {
		this.navDrawerTitle = navDrawerTitle;
	}

	public String getUrlHostName() {
		return urlHostName;
	}

	public void setUrlHostName(String urlHostName) {
		this.urlHostName = urlHostName;
	}
}
