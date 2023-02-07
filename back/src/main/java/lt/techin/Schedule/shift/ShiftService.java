package lt.techin.Schedule.shift;


public class ShiftService {

    public LessonTime chooseEnumFromData (int name) {
        return switch (name) {
            case LessonTime.LESSON_NAME_1 -> LessonTime.FIRST;
            case LessonTime.LESSON_NAME_2 -> LessonTime.SECOND;
            case LessonTime.LESSON_NAME_3 -> LessonTime.THIRD;
            case LessonTime.LESSON_NAME_4 -> LessonTime.FOURTH;
            case LessonTime.LESSON_NAME_5 -> LessonTime.FIFTH;
            case LessonTime.LESSON_NAME_6 -> LessonTime.SIXTH;
            case LessonTime.LESSON_NAME_7 -> LessonTime.SEVENTH;
            case LessonTime.LESSON_NAME_8 -> LessonTime.EIGHTH;
            case LessonTime.LESSON_NAME_9 -> LessonTime.NINTH;
            case LessonTime.LESSON_NAME_10 -> LessonTime.TENTH;
            case LessonTime.LESSON_NAME_11 -> LessonTime.ELEVENTH;
            case LessonTime.LESSON_NAME_12 -> LessonTime.TWELFTH;
            default -> null;
        };
    }


}
