//package lt.techin.schedule.teachers;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class TeacherServiceFindTest {
//    @InjectMocks
//    private TeacherServiceFind teacherService;
//
//    @Mock
//    private TeacherRepository teacherRepository;
//    @Test
//    void getAllTeachers() {
//        // given
//        List<Teacher> teachers = new ArrayList<>();
//        teachers.add(new Teacher(1L, "John", "Doe", true));
//        teachers.add(new Teacher(2L, "Jane", "Doe", false));
//        teachers.add(new Teacher(3L, "Bob", "Smith", true));
//        Mockito.when(teacherRepository.findAll()).thenReturn( teachers);
//
//        // when
//        Set<TeacherDto> teacherDtos = teacherService.getAllTeachers();
//
//        // then
//        assertEquals(3, teacherDtos.size());
//        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 1L));
//        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 2L));
//        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 3L));
//
//
//    }
//
//    @Test
//    void getTeachersByActiveStatus() {
//        List<Teacher> teachers = new ArrayList<>();
//        teachers.add(new Teacher(1L, "John", "Zakas", true));
//        teachers.add(new Teacher(2L, "Jane", "Meka", false));
//        teachers.add(new Teacher(3L, "Bob", "Bakas", true));
//        Mockito.when(teacherRepository.findByisActive(true)).thenReturn( teachers);
//        Mockito.when(teacherRepository.findByisActive(false)).thenReturn( teachers);
//
//        // when
//        Set<TeacherDto> teachersResultT = teacherService.getTeachersByActiveStatus(true);
//        Set<TeacherDto> teachersResultF = teacherService.getTeachersByActiveStatus(false);
//
//        List<Long> ids = teachersResultT.stream().map(t->t.getId()).toList();
//
//       assertEquals(2, teachersResultT.size(), "Size should be 2");
//       assertEquals(1, teachersResultF.size(),"Size should be 1");
//
//        assertEquals(3L, ids.get(0),"a");
//        assertEquals(1L, ids.get(1),"b");
//
//
//
//    }
//}