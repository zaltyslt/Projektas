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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

//@WebMvcTest(controllers = TeacherControllerAction.class)
@WebMvcTest(controllers = MockitoExtension.class)
//@SpringBootTest
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
        TeacherDto teacherDto = new TeacherDto(null, "Kitas", "Kitoks", true);
        teacherDto.setSelectedShift(new ShiftDto());
        teacherDto.setContacts(new ContactDto2());

        TeacherDto expected = new TeacherDto(1L, "Kitas", "Kitoks", true);
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
//    public void teacherCreateTest() throws Exception {
//        ShiftDto shift = new ShiftDto();
//        shift.setId(1L);
//        shift.setStartIntEnum(1);
//        shift.setEndIntEnum(1);
//        shift.setActive(true);
//        TeacherDto teacherDto = new TeacherDto(null, "Kitas", "Kitoks", true);
//        teacherDto.setSelectedShift(shift);
//        teacherDto.setContacts(new ContactDto2());
//
//        TeacherDto expected = new TeacherDto(1L, "Kitas", "Kitoks", true);
//        expected.setSelectedShift(shift);
//        expected.setContacts(new ContactDto2());
//
////        TeacherServiceDo teacherServiceDo = Mockito.mock(TeacherServiceDo.class);
////        Mockito.when(teacherServiceDo.createTeacher(Mockito.any(TeacherDto.class)))
////                .thenReturn(ResponseEntity.ok(expected));
////        TeacherControllerAction teacherController = new TeacherControllerAction(teacherServiceDo);
//
//        mockMvc.perform(post("/api/v1/teachers/create")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(teacherDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.fName").value("Kitas"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lName").value("Kitoks"));
//
//    }
//    @PutMapping("/update")
//    public ResponseEntity<TeacherDto> updateTeacherDetails(@RequestParam("tid") Long teacherId, @RequestBody TeacherDto teacherDto) {
//        return teacherServiceDo.updateTeacher(teacherId, teacherDto);
//    }
//@Test
//public void teacherUpdateTest() throws Exception {
//
//    ShiftDto shift = new ShiftDto();
//    shift.setId(1L);
//    shift.setStartIntEnum(1);
//    shift.setEndIntEnum(1);
//    shift.setActive(true);
//    TeacherDto teacherDto = new TeacherDto(null, "Kitas", "Kitoks", true);
//    teacherDto.setSelectedShift(shift);
//    teacherDto.setContacts(new ContactDto2());
//
//    TeacherDto expected = new TeacherDto(1L, "Kitas", "Kitoks", true);
//    expected.setSelectedShift(shift);
//    expected.setContacts(new ContactDto2());
//
//    mockMvc.perform(put("/api/v1/teachers/update?tid=1")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .content(objectMapper.writeValueAsString(teacherDto)))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.fName").value("Kitas"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.lName").value("Kitoks"));
//
//}

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

        Mockito.when(teacherServiceDo.updateTeacher(Mockito.any(), Mockito.any())).thenReturn(ResponseEntity.ok(teacherDto));

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
        ResponseEntity<Void> responseT = teacherControllerAction.changeState(teacherId, true);
        ResponseEntity<Void> responseF = teacherControllerAction.changeState(teacherId, false);

        // Then
        assertEquals(HttpStatus.OK, responseT.getStatusCode());
        assertEquals(HttpStatus.OK, responseF.getStatusCode());

    }

    @Test
    void deleteTeacherDetails() {
    }
}