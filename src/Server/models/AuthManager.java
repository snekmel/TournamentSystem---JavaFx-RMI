package Server.models;

import Server.repositorys.interfaces.IAuthRepository;
import Shared.interfaces.IAuthManager;
import Shared.interfaces.ITournamentManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AuthManager extends UnicastRemoteObject implements IAuthManager{

    private List<Account> accounts;

    protected AuthManager() throws RemoteException {
    }


    @Override
    public void registerAccount(String name, String password) throws RemoteException {

    }

    @Override
    public void deleteAccount(String id) throws RemoteException {

    }

    @Override
    public ITournamentManager checkAuth(String name, String password) throws RemoteException {
        return null;
    }
}
