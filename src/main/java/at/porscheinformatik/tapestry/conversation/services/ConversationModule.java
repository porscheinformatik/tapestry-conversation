package at.porscheinformatik.tapestry.conversation.services;

import org.apache.tapestry5.internal.services.PersistentFieldManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ApplicationStatePersistenceStrategy;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
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
import at.porscheinformatik.tapestry.conversation.internal.transform.ConversationWorker;
import at.porscheinformatik.tapestry.conversation.internal.transform.WindowStateWorker;

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

        // handles the injection of the conversation script, can run at any time assuming a
        // JavaScriptSupport is available
        configuration.addInstance("ConversationWorker", ConversationWorker.class, "after:JavaScriptSupport");
    }

    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.CONVERSATION_ID, "conversation");
        configuration.add(SymbolConstants.CONVERSATION_ALWAYS_ACTIVE, true);
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
