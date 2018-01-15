package Server.models;

import Shared.interfaces.IMatch;

import java.io.Serializable;
import java.util.List;

public class Round implements Serializable {

    private int id;
    private List<Match> results;

    public int getId() {
        return id;
    }

    public List<Match> getResults() {
        return results;
    }

    public Round(int id, List<Match> results) {
        this.id = id;
        this.results = results;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
