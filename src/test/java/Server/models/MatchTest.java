package Server.models;

import Shared.enums.Status;
import Shared.interfaces.IMatch;
import Shared.models.Participant;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class MatchTest {
    @Test
    public void start() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.start();
        assertTrue(match.getStatus() == Status.Active);
    }

    @Test
    public void addPointsParticipant1() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.addPointsParticipant1(5);
        assertTrue(match.getPointsParticipant1() == 5);

    }

    @Test
    public void addPointsParticipant2() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.addPointsParticipant2(5);
        assertTrue(match.getPointsParticipant2() == 5);
    }

    @Test
    public void removePointsParticipant1() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.removePointsParticipant1(5);
        assertTrue(match.getPointsParticipant1() == -5);
    }

    @Test
    public void removePointsParticipant2() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.removePointsParticipant2(5);
        assertTrue(match.getPointsParticipant2() == -5);
    }

    @Test
    public void endMatch() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.start();
        match.endMatch(match.getParticipant1());
        assertTrue(match.getStatus() == Status.Finished);
    }

    @Test
    public void getPointsParticipant1() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");

        assertTrue(match.getPointsParticipant1() == 0);
    }

    @Test
    public void getPointsParticipant2() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");

        assertTrue(match.getPointsParticipant2() == 0);
    }

    @Test
    public void getWinner() throws RemoteException {
    }

    @Test
    public void pause() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.start();
        match.pause();
        assertTrue(match.getStatus() == Status.Paused);
    }

    @Test
    public void getSeconds() throws RemoteException {
        Match match = new Match(new Participant("Tester1"),new Participant("Tester3"),"123");
        match.start();

        assertTrue(match.getSeconds() > 1);
    }

}