package be.vdab.beers.domain;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class BestelBonLijn {
    private final Bier bier;
    @Positive
    private final int aantal;
    private long bestelBonId;

    public BestelBonLijn(Bier bier, int aantal) {
        this.bier = bier;
        this.aantal = aantal;
    }

    public BestelBonLijn(long bestelBonId, Bier bier, int aantal) {
        this(bier, aantal);
        this.bestelBonId = bestelBonId;

    }

    public long getBestelBonId() {
        return bestelBonId;
    }

    public void setBestelBonId(long bestelBonId) {
        this.bestelBonId = bestelBonId;
    }

    public int getAantal() {
        return aantal;
    }

    public Bier getBier() {
        return bier;
    }

    public long getBierId() {
        return bier.getId();
    }

    public String getBierNaam() {
        return bier.getNaam();
    }

    public BigDecimal getBierPrijs() {
        return bier.getPrijs();
    }

    public BigDecimal getPrijs() {
        return bier.getPrijs().multiply(BigDecimal.valueOf(aantal));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BestelBonLijn that)) return false;

        if (aantal != that.aantal) return false;
        if (bestelBonId != that.bestelBonId) return false;
        return bier.equals(that.bier);
    }

    @Override
    public int hashCode() {
        int result = bier.hashCode();
        result = 31 * result + aantal;
        result = 31 * result + (int) (bestelBonId ^ (bestelBonId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BestelBonLijn{" +
                "bier=" + bier +
                ", aantal=" + aantal +
                ", bestelBonId=" + bestelBonId +
                '}';
    }
}
