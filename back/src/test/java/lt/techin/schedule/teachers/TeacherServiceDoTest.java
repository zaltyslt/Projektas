package lt.techin.schedule.teachers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.contacts.ContactDto;
import lt.techin.schedule.teachers.contacts.ContactService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TeacherServiceDoTest {
    @InjectMocks
    private TeacherServiceDo teacherServiceDo;
    @Mock
    private TeacherRepository teacherRepository;

//    @Before
//    public void setUp() {
//        }



    @Test
    void isNotDuplicate() {
        Set<Teacher> teachers = new HashSet<>();

        Teacher t1 = new Teacher(1L, "John", "Doe", true);
        Teacher t2 = new Teacher(2L, "John", "Doe", true);
        t2.setNickName("two");
       teachers.addAll(Arrays.asList(t1,t2));

        int teacherHash = Objects.hash("John".toLowerCase(), "Doe".toLowerCase());



        Mockito.when(teacherRepository.findTeachersByHashCode(teacherHash)).thenReturn( teachers);
        t1.setId(null);
//       boolean aa =teacherServiceDo.isNotDuplicate(t1);
//

//        // when
//        Set<TeacherDto> teacherDtos = teacherService.getAllTeachers();
//        Teacher t1 = new Teacher()
    }
}