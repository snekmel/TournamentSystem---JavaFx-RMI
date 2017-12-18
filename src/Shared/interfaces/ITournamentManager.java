package Shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITournamentManager extends Remote {
    List<ITournament> getTournaments() throws RemoteException;
    String addTournament() throws RemoteException;
    ITournament getTournament(String id) throws RemoteException;
}
