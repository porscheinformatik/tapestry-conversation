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
    public static final String TIMEOUT = "10000";// 10 sec

    @BeforeTest(groups = "beforeStartup")
    void beforeStartup(XmlTest xmlTest)
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(TapestryTestConstants.WEB_APP_FOLDER_PARAMETER, "src/test/webapp");
        final String startupcommand = System.getProperty("tapestry.browser-start-command");
        if (startupcommand != null)
        {
            parameters.put(TapestryTestConstants.BROWSER_START_COMMAND_PARAMETER, startupcommand);
        }
        xmlTest.setParameters(parameters);
    }

    @Test
    public void windowIdPersistsOnPageLink() throws InterruptedException
    {
        open("/index");
        // TODO: it works only with this sleep
        // waitForPage and so on does not work
        Thread.sleep(1000L);
        clickAndWait("link=Form Test");
        assert getLocation().contains("conversation");
    }

    @Test
    public void windowIdPersistsOnAjax() throws InterruptedException
    {
        open("/index");
        // TODO: it works only with this sleep
        // waitForPage and so on does not work
        Thread.sleep(1000L);
        clickAndWait("link=Ajax Test");
        assert getLocation().contains("conversation");
        waitForAjaxRequestsToComplete(TIMEOUT);
        Thread.sleep(1000L);
        click("link=Load Zone");
//        waitForAjaxRequestsToComplete(TIMEOUT);
        Thread.sleep(1000L);
        assertTextPresent("In Zone");
        clickAndWait("link=In Zone");
        assert getLocation().contains("conversation");
    }
    
    @Test
    public void testSetupRender() throws InterruptedException {
        
        open("/index");
        Thread.sleep(1000L);
        clickAndWait("link=Check setup render");
        assertTextPresent("text was set in setup render");
    }
}
