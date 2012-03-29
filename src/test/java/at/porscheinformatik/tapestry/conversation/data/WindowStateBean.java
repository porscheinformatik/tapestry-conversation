package at.porscheinformatik.tapestry.conversation.data;

/**
 * Bean to demonstrate the @WindowState Annotation function
 * 
 * @author Gerold Glaser (gla)
 * @since 29.03.2012
 */
public class WindowStateBean
{
    public Long id;

    public String text;
    
    public String toString()
    {
        return String.format("WindowState={id=%s, text=%s}", id, text);
    }
}
