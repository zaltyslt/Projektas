package lt.techin.schedule.programs;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lt.techin.schedule.programs.ProgramMapper.toProgram;
import static lt.techin.schedule.programs.ProgramMapper.toProgramDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/programs")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProgramDto> getProgramsList() {
        return programService.getAll().stream().map(ProgramMapper::toProgramDto).toList();
    }

    @GetMapping(value = "/program/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProgramDto> getProgram(@PathVariable Long id) {
        return ResponseEntity.ok(toProgramDto(programService.finById(id)));
    }

    @PostMapping(value = "/create-program", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto) {
        var createProgram = programService.create(toProgram(programDto));
        if (createProgram == null) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toProgramDto(createProgram));
    }

    @PatchMapping("/update-program/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId,
                                                    @RequestBody ProgramDto programDto) {
        var updatedProgram = programService.update(programId, toProgram(programDto));
        return ok(toProgramDto(updatedProgram));
    }

    @PatchMapping("/disable-program/{programId}")
    public ProgramDto disableProgram(@PathVariable Long programId) {
        var disableProgram = programService.disable(programId);
        return toProgramDto(disableProgram);
    }

    @PatchMapping("/enable-program/{programId}")
    public ProgramDto enableProgram(@PathVariable Long programId) {
        var enableProgram = programService.enable(programId);
        return toProgramDto(enableProgram);
    }
}
