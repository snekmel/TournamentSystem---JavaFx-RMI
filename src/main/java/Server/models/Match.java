package Server.models;

import Server.repositorys.RegistryRepository;
import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.models.Participant;
import publisher.IRemotePublisherForDomain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class Match extends UnicastRemoteObject implements IMatch{
    private String id;
    private Status matchStatus;
    private Participant participant1;
    private Participant participant2;
    private int participant1Points = 0;
    private int participant2Points = 0;
    private Participant winner;
    private Timer timer = new Timer();
    private int seconds = 0;
    private String tournamentKey;


    public Match(Participant participant1, Participant participant2, String id) throws RemoteException{
        this.id = java.util.UUID.randomUUID().toString();
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.matchStatus = Status.NotStarted;
        this.tournamentKey = id;
    }

    public Match(Participant participant1, String id)throws RemoteException{
        this.id = java.util.UUID.randomUUID().toString();
        this.participant1 = participant1;
        this.participant2 = new Participant("");
        this.winner = participant1;
        this.matchStatus = Status.Finished;
        this.tournamentKey = id;
    }

    @Override
    public void start() throws RemoteException {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                pushTimeToClients();
            }
        }, 0,1000);

        this.matchStatus = Status.Active;
        this.pushToClients();
    }

    @Override
    public int addPointsParticipant1(int points) throws RemoteException {
       this.participant1Points = this.participant1Points + points;
        this.pushToClients();
        return this.participant1Points;
    }

    @Override
    public int addPointsParticipant2(int points) throws RemoteException {
       this.participant2Points = this.participant2Points + points;
        this.pushToClients();
        return this.participant2Points;
    }

    @Override
    public int removePointsParticipant1(int points) throws RemoteException {
        this.participant1Points = this.participant1Points - points;
        this.pushToClients();
        return this.participant1Points;
    }

    @Override
    public int removePointsParticipant2(int points) throws RemoteException {
        this.participant2Points = this.participant2Points - points;
        this.pushToClients();
        return this.participant2Points;
    }

    @Override
    public void endMatch() throws RemoteException {
        if (this.participant2Points > this.participant1Points){
            this.winner = this.participant2;
        }else{
            this.winner = this.participant1;
        }
        this.matchStatus = Status.Finished;
        this.pushToClients();
    }

    @Override
    public void endMatch(Participant winner) throws RemoteException {
        this.winner = winner;
        this.matchStatus = Status.Finished;
        this.timer.cancel();
        this.pushToClients();
    }

    @Override
    public Participant getParticipant1() throws RemoteException {
        return  this.participant1;
    }

    @Override
    public Participant getParticipant2() throws RemoteException {
      return this.participant2;
    }

    @Override
    public Status getStatus() throws RemoteException {
        return  this.matchStatus;
    }

    @Override
    public String getId() throws RemoteException {
      return  this.id;
    }

    @Override
    public int getPointsParticipant1() throws RemoteException {
      return this.participant1Points;
    }

    @Override
    public int getPointsParticipant2() throws RemoteException {
        return this.participant2Points;
    }

    @Override
    public Participant getWinner() throws RemoteException {
       if (this.winner !=null){
           return  this.winner;
       }else{
           return null;
       }
    }

    private void pushToClients(){
        try{
            Registry registry = RegistryRepository.getRmiRegistry();
            IRemotePublisherForDomain publisherForDomain = (IRemotePublisherForDomain) registry.lookup(this.tournamentKey);
            publisherForDomain.inform("Match",null,this);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void pushTimeToClients(){
        try{
            Registry registry = RegistryRepository.getRmiRegistry();
            IRemotePublisherForDomain publisherForDomain = (IRemotePublisherForDomain) registry.lookup(this.tournamentKey);
            publisherForDomain.inform("Match-Time",null,(this.id+"|"+this.seconds));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void pause() throws RemoteException {
        this.timer.cancel();
        this.matchStatus = Status.Paused;
        this.pushToClients();
    }



    @Override
    public int getSeconds() throws RemoteException {
        return this.seconds;
    }

    @Override
    public void resume() throws RemoteException {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                pushTimeToClients();
            }
        }, 0,1000);

        this.matchStatus = Status.Active;
        this.pushToClients();
    }


    @Override
    public String toString() {
        return this.participant1.getName() + "V.S." + this.participant2.getName() + "| Winner: " + this.winner.getName();
    }
}
