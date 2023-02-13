package lt.techin.schedule.classrooms.buildings;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    public Building create(Building building) {
        return buildingRepository.save(building);
    }

    public Building update(Long id, Building building) {
        building.setId(id);
        return buildingRepository.save(building);
    }

    public Building getBuildingById(Long id) {
        return buildingRepository.findById(id).orElse(new Building());
    }

//    public boolean deleteById(Long id) {
//        if (buildingRepository.existsById(id)) {
//            buildingRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
}
