package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleService = new ScheduleService(scheduleRepository, groupRepository);
    }

    @Test
    void testGetAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule1 = new Schedule();
        schedule1.setId(1L);
        schedule1.setDateFrom(LocalDate.of(2022, 1, 1));
        schedules.add(schedule1);
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        schedule2.setDateFrom(LocalDate.of(2023, 1, 1));
        schedules.add(schedule2);
        when(scheduleRepository.findAll()).thenReturn(schedules);
        List<Schedule> result = scheduleService.getAll();
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(LocalDate.of(2022, 1, 1), result.get(0).getDateFrom());
        assertEquals(2L, result.get(1).getId());
        assertEquals(LocalDate.of(2023, 1, 1), result.get(1).getDateFrom());
        verify(scheduleRepository, times(2)).findAll();
    }

    @Test
    void testFindScheduleById() {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDateFrom(LocalDate.of(2022, 1, 1));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        Schedule result = scheduleService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(LocalDate.of(2022, 1, 1), result.getDateFrom());
        verify(scheduleRepository).findById(1L);
    }

    @Test
    void testCreateSchedule() {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group 1");
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setSchoolYear("2022-2023");
        schedule.setSemester("spring");
        schedule.setDateFrom(LocalDate.of(2023, 2, 1));
        schedule.setDateUntil(LocalDate.of(2023, 6, 30));
        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        when(scheduleRepository.findAll()).thenReturn(Collections.emptyList());
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        Schedule createdSchedule = scheduleService.createSchedule(schedule, 1L);
        verify(groupRepository, times(1)).findById(eq(1L));
        verify(scheduleRepository, times(1)).findAll();
        ArgumentCaptor<Schedule> scheduleArgumentCaptor = ArgumentCaptor.forClass(Schedule.class);
        verify(scheduleRepository, times(1)).save(scheduleArgumentCaptor.capture());
        Schedule savedSchedule = scheduleArgumentCaptor.getValue();
        assertEquals(schedule, savedSchedule);
        assertEquals(schedule, createdSchedule);
    }


    @Test
    void testDisableSchedule() {
        Long id = 1L;
        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(id);
        existingSchedule.setActive(true);
        when(scheduleRepository.findById(id)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);
        Schedule disabledSchedule = scheduleService.disable(id);
        assertTrue(disabledSchedule != null && !disabledSchedule.isActive());
        verify(scheduleRepository).findById(id);
        verify(scheduleRepository).save(existingSchedule);
    }

    @Test
    void testEnableSchedule() {
        Long id = 1L;
        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(id);
        existingSchedule.setActive(false);
        when(scheduleRepository.findById(id)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);
        Schedule enabledSchedule = scheduleService.enable(id);
        assertTrue(enabledSchedule != null && enabledSchedule.isActive());
        verify(scheduleRepository).findById(id);
        verify(scheduleRepository).save(existingSchedule);
    }

    @Test
    void deleteSchedule() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(new Schedule()));
        when(scheduleRepository.findById(2L)).thenReturn(Optional.empty());
        Long id = 1L;
        var schedule = new Schedule();
        schedule.setId(3L);
        Optional<Schedule> scheduleToDelete = Optional.of(schedule);
        when(scheduleRepository.findById(3L)).thenReturn(scheduleToDelete);
        doThrow(new RuntimeException()).when(scheduleRepository).delete(scheduleToDelete.get());
        assertTrue(scheduleService.deleteSchedule(1L), "a");
        assertThrows(ValidationException.class, () -> scheduleService.deleteSchedule(2L), "b");
        assertFalse(scheduleService.deleteSchedule(3L), "c");
    }
}


