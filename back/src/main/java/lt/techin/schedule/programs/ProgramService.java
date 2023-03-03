package lt.techin.schedule.programs;

import lt.techin.schedule.programs.subjectsHours.SubjectHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;
    private final SubjectHoursService subjectHoursService;

    public ProgramService(ProgramRepository programRepository, SubjectHoursService subjectHoursService) {
        this.programRepository = programRepository;
        this.subjectHoursService = subjectHoursService;
    }

    @Autowired

    public List<Program> getAll() {
        return programRepository.findAll();
    }

    public Program create(Program program) {
        if (program.getProgramName() == null) return null;
        if (findByProgramName(program.getProgramName())) {
            return null;
        } else {
            try {
                return programRepository.save(program);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        }
    }

    public Program createWithSubjectList(Program program) {
        if (program.getProgramName() == null) return null;
        if (findByProgramName(program.getProgramName())) {
            return null;
        } else {
            try {
                var hourlist = subjectHoursService.create(
                        program.getSubjectHoursList()
                );
                program.setSubjectHoursList(hourlist);
                return programRepository.save(program);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        }
    }

    public boolean findByProgramName(String programName) {
        return getAll().stream()
                .anyMatch(program -> program.getProgramName().matches(programName));
    }

    public boolean findByProgramNameNotSelf(String programName, String newName) {
        if (!programName.matches(newName)) {
            return !getAll().stream()
                    .filter(s -> newName.matches(s.getProgramName()))
                    .toList()
                    .isEmpty();
        } else {
            return false;
        }
    }

    public Program update(Long id, Program program) {
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
