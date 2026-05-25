package example.demo.controller;

import example.demo.dto.ClubDto;
import example.demo.entity.ClubEntity;
import example.demo.entity.StudentEntity;
import example.demo.service.ClubService;
import example.demo.service.StudentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/club")
public class ClubController {
    private final ClubService clubService;
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public ClubController(ClubService clubService, StudentService studentService, ModelMapper modelMapper) {
        this.clubService = clubService;
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    protected ClubDto toDto(ClubEntity ent) {
        return modelMapper.map(ent, ClubDto.class);
    }

    @Transactional
    protected ClubEntity toEntity(ClubDto dto) {
        return modelMapper.map(dto, ClubEntity.class);
    }

    @GetMapping("/student/{studentId}")
    public List<ClubDto> getClubsByStudent(@PathVariable Long studentId) {
        StudentEntity student = studentService.get(studentId);
        return student.getClubs().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ClubDto> getAll(){
        return clubService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ClubDto get(
            @PathVariable(name = "id") Long id){
        return toDto(clubService.get(id));
    }

    @PutMapping("/{id}")
    public ClubDto update(
            @RequestBody @Valid ClubDto dto,
            @PathVariable(name = "id") Long id) {
        var ent = toEntity(dto);
        return toDto(clubService.update(id, ent));
    }

    @PostMapping
    public ClubDto create(
            @RequestBody @Valid ClubDto dto) {
        var ent = toEntity(dto);
        ent.setId(null);
        return toDto(clubService.create(ent));
    }

    @DeleteMapping("/{id}")
    public ClubDto delete(
            @PathVariable(name = "id") Long id) {
        return toDto(clubService.delete(id));
    }

    @PostMapping("/{clubId}/students/{studentId}")
    public ClubDto addStudentToClub(@PathVariable Long clubId, @PathVariable Long studentId) {
        return toDto(clubService.addStudentToClub(clubId, studentId));
    }

    @DeleteMapping("/{clubId}/students/{studentId}")
    public ClubDto removeStudentFromClub(@PathVariable Long clubId, @PathVariable Long studentId) {
        return toDto(clubService.removeStudentFromClub(clubId, studentId));
    }
}
