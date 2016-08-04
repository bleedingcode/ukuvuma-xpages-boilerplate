package core.demo.app1;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.openntf.domino.Database;
import org.openntf.domino.Document;
import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;
import org.openntf.jsonbeanx.J2BConverter;

import core.AppController;
import core.FormHeaderModel;
import core.SessionController;
import core.Utilities;

public class App1Controller implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTANTS
	public static final String DEFAULT_FACET = "home";

	// VARIABLES
	App1Model app1Form;
	String currentFacet = "view";

	// PUBLIC METHODS
	public String CreateDocument() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		String result = "";

		try {
			app1Form = new App1Model();

			app1Form.header.documentTitle = "App 1 Form";
			app1Form.header.formName = "App1Form";

			// Finalise Header Result
			appCon.formHeader = app1Form.header;
			result = J2BConverter.beanToJson(app1Form.header);
			currentFacet = "form";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String CancelDocument() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		String result = "";

		try {
			appCon.formHeader = new FormHeaderModel();
			app1Form = null;
			result = J2BConverter.beanToJson(appCon.formHeader);
			currentFacet = "view";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String SaveDocument() {
		Session ss = Factory.getSession(SessionType.SIGNER);
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		SessionController userSession = (SessionController) context.getApplication().getVariableResolver().resolveVariable(
				context, "UserSession");

		Database db = null;
		Database dataDb = null;
		Document doc = null;

		String result = "";

		try {
			db = ss.getCurrentDatabase();
			dataDb = ss.getDatabase(db.getServer(), appCon.app.dataDbFilePath);

			if (dataDb.isOpen()) {
				// Check if Doc is new
				if (app1Form.header.id.equals("")) {
					doc = Utilities
							.SetupNewDocument(dataDb, app1Form.header.formName, userSession.getUser().canonicalName, false);
				} else {
					doc = dataDb.getDocumentByUNID(app1Form.header.id);
				}

				if (doc != null) {
					doc.replaceItemValue("Field1", app1Form.field1);
					doc.replaceItemValue("Field2", app1Form.field2);

					Utilities.SaveDocument(doc, false);
				}

				// Update Dropzone Objects
				appCon.formHeader = new FormHeaderModel();
				app1Form = null;
				result = J2BConverter.beanToJson(appCon.formHeader);
				currentFacet = "view";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String OpenDocument(String docId) {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database dataDb = null;
		Document doc = null;
		String result = "";

		try {
			db = ss.getCurrentDatabase();
			dataDb = ss.getDatabase(db.getServer(), appCon.app.dataDbFilePath);

			if (dataDb.isOpen()) {
				doc = dataDb.getDocumentByUNID(docId);

				if (doc != null) {
					app1Form = new App1Model();

					app1Form.header.isNewDoc = false;
					app1Form.header.id = docId;
					app1Form.header.readOnly = true;
					app1Form.field1 = doc.getItemValueString("Field1");
					app1Form.field2 = doc.getItemValueString("Field2");
					app1Form.header.documentTitle = app1Form.field1;
				}

				// Update Globals and return JSON Result
				appCon.formHeader = app1Form.header;
				result = J2BConverter.beanToJson(appCon.formHeader);
				currentFacet = "form";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// GETTERS AND SETTERS
	public String getCurrentFacet() {
		return currentFacet;
	}

	public void setCurrentFacet(String currentFacet) {
		this.currentFacet = currentFacet;
	}

	public App1Model getApp1Form() {
		return app1Form;
	}
}
