package ConfigClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    this.showLoginPage();
    }


    private void showLoginPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        }catch (Exception e){
            Logger.getLogger(ConfigClient.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
