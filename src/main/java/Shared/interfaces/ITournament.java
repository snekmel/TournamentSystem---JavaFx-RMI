package Shared.interfaces;

import Shared.enums.Status;
import Shared.models.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface ITournament extends Remote {

    void addParticipant(Participant p) throws RemoteException;
    List<Participant> getParticipants() throws RemoteException;
    void removeParticipant(String id) throws  RemoteException;
    List<IMatch> getMatches() throws RemoteException;
    IMatch getMatch(String id) throws RemoteException;
    void startTournament() throws RemoteException;
    void finishTournament() throws RemoteException;
    String getId() throws RemoteException;
    Status getGameStatus() throws RemoteException;
    Date getStartDate() throws RemoteException;
    String getName() throws RemoteException;
    String getOwnername() throws RemoteException;
    void nextRound() throws RemoteException;
    int getRoundCount() throws RemoteException;

}
