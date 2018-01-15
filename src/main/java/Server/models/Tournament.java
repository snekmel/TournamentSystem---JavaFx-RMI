package Server.models;

import Server.repositorys.RegistryRepository;
import Server.repositorys.TournamentRepository;
import Server.repositorys.context.TournamentRepositorySQL;
import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.interfaces.ITournament;
import Shared.interfaces.ITournamentManager;
import Shared.models.Participant;
import publisher.IRemotePublisherForDomain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tournament extends UnicastRemoteObject implements ITournament,Serializable {

    private String id;
    private Status gameStatus;
    private Date startDate;
    private String tournamentName;
    private String ownerName;
    private List<Participant> participants;
    private List<Match> matches;
    private List<Participant> winners;
    private List<Round> rounds;
    private int roundCount;

    public Tournament() throws RemoteException {
        this.id = java.util.UUID.randomUUID().toString();
        this.startDate = new Date();
        this.participants = new ArrayList<Participant>();
        this.matches = new ArrayList<Match>();
        this.tournamentName = this.id;
        this.gameStatus = Status.NotStarted;
        this.rounds = new ArrayList<>();

    }


    public Tournament(String id, Status gameStatus, Date startDate, String tournamentName, String ownerName) throws RemoteException {
        this.id = id;
        this.gameStatus = gameStatus;
        this.startDate = startDate;
        this.tournamentName = tournamentName;
        this.ownerName = ownerName;
        this.rounds = new ArrayList<>();
    }




    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setGameStatus(Status gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public void addParticipant(Participant p) throws RemoteException {
        try{
            this.participants.add(p);
           Registry registry = RegistryRepository.getRmiRegistry();
           IRemotePublisherForDomain publisherForDomain = (IRemotePublisherForDomain) registry.lookup(this.id);
           publisherForDomain.inform("Participants",null,this.participants);

            TournamentRepository tournamentRepository = new TournamentRepository(new TournamentRepositorySQL());
            tournamentRepository.updateTournament(this);

        }catch (Exception e){
             Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public List<Participant> getParticipants() throws RemoteException {
        return this.participants;
    }

    public void removeParticipant(String id) throws RemoteException {
        for (Participant p: this.participants
             ) {
            if (p.getId().equals(id)){
                try{
                    this.participants.remove(p);
                    Registry registry = RegistryRepository.getRmiRegistry();
                    IRemotePublisherForDomain publisherForDomain = (IRemotePublisherForDomain) registry.lookup(this.id);
                    publisherForDomain.inform("Participants",null,this.participants);
                    this.updateTournamentDb();

                }catch (Exception e){
                     Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    public List<IMatch> getMatches() throws RemoteException {
        List<IMatch> returnList = new ArrayList<>();
        for (Match match: this.matches
             ) {
            returnList.add((IMatch) match);
        }
        return returnList;


    }

    public IMatch getMatch(String id) throws RemoteException {
      IMatch returnMatch = null;
        for (Match match: this.matches
             ) {
            if (match.getId().equals(id)) {
                returnMatch = (IMatch) match;
            }
        }
      return returnMatch;

    }

    public void startTournament() throws RemoteException {
        this.winners = this.participants;
        this.generateMatches();
        this.gameStatus = Status.Active;
    }

    public void finishTournament() throws RemoteException {
        this.gameStatus = Status.Finished;
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

    private void generateMatches(){
        int participantsIndex = 0;
        int matchCount = this.participants.size() / 2;
        if ((this.participants.size() % 2) ==1){
            matchCount++;
        }
        try{
            for (int i = 0; i <= (matchCount - 1) ; i++) {
                if (this.participants.get(participantsIndex) != null && this.participants.get(participantsIndex + 1) !=null){
                    Match match = new Match(this.participants.get(participantsIndex),this.participants.get(participantsIndex + 1), this.id);
                    this.matches.add(match);
                }
                else if( this.participants.get(participantsIndex + 1) !=null){
                    System.out.println("1 left");
                }
            participantsIndex++;
            participantsIndex++;
            }

        }catch (Exception e){
             Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, e);
        }

        try{
            Registry registry = RegistryRepository.getRmiRegistry();
            IRemotePublisherForDomain publisherForDomain = (IRemotePublisherForDomain) registry.lookup(this.id);
            publisherForDomain.inform("Matches",null,this.matches);
        }catch (Exception e){
             Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void updateTournamentDb(){
        try{
            TournamentRepository tournamentRepository = new TournamentRepository(new TournamentRepositorySQL());
            tournamentRepository.updateTournament(this);
        }catch (Exception e){
             Logger.getLogger(Tournament.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
