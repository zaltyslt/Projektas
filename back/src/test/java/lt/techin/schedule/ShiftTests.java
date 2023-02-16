package lt.techin.schedule;

import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftDTO;
import lt.techin.schedule.shift.ShiftService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ShiftTests {

    @Test
    public void lessonTimeEnumTest() {
        Assertions.assertEquals(LessonTime.FIFTH, LessonTime.getLessonTimeByInt(5),"Number 5 should return Fifth lesson enum.");
        Assertions.assertEquals(LessonTime.FOURTEENTH, LessonTime.getLessonTimeByInt(14),"Number 14 should return Fourteenth lesson enum.");
        Assertions.assertEquals(LessonTime.TENTH, LessonTime.getLessonTimeByInt(10),"Number 10 should return Tenth lesson enum.");
        Assertions.assertEquals(LessonTime.FIRST, LessonTime.getLessonTimeByInt(1),"Number 1 should return First lesson enum.");
        Assertions.assertEquals(LessonTime.FOURTH, LessonTime.getLessonTimeByInt(4),"Number 4 should return Fourth lesson enum.");

        Assertions.assertEquals(2, LessonTime.SECOND.getLessonName(),"Second enum should return value of 2.");
        Assertions.assertEquals(7, LessonTime.SEVENTH.getLessonName(),"Seventh enum should return value of 7.");

        Assertions.assertEquals("14:45", LessonTime.EIGHTH.getLessonStart(),"Eighth enum should have start value of 14:45");
        Assertions.assertEquals("17:30", LessonTime.ELEVENTH.getLessonStart(),"Eleventh enum should have start value of 17:30");
        Assertions.assertEquals("09:50", LessonTime.THIRD.getLessonStart(),"Third enum should have start value of 09:50");
        Assertions.assertEquals("12:55", LessonTime.SIXTH.getLessonStart(),"Sixth enum should have start value of 12:55");
        Assertions.assertEquals("15:40", LessonTime.NINTH.getLessonStart(),"Ninth enum should have start value of 15:40");

        Assertions.assertEquals("20:05", LessonTime.THIRTEENTH.getLessonEnd(),"Thirteenth enum should have end value of 20:05");
        Assertions.assertEquals("12:45", LessonTime.FIFTH.getLessonEnd(),"Fifth enum should have end value of 12:45");
        Assertions.assertEquals("09:40", LessonTime.SECOND.getLessonEnd(),"Second enum should have end value of 09:40");
        Assertions.assertEquals("11:30", LessonTime.FOURTH.getLessonEnd(),"Fourth enum should have end value of 11:30");
        Assertions.assertEquals("13:40", LessonTime.SIXTH.getLessonEnd(),"Sixth enum should have end value of 13:40");
    }

    @Test
    public void shiftEntityTest() {
        Shift shift = new Shift("Morning", "10:00", "15:00", true, 2, 5);

        Assertions.assertEquals("Morning", shift.getName(), "Name assigned in the constructor is wrong.");
        Assertions.assertEquals("10:00", shift.getShiftStartingTime(), "Shift starting time assigned in the constructor is wrong.");
        Assertions.assertEquals("15:00", shift.getShiftEndingTime(), "Shift ending time assigned in the constructor is wrong.");
        Assertions.assertTrue(shift.getIsActive(), "Is active boolean assigned in the constructor is wrong.");
        Assertions.assertEquals(2, shift.getStartIntEnum(), "Start int enum assigned in the constructor is wrong.");
        Assertions.assertEquals(5, shift.getEndIntEnum(), "End int enum assigned in the constructor is wrong.");
        Assertions.assertEquals("10:00-15:00", shift.getShiftTime(), "End int enum assigned in the constructor is wrong.");

        Assertions.assertNull(shift.getCreatedDate(), "Created date should be null.");
        Assertions.assertNull(shift.getModifiedDate(), "Modified date should be null.");
    }

    @Mock
    ShiftDTO shiftDTO;

    @InjectMocks
    ShiftService shiftService = new ShiftService(shiftDTO);

    @Test
    public void shiftServiceTest() {


        Shift shift = new Shift("Morning", "10:00", "15:00", true, 2, 5);
        shiftService.addShift(shift);

        Shift shiftFromBase = shiftService.getActiveShifts().get(0);

    }
}
