package at.porscheinformatik.tapestry.conversation.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;

/** 
 * Tapestry IoC Test module. 
 */
@SubModule(ConversationModule.class)
public class AppModule
{
    /**
     * Contribute application defaults
     * 
     * @param configuration configuration
     */
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "de,en");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "0.1-SNAPSHOT");
    }
}
