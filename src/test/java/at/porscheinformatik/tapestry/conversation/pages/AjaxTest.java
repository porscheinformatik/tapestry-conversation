package at.porscheinformatik.tapestry.conversation.pages;

import javax.inject.Inject;

import org.apache.tapestry5.Block;

public class AjaxTest
{
    @Inject
    private Block testblock;

    Block onLoadZone()
    {
        return testblock;
    }

    void onInZone()
    {
    }
}
