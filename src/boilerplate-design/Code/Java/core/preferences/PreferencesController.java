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

	// CONSTRUCTOR
	public PreferencesController() {
		InitAdminPreferences();
	}

	// CONSTANTS
	private static final String prefView = "Core_Preferences";
	private static final String prefFormName = "Core_Preferences";
	private static final String adminViewKey = "Admin";

	// VARIABLES
	AdminPreferencesModel adminPref;

	// PUBLIC METHODS
	public void InitAdminPreferences() {
		Document adminPrefDoc = null;

		try {
			adminPref = new AdminPreferencesModel();
			adminPrefDoc = GetAdminPreferences();

			if (adminPrefDoc == null) {
				adminPrefDoc = CreateDefaultAdminPreferences();
			}

			// Populate Global Admin Preferences Model
			PopulateGlobalAdminPreferences(adminPrefDoc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document CreateDefaultAdminPreferences() {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database portalDb = null;
		Document doc = null;

		AdminPreferencesModel tempPref;

		try {
			tempPref = new AdminPreferencesModel();

			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);

			if (portalDb.isOpen()) {
				doc = Utilities.SetupNewDocument(portalDb, prefFormName, null, false);

				doc.replaceItemValue("PreferencesType", adminViewKey);
				doc.replaceItemValue("AuthenticationType", tempPref.authType);
				doc.replaceItemValue("AuthenticationFilePath", tempPref.authFilePath);
				doc.replaceItemValue("GroupFilePath", tempPref.groupFilePath);
				doc.replaceItemValue("UITheme", tempPref.uiTheme);
				doc.replaceItemValue("AttachmentsExpireDays", tempPref.attachmentsExpireDays);
				doc.replaceItemValue("HelpdeskEmail", tempPref.helpdeskEmail);

				doc.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	public Document GetAdminPreferences() {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database portalDb = null;
		View view = null;
		Document doc = null;

		try {
			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);

			if (portalDb.isOpen()) {
				view = portalDb.getView(prefView);
				doc = view.getFirstDocumentByKey(adminViewKey, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	public void PopulateGlobalAdminPreferences(Document doc) {
		try {
			adminPref.authType = doc.getItemValueString("AuthenticationType");
			adminPref.authFilePath = doc.getItemValueString("AuthenticationFilePath");
			adminPref.groupFilePath = doc.getItemValueString("GroupFilePath");
			adminPref.uiTheme = doc.getItemValueString("UITheme");
			adminPref.attachmentsExpireDays = doc.getItemValueInteger("AttachmentsExpireDays");
			adminPref.helpdeskEmail = doc.getItemValueString("HelpdeskEmail");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String EditAdminPreferences() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");

		String result = "";

		try {
			adminPref.header.readOnly = false;

			// Finalise Header Result
			appCon.formHeader = adminPref.header;
			result = GlobalController.gson.toJson(adminPref.header);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void SaveAdminPreferences() {
		Document doc = null;

		try {
			doc = GetAdminPreferences();

			if (doc == null) {
				doc = CreateDefaultAdminPreferences();
			}

			doc.replaceItemValue("AuthenticationType", adminPref.authType);
			doc.replaceItemValue("AuthenticationFilePath", adminPref.authFilePath);
			doc.replaceItemValue("GroupFilePath", adminPref.groupFilePath);
			doc.replaceItemValue("UITheme", adminPref.uiTheme);
			doc.replaceItemValue("AttachmentsExpireDays", adminPref.attachmentsExpireDays);
			doc.replaceItemValue("HelpdeskEmail", adminPref.helpdeskEmail);

			doc.save();
			adminPref.header.readOnly = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PRIVATE METHODS
	public AdminPreferencesModel getAdminPref() {
		return adminPref;
	}

	public void setAdminPref(AdminPreferencesModel adminPref) {
		this.adminPref = adminPref;
	}
}
