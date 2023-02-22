package lt.techin.schedule.module;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
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

    public Module create(Module module) {
        if (moduleRepository.findAll().stream().anyMatch(m -> m.getNumber().equalsIgnoreCase(module.getNumber()))) {
            return null;
        }
        else {
            return moduleRepository.save(module);
        }
    }

    public Module updateModule(Long id, Module module) {
        var existingModule = moduleRepository.findById(id).orElseThrow();
        if (existingModule != null) {
            existingModule.setNumber(module.getNumber());
            existingModule.setName(module.getName());
            return moduleRepository.save(existingModule);
        }
        return null;
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

    private Pageable pageable(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        return PageRequest.of(page, pageSize, sortDirection, sortField);
    }

    public Page<Module> findAllPaged(int page, int pageSize, boolean isDeleted) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedModuleFilter");
        filter.setParameter("isDeleted", isDeleted);
        Page<Module> modules = moduleRepository.findAll(pageable);
        session.disableFilter("deletedModuleFilter");
        return modules;
    }

    @PostConstruct
    //FIXME for dev purpose
    public void loadInitialModules() {
        var initialModulesToAdd = List.of(
                new ModuleDto("ABC", "Java"),
                new ModuleDto("59A", "Spring Boot"),
                new ModuleDto("TRA", "React")
        );

        initialModulesToAdd.stream()
                .map(ModuleMapper::toModule)
                .forEach(moduleRepository::save);


//        List<ModuleDto> modules = new ArrayList<>();
//        modules.addAll(initialModulesToAdd);
//        for (int i = 0; i < 100; i++) {
//            var moduleDto = new ModuleDto();
//            modules.add(moduleDto);
//        }

//        modules.stream()
//                .map(ModuleMapper::toModule)
//                .forEach(moduleRepository::save);
//    }
    }
}
