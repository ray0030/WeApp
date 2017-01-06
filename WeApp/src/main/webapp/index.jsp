<%
String getUserAgent=request.getHeader("User-Agent").toLowerCase();

if(null == getUserAgent){
	response.sendRedirect("/platform/login");
}
getUserAgent = getUserAgent.toLowerCase();

	response.sendRedirect(request.getContextPath()+"/platform/login.jsp");
/*if(getUserAgent.indexOf("iphone")>-1
|| getUserAgent.indexOf("android")>-1
|| getUserAgent.indexOf("phone")>-1
|| getUserAgent.indexOf("ucbrowser")>-1
|| getUserAgent.indexOf("micromessenger")>-1){
	response.sendRedirect("platform/mobile/index.html");
}else{
	response.sendRedirect("platform/web/index.html");
}*/
%>