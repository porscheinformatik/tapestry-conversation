package at.porscheinformatik.tapestry.conversation.internal;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.services.ApplicationStateCreator;
import org.apache.tapestry5.services.ApplicationStatePersistenceStrategy;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;
import at.porscheinformatik.tapestry.conversation.services.WindowStateManager;

/**
 * @author Gerold Glaser (gla)
 * @since 28.03.2012
 */
public class WindowStateManagerImpl implements WindowStateManager
{
    // is scoped per thread
    private final WindowContext windowContext;

    private final ApplicationStatePersistenceStrategy applicationStatePersistenceStrategy;

    private final ObjectLocator locator;

    public WindowStateManagerImpl(WindowContext windowContext,
        ApplicationStatePersistenceStrategy applicationStatePersistenceStrategy, ObjectLocator locator)
    {
        super();
        this.windowContext = windowContext;
        this.applicationStatePersistenceStrategy = applicationStatePersistenceStrategy;
        this.locator = locator;
    }

    public <T> T get(Class<T> ssoClass)
    {
        String windowId = windowContext.getId();
        ApplicationStateCreator createor = new ApplicationStateCreator<WindowStateHolder>()
        {
            public WindowStateHolder create()
            {
                return locator.autobuild("Instantiating instance of SSO class "
                    + WindowStateHolder.class.getName(), WindowStateHolder.class);
            }
        };

        WindowStateHolder holder = applicationStatePersistenceStrategy.get(WindowStateHolder.class, createor);
        return holder.getObject(windowId, ssoClass);

    }

    public <T> T getIfExists(Class<T> ssoClass)
    {
        if(exists(ssoClass))
        {
            return null;
        }
        return get(ssoClass);
    }

    public <T> boolean exists(Class<T> ssoClass)
    {
        
        if(applicationStatePersistenceStrategy.exists(WindowStateHolder.class))
        {
            return false;
        }
        else
        {
            return get(ssoClass) != null;
        }
    }

    public <T> void set(final Class<T> ssoClass, T SSO)
    {
        String windowId = windowContext.getId();
        System.out.println(windowId);
        if (windowId == null)
        {
            // TODO what to do in this case?
            throw new RuntimeException("No window id found");
        }

        ApplicationStateCreator createor = new ApplicationStateCreator<WindowStateHolder>()
        {
            public WindowStateHolder create()
            {
                return locator.autobuild("Instantiating instance of SSO class "
                    + WindowStateHolder.class.getName(), WindowStateHolder.class);
            }
        };

        WindowStateHolder holder = applicationStatePersistenceStrategy.get(WindowStateHolder.class, createor);
        holder.setObject(windowId, SSO);

        applicationStatePersistenceStrategy.set(WindowStateHolder.class, holder);

    }
}
