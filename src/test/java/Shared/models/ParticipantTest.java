package Shared.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParticipantTest {
    @Test
    public void getId() throws Exception {
        Participant participant = new Participant("Lars");
        assertTrue(participant.getId() != null);

    }

    @Test
    public void getName() throws Exception {
        Participant participant = new Participant("Lars");
        assertTrue(participant.getName().equals("Lars"));
    }

}