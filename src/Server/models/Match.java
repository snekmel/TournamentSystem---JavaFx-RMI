package Server.models;

import Shared.enums.Status;
import Shared.models.Participant;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Match extends UnicastRemoteObject {
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
}
