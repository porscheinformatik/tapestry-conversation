package at.porscheinformatik.tapestry.conversation.internal;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;

/**
 * Internally used {@link WindowContext} - has some additional methods.
 */
public interface InternalWindowContext extends WindowContext
{
    /**
     * Inits the window id for the current request - only to be used in internal handler.
     * @param windowId .
     */
    void initWindowId(String windowId);
}
