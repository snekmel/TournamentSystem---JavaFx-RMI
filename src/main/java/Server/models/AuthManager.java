package Server.models;

import MatchViewClient.models.ScoreboardCommunicator;
import Server.repositorys.AuthRepository;
import Server.repositorys.RegistryRepository;
import Server.repositorys.context.AuthRepositorySQL;
import Shared.interfaces.IAuthManager;
import Shared.interfaces.ITournamentManager;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthManager extends UnicastRemoteObject implements IAuthManager{

    private AuthRepository authRepository = new AuthRepository(new AuthRepositorySQL());
    private List<Account> accounts;
    private transient Registry registry = RegistryRepository.getRmiRegistry();


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
        try {
            if (this.authRepository.checkAuth(name, password)) {
                ITournamentManager itm = (ITournamentManager) this.registry.lookup("TournamentManager");
                return itm;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(AuthManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
