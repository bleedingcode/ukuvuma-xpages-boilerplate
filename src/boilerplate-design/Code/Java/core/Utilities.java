package core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.openntf.domino.Database;
import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.Item;
import org.openntf.domino.Name;
import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import com.ibm.xsp.extlib.component.dynamiccontent.UIDynamicContent;

public class Utilities {
	// TODO: See if we are going to use this.

	@SuppressWarnings("unchecked")
	public static void SetPageFacet(int facetType, String facetName, long delay) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map viewScope = context.getViewRoot().getViewMap();

		String previousFacet = "";
		String selectedFacet = "";

		try {
			switch (facetType) {
			case 1:// Login
				previousFacet = "previousLoginFacet";
				selectedFacet = "selectedLoginFacet";
				break;
			case 2:// Home
				previousFacet = "previousHomeFacet";
				selectedFacet = "selectedHomeFacet";
				break;
			case 3:// FormPlaceholderFacet
				previousFacet = "previousFormPlaceholderFacet";
				selectedFacet = "selectedFormPlaceholderFacet";
				break;
			}

			if (facetName.equals("Previous")) {
				SetPageFacet(facetType, viewScope.get(previousFacet).toString(), delay);
			} else if (!facetName.equals(viewScope.get(selectedFacet))) {
				if (delay > 0) {
					Thread.currentThread();
					Thread.sleep(delay);
				}

				viewScope.put(previousFacet, viewScope.get(selectedFacet));
				viewScope.put(selectedFacet, facetName);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Document SetupNewDocument(Database db, String formName, String username, boolean computeForm) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Document doc = null;
		Item item = null;
		DateTime tempDate = null;
		Name tempUser = null;

		try {
			if (db == null) {
				db = ss.getCurrentDatabase();
			}

			if (username == null) {
				tempUser = ss.createName(ss.getEffectiveUserName());
			} else {
				tempUser = ss.createName(username);
			}

			tempDate = ss.createDateTime(new Date());
			doc = db.createDocument();

			doc.replaceItemValue("Form", formName);
			doc.replaceItemValue("DateCreated", tempDate);
			doc.replaceItemValue("DateModified", tempDate);
			doc.replaceItemValue("AllDocReaders", "[AllDocReaders]");
			doc.replaceItemValue("AllDocAuthors", "[AllDocAuthors]");
			doc.replaceItemValue("CurrentReaders", tempUser.getCanonical());
			doc.replaceItemValue("CurrentAuthors", tempUser.getCanonical());
			doc.replaceItemValue("CreatedBy", tempUser.getCanonical());
			doc.replaceItemValue("ModifiedBy", tempUser.getCanonical());

			item = doc.getFirstItem("AllDocReaders");
			item.setReaders(true);

			item = doc.getFirstItem("AllDocAuthors");
			item.setAuthors(true);

			item = doc.getFirstItem("CurrentReaders");
			item.setReaders(true);

			item = doc.getFirstItem("CurrentAuthors");
			item.setAuthors(true);

			item = doc.getFirstItem("CreatedBy");
			item.setNames(true);

			item = doc.getFirstItem("ModifiedBy");
			item.setNames(true);

			if (computeForm) {
				doc.computeWithForm(false, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static Document SaveDocument(Document doc, boolean computeForm) {
		FacesContext context = FacesContext.getCurrentInstance();
		SessionController userSession = (SessionController) context.getApplication().getVariableResolver().resolveVariable(
				context, "UserSession");
		Document result = null;
		DateTime tempDate = doc.getParentDatabase().getParent().createDateTime(new Date());

		try {
			doc.replaceItemValue("ModifiedBy", userSession.getUser().canonicalName);
			doc.replaceItemValue("DateModified", tempDate);

			if (computeForm) {
				doc.computeWithForm(false, false);
			}

			doc.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static Document SaveBackgroundDocument(Database db, Document doc, boolean computeForm, String userCanonical) {
		Document result = null;
		DateTime tempDate = db.getParent().createDateTime(new Date());

		try {
			doc.replaceItemValue("ModifiedBy", userCanonical);
			doc.replaceItemValue("DateModified", tempDate);

			if (computeForm) {
				doc.computeWithForm(false, false);
			}

			doc.save();

			// Update Database Index
			// db.updateFTIndex(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("finally")
	public boolean ValidateDateRange(String startDate, String endDate) {
		boolean isValid = true;
		String tempStart = "";
		String tempEnd = "";

		try {
			if (!startDate.equals("") && !endDate.equals("")) {
				tempStart = startDate.replaceAll("/", "");
				tempEnd = endDate.replaceAll("/", "");

				if (Long.parseLong(tempStart) > Long.parseLong(tempEnd)) {
					isValid = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return isValid;
		}
	}

	public static boolean SwitchDynamicContent(String id, String value) {
		UIComponent component = FindComponent(FacesContext.getCurrentInstance().getViewRoot(), id);
		UIDynamicContent dynamicData = (UIDynamicContent) component;

		dynamicData.show(value);

		return true;
	}

	@SuppressWarnings("unchecked")
	public static UIComponent FindComponent(UIComponent topComponent, String compId) {

		if (compId == null)
			throw new NullPointerException("Component identifier cannot be null");

		if (compId.equals(topComponent.getId()))
			return topComponent;

		if (topComponent.getChildCount() > 0) {
			List<UIComponent> childComponents = topComponent.getChildren();

			for (UIComponent currChildComponent : childComponents) {
				UIComponent foundComponent = FindComponent(currChildComponent, compId);
				if (foundComponent != null)
					return foundComponent;
			}
		}
		return null;
	}
}
