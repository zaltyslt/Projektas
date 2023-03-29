package lt.techin.schedule.module;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    private EntityManager entityManager;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAll() {
        return moduleRepository.findAll().stream().filter(module -> !module.isDeleted()).collect(Collectors.toList());
    }

    public List<Module> getAllDeleted() {
        return moduleRepository.findAll().stream().filter(Module::isDeleted).collect(Collectors.toList());
    }

    public Module delete(Long moduleId) {
        var existingSubject = moduleRepository.findById(moduleId).orElseThrow();
        existingSubject.setDeleted(true);
        return moduleRepository.save(existingSubject);
    }

    public Optional<Module> getById(Long id) {
        return moduleRepository.findById(id);
    }

    private Optional<Module> findModuleByNumber(String number) {
        return moduleRepository.findAll().stream().filter(s -> s.getNumber().equalsIgnoreCase(number)).findFirst();
    }

    public String create(Module module) {
        if (moduleRepository.findAll().stream().anyMatch(m -> m.getNumber().equalsIgnoreCase(module.getNumber()))) {
            return "Modulio numeris turi būti unikalus.";
        } else {
            moduleRepository.save(module);
            return "";
        }
    }

    public String updateModule(Long id, Module module) {
        if (moduleRepository.findById(id).isPresent()) {
            Optional<Module> foundModule = findModuleByNumber(module.getNumber());
            if (foundModule.isPresent() && !foundModule.get().getId().equals(id)) {
                return "Modulio numeris turi būti unikalus.";
            }
            module.setId(id);
            moduleRepository.save(module);
            return "";
        }
        return "Modulio pakeisti nepavyko.";
    }

    public Module restoreModule(Long id) {
        var module = moduleRepository.findById(id).orElseThrow();
        module.setDeleted(false);
        return moduleRepository.save(module);
    }

    public boolean deleteById(Long id) {
        try {
            moduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }
}

