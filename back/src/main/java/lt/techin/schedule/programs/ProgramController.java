package lt.techin.schedule.programs;

import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursDto;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursMapper;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursService;
import lt.techin.schedule.subject.SubjectMapper;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lt.techin.schedule.programs.ProgramMapper.toProgram;
import static lt.techin.schedule.programs.ProgramMapper.toProgramDto;
import static lt.techin.schedule.programs.subjectsHours.SubjectHoursMapper.toSubjectHours;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/programs")
public class ProgramController {
    Logger logger = Logger.getLogger(ProgramController.class.getName());
    private final ProgramService programService;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectHoursService subjectHoursService;
    @Autowired
    private SubjectRepository subjectRepository;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProgramDto> getProgramsList() {
        logger.info("Programs have been created");
        return programService.getAll().stream().map(ProgramMapper::toProgramDto).toList();
    }

    @GetMapping(value = "/program/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProgramDto> getProgram(@PathVariable Long id) {
        logger.log(Level.INFO, "The program is rendered: {0} ", id);
        return ResponseEntity.ok(toProgramDto(programService.finById(id)));
    }

    @PostMapping(value = "/create-program", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto) {
        var createProgram = programService.create(toProgram(programDto));
        if (createProgram == null) {
            logger.info("Bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        logger.info("The program was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(toProgramDto(createProgram));
    }

    @PatchMapping("/update-program/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId,
                                                    @RequestBody ProgramDto programDto) {
        var updatedProgram = programService.update(programId, toProgram(programDto));
        logger.info("The program was updated, successfully");
        return ok(toProgramDto(updatedProgram));
    }

    @PatchMapping("/disable-program/{programId}")
    public ProgramDto disableProgram(@PathVariable Long programId) {
        var disableProgram = programService.disable(programId);
        logger.log(Level.INFO, "The program was disable: {0} ", programId);
        return toProgramDto(disableProgram);
    }

    @PatchMapping("/enable-program/{programId}")
    public ProgramDto enableProgram(@PathVariable Long programId) {
        var enableProgram = programService.enable(programId);
        logger.log(Level.INFO, "The program was enabled: {0} ", programId);
        return toProgramDto(enableProgram);
    }

    @PostMapping(value = "/create-program-hours", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgramDto> createProgramHours(@RequestBody ProgramDto programDto) {
        if (programDto == null || programDto.getSubjectHoursList().isEmpty()) {
            logger.info("Bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//        sukuriam SubjectHours i DB nes
        var hourlist = subjectHoursService.create(programDto.getSubjectHoursList());
        programDto.setSubjectHoursList(hourlist);
        var createProgram = programService.create(toProgram(programDto));
        logger.info("The program was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(toProgramDto(createProgram));

    }

    @PatchMapping(value = "/update-hours-program/{programId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgramDto> getProgramHours(@RequestBody List<SubjectHoursDto> subjectHoursDto,
                                                      @PathVariable Long programId) {
        Program program = programService.finById(programId);
        if (program != null) {

            var hourlist = subjectHoursService.updateAll(subjectHoursDto.stream()
                    .map(SubjectHoursMapper::toSubjectHours).toList());

            program.setSubjectHoursList(hourlist);
            programService.update(program.getId(), program);
        }

        if (program != null) {
            return ResponseEntity.ok(toProgramDto(program));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @DeleteMapping(value = "/delete-hours-id/{subjHourId}")
    public ResponseEntity<String> deleteSubjectHourbyId(@PathVariable Long subjHourId) {
        var deleted = subjectHoursService.deleteById(subjHourId);
        return deleted ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }
}
