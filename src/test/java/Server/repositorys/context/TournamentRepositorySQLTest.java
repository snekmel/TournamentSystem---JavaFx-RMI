package Server.repositorys.context;

import Server.models.Tournament;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TournamentRepositorySQLTest {

    private TournamentRepositorySQL trs = new TournamentRepositorySQL();

    @Test
    public void createTournament() throws Exception {

        Tournament tournament = new Tournament();
        trs.createTournament(tournament);

    }


    @Test
    public void deleteTournament() throws Exception {

        trs.deleteTournament("eecd2354-77e4-4d9d-9869-2c729fa05db3");

    }

    @Test
    public void getTournaments() throws Exception {
        List<Tournament> tournaments = trs.getTournaments();
        for (Tournament t: tournaments
             ) {
            System.out.println(t.getId() + t.getName());
        }

    }

    @Test
    public void updateTournament() throws Exception {
    }

}