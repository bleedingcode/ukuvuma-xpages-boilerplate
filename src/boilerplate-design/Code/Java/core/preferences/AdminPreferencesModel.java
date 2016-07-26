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
	public String authType;// Authentication Type (Domino/LDAP/etc.)
	public String authFilePath;
	public String groupFilePath;
	public String uiTheme;
	public int attachmentsExpireDays;// Belongs to File Attachments Module
	public String helpdeskEmail;// Belongs to User Module

	// PRIVATE METHODS
	private void _InitModel() {
		header = new FormHeaderModel();
		authType = "domino";// Most of the time Domino will be used
		authFilePath = "names.nsf";// Most of the time it's the primary Domino
									// Directory
		groupFilePath = "names.nsf";// Most of the time it's the primary Domino
									// Directory
		uiTheme = "inspinia";// This will change most of the time to the
								// company's theme
		attachmentsExpireDays = 5;// Default to 5 Days
		helpdeskEmail = "";
	}

	// GETTERS AND SETTERS
	public FormHeaderModel getHeader() {
		return header;
	}

	public void setHeader(FormHeaderModel header) {
		this.header = header;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthFilePath() {
		return authFilePath;
	}

	public void setAuthFilePath(String authFilePath) {
		this.authFilePath = authFilePath;
	}

	public String getGroupFilePath() {
		return groupFilePath;
	}

	public void setGroupFilePath(String groupFilePath) {
		this.groupFilePath = groupFilePath;
	}

	public String getUiTheme() {
		return uiTheme;
	}

	public void setUiTheme(String uiTheme) {
		this.uiTheme = uiTheme;
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
}
