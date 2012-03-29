/**
 * 
 */
package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.Property;

import at.porscheinformatik.tapestry.conversation.annotations.WindowState;
import at.porscheinformatik.tapestry.conversation.data.WindowStateBean;

/**
 * End Deo Page to show the @WindowState behavior
 * 
 * @author Gerold Glaser (gla)
 */
public class WindowStateEnd
{
    @WindowState
    @Property(write = false)
    private WindowStateBean windowStateBean;
}
