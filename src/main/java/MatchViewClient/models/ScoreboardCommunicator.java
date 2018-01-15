package MatchViewClient.models;

import ConfigClient.models.TournamentDetailsCommunicator;
import ConfigClient.pages.TournamentDetailsController;
import MatchViewClient.pages.ScoreboardController;
import Server.repositorys.RegistryRepository;
import Shared.interfaces.IMatch;
import Shared.models.Participant;
import publisher.IRemotePropertyListener;
import publisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreboardCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {

    private ScoreboardController scoreboardController;
    private IRemotePublisherForListener publisher;
    private transient Registry registry = RegistryRepository.getRmiRegistry();

    public ScoreboardCommunicator(ScoreboardController scoreboardController) throws RemoteException {
        this.scoreboardController = scoreboardController;
        try{
            System.out.println(scoreboardController.getSelectedTournamentKey());
            this.publisher = (IRemotePublisherForListener)this.registry.lookup(scoreboardController.getSelectedTournamentKey());
            this.subscribe("Matches");
            this.subscribe("Match");
            this.subscribe("Match-Time");
        }catch (Exception e){
            Logger.getLogger(ScoreboardCommunicator.class.getName()).log(Level.SEVERE, null, e);

        }

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "Matches":
                this.scoreboardController.updateMatchesList((List<IMatch>) evt.getNewValue());
                break;
            case "Match":
                this.scoreboardController.updateMatch((IMatch) evt.getNewValue());
                break;
            case "Match-Time":
                System.out.println("Got new time");
                this.scoreboardController.updateMatchTimer((String)evt.getNewValue());
                break;
        }

    }

    public void subscribe(String property){
        try {
            publisher.subscribeRemoteListener(this, property);
        } catch (RemoteException ex) {
            Logger.getLogger(ScoreboardCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
