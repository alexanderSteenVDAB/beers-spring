package be.vdab.beers.services;

import be.vdab.beers.domain.Brouwer;
import be.vdab.beers.repositories.BrouwerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BrouwerService {
    private final BrouwerRepository repository;

    public BrouwerService(BrouwerRepository repository) {
        this.repository = repository;
    }

    public List<Brouwer> findAll() {
        return repository.findAll();
    }
}
