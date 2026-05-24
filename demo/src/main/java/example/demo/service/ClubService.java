package example.demo.service;

import example.demo.entity.ClubEntity;
import example.demo.repository.ClubRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Transactional
    public List<ClubEntity> getAll() {
        return clubRepository.findAll();
    }

    @Transactional
    public ClubEntity get(Long id) {
        var result = clubRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("club not found"));
    }

    @Transactional
    public ClubEntity create(ClubEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        return clubRepository.save(entity);
    }

    @Transactional
    public ClubEntity update(Long id, ClubEntity entity) {
        var ent = clubRepository.findById(id).orElseThrow(() -> new RuntimeException("club not found"));
        ent.setName(entity.getName());
        return clubRepository.save(ent);
    }

    @Transactional
    public ClubEntity delete(Long id) {
        var ent = clubRepository.findById(id).orElseThrow(() -> new RuntimeException("club not found"));
        clubRepository.delete(ent);
        return ent;
    }
}
