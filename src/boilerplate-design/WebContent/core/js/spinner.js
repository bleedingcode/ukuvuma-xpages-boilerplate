//showSpinner("","#Div1");
//hideSpinner("#Div1", 1);
//showSpinner("","#Div2");
//hideSpinner("#Div2", 3);

//Spinners script
//Include target as .DivClass, #DivId
function showSpinner(type, target)
{
	jQuery(target).prepend(getSpinnerHtml(type, target));
}

//Delay will delay the spinner for delay time (seconds)
//if you don't pass a target all items will be removed
function hideSpinner(target, delay)
{
	setTimeout(function(){ 
		if (target == "") {
			jQuery(".spinnerMain").each(function(){
				this.remove();
			});			
		}
		else
		{
			jQuery(target).find(".spinnerMain:first").remove();
		}
		
	}, (delay * 1000));
	
}

function getSpinnerHtml(type, target)
{
	var spinnerText = "";
	
	//Sets the type as default to rp if it is empty
	type = type == "" ? "rp" : type;

	switch (type)
	{
		//Rotating Plane
		case "rp": spinnerText = '<div class="sk-spinner sk-spinner-rotating-plane"></div>'; 
		break;
		
		//Double Bounce 
		case "db": spinnerText = '<div class="sk-spinner sk-spinner-double-bounce"><div class="sk-double-bounce1"></div><div class="sk-double-bounce2"></div></div>'; 
		break;

		//Wave
		case "w": spinnerText = '<div class="sk-spinner sk-spinner-wave"><div class="sk-rect1"></div><div class="sk-rect2"></div><div class="sk-rect3"></div><div class="sk-rect4"></div><div class="sk-rect5"></div></div>'; 
		break;

		//Wondering Cubes
		case "wc": spinnerText = '<div class="sk-spinner sk-spinner-wandering-cubes"><div class="sk-cube1"></div><div class="sk-cube2"></div></div>'; 
		break;

		//Pulse
		case "p": spinnerText = '<div class="sk-spinner sk-spinner-pulse"></div>'; 
		break;

		//Chasing Dots
		case "cd": spinnerText = '<div class="sk-spinner sk-spinner-chasing-dots"><div class="sk-dot1"></div><div class="sk-dot2"></div></div>'; 
		break;

		//Three Bounce
		case "tb": spinnerText = '<div class="sk-spinner sk-spinner-three-bounce"><div class="sk-bounce1"></div><div class="sk-bounce2"></div><div class="sk-bounce3"></div></div>'; 
		break;

		//Circle
		case "c": spinnerText = '<div class="sk-spinner sk-spinner-circle"><div class="sk-circle1 sk-circle"></div><div class="sk-circle2 sk-circle"></div><div class="sk-circle3 sk-circle"></div><div class="sk-circle4 sk-circle"></div><div class="sk-circle5 sk-circle"></div><div class="sk-circle6 sk-circle"></div><div class="sk-circle7 sk-circle"></div><div class="sk-circle8 sk-circle"></div><div class="sk-circle9 sk-circle"></div><div class="sk-circle10 sk-circle"></div><div class="sk-circle11 sk-circle"></div><div class="sk-circle12 sk-circle"></div></div>'; 
		break;

		//Cube Grid
		case "cg": spinnerText = '<div class="sk-spinner sk-spinner-cube-grid"><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div><div class="sk-cube"></div></div>'; 
		break;

		//Fading Circle
		case "fc": spinnerText = '<div class="sk-spinner sk-spinner-fading-circle"><div class="sk-circle1 sk-circle"></div><div class="sk-circle2 sk-circle"></div><div class="sk-circle3 sk-circle"></div><div class="sk-circle4 sk-circle"></div><div class="sk-circle5 sk-circle"></div><div class="sk-circle6 sk-circle"></div><div class="sk-circle7 sk-circle"></div><div class="sk-circle8 sk-circle"></div><div class="sk-circle9 sk-circle"></div><div class="sk-circle10 sk-circle"></div><div class="sk-circle11 sk-circle"></div><div class="sk-circle12 sk-circle"></div></div>'; 
		break;
	}

	//Creates the modal popup script
	var getTargetHeight = jQuery(target).height();
	var modalBackground = '<div id="spinnerMain" class="spinnerMain" style="height:' + getTargetHeight + 'px; z-index: 1000; position: absolute; width:100%;"><div id="spinnerModal" style="width: 100%; height: 100%; opacity: 0.2; position: absolute;"></div><div id="spinner" style="  position: absolute;left: 50%;top: 50%;margin-left: -17px;margin-top: -17px;">' + spinnerText + '</div></div>'

	return modalBackground;
}
                           