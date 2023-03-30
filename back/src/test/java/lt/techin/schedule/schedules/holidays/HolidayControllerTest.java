package lt.techin.schedule.schedules.holidays;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.schedules.Schedule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@SqlGroup({
//        @Sql(value = "classpath:init/schedule.sql", executionPhase = BEFORE_TEST_METHOD)
//})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
@AutoConfigureMockMvc
public class HolidayControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setUp() {
//        Schedule testSchedule = new Schedule();
//        testSchedule.setId(1L);
//    }

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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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

    @Test
    void testDeleteHolidayController() throws Exception {
        Long id = 1L;

        when(holidayService.deleteHoliday(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/schedules/holidays/delete-holiday/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
