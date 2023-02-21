package lt.techin.schedule.shift;

public class ShiftMapper {

    public static ShiftDto shiftToDto(Shift shift){
        ShiftDto shiftDto = new ShiftDto();
        shiftDto.setId(shift.getId());
        shiftDto.setName(shift.getName());
        shiftDto.setShiftStartingTime(shift.getShiftStartingTime());
        shiftDto.setShiftEndingTime(shift.getShiftEndingTime());
        shiftDto.setStartIntEnum(shift.getStartIntEnum());
        shiftDto.setEndIntEnum(shift.getEndIntEnum());
        shiftDto.setCreatedDate(shift.getCreatedDate());
        shiftDto.setModifiedDate(shift.getModifiedDate());
        shiftDto.setActive(shift.getIsActive());
        return shiftDto;
    }

    public static Shift shiftFromDto(ShiftDto shiftDto){
        Shift shift = new Shift();
        shift.setId(shiftDto.getId());
        shift.setName(shiftDto.getName());
        shift.setShiftStartingTime(shiftDto.getShiftStartingTime());
        shift.setShiftEndingTime(shiftDto.getShiftEndingTime());
        shift.setStartIntEnum(shiftDto.getStartIntEnum());
        shift.setEndIntEnum(shiftDto.getEndIntEnum());
        shift.setCreatedDate(shiftDto.getCreatedDate());
        shift.setModifiedDate(shiftDto.getModifiedDate());
        shift.setIsActive(shiftDto.getIsActive());
        return shift;
    }
}
