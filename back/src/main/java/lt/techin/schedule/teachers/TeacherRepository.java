package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Set<Teacher> findTeachersByHashCode(Integer hashCode);

    @Query("SELECT e FROM Teacher e WHERE lower(e.fName) LIKE lower(:fragment) " +
            "                           OR lower(e.lName) LIKE lower(:fragment) " +

            "ORDER BY e.lName DESC")
    List<Teacher> getTeachersByNameFragment(String fragment);

    @Query(nativeQuery = true,
            value = "SELECT * FROM TEACHER_SUBJECT " +
                    "INNER JOIN TEACHER " +
                    "ON TEACHER_SUBJECT.TEACHER_ID =TEACHER .TEACHER_ID " +
                    "INNER JOIN SUBJECT " +
                    "ON TEACHER_SUBJECT.SUBJECT_ID=SUBJECT.ID " +
                    "Where TEACHER_SUBJECT.SUBJECT_ID = :subject" )
    Set<Teacher> getTeacherBySubject(Subject subject );
    List<Teacher> findByisActive(boolean active);

}
