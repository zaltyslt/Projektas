package lt.techin.schedule.schedule;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupRepository;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleCreateDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    private ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;
    private GroupRepository groupRepository;

    @BeforeEach
    public void setUp() {
        scheduleRepository = mock(ScheduleRepository.class);
        groupRepository = mock(GroupRepository.class);
//        scheduleService = new ScheduleService(scheduleRepository, groupRepository);
    }

    @Test
    public void testGetAll() {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule());
        schedules.add(new Schedule());
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> result = scheduleService.getAll();

        assertEquals(schedules, result);
    }

    @Test
    public void testFindById() {
        Schedule schedule = new Schedule();
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.findById(1L);

        assertNotNull(result);
        assertEquals(schedule, result);
    }

    @Test
    public void testCreateSchedule() {
        Schedule schedule = new Schedule();
        Group group = new Group();
        group.setId(1L);
        schedule.setId(1L);
        schedule.setDateFrom(LocalDate.now().minusDays(1));
        schedule.setDateUntil(LocalDate.now().plusDays(1));
        schedule.setGroups(group);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(scheduleRepository.findAll()).thenReturn(new ArrayList<>());
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        Schedule result = scheduleService.createSchedule(schedule, 1L);

        assertNotNull(result);
        assertEquals(schedule, result);
    }

    public Schedule createSchedule(Schedule schedule, Long groupId) {
        var existingGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ValidationException("Nurodyta grupė nerasta", "Group", "Does not exist", groupId.toString()));
        schedule.setGroups(existingGroup);

        var existing = scheduleRepository.findAll();
        existing = existing.stream().filter(s -> existingGroup.getName() != null && s.getGroups().getName().equalsIgnoreCase(existingGroup.getName()))
                .filter(s -> s.getDateFrom().equals(schedule.getDateFrom()))
                .filter(s -> s.getDateUntil().equals(schedule.getDateUntil()))
                .collect(Collectors.toList());
        if (existing.size() > 0) {
            var scheduleDto = toScheduleCreateDto(schedule);

            throw new ValidationException("Tvarkaraštis šiai grupei ir šiam " +
                    "laikotarpiui jau yra sukurtas", "Schedule", "Not unique", scheduleDto.toString());
        } else {
            return scheduleRepository.save(schedule);
        }
    }

    @Test
    public void disableSchedule_ValidScheduleId_ShouldReturnDisabledSchedule() {
        // Arrange
        long scheduleId = 1;
        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(scheduleId);
        existingSchedule.setActive(true);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);

        // Act
        Schedule disabledSchedule = scheduleService.disable(scheduleId);

        // Assert
        assertNotNull(disabledSchedule);
        assertFalse(disabledSchedule.isActive());
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(scheduleRepository, times(1)).save(existingSchedule);
    }

    @Test
    public void disableSchedule_InvalidScheduleId_ShouldReturnNull() {
        // Arrange
        long scheduleId = 1;
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // Act
        Schedule disabledSchedule = scheduleService.disable(scheduleId);

        // Assert
        assertNull(disabledSchedule);
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(scheduleRepository, never()).save(any());
    }

    @Test
    public void enableSchedule_ValidScheduleId_ShouldReturnEnabledSchedule() {
        // Arrange
        long scheduleId = 1;
        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(scheduleId);
        existingSchedule.setActive(false);
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.save(existingSchedule)).thenReturn(existingSchedule);

        // Act
        Schedule enabledSchedule = scheduleService.enable(scheduleId);

        // Assert
        assertNotNull(enabledSchedule);
        assertTrue(enabledSchedule.isActive());
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(scheduleRepository, times(1)).save(existingSchedule);
    }

    @Test
    public void enableSchedule_InvalidScheduleId_ShouldReturnNull() {
        // Arrange
        long scheduleId = 1;
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // Act
        Schedule enabledSchedule = scheduleService.enable(scheduleId);

        // Assert
        assertNull(enabledSchedule);
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(scheduleRepository, never()).save(any());
    }

}