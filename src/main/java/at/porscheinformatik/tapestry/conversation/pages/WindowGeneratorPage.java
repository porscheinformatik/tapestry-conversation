package at.porscheinformatik.tapestry.conversation.pages;

import javax.inject.Inject;

import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;

import at.porscheinformatik.tapestry.conversation.services.ConversationIdGenerator;

/**
 * @author Gerold Glaser (gla)
 * @since 11.04.2012
 */
public class WindowGeneratorPage
{
    @Inject
    private ConversationIdGenerator conversationIdGenerator;

    TextStreamResponse onActivate()
    {
        return new TextStreamResponse("application/json", new JSONObject("conversationResponse",
            conversationIdGenerator.generateId()).toString());
    }
}
