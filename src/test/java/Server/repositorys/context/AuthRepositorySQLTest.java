package Server.repositorys.context;

import Server.models.Account;
import Server.repositorys.AuthRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthRepositorySQLTest {

    private AuthRepositorySQL authRepositorySQL = new AuthRepositorySQL();

    @Test
    public void checkAuth() throws Exception {
        Assert.assertTrue(authRepositorySQL.CheckAuth("lars","123"));
    }

    @Test
    public void createAccount() throws Exception {
      Account account = new Account("testUser", "testUser");
      int Size =   authRepositorySQL.getAccounts().size();
      authRepositorySQL.createAccount(account);
      AuthRepositorySQL ar = new AuthRepositorySQL();
      int Size2 = ar.getAccounts().size();
      Assert.assertTrue(Size > Size2);

    }

    @Test
    public void deleteAccount() throws Exception {
        authRepositorySQL.deleteAccount("7d5711f4-cf76-48b7-a0cd-928a66d07ffb");
    }

    @Test
    public void getAccounts() throws Exception {
        Assert.assertTrue(authRepositorySQL.getAccounts().size() >= 2);
    }

}