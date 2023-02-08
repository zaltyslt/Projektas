package lt.techin.schedule.classrooms.buildings;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping(value = "/building", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<BuildingDto> getBuilding() {
        var response = new BuildingDto();
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/buildings", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<BuildingDto> getBuildings() {
        return buildingService.getAll().stream()
                .map(BuildingMapper::toBuildingDto)
                .collect(toList());
    }

    @PostMapping(value = "/createBuilding", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BuildingDto> createBuilding(@Valid @RequestBody BuildingDto buildingDto) {
        var createdBuilding = buildingService.create(BuildingMapper.toBuilding(buildingDto));
        return ok(BuildingMapper.toBuildingDto(createdBuilding));
    }

    @PatchMapping(value = "/updateBuilding/{buildingId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BuildingDto> updateBuilding(@PathVariable Long buildingId,
                                                      @RequestBody BuildingDto buildingDto) {
        var updatedBuilding = buildingService.update(buildingId, BuildingMapper.toBuilding(buildingDto));
        return ok(BuildingMapper.toBuildingDto(updatedBuilding));
    }

//    @DeleteMapping("/deleteBuilding/{buildingId}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long buildingId) {
//        var buildingDeleted = buildingService.deleteById(buildingId);
//        if (buildingDeleted) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
