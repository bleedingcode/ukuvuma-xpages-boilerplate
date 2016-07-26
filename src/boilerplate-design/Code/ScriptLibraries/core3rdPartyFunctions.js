function InitToastr() {
	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"progressBar" : true,
		"preventDuplicates" : false,
		"positionClass" : "toast-top-right",
		"onclick" : function() {
			console.log('click');
		},
		"showDuration" : "400",
		"hideDuration" : "1000",
		"timeOut" : "5000",
		"extendedTimeOut" : "3000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut"
	};

	return true;
}

function SweetConfirm(title, additionalText, functionToRun) {
	swal( {
		title : title,
		text : additionalText,
		type : "warning",
		showCancelButton : true,
		confirmButtonColor : "#DD6B55",
		confirmButtonText : "Yes",
		closeOnConfirm : true
	}, function() {
		functionToRun();
	});

	return true;
}