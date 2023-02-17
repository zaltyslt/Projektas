package lt.techin.schedule.module;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static lt.techin.schedule.module.ModuleMapper.toModule;
import static lt.techin.schedule.module.ModuleMapper.toModuleDto;
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

    @GetMapping(value = "/deleted", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ModuleEntityDto> getDeletedModules() {
        return moduleService.getAll(true).stream().map(ModuleMapper::toModuleEntityDto).collect(toList());
    }
    //ModuleDto Validation - Internal server error 500
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ModuleDto> createModule(@RequestBody ModuleDto moduleDto) {
        var createdModule = moduleService.create(toModule(moduleDto));
        if (createdModule == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ok(toModuleDto(createdModule));
        }
    }

    @PatchMapping("/{moduleId}")
    public @ResponseBody Map<Integer, String> updateModule (@PathVariable Long moduleId, @Valid @RequestBody Module moduleDto, BindingResult bindingResult) {
        Map<Integer, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                response.put(x, errors.get(x).getDefaultMessage());
            }
        } else {
            String modifyResponse = moduleService.updateModule(moduleId, moduleDto);
            if (!modifyResponse.isEmpty()) {
                response.put(0, modifyResponse);
            }
        }
        return response;
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
