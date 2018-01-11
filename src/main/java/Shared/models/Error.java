package Shared.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Error {

    private Exception exception;

    public Error(Exception e) {
        this.exception = e;
        this.openDialog();
    }

    private void openDialog() {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/Exception.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Oops");
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
