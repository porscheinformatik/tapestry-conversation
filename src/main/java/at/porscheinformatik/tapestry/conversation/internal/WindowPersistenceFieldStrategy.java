/**
 * 
 */
package at.porscheinformatik.tapestry.conversation.internal;

import static org.apache.tapestry5.ioc.internal.util.CollectionFactory.newList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.internal.services.PersistentFieldChangeImpl;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.PersistentFieldChange;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import at.porscheinformatik.tapestry.conversation.services.WindowContext;

/**
 * The persist window object is stored for a page/component in the format <b>window:id:object</b>
 * 
 * @author Gerold Glaser (gla)
 * @since 30.03.2012
 */
public class WindowPersistenceFieldStrategy implements PersistentFieldStrategy
{
    /**
     * Prefix used to identify keys stored in the session.
     */
    static final String COLON = ":";
    static final String PREFIX = "window" + COLON;

    private final Request request;

    private final WindowContext windowContext;

    public WindowPersistenceFieldStrategy(Request request, WindowContext windowContext)
    {
        super();
        this.request = request;
        this.windowContext = windowContext;
    }

    public void postChange(String pageName, String componentId, String fieldName, Object newValue)
    {
        assert InternalUtils.isNonBlank(pageName);
        assert InternalUtils.isNonBlank(fieldName);
        Object persistedValue = newValue;

        StringBuilder builder = new StringBuilder(PREFIX);
        // TODO: What to do if id is null?
        builder.append(windowContext.getId());
        builder.append(':');
        builder.append(pageName);
        builder.append(':');

        if (componentId != null)
            builder.append(componentId);

        builder.append(':');
        builder.append(fieldName);

        Session session = request.getSession(persistedValue != null);

        // TAPESTRY-2308: The session will be false when newValue is null and the session
        // does not already exist.

        if (session != null)
        {
            session.setAttribute(builder.toString(), persistedValue);
        }

    }

    public Collection<PersistentFieldChange> gatherFieldChanges(String pageName)
    {
        Session session = request.getSession(false);

        if (session == null)
        {
            return Collections.emptyList();
        }
        // now check if the window

        List<PersistentFieldChange> result = newList();

        String fullPrefix = PREFIX + windowContext.getId() + COLON + pageName + COLON;

        for (String name : session.getAttributeNames(fullPrefix))
        {
            Object persistedValue = session.getAttribute(name);

            Object applicationValue = persistedValue;

            PersistentFieldChange change = buildChange(name, applicationValue);

            result.add(change);
        }

        return result;
    }

    public void discardChanges(String pageName)
    {
        Session session = request.getSession(false);

        if (session == null)
        {
            return;
        }

        String fullPrefix = PREFIX + windowContext.getId() + COLON + pageName + COLON;

        for (String name : session.getAttributeNames(fullPrefix))
        {
            session.setAttribute(name, null);
        }

    }

    private PersistentFieldChange buildChange(String name, Object newValue)
    {
        String[] chunks = name.split(":");

        // Will be empty string for the root component
        String componentId = chunks[3];
        String fieldName = chunks[4];

        return new PersistentFieldChangeImpl(componentId, fieldName, newValue);
    }
}
