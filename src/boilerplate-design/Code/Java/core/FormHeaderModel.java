package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.openntf.domino.Name;
import org.openntf.domino.Session;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

public class FormHeaderModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public FormHeaderModel() {
		_InitModel();
	}

	// VARIABLES
	public String status;// 1-Success ; 2-Validation Failed ; 3-Error
	public String statusMessage;
	public boolean isNewDoc;
	public String id;
	public String author;
	public String tempId;
	public String documentTitle;
	public String formName;
	public String containerType;
	public boolean readOnly;
	public boolean canEditDoc;
	public boolean isModified;
	public boolean validationPassed;
	public String labelPosition;
	public ArrayList<FormAttachmentHeaderModel> fileAttachmentHeaders;

	// PRIVATE METHODS
	private void _InitModel() {
		Session ss = Factory.getSession(SessionType.CURRENT);
		Name tempUser = null;

		try {
			tempUser = ss.createName(ss.getEffectiveUserName());

			status = "1";// Success
			statusMessage = "";
			isNewDoc = true;
			id = "";
			tempId = UUID.randomUUID().toString();
			documentTitle = "New Document";
			formName = "";
			containerType = "main";
			readOnly = false;
			canEditDoc = true;
			isModified = true;
			validationPassed = false;
			labelPosition = "left";
			fileAttachmentHeaders = new ArrayList<FormAttachmentHeaderModel>();
			author = tempUser.getCanonical();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GETTERS AND SETTERS
	public boolean isNewDoc() {
		return isNewDoc;
	}

	public void setNewDoc(boolean isNewDoc) {
		this.isNewDoc = isNewDoc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isValidationPassed() {
		return validationPassed;
	}

	public void setValidationPassed(boolean validationPassed) {
		this.validationPassed = validationPassed;
	}

	public String getLabelPosition() {
		return labelPosition;
	}

	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<FormAttachmentHeaderModel> getFileAttachmentHeaders() {
		return fileAttachmentHeaders;
	}

	public void setFileAttachmentHeaders(ArrayList<FormAttachmentHeaderModel> fileAttachmentHeaders) {
		this.fileAttachmentHeaders = fileAttachmentHeaders;
	}

	public boolean isCanEditDoc() {
		return canEditDoc;
	}

	public void setCanEditDoc(boolean canEditDoc) {
		this.canEditDoc = canEditDoc;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}
