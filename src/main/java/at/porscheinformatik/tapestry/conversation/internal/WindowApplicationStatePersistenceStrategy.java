package at.porscheinformatik.tapestry.conversation.internal;

import org.apache.tapestry5.internal.services.SessionApplicationStatePersistenceStrategy;
import org.apache.tapestry5.services.Request;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;

/**
 * Stores window-state data in session.
 */
public class WindowApplicationStatePersistenceStrategy extends SessionApplicationStatePersistenceStrategy
{
    private final WindowContext windowContext;

    /**
     * @param request .
     * @param windowContext .
     */
    public WindowApplicationStatePersistenceStrategy(Request request, WindowContext windowContext)
    {
        super(request);
        this.windowContext = windowContext;
    }

    @Override
    protected <T> String buildKey(Class<T> ssoClass)
    {
        return "sso:window:" + windowContext.getId() + ":" + ssoClass.getName();
    }
}
