package example.demo.controller;

import example.demo.dto.ClubDto;
import example.demo.dto.StudentDto;
import example.demo.entity.ClubEntity;
import example.demo.entity.StudentEntity;
import example.demo.service.StudentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequestMapping ("/api/student")
public class StudentController {
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    protected StudentDto toDto(StudentEntity ent) {
        return modelMapper.map(ent, StudentDto.class);
    }

    @Transactional
    protected StudentEntity toEntity(StudentDto dto) {
        return modelMapper.map(dto, StudentEntity.class);
    }

    @GetMapping
    public List<StudentDto> getAll(){
        return studentService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public StudentDto get(
            @PathVariable(name = "id") Long id){
        return toDto(studentService.get(id));
    }

    @GetMapping("/{id}/clubs")
    public List<ClubDto> getStudentClubs(@PathVariable Long id) {
        StudentEntity student = studentService.get(id);
        return student.getClubs().stream()
                .map(this::toClubDto)
                .collect(Collectors.toList());
    }

    protected ClubDto toClubDto(ClubEntity ent) {
        return modelMapper.map(ent, ClubDto.class);
    }

    @PutMapping("/{id}")
    public StudentDto update(
            @RequestBody @Valid StudentDto dto,
            @PathVariable(name = "id") Long id) {
        var ent = toEntity(dto);
        return toDto(studentService.update(id, ent));
    }

    @PostMapping
    public StudentDto create(
            @RequestBody @Valid StudentDto dto) {
        var ent = toEntity(dto);
        ent.setId(null);
        return toDto(studentService.create(ent));
    }

    @DeleteMapping("/{id}")
    public StudentDto delete(
            @PathVariable(name = "id") Long id) {
        return toDto(studentService.delete(id));
    }
}
