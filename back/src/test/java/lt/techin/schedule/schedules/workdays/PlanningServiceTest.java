package lt.techin.schedule.schedules.workdays;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomSmallDto;
import lt.techin.schedule.schedules.planner.*;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlannerServiceTest {
    @Autowired
    private PlannerService plannerService;
    @MockBean
    private WorkDayRepository workDayRepository;

    @Test
    void testGetLessonStartString() {
        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setStartIntEnum(1);
        String expectedStartString = "08:00";
        String actualStartString = plannerService.getLessonStartString(plannerDto);
        assertEquals(expectedStartString, actualStartString);
    }

    @Test
    public void testGetWorkDays() {
        Long scheduleId = 1L;
        List<WorkDay> expectedWorkDays = new ArrayList<>();
        expectedWorkDays.add(new WorkDay(1L));
        expectedWorkDays.add(new WorkDay(2L));
        when(workDayRepository.findWorkDaysByScheduleId(scheduleId))
                .thenReturn(expectedWorkDays);
        List<WorkDay> actualWorkDays = plannerService.getWorkDays(scheduleId);
        assertThat(actualWorkDays, equalTo(expectedWorkDays));
        Mockito.verify(workDayRepository).findWorkDaysByScheduleId(scheduleId);
    }

    @Test
    public void testGetWorkDay() {
        WorkDay workDay = new WorkDay();
        workDay.setId(1L);
        when(workDayRepository.findById(1L)).thenReturn(Optional.of(workDay));
        Optional<WorkDay> result = plannerService.getWorkDay(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getId(), 1L);
    }

    public void testUpdateWorkDay() {
        Long workDayId = 1L;
        WorkDayDto workDayDto = new WorkDayDto();
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setfName("Vardenis");
        teacherDto.setlName("Pavardenis");
        ClassroomSmallDto classroomSmallDto = new ClassroomSmallDto();
        classroomSmallDto.setClassroomName("101");
        workDayDto.setClassroom(classroomSmallDto);
        workDayDto.setOnline(false);
        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.setId(workDayId);
        existingWorkDay.setTeacher(new Teacher());
        existingWorkDay.setClassroom(new Classroom());
        existingWorkDay.setOnline(true);
        when(workDayRepository.findById(workDayId)).thenReturn(Optional.of(existingWorkDay));
        WorkDay updatedWorkDay = plannerService.updateWorkDay(workDayId, workDayDto);
        assertEquals(existingWorkDay, updatedWorkDay);
        assertEquals("Vardenis", existingWorkDay.getTeacher().getfName());
        assertEquals("Pavardenis", existingWorkDay.getTeacher().getlName());
        assertEquals("101", existingWorkDay.getClassroom().getClassroomName());
        assertFalse(existingWorkDay.getOnline());
    }
}

