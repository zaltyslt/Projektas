package lt.techin.schedule.programs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProgramControllerTest {
    @Autowired
    private MockMvc mvc;
    @Test
    void testGetProgramsController() throws Exception {
        mvc.perform(get("/api/v1/programs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void testGetProgramController() throws Exception {
//        mvc.perform(get("/api/v1/programs/program/{id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentType(MediaType.APPLICATION_JSON));
//    }

    @Test
    void testCreateProgramController() throws Exception {
        mvc.perform(post("/api/v1/programs/create-program")
                        .content("{\"programName\":\"Program name\",\"description\":\"Description\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateProgramController() throws Exception {
        mvc.perform(patch("/api/v1/programs/update-program/{programId}", "1")
                        .content("{\"programName\":\"Program name\",\"description\":\"Description1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateProgramHoursController() throws Exception {
        mvc.perform(post("/api/v1/programs/create-program-hours")
                        .content("{\"id\":1,\"programName\":\"First program\"," +
                                "\"description\":\"Description\",\"active\":true," +
                                "\"subjectHoursList\":[{\"id\":1,\"subjectName\":\"Subject 1\"," +
                                "\"subject\":1,\"deleted\":false,\"hours\":10},{\"id\":2," +
                                "\"subjectName\":\"Subject 2\",\"subject\":2,\"deleted\":false,\"hours\":10}]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateProgramHoursController() throws Exception {
        mvc.perform(patch("/api/v1/programs/update-hours-program/{programId}", "1")
                        .content("{\"id\":1,\"programName\":\"First program\"," +
                                "\"description\":\"Description2\",\"active\":true," +
                                "\"subjectHoursList\":[{\"id\":1,\"subjectName\":\"Subject 1\"," +
                                "\"subject\":1,\"deleted\":false,\"hours\":10},{\"id\":2," +
                                "\"subjectName\":\"Subject 2\",\"subject\":2,\"deleted\":false,\"hours\":10}]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void testDisableProgramController() throws Exception {
//        mvc.perform(patch("/api/v1/programs/disable-program/{programId}", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentType(MediaType.APPLICATION_JSON));
//    }

    @Test
    void testEnableProgramController() throws Exception {
        mvc.perform(patch("/api/v1/programs/enable-program/{programId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
