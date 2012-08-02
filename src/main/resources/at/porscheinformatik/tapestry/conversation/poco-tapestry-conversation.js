var poco = poco || {};

poco.conversationInit = function(spec) 
{
	var contextPath = spec.contextPath;
	var conversationName = spec.conversationName;
	
	var windowIdSearch = new RegExp(conversationName+"=([^\/\?]*)");
	var windowMatch = windowIdSearch.exec(window.location.href);
	var windowId = windowMatch != null ? windowMatch[1] : undefined;

	if (!window.name || window.name.length < 3 || windowId != window.name)
	{	
		var successHandler = function(reply)
		{
			var conversationId = reply.conversationResponse;
			if (conversationId === undefined)
			{
				return;
			}
			
			window.name = conversationId;
			
			if (window.location.href.indexOf(conversationName + "=") >= 0)
			{
				// adapt url
				window.location.href = window.location.href.replace(new RegExp(conversationName+"=[^\/\?]*"), conversationName + "=" + window.name)
			}
			else
			{
				// rewrite url
				if(contextPath === "")
				{
					window.location.href = "/" + conversationName + "=" + window.name + window.location.pathname;
				}
				else
				{
					window.location.href = window.location.href.replace(contextPath, contextPath + "/" + conversationName + "=" + window.name);
				}
			}
		}
		
		// send old window id
		if (!!Tapestry.ajaxRequest) 
		{
			Tapestry.ajaxRequest(spec.url, {
				parameters : {"conversationOld" : windowId},
				onSuccess : function(data) { successHandler(data.responseJSON); }
			});
		}
		else if (typeof(jQuery) !== "undefined" && !!jQuery.tapestry && !!jQuery.tapestry.utils && !!jQuery.tapestry.utils.ajaxRequest) 
		{
			// GOT5
			jQuery.tapestry.utils.ajaxRequest({
				url: spec.url,
				data : {"conversationOld" : windowId},
				success : successHandler,
				cache: false
			});
		} 
		

	}
	return;
}

Tapestry.Initializer.conversationInit = function(spec) { poco.conversationInit(spec) }
