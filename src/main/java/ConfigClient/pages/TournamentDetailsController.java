package ConfigClient.pages;

import ConfigClient.models.TournamentDetailsCommunicator;
import Server.repositorys.RegistryRepository;
import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import Shared.models.Participant;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.rmi.registry.Registry;
import java.util.List;

public class TournamentDetailsController {

    @FXML
    private TableView<matchTableItem> matchesTableView;

    @FXML
    private TableColumn<matchTableItem, String> idColumn;

    @FXML
    private TableColumn<matchTableItem, String> player1Column;

    @FXML
    private TableColumn<matchTableItem, String> player2Column;

    @FXML
    private TableColumn<matchTableItem, Status> stateColumn;

    @FXML
    private Label playerName1Tb;

    @FXML
    private Button Plus1Btn1;

    @FXML
    private Button Minus2Btn1;

    @FXML
    private Button Minus1Btn1;

    @FXML
    private Button Plus2Btn1;

    @FXML
    private Label playerPoints1Lbl;

    @FXML
    private Label playerName2Tb;

    @FXML
    private Label playerPoints2Lbl;

    @FXML
    private Button Plus1Btn2;

    @FXML
    private Button Minus2Btn2;

    @FXML
    private Button Minus1Btn2;

    @FXML
    private Button Plus2Btn2;

    @FXML
    private Label timerLbl;

    @FXML
    private ComboBox<?> winnerDropdown;

    @FXML
    private Button endMatchBtn;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Label tournamentNameLbl;

    @FXML
    private Label statusLbl;

    @FXML
    private ListView<Participant> participantsListView;

    @FXML
    private TextField participantName;


    private String tournamentKey;
    private ITournamentManager iTournamentManager;
    private ITournament iTournament;
    private TournamentDetailsCommunicator tournamentDetailsCommunicator;
    private ObservableList<matchTableItem> matches = FXCollections.observableArrayList();
    private ObservableList<Participant> participants  = FXCollections.observableArrayList();
    private Registry registry = RegistryRepository.getRmiRegistry();



    public void initTournamentKey(String id) {
        this.tournamentKey = id;
        try{
            this.tournamentDetailsCommunicator = new TournamentDetailsCommunicator(this);
            this.iTournamentManager = (ITournamentManager) registry.lookup("TournamentManager");
            this.iTournament = this.iTournamentManager.getTournament(id);
            this.tournamentNameLbl.setText(this.iTournament.getName());
            this.statusLbl.setText(this.iTournament.getGameStatus().toString());
            this.participants.addAll(this.iTournament.getParticipants());
        }catch (Exception e){
            System.out.println(e);
        }


    }


    @FXML
    public void initialize() {
        //Tablie view collumns
        this.idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        this.player1Column.setCellValueFactory(data -> data.getValue().name1Property());
        this.player2Column.setCellValueFactory(data -> data.getValue().name2Property());
        this.stateColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        //Set observable lists
        this.matchesTableView.setItems(this.matches);
        this.participantsListView.setItems(this.participants);

    }

    @FXML
    void addParticipantBtnClicked(ActionEvent event) {
        Participant participant = new Participant( this.participantName.getText());
        this.participantName.setText("");
        try{
            this.iTournament.addParticipant(participant);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void removeParticipantBtnClicked(ActionEvent event) {
     Participant p = this.participantsListView.getSelectionModel().getSelectedItem();
     if (p !=null){
         try{
             this.iTournament.removeParticipant(p.getId());
         }catch (Exception e){
             System.out.println(e);
         }
     }

    }

    @FXML
    void selectMatchClicked(ActionEvent event) {
        System.out.println("selected");
    }

    @FXML
    void startTournamentBtnClicked(ActionEvent event) {
     try{
         this.iTournament.startTournament();
     }catch (Exception e){
         System.out.println(e);
     }
    }


    public String getTournamentKey() {
        return tournamentKey;
    }

    public ITournamentManager getiTournamentManager() {
        return iTournamentManager;
    }

    public TournamentDetailsCommunicator getTournamentDetailsCommunicator() {
        return tournamentDetailsCommunicator;
    }


    //-----------------------------------------------------------------------------------------View updating methodes
    public  void updateParticipantsList(List<Participant> participantList){
        Platform.runLater(() -> {
            this.participants.clear();
            this.participants.addAll(participantList);
        });
    }

    public void updateMatchesList(List<IMatch> matchList){
        Platform.runLater(() -> {
            this.matches.clear();
            this.matchesTableView.getItems().clear();
           try{
               for (IMatch iMatch: matchList
                       ) {
                   System.out.println("Match" +iMatch.getId());
                   matchTableItem matchTableItem = new matchTableItem(iMatch.getId(), iMatch.getParticipant1().getName(),iMatch.getParticipant2().getName(),iMatch.getStatus());
                   this.matches.add(matchTableItem);
               }
           }catch (Exception e){
               System.out.println(e);
           }
        });
    }

    private class matchTableItem{

        private SimpleStringProperty id;
        private SimpleStringProperty name1;
        private SimpleStringProperty name2;
        private SimpleObjectProperty<Status> status;



        public SimpleStringProperty idProperty() {
            return id;
        }

        public SimpleStringProperty name1Property() {
            return name1;
        }

        public SimpleStringProperty name2Property() {
            return name2;
        }

        public SimpleObjectProperty<Status> statusProperty() {
            return status;
        }

        public matchTableItem(String id, String name1, String name2, Status status) {

            this.id = new SimpleStringProperty(id);
            this.name1 = new SimpleStringProperty(name1);
            this.name2 = new SimpleStringProperty(name2);
            this.status = new SimpleObjectProperty<>(status);



        }
    }
}
