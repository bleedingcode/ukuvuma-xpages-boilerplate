package demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import lotus.domino.EmbeddedObject;

import org.openntf.domino.Database;
import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.RichTextItem;
import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;
import org.openntf.jsonbeanx.J2BConverter;

import core.AppController;
import core.FileAttachmentController;
import core.FileEntryModel;
import core.FormAttachmentHeaderModel;
import core.FormHeaderModel;
import core.GlobalController;
import core.SessionController;
import core.Utilities;

public class DropzoneController implements Serializable {
	private static final long serialVersionUID = 1L;

	// VARIABLES
	DropzoneModel dropNew;
	DropzoneModel dropEdit;

	// PUBLIC METHODS
	public String CreateDocument() {
		FacesContext context = FacesContext.getCurrentInstance();
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		String result = "";

		try {
			dropNew = new DropzoneModel();

			dropNew.header.documentTitle = "New Dropzone Form";
			dropNew.header.formName = "DropzoneRecord";

			// Finalise Header Result
			appCon.formHeader = dropNew.header;
			result = J2BConverter.beanToJson(dropNew.header);
			Utilities.SwitchDynamicContent(GlobalController.DYNAMIC_CONTENT_MODAL_FORM_DATA_ID, dropNew.header.formName);
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
			appCon.formHeader = null;

			if (dropNew != null) {
				dropNew = null;

				if (dropEdit != null) {
					appCon.formHeader = dropEdit.header;
				}
			} else {
				dropEdit = null;
			}

			// Finalise Json Result
			if (appCon.formHeader == null) {
				result = null;
				appCon.formHeader = new FormHeaderModel();
			} else {
				result = J2BConverter.beanToJson(appCon.formHeader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public String SaveDocument() {
		Session ss = Factory.getSession(SessionType.SIGNER);
		FacesContext context = FacesContext.getCurrentInstance();
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		SessionController userSession = (SessionController) context.getApplication().getVariableResolver().resolveVariable(
				context, "UserSession");

		Database db = null;
		Database dataDb = null;
		Document doc = null;
		RichTextItem rtItem = null;
		EmbeddedObject embed = null;

		String result = "";
		DropzoneModel tempDrop = null;
		String attachId = "";
		Iterator iterator = null;
		FormAttachmentHeaderModel file = null;
		ArrayList<String> fieldNames = new ArrayList<String>();

		try {
			db = ss.getCurrentDatabase();
			dataDb = ss.getDatabase(db.getServer(), globals.portalDataFilePath);
			DateTime tempDate = ss.createDateTime(new Date());

			if (dataDb.isOpen()) {
				if (appCon.formHeader.isNewDoc) {
					tempDrop = dropNew;
				} else {
					tempDrop = dropEdit;
				}

				// Add FieldNames to Array for use later on
				fieldNames.add(tempDrop.header.fileAttachmentHeaders.get(0).id);
				fieldNames.add(tempDrop.header.fileAttachmentHeaders.get(1).id);

				// Check if Doc is new
				if (tempDrop.header.id.equals("")) {
					attachId = tempDrop.header.tempId;
					doc = Utilities
							.SetupNewDocument(dataDb, tempDrop.header.formName, userSession.getUser().canonicalName, false);
				} else {
					attachId = tempDrop.header.id;
					doc = dataDb.getDocumentByUNID(tempDrop.header.id);
				}

				if (doc != null) {
					doc.replaceItemValue("ModifiedBy", userSession.getUser().username);
					doc.replaceItemValue("DateModified", tempDate);
					doc.replaceItemValue("Title", tempDrop.title);

					// Process Dropzone 1 Removals, if any
					file = tempDrop.header.fileAttachmentHeaders.get(0);
					if (file.removeFileList.size() > 0) {
						rtItem = (RichTextItem) doc.getFirstItem(file.id);

						if (rtItem != null) {
							if (!rtItem.getEmbeddedObjects().isEmpty()) {
								// Check removal list if names match and remove
								// accordingly
								for (FileEntryModel tempFile : file.removeFileList) {
									iterator = rtItem.getEmbeddedObjects().iterator();

									while (iterator.hasNext()) {
										embed = (EmbeddedObject) iterator.next();

										if (tempFile.name.equals(embed.getName())) {
											embed.remove();
										}
									}
								}
							}
						}
					}

					// Process Dropzone 2 Removals, if any
					file = tempDrop.header.fileAttachmentHeaders.get(1);
					if (file.removeFileList.size() > 0) {
						rtItem = (RichTextItem) doc.getFirstItem(file.id);

						if (rtItem != null) {
							if (!rtItem.getEmbeddedObjects().isEmpty()) {
								// Check removal list if names match and remove
								// accordingly
								for (FileEntryModel tempFile : file.removeFileList) {
									iterator = rtItem.getEmbeddedObjects().iterator();

									while (iterator.hasNext()) {
										embed = (EmbeddedObject) iterator.next();

										if (tempFile.name.equals(embed.getName())) {
											embed.remove();
										}
									}
								}
							}
						}
					}

					// Check if there are new Files to attach
					FileAttachmentController.ProcessAttachments(doc, attachId, fieldNames);

					doc.save();
				}

				// Cleanup Dropzone Attachments
				FileAttachmentController.RemoveAttachments(attachId, tempDrop.header.formName, fieldNames);

				// Update Database Index
				dataDb.updateFTIndex(true);

				// Update Dropzone Objects
				appCon.formHeader = null;

				if (dropNew != null) {
					// Check if Marketing Edit exists and if so, update Form
					// Template accordingly
					dropNew = null;

					if (dropEdit != null) {
						appCon.formHeader = dropEdit.header;
					}
				} else {
					// Edit Location is being closed. Nullify
					dropEdit = null;
				}

				if (appCon.formHeader == null) {
					result = null;
					appCon.formHeader = new FormHeaderModel();
				} else {
					result = J2BConverter.beanToJson(appCon.formHeader);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public String OpenDocument(String docId) {
		FacesContext context = FacesContext.getCurrentInstance();
		GlobalController globals = (GlobalController) context.getApplication().getVariableResolver().resolveVariable(context,
				"Globals");
		AppController appCon = (AppController) context.getApplication().getVariableResolver().resolveVariable(context, "App");
		Session ss = Factory.getSession(SessionType.SIGNER);
		HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

		Database db = null;
		Database dataDb = null;
		Document doc = null;
		RichTextItem rtItem = null;
		EmbeddedObject embed = null;

		String result = "";
		Iterator iterator = null;
		FileEntryModel tempImage;
		int imageIndex;

		try {
			db = ss.getCurrentDatabase();
			dataDb = ss.getDatabase(db.getServer(), globals.portalDataFilePath);

			if (dataDb.isOpen()) {
				doc = dataDb.getDocumentByUNID(docId);

				if (doc != null) {
					dropEdit = new DropzoneModel();

					dropEdit.header.isNewDoc = false;
					dropEdit.header.id = docId;
					dropEdit.header.tempId = docId;
					dropEdit.header.containerType = "side";
					dropEdit.header.formName = "DropzoneRecord";
					dropEdit.header.readOnly = true;
					dropEdit.header.labelPosition = "top";
					dropEdit.title = doc.getItemValueString("Title");
					dropEdit.header.documentTitle = dropEdit.title;

					// Load Body1 Images List
					rtItem = (RichTextItem) doc.getFirstItem(dropEdit.header.fileAttachmentHeaders.get(0).id);

					if (rtItem != null) {
						if (!rtItem.getEmbeddedObjects().isEmpty()) {
							iterator = rtItem.getEmbeddedObjects().iterator();
							imageIndex = 0;

							while (iterator.hasNext()) {
								embed = (EmbeddedObject) iterator.next();

								tempImage = new FileEntryModel();
								tempImage.id = String.valueOf(imageIndex);
								tempImage.name = embed.getName();
								tempImage.size = embed.getFileSize();
								tempImage.imageURL = httpRequest.getScheme() + "://" + httpRequest.getServerName() + "/"
										+ globals.portalDataFilePath + "/0/" + dropEdit.header.id + "/$File/" + embed.getSource()
										+ "?Open";

								dropEdit.header.fileAttachmentHeaders.get(0).fileList.add(tempImage);
								imageIndex++;
							}
						}
					}

					// Load Body2 Images List
					rtItem = (RichTextItem) doc.getFirstItem(dropEdit.header.fileAttachmentHeaders.get(1).id);

					if (rtItem != null) {
						if (!rtItem.getEmbeddedObjects().isEmpty()) {
							iterator = rtItem.getEmbeddedObjects().iterator();
							imageIndex = 0;

							while (iterator.hasNext()) {
								embed = (EmbeddedObject) iterator.next();

								tempImage = new FileEntryModel();
								tempImage.id = String.valueOf(imageIndex);
								tempImage.name = embed.getName();
								tempImage.size = embed.getFileSize();
								tempImage.imageURL = httpRequest.getScheme() + "://" + httpRequest.getServerName() + "/"
										+ globals.portalDataFilePath + "/0/" + dropEdit.header.id + "/$File/" + embed.getSource()
										+ "?Open";

								dropEdit.header.fileAttachmentHeaders.get(1).fileList.add(tempImage);
								imageIndex++;
							}
						}
					}
				}

				// Update Globals and return JSON Result
				appCon.formHeader = dropEdit.header;
				result = J2BConverter.beanToJson(appCon.formHeader);
				Utilities.SwitchDynamicContent(GlobalController.DYNAMIC_CONTENT_SIDE_FORM_DATA_ID, dropEdit.header.formName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean RemoveFile(int fileHeaderIndex, String id) {
		boolean result = true;

		try {
			for (FileEntryModel tempFile : dropEdit.header.fileAttachmentHeaders.get(fileHeaderIndex).fileList) {
				if (tempFile.id.equals(id)) {
					dropEdit.header.fileAttachmentHeaders.get(fileHeaderIndex).removeFileList.add(tempFile);
					dropEdit.header.fileAttachmentHeaders.get(fileHeaderIndex).fileList.remove(tempFile);
					break;
				}
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

		return result;
	}

	// PRIVATE METHODS

	// GETTERS AND SETTERS
	public DropzoneModel getDropNew() {
		return dropNew;
	}

	public void setDropNew(DropzoneModel dropNew) {
		this.dropNew = dropNew;
	}

	public DropzoneModel getDropEdit() {
		return dropEdit;
	}

	public void setDropEdit(DropzoneModel dropEdit) {
		this.dropEdit = dropEdit;
	}
}
