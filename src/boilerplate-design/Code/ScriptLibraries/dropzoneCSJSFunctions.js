var dropGlobals = {
	drop1 : null,
	key : "dropzone"
};

function InitDropzoneForm() {
	// DROPZONE 1
	dropGlobals.drop1 = new Dropzone(
			'#divDropzoneDemo',
			{
				url : "/file/post",
				maxFilesize : 2,
				maxFiles : 3,
				parallelUploads : 2,
				clickable : true,
				uploadMultiple : true,
				addRemoveLinks : true,
				autoProcessQueue : false
			});
	
	return true;
}

function CreateDropzoneDocument() {
	// Make sure user is not editing a document
	if (appGlobals.formHeader) {
		if (!appGlobals.formHeader.readOnly) {
			sweetAlert("Oops...",
					"You have a document in edit mode. Please revise.", "error");
			return false;
		}
	}

	var myObject = coreRPC.CreateDropzoneDocument();

	myObject.addCallback( function(result) {
		appGlobals.formHeader = JSON.parse(result);

		// Refresh Edit Task Form and Load Modal
			XSP.allowSubmit();
			XSP.partialRefreshGet($('[id$="formModalContainerContent"]').attr(
					'id'), {
				onComplete : function() {
					if (appGlobals.formHeader) {
						if (!appGlobals.formHeader.readOnly) {
							InitDropzoneForm();
							FixGeneralUIStyles();
						} else {
							InitDropzoneForm();
							FixGeneralUIStyles();
						}

						switch (appGlobals.formHeader.containerType) {
						case "modal":
							$('#divModalFormContainer').modal(
									appGlobals.modalOptions.showDefault);
							break;
						}

						ToggleCreateBox();
					}
				}
			});
		});

	return true;
}

function CancelDropzoneDocument(forceCancel) {
	if (forceCancel === undefined) {
		forceCancel = true;
	}

	if ((!appGlobals.formHeader.readOnly) && (!forceCancel)) {
		SweetConfirm("Are you sure you want to Cancel this document?",
				"Any unsaved changes will be discarded", CancelDropzoneDocument);
		// ToggleFormWarningSection(dropGlobals.key);
	} else {
		var myObject = coreRPC.CancelDropzoneDocument();

		myObject.addCallback( function(result) {
			switch (appGlobals.formHeader.containerType) {
			case "modal":
				$('#divModalFormContainer').modal('hide');
				XSP.showContent($('[id$="dynamicContentModalFormData"]').attr(
						'id'), "blank");
				break;
			case "side":
				CloseSideForm();
				break;
			}

			if (result === null) {
				appGlobals.formHeader = result;
			} else {
				appGlobals.formHeader = JSON.parse(result);
			}
		});
	}

	return true;
}

function SaveDropzoneDocument() {
	showSpinner("c", "#divFormData");
	var myObject = coreRPC.IsValidationPassed();

	myObject.addCallback( function(result) {
		if (!result) {
			 $('html, body').animate({
			        scrollTop: $("[aria-invalid=true]").offset().top
			    }, 500);

			hideSpinner("#divFormData", 0.2);
			return false;
		} else {
			// Process Dropzone uploads
			if ((dropGlobals.drop1.getQueuedFiles().length > 0)
					&& (dropGlobals.drop2.getQueuedFiles().length > 0)) {
				// Process both Dropzones
				dropGlobals.drop1.on("queuecomplete", function(file) {
					dropGlobals.drop2.processQueue();
				});

				dropGlobals.drop2.on("completemultiple", function(file) {
					SaveDropzoneDocumentExtended();
				});

				dropGlobals.drop1.processQueue();
			} else if (dropGlobals.drop1.getQueuedFiles().length > 0) {
				// Process Main Dropzone
				dropGlobals.drop1.processQueue();

				dropGlobals.drop1.on("queuecomplete", function(file) {
					SaveDropzoneDocumentExtended();
				});
			} else if (dropGlobals.drop2.getQueuedFiles().length > 0) {
				// Process Other Dropzone
				dropGlobals.drop2.processQueue();

				dropGlobals.drop2.on("queuecomplete", function(file) {
					SaveDropzoneDocumentExtended();
				});
			} else {
				// No Files Added
				SaveDropzoneDocumentExtended();
			}
		}
	});

	return true;
}

function SaveDropzoneDocumentExtended() {
	var myObject2 = coreRPC.SaveDropzoneDocument();

	myObject2.addCallback( function(result) {
		// Close Document
			XSP.partialRefreshGet($('[id$="divViewPreviewiBox"]').attr('id'), {
				onComplete : function() {
					switch (appGlobals.formHeader.containerType) {
					case "modal":
						$('#divModalFormContainer').modal('hide');
						XSP.showContent(
								$('[id$="dynamicContentModalFormData"]').attr(
										'id'), "blank");
						break;
					case "side":
						CloseSideForm();
						break;
					}

					// Update Global Object
					if (result === null) {
						appGlobals.formHeader = result;
					} else {
						appGlobals.formHeader = JSON.parse(result);
					}

					hideSpinner("#divFormData", 0.2);
				}
			});
		});

	return true;
}