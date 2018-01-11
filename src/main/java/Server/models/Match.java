package Server.models;

import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.models.Participant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Match extends UnicastRemoteObject implements IMatch{
    private String id;
    private Status matchStatus;
    private Participant participant1;
    private Participant participant2;
    private int participant1Points = 0;
    private int participant2Points = 0;
    private Participant winner;

    public Match(Participant participant1, Participant participant2) throws RemoteException{
        this.id = java.util.UUID.randomUUID().toString();
        this.participant1 = participant1;
        this.participant2 = participant2;
    }


    @Override
    public void start() throws RemoteException {

    }

    @Override
    public int addPointsParticipant1(int points) throws RemoteException {
        return 0;
    }

    @Override
    public int addPointsParticipant2(int points) throws RemoteException {
        return 0;
    }

    @Override
    public int removePointsParticipant1(int points) throws RemoteException {
        return 0;
    }

    @Override
    public int removePointsParticipant2(int points) throws RemoteException {
        return 0;
    }

    @Override
    public void endMatch() throws RemoteException {

    }

    @Override
    public void endMatch(Participant winner) throws RemoteException {

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
}
