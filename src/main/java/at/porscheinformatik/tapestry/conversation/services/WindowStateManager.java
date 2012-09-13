package at.porscheinformatik.tapestry.conversation.services;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;
import org.apache.tapestry5.services.ApplicationStateContribution;

/**
 * TODO implement an state manager for WindowState at the moment on interface due to multiple type injection in tapestry
 * core
 */
@UsesMappedConfiguration(key = Class.class, value = ApplicationStateContribution.class)
public interface WindowStateManager
{
    /**
     * For a given class, find the SSO for the class, creating it if necessary. The manager has a configuration that
     * determines how an instance is stored and created as needed. The default (when there is no configuration for a SSO
     * type) is to instantiate the object with injected dependencies, via
     * {@link org.apache.tapestry5.ioc.ObjectLocator#autobuild(Class)}. This allows an SSO to keep references to
     * Tapestry IoC services or other objects that can be injected.
     * 
     * @param <T> 
     * @param ssoClass identifies the SSO to access or create
     * @return the SSO instance
     */
    <T> T get(Class<T> ssoClass);

    /**
     * For a given class, find the SSO for the class. The manager has a configuration that determines how an instance is
     * stored.
     * 
     * @param <T> 
     * @param ssoClass identifies the SSO to access or create
     * @return the SSO instance or null if it does not already exist
     */
    <T> T getIfExists(Class<T> ssoClass);

    /**
     * Returns true if the SSO already exists, false if it has not yet been created.
     *
     * @param <T> 
     * @param ssoClass used to select the SSO
     * @return true if SSO exists, false if null
     */
    <T> boolean exists(Class<T> ssoClass);

    /**
     * Stores a new SSO, replacing the existing SSO (if any). Storing the value null will delete the SSO so that it may
     * be re-created later.
     * 
     * @param <T> 
     * @param ssoClass the type of SSO
     * @param sso the SSO instance
     */
    <T> void set(Class<T> ssoClass, T sso);
}
