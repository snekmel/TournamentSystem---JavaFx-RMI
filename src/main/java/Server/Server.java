package Server;

import Server.models.AuthManager;
import Server.models.TournamentManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server extends Application {

    private int port = 1099;
    private Registry registry;



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.configureRegistry();
        this.configureAuthmanager();
        this.configureTournamentManager();
    }

    public void configureRegistry(){
        try{
            this.registry  = LocateRegistry.createRegistry(port);
        }catch (Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void configureAuthmanager(){
        try{
            AuthManager authManager = new AuthManager();
            this.registry.bind("AuthManager", authManager);

        }catch(Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void configureTournamentManager(){
        try{
            TournamentManager tournamentManager = new TournamentManager();
            this.registry.bind("TournamentManager", tournamentManager);

        }catch(Exception e){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
