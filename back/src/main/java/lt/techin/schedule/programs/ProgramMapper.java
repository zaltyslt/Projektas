package lt.techin.schedule.programs;

public class ProgramMapper {
    public static ProgramDto toProgramDto(Program program) {
        var programDto = new ProgramDto();
        programDto.setId(program.getId());
        programDto.setProgramName(program.getProgramName());
        programDto.setDescription(program.getDescription());
        programDto.setActive(program.isActive());
        programDto.setCreatedDate(program.getCreatedDate());
        programDto.setModifiedDate(program.getModifiedDate());
        programDto.setSubjectHoursList(program.getSubjectHoursList());
        return programDto;
    }

    public static Program toProgram(ProgramDto programDto){
        var program = new Program();
        program.setId(programDto.getId());
        program.setProgramName(programDto.getProgramName());
        program.setDescription(programDto.getDescription());
        program.setActive(programDto.isActive());
        program.setCreatedDate(programDto.getCreatedDate());
        program.setModifiedDate(programDto.getModifiedDate());
        program.setSubjectHoursList(programDto.getSubjectHoursList());
        return program;
    }
}
