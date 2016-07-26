function FixGeneralUIStyles(){
	//Override the body background-color. Struggling to get this right in a stylesheet
//	$("body").css({"background-color":"#fafafa"});
	
	//ExtLib is double setting buttons with btn-default. We need to remove it
	$('.btn-xpages-fix').removeClass("btn-default");
	return true;
}