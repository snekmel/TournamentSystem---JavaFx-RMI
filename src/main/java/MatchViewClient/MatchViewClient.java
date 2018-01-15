package MatchViewClient;

import ConfigClient.ConfigClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchViewClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/Scoreboard.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Scoreboard");
            stage.show();
        }catch (Exception e){
            Logger.getLogger(ConfigClient.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
