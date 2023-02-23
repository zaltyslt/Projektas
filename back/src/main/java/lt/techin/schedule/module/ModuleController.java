package lt.techin.schedule.module;

import jakarta.validation.Valid;
import lt.techin.schedule.validators.ValidationDto;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping(value = "/deleted", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ModuleEntityDto> getDeletedModules() {
        return moduleService.getAll(true).stream().map(ModuleMapper::toModuleEntityDto).collect(toList());
    }

    @GetMapping(value = "/{moduleId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Module> getModule(@PathVariable Long moduleId) {
        var moduleOptional = moduleService.getById(moduleId);
        return moduleOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public @ResponseBody ValidationDto createModule(@RequestBody @Valid ModuleDto moduleDto, BindingResult bindingResult) {
        ValidationDto validationDto = new ValidationDto();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                validationDto.addValidationError(
                        "\"" + Objects.requireNonNull(bindingResult.getFieldError()).getField() + "\"",
                        errors.get(x).getDefaultMessage());
            }
            validationDto.setPassedValidation(false);
            validationDto.setValid(false);
        } else {
            String modifyResponse = moduleService.create(toModule(moduleDto));
            validationDto.setPassedValidation(true);
            if (modifyResponse.isEmpty()) {
                validationDto.setValid(true);
            } else {
                validationDto.setValid(false);
                validationDto.addDatabaseError(modifyResponse);
            }
        }
        return validationDto;
    }

    @PutMapping("/update/{moduleId}")
    public @ResponseBody ValidationDto updateModule(@PathVariable Long moduleId, @RequestBody @Valid ModuleDto moduleDto, BindingResult bindingResult) {
        ValidationDto validationDto = new ValidationDto();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                validationDto.addValidationError(
                        "\"" + Objects.requireNonNull(bindingResult.getFieldError()).getField() + "\"",
                        errors.get(x).getDefaultMessage());
            }
            validationDto.setPassedValidation(false);
            validationDto.setValid(false);
        } else {
            String modifyResponse = moduleService.updateModule(moduleId, toModule(moduleDto));
            validationDto.setPassedValidation(true);
            if (modifyResponse.isEmpty()) {
                validationDto.setValid(true);
            } else {
                validationDto.setValid(false);
                validationDto.addDatabaseError(modifyResponse);
            }
        }
        return validationDto;
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        boolean deleted = moduleService.deleteById(moduleId);
        System.out.println("Deletions is called");
        System.out.println(moduleService.getById(moduleId));
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
}
