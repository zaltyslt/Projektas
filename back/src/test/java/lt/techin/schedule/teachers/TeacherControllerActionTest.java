package lt.techin.schedule.teachers;
import java.util.Objects;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(controllers = TeacherControllerAction.class)
@SpringBootTest
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
        // Arrange
        TeacherDto teacherDto = new TeacherDto(null,"Kitas", "Kitoks", true );
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());

        TeacherDto expected = new TeacherDto(1L,"Kitas", "Kitoks", true );
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());

        Mockito.when(teacherServiceDo.createTeacher(Mockito.any())).thenReturn(ResponseEntity.ok(expected));

        // Act
        ResponseEntity<TeacherDto> response = teacherControllerAction.createTeacher(teacherDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
//    @Test
//    public void testMyMethod() throws Exception {
//        // Define the parameters to be passed
////        String param1 = "value1";
////        String param2 = "value2";
//
//        TeacherDto teacherDto = new TeacherDto(null,"Kitas", "Kitoks", true );
//        teacherDto.setSelectedShift(new ShiftDto());
//        teacherDto.setContacts(new ContactDto2());
//
//        TeacherDto expected = new TeacherDto(1L,"Kitas", "Kitoks", true );
//        teacherDto.setSelectedShift(new ShiftDto());
//        teacherDto.setContacts(new ContactDto2());
//
//        Mockito.when(teacherServiceDo.createTeacher(Mockito.any())).thenReturn(ResponseEntity.ok(expected));
//
//        // Make the GET request to the controller method
//        var aa = objectMapper.writeValueAsString(teacherDto);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/create")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(aa))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.fName").value("Kitas"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lName").value("Kitoks"));
//
//    }
    @Test
    void testCreateTeacherMissingData() {
        // Arrange
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());

        TeacherException exception = assertThrows(TeacherException.class,
                () -> teacherControllerAction.createTeacher(teacherDto));
        assertEquals("Nepakanka duomenų!", exception.getMessage());
        assertEquals(HttpStatus.EXPECTATION_FAILED, exception.getStatus());
    }

    @Test
    void testData() {
    }

    @Test
    void updateTeacherTest() {
        // Given
        Long teacherId = 1L;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setfName("Kitas");
        teacherDto.setlName("Kitoks");
        teacherDto.setId(teacherId);

        Mockito.when(teacherServiceDo.updateTeacher(Mockito.any(),Mockito.any())).thenReturn(ResponseEntity.ok(teacherDto));

        // When
        ResponseEntity<TeacherDto> response = teacherControllerAction.updateTeacherDetails(teacherId, teacherDto);

        // Then
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(teacherId, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void changeStateTrueTest() {
        Long teacherId = 1L;
        //.header(HttpHeaders.ACCEPT, "Restored").build();
        Mockito.when(teacherServiceDo.switchOn(teacherId)).thenReturn(ResponseEntity.ok().build());
        Mockito.when(teacherServiceDo.switchOff(teacherId)).thenReturn(ResponseEntity.ok().build());

        // When
        ResponseEntity<Void> responseT = teacherControllerAction.changeState(teacherId,true);
        ResponseEntity<Void> responseF = teacherControllerAction.changeState(teacherId,false);

        // Then
        assertEquals(HttpStatus.OK, responseT.getStatusCode());
        assertEquals(HttpStatus.OK, responseF.getStatusCode());

    }

    @Test
    void deleteTeacherDetails() {
    }
}