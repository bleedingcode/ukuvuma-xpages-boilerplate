package core.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.faces.context.FacesContext;

import org.openntf.domino.Database;
import org.openntf.domino.Document;
import org.openntf.domino.Item;
import org.openntf.domino.Name;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import com.ibm.commons.util.io.json.JsonJavaObject;

import core.GlobalController;

public class UserController implements Serializable {
	private static final long serialVersionUID = 1L;

	// PUBLIC METHODS
	public static UserModel InitCurrentUser() {
		Session ss = Factory.getSession(SessionType.CURRENT);
		Database db = (Database) FacesContext.getCurrentInstance().getApplication().getVariableResolver().resolveVariable(
				FacesContext.getCurrentInstance(), "database");

		UserModel user = new UserModel();
		Name tempUser;

		try {
			// Init User Model and populate User Profile Data
			tempUser = ss.createName(ss.getEffectiveUserName());
			user = _FetchUserDetails(tempUser.getCanonical(), GlobalController.namesDbPath);

			// Check Access Roles
			Vector<String> userRoles = db.queryAccessRoles(tempUser.getCanonical());

			if (userRoles.contains("[Admin]")) {
				user.setAdmin(true);
			}
			if (userRoles.contains("[Debug]")) {
				user.setDebug(true);
			}

			System.out.println(GlobalController.gson.toJson(user));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	// TODO: We need to review this code, but we'll be using it definitely
	public String RegisterUser(UserModel user, boolean requiresConfirmation, String namesPath) {
		String returnValue = "True";// Default to Successful
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;
		Item item = null;
		Name tempUser = null;

		try {
			// Now we can Register User
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);// TODO

			// Make sure Address Book can be found
			if (!namesDb.isOpen()) {
				returnValue = "Cannot find AfriTug Address Book";// TODO
			} else {
				namesDoc = namesDb.createDocument();

				namesDoc.replaceItemValue("Form", "Person");
				namesDoc.replaceItemValue("Type", "Person");
				namesDoc.replaceItemValue("MailSystem", "3");
				namesDoc.replaceItemValue("FullName", "");
				namesDoc.replaceItemValue("FirstName", user.firstName);
				namesDoc.replaceItemValue("LastName", user.lastName);
				namesDoc.replaceItemValue("ShortName", user.username);
				namesDoc.replaceItemValue("InternetAddress", user.email);
				namesDoc.replaceItemValue("MailAddress", user.email);

				if (!requiresConfirmation) {
					namesDoc.replaceItemValue("HTTPPassword", user.password);
				}

				namesDoc.replaceItemValue("ATP", user.password);// Temp Password

				// Populate Username Field
				item = namesDoc.getFirstItem("FullName");
				item.setNames(true);

				tempUser = ss.createName(user.firstName + " " + user.lastName + "/" + "AfriTug");// TODO

				item.appendToTextList(tempUser.getCanonical());
				item.appendToTextList(user.username);
				item.appendToTextList(user.email);

				namesDoc.computeWithForm(false, false);
				namesDoc.save();
			}
		} catch (Exception e) {
			returnValue = "Error";
			e.printStackTrace();
		}

		// Return Value
		return returnValue;
	}

	// TODO: We need to review this code, but we'll be using it definitely
	public String DoesEmailExist(String email, String namesPath) {
		String doesExist = "False";

		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		View namesView = null;
		Document namesDoc = null;

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);// TODO

			// Make sure Address Book can be found
			if (!namesDb.isOpen()) {
				doesExist = "Cannot find AfriTug Address Book";// TODO
			} else {
				namesView = namesDb.getView("");// TODO
				namesDoc = namesView.getFirstDocumentByKey(email, true);

				if (namesDoc != null) {
					doesExist = "True";
				}
			}
		} catch (Exception e) {
			doesExist = "Error";
			e.printStackTrace();
		}

		// Recycle
		ss.recycle();// TODO: Check with Paul Withers if this is required

		// Return Value
		return doesExist;
	}

	// TODO: We need to review this code, but we'll be using it definitely
	public String DoesUsernameExist(String username, String namesPath) {
		String doesExist = "False";

		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		View namesView = null;
		Document namesDoc = null;

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);// TODO

			// Make sure Address Book can be found
			if (!namesDb.isOpen()) {
				doesExist = "Cannot find AfriTug Address Book";
			} else {
				namesView = namesDb.getView("");// TODO
				namesDoc = namesView.getFirstDocumentByKey(username, true);

				if (namesDoc != null) {
					doesExist = "True";
				}
			}
		} catch (Exception e) {
			doesExist = "Error";
			e.printStackTrace();
		}

		// Recycle
		ss.recycle();// TODO: Check with Paul Withers if this is required

		// Return Value
		return doesExist;
	}

	// TODO: We need to review this code, but we'll be using it definitely
	public String ConfirmRegistration(String id, String namesPath) {
		String returnValue = "False";// Default to Unsuccessful
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);// TODO

			// Make sure Address Book can be found
			if (!namesDb.isOpen()) {
				returnValue = "Cannot find AfriTug Address Book";
			} else {
				namesDoc = namesDb.getDocumentByUNID(id);

				if (namesDoc != null) {
					namesDoc.replaceItemValue("HTTPPassword", namesDoc.getItemValueString("ATP"));
					namesDoc.replaceItemValue("ATP", "");

					namesDoc.computeWithForm(false, false);
					namesDoc.save();
					returnValue = "True";
				}
			}
		} catch (Exception e) {
			returnValue = "Error";
			e.printStackTrace();
		}

		// Recycle
		ss.recycle();// TODO: Check with Paul Withers if this is required

		// Return Value
		return returnValue;
	}

	public static ArrayList<JsonJavaObject> GetUserListAsJSON(String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		View namesView = null;
		Document namesDoc = null;

		JsonJavaObject jsonEntry = null;
		ArrayList<JsonJavaObject> result = new ArrayList<JsonJavaObject>();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			// Make sure Address Book can be found
			if (namesDb.isOpen()) {
				namesView = namesDb.getView(GlobalController.getNamesDbUserListView());
				namesDoc = namesView.getFirstDocument();

				while (namesDoc != null) {
					jsonEntry = new JsonJavaObject();
					jsonEntry.putJsonProperty("id", namesDoc.getUniversalID());
					jsonEntry.putJsonProperty("text", namesDoc.getItemValueString("FirstName") + " "
							+ namesDoc.getItemValueString("LastName"));
					result.add(jsonEntry);

					namesDoc = namesView.getNextDocument(namesDoc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<UserModel> GetUsersByGroupId(String groupId, String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;

		UserModel user = null;
		ArrayList<UserModel> result = new ArrayList<UserModel>();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			// Make sure Address Book can be found
			if (namesDb.isOpen()) {
				namesDoc = namesDb.getDocumentByUNID(groupId);

				if (namesDoc != null) {
					for (Object username : namesDoc.getItemValue("Members")) {
						user = _FetchUserDetails((String) username, GlobalController.namesDbPath);

						if (user != null) {
							result.add(user);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<Object> GetUserCommonNamesById(ArrayList<Object> ids, String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;

		ArrayList<Object> result = new ArrayList<Object>();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			// Make sure Address Book can be found
			if (namesDb.isOpen()) {
				for (Object id : ids) {
					namesDoc = namesDb.getDocumentByUNID((String) id);

					if (namesDoc != null) {
						result.add(namesDoc.getItemValueString("FirstName") + " " + namesDoc.getItemValueString("LastName"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<Object> GetGroupNamesById(ArrayList<Object> ids, String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;

		ArrayList<Object> result = new ArrayList<Object>();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			// Make sure Address Book can be found
			if (namesDb.isOpen()) {
				for (Object id : ids) {
					namesDoc = namesDb.getDocumentByUNID((String) id);

					if (namesDoc != null) {
						result.add(namesDoc.getItemValueString("ListName"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<JsonJavaObject> GetGroupListAsJSON(String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		View namesView = null;
		Document namesDoc = null;

		JsonJavaObject jsonEntry = null;
		ArrayList<JsonJavaObject> result = new ArrayList<JsonJavaObject>();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			// Make sure Address Book can be found
			if (namesDb.isOpen()) {
				namesView = namesDb.getView(GlobalController.getNamesDbGroupListView());
				namesDoc = namesView.getFirstDocument();

				while (namesDoc != null) {
					jsonEntry = new JsonJavaObject();
					jsonEntry.putJsonProperty("id", namesDoc.getUniversalID());
					jsonEntry.putJsonProperty("text", namesDoc.getItemValueString("ListName"));
					result.add(jsonEntry);

					namesDoc = namesView.getNextDocument(namesDoc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static UserModel FetchUserDetailsById(String id, String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		Document namesDoc = null;
		Name tempUser = null;

		UserModel result = new UserModel();

		try {
			db = ss.getCurrentDatabase();
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			if (namesDb.isOpen()) {
				namesDoc = namesDb.getDocumentByUNID(id);

				if (namesDoc != null) {
					result = new UserModel();
					tempUser = ss.createName(namesDoc.getItemValue("FullName").elementAt(0).toString());

					result.id = id;
					result.canonicalName = tempUser.getCanonical();
					result.username = tempUser.getAbbreviated();
					result.firstName = namesDoc.getItemValueString("FirstName");
					result.lastName = namesDoc.getItemValueString("LastName");
					result.commonName = result.firstName + " " + result.lastName;
					result.email = namesDoc.getItemValueString("InternetAddress");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// PRIVATE METHODS
	private static UserModel _FetchUserDetails(String username, String namesPath) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database namesDb = null;
		View namesView = null;
		Document namesDoc = null;
		Name tempUser = null;

		UserModel result = new UserModel();

		try {
			db = ss.getCurrentDatabase();
			tempUser = ss.createName(username);
			namesDb = ss.getDatabase(db.getServer(), namesPath);

			if (namesDb.isOpen()) {
				namesView = namesDb.getView(GlobalController.getNamesDbUserView());
				namesDoc = namesView.getFirstDocumentByKey(tempUser.getCanonical(), true);

				if (namesDoc != null) {
					result = new UserModel();

					result.id = namesDoc.getUniversalID();
					result.canonicalName = tempUser.getCanonical();
					result.username = tempUser.getAbbreviated();
					result.firstName = namesDoc.getItemValueString("FirstName");
					result.lastName = namesDoc.getItemValueString("LastName");
					result.commonName = result.firstName + " " + result.lastName;
					result.email = namesDoc.getItemValueString("InternetAddress");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
