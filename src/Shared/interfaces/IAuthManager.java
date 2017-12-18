package Shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthManager extends Remote {
    void registerAccount(String name, String password) throws RemoteException;
    void deleteAccount(String id) throws  RemoteException;
    ITournamentManager checkAuth(String name, String password) throws RemoteException;
}
