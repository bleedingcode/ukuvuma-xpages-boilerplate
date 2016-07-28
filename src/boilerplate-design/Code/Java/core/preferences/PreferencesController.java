package core.preferences;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.openntf.domino.Database;
import org.openntf.domino.Document;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import core.AppController;
import core.GlobalController;
import core.Utilities;

public class PreferencesController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTANTS
	private static final String PREF_VIEW = "Core_Preferences";
	private static final String PREF_FORM_NAME = "Core_Preferences";
	private static final String ADMIN_VIEW_KEY = "Admin";

	// FIELD CONSTANTS
	private static final String PREFERENCES_TYPE = "PreferencesType";
	private static final String URL_HOST_NAME = "UrlHostName";
	private static final String NAMES_DB_FILE_PATH = "NamesDbFilePath";
	private static final String ATTACHMENT_EXPIRY_DAYS = "AttachmentsExpireDays";
	private static final String HELPDESK_EMAIL = "HelpdeskEmail";
	private static final String PORTAL_TITLE = "PortalTitle";
	private static final String NAV_DRAWER_TITLE = "NavDrawerTitle";

	public AdminPreferencesModel adminEdit;

	// PUBLIC METHODS
	public static AdminPreferencesModel InitAdminPreferences() {
		Document adminPrefDoc = null;
		AdminPreferencesModel adminPref = new AdminPreferencesModel();

		try {
			adminPrefDoc = GetAdminPreferences();

			// Database is initialised for the first time...populate defaults
			if (adminPrefDoc == null) {
				adminPrefDoc = CreateDefaultAdminPreferences();
			}

			// Populate Global Admin Preferences Model
			PopulateGlobalAdminPreferences(adminPref, adminPrefDoc);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminPref;
	}

	public static Document GetAdminPreferences() {
		Session ss = Factory.getSession(SessionType.SIGNER);
		FacesContext context = FacesContext.getCurrentInstance();
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");

		Database db = null;
		Database portalDb = null;
		View view = null;
		Document doc = null;

		try {
			db = ss.getCurrentDatabase();
			System.out.println(globals.portalDataFilePath);
			portalDb = ss.getDatabase(db.getServer(), globals.portalDataFilePath);

			if (portalDb.isOpen()) {
				view = portalDb.getView(PREF_VIEW);
				doc = view.getFirstDocumentByKey(ADMIN_VIEW_KEY, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static Document CreateDefaultAdminPreferences() {
		Session ss = Factory.getSession(SessionType.SIGNER);
		FacesContext context = FacesContext.getCurrentInstance();
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");

		Database db = null;
		Database portalDb = null;
		Document doc = null;

		AdminPreferencesModel tempPref;

		try {
			tempPref = new AdminPreferencesModel();

			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), globals.portalDataFilePath);

			if (portalDb.isOpen()) {
				doc = Utilities.SetupNewDocument(portalDb, PREF_FORM_NAME, null, false);

				doc.replaceItemValue(PREFERENCES_TYPE, ADMIN_VIEW_KEY);
				doc.replaceItemValue(URL_HOST_NAME, tempPref.urlHostName);
				doc.replaceItemValue(NAMES_DB_FILE_PATH, tempPref.namesDbFilePath);
				doc.replaceItemValue(ATTACHMENT_EXPIRY_DAYS, tempPref.attachmentsExpireDays);
				doc.replaceItemValue(HELPDESK_EMAIL, tempPref.helpdeskEmail);
				doc.replaceItemValue(PORTAL_TITLE, tempPref.portalTitle);
				doc.replaceItemValue(NAV_DRAWER_TITLE, tempPref.navDrawerTitle);

				doc.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static void PopulateGlobalAdminPreferences(AdminPreferencesModel adminPref, Document doc) {
		try {
			adminPref.urlHostName = doc.getItemValueString(URL_HOST_NAME);
			adminPref.namesDbFilePath = doc.getItemValueString(NAMES_DB_FILE_PATH);
			adminPref.attachmentsExpireDays = doc.getItemValueInteger(ATTACHMENT_EXPIRY_DAYS);
			adminPref.helpdeskEmail = doc.getItemValueString(HELPDESK_EMAIL);
			adminPref.portalTitle = doc.getItemValueString(PORTAL_TITLE);
			adminPref.navDrawerTitle = doc.getItemValueString(NAV_DRAWER_TITLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String EditAdminPreferences() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");
		String result = "";

		try {
			globals.adminPref.header.readOnly = false;

			// Finalise Header Result
			appCon.formHeader = globals.adminPref.header;
			result = GlobalController.gson.toJson(globals.adminPref.header);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void SaveAdminPreferences() {
		FacesContext context = FacesContext.getCurrentInstance();
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");
		Document doc = null;

		try {
			doc = GetAdminPreferences();

			if (doc == null) {
				doc = CreateDefaultAdminPreferences();
			}

			doc.replaceItemValue(URL_HOST_NAME, globals.adminPref.urlHostName);
			doc.replaceItemValue(NAMES_DB_FILE_PATH, globals.adminPref.namesDbFilePath);
			doc.replaceItemValue(ATTACHMENT_EXPIRY_DAYS, globals.adminPref.attachmentsExpireDays);
			doc.replaceItemValue(HELPDESK_EMAIL, globals.adminPref.helpdeskEmail);
			doc.replaceItemValue(PORTAL_TITLE, globals.adminPref.portalTitle);
			doc.replaceItemValue(NAV_DRAWER_TITLE, globals.adminPref.navDrawerTitle);

			doc.save();
			globals.adminPref.header.readOnly = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
