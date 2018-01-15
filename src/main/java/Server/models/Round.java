package Server.models;

import Shared.interfaces.IMatch;

import java.io.Serializable;
import java.util.List;

public class Round implements Serializable {

    private int id;
    private List<IMatch> results;

    public int getId() {
        return id;
    }

    public List<IMatch> getResults() {
        return results;
    }

    public Round(int id, List<IMatch> results) {
        this.id = id;
        this.results = results;
    }
}
