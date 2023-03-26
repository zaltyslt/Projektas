package lt.techin.schedule.schedules.workdays;

import lt.techin.schedule.schedules.planner.PlannerDto;
import lt.techin.schedule.schedules.planner.PlannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class PlannerServiceTest {

    @Autowired
    private PlannerService plannerService;

    @Test
    void testGetLessonStartString() {
        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setStartIntEnum(1);
        String expectedStartString = "08:00";
        String actualStartString = plannerService.getLessonStartString(plannerDto);
        assertEquals(expectedStartString, actualStartString);
    }

//    @Test
//    void testGetLessonEndString() {
//        // create a PlannerDto with an end time of 3
//        PlannerDto plannerDto = new PlannerDto();
//        plannerDto.setEndIntEnum(3);
//
//        // test that the end time string is correct
//        String expectedEndString = "09:30";
//        String actualEndString = plannerService.getLessonEndString(plannerDto);
//        assertEquals(expectedEndString, actualEndString);
//    }


}

