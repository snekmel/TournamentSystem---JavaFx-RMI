package publisher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemotePublisherForDomain extends Remote{
    void registerProperty(String property) throws RemoteException;
    void unregisterProperty(String property) throws RemoteException;
    void inform(String property, Object oldValue, Object newValue)
            throws RemoteException;
    List<String> getProperties() throws RemoteException;
}
