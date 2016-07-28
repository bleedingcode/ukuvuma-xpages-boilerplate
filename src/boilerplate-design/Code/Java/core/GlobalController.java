package core;

import java.io.Serializable;

import org.openntf.domino.Database;
import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import com.google.gson.Gson;
import com.ibm.xsp.extlib.util.ExtLibUtil;

import core.preferences.AdminPreferencesModel;
import core.preferences.PreferencesController;

public class GlobalController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTANTS
	public static final String DYNAMIC_CONTENT_SIDE_FORM_DATA_ID = "dynamicContentSideFormData";
	public static final String DYNAMIC_CONTENT_MODAL_FORM_DATA_ID = "dynamicContentModalFormData";

	// VARIABLES
	public static Gson gson;
	public AdminPreferencesModel adminPref;
	private boolean isInitialized = false;

	// ADMIN PREFERENCES VARIABLES
	public static String namesDbUserView = "($Users)";
	public static String namesDbUserListView = "($VIMPeople)";
	public static String namesDbGroupListView = "($VIMGroups)";
	public static String namesDbGroupView = "($Groups)";
	public String portalDataFilePath;

	// PUBLIC METHODS
	public void InitGlobalObjects() {
		if (!isInitialized) {
			Session ss = Factory.getSession(SessionType.SIGNER);
			Database db = ss.getCurrentDatabase();
			String filePath = db.getFilePath().replace(db.getFileName(), "");
			portalDataFilePath = filePath + ExtLibUtil.getXspProperty("xsp.portal.datafilename");
			System.out.println(portalDataFilePath);
			gson = new Gson();
			adminPref = PreferencesController.InitAdminPreferences();
			isInitialized = true;
		}
	}

	// GETTERS AND SETTERS
	public Gson getGson() {
		return gson;
	}

	public static String getNamesDbUserView() {
		return namesDbUserView;
	}

	public static String getNamesDbGroupView() {
		return namesDbGroupView;
	}

	public static String getNamesDbUserListView() {
		return namesDbUserListView;
	}

	public static String getNamesDbGroupListView() {
		return namesDbGroupListView;
	}

	public AdminPreferencesModel getAdminPref() {
		return adminPref;
	}

	public String getPortalDataFilePath() {
		return portalDataFilePath;
	}
}
