package example.demo.controller;

import example.demo.dto.GroupDto;
import example.demo.entity.GroupEntity;
import example.demo.service.GroupService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/group")
public class GroupController {
    private final GroupService groupService;
    private final ModelMapper modelMapper;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    protected GroupDto toDto(GroupEntity ent) {
        return modelMapper.map(ent, GroupDto.class);
    }

    @Transactional
    protected GroupEntity toEntity(GroupDto dto) {
        return modelMapper.map(dto, GroupEntity.class);
    }

    @GetMapping
    public List<GroupDto> getAll(){
        return groupService.getAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public GroupDto get(
            @PathVariable(name = "id") Long id){
        return toDto(groupService.get(id));
    }

    @PutMapping("/{id}")
    public GroupDto update(
            @RequestBody @Valid GroupDto dto,
            @PathVariable(name = "id") Long id) {
        var ent = toEntity(dto);
        return toDto(groupService.update(id, ent));
    }

    @PostMapping
    public GroupDto create(
            @RequestBody @Valid GroupDto dto) {
        var ent = toEntity(dto);
        ent.setId(null);
        return toDto(groupService.create(ent));
    }

    @DeleteMapping("/{id}")
    public GroupDto delete(
            @PathVariable(name = "id") Long id) {
        return toDto(groupService.delete(id));
    }
}
