package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class HolidayServiceTest {
    @MockBean
    private HolidayRepository holidayRepository;
    @MockBean
    private ScheduleRepository scheduleRepository;

    private Holiday testHoliday;
    private Schedule testSchedule;

    @BeforeEach
    public void setUp() {
        testHoliday = new Holiday();
        testHoliday.setHolidayName("Christmas");
        testSchedule = new Schedule();
        testSchedule.setId(1L);
        testSchedule.setSemester("Spring");
    }

    @Test
    public void testGetAll() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(testHoliday);
        when(holidayRepository.findAll()).thenReturn(holidays);
    }

    @Test
    public void testGetById() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(testHoliday);
        when(holidayRepository.findByScheduleId(1L)).thenReturn(holidays);
    }

    @Test
    public void testCreateWithNonExistingSchedule() {
        when(scheduleRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    }
}
