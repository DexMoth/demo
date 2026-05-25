package example.demo.repository;

import example.demo.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
    @Query("SELECT COUNT(s) FROM ClubEntity c JOIN c.students s WHERE c.id = :clubId")
    long countStudentsByClubId(@Param("clubId") Long clubId);
}

/*
@Query("SELECT cd FROM CurriculumDisciplineModel cd " +
        "LEFT JOIN FETCH cd.curriculum c " +
        "LEFT JOIN FETCH c.studyForm " +
        "LEFT JOIN FETCH c.studyDirection " +
        "LEFT JOIN FETCH cd.discipline d " +
        "LEFT JOIN FETCH d.department " +
        "WHERE cd.id = :id")
Optional<CurriculumDisciplineModel> findByIdWithRelations(@Param("id") Long id);
}

@Query("SELECT cd FROM CurriculumDisciplineModel cd WHERE cd.curriculum.id = :curriculumId")
    List<CurriculumDisciplineModel> findByCurriculumId(@Param("curriculumId") Long curriculumId);

    Optional<TeacherModel> findByLoginIgnoreCase(String login);
*/