package core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openntf.domino.Database;
import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.DocumentCollection;
import org.openntf.domino.RichTextItem;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.utils.Factory.SessionType;

import com.ibm.xsp.http.UploadedFile;
import com.ibm.xsp.webapp.XspHttpServletResponse;

public class FileAttachmentController implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public FileAttachmentController() {
	}

	// CONSTANTS
	public static final String fileAttachView = "Core_TempFileAttachments";
	private static final String fileAttachForm = "Core_TempFileAttachment";
	public static final String fileAttachFieldName = "Body";

	// CONTANTS FOR DROPZONE
	private static final String DROPZONE_PARAM_FORM = "form";
	private static final String DROPZONE_PARAM_FIELD = "field";
	private static final String DROPZONE_PARAM_DOCID = "docId";
	private static String DROPZONE_PARAM_FILE = "uploadedFile";

	// VARIABLES

	// PUBLIC METHODS
	@SuppressWarnings("unchecked")
	public static void ProcessDropzone() {
		Session ss = Factory.getSession(SessionType.SIGNER);
		FacesContext facesContext = FacesContext.getCurrentInstance();

		XspHttpServletResponse response = null;
		PrintWriter pw = null;

		UploadedFile uploadedFile = null;
		File correctedFile = null;
		File tempFile = null;

		Database db = null;
		Database marketingDb = null;
		RichTextItem rtFiles = null;
		Document doc = null;

		String fileName = "";
		String formName = "";
		String fieldName = "";
		String docId = "";
		String indexKey = "";
		boolean renamed = false;
		ArrayList<String> fileArray = new ArrayList<String>();

		try {
			ExternalContext extCon = facesContext.getExternalContext();
			response = (XspHttpServletResponse) extCon.getResponse();
			pw = response.getWriter();

			// only HTTP POST is allowed
			HttpServletRequest request = (HttpServletRequest) extCon.getRequest();
			if (!request.getMethod().equalsIgnoreCase("post")) {
				throw (new Exception("Only POST is allowed"));
			}

			formName = request.getParameter(DROPZONE_PARAM_FORM);
			fieldName = request.getParameter(DROPZONE_PARAM_FIELD);
			docId = request.getParameter(DROPZONE_PARAM_DOCID);

			db = ss.getCurrentDatabase();
			marketingDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);

			// set up output object
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", -1);

			// check if we have a file in the POST
			Map map = request.getParameterMap();
			indexKey = "[0]";// Default this to perform Contains Key check

			if (!map.containsKey(DROPZONE_PARAM_FILE + indexKey)) {
				throw (new Exception("no file received"));
			}

			for (int x = 0, y = map.size(); x < y; x++) {
				indexKey = "[" + x + "]";

				// get the file from the request
				uploadedFile = (UploadedFile) map.get(DROPZONE_PARAM_FILE + indexKey);

				if (uploadedFile == null) {
					throw (new Exception("that's not a file!"));
				}

				// store file in a document
				fileName = uploadedFile.getClientFileName(); // original name of
				// the file
				tempFile = uploadedFile.getServerFile(); // the uploaded file
				// with a cryptic
				// name

				if (!fileArray.contains(fileName)) {
					System.out.println("Adding File = " + fileName);
					fileArray.add(fileName);
					correctedFile = new java.io.File(tempFile.getParentFile().getAbsolutePath() + java.io.File.separator
							+ fileName);
					renamed = tempFile.renameTo(correctedFile);

					if (renamed) {
						// create a document in the current db
						doc = marketingDb.createDocument();
						doc.replaceItemValue("Form", fileAttachForm);
						doc.replaceItemValue("ForForm", formName);
						doc.replaceItemValue("ForField", fieldName);
						doc.replaceItemValue("DocId", docId);

						// attach file to target document
						rtFiles = doc.createRichTextItem("Body");
						rtFiles.embedObject(lotus.domino.EmbeddedObject.EMBED_ATTACHMENT, "", correctedFile.getAbsolutePath(),
								null);

						doc.save();
						tempFile.delete();
						correctedFile.delete();
					}
				} else {
					System.out.println("Duplicate = " + fileName);
				}
			}

			pw.print("add code to return to the upload method here");
			response.commitResponse();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pw.print("add code here to return an error");

			try {
				response.commitResponse();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			facesContext.responseComplete();

			try {
				if (correctedFile != null) {
					// rename temporary file back to its original name so it's
					// automatically
					// deleted by the XPages engine
					correctedFile.renameTo(uploadedFile.getServerFile());
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public static void CleanupTempFileAttachments() {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database portalDb = null;
		View view = null;
		Document doc = null;

		int expireDays = 0;
		boolean isExpired = false;

		try {
			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);

			if (portalDb.isOpen()) {
				view = portalDb.getView(fileAttachView);
				view.setAutoUpdate(false);

				doc = view.getFirstDocument();
				expireDays = GlobalController.getPrefCon().getAdminPref().attachmentsExpireDays;

				while (doc != null) {
					isExpired = IsTempFileAttachmentExpired(doc, expireDays);

					if (isExpired) {
						doc.remove(true);// TODO: Test if this affects the
						// GetNextDocument
					}

					doc = view.getNextDocument(doc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean IsTempFileAttachmentExpired(Document doc, int expireDays) {
		// TODO: Test this properly
		Session ss = Factory.getSession(SessionType.SIGNER);

		DateTime tempDate = null;
		DateTime currDate = null;
		DateTime dateCreated = null;

		boolean result = false;

		try {
			tempDate = ss.createDateTime(new Date());
			dateCreated = ss.createDateTime(doc.getItemValueDateTimeArray("DateCreated").elementAt(0) + " "
					+ tempDate.getTimeOnly());
			currDate = ss.createDateTime(tempDate.getDateOnly() + " " + tempDate.getTimeOnly());
			currDate.adjustDay(-expireDays);

			if (currDate.isBefore(dateCreated)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void ProcessAttachments(Document doc, String id, ArrayList<String> fieldNames) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database portalDb = null;
		View view = null;
		DocumentCollection col = null;
		RichTextItem rtitem = null;
		RichTextItem tempRTItem = null;

		String key = "";

		try {
			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);
			view = portalDb.getView(FileAttachmentController.fileAttachView);

			// Check Rich Text Fields
			for (String fieldName : fieldNames) {
				rtitem = (RichTextItem) doc.getFirstItem(fieldName);

				if (rtitem == null) {
					rtitem = doc.createRichTextItem(fieldName);
				}

				key = id + ":" + doc.getItemValueString("Form") + ":" + fieldName;
				System.out.println("Key = " + key);
				col = view.getAllDocumentsByKey(key, true);

				for (Document tempDoc : col) {
					tempRTItem = (RichTextItem) tempDoc.getFirstItem(FileAttachmentController.fileAttachFieldName);
					rtitem.appendRTItem(tempRTItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void RemoveAttachments(String id, String formName, ArrayList<String> fieldNames) {
		Session ss = Factory.getSession(SessionType.SIGNER);

		Database db = null;
		Database portalDb = null;
		View view = null;
		DocumentCollection col = null;

		String key = "";

		try {
			db = ss.getCurrentDatabase();
			portalDb = ss.getDatabase(db.getServer(), GlobalController.portalDbFilePath);
			view = portalDb.getView(FileAttachmentController.fileAttachView);

			for (String fieldName : fieldNames) {
				key = id + ":" + formName + ":" + fieldName;
				col = view.getAllDocumentsByKey(key, true);

				if (col.getCount() > 0) {
					col.removeAll(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
