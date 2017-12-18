package Server.models;

public class Account {

    private String id;
    private String userName;
    private String password;

    public Account(String userName, String password) {
        this.userName = userName;
        this.id = java.util.UUID.randomUUID().toString();
        this.password = password;
    }
}
