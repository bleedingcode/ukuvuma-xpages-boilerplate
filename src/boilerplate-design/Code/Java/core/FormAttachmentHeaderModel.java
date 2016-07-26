package core;

import java.io.Serializable;
import java.util.ArrayList;

public class FormAttachmentHeaderModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public FormAttachmentHeaderModel() {
		_InitModel();
	}

	// VARIABLES
	public String id;// Field name that the header applies to
	public String fileTypesAllowed;
	public int fileSize;
	public int noOfFilesAllowed;
	public boolean isActive;// Should the Dropzone Control be shown or not
	public ArrayList<FileEntryModel> fileList;
	public ArrayList<FileEntryModel> removeFileList;

	// PRIVATE METHODS
	private void _InitModel() {
		id = "";
		fileSize = 1000;
		noOfFilesAllowed = 100;
		isActive = true;
		fileTypesAllowed = null;
		fileList = new ArrayList<FileEntryModel>();
		removeFileList = new ArrayList<FileEntryModel>();
	}

	// GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getNoOfFilesAllowed() {
		return noOfFilesAllowed;
	}

	public void setNoOfFilesAllowed(int noOfFilesAllowed) {
		this.noOfFilesAllowed = noOfFilesAllowed;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public ArrayList<FileEntryModel> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<FileEntryModel> fileList) {
		this.fileList = fileList;
	}

	public ArrayList<FileEntryModel> getRemoveFileList() {
		return removeFileList;
	}

	public void setRemoveFileList(ArrayList<FileEntryModel> removeFileList) {
		this.removeFileList = removeFileList;
	}

	public String getFileTypesAllowed() {
		return fileTypesAllowed;
	}

	public void setFileTypesAllowed(String fileTypesAllowed) {
		this.fileTypesAllowed = fileTypesAllowed;
	}
}
