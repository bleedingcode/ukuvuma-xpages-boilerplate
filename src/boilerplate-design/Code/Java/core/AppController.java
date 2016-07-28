package core;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import com.ibm.xsp.designer.context.XSPContext;
import com.ibm.xsp.extlib.beans.DeviceBean;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class AppController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public AppController() {
		_InitController();
	}

	// CONSTANTS
	private static final String DEFAULT_APP_SECTION = "home";

	// VARIABLES
	public AppModel app;
	public FormHeaderModel formHeader;

	// PUBLIC METHODS
	public void InitAppModel(String key) {
		XSPContext context = (XSPContext) ExtLibUtil.resolveVariable(FacesContext.getCurrentInstance(), "context");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		DeviceBean deviceBean = (DeviceBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext,
				"deviceBean");

		try {
			app = new AppModel();
			app.pageName = context.getUrl().getSiteRelativeAddress(context).toString().replace("/", "").replace(".xsp", "");
			app.portalFilePath = context.getUrl().getPath().replace(context.getUrl().getSiteRelativeAddress(context), "");
			app.sectionName = key;

			// Check device responsiveness
			if (deviceBean.isMobile() || deviceBean.isTablet()) {
				app.isMobile = true;
				app.tableRowLabelPosition = "above";

				if (deviceBean.isMobile()) {
					System.out.println("Smartphone");
					app.isSmartphone = true;
				} else if (deviceBean.isTablet()) {
					System.out.println("Tablet");
					app.isTablet = true;
				}
			}

			_ResetControllers();

			if (key.equals("x")) {
				// keywordsCon = new KeywordsController();
				// app.dataDbFilePath = "agilit-e/design/agilitedata_100.nsf";
			}

			// TODO: Complete these properties
			_InitAppAccessControl(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean SwitchLeftMenu(String newValue) {
		InitAppModel(newValue);

		// Switch Dynamic Controls
		System.out.println("HELLO!!! - " + newValue);
		Utilities.SwitchDynamicContent("dynamicContentMain", newValue);
		Utilities.SwitchDynamicContent("dynamicNavTopMenu", newValue);

		return true;
	}

	public boolean ChangeViewSelection(String key, String newViewName) {
		// Use key to determine which view property to change
		app.viewPreviewName = newViewName;
		return true;
	}

	public String ReturnAppHeader() {
		String result = "";

		try {
			result = GlobalController.gson.toJson(app);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String ReturnFormHeader() {
		String result = "";

		try {
			result = GlobalController.gson.toJson(formHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String ToggleDocumentMode() {
		String result = "";

		try {
			if (formHeader.readOnly) {
				formHeader.readOnly = false;
			} else {
				formHeader.readOnly = true;
			}

			result = GlobalController.gson.toJson(formHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void ProcessPageParameters(String pageName, String paramKey, String paramData) {
		try {
			if (pageName.equals("/home.xsp")) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PRIVATE METHODS
	private void _InitController() {
		try {
			// Init App Model
			InitAppModel(DEFAULT_APP_SECTION);
			formHeader = new FormHeaderModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _InitAppAccessControl(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		@SuppressWarnings("unused")
		SessionController userSession = (SessionController) context.getApplication().getVariableResolver().resolveVariable(
				context, "UserSession");

		try {
			app.canCreateDoc = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _ResetControllers() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GETTESR AND SETTERS
	public AppModel getApp() {
		return app;
	}

	public FormHeaderModel getFormHeader() {
		return formHeader;
	}
}