package Server.repositorys.context;

import Server.Server;
import Server.models.Tournament;
import Server.repositorys.interfaces.ITournamentRepository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TournamentRepositorySQL implements ITournamentRepository{

    Properties properties = new Properties();

    public TournamentRepositorySQL( ) {
        try{

            FileInputStream propFile = new FileInputStream("src/main/java/Server/config/db.prop");
            properties.load(propFile);

        }catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public String createTournament(Tournament t) {
        try
        {
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            byte[] tournamentToBytes = baos.toByteArray();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tournament (Id,SerializedTournament) VALUES(?,?)");
            ByteArrayInputStream bais = new ByteArrayInputStream(tournamentToBytes);
            pstmt.setString(1,t.getId());
            pstmt.setBinaryStream(2, bais, tournamentToBytes.length);
            pstmt.executeUpdate();
            pstmt.close();

            return t.getId();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public void deleteTournament(String key) {
        try{
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));
            PreparedStatement insertStatement = conn.prepareStatement("DELETE FROM Tournament WHERE `Id`=?;");
            insertStatement.setString(1,key);
            insertStatement.executeUpdate();
            conn.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public List<Tournament> getTournaments() {
        List<Tournament> returnList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(this.properties.getProperty("connection"), this.properties.getProperty("user"), this.properties.getProperty("password"));

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tournament;");
            while (rs.next()) {
                byte[] st = (byte[]) rs.getObject(2);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                returnList.add((Tournament) ois.readObject());
            }
            stmt.close();
            rs.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return  returnList;

    }

    @Override
    public void updateTournament(Tournament t) {
        try{
            this.deleteTournament(t.getId());
            this.createTournament(t);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
