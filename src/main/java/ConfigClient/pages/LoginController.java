package ConfigClient.pages;
import Server.repositorys.RegistryRepository;
import Shared.interfaces.IAuthManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private Registry registry = RegistryRepository.getRmiRegistry();

    private IAuthManager iAuthManager;

    @FXML
    private TextField usernameTb;

    @FXML
    private PasswordField passwordTb;

    @FXML
    private GridPane loginGrid;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;


    public LoginController() {
     try{
         this.iAuthManager = (IAuthManager) this.registry.lookup("AuthManager");

     }catch (Exception e){

         Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
     }
    }


    @FXML
    void loginBtnClicked(ActionEvent event)
    {
        try{
            if (this.usernameTb.getText() != "" && this.passwordTb.getText() !=""){
                if (this.iAuthManager.checkAuth(usernameTb.getText(), passwordTb.getText()) != null){
                    this.openDashboard();
                }
            }
        }catch (Exception e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    @FXML
    void registerBtnClicked(ActionEvent event) {
        this.openRegisterPage();
    }


    private void openDashboard(){
        try{
            Stage activeStage = (Stage) loginBtn.getScene().getWindow();
            activeStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/Main.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        }catch (Exception e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void openRegisterPage(){
        try{
            Stage activeStage = (Stage) loginBtn.getScene().getWindow();
            activeStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/Register.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        }catch (Exception e){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
