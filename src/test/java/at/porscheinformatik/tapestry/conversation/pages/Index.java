package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.Persist;

public class Index
{
    @Persist
    private Long id;
    
    void beginRender()
    {
        id = 12L;
    }
}
