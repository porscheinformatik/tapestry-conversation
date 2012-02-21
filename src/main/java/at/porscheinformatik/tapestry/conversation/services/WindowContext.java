package at.porscheinformatik.tapestry.conversation.services;

/**
 * Interface for handling the current window context as well as the conversations of the current window.
 */
public interface WindowContext
{
    /**
     * @return the id of the conversation (unique for each window/tab)
     */
    String getId();

    /**
     * Invalidate the whole {@link WindowContext} - invalidates all conversations in the current window immediately.
     */
    void close();
}
