package at.porscheinformatik.tapestry.conversation.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class FormTest
{
    @SuppressWarnings("unused")
    @Property
    @Persist(PersistenceConstants.FLASH)
    private String name;
}
