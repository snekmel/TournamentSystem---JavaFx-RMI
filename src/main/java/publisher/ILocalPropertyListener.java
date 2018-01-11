package publisher;

import java.beans.PropertyChangeEvent;

public interface ILocalPropertyListener extends IPropertyListener{

    void propertyChange(PropertyChangeEvent evt);
}
