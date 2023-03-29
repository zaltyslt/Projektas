package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SqlGroup({
        @Sql(value = "classpath:init/schedule.sql", executionPhase = BEFORE_TEST_METHOD)
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HolidayControllerTest {
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        Schedule testSchedule = new Schedule();
        testSchedule.setId(1L);
    }

    @Test
    @Order(1)
    void testGetHolidaysController() throws Exception {
        mvc.perform(get("/api/v1/schedules/holidays")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetHolidayController() throws Exception {
        mvc.perform(get("/api/v1/schedules/holidays/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateHolidayController() throws Exception {
        String requestBody = ("{\"id\":1,\"name\":\"Holiday name\",\"dateFrom\":\"2023-03-01\"," +
                "\"dateUntil\":\"2023-03-31\"}");
        Long scheduleId = 1L;
        mvc.perform(post("/api/v1/schedules/create-schedule/{id}")
                        .param("scheduleId", scheduleId.toString())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
