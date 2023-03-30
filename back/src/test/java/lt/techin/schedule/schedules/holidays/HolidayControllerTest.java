package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.schedules.Schedule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HolidayControllerTest {

    @MockBean
    HolidayService holidayService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetHolidayController() throws Exception {
        Holiday expectedHoliday = new Holiday();

        expectedHoliday.setId(1L);
        expectedHoliday.setHolidayName("Holiday");
        expectedHoliday.setDate(LocalDate.of(2023, 3, 29));

        Schedule scheduleToAdd = new Schedule();
        scheduleToAdd.setId(1L);

        expectedHoliday.setSchedule(scheduleToAdd);

        when(holidayService.getHolidayById(1L)).thenReturn(expectedHoliday);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schedules/holiday/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                  "id": 1,
                                  "name": "Holiday",
                                  "date": "2023-03-29",
                                  "schedule": {
                                    "id": 1
                                  }
                                }"""
                ));
    }

    @PutMapping(value = "/holidays/update-holiday/{holidayId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable Long holidayId, @RequestBody String holidayName) {
        Holiday updatedHoliday = holidayService.update(holidayId, holidayName);
        return ok(HolidayMapper.toHolidayDto(updatedHoliday));
    }
}
