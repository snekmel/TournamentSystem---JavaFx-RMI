package Server.repositorys;

import Server.models.Account;
import Server.repositorys.interfaces.IAuthRepository;

import java.util.List;

public class AuthRepository {

    private IAuthRepository iAuthRepository;

    public AuthRepository(IAuthRepository iAuthRepository) {
        this.iAuthRepository = iAuthRepository;
    }


    public boolean checkAuth(String username, String pwd){
        return this.iAuthRepository.CheckAuth(username, pwd);
    }

    public String createAccount(Account a){
        return  this.iAuthRepository.createAccount(a);
    }


    public void deleteAccount(String key){
        this.iAuthRepository.deleteAccount(key);
    }

    public List<Account> getAccounts(){
        return this.iAuthRepository.getAccounts();

    }

}
