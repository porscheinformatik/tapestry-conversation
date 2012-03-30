package at.porscheinformatik.tapestry.conversation.data;

import java.io.Serializable;

/**
 * Bean to demonstrate the @WindowState Annotation function
 * 
 * @author Gerold Glaser (gla)
 * @since 29.03.2012
 */
public class WindowStateBean implements Serializable
{
    private static final long serialVersionUID = 8804391761621664232L;

    public Long id;

    public String text;
    
    public String toString()
    {
        return String.format("WindowState={id=%s, text=%s}", id, text);
    }
}
