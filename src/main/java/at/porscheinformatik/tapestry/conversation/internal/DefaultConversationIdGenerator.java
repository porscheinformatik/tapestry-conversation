package at.porscheinformatik.tapestry.conversation.internal;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.tapestry5.services.Request;

import at.porscheinformatik.tapestry.conversation.services.ConversationIdGenerator;

/**
 * Default Id Generator for the conversation ids
 * 
 * @author Gerold Glaser (gla)
 * @since 11.04.2012
 */
public class DefaultConversationIdGenerator implements ConversationIdGenerator
{
    private final AtomicLong sequenceNumber = new AtomicLong(System.currentTimeMillis());

    private final Request request;

    /**
     * @param request the request
     */
    public DefaultConversationIdGenerator(Request request)
    {
        super();
        this.request = request;
    }

    /**
     * {@inheritDoc}
     */
    public String generateId()
    {
        // at this point a new id is generated and the old one is encoded in the request - 
        // all props from the session for the old window id could be copied
        System.out.println("Old windowId: " + request.getParameter("conversationOld"));
        return Long.toString(sequenceNumber.getAndIncrement());
    }

}
