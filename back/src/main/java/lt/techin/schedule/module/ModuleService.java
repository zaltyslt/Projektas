package lt.techin.schedule.module;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lt.techin.schedule.shift.Shift;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    private EntityManager entityManager;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedModuleFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Module> modules = moduleRepository.findAll();
        session.disableFilter("deletedModuleFilter");
        return modules;
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
        }
        else {
            moduleRepository.save(module);
            return "";
        }
    }

    public String updateModule(Long id, Module module) {
        if (moduleRepository.findById(id).isPresent()) {
            Optional<Module> foundModule = findModuleByNumber(module.getNumber());
            if(foundModule.isPresent() && !foundModule.get().getId().equals(id)) {
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
        System.out.println("Deleting module");
        try {
            moduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    @PostConstruct
    public void loadInitialModules() {
        var initialModulesToAdd = List.of(
                new ModuleDto("ABC", "Java"),
                new ModuleDto("59A", "Spring Boot"),
                new ModuleDto("TRA", "React"),
                new ModuleDto("ASDD", "Java"),
                new ModuleDto("Das", "Java"),
                new ModuleDto("fsaf", "React"),
                new ModuleDto("sad", "Java"),
                new ModuleDto("ewqe", "Java"),
                new ModuleDto("TRgA", "ewqr"),
                new ModuleDto("dsa", "fsaf"),
                new ModuleDto("ewq", "dasa"),
                new ModuleDto("TRsA", "ewqesdx")
        );

        initialModulesToAdd.stream()
                .map(ModuleMapper::toModule)
                .forEach(moduleRepository::save);
    }
}
