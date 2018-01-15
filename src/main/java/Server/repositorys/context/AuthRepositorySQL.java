package Server.repositorys.context;

import Server.Server;
import Server.models.Account;
import Server.repositorys.interfaces.IAuthRepository;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthRepositorySQL implements IAuthRepository {

    Properties properties = new Properties();


    public AuthRepositorySQL() {
        try{

            FileInputStream propFile = new FileInputStream("src/main/java/Server/config/db.prop");
            properties.load(propFile);

        }catch (Exception e){
            Logger.getLogger(AuthRepositorySQL.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    public boolean CheckAuth(String userName, String pwd) {
      boolean result = false;

        for (Account account: this.getAccounts()
             ) {

            if (account.getUserName().equals(userName) && account.getPassword().equals(pwd)){
                result = true;
            }
        }

        return result;

    }

    public String createAccount(Account a) {
        try
        {
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));
            PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO `account`(Id,Username,password) VALUES (?, ?, ?)");

            insertStatement.setString(1,a.getId());
            insertStatement.setString(2,a.getUserName());
            insertStatement.setString(3,a.getPassword());

            insertStatement.executeUpdate();

        }
        catch (Exception e)
        {
            Logger.getLogger(AuthRepositorySQL.class.getName()).log(Level.SEVERE, null, e);}
            finally {
        }

        return a.getId();

    }

    public void deleteAccount(String key) {
        try
        {
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));
            PreparedStatement insertStatement = conn.prepareStatement("DELETE FROM `killerapp`.`account` WHERE `Id`=?;");
            insertStatement.setString(1,key);
            insertStatement.executeUpdate();
            conn.close();
        }
        catch (Exception e)
        {
            Logger.getLogger(AuthRepositorySQL.class.getName()).log(Level.SEVERE, null, e);
        }



    }


    public List<Account> getAccounts() {
        List<Account> returnList = new ArrayList<Account>();

        try
        {
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));
            String query = "SELECT * FROM account";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                Account newAccount = new Account(rs.getString("Id"),rs.getString("Username"),rs.getString("password"));
                returnList.add(newAccount);
            }
            st.close();
            conn.close();
        }
        catch (Exception e)
        {
            Logger.getLogger(AuthRepositorySQL.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnList;

    }
}
