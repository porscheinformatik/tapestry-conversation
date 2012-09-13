package at.porscheinformatik.tapestry.conversation.internal.transform;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import at.porscheinformatik.tapestry.conversation.SymbolConstants;
import at.porscheinformatik.tapestry.conversation.annotations.Conversation;
import at.porscheinformatik.tapestry.conversation.pages.WindowGeneratorPage;

/**
 * This worker handles the injection of the conversation-check script.
 * 
 * @author Michael Aspetsberger
 */
public class ConversationWorker implements ComponentClassTransformWorker2
{
    private final JavaScriptSupport javaScriptSupport;
    private final PageRenderLinkSource pageRenderLinkSource;
    private final Request request;
    private final String conversationName;
    private final boolean conversationAlwaysActive;
    private final AssetSource assetSource;

    /**
     * @param javaScriptSupport .
     * @param pageRenderLinkSource .
     * @param request .
     * @param conversationName .
     * @param conversationAlwaysActive .
     * @param assetSource .
     */
    public ConversationWorker(final JavaScriptSupport javaScriptSupport,
        final PageRenderLinkSource pageRenderLinkSource,
        final Request request,
        @Symbol(SymbolConstants.CONVERSATION_ID) final String conversationName,
        @Symbol(SymbolConstants.CONVERSATION_ALWAYS_ACTIVE) final boolean conversationAlwaysActive,
        final AssetSource assetSource)
    {
        this.javaScriptSupport = javaScriptSupport;
        this.pageRenderLinkSource = pageRenderLinkSource;
        this.request = request;
        this.conversationName = conversationName;
        this.conversationAlwaysActive = conversationAlwaysActive;
        this.assetSource = assetSource;
    }

    @Override
    public void transform(final PlasticClass plasticClass, final TransformationSupport support,
        final MutableComponentModel model)
    {
        if (!model.isPage())
        {
            // we only care about page requests
            return;
        }

        if (WindowGeneratorPage.class.getName().equals(model.getComponentClassName()))
        {
            // exclude the WindowGeneratorPage as it is used to generate the IDs
            return;
        }

        final Conversation conversation = plasticClass.getAnnotation(Conversation.class);

        if (conversation == null && conversationAlwaysActive || conversation != null && conversation.active())
        {
            // add the conversation check
            addAdvice(plasticClass, model);
        }
        else
        {
            // don't add it
        }
    }

    private void addAdvice(final PlasticClass plasticClass, final MutableComponentModel model)
    {
        PlasticMethod setupRender = plasticClass.introduceMethod(TransformConstants.SETUP_RENDER_DESCRIPTION);

        setupRender.addAdvice(new MethodAdvice()
        {
            @Override
            public void advise(final MethodInvocation invocation)
            {
                addConversationCheckScript();

                invocation.proceed();
            }
        });

        model.addRenderPhase(SetupRender.class);
    }

    private void addConversationCheckScript()
    {
        // the source for new coversation ids
        final String uri = pageRenderLinkSource.createPageRenderLink(WindowGeneratorPage.class).toURI();

        Asset conversationScript = assetSource.getUnlocalizedAsset(
            "at/porscheinformatik/tapestry/conversation/poco-tapestry-conversation.js"
            );

        javaScriptSupport.importJavaScriptLibrary(conversationScript);

        JSONObject spec = new JSONObject();
        spec.put("contextPath", request.getContextPath());
        spec.put("conversationName", conversationName);
        spec.put("url", uri);

        javaScriptSupport.addInitializerCall("conversationInit", spec);
    }

}
