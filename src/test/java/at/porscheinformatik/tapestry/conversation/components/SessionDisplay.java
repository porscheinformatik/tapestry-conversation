package at.porscheinformatik.tapestry.conversation.components;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

public class SessionDisplay
{
    @Inject
    private Request request;

    @Property
    private String currentParam;

    @Cached
    public Session getSession()
    {
        return request.getSession(false);
    }

    @Cached
    public List<String> getParameterNames()
    {
        return getSession().getAttributeNames();
    }

    @Cached(watch = "currentParam")
    public Object getValue()
    {
        return getSession().getAttribute(currentParam);
    }
}
