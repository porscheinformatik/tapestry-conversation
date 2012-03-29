package at.porscheinformatik.tapestry.conversation.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.linktransform.ComponentEventLinkTransformer;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import at.porscheinformatik.tapestry.conversation.internal.ConversationLinkTransformer;
import at.porscheinformatik.tapestry.conversation.internal.InternalWindowContext;
import at.porscheinformatik.tapestry.conversation.internal.WindowContextImpl;
import at.porscheinformatik.tapestry.conversation.internal.WindowStateManagerImpl;
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

    public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
        final AssetSource assetSource, final ThreadLocale threadLocale, final Environment environment,
        final Request request)
    {
        MarkupRendererFilter injectScopesScript = new MarkupRendererFilter()
        {
            public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer)
            {
                JavaScriptSupport renderSupport = environment.peek(JavaScriptSupport.class);

                Asset validators =
                    assetSource
                        .getUnlocalizedAsset("at/porscheinformatik/tapestry/conversation/poco-tapestry-conversation.js");

                renderSupport.importJavaScriptLibrary(validators);

                renderSupport.addInitializerCall("conversationInit", request.getContextPath());

                renderer.renderMarkup(writer);
            }
        };

        configuration.add("InjectScopesScript", injectScopesScript, "after:*");
    }

}
