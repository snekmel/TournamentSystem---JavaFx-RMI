package publisher;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemotePublisherForListener extends Remote{
    void subscribeRemoteListener(IRemotePropertyListener listener, String property)
            throws RemoteException;
    void unsubscribeRemoteListener(IRemotePropertyListener listener, String property)
            throws RemoteException;
}
