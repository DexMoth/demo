package example.demo.dto;

import example.demo.entity.ClubEntity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String email;
    private String avatar;
    private GroupDto group;
    private List<ClubDto> clubs = new ArrayList<>();
}
