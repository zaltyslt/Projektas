package lt.techin.schedule.teachers;


import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerViewTest {

    @Mock
    private TeacherServiceFind teacherFinder;

    @InjectMocks
    private TeacherControllerView teacherController;

    @Test
    public void testGetAllTeachersWithoutActiveParam() {
        // create a list of teachers to be returned by the mock teacherFinder
        List<TeacherDto> teacherList = new ArrayList<>();
        teacherList.add(new TeacherDto(1L, "John1"," Doe", true));
        teacherList.add(new TeacherDto(2L, "John2"," Doe", false));
        teacherList.add(new TeacherDto(3L, "John3"," Doe", true));
        teacherList.add(new TeacherDto(4L, "John4"," Doe", false));
        teacherList.add(new TeacherDto(5L, "John5"," Doe", true));

        Mockito.when(teacherFinder.getAllTeachers()).thenReturn(teacherList);

        // perform the GET request to the controller without the "active" param
        List<TeacherDto> response = teacherController.getAllTeachers(Optional.empty());

        // verify that the response contains the expected teachers
        assertEquals(teacherList, response);
    }

    @Test
    public void testGetAllTeachersWithActiveParam() {
        // create a list of teachers to be returned by the mock teacherFinder
        List<TeacherDto> teacherList = new ArrayList<>();
        teacherList.add(new TeacherDto(1L, "John1"," Doe", true));
        teacherList.add(new TeacherDto(3L, "John3"," Doe", true));
        teacherList.add(new TeacherDto(5L, "John5"," Doe", true));

        Mockito.when(teacherFinder.getTeachersByActiveStatus(true)).thenReturn(teacherList);

        // perform the GET request to the controller with the "active=true" param
        List<TeacherDto> response = teacherController.getAllTeachers(Optional.of(true));

        // verify that the response contains the expected teachers
        assertEquals(teacherList, response);
    } @Test
    public void testGetTeacherById() {
      TeacherDto teacherDto = new TeacherDto(1L,"Name", "LastName", true);

        Mockito.when(teacherFinder.getTeacherById(1L)).thenReturn(teacherDto);
        Mockito.when(teacherFinder.getTeacherById(2L)).thenReturn(new TeacherDto());

        // perform the GET request to the controller with the "active=true" param
        ResponseEntity<TeacherDto> response = teacherController.getTeacherById(1L);
        ResponseEntity<TeacherDto> response2 = teacherController.getTeacherById(2L);

        // verify that the response contains the expected teachers
        assertEquals(ResponseEntity.ok(teacherDto), response);
        assertEquals(ResponseEntity.notFound().build(), response2);

    }

    @Test
    void getActiveTeacherSubjectsDtoFull() {
        HashSet<TeacherSubjectsDto> set1 = new HashSet<>(Arrays.asList(new TeacherSubjectsDto()));

        Mockito.when(teacherFinder.getMiniSubjects()).thenReturn(set1);
       // perform the GET request to the controller with the "active=true" param
        ResponseEntity<Set<TeacherSubjectsDto>> response = teacherController.getActiveTeacherSubjectsDto();
       // verify that the response contains the expected teachers
        assertEquals(ResponseEntity.ok(set1), response);


    }
    @Test
    void getActiveTeacherSubjectsDtoEmpty() {
        HashSet<TeacherSubjectsDto> set1 = new HashSet<>();

        Mockito.when(teacherFinder.getMiniSubjects()).thenReturn(set1);
        // perform the GET request to the controller with the "active=true" param
        ResponseEntity<Set<TeacherSubjectsDto>> response = teacherController.getActiveTeacherSubjectsDto();
        // verify that the response contains the expected teachers
        assertEquals(ResponseEntity.ok(set1), response);


    }
}
