package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

public class ActivationRequestParamTest
{
    @ActivationRequestParameter
    @Property
    private Integer param;

    @OnEvent(component = "increment")
    void onIncrement()
    {
        param++;
    }
}
