package Server.models;

import Shared.enums.Status;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import Shared.models.Participant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class Tournament extends UnicastRemoteObject implements ITournamentManager  {

    private String id;
    private Status gameStatus;
    private Date startDate;
    private String ownerName;
    private List<Participant> participants;
    private List<Match> matches;


    protected Tournament() throws RemoteException {
        this.id = java.util.UUID.randomUUID().toString();;
        this.startDate = new Date();
    }

    @Override
    public List<ITournament> getTournaments() throws RemoteException {
        return null;
    }

    @Override
    public String addTournament() throws RemoteException {
        return null;
    }

    @Override
    public ITournament getTournament(String id) throws RemoteException {
        return null;
    }
}
