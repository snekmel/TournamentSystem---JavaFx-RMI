package Server.models;

import Shared.interfaces.ITournament;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TournamentManagerTest {
    @Test
    public void getTournaments() throws Exception {
        TournamentManager manager = new TournamentManager();
        List<ITournament> tournaments = manager.getTournaments();
        System.out.println(tournaments.size());

    }

    @Test
    public void addTournament() throws Exception {
        TournamentManager manager = new TournamentManager();
        manager.addTournament("test", "UnitTest");
    }

    @Test
    public void getTournament() throws Exception {
    }

    @Test
    public void deleteTournament() throws Exception {
    }

}