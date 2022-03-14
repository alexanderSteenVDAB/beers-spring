package be.vdab.beers.domain;

import java.math.BigDecimal;

public class Bier {
    private final long id;
    private final String naam;
    private final long brouwerId;
    private final BigDecimal alcohol;
    private final BigDecimal prijs;

    public Bier(long id, String naam, long brouwerId, BigDecimal alcohol, BigDecimal prijs) {
        this.id = id;
        this.naam = naam;
        this.brouwerId = brouwerId;
        this.alcohol = alcohol;
        this.prijs = prijs;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getBrouwerId() {
        return brouwerId;
    }

    public BigDecimal getAlcohol() {
        return alcohol;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bier bier = (Bier) o;

        if (id != bier.id) return false;
        if (brouwerId != bier.brouwerId) return false;
        if (!naam.equals(bier.naam)) return false;
        if (!alcohol.equals(bier.alcohol)) return false;
        return prijs.equals(bier.prijs);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + naam.hashCode();
        result = 31 * result + (int) (brouwerId ^ (brouwerId >>> 32));
        result = 31 * result + alcohol.hashCode();
        result = 31 * result + prijs.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bier{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", brouwerId=" + brouwerId +
                ", alcohol=" + alcohol +
                ", prijs=" + prijs +
                '}';
    }
}
