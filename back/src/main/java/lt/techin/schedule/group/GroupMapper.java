package lt.techin.schedule.group;


import lt.techin.schedule.programs.ProgramMapper;
import lt.techin.schedule.shift.ShiftMapper;

public class GroupMapper {

    public static GroupDto groupToDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getSchoolYear(),
                group.getStudentAmount(),
                group.getIsActive(),
                ProgramMapper.toProgramDto(group.getProgram()),
                ShiftMapper.shiftToDto(group.getShift()),
                group.getCreatedDate(),
                group.getModifiedDate()
        );
    }

    public static Group dtoToGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getSchoolYear(),
                groupDto.getStudentAmount(),
                groupDto.getIsActive(),
                ProgramMapper.toProgram(groupDto.getProgram()),
                ShiftMapper.dtoToShift(groupDto.getShift()),
                groupDto.getCreatedDate(),
                groupDto.getModifiedDate()
        );
    }

    public static GroupEntityDto toGroupEntityDto(Group group) {
        var groupEntityDto = new GroupEntityDto();
        groupEntityDto.setId(group.getId());
        groupEntityDto.setName(group.getName());
        return groupEntityDto;
    }
}
