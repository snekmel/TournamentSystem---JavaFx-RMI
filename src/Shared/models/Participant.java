package Shared.models;

import java.io.Serializable;

public class Participant implements Serializable {

    private String id;
    private String name;

    public Participant(String name) {
        this.name = name;
        this.id = java.util.UUID.randomUUID().toString();
    }
}
