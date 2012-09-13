package at.porscheinformatik.tapestry.conversation;

/**
 * Symbol constants for conversation.
 * 
 * @author Gerold Glaser (gla)
 * @since 30.03.2012
 */
public final class SymbolConstants
{
    private SymbolConstants()
    {
    }

    /**
     * The parameter name used in the url to identify the window
     */
    public static final String CONVERSATION_ID = "poco.conversation.id";

    /**
     * Determines whether conversations should be always active, even when no
     * {@link at.porscheinformatik.tapestry.conversation.annotations.Conversation} annotation is present.
     */
    public static final String CONVERSATION_ALWAYS_ACTIVE = "poco.conversation.always.active";

}
