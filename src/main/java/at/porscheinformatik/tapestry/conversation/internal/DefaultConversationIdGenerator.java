package at.porscheinformatik.tapestry.conversation.internal;

import java.util.concurrent.atomic.AtomicLong;

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

    /**
     * {@inheritDoc}
     */
    public String generateId()
    {
        // at this point a new id is generated and the old one is encoded in the request - 
        // all props from the session for the old window id could be copied
        return Long.toString(sequenceNumber.getAndIncrement());
    }

}
