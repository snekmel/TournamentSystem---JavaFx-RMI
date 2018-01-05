package ConfigClient.pages;

import Server.models.Match;
import Server.models.Tournament;
import Server.repositorys.RegistryRepository;
import Server.repositorys.TournamentRepository;
import Shared.enums.Status;
import Shared.interfaces.IAuthManager;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import Shared.models.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private Registry registry = RegistryRepository.getRmiRegistry();

    private ITournamentManager iTournamentManager;

    private ObservableList<tournamentTableItem> tournaments = FXCollections.observableArrayList();

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

    private tournamentTableItem selectedItem;


    @FXML
    void deleteBtnClicked(ActionEvent event) {
        System.out.println(this.selectedItem);
       try{

           String id = this.selectedItem.idProperty().getValue()+"";
           this.selectedItem = null;

        this.iTournamentManager.deleteTournament(id);
        this.fillTournamentObservable();
       }catch (Exception e){
           System.out.println(e);
       }

    }

    @FXML
    void createBtnClicked(ActionEvent event) {
        try{
            Tournament newTournament = new Tournament();
            newTournament.setTournamentName(this.tournamentNameTb.getText());
            this.iTournamentManager.addTournament(newTournament);
            this.fillTournamentObservable();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public MainController() {

        try{
            this.iTournamentManager = (ITournamentManager) this.registry.lookup("TournamentManager");
            this.fillTournamentObservable();
        }catch (Exception e){
            System.out.println(e);
        }


    }

    @FXML
    public void initialize() {

        this.idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        this.nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        this.stateColumn.setCellValueFactory(data -> data.getValue().gamestatusProperty());
        this.ownerColumn.setCellValueFactory(data -> data.getValue().ownernameProperty());

        this.tournamentsTableView.setItems(this.tournaments);
        this.tournamentsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)  -> {
           this.selectedTournamentIdLb.setText(newSelection.id.getValue());
            this.selectedTournamentNameLb.setText(newSelection.name.getValue());
            this.selectedItem = newSelection;

        });
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
        private final SimpleObjectProperty<Status> gamestatus = new SimpleObjectProperty<Status>();
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



