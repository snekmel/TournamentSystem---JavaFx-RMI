package ConfigClient.pages.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameTb;

    @FXML
    private TextField passwordTb;

    @FXML
    private GridPane loginGrid;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    void loginBtnClicked(ActionEvent event) {
        System.out.println(usernameTb.getText() + passwordTb.getText());
    }

    @FXML
    void registerBtnClicked(ActionEvent event) {

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Register/Register.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            System.out.println("2");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }




    }

}
