package be.vdab.beers.forms;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class BestelBonLijnForm {
    private final long bierId;
    @Min(1)
    private final int aantal;
    @NotNull
    @PositiveOrZero
    @NumberFormat(pattern = "0.00")
    private final BigDecimal prijs;

    public BestelBonLijnForm(long bierId, int aantal, BigDecimal prijs) {
        this.bierId = bierId;
        this.aantal = aantal;
        this.prijs = prijs;
    }

    public long getBierId() {
        return bierId;
    }

    public int getAantal() {
        return aantal;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }
}
