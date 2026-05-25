package example.demo.dto;

import example.demo.entity.StudentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClubDto {
    Long id;
    private String name;
    private long studentCount;
}
