package Server.models;

import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import Shared.models.Participant;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament extends UnicastRemoteObject implements ITournament {

    private String id;
    private Status gameStatus;
    private Date startDate;
    private String tournamentName;
    private String ownerName;
    private List<Participant> participants;
    private List<Match> matches;


    public Tournament() throws RemoteException {
        this.id = java.util.UUID.randomUUID().toString();
        this.startDate = new Date();
        this.participants = new ArrayList<Participant>();
        this.matches = new ArrayList<Match>();
        this.tournamentName = this.id;
        this.gameStatus = Status.NotStarted;
    }


    public Tournament(String id, Status gameStatus, Date startDate, String tournamentName, String ownerName) throws RemoteException {
        this.id = id;
        this.gameStatus = gameStatus;
        this.startDate = startDate;
        this.tournamentName = tournamentName;
        this.ownerName = ownerName;
    }


    public void setGameStatus(Status gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public void addParticipant(Participant p) throws RemoteException {

    }

    public List<Participant> getParticipants() throws RemoteException {
        return null;
    }

    public void removeParticipant(String id) throws RemoteException {

    }

    public List<IMatch> getMatches() throws RemoteException {
        return null;
    }

    public IMatch getMatch(String id) throws RemoteException {
        return null;
    }

    public void startTournament() throws RemoteException {

    }

    public void finishTournament() throws RemoteException {

    }

    public String getId() throws RemoteException {
        return  this.id;
    }

    public Status getGameStatus() throws RemoteException {
        return this.gameStatus;
    }

    public Date getStartDate() throws RemoteException {
       return this.startDate;
    }

    public String getName() throws RemoteException {
       return this.tournamentName;
    }

    public String getOwnername() throws RemoteException {
        return this.ownerName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
