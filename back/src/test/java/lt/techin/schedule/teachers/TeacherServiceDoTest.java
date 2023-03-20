package lt.techin.schedule.teachers;

import lt.techin.schedule.exceptions.TeacherException;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.teachers.contacts.Contact;
import lt.techin.schedule.teachers.contacts.ContactService;
import lt.techin.schedule.teachers.contacts.ContactType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;



@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherServiceDoTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private ShiftRepository shiftRepository;
    @Mock
    private ContactService contactService;
    @InjectMocks
    private TeacherServiceDo teacherServiceDo;

//    @Before
//    public void setUp() {
//        }


    @Test
    void isNotDuplicateTest_NoDuplicates() {
        //setUp
        Shift shift = new Shift("Pirma", LessonTime.FIRST.toString(), LessonTime.FIRST.toString(), true, 1, 1);
        shift.setId(1L);
        Teacher teacherToCreate = new Teacher(null, "John", "Doe", true);
        teacherToCreate.setShift(shift);

        Teacher createdTeacher = new Teacher(1L, "John", "Doe", true);

        TeacherDto teacherDto = TeacherMapper.teacherToDto(teacherToCreate);
        //When
        Mockito.when(teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(createdTeacher);
        Mockito.when(shiftRepository.findById(1L)).thenReturn(Optional.of(shift));
        //Then
        ResponseEntity<TeacherDto> result = teacherServiceDo.createTeacher(teacherDto);
        assertEquals(HttpStatus.OK, result.getStatusCode(), "");

    }

    @Test
    void isNotDuplicateTest_DuplicateThrows() {
        //setUp
//        Shift shift = new Shift("Pirma", LessonTime.FIRST.toString(),LessonTime.FIRST.toString(),true,1,1);
//        shift.setId(1L);
        Contact phone = new Contact();
        phone.setContactType(ContactType.PHONE_NUMBER);
        phone.setContactValue("888888888");
        Contact mail = new Contact();
        mail.setContactType(ContactType.DIRECT_EMAIL);
        mail.setContactValue("mail@mail.lt");
        List<Contact> contacts = new ArrayList<>(Arrays.asList(phone, mail));

        Teacher teacherToCreate = new Teacher(null, "John", "Doe", true);
        teacherToCreate.setContacts(contacts);

        Teacher createdTeacher = new Teacher(1L, "John", "Doe", true);
        createdTeacher.setContacts(contacts);


        //When
        Mockito.when(teacherRepository.findTeachersByHashCode(Mockito.any())).thenReturn(new HashSet<Teacher>(List.of(createdTeacher)));
        assertThrows(TeacherException.class, () -> teacherServiceDo.isNotDuplicate(teacherToCreate));
        //Then
    }

    @Test
    public void deleteTeacherById_success() {
        // Act
        ResponseEntity<Void> response = teacherServiceDo.deleteTeacherById(1L);
        // Assert
        verify(teacherRepository).deleteById(1L);
        Assertions.assertTrue(true, "");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody(), "");
    }

    @Test
    public void deleteTeacherById_notFound() {
        // Arrange
        doThrow(new EmptyResultDataAccessException(1)).when(teacherRepository).deleteById(1L);
        // Act
        ResponseEntity<Void> response = teacherServiceDo.deleteTeacherById(1L);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void switchOff_success() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setActive(true);
        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Act
        ResponseEntity<Void> response = teacherServiceDo.switchOff(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.ACCEPT)).contains("Deleted"));
        assertEquals(false, teacher.getActive());
    }

    @Test
    public void switchOff_teacherNotFound() {
        // Arrange

        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        TeacherException exception = assertThrows(TeacherException.class, () -> teacherServiceDo.switchOff(1L));
        assertEquals("Mokytojas nerastas !", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void switchOff_failure() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setActive(true);
        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        Mockito.when(teacherRepository.save(teacher)).thenThrow(DataIntegrityViolationException.class);

        // Act and Assert
        TeacherException exception = assertThrows(TeacherException.class, () -> teacherServiceDo.switchOff(1L));
        assertEquals("Ištrinti nepavyko !", exception.getMessage(),"a");
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus(),"b");

    }
    @Test
    public void switchOn_success() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setActive(false);
        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Act
        ResponseEntity<Void> response = teacherServiceDo.switchOn(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.ACCEPT)).contains("Restored"));
        assertEquals(true, teacher.getActive());
    }

    @Test
    public void switchOn_teacherNotFound() {
        // Arrange

        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        TeacherException exception = assertThrows(TeacherException.class, () -> teacherServiceDo.switchOn(1L));
        assertEquals("Mokytojas nerastas !", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void switchOn_failure() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setActive(false);
        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        Mockito.when(teacherRepository.save(teacher)).thenThrow(DataIntegrityViolationException.class);

        // Act and Assert
        TeacherException exception = assertThrows(TeacherException.class, () -> teacherServiceDo.switchOn(1L));
        assertEquals("Atstatyti nepavyko !", exception.getMessage(),"a");
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus(),"b");

    }
}