package lt.techin.schedule.programs.subjectsHours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectHoursRepository extends JpaRepository<SubjectHours, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM SUBJECT_HOURS where subject = ?")
    Optional<SubjectHours> findBySubject(Long aLong);

}
