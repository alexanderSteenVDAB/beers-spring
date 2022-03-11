package be.vdab.beers.domain;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

public class BestelBon {
    private final long id;
    @NotEmpty
    private final String naam;
    @NotEmpty
    private final String straat;
    @NotEmpty
    private final String huisnr;
    @Range(min = 1000, max = 9999)
    private final int postcode;
    @NotEmpty
    private final String gemeente;


    public BestelBon(long id, String naam, String straat, String huisnr, int postcode, String gemeente) {
        this.id = id;
        this.naam = naam;
        this.straat = straat;
        this.huisnr = huisnr;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnr() {
        return huisnr;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }
}
