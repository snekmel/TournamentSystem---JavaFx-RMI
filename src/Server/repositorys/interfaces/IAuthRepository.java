package Server.repositorys.interfaces;

import Server.models.Account;

public interface IAuthRepository {

    boolean CheckAuth(String email, String pwd);

    String createAccount(Account a);

    void deleteAccount(String key);

}
