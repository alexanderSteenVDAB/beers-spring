package be.vdab.beers.domain;

public class Brouwer {
    private final long id;
    private final String naam;

    public Brouwer(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

}
