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
import javafx.scene.layout.Pane;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ComboBox<Participant> winnerDropdown;

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

    @FXML
    private Button participantsAddBtn;

    @FXML
    private Button removeParticipantBtn;

    @FXML
    private Label participantNameLbl;

    @FXML
    private Pane tournamentStartPane;

    @FXML
    private Label matchLbl;

    @FXML
    private Label matchStatusLbl;
    @FXML
    private Label roundLbl;

    @FXML
    private Button nextRoundBtn;

    private String tournamentKey;
    private ITournamentManager iTournamentManager;
    private ITournament iTournament;
    private TournamentDetailsCommunicator tournamentDetailsCommunicator;
    private ObservableList<matchTableItem> matches = FXCollections.observableArrayList();
    private ObservableList<Participant> participants  = FXCollections.observableArrayList();
    private Registry registry = RegistryRepository.getRmiRegistry();
    private String selectedMatchKey;


    public String getTournamentKey() {
        return tournamentKey;
    }

    public ITournamentManager getiTournamentManager() {
        return iTournamentManager;
    }

    public TournamentDetailsCommunicator getTournamentDetailsCommunicator() {
        return tournamentDetailsCommunicator;
    }

    public void initTournamentKey(String id) {
        this.tournamentKey = id;
        try{
            this.tournamentDetailsCommunicator = new TournamentDetailsCommunicator(this);
            this.iTournamentManager = (ITournamentManager) registry.lookup("TournamentManager");
            this.iTournament = this.iTournamentManager.getTournament(id);
            this.tournamentNameLbl.setText(this.iTournament.getName());
            this.statusLbl.setText(this.iTournament.getGameStatus().toString());
            this.participants.addAll(this.iTournament.getParticipants());
            this.updateMatchesList(this.iTournament.getMatches());
            this.statusLbl.setText(this.iTournament.getGameStatus().toString());

            if (this.iTournament.getGameStatus() == Status.Active){
                this.tournamentStartPane.setVisible(false);

                this.enableParticipantsCrud(false);
            }
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    public void initialize() {

        this.nextRoundBtn.setVisible(false);

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
    void nextRoundBtnClicked(ActionEvent event) {
        try{
          this.iTournament.nextRound();
            this.roundLbl.setText(this.iTournament.getRoundCount()+"");

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void endMatchClicked(ActionEvent event) {
        try{
            Participant winner = this.winnerDropdown.getSelectionModel().getSelectedItem();
            IMatch iMatch = this.iTournament.getMatch(this.selectedMatchKey);
            iMatch.endMatch(winner);
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void pauseMatchClicked(ActionEvent event) {
        try{
            IMatch iMatch = this.iTournament.getMatch(this.selectedMatchKey);
            if (iMatch.getStatus() == Status.Paused){
                iMatch.resume();
                this.pauseBtn.setText("Pause");
            }else if(iMatch.getStatus() == Status.Active){
                iMatch.pause();
                this.pauseBtn.setText("Start");
            }
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void startMatchClicked(ActionEvent event) {
        try{
            IMatch iMatch = this.iTournament.getMatch(this.selectedMatchKey);
            iMatch.start();

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    void addParticipantBtnClicked(ActionEvent event) {
        Participant participant = new Participant( this.participantName.getText());
        this.participantName.setText("");
        try{
            this.iTournament.addParticipant(participant);
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void removeParticipantBtnClicked(ActionEvent event) {
     Participant p = this.participantsListView.getSelectionModel().getSelectedItem();
     if (p !=null){
         try{
             this.iTournament.removeParticipant(p.getId());
         }catch (Exception e){
             Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
         }
     }

    }

    @FXML
    void selectMatchClicked(ActionEvent event) {
           matchTableItem selectedItem =  this.matchesTableView.getSelectionModel().getSelectedItem();
            this.selectedMatchKey = selectedItem.idProperty().getValue();
            try{
                this.updateScoreBoard(this.iTournament.getMatch(this.selectedMatchKey));
            }catch (Exception e){
                Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
            }

    }

    @FXML
    void Player1Min1(ActionEvent event) {
     try {
         IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
         iMatch.removePointsParticipant1(1);

     }catch (Exception e){
         Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
     }

    }

    @FXML
    void Player1Min2(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.removePointsParticipant1(2);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void Player1Plus1(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.addPointsParticipant1(1);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void Player1Plus2(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.addPointsParticipant1(2);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void Player2Min1(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.removePointsParticipant2(1);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    void Player2Min2(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.removePointsParticipant2(2);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void Player2Plus1(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.addPointsParticipant2(1);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void Player2Plus2(ActionEvent event) {
        try {
            IMatch iMatch = this.iTournament.getMatch(selectedMatchKey);
            iMatch.addPointsParticipant2(2);

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void startTournamentBtnClicked(ActionEvent event) {
     try{
         this.iTournament.startTournament();
         this.tournamentStartPane.setVisible(false);
         this.statusLbl.setText(this.iTournament.getGameStatus().toString());

         this.enableParticipantsCrud(false);
         this.selectedMatchKey = this.iTournament.getMatches().get(0).getId();
         this.updateScoreBoard( this.iTournament.getMatch(this.selectedMatchKey));
     }catch (Exception e){
         Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
     }
    }



    //-----------------------------------------------------------------------------------------View updating methodes


    private void enableParticipantsCrud(Boolean bool){
        this.participantName.setVisible(bool);
        this.participantsAddBtn.setVisible(bool);
        this.removeParticipantBtn.setVisible(bool);
        this.participantNameLbl.setVisible(bool);
    }

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
                   matchTableItem matchTableItem = new matchTableItem(iMatch.getId(), iMatch.getParticipant1().getName(),iMatch.getParticipant2().getName(),iMatch.getStatus());
                   this.matches.add(matchTableItem);
               }
           }catch (Exception e){
               Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
           }

          this.showOrDisableRoundBtn();
        });
    }


    private void showOrDisableRoundBtn(){
        boolean allMatchesFinished = true;
        for (matchTableItem item: this.matches
             ) {
            if(item.statusProperty().getValue() != Status.Finished ){
                allMatchesFinished = false;
            }
        }
     try{
         if(allMatchesFinished && (this.iTournament.getGameStatus() != Status.Finished)){
             this.nextRoundBtn.setVisible(true);
         }else{
             this.nextRoundBtn.setVisible(false);
         }
     }catch (Exception e){
         Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);

     }
    }

    public void updateScoreBoard(IMatch iMatch){
        try{
            this.statusLbl.setText(this.iTournament.getGameStatus().toString());
            this.roundLbl.setText(this.iTournament.getRoundCount()+"");
            this.showOrDisableRoundBtn();
            this.playerName1Tb.setText(iMatch.getParticipant1().getName());
            this.playerName2Tb.setText(iMatch.getParticipant2().getName());
            this.playerPoints1Lbl.setText(iMatch.getPointsParticipant1()+"");
            this.playerPoints2Lbl.setText(iMatch.getPointsParticipant2()+"");
            this.winnerDropdown.getItems().clear();
            this.winnerDropdown.getItems().add(iMatch.getParticipant1());
            this.winnerDropdown.getItems().add(iMatch.getParticipant2());
            int seconds = iMatch.getSeconds();
            String timeString =   LocalTime.MIN.plusSeconds(seconds).toString();
            this.timerLbl.setText(timeString);

            this.matchLbl.setText(iMatch.getParticipant1().getName() + " V.S. " + iMatch.getParticipant2().getName());
            this.matchStatusLbl.setText(iMatch.getStatus().toString());

            if (iMatch.getStatus() == Status.Active){
                this.pauseBtn.setVisible(true);
                this.pauseBtn.setText("Pause");
                this.winnerDropdown.setVisible(true);
                this.endMatchBtn.setVisible(true);
                this.startBtn.setVisible(false);
            }else if(iMatch.getStatus() == Status.NotStarted){
                this.pauseBtn.setVisible(false);
                this.winnerDropdown.setVisible(false);
                this.endMatchBtn.setVisible(false);
                this.startBtn.setVisible(true);
            }else if(iMatch.getStatus() == Status.Finished) {
                this.pauseBtn.setVisible(false);
                this.winnerDropdown.setVisible(false);
                this.endMatchBtn.setVisible(false);
                this.startBtn.setVisible(false);
            }else if(iMatch.getStatus() == Status.Paused) {
                this.pauseBtn.setVisible(true);
                this.pauseBtn.setText("Start");
                this.winnerDropdown.setVisible(true);
                this.endMatchBtn.setVisible(true);
                this.startBtn.setVisible(false);
            }

        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }



    public void updateMatch(IMatch iMatch) {
        matchTableItem newItem;
        try {
            newItem = new matchTableItem(iMatch.getId(), iMatch.getParticipant1().getName(),iMatch.getParticipant2().getName(),iMatch.getStatus());
            Platform.runLater(() -> {
                for (int i = 0; i < this.matches.size() ; i++) {
                    if (this.matches.get(i).idProperty().getValue().equals(newItem.idProperty().getValue())){
                        this.matches.set(i,newItem);
                    }
                }
                if (this.selectedMatchKey.equals(newItem.idProperty().getValue())){
                    this.updateScoreBoard(iMatch);
                }
            });

            this.showOrDisableRoundBtn();
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateMatchTimer(String matchTimeString) {
        //Split in: key|seconds
        String[] matchTimeSplit = matchTimeString.split("\\|");
        String id = matchTimeSplit[0];
        if (id.equals(this.selectedMatchKey)){
            int seconds = Integer.parseInt(matchTimeSplit[1]);
            String timeString =   LocalTime.MIN.plusSeconds(seconds).toString();
            Platform.runLater(() ->{
                this.timerLbl.setText(timeString);
            });
        }
    }

    public void updateTournamentStatus(Status newValue) {
        Platform.runLater(()->{
            this.statusLbl.setText(newValue.toString());
        });
    }

    public void updateRoundCount(int newValue) {
        Platform.runLater(()->{
            this.roundLbl.setText(newValue+"");
        });

    }

    public class matchTableItem{
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
