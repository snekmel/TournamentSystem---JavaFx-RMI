package Server.models;

import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TournamentManager extends UnicastRemoteObject implements ITournamentManager {

    private List<Tournament> Tournaments;

    public TournamentManager() throws RemoteException {

        this.Tournaments = new ArrayList<Tournament>();

        Tournament t1 = new Tournament();
        Tournament t2 = new Tournament();

        this.Tournaments.add(t1);
        this.Tournaments.add(t2);
    }



    @Override
    public List<ITournament> getTournaments() throws RemoteException {
        List<ITournament> returnList = new ArrayList<ITournament>();
        for (Tournament t:this.Tournaments
             ) {
            returnList.add((ITournament)t);
        }
        return returnList;

    }

    @Override
    public String addTournament(Tournament t) throws RemoteException {
    this.Tournaments.add(t);

        System.out.println(this.Tournaments);
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
               return true;
           }
       }
      }catch (Exception e){
          System.out.println(e);
      }finally {
          return false;
      }
    }
}
