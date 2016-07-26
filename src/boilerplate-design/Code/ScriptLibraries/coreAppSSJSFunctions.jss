function InitHomeAfterPageLoad(){
	var pageName = view.getPageName();

	if(pageName === "/login.xsp"){
		return false;
	}
	
	print("Key = " + sessionScope.ParamKey);
	print("Data = " + sessionScope.ParamData);
	
	var paramKey = sessionScope.ParamKey;
	var paramData = sessionScope.ParamData;
	
	if((paramKey !== null) && (paramData !== null)){
		//TODO: Action Page Operation based on parameters passed.
		App.ProcessPageParameters(pageName, paramKey, paramData);
		
		//Reset URL Parameters
		sessionScope.put("ParamKey", null);
		sessionScope.put("ParamData", null);
	}else{
		//TODO: Load defaults if there are no parameters
	}

	return true;
}

function ApplicationRedirectCheck(){
	var user = context.getUser().getCommonName();
	var pageName = view.getPageName();
	
	if(param.get("key") !== null && param.get("data") !== null){
		sessionScope.put("ParamKey", param.get("key"));
		sessionScope.put("ParamData", param.get("data"));
	}
	
	if(pageName === "/login.xsp"){
		if(user !== "Anonymous"){
			context.redirectToPage("home.xsp");	
		}
	}else if(pageName === "/home.xsp"){
		if(user === "Anonymous"){
			context.redirectToPage("login.xsp");	
		}
	}
	
	Globals.InitGlobalObjects();
	UserSession.InitUserSession();
	
	return true;
}