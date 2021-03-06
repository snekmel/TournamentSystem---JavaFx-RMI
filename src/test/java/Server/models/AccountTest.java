package Server.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void getId() {
        Account testAccount = new Account("1", "Lars", "test");
        assertTrue(testAccount.getId().equals("1"));

    }

    @Test
    public void setId()  {
        Account testAccount = new Account("1", "Lars", "test");
        testAccount.setId("2");
        assertTrue(testAccount.getId().equals("2"));
    }

    @Test
    public void getUserName()  {
        Account testAccount = new Account("1", "Lars", "test");
        assertTrue(testAccount.getUserName().equals("Lars"));

    }

    @Test
    public void setUserName()  {
        Account testAccount = new Account("1", "Lars", "test");
        testAccount.setUserName("Test");
        assertTrue(testAccount.getUserName().equals("Test"));
    }

    @Test
    public void getPassword()  {
        Account testAccount = new Account("1", "Lars", "test");
        assertTrue(testAccount.getPassword().equals("test"));
    }

}