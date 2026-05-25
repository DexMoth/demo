package example.demo.service;

import example.demo.entity.ClubEntity;
import example.demo.repository.ClubRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final StudentService studentService;

    public ClubService(ClubRepository clubRepository, StudentService studentService) {
        this.clubRepository = clubRepository;
        this.studentService = studentService;
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

    @Transactional
    public ClubEntity addStudentToClub(Long clubId, Long studentId) {
        ClubEntity club = get(clubId);
        var student = studentService.get(studentId);
        club.getStudents().add(student);
        student.getClubs().add(club);
        clubRepository.save(club);
        studentService.update(studentId, student);
        return club;
    }

    @Transactional
    public ClubEntity removeStudentFromClub(Long clubId, Long studentId) {
        ClubEntity club = get(clubId);
        var student = studentService.get(studentId);
        club.getStudents().remove(student);
        student.getClubs().remove(club);
        clubRepository.save(club);
        studentService.update(studentId, student);
        return club;
    }

    @Transactional
    public long getStudentCount(Long clubId) {
        return clubRepository.countStudentsByClubId(clubId);
    }
}
