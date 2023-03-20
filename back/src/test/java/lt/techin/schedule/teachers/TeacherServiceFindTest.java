package lt.techin.schedule.teachers;

import lt.techin.schedule.module.Module;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherServiceFindTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private TeacherServiceFind teacherServiceFind;
    List<Teacher> teachers = new ArrayList<>();
    Teacher teacher1;
    Teacher teacher2;
    Teacher teacher3;
    Teacher teacher4;

    @BeforeEach
    void setup(){
      teacher1 = new Teacher(1L, new ArrayList<>(),new HashSet<Subject>(), new Shift(),
                "John", "Doe", true,
                LocalDateTime.of(2023, 3, 1, 10, 40, 0),
                LocalDateTime.of(2023, 3, 1, 10, 40, 0 ),
                10);

        teacher2 = new Teacher(2L, new ArrayList<>(),new HashSet<Subject>(), new Shift(),
                "Jane", "Doe", true,
                LocalDateTime.of(2023, 3, 1, 10, 30, 0),
                LocalDateTime.of(2023, 3, 1, 10, 30, 0 ),
                20);
        teacher3 = new Teacher(3L, new ArrayList<>(),new HashSet<Subject>(), new Shift(),
                "Bob", "Smith", false,
                LocalDateTime.of(2023, 3, 1, 10, 20, 0),
                LocalDateTime.of(2023, 3, 1, 10, 20, 0 ),
                30);
        teacher4 = new Teacher(4L, new ArrayList<>(),new HashSet<Subject>(), new Shift(),
                "Bob", "Smith", false,
                LocalDateTime.of(2023, 3, 1, 10, 10, 0),
                LocalDateTime.of(2023, 3, 1, 10, 10, 0 ),
                40);

        teachers.addAll(Arrays.asList(teacher1, teacher2, teacher3, teacher4));
    }
    @Test
    void contextLoads(ApplicationContext context) {
        assertNotNull(context);
    }


    @Test
    void getAllTeachersTest() {
        // given
        Mockito.when(teacherRepository.findAll()).thenReturn( teachers);
        // when
        List<TeacherDto> teacherDtos = teacherServiceFind.getAllTeachers();
        // then
        assertEquals(4, teacherDtos.size());
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 1L));
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 2L));
        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 3L));
    }

    @Test
    void getTeacherById() {
        // given
       Mockito.when(teacherRepository.findById(2L)).thenReturn( Optional.of(teachers.get(1)));
       Mockito.when(teacherRepository.findById(5L)).thenReturn( Optional.empty());

        // when
        TeacherDto teacher1 = teacherServiceFind.getTeacherById(2L);
        TeacherDto teacher2 = teacherServiceFind.getTeacherById(5L);

        // then
        assertEquals(teacher1.getId(), 2L);
        assertEquals(teacher2.getId(), null);
//        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 1L));
//        assertTrue(teacherDtos.stream().anyMatch(t -> t.getId() == 2L));
    }

    @Test
    void getTeachersByActiveStatusTest() {

        List<Teacher> active = teachers.subList(0,2);
        List<Teacher> passive = teachers.subList(2,4);
        List<Teacher> empty = new ArrayList<Teacher>();


        Mockito.when(teacherRepository.findByisActive(true)).thenReturn( active);
        Mockito.when(teacherRepository.findByisActive(false)).thenReturn( passive);

        // when
        List<TeacherDto> teachersActive= teacherServiceFind.getTeachersByActiveStatus(true);
        List<TeacherDto> teachersPassive = teacherServiceFind.getTeachersByActiveStatus(false);

        List<Long> idsActive = teachersActive.stream().map(t->t.getId()).toList();
        List<Long> idsPasive = teachersPassive.stream().map(t->t.getId()).toList();

       assertEquals(2, teachersActive.size(), "Size should be 2");
       assertEquals(2, teachersPassive.size(),"Size should be 2");

        assertEquals(1L, idsActive.get(0),"a");
        assertEquals(2L, idsActive.get(1),"b");
        assertEquals(3L, idsPasive.get(0),"a");
        assertEquals(4L, idsPasive.get(1),"b");
   }
    @Test
    void getTeachersByActiveStatusEmptyTest() {
        List<Teacher> empty = new ArrayList<Teacher>();
        Mockito.when(teacherRepository.findByisActive(false)).thenReturn( empty);
        // when
        List<TeacherDto> teachersEmpty = teacherServiceFind.getTeachersByActiveStatus(false);
       //then
        assertEquals(0, teachersEmpty.size(), "Size should be 0");
    }
    @Test
    void getMiniSubjectsTest() {
        List<Subject> subjects = new ArrayList<>(Arrays.asList(
                new Subject(1L, "Pirmas","", new Module(), new HashSet<>(),false),
                new Subject(2L, "Pirmas","", new Module(), new HashSet<>(),true),
                new Subject(3L, "Pirmas","", new Module(), new HashSet<>(),false),
                new Subject(4L, "Pirmas","", new Module(), new HashSet<>(),true)
        ));

        Mockito.when(subjectRepository.findAll()).thenReturn( subjects);
        // when
        Set<TeacherSubjectsDto> subjectDtoList = teacherServiceFind.getMiniSubjects();
        //then
        assertEquals(2, subjectDtoList.size(), "Size should be 2");
    }
    @Test
    void findTeachersBySubjectsIdTest(){ //and Shift Id
        List<Subject> subjects = new ArrayList<>(Arrays.asList(
                new Subject(1L, "Pirmas","", new Module(), new HashSet<>(),false),
                new Subject(2L, "Pirmas","", new Module(), new HashSet<>(),true),
                new Subject(3L, "Pirmas","", new Module(), new HashSet<>(),false),
                new Subject(4L, "Pirmas","", new Module(), new HashSet<>(),true)
        ));
         var sub1 = new Subject(1L, "Pirmas","", new Module(), new HashSet<>(),false);
         var sub2 = new Subject(2L, "Antras","", new Module(), new HashSet<>(),true);

        Shift shift1 = new Shift();
        shift1.setId(1L);
        Shift shift2 = new Shift();
        shift2.setId(2L);
        teacher1.setSubjects(new HashSet<>(Arrays.asList(subjects.get(0),subjects.get(1))));
        teacher1.setSubjects(new HashSet<>(Arrays.asList(sub1)));
        teacher1.setShift(shift1);
        teacher2.setSubjects(new HashSet<>(Arrays.asList(subjects.get(1),subjects.get(2))));
        teacher2.setSubjects(new HashSet<>(Arrays.asList(sub1, sub2)));
        teacher2.setShift(shift2);
        teacher3.setSubjects(new HashSet<>(Arrays.asList(subjects.get(2),subjects.get(3))));
        teacher3.setShift(shift1);
        teacher4.setSubjects(new HashSet<>(Arrays.asList(subjects.get(3),subjects.get(0))));
        teacher4.setShift(shift2);

        Mockito.when(teacherRepository.findTeachersBySubjectsId(2L))
                .thenReturn( Arrays.asList(teacher1, teacher2));
        Mockito.when(teacherRepository.findTeachersBySubjectsId(4L))
                .thenReturn( Arrays.asList(teacher3, teacher4));
        //when
        List<Teacher> list1 = teacherServiceFind.findTeachersBySubjectsId(2L, 1L);
        List<Teacher> list2 = teacherServiceFind.findTeachersBySubjectsId(4L, 2L);

        assertEquals(1,list1.size(),"Size should be 1 (a)");
        assertEquals(1,list2.size(),"Size should be 1 (b)");
    }
}
