package core;

import java.io.Serializable;

import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import com.google.gson.Gson;

import core.preferences.PreferencesController;

public class GlobalController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTANTS
	public static final String DYNAMIC_CONTENT_SIDE_FORM_DATA_ID = "dynamicContentSideFormData";
	public static final String DYNAMIC_CONTENT_MODAL_FORM_DATA_ID = "dynamicContentModalFormData";

	// VARIABLES
	public static Gson gson;
	public static PreferencesController prefCon;
	public static String portalDbFilePath = "agilit-e/design/agilitedata_100.nsf";
	public String agiliteDbFilePath = "agilit-e/design/agilitedata_100.nsf";
	private boolean isInitialized = false;

	// ADMIN PREFERENCES VARIABLES
	public static String namesDbPath;
	public static String namesDbUserView = "($Users)";
	public static String namesDbUserListView = "($VIMPeople)";
	public static String namesDbGroupListView = "($VIMGroups)";
	public static String namesDbGroupView = "($Groups)";
	public static String title = "XPages Base Template";
	public static String surveyDbFilePath = "clients/dev/monier/surveydata.nsf";
	public String urlHostName;

	// PUBLIC METHODS
	public void InitGlobalObjects() {
		if (!isInitialized) {
			Session ss = Factory.getSession(SessionType.SIGNER);

			if (ss.getServerName().equals("CN=UKUCLD00/OU=Server/O=Ukuvuma")) {
				namesDbPath = "ukucloud/design/names_ukucloud.nsf";
				urlHostName = "41.76.213.157";
			} else {
				namesDbPath = "names.nsf";
				urlHostName = "localhost";
			}

			gson = new Gson();
			prefCon = new PreferencesController();
			prefCon.InitAdminPreferences();
			isInitialized = true;
		}
	}

	// GETTERS AND SETTERS
	public Gson getGson() {
		return gson;
	}

	public String getTitle() {
		return title;
	}

	public static PreferencesController getPrefCon() {
		return prefCon;
	}

	public String getPortalDbFilePath() {
		return portalDbFilePath;
	}

	public static String getNamesDbUserView() {
		return namesDbUserView;
	}

	public static String getSurveyDbFilePath() {
		return surveyDbFilePath;
	}

	public String getNamesDbPath() {
		return namesDbPath;
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

	public String getUrlHostName() {
		return urlHostName;
	}

	public String getAgiliteDbFilePath() {
		return agiliteDbFilePath;
	}
}
