/**
 * 
 */
package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import at.porscheinformatik.tapestry.conversation.ConversationPersistenceConstants;
import at.porscheinformatik.tapestry.conversation.data.WindowStateBean;

/**
 * End Demo Page to show the @Persist("window") behavior
 * 
 * @author Gerold Glaser (gla)
 */
public class PersistWindowEnd
{
    @Persist(ConversationPersistenceConstants.WINDOW)
    @Property(write = false)
    private WindowStateBean windowStateBean;
}
