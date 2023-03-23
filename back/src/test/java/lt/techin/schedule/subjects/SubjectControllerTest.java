package lt.techin.schedule.subjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectController;
import lt.techin.schedule.subject.SubjectDto;
import lt.techin.schedule.subject.SubjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SubjectControllerTest {

    @MockBean
    SubjectService subjectService;

    @InjectMocks
    SubjectController subjectController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetSubjects() throws Exception {
        Subject subject1 = new Subject();
        subject1.setId(1l);
        subject1.setName("Subject1");
        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Subject2");
        List<Subject> subjectList = Arrays.asList(subject1, subject2);
        when(subjectService.getAll()).thenReturn(subjectList);

        mockMvc.perform(get("/api/v1/subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Subject1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Subject2"));
    }

    @Test
    public void testGetDeletedSubjects() throws Exception {
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject1 = new Subject(1L, "Subject1", "Description1", new Module(), classroomSet, true);
        Subject subject2 = new Subject(2L, "Subject2", "Description2", new Module(), classroomSet, true);

        List<Subject> deletedSubjects = List.of(subject1, subject2);
        when(subjectService.getAllDeleted()).thenReturn(deletedSubjects);

        mockMvc.perform(get("/api/v1/subjects/deleted").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Subject1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Subject2"))
                .andExpect(jsonPath("$[0].deleted").value(true))
                .andExpect(jsonPath("$[1].deleted").value(true));
    }

    @Test
    public void testCreateSubject() throws Exception {
        Module module = new Module();
        Set<Classroom> classroomSet = new HashSet<>();

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setDeleted(false);
        subjectDto.setName("Subject1");
        subjectDto.setDescription("Description1");
        subjectDto.setModule(module);
        subjectDto.setClassRooms(classroomSet);

        Subject createdSubject = new Subject(1L, "Subject1", "Description1", module, classroomSet, false);
        when(subjectService.create(any(Subject.class))).thenReturn(createdSubject);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(subjectDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/subjects").contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Subject1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.deleted").value(false));
    }
}