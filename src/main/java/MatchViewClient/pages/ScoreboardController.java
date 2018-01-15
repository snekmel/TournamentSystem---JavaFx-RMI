package MatchViewClient.pages;

import ConfigClient.pages.TournamentDetailsController;
import MatchViewClient.models.ScoreboardCommunicator;
import Server.repositorys.RegistryRepository;
import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.*;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreboardController {

    @FXML
    private Text playerName1Tb;

    @FXML
    private Label playerPoints1Lbl;

    @FXML
    private Text playerName2Tb;

    @FXML
    private Label playerPoitns2Lbl;

    @FXML
    private Label timerLbl;

    @FXML
    private Pane formPane;

    @FXML
    private ListView<String> tournamentsListView;

    @FXML
    private ListView<String> matchesListview;

    private Registry registry = RegistryRepository.getRmiRegistry();
    private ITournamentManager iTournamentManager;
    private ITournament iTournament;
    private IMatch iMatch;
    private boolean formOpen = true;
    private ObservableList<ITournament> tournaments = FXCollections.observableArrayList();
    private ObservableList<IMatch> matches = FXCollections.observableArrayList();
    private ScoreboardCommunicator scoreboardCommunicator;
    private String selectedMatchKey;

    @FXML
    void openMatchBtnClicked(ActionEvent event) {
        this.iMatch = this.matches.get(this.matchesListview.getSelectionModel().getSelectedIndex());
    try {
        this.selectedMatchKey = this.iMatch.getId();
    }catch (Exception e){
        System.out.println(e);
    }
        this.updateScoreBoard(iMatch);
      this.openSettingsClicked();

    }

    @FXML
    void openSettingsClicked() {
if (formOpen){
    this.formPane.setVisible(false);
    this.formOpen = false;
}else{
    this.formPane.setVisible(true);
    this.formOpen = true;
}
    }


    @FXML
    private void initialize(){
    this.registry = RegistryRepository.getRmiRegistry();
    try{

        this.iTournamentManager = (ITournamentManager) registry.lookup("TournamentManager");
        for (ITournament tournament: this.iTournamentManager.getTournaments()
             ) {
            this.tournaments.add(tournament);
            this.tournamentsListView.getItems().add(tournament.getName());
        }


    }catch (Exception e){
        Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);

    }


        this.tournamentsListView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
               clearMatches();
                iTournament = tournaments.get(tournamentsListView.getSelectionModel().getSelectedIndex());
                System.out.println(iTournament);
                try {

                    for (IMatch iMatch: iTournament.getMatches()
                            ) {
                        matches.add(iMatch);
                        matchesListview.getItems().add(iMatch.getParticipant1().getName() + " V.S " + iMatch.getParticipant2().getName());
                    }
                initCommunicator();
                }catch (Exception e){
                    Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);

                }
            }
        });


    }

    public void clearMatches(){
        this.matchesListview.getItems().clear();

    }

    public void initCommunicator(){
        try {
            this.scoreboardCommunicator = new ScoreboardCommunicator(this);
        }catch (Exception e){
            Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void updateScoreBoard(IMatch iMatch){
        try{
            this.playerName1Tb.setText(iMatch.getParticipant1().getName());
            this.playerName2Tb.setText(iMatch.getParticipant2().getName());
            this.playerPoints1Lbl.setText(iMatch.getPointsParticipant1()+"");

            this.playerPoitns2Lbl.setText(iMatch.getPointsParticipant2()+"");

            int seconds = iMatch.getSeconds();
            String timeString =   LocalTime.MIN.plusSeconds(seconds).toString();
            this.timerLbl.setText(timeString);


        }catch (Exception e){
            Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public String getSelectedTournamentKey(){
        try{
            return this.iTournament.getId();
        }catch (Exception e){
            Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }


    public void updateMatchesList(List<IMatch> newMatches){
     Platform.runLater(new Runnable() {
         @Override
         public void run() {
             matches.clear();
             matches = FXCollections.observableArrayList(newMatches);
         }
     });
    }

    public void updateMatch(IMatch newiMatch){


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    for (int i = 0; i <matches.size() ; i++) {
                        if (matches.get(i).getId().equals(newiMatch.getId())){
                            matches.set(i,newiMatch);
                        } }
                    if (iMatch.getId().equals(newiMatch.getId())) {
                        updateScoreBoard(iMatch);
                    }
                }catch (Exception e){
                    Logger.getLogger(ScoreboardController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });



    }

    public void updateMatchTimer(String idSeconds){
//Split in: key|seconds
        String[] matchTimeSplit = idSeconds.split("\\|");
        String id = matchTimeSplit[0];
        if (id.equals(this.selectedMatchKey)){
            int seconds = Integer.parseInt(matchTimeSplit[1]);
            String timeString =   LocalTime.MIN.plusSeconds(seconds).toString();
            Platform.runLater(() ->{
                this.timerLbl.setText(timeString);
            });
        }
    }


}
