package Shared.interfaces;

import Server.models.Tournament;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITournamentManager extends Remote {
    List<ITournament> getTournaments() throws RemoteException;
    String addTournament(String tournamentName, String tournamentOwner) throws RemoteException;
    ITournament getTournament(String id) throws RemoteException;
    boolean deleteTournament(String id) throws RemoteException;
}
