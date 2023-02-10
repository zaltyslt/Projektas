package lt.techin.Schedule.module;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return moduleRepository.findAll();
    }

    public Optional<Module> getById(Long id) {
        return moduleRepository.findById(id);
    }

    public Module create(Module module) {
        return moduleRepository.save(module);
    }

    public Module update(Long id, Module module) {
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

}
