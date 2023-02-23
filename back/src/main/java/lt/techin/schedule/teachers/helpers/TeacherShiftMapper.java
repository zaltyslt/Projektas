package lt.techin.schedule.teachers.helpers;

import lt.techin.schedule.shift.Shift;

public class TeacherShiftMapper {
    public static TeacherShiftDto shiftToDto(Shift shift) {
        TeacherShiftDto dto = new TeacherShiftDto();
        dto.setId(shift.getId());
        dto.setName(shift.getName());
        return dto;
    }

    public static Shift shiftFromDto(TeacherShiftDto teacherShiftDto) {
        if (teacherShiftDto != null) {
            Shift shift = new Shift();
            shift.setId(teacherShiftDto.getId());
            shift.setName(teacherShiftDto.getName());
            return shift;
        }
        return null;
    }
}
