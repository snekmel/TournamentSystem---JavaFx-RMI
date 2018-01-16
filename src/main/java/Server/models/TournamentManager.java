package Server.models;

import Server.repositorys.RegistryRepository;
import Server.repositorys.TournamentRepository;
import Server.repositorys.context.TournamentRepositorySQL;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import publisher.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TournamentManager extends UnicastRemoteObject implements ITournamentManager {

    private List<Tournament> Tournaments;
    private TournamentRepository tournamentRepository = new TournamentRepository(new TournamentRepositorySQL());
    private transient Registry registry = RegistryRepository.getRmiRegistry();

    public TournamentManager() throws RemoteException {
        this.Tournaments = tournamentRepository.getTournaments();

            try{
           for (Tournament tournament: this.Tournaments
                   ) {
               RemotePublisher remotePublisher = new RemotePublisher();
               remotePublisher.registerProperty("Participants");
               remotePublisher.registerProperty("Matches");
               remotePublisher.registerProperty("Match");
               remotePublisher.registerProperty("Match-Time");
               remotePublisher.registerProperty("Status");
               remotePublisher.registerProperty("RoundCount");
               this.registry.bind(tournament.getId(),remotePublisher);
           }
       }catch (Exception e){
          Logger.getLogger(TournamentManager.class.getName()).log(Level.SEVERE, null, e);
       }
    }

    @Override
    public List<ITournament> getTournaments() throws RemoteException {
        List<ITournament> returnList = new ArrayList<>();
        for (Tournament t:this.Tournaments
             ) {
            returnList.add((ITournament)t);
        }
        return returnList;

    }

    @Override
    public String addTournament(String tournamentName, String tournamentOwner) throws RemoteException {

        Tournament t = new Tournament();
        t.setTournamentName(tournamentName);
        t.setOwnerName(tournamentOwner);
        this.Tournaments.add(t);
        this.tournamentRepository.createTournament(t);
        try{
                RemotePublisher remotePublisher = new RemotePublisher();
                remotePublisher.registerProperty("Participants");
                remotePublisher.registerProperty("Matches");
                remotePublisher.registerProperty("Match");
                remotePublisher.registerProperty("Match-Time");
                remotePublisher.registerProperty("Status");
            remotePublisher.registerProperty("RoundCount");
                this.registry.bind(t.getId(),remotePublisher);

        }catch (Exception e){
           Logger.getLogger(TournamentManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return t.getId();
    }





    @Override
    public ITournament getTournament(String id) throws RemoteException {
        for (Tournament tournament: this.Tournaments
             ) {
            if (tournament.getId().equals(id)){
                return (ITournament)tournament;
            }
        }
        return null;
    }

    @Override
    public boolean deleteTournament(String id) throws RemoteException {
      try{
       for (Tournament t: this.Tournaments){
           if (t.getId().equals(id)){
               this.Tournaments.remove(t);
               this.tournamentRepository.deleteTournament(t.getId());
               return true;
           }
       }
      }catch (Exception e){
         Logger.getLogger(TournamentManager.class.getName()).log(Level.SEVERE, null, e);
      }
          return false;
    }
}
