package Server.models;

import Shared.interfaces.ITournament;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.Assert.*;

public class TournamentManagerTest {
    @Test
    public void getTournaments() throws RemoteException {
        TournamentManager manager = new TournamentManager();
        List<ITournament> tournaments = manager.getTournaments();
        System.out.println(tournaments.size());

    }

    @Test
    public void addTournament() throws RemoteException {
        TournamentManager manager = new TournamentManager();
        String string = manager.addTournament("test", "UnitTest");
       assertTrue(string != null);
    }

    @Test
    public void getTournament() throws RemoteException {
        TournamentManager manager = new TournamentManager();
        String string = manager.addTournament("test", "UnitTest");
        ITournament tournament = manager.getTournament(string);
        assertTrue(tournament !=null);
    }

    @Test
    public void deleteTournament() throws RemoteException {
        TournamentManager manager = new TournamentManager();
        String newId = manager.addTournament("test","Owner");
        manager.deleteTournament(newId);


        ITournament tournament = manager.getTournament(newId);
        assertTrue(tournament == null);
    }

}