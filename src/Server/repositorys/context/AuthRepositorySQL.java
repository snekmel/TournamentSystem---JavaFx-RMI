package Server.repositorys.context;

import Server.models.Account;
import Server.repositorys.interfaces.IAuthRepository;

public class AuthRepositorySQL implements IAuthRepository {

    @Override
    public boolean CheckAuth(String email, String pwd) {
        return false;
    }

    @Override
    public String createAccount(Account a) {
        return null;
    }

    @Override
    public void deleteAccount(String key) {

    }
}
