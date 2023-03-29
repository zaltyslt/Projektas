package lt.techin.schedule.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

//    @Query(value = "UPDATE customer SET name = :name WHERE id = :id", nativeQuery = true)
//    @Query(value = "delete from CLASS_GROUP where id = 5 ", nativeQuery = true)
//        void deleteFromClassGroup(@Param("id") Long id, @Param("name") String name);
@Modifying
@Query(value = "DELETE FROM CLASS_GROUP WHERE id = :id", nativeQuery = true)
void deleteClassGroupById(@Param("id") Long id);

}
