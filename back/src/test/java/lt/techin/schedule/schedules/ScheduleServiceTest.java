package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleServiceTest {
    @MockBean
    ScheduleRepository scheduleRepository;

//@InjectMocks
    @Autowired
ScheduleService scheduleService;



    @Test
    void deleteSchedule() {

        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(new Schedule()));
        Mockito.when(scheduleRepository.findById(2L)).thenReturn(Optional.empty());

        Long id = 1L;
        var schedule = new Schedule();
        schedule.setId(3L);
        Optional<Schedule> scheduleToDelete = Optional.of(schedule);
        Mockito.when(scheduleRepository.findById(3L)).thenReturn(scheduleToDelete);
        doThrow(new RuntimeException()).when(scheduleRepository).delete(scheduleToDelete.get());

        assertTrue(scheduleService.deleteSchedule(1L), "a");
        assertThrows(ValidationException.class, () -> scheduleService.deleteSchedule(2L), "b");
        assertFalse(scheduleService.deleteSchedule(3L), "c");

    }
}


