package at.porscheinformatik.tapestry.conversation.internal.transform;

import java.util.List;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.internal.services.ComponentClassCache;
import org.apache.tapestry5.internal.transform.ReadOnlyComponentFieldConduit;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.FieldConduit;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import at.porscheinformatik.tapestry.conversation.annotations.WindowState;
import at.porscheinformatik.tapestry.conversation.services.WindowStateManager;

/**
 * Looks for the {@link WindowState} annotations and converts read and write access on such fields into calls to the
 * {@link WindowStateManager}.
 * 
 * @author Gerold Glaser (gla)
 * @since 29.03.2012
 */
public class WindowStateWorker implements ComponentClassTransformWorker2
{

    private final WindowStateManager windowStateManager;

    private final ComponentClassCache componentClassCache;

    public WindowStateWorker(@InjectService("WindowStateManager") WindowStateManager windowStateManager, ComponentClassCache componentClassCache)
    {
        super();
        this.windowStateManager = windowStateManager;
        this.componentClassCache = componentClassCache;
    }

    public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model)
    {
        for (PlasticField field : plasticClass.getFieldsWithAnnotation(WindowState.class))
        {
            WindowState annotation = field.getAnnotation(WindowState.class);

            transform(plasticClass, field, annotation.create());

            field.claim(annotation);
        }
    }

    @SuppressWarnings("unchecked")
    private void transform(PlasticClass transformation, PlasticField field, final boolean create)
    {
        final Class fieldClass = componentClassCache.forName(field.getTypeName());

        field.setConduit(new FieldConduit()
        {
            public void set(Object instance, InstanceContext context, Object newValue)
            {
                windowStateManager.set(fieldClass, newValue);
            }

            public Object get(Object instance, InstanceContext context)
            {
                return create ? windowStateManager.get(fieldClass) : windowStateManager
                    .getIfExists(fieldClass);
            }
        });

        final String expectedName = field.getName() + "Exists";

        List<PlasticField> fields = F.flow(transformation.getAllFields()).filter(new Predicate<PlasticField>()
        {
            public boolean accept(PlasticField field)
            {
                return field.getTypeName().equals("boolean") && field.getName().equalsIgnoreCase(expectedName);
            }
        }).toList();

        for (PlasticField existsField : fields)
        {
            existsField.claim(this);

            final String className = transformation.getClassName();

            final String fieldName = existsField.getName();

            existsField.setConduit(new ReadOnlyComponentFieldConduit(className, fieldName)
            {
                public Object get(Object instance, InstanceContext context)
                {
                    return windowStateManager.exists(fieldClass);
                }
            });
        }
    }

}
