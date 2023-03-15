package lt.techin.schedule.teachers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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