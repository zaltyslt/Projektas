package lt.techin.Schedule.module;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.Schedule.module.ModuleMapper.toModule;
import static lt.techin.Schedule.module.ModuleMapper.toModuleDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ModuleEntityDto> getModules() {
        return moduleService.getAll(false).stream().map(ModuleMapper::toModuleEntityDto).collect(toList());
    }

    @GetMapping(value = "/{moduleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Module> getModule(@PathVariable Long moduleId) {
        var moduleOptional = moduleService.getById(moduleId);
        var responseEntity = moduleOptional.map(module -> ok(module)).orElseGet(() -> ResponseEntity.notFound().build());
        return responseEntity;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ModuleDto> createModule(@RequestBody ModuleDto moduleDto) {
        var createdModule = moduleService.create(toModule(moduleDto));
        return ok(toModuleDto(createdModule));
    }

    @PatchMapping("/{moduleId}")
    public ResponseEntity<ModuleDto> updateModule(@PathVariable Long moduleId,
                                                  @RequestBody ModuleDto moduleDto) {
        var updatedModule = moduleService.updateModule(moduleId, toModule(moduleDto));
        return ok(toModuleDto(updatedModule));
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        boolean deleted = moduleService.deleteById(moduleId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/restore/{moduleId}")
    public ResponseEntity<ModuleDto>  restoreModule(@PathVariable Long moduleId) {
        var restoredModule = moduleService.restoreModule(moduleId);
        return ok(toModuleDto(restoredModule));
    }

    @GetMapping("/paged")
    @ResponseBody
    public ResponseEntity<List<ModuleEntityDto>> findModulesPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Module> pagedModules = moduleService.findAllPaged(page, pageSize, false);
        int totalPageCount = pagedModules.getTotalPages();
        List<ModuleEntityDto> modules = pagedModules.stream().map(ModuleMapper::toModuleEntityDto).collect(toList());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("totalCount", Integer.toString(totalPageCount));
        return ResponseEntity.ok().headers(httpHeaders).body(modules);
    }
}