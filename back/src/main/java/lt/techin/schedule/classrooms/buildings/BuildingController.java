package lt.techin.schedule.classrooms.buildings;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController("/api/v1/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    //     pridejau i path /{id}, tam kad atitiktu adresa, ieskant /building
    @GetMapping(value = "/building/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    // nereikia, @RestController jau turi sita ijunges defaultu
//    @ResponseBody
    public ResponseEntity<BuildingDto> getBuilding(@PathVariable Long id) {
//        sitos eilutes nebereikia
//        var response = new BuildingDto();
//        pasirodo turi buildingmapper kas yra nice, tai ji ir naudojam kad grazintu DTO buildinga
        return ResponseEntity.ok(BuildingMapper.toBuildingDto(buildingService.getBuildingById(id)));
    }


    @GetMapping(value = "/buildings", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    // nereikia, @RestController jau turi sita ijunges defaultu
//    @ResponseBody
    public List<BuildingDto> getBuildings() {
        return buildingService.getAll().stream()
                .map(BuildingMapper::toBuildingDto)
//                .collect(toList());
//                sitas yra trumpesnis variantas, grazina ta pati lista
                .toList();
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
