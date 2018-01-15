package ConfigClient.pages;

        import Server.repositorys.RegistryRepository;
        import Shared.interfaces.IAuthManager;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;

        import java.rmi.registry.Registry;
        import java.util.logging.Level;
        import java.util.logging.Logger;

public class RegisterController {


    private Registry registry = RegistryRepository.getRmiRegistry();

    private IAuthManager iAuthManager;

    @FXML
    private GridPane loginGrid;

    @FXML
    private TextField usernameTb;

    @FXML
    private TextField passwordTb;

    @FXML
    private Button registerBtn;

    @FXML
    private Button goToLoginBtn;


    public RegisterController() {
        try{
            this.iAuthManager = (IAuthManager) this.registry.lookup("AuthManager");
        }catch (Exception e){

            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void goToLoginBtnClicked(ActionEvent event) {
        try{
            Stage activeStage = (Stage) goToLoginBtn.getScene().getWindow();
            activeStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pages/LoginPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        }catch (Exception e){
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void registerBtnClicked(ActionEvent event) {
        if (this.usernameTb.getText() != null && this.passwordTb.getText() !=null){
     try{
         this.iAuthManager.registerAccount(this.usernameTb.getText(),this.passwordTb.getText());
     }catch (Exception e){
         Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
     }
        }
    }

}
