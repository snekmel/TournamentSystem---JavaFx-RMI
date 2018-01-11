package Server.models;

import Server.repositorys.AuthRepository;
import Server.repositorys.context.AuthRepositorySQL;
import Server.repositorys.interfaces.IAuthRepository;
import Shared.interfaces.IAuthManager;
import Shared.interfaces.ITournamentManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AuthManager extends UnicastRemoteObject implements IAuthManager{

    private AuthRepository authRepository = new AuthRepository(new AuthRepositorySQL());
    private List<Account> accounts;



    public AuthManager() throws RemoteException {
        this.accounts = this.authRepository.getAccounts();

    }



    public void registerAccount(String name, String password) throws RemoteException {
        Account account = new Account(name, password);
        accounts.add(account);
        this.authRepository.createAccount(account);
    }


    public void deleteAccount(String id) throws RemoteException {
        for (Account acc: this.accounts) {
                if (acc.getId().equals(id)){
                    this.accounts.remove(acc);
                    this.authRepository.deleteAccount(id);
                }
        }

        this.accounts = this.authRepository.getAccounts();
    }


    public ITournamentManager checkAuth(String name, String password) throws RemoteException {
            if (this.authRepository.checkAuth(name,password)){
               //TODO Haal uit registry ipv aanmken
                ITournamentManager itm = new TournamentManager();
                return itm;
            }
            else{
                return null;
            }
    }
}
