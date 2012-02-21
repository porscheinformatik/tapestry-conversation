package at.porscheinformatik.tapestry.conversation.internal;

import static org.apache.tapestry5.ioc.ScopeConstants.PERTHREAD;

import org.apache.tapestry5.ioc.annotations.Scope;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;

/**
 * Holds the current window identifier per request (service is {@link ScopeConstants#PERTHREAD} scoped).
 */
@Scope(PERTHREAD)
public class WindowContextImpl implements WindowContext, InternalWindowContext
{
    private String currentWindowId;

    public String getId()
    {
        return currentWindowId;
    }

    /**
     * {@inheritDoc}
     */
    public void initWindowId(String windowId)
    {
        if (currentWindowId == null)
        {
            this.currentWindowId = windowId;
        }
        else
        {
            throw new IllegalStateException("windowId is already set");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void close()
    {
        currentWindowId = null;
    }
}
