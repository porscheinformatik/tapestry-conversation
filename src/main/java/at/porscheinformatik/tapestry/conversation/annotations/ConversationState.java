package at.porscheinformatik.tapestry.conversation.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.COMPONENT;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.MIXIN;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Marker annotation for a field that is a <em>conversation state object</em> (CSO) as controlled by the
 * {@link org.apache.tapestry5.services.ApplicationStateManager}.
 * <p>
 * Works similar to {@link org.apache.tapestry5.annotations.SessionState} but puts objects into conversation state.
 * <p>
 * TODO make this work!!
 */
@Target(FIELD)
@Documented
@Retention(RUNTIME)
@UseWith({COMPONENT, MIXIN, PAGE})
public @interface ConversationState
{
    /**
     * If true (the default), then referencing an field marked with the annotation will create the SSO. If false, then
     * accessing the field will not create the SSO, it will only allow access to it if it already exists.
     */
    boolean create() default true;
}
