package Server;

import Server.models.AuthManager;
import Server.models.TournamentManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.Registry;


public class Server extends Application {

    private String hostIp;
    private int port;
    private Registry registry;
    private AuthManager authManager;
    private TournamentManager tournamentManager;



    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void configureRegistry(){

    }

    public void configureAuthmanager(){

    }

    public void configureTournamentManager(){

    }
}
