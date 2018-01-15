package ConfigClient.pages;


import Server.repositorys.RegistryRepository;
import Shared.enums.Status;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private Registry registry = RegistryRepository.getRmiRegistry();

    private ITournamentManager iTournamentManager;

    private ObservableList<tournamentTableItem> tournaments = FXCollections.observableArrayList();

    @FXML
    private TextField ownerTb;

    @FXML
    private TableColumn<tournamentTableItem, String> idColumn;

    @FXML
    private TableColumn<tournamentTableItem, String> nameColumn;

    @FXML
    private TableColumn<tournamentTableItem, Status> stateColumn;

    @FXML
    private TableColumn<tournamentTableItem, String> ownerColumn ;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableView<tournamentTableItem> tournamentsTableView;

    @FXML
    private Button openBtn;

    @FXML
    private Button newBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField tournamentNameTb;

    @FXML
    private GridPane mainGrid;

    @FXML
    private Label selectedTournamentNameLb;

    @FXML
    private Label selectedTournamentIdLb;

    @FXML
    private Pane deletePane;

    @FXML
    private ListView<?> participantsListView;

    @FXML
    private TextField participantName;

    private tournamentTableItem selectedItem;


    @FXML
    void openBtnClicked(ActionEvent event) {
        try{
            String id = this.selectedItem.idProperty().getValue()+"";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/TournamentDetails.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            TournamentDetailsController controller = (TournamentDetailsController) fxmlLoader.getController();
            controller.initTournamentKey(id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Lobby");
            stage.show();

        }catch (Exception e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    @FXML
    void deleteBtnClicked(ActionEvent event) {
       try{

           String id = this.selectedItem.idProperty().getValue()+"";
           this.selectedItem = null;
           this.deletePane.setVisible(false);
        this.iTournamentManager.deleteTournament(id);
        this.fillTournamentObservable();
       }catch (Exception e){
           Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);       }

    }

    @FXML
    void createBtnClicked(ActionEvent event) {
        try{
            this.iTournamentManager.addTournament(this.tournamentNameTb.getText(), this.ownerTb.getText());
            this.fillTournamentObservable();
            this.clearCreatePane();
        }catch (Exception e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);        }
    }


    public MainController() {

        try{
            this.iTournamentManager = (ITournamentManager) this.registry.lookup("TournamentManager");
            this.fillTournamentObservable();
        }catch (Exception e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);        }


    }

    @FXML
    public void initialize() {

        this.idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        this.nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        this.stateColumn.setCellValueFactory(data -> data.getValue().gamestatusProperty());
        this.ownerColumn.setCellValueFactory(data -> data.getValue().ownernameProperty());

        this.deletePane.setVisible(false);



        this.tournamentsTableView.setItems(this.tournaments);
        this.tournamentsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)  -> {
           this.selectedTournamentIdLb.setText(newSelection.id.getValue());
            this.selectedTournamentNameLb.setText(newSelection.name.getValue());
            this.selectedItem = newSelection;

            this.deletePane.setVisible(true);


        });

        this.logoutBtn.setOnAction(data->{
            try{
                Stage activeStage = (Stage) logoutBtn.getScene().getWindow();
                activeStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pages/LoginPage.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.show();
            }catch (Exception e){
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private void clearCreatePane(){
        this.ownerTb.setText("");
        this.tournamentNameTb.setText("");
    }

    private void fillTournamentObservable() {
        try {
            this.tournaments.clear();
            if(iTournamentManager.getTournaments() != null){
                for (ITournament iTournament : iTournamentManager.getTournaments()){
                    tournamentTableItem tournament = new tournamentTableItem(iTournament.getId(),iTournament.getGameStatus(),iTournament.getName(),iTournament.getOwnername());
                    this.tournaments.add(tournament);
                }
            }

        }catch (Exception e){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public class tournamentTableItem{

        private final SimpleStringProperty id = new SimpleStringProperty();
        private final SimpleObjectProperty<Status> gamestatus = new SimpleObjectProperty<>();
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleStringProperty ownername = new SimpleStringProperty();



        public SimpleStringProperty idProperty() {
            return id;
        }

        public SimpleObjectProperty<Status> gamestatusProperty() {
            return gamestatus;
        }


        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleStringProperty ownernameProperty() {
            return ownername;
        }

        public tournamentTableItem(String id, Status status,String name, String ownerName) {
            this.id.set(id);
            this.gamestatus.set(status);
            this.name.set(name);
            this.ownername.set(ownerName);

        }

        @Override
        public String toString() {
            return "tournamentTableItem{" +
                    "id=" + id +
                    ", gamestatus=" + gamestatus +
                    ", name=" + name +
                    ", ownername=" + ownername +
                    '}';
        }
    }
}



