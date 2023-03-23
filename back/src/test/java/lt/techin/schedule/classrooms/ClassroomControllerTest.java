package lt.techin.schedule.classrooms;

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
public class ClassroomControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testGetClassroomsController() throws Exception {
        mvc.perform(get("/api/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetClassroomController() throws Exception {
        mvc.perform(get("/api/v1/classrooms/classroom/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateClassroomController() throws Exception {
        mvc.perform(post("/api/v1/classrooms/create-classroom")
                        .content("{\"classroomName\":\"1\",\"description\":\"description\"," +
                                "\"active\":true,\"building\":\"AKADEMIJA\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateClassroomController() throws Exception {
        mvc.perform(patch("/api/v1/classrooms/update-classroom/{classroomId}", "1")
                        .content("{\"active\":\"true\",\"classroomName\":\"1\",\"description\":\"description1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDisableClassroomController() throws Exception {
        mvc.perform(patch("/api/v1/classrooms/disable-classroom/{classroomId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testEnableClassroomController() throws Exception {
        mvc.perform(patch("/api/v1/classrooms/enable-classroom/{classroomId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
