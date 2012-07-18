package at.porscheinformatik.tapestry.conversation.internal;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.DelegatingRequest;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;

import at.porscheinformatik.tapestry.conversation.SymbolConstants;

/**
 * Inits {@link WindowIdContext} with window id encoded in request and encodes window id in all requests.
 */
public class ConversationLinkTransformer implements PageRenderLinkTransformer, ComponentEventLinkTransformer,
    RequestFilter
{
    private final InternalWindowContext windowContext;
    private final String conversationName;

    @Inject
    private Request request;

    private Link transformWindowLink(final Link defaultLink)
    {
        String windowId = windowContext.getId();

        if (windowId != null)
        {
            String basePath = defaultLink.getBasePath();
            String contextPath = request.getContextPath();
            String windowParam = "/" + conversationName + "=" + windowId.toString();

            String newPath = "".equals(contextPath)
                ? windowParam + basePath
                : basePath.replace(contextPath, contextPath + windowParam);

            return defaultLink.copyWithBasePath(newPath);
        }
        return defaultLink;
    }

    /**
     * @param windowContext .
     */
    public ConversationLinkTransformer(final InternalWindowContext windowContext,
        @Symbol(SymbolConstants.CONVERSATION_ID) final String conversationName)
    {
        super();
        this.windowContext = windowContext;
        this.conversationName = conversationName;
    }

    /**
     * {@inheritDoc}
     */
    public Link transformComponentEventLink(Link defaultLink, ComponentEventRequestParameters parameters)
    {
        return transformWindowLink(defaultLink);
    }

    /**
     * {@inheritDoc}
     */
    public ComponentEventRequestParameters decodeComponentEventRequest(Request request)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Link transformPageRenderLink(Link defaultLink, PageRenderRequestParameters parameters)
    {
        return transformWindowLink(defaultLink);
    }

    /**
     * {@inheritDoc}
     */
    public PageRenderRequestParameters decodePageRenderRequest(Request request)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean service(Request request, Response response, RequestHandler handler) throws IOException
    {
        String requestPath = request.getPath();

        int windowIdIdx = requestPath.indexOf(conversationName + "=");
        if (windowIdIdx > 0)
        {
            int windowIdEndIdx = requestPath.indexOf('/', windowIdIdx);

            String windowId = windowIdEndIdx > 0
                ? requestPath.substring(windowIdIdx + conversationName.length()+1, windowIdEndIdx)
                : requestPath.substring(windowIdIdx + conversationName.length()+1);

            windowContext.initWindowId(windowId);

            final String newRequestPath =
                requestPath.substring(0, windowIdIdx)
                    + (windowIdEndIdx > 0 ? requestPath.substring(windowIdEndIdx + 1) : "");

            request = new DelegatingRequest(request)
            {
                @Override
                public String getPath()
                {
                    return newRequestPath;
                }
            };
        }

        return handler.service(request, response);
    }
}
