package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.Property;

import at.porscheinformatik.tapestry.conversation.annotations.WindowState;
import at.porscheinformatik.tapestry.conversation.data.WindowStateBean;

/**
 * Start demo Page to show the @WindowState behavior. Persists an Object in the windowstate and redirects to another
 * page in the same window.
 * 
 * @author Gerold Glaser (gla)
 */
public class WindowStateStart
{

    @WindowState
    @Property
    private WindowStateBean windowStateBean;

    void beginRender()
    {
        if (windowStateBean == null)
        {
            windowStateBean = new WindowStateBean();
        }
    }

    void onSuccessFromForm()
    {
        System.out.println("Saving: " + windowStateBean);
    }

    Object onActionFromRefresh()
    {
        return this;
    }

}
