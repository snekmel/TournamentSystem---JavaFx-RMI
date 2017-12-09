package ConfigClient.pages.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
        System.out.println(event);
    }

}
