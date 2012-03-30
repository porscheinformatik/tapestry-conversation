package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import at.porscheinformatik.tapestry.conversation.ConversationPersistenceConstants;
import at.porscheinformatik.tapestry.conversation.data.WindowStateBean;

/**
 * Start demo Page to show the @Persist("window") behavior.
 * 
 * @author Gerold Glaser (gla)
 */
public class PersistWindowStart
{

    @Persist(ConversationPersistenceConstants.WINDOW)
    @Property
    private WindowStateBean windowStateBean;

    void beginRender()
    {
        if (windowStateBean == null)
        {
            windowStateBean = new WindowStateBean();
        }
    }

    Object onActionFromRefresh()
    {
        return this;
    }

    @DiscardAfter
    void onActionFromDiscard()
    {

    }

}
