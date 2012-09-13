package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.Property;

/**
 * In tapestry 5.3.3 there is a bug that prevents calling tapestry events. see:
 * https://issues.apache.org/jira/browse/TAP5-1752 should be fixed in 5.3.5 see:
 * https://issues.apache.org/jira/browse/TAP5-1979
 * 
 * @author gla
 */
public class SetupRenderTest
{
    @Property(write = false)
    private String stringToSetInSetupRender;

    void setupRender()
    {
        stringToSetInSetupRender = "text was set in setup render";
    }

}
