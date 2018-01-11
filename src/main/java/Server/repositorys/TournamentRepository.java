package Server.repositorys;

import Server.models.Tournament;
import Server.repositorys.interfaces.ITournamentRepository;

import java.util.List;

public class TournamentRepository {

    private ITournamentRepository iTournamentRepository;

    public TournamentRepository(ITournamentRepository iTournamentRepository) {
        this.iTournamentRepository = iTournamentRepository;
    }

    public String createTournament(Tournament t){
       return this.iTournamentRepository.createTournament(t);
    }

    public void deleteTournament(String key){
        this.iTournamentRepository.deleteTournament(key);
    }

    public List<Tournament> getTournaments(){
        return this.iTournamentRepository.getTournaments();
    }

    public void updateTournament(Tournament t){
        this.iTournamentRepository.updateTournament(t);
    }

}
