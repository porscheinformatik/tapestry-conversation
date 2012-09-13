package at.porscheinformatik.tapestry.conversation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.AnnotationUseContext;
import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Marker annotation for a page that should be part of a conversation.
 *
 * @author Michael Aspetsberger
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@UseWith({AnnotationUseContext.PAGE})
public @interface Conversation
{
    /**
     * Indicates whether the conversation handling should be active for this page. When the handling
     * is active, a script will be injected that checks the conversation state.
     * <p>
     * <code>true</code> if conversations should be active, otherwise <code>false</code>.
     * <p> 
     * Default is <code>true</code>.
     */
    boolean active() default true;
}
