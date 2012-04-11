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
        return Long.toString(sequenceNumber.getAndIncrement());
    }

}
