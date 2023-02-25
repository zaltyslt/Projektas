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
            "                           OR lower(e.nickName) LIKE lower(:fragment) " +
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

//    TypedQuery<Set<Teacher>> query = entityManager.createQuery(
//            "SELECT ts FROM TeacherSubject ts " +
//                    "JOIN ts.teacher t " +
//                    "JOIN ts.subject s " +
//                    "WHERE s.id = :subjectId", Teacher.class);
//
//query.setParameter("subjectId", 3);
//
//    List<Teacher> results = query.getResultList();

 /*
            * Get teachers by subject id
* SELECT TEACHER.TEACHER_ID ,TEACHER.F_NAME ,TEACHER.L_NAME ,SUBJECT.ID ,SUBJECT.NAME
    FROM TEACHER_SUBJECT
    INNER JOIN TEACHER
    ON TEACHER_SUBJECT.TEACHER_ID =TEACHER .TEACHER_ID
    INNER JOIN SUBJECT
    ON TEACHER_SUBJECT.SUBJECT_ID=SUBJECT.ID
    Where TEACHER_SUBJECT.SUBJECT_ID =3;
*
        *@Query("SELECT e FROM Teacher e WHERE lower(e.fName) LIKE lower(:fragment) " +
            "                           OR lower(e.lName) LIKE lower(:fragment) " +
            "                           OR lower(e.nickName) LIKE lower(:fragment) " +
            "ORDER BY e.lName DESC")

    List<Teacher> getTeachersByNameFragment(String fragment);
* */
//    @Query("SELECT t FROM Teacher t WHERE lower(e.fName) LIKE lower(:fragment) " +





    List<Teacher> findByisActive(boolean active);
//    List<Teacher> findByfNameLikeIgnoreCaseOrlNameLikeIgnoreCaseOrnickNameLikeIgnoreCaseAllIgnoreCase(@Nullable String fName, @Nullable String lName, @Nullable String nickName);
//    Teacher findByfNameLikeIgnoreCaseAllIgnoreCase(@Nullable String fName);
//    List<Teacher> findByfNameLikeIgnoreCaseOrlNameLikeIgnoreCaseAllIgnoreCase(String fName, String lName);
//    Teacher findByfNameContainsIgnoreCaseOrlNameContainsIgnoreCaseAndNickNameContainsIgnoreCaseAllIgnoreCase(String fName, String lName, String nickName);

}
