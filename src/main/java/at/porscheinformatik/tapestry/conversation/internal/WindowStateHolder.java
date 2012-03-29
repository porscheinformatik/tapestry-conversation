package at.porscheinformatik.tapestry.conversation.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WindowStateHolder
{
    private final Map<String, Map<Class<?>, Object>> objects = new HashMap<String, Map<Class<?>, Object>>();

    public <T> T getObject(String windowId, Class<T> clazz)
    {
        Map<Class<?>, Object> context = objects.get(windowId);
        if (context == null)
        {
            return null;
        }

        return (T) context.get(clazz);
    }

    public void setObject(String windowId, Object object)
    {
        if (objects.containsKey(windowId))
        {

            objects.get(windowId).put(object.getClass(), object);
        }
        else
        {
            Map<Class<?>, Object> context = new WeakHashMap<Class<?>, Object>();
            context.put(object.getClass(), object);

            objects.put(windowId, context);

        }
    }
}
