package at.porscheinformatik.tapestry.conversation.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.internal.services.PersistentFieldManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationStatePersistenceStrategy;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import at.porscheinformatik.tapestry.conversation.ConversationPersistenceConstants;
import at.porscheinformatik.tapestry.conversation.SymbolConstants;
import at.porscheinformatik.tapestry.conversation.internal.ConversationLinkTransformer;
import at.porscheinformatik.tapestry.conversation.internal.DefaultConversationIdGenerator;
import at.porscheinformatik.tapestry.conversation.internal.InternalWindowContext;
import at.porscheinformatik.tapestry.conversation.internal.WindowApplicationStatePersistenceStrategy;
import at.porscheinformatik.tapestry.conversation.internal.WindowContextImpl;
import at.porscheinformatik.tapestry.conversation.internal.WindowPersistenceFieldStrategy;
import at.porscheinformatik.tapestry.conversation.internal.WindowStateManagerImpl;
import at.porscheinformatik.tapestry.conversation.internal.transform.WindowStateWorker;
import at.porscheinformatik.tapestry.conversation.pages.WindowGeneratorPage;

/**
 * Tapestry IoC Module for poco-tapestry5-conversation. CHECKSTYLE:OFF
 */
public class ConversationModule
{

    public static void bind(ServiceBinder binder)
    {
        binder.bind(InternalWindowContext.class, WindowContextImpl.class);
        binder.bind(ConversationLinkTransformer.class);
        binder.bind(WindowStateManager.class, WindowStateManagerImpl.class);
        binder.bind(ApplicationStatePersistenceStrategy.class, WindowApplicationStatePersistenceStrategy.class);
        binder.bind(ConversationIdGenerator.class, DefaultConversationIdGenerator.class);
    }
    
    @Contribute(ComponentClassResolver.class)
    public void contributeComponentClassResolver(final Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("conversation", "at.porscheinformatik.tapestry.conversation"));
    }

    @Contribute(PersistentFieldManager.class)
    public void addPersistWindowStrategy(MappedConfiguration<String, PersistentFieldStrategy> configuration,
        WindowContext windowContext,
        Request request)
    {
        configuration.add(ConversationPersistenceConstants.WINDOW, new WindowPersistenceFieldStrategy(request,
            windowContext));
    }

    @Contribute(RequestHandler.class)
    public static final void addRequestFilter(final OrderedConfiguration<RequestFilter> configuration,
        final ConversationLinkTransformer conversationLinkTransformer)
    {
        configuration.add("poco-conversation", conversationLinkTransformer);
    }

    @Contribute(ComponentEventLinkTransformer.class)
    public static void addLinkTransformer(OrderedConfiguration<ComponentEventLinkTransformer> configuration,
        final ConversationLinkTransformer conversationLinkTransformer)
    {
        configuration.add("poco-conversation", conversationLinkTransformer);
    }

    @Contribute(PageRenderLinkTransformer.class)
    public static void addPageLinkTransformer(OrderedConfiguration<PageRenderLinkTransformer> configuration,
        final ConversationLinkTransformer conversationLinkTransformer)
    {
        configuration.add("poco-conversation", conversationLinkTransformer);
    }

    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideTransformWorkers(
        OrderedConfiguration<ComponentClassTransformWorker2> configuration)
    {
        // These must come after Property, since they actually delete fields
        // that may still have the annotation
        configuration.addInstance("WindowState", WindowStateWorker.class, "after:Property");
    }

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.CONVERSATION_ID, "conversation");
    }

    public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
        final AssetSource assetSource, final ThreadLocale threadLocale, final Environment environment,
        final Request request, @Symbol(SymbolConstants.CONVERSATION_ID) final String conversationName,
        final PageRenderLinkSource pageRenderLinkSource)
    {
        final String uri = pageRenderLinkSource.createPageRenderLink(WindowGeneratorPage.class).toURI();

        MarkupRendererFilter injectScopesScript = new MarkupRendererFilter()
        {

            public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer)
            {
                JavaScriptSupport renderSupport = environment.peek(JavaScriptSupport.class);

                Asset validators =
                    assetSource
                        .getUnlocalizedAsset("at/porscheinformatik/tapestry/conversation/poco-tapestry-conversation.js");

                renderSupport.importJavaScriptLibrary(validators);

                JSONObject spec = new JSONObject();
                spec.put("contextPath", request.getContextPath());
                spec.put("conversationName", conversationName);
                spec.put("url", uri);

                renderSupport.addInitializerCall("conversationInit", spec);

                renderer.renderMarkup(writer);
            }
        };

        configuration.add("InjectScopesScript", injectScopesScript, "after:*");
    }

    /**
     * Contributes the default "session" strategy.
     */
    public void contributeApplicationStatePersistenceStrategySource(
        MappedConfiguration<String, ApplicationStatePersistenceStrategy> configuration,
        @Local ApplicationStatePersistenceStrategy windowStrategy)
    {
        configuration.add("window", windowStrategy);
    }
}
