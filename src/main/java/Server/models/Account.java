package Server.models;

public class Account {

    private String id;
    private String userName;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    

    public Account(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.id = java.util.UUID.randomUUID().toString();
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'';
    }
}
