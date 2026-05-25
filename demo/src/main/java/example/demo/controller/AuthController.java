package example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import example.demo.dto.AuthDto;
import example.demo.entity.StudentEntity;
import example.demo.repository.StudentRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final StudentRepository studentRepository;

    public AuthController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Регистрация
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto dto, HttpSession session) {
        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Студент с таким email уже существует"));
        }

        StudentEntity student = new StudentEntity();
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword());
        student.setName(dto.getName());
        student.setAdmin(dto.isAdmin());

        StudentEntity savedStudent = studentRepository.save(student);

        session.setAttribute("student", savedStudent);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Регистрация успешна");
        response.put("student", Map.of(
                "id", savedStudent.getId(),
                "email", savedStudent.getEmail(),
                "name", savedStudent.getName(),
                "isAdmin", savedStudent.isAdmin()
        ));
        return ResponseEntity.ok(response);
    }

    // Вход
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto dto, HttpSession session) {
        // Ищем студента по email
        StudentEntity student = studentRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (student == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Студент не найден"));
        }

        // Проверяем пароль
        if (!student.getPassword().equals(dto.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Неверный пароль"));
        }

        // Сохраняем студента в сессию
        session.setAttribute("student", student);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Вход выполнен");
        response.put("student", Map.of(
                "id", student.getId(),
                "email", student.getEmail(),
                "name", student.getName(),
                "isAdmin", student.isAdmin()
        ));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentStudent(HttpSession session) {
        StudentEntity student = (StudentEntity) session.getAttribute("student");

        if (student == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Не авторизован"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", student.getId());
        response.put("email", student.getEmail());
        response.put("name", student.getName());
        response.put("isAdmin", student.isAdmin());

        return ResponseEntity.ok(response);
    }

    // Выход
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Выход выполнен"));
    }
}
