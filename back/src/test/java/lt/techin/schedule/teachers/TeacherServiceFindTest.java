package lt.techin.schedule.teachers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeacherServiceFindTest {
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherServiceFind teacherService;
    List<Teacher> teachers = new ArrayList<>();

    @BeforeEach
    void setUp() {

        teachers.add(new Teacher(1L, "John", "Doe", true));
        teachers.add(new Teacher(2L, "Jane", "Doe", false));
        teachers.add(new Teacher(3L, "Bob", "Smith", true));
    }

    @Test
    void getAllTeachers() {
        // given
        Mockito.when(teacherRepository.findAll()).thenReturn( teachers);
        // when
       List<TeacherDto> teacherDtos = teacherService.getAllTeachers();
        // then
        assertEquals(3, teacherDtos.size());
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 1L));
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 2L));
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 3L));
    }
    @Test
    void getTeacherByIdTest() {
        // given
        Mockito.when(teacherRepository.findById(1L)).thenReturn( Optional.of(teachers.get(0)));
        Mockito.when(teacherRepository.findById(5L)).thenReturn( Optional.empty());
        // when
        TeacherDto teacherDto1 = teacherService.getTeacherById(1L);
        TeacherDto teacherDto2 = teacherService.getTeacherById(5L);
        // then
        assertEquals(TeacherMapper.teacherToDto(teachers.get(0)), teacherDto1);
//        assertEquals(Optional.empty(), teacherDto2);

    }

    @Test
    void getTeachersByActiveStatus() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1L, "John", "Zakas", true));
        teachers.add(new Teacher(2L, "Jane", "Meka", false));
        teachers.add(new Teacher(3L, "Bob", "Bakas", true));
        Mockito.when(teacherRepository.findByisActive(true)).thenReturn( teachers);
        Mockito.when(teacherRepository.findByisActive(false)).thenReturn( teachers);

        // when
        List<TeacherDto> teachersResultT = teacherService.getTeachersByActiveStatus(true);
        List<TeacherDto> teachersResultF = teacherService.getTeachersByActiveStatus(false);

        List<Long> ids = teachersResultT.stream().map(t->t.getId()).toList();

       assertEquals(2, teachersResultT.size(), "Size should be 2");
       assertEquals(1, teachersResultF.size(),"Size should be 1");

        assertEquals(3L, ids.get(0),"a");
        assertEquals(1L, ids.get(1),"b");

    }



    @Test
    void getTeachersByName() {
    }

    @Test
    void getTeachersBySubjects() {
    }

    @Test
    void testGetTeachersByActiveStatus() {
    }

    @Test
    void getMiniSubjects() {
    }
}