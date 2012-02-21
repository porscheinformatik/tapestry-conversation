package at.porscheinformatik.tapestry.conversation;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.apache.tapestry5.test.TapestryTestConstants;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

public class WindowStateIntegrationTest extends SeleniumTestCase
{
    @BeforeTest(groups = "beforeStartup")
    void beforeStartup( XmlTest xmlTest)
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(TapestryTestConstants.WEB_APP_FOLDER_PARAMETER, "src/test/webapp");
        xmlTest.setParameters(parameters);
    }

    @Test
    public void windowIdPersistsOnPageLink()
    {
        open("/index");
        click("link=Form Test");
        assert getLocation().contains("WINDOWID");
    }

    @Test
    public void windowIdPersistsOnAjax()
    {
        open("/index");
        clickAndWait("link=Ajax Test");
        assert getLocation().contains("WINDOWID");
        click("link=Load Zone");
        waitForAjaxRequestsToComplete("200");
        assertTextPresent("In Zone");
        clickAndWait("link=In Zone");
        assert getLocation().contains("WINDOWID");
    }

}
