package at.porscheinformatik.tapestry.conversation.internal;

import org.apache.tapestry5.internal.services.SessionApplicationStatePersistenceStrategy;
import org.apache.tapestry5.services.Request;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;

public class WindowApplicationStatePersistenceStrategy extends SessionApplicationStatePersistenceStrategy
{
    private final WindowContext windowContext;

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
