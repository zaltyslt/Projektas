package lt.techin.schedule.programs;

import lt.techin.schedule.programs.subjectsHours.SubjectHoursService;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return ResponseEntity.ok(ProgramMapper.toProgramDto(programService.finById(id)));
    }

    @PostMapping(value = "/create-program", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, String>> createProgram(@RequestBody ProgramDto programDto) {
        var createProgram = programService.create(ProgramMapper.toProgram(programDto));
        if (createProgram == null) {
            logger.info("The program is already created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tokia programa jau yra sukurta"));
        }
        logger.info("The program was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (ProgramMapper.toProgramDto(createProgram).toString())));
    }

    @PatchMapping("/update-program/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId,
                                                    @RequestBody ProgramDto programDto) {
        var updatedProgram = programService.update(programId, ProgramMapper.toProgram(programDto));
        logger.info("The program was updated, successfully");
        return ok(ProgramMapper.toProgramDto(updatedProgram));
    }

    @PatchMapping("/disable-program/{programId}")
    public ProgramDto disableProgram(@PathVariable Long programId) {
        var disableProgram = programService.disable(programId);
        logger.log(Level.INFO, "The program was disable: {0} ", programId);
        return ProgramMapper.toProgramDto(disableProgram);
    }

    @PatchMapping("/enable-program/{programId}")
    public ProgramDto enableProgram(@PathVariable Long programId) {
        var enableProgram = programService.enable(programId);
        logger.log(Level.INFO, "The program was enabled: {0} ", programId);
        return ProgramMapper.toProgramDto(enableProgram);
    }

    @PostMapping(value = "/create-program-hours", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createProgramHours(@RequestBody ProgramDto programDto) {
        if (programDto.getSubjectHoursList().isEmpty()) {
            logger.info("getSubjectHoursList is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Prašome pridėti dalyką"));
        }
        var createProgram = programService.createWithSubjectList(ProgramMapper.toProgram(programDto));
        if (createProgram == null) {
            logger.info("The program is already created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Tokia programa jau yra sukurta"));
        }
        logger.info("The program was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (ProgramMapper.toProgramDto(createProgram)).toString()));
    }

    @PatchMapping(value = "/update-hours-program/{programId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getProgramHours(@RequestBody ProgramDto subjectHoursDto,
                                                               @PathVariable Long programId) {
        if (subjectHoursDto.getSubjectHoursList().isEmpty()) {
            logger.info("getSubjectHoursList is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Prašome pridėti dalyką"));
        }
        var program = programService.finById(programId);
        if (program != null) {
            boolean programNameExists = programService.findByProgramNameNotSelf(program.getProgramName(), subjectHoursDto.getProgramName());
            if (programNameExists) {
                logger.info("The program is already created");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Tokia programa jau yra sukurta"));
            }
            var hourlist = subjectHoursService.updateAll(subjectHoursDto.getSubjectHoursList());
            program.setSubjectHoursList(hourlist);
            Program update = programService.update(program.getId(), ProgramMapper.toProgram(subjectHoursDto));
            logger.info("The program was updated, successfully");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", (ProgramMapper.toProgramDto(update)).toString()));
        } else {
            logger.info("subject is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Prašome pridėti dalyką"));
        }
    }

    @DeleteMapping(value = "/delete-hours-id/{subjHourId}")
    public ResponseEntity<String> deleteSubjectHourbyId(@PathVariable Long subjHourId) {
        var deleted = subjectHoursService.deleteById(subjHourId);
        logger.log(Level.INFO, "The subject hours were deleted: {0} ", subjHourId);
        return deleted ? ResponseEntity.accepted().build() : ResponseEntity.notFound().build();
    }
}
