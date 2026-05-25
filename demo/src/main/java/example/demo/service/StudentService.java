package example.demo.service;

import example.demo.entity.StudentEntity;
import example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public List<StudentEntity> getAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public StudentEntity get(Long id) {
        var result = studentRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("student not found"));
    }

    @Transactional
    public StudentEntity create(StudentEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        return studentRepository.save(entity);
    }

    @Transactional
    public StudentEntity update(Long id, StudentEntity entity) {
        var ent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student not found"));
        ent.setGroup(entity.getGroup());
        ent.setName(entity.getName());
        ent.setClubs(entity.getClubs());
        ent.setEmail(entity.getEmail());
        //ent.setPassword(entity.getPassword());
        ent.setAvatar(entity.getAvatar());
        ent.setAdmin(entity.isAdmin());
        return studentRepository.save(ent);
    }

    @Transactional
    public StudentEntity delete(Long id) {
        var ent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student not found"));
        studentRepository.delete(ent);
        return ent;
    }
}
