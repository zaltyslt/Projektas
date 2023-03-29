package lt.techin.schedule.schedules;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "classpath:init/schedule.sql", executionPhase = BEFORE_TEST_METHOD)
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScheduleControllerTest {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    MockMvc mvc;

    @Test
    @Order(1)
    void testGetSchedulesController() throws Exception {
        mvc.perform(get("/api/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    void testCreateScheduleController() throws Exception {
        String requestBody = "{\"schoolYear\":\"2022-2023\",\"semester\":\"spring\"," +
                "\"dateFrom\":\"2023-02-01\",\"dateUntil\":\"2023-06-30\"}";
        Long groupId = 1L;
        mvc.perform(post("/api/v1/schedules/create-schedule")
                        .param("groupId", groupId.toString())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    void testGetScheduleController() throws Exception {
        mvc.perform(get("/api/v1/schedules/schedule/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void testDisableScheduleController() throws Exception {
        mvc.perform(patch("/api/v1/schedules/disable-schedule/{scheduleId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testEnableScheduleController() throws Exception {
        mvc.perform(patch("/api/v1/schedules/enable-schedule/{scheduleId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteSchedule() throws Exception {
        mvc.perform(delete("/api/v1/schedules/delete-schedule/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(delete("/api/v1/schedules/delete-schedule/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}