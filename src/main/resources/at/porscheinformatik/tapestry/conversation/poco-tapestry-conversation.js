var poco = poco || {};

poco.conversationInit = function(spec) 
{
	var path = spec.contextPath;
	var conversationName = spec.conversationName;
	
	var windowIdSearch = new RegExp(conversationName+"=([^\/]*)");
	windowIdSearch.exec(window.location.href);
	var windowId = RegExp.$1;

	if (!window.name || window.name.length < 3 || windowId != window.name)
	{	
		// alert("New window / changed window name");

		var now = new Date();
		window.name = now.getTime();
		// TODO get windowId from server via AJAX call

		if (window.location.href.indexOf(conversationName) >= 0)
		{
			window.location.href = window.location.href.replace(new RegExp(conversationName+"=[^\/]*"), conversationName + "=" + window.name)
		}
		else
		{
			if(window.location.pathname || '/' == window.location.pathname)
			{
				window.location.href = "/" + conversationName + "=" + window.name;
			}
			else
			{
				window.location.href = window.location.href.replace(window.location.pathname, "/"+conversationName +"=" + window.name + window.location.pathname);
			}
		}

		return;
	}
}

Tapestry.Initializer.conversationInit = function(spec) { poco.conversationInit(spec) }
