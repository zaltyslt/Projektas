package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.schedules.holidays.HolidayRepository;
//import lt.techin.schedule.schedules.holidays.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HolidayServiceTest {

    @Autowired
//    private HolidayService holidayService;

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
//        testHoliday.setDateFrom(LocalDate.parse("2022-12-25"));
//        testHoliday.setDateUntil(LocalDate.parse("2023-01-02"));

        testSchedule = new Schedule();
        testSchedule.setId(1L);
        testSchedule.setSemester("Spring");
    }

    @Test
    public void testGetAll() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(testHoliday);
        when(holidayRepository.findAll()).thenReturn(holidays);
//        assertEquals(holidays, holidayService.getAll());
    }

    @Test
    public void testGetById() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(testHoliday);
        when(holidayRepository.findByScheduleId(1L)).thenReturn(holidays);
//        assertEquals(holidays, holidayService.getById(1L));
    }

    @Test
    public void testCreateWithExistingSchedule() {
        when(scheduleRepository.findById(1L)).thenReturn(java.util.Optional.of(testSchedule));
        when(holidayRepository.save(testHoliday)).thenReturn(testHoliday);
        Holiday testHoliday1 = new Holiday();
        testHoliday1.setHolidayName("Easter");
//        testHoliday1.setDateFrom(LocalDate.parse("2022-12-25"));
//        testHoliday1.setDateUntil(LocalDate.parse("2023-01-02"));
//        when(holidayService.create(testHoliday1, 1L)).thenReturn(testHoliday1);
        assertEquals(1, testSchedule.getHolidays().size());
    }

    @Test
    public void testCreateWithNonExistingSchedule() {
        when(scheduleRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//        assertEquals(null, holidayService.create(testHoliday, 1L));
    }

}
