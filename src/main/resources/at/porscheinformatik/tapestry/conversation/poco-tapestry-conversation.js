var poco = poco || {};

poco.conversationInit = function(path) 
{
	var windowIdSearch = /WINDOWID=([^\/]*)/;
	windowIdSearch.exec(window.location.href);
	var windowId = RegExp.$1;

	if (!window.name || window.name.length < 3 || windowId != window.name)
	{	
		// alert("New window / changed window name");

		var now = new Date();
		window.name = now.getTime();
		// TODO get windowId from server via AJAX call

		if (window.location.href.indexOf("WINDOWID") >= 0)
		{
			window.location.href = window.location.href.replace(/WINDOWID=[^\/]*/, 'WINDOWID=' + window.name)
		}
		else
		{
			if(window.location.pathname || '/' == window.location.pathname)
			{
				window.location.href = "/WINDOWID=" + window.name;
			}
			else
			{
				window.location.href = window.location.href.replace(window.location.pathname, "/WINDOWID=" + window.name + window.location.pathname);
			}
		}

		return;
	}
}

Tapestry.Initializer.conversationInit = function(path) { poco.conversationInit(path) }
