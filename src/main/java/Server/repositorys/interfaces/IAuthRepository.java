package Server.repositorys.interfaces;

import Server.models.Account;

import java.util.List;

public interface IAuthRepository {

    boolean CheckAuth(String userName, String pwd);

    String createAccount(Account a);

    void deleteAccount(String key);

    List<Account> getAccounts();

}
