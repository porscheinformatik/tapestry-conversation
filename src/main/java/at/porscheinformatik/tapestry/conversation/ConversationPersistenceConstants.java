package at.porscheinformatik.tapestry.conversation;

/**
 * Constants for persistent field strategies.
 * 
 * @see org.apache.tapestry5.annotations.Persist#value()
 */
public final class ConversationPersistenceConstants
{
    private ConversationPersistenceConstants()
    {
    }

    /**
     * The field's value is stored per browser window in the session. 
     */
    public static final String WINDOW = "window";
}
