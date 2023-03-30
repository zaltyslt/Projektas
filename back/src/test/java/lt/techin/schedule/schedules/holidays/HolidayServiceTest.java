package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private HolidayService holidayService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHoliday() {
        Holiday holiday = new Holiday();
        holiday.setId(1L);
        holiday.setHolidayName("Holiday");

        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));
        assertEquals(holiday, holidayService.getHolidayById(1L));
    }

    @Test
    public void testDeleteHoliday() {
        Long holidayId = 15L;
        Boolean result = holidayService.deleteHoliday(holidayId);
        assertTrue(result);
        verify(holidayRepository, times(1)).deleteById(holidayId);
    }

    @Test
    public void deleteHoliday_ShouldReturnFalse_WhenIllegalArgumentExceptionIsThrown() {
        Long holidayId = 15L;
        doThrow(new IllegalArgumentException()).when(holidayRepository).deleteById(holidayId);
        Boolean result = holidayService.deleteHoliday(holidayId);
        assertFalse(result);
        verify(holidayRepository, times(1)).deleteById(holidayId);
    }

    @Test
    public void testUpdateHoliday() {
        Holiday holiday = new Holiday();
        holiday.setId(10L);
        holiday.setHolidayName("Holiday");

        when(holidayRepository.findById(10L)).thenReturn(Optional.of(holiday));
        when(holidayRepository.save(holiday)).thenReturn(holiday);

        Holiday updatedHoliday = holidayService.update(holiday.getId(), "Not a Holiday");
        System.out.println(updatedHoliday);

        assertEquals("Not a Holiday", updatedHoliday.getHolidayName());
        assertEquals(holiday.getId(), updatedHoliday.getId());
    }

    @Test
    public void testCreateHolidayReturnNull() {
        assertEquals("Toks tvarkaraštis neegzistuoja.", holidayService.create(new HolidayPlanDto(),1L));
    }

    @Test
    public void testCreateHolidayWrongDataRange() {
        Schedule schedule = new Schedule();
        schedule.setDateFrom(LocalDate.of(2023, 4, 2));
        schedule.setDateUntil(LocalDate.of(2023, 4, 14));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        HolidayPlanDto holidayPlanDto = new HolidayPlanDto();
        holidayPlanDto.setDateFrom(LocalDate.of(2023, 4, 1));
        holidayPlanDto.setDateUntil(LocalDate.of(2023, 4, 15));
        assertEquals("Atostogų datos nesutampa su tvarkaraščio datomis. Tvarkaraštis prasideda/pasibaigia: 2023-04-02/2023-04-14. " +
               "Atostogos prasideda/pasibaigia: 2023-04-01/2023-04-15." , holidayService.create(holidayPlanDto,1L));
    }

    @Test
    public void testCreateHoidayHoidaysAlreadyInRange() {
        Schedule schedule = new Schedule();
        schedule.setDateFrom(LocalDate.of(2023, 3, 1));
        schedule.setDateUntil(LocalDate.of(2023, 5, 1));

        Holiday holiday = new Holiday();
        holiday.setHolidayName("Holiday");
        holiday.setDate(LocalDate.of(2023, 4, 12));

        schedule.setHolidays(Set.of(holiday));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        HolidayPlanDto holidayPlanDto = new HolidayPlanDto();
        holidayPlanDto.setDateFrom(LocalDate.of(2023, 4, 1));
        holidayPlanDto.setDateUntil(LocalDate.of(2023, 4, 15));
        assertEquals("Tvarkaraštyje esančios atostogos sutampa su planuojamomis atostogomis.", holidayService.create(holidayPlanDto,1L));
    }

    @Test
    public void testCreateHolidaySuccessful() {
        Schedule schedule = new Schedule();
        schedule.setDateFrom(LocalDate.of(2023, 3, 1));
        schedule.setDateUntil(LocalDate.of(2023, 5, 1));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        HolidayPlanDto holidayPlanDto = new HolidayPlanDto();
        holidayPlanDto.setDateFrom(LocalDate.of(2023, 4, 1));
        holidayPlanDto.setDateUntil(LocalDate.of(2023, 4, 15));
        assertEquals("", holidayService.create(holidayPlanDto,1L));
    }
}
