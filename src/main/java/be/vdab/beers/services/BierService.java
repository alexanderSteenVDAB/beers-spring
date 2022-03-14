package be.vdab.beers.services;

import be.vdab.beers.domain.Bier;
import be.vdab.beers.repositories.BierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class BierService {
    private final BierRepository repository;

    public BierService(BierRepository repository) {
        this.repository = repository;
    }

    public long findAantal() {
        return repository.findAantal();
    }

    public List<Bier> findByBrouwer(long brouwerId) {
        return repository.findByBrouwer(brouwerId);
    }

    public Optional<Bier> findById(long id) {
        return repository.findById(id);
    }

    public Optional<Bier> findByIdForUpdate(long bierId) {
        return repository.findByIdForUpdate(bierId);
    }

    public List<Bier> findByIds(Set<Long> ids) {
        return repository.findByIds(ids);
    }

    public List<Bier> findByIdsForUpdate(Set<Long> ids) {
        return repository.findByIdsForUpdate(ids);
    }

    public void verhoogAantal(long bierId, int aantal) {
        repository.verhoogAantal(bierId, aantal);
    }
}
