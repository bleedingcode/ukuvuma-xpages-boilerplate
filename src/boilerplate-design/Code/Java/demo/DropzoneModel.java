package demo;

import java.io.Serializable;

import core.FormAttachmentHeaderModel;
import core.FormHeaderModel;

public class DropzoneModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public DropzoneModel() {
		_InitModel();
	}

	// VARIABLES
	public FormHeaderModel header;
	public String title;

	// PRIVATE METHODS
	private void _InitModel() {
		FormAttachmentHeaderModel fileHeader = null;

		header = new FormHeaderModel();
		title = "";

		// Setup the Attachment Options
		fileHeader = new FormAttachmentHeaderModel();
		fileHeader.id = "Body1";
		fileHeader.fileSize = 1;
		header.fileAttachmentHeaders.add(fileHeader);

		fileHeader = new FormAttachmentHeaderModel();
		fileHeader.id = "Body2";
		header.fileAttachmentHeaders.add(fileHeader);
	}

	// GETTERS AND SETTERS
	public FormHeaderModel getHeader() {
		return header;
	}

	public void setHeader(FormHeaderModel header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
