package Shared.interfaces;

import Shared.models.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMatch extends Remote {
    void start() throws RemoteException;
    int addPointsParticipant1(int points) throws RemoteException;
    int addPointsParticipant2(int points) throws RemoteException;
    int removePointsParticipant1(int points) throws RemoteException;
    int removePointsParticipant2(int points) throws RemoteException;
    void endMatch() throws RemoteException;
    void endMatch(Participant winner) throws RemoteException;
    Participant getParticipant1() throws RemoteException;
    Participant getParticipant2() throws RemoteException;

}
