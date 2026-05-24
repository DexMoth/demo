package example.demo.service;

import example.demo.entity.GroupEntity;
import example.demo.repository.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public List<GroupEntity> getAll() {
        return groupRepository.findAll();
    }

    @Transactional
    public GroupEntity get(Long id) {
        var result = groupRepository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("group not found"));
    }

    @Transactional
    public GroupEntity create(GroupEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        return groupRepository.save(entity);
    }

    @Transactional
    public GroupEntity update(Long id, GroupEntity entity) {
        var ent = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("group not found"));
        ent.setName(entity.getName());
        return groupRepository.save(ent);
    }

    @Transactional
    public GroupEntity delete(Long id) {
        var ent = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("group not found"));
        groupRepository.delete(ent);
        return ent;
    }
}
