package lt.techin.schedule.schedules.holidays;

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
    public void testCreateHoliday() {
        HolidayPlanDto holidayPlanDto = new HolidayPlanDto();
        holidayPlanDto.setDateFrom(LocalDate.of(2023, 4, 1));
        holidayPlanDto.setDateUntil(LocalDate.of(2023, 4, 15));
        assertEquals("Toks tvarkaraštis neegzistuoja.", holidayService.create(new HolidayPlanDto(),1L));
    }
}
