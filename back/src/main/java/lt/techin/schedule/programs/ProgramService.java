package lt.techin.schedule.programs;

import lt.techin.schedule.programs.subjectsHours.SubjectHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;


    @Autowired

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> getAll() {
        return programRepository.findAll();
    }

    public Program create(Program program) {
        return programRepository.save(program);
    }

    public Program update(Long id, Program program) {
//        var existingProgram = programRepository.findById(id).orElse(null);
        var existingProgram = finById(id);

        if (existingProgram != null) {
            existingProgram.setProgramName(program.getProgramName());
            existingProgram.setDescription(program.getDescription());
            existingProgram.setActive(program.isActive());

            existingProgram.setSubjectHoursList(program.getSubjectHoursList());
            return programRepository.save(existingProgram);
        }
        return null;
    }

    public Program finById(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    public Program disable(Long id) {

        var existingProgram = programRepository.findById(id).orElse(null);
        if (existingProgram != null) {
            existingProgram.setActive(false);
            return programRepository.save(existingProgram);
        }
        return null;
    }

    public Program enable(Long programId) {
        var existingProgram = programRepository.findById(programId).orElse(null);
        if (existingProgram != null) {
            existingProgram.setActive(true);
            return programRepository.save(existingProgram);
        }
        return null;
    }
}
