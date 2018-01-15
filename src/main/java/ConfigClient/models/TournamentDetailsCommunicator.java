package ConfigClient.models;

import ConfigClient.pages.TournamentDetailsController;
import Server.repositorys.RegistryRepository;
import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.models.Participant;
import publisher.IRemotePropertyListener;
import publisher.IRemotePublisherForListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TournamentDetailsCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {

    private TournamentDetailsController tournamentDetailsController;
    private IRemotePublisherForListener publisher;
    private transient Registry registry = RegistryRepository.getRmiRegistry();

    public TournamentDetailsCommunicator(TournamentDetailsController tournamentDetailsController) throws RemoteException {
        this.tournamentDetailsController = tournamentDetailsController;
        try{
             this.publisher = (IRemotePublisherForListener)this.registry.lookup(tournamentDetailsController.getTournamentKey());
              this.subscribe("Participants");
              this.subscribe("Matches");
              this.subscribe("Match");
              this.subscribe("Match-Time");
              this.subscribe("Status");
              this.subscribe("RoundCount");
        }catch (Exception e){
            Logger.getLogger(TournamentDetailsCommunicator.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        String propertyName = evt.getPropertyName();
       switch (propertyName) {
           case "Participants":
               this.tournamentDetailsController.updateParticipantsList((List<Participant>) evt.getNewValue());
               break;
           case "Matches":
                this.tournamentDetailsController.updateMatchesList((List<IMatch>) evt.getNewValue());
               break;
           case "Match":
               this.tournamentDetailsController.updateMatch((IMatch) evt.getNewValue());
               break;
           case "Match-Time":
               this.tournamentDetailsController.updateMatchTimer((String)evt.getNewValue());
               break;
           case "Status":
               this.tournamentDetailsController.updateTournamentStatus((Status)evt.getNewValue());
               break;
           case "RoundCount":
               this.tournamentDetailsController.updateRoundCount((int)evt.getNewValue());
               break;
           default:
               break;
        }
    }

    public void subscribe(String property) {
        try {
            publisher.subscribeRemoteListener(this, property);
        } catch (RemoteException ex) {
            Logger.getLogger(TournamentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
