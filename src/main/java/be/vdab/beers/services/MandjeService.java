package be.vdab.beers.services;

import be.vdab.beers.domain.BestelBon;
import be.vdab.beers.domain.BestelBonLijn;
import be.vdab.beers.repositories.BestelBonLijnRepository;
import be.vdab.beers.repositories.BestelBonRepository;
import be.vdab.beers.sessions.Mandje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MandjeService {
    private final Mandje mandje;
    private final BierService bierService;
    private final BestelBonRepository bestelBonRepository;
    private final BestelBonLijnRepository bestelBonLijnRepository;

    public MandjeService(Mandje mandje, BierService bierService, BestelBonRepository bestelBonRepository,
                         BestelBonLijnRepository bestelBonLijnRepository) {
        this.mandje = mandje;
        this.bierService = bierService;
        this.bestelBonRepository = bestelBonRepository;
        this.bestelBonLijnRepository = bestelBonLijnRepository;
    }


    public List<BestelBonLijn> findBestelBonLijnen() {
        var besteldeBieren = bierService.findByIds(mandje.getBesteldeArtikels().keySet());

        return besteldeBieren.stream().map(
                        bier -> new BestelBonLijn(bier, mandje.getBesteldeArtikels().get(bier.getId())))
                .toList();
    }

    public List<BestelBonLijn> findBestelBonLijnenForUpdate() {
        var besteldeBieren = bierService.findByIdsForUpdate(mandje.getBesteldeArtikels().keySet());

        return besteldeBieren.stream().map(
                        bier -> new BestelBonLijn(bier, mandje.getBesteldeArtikels().get(bier.getId())))
                .toList();
    }


    @Transactional()
    public long bevestig(BestelBon bestelBon) {
        var id = bestelBonRepository.create(bestelBon);

        findBestelBonLijnenForUpdate().forEach(bestelBonLijn -> {
            bestelBonLijn.setBestelBonId(id);
            bestelBonLijnRepository.create(bestelBonLijn);
            bierService.verhoogAantal(bestelBonLijn.getBierId(), bestelBonLijn.getAantal());
            mandje.verwijder(bestelBonLijn.getBierId());
        });

        return id;
    }

    public List<String> getBierNamen() {
        return findBestelBonLijnen().stream().map(BestelBonLijn::getBierNaam).sorted().toList();
    }

    public void maakLeeg() {
        mandje.maakLeeg();
    }
}
