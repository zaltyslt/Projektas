package lt.techin.schedule.teachers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.exceptions.TeacherException;
import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.teachers.contacts.ContactDto2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(controllers = MockitoExtension.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TeacherControllerActionTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private TeacherServiceDo teacherServiceDo;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherControllerAction teacherControllerAction;

    @Test
    void createTeacherSuccessTest() {
        TeacherDto teacherDto = new TeacherDto(null, "Kitas", "Kitoks", true);
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());
        TeacherDto expected = new TeacherDto(1L, "Kitas", "Kitoks", true);
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());
        Mockito.when(teacherServiceDo.createTeacher(Mockito.any())).thenReturn(ResponseEntity.ok(expected));
        ResponseEntity<TeacherDto> response = teacherControllerAction.createTeacher(teacherDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testCreateTeacherMissingData() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());
        TeacherException exception = assertThrows(TeacherException.class,
                () -> teacherControllerAction.createTeacher(teacherDto));
        assertEquals("Nepakanka duomenų!", exception.getMessage());
        assertEquals(HttpStatus.EXPECTATION_FAILED, exception.getStatus());
    }

    @Test
    void updateTeacherTest() {
        Long teacherId = 1L;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setfName("Kitas");
        teacherDto.setlName("Kitoks");
        teacherDto.setId(teacherId);
        Mockito.when(teacherServiceDo.updateTeacher(Mockito.any(), Mockito.any())).thenReturn(ResponseEntity.ok(teacherDto));
        ResponseEntity<TeacherDto> response = teacherControllerAction.updateTeacherDetails(teacherId, teacherDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherId, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void changeStateTrueTest() {
        Long teacherId = 1L;
        Mockito.when(teacherServiceDo.switchOn(teacherId)).thenReturn(ResponseEntity.ok().build());
        Mockito.when(teacherServiceDo.switchOff(teacherId)).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<Void> responseT = teacherControllerAction.changeState(teacherId, true);
        ResponseEntity<Void> responseF = teacherControllerAction.changeState(teacherId, false);
        assertEquals(HttpStatus.OK, responseT.getStatusCode());
        assertEquals(HttpStatus.OK, responseF.getStatusCode());
    }
}