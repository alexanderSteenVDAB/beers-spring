package be.vdab.beers.forms;

import javax.validation.constraints.Min;

public class BestelBonLijnForm {
    private final long bierId;
    @Min(1)
    private final int aantal;

    public BestelBonLijnForm(long bierId, int aantal) {
        this.bierId = bierId;
        this.aantal = aantal;
    }

    public long getBierId() {
        return bierId;
    }

    public int getAantal() {
        return aantal;
    }

}
