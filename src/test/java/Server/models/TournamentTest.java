package Server.models;

import Shared.enums.Status;
import Shared.models.Participant;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class TournamentTest {
    @Test
    public void setOwnerName() throws RemoteException {
        Tournament t = new Tournament();
        t.setOwnerName("test");
        assertTrue(t.getOwnername().equals("test"));
    }

    @Test
    public void setGameStatus() throws RemoteException {
        Tournament t = new Tournament();
        t.setGameStatus(Status.Paused);
        assertTrue(t.getGameStatus() == Status.Paused);
    }

    @Test
    public void setTournamentName() throws RemoteException {
        Tournament t = new Tournament();
        t.setTournamentName("Test");
        assertTrue(t.getName().equals("Test"));
    }

    @Test
    public void addParticipant() throws RemoteException {
        Tournament t = new Tournament();
        t.setTournamentName("Test");
        t.addParticipant(new Participant("test1"));
        t.addParticipant(new Participant("test2"));
        assertTrue(t.getParticipants().get(0).getName().equals("test1"));
    }

    @Test
    public void getParticipants() throws RemoteException {
        Tournament t = new Tournament();
        t.setTournamentName("Test");
        t.addParticipant(new Participant("test1"));
        t.addParticipant(new Participant("test2"));
        assertTrue(t.getParticipants().size() > 1);
    }

    @Test
    public void removeParticipant() throws RemoteException {
        Tournament t = new Tournament();
        t.setTournamentName("Test");
        t.addParticipant(new Participant("test1"));
        t.addParticipant(new Participant("test2"));
        assertTrue(t.getParticipants().size() > 1);

        String removeId = t.getParticipants().get(0).getId();
        t.removeParticipant(removeId);
        assertTrue(t.getParticipants().size() == 1);
    }


    @Test
    public void startTournament() throws RemoteException {
        Tournament t = new Tournament();
        t.setTournamentName("Test");
        t.addParticipant(new Participant("test1"));
        t.addParticipant(new Participant("test2"));
        assertTrue(t.getParticipants().size() > 1);

        t.startTournament();
        assertTrue(t.getGameStatus() == Status.Active);
        assertTrue(t.getMatches().size() == 1);

    }

    @Test
    public void finishTournament() throws RemoteException {


    }


    @Test
    public void getName() throws RemoteException {

        Tournament t = new Tournament();
        t.setTournamentName("Test");
        assertTrue(t.getName().equals("Test"));
    }

    @Test
    public void getOwnername() throws RemoteException {
        Tournament t = new Tournament();
        t.setOwnerName("Test");
        assertTrue(t.getOwnername().equals("Test"));
    }

}