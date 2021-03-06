package Server.models;

import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class AuthManagerTest {
    @Test
    public void registerAccount() throws RemoteException {
        AuthManager authManager = new AuthManager();
        authManager.registerAccount("test","test");
        assertTrue(authManager.checkAuth("test", "test") !=null);

    }

    @Test
    public void deleteAccount() throws RemoteException {
        AuthManager authManager = new AuthManager();
        authManager.deleteAccount("6f9f19eb-d3ff-4f00-873e-2bd050bcbbc8");
    }
}