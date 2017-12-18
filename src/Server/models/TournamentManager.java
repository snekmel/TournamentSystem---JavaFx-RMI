package Server.models;

import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TournamentManager extends UnicastRemoteObject implements ITournamentManager {

    private List<Tournament> Tournaments;

    protected TournamentManager() throws RemoteException {
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
