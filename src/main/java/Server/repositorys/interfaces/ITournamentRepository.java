package Server.repositorys.interfaces;

import Server.models.Tournament;

import java.util.List;

public interface ITournamentRepository {

    String createTournament(Tournament t);

    void deleteTournament(String key);

    List<Tournament> getTournaments();

    void updateTournament(Tournament t);
}
