package example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="club")
public class ClubEntity extends BaseEntity{
    private String name;
    @ManyToMany(mappedBy = "clubs")
    private Set<StudentEntity> students = new HashSet<>();
}
