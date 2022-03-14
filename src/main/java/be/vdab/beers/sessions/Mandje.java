package be.vdab.beers.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@SessionScope
public class Mandje implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<Long, Integer> besteldeArtikels = new LinkedHashMap<>();

    public void voegToe(long id, int aantal) {
        if (besteldeArtikels.containsKey(id)) {
            besteldeArtikels.put(id, besteldeArtikels.get(id) + aantal);
        } else {
            besteldeArtikels.put(id, aantal);
        }
    }

    public boolean isMandjeGevuld() {
        return !besteldeArtikels.isEmpty();
    }

    public void verwijder(long id) {
        besteldeArtikels.remove(id);
    }

    public Map<Long, Integer> getBesteldeArtikels() {
        return besteldeArtikels;
    }

    public void maakLeeg() {
        besteldeArtikels.clear();
    }
}
