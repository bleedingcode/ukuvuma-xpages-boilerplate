var appGlobals = {
	sideFormOpen : false,
	formHeader : null,
	app : null,
	dropzoneOptions : {
		url : "coreFileUpload.xsp?",
		paramName : "uploadedFile",
		defaultMessage : "Drop files or click here to upload"
	},
	modalOptions : {
		showDefault : {
			backdrop : 'static',
			keyboard : false
		}
	},
	select2Options : {
		defaultOptions : {
			width : "100%"
		},
		optionsInfinity : {
			width : "100%",
			minimumResultsForSearch : Infinity
		},
		viewSelection : {
			minimumResultsForSearch : Infinity,
			width : "200px"
		}
	}
};

function InitDefaults() {
	var myObject = coreRPC.ReturnAppHeader();

	myObject.addCallback( function(result) {
		appGlobals.app = JSON.parse(result);

		if (appGlobals.app.pageName === "home") {
			InitLeftMenu();
			InitToastr();
			Dropzone.autoDiscover = false;
		} else if (appGlobals.app.pageName === "login") {
			InitLoginPage();
		} else if (appGlobals.app.pageName === "demo") {
			Dropzone.autoDiscover = false;
			InitDropzoneForm();
			InitToastr();
		}

		$.material.init();
		FixGeneralUIStyles();
	});

	return true;
}

function InitLeftMenu() {
	var tmp = $(".drawer-nav li").find("[data-id='" + appGlobals.app.sectionName + "']");
	$(tmp[0].parentElement).addClass("active");

	$(".drawer-nav li a").click( function(e) {
		e.preventDefault();
		SwitchLeftMenu(e);
	});

	return true;
}

function InitViewSelection() {
	var key = ".comboViewPreviewSelection";
	$(key).select2("destroy");
	$(key).removeClass("form-control");
	var viewName = $(key).select2(appGlobals.select2ViewOptions);

	viewName.on("change", function(e) {
		var myObject = coreRPC.ChangeViewSelection(e.val);

		myObject.addCallback( function() {
			$('[id$=inputViewPreviewSearch]').val('');
			XSP.partialRefreshPost($('[id$=divViewContent]').attr('id'));
		});
	});

	var searchField = document.getElementById($('[id$=inputViewPreviewSearch]').attr('id'));

	searchField.addEventListener("keydown", function(e) {
		if (e.keyCode == 13 && !e.shiftKey) { // checks whether the pressed
				// key is "Enter"
			e.preventDefault();// Stop from entering new line unless shift
			// key was used
			InitSearchQuery();
		}
	});

	return true;
}

function InitSearchQuery() {
	XSP.partialRefreshPost($('[id$=divViewContent]').attr('id'));

	return true;
}

function InitLoginPage() {
	var usernameField = document.getElementById($('[id$=inputLoginUsername]').attr('id'));
	var passwordField = document.getElementById($('[id$=inputLoginPassword]').attr('id'));

	usernameField.addEventListener("keydown", function(e) {
		if (e.keyCode == 13 && !e.shiftKey) { // checks whether the pressed
				// key is "Enter"
			e.preventDefault();// Stop from entering new line unless shift
			// key was used
			$('[id$=btnLoginSignIn]').click();
		}
	});

	passwordField.addEventListener("keydown", function(e) {
		if (e.keyCode == 13 && !e.shiftKey) { // checks whether the pressed
				// key is "Enter"
			e.preventDefault();// Stop from entering new line unless shift
			// key was used
			$('[id$=btnLoginSignIn]').click();
		}
	});

	return true;
}

function OpenSideForm() {
	var myObject = coreRPC.ReturnFormHeader();
	var idContent = "";
	var idContainer = "";

	myObject.addCallback( function(result) {
		appGlobals.formHeader = JSON.parse(result);

		switch (appGlobals.formHeader.containerType) {
		case "modal":
			idContent = "divModalFormContainer";
			break;
		case "side":
			idContent = "divSideFormContent";
			break;
		}

		XSP.allowSubmit();
		XSP.partialRefreshGet($('[id$=' + idContent + ']').attr('id'), {
			onComplete : function() {
				if (!appGlobals.formHeader.readOnly) {
					InitDropzoneForm();
					FixGeneralUIStyles();
				}

				switch (appGlobals.formHeader.containerType) {
				case "modal":
					$('#' + idContent).modal(appGlobals.modalShowOptions);
					break;
				case "side":
					if (!appGlobals.sideFormOpen) {
						$("#divViewPreviewLayout").removeClass("col-lg-12").addClass("col-lg-8");
						$("#divSideFormContainer").toggle();

						appGlobals.sideFormOpen = true;
					}

					break;
				}
			}
		});

	});

	return true;
}

function CloseSideForm() {
	// Close Side Form if it's open
	if (appGlobals.sideFormOpen) {
		$("#divSideFormContainer").toggle();
		$('#divViewPreviewLayout').removeClass("col-lg-8").addClass("col-lg-12");
		document.body.scrollTop = document.documentElement.scrollTop = 0;
		appGlobals.sideFormOpen = false;
		XSP.showContent($('[id$="dynamicContentSideFormData"]').attr('id'), "blank");
	}

	return true;
}

function ToggleSideForm() {
	var myObject = coreRPC.ToggleDocumentMode();

	myObject.addCallback( function(result) {
		appGlobals.formHeader = JSON.parse(result);

		if (!appGlobals.formHeader.readOnly) {
			XSP.partialRefreshGet($('[id$=divSideFormContent]').attr('id'), {
				onComplete : function() {
					switch (appGlobals.formHeader.documentType) {
					case "dropzone":
						InitDropzoneForm();
						break;
					}

					FixGeneralUIStyles();
				}
			});
		}
	});

	return true;
}

function SwitchLeftMenu(e) {
	var newValue = e.currentTarget.attributes["data-id"].nodeValue;

	// Remove Active Classes and set new Active Menu Item
	$(".drawer-nav li").removeClass("active");
	$(e.currentTarget.parentElement).addClass("active");
	var myObject = coreRPC.SwitchLeftMenu(newValue);

	myObject.addCallback( function() {
		$('.drawer').drawer('toggle');

		XSP.partialRefreshGet($('[id$=wrapper]').attr('id'), {
			onComplete : function() {
				InitDefaults();
			}
		});
	});

	return true;
}

function LoginUser() {
	var usernameField = $('[id$=inputLoginUsername]');
	var passwordField = $('[id$=inputLoginPassword]');
	var returnMessage = "";

	require( [ "dojo/request/xhr" ], function(xhr) {
		xhr(appGlobals.app.portalFilePath + "?login", {
			method : "POST",
			data : {
				"username" : usernameField.val(),
				"password" : passwordField.val()
			},
			sync : true,
			handleAs : "text"
		}).then( function(e) {
			if (e.toLowerCase().indexOf("you are not authorized to perform this operation") > -1) {
				returnMessage = "Your account is not authorised";
				LogMessageToForm(returnMessage, "divErrorLoginMessages");
				usernameField.focus();
			} else if (e.toLowerCase().indexOf("invalid username") > -1) {
				returnMessage = "Login failed. Please try again";
				LogMessageToForm(returnMessage, "divErrorLoginMessages");
				usernameField.focus();
			} else if (document.cookie.match(/DomAuthSessId|LtpaToken|LtpaToken2/)) {
				location.reload();
			} else {
				returnMessage = "Login failed. Please try again";
				LogMessageToForm(returnMessage, "messagesLogin");
				usernameField.focus();
			}
		}, function(err) {
			console.log(err);
		}, function(evt) {
			console.log(evt);
		});
	});
}

function LogoutUser() {
	require( [ "dojo/cookie" ], function(cookie) {
		if (cookie("DomAuthSessId")) {
			cookie("DomAuthSessId", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}
		if (cookie("LtpaToken")) {
			cookie("LtpaToken", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}
		if (cookie("LtpaToken2")) {
			cookie("LtpaToken2", null, {
				path : "/",
				expires : "Thu, 01 Jan 1970 00:00:00 GMT"
			});
		}

		location.reload();
	});

	return true;
}

function LogMessageToForm(message, messageId) {
	$('[id$="' + messageId + '"]').html("<ul class='alert alert-danger'><li>" + message + "</li></ul>");
	return true;
}

// function ToggleFormWarningSection(key){
// var newKey = Humanize.capitalize(key);
//
// $(".div" + newKey + "CancelWarning").toggle();
// $("[id$=btn" + newKey + "Cancel]").attr("disabled", "disabled");
// $("[id$=btn" + newKey + "Save]").attr("disabled", "disabled");
//	
// return true;
// }

// function UnToggleFormWarningSection(key) {
// var newKey = Humanize.capitalize(key);
//	
// $(".div" + newKey + "CancelWarning").toggle();
// $("[id$=btn" + newKey + "Cancel]").removeAttr("disabled");
// $("[id$=btn" + newKey + "Save]").removeAttr("disabled");
//
// return true;
// }

function InitViewPreviewSelection() {
	var key = ".comboViewPreviewSelection";
	$(key).select2("destroy");
	$(key).removeClass("form-control");
	var viewName = $(key).select2(appGlobals.select2ViewOptions);

	viewName.on("change", function(e) {
		var myObject = coreRPC.ChangeViewSelection(e.val);

		myObject.addCallback( function() {
			$('[id$=inputViewPreviewSearch]').val('');
			XSP.partialRefreshPost($('[id$=divContainerView]').attr('id'));
		});
	});

	InitDropzoneSearch();

	return true;
}

function InitViewPreviewSearch() {
	var searchField = document.getElementById($('[id$=inputViewPreviewSearch]').attr('id'));
	var searchButton = document.getElementById($('[id$=btnViewPreviewSearch]').attr('id'));

	searchField.addEventListener("keydown", function(e) {
		if (e.keyCode == 13 && !e.shiftKey) { // checks whether the pressed
				// key is "Enter"
			e.preventDefault();// Stop from entering new line unless shift
			// key was used
			ProcessViewPreviewSearchQuery(e.target.value);
		}
	});

	searchButton.addEventListener("click", function(e) {
		ProcessViewPreviewSearchQuery($('[id$=inputViewPreviewSearch]').val());
	});

	return true;
}

function ProcessViewPreviewSearchQuery(searchValue) {
	XSP.partialRefreshPost($('[id$=divViewPreviewiBox]').attr('id'), {
		onComplete : function() {
			if (searchValue !== "") {
				$('[id$=linkViewPreviewClearSearch]').addClass('clear-icon-active');
			} else {
				$('[id$=linkViewPreviewClearSearch]').removeClass('clear-icon-active');
			}
		}
	});

	return true;
}

function ClearViewPreviewSearchQuery() {
	$('[id$=inputViewPreviewSearch]').val("");
	ProcessViewPreviewSearchQuery("");

	return true;
}

function LogMessage(messageId, message) {
	var html = "<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span>";
	html += "<span class='sr-only'>Error:</span>";
	html += " " + message;

	var styleClass = "alert alert-danger";

	$("#" + messageId).addClass(styleClass);
	$("#" + messageId).html(html);

	return true;
}

function ClearMessage(messageId) {
	$("#" + messageId).removeClass("alert alert-danger");
	$("#" + messageId).html("");

	return true;
}