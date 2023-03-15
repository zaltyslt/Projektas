package lt.techin.schedule.module;

public class ModuleMapper {

    public static ModuleDto toModuleDto(Module module) {
        var moduleDto = new ModuleDto();
        moduleDto.setNumber(module.getNumber());
        moduleDto.setName(module.getName());
        return moduleDto;
    }

    public static ModuleEntityDto toModuleEntityDto(Module module) {
        var moduleEntityDto = new ModuleEntityDto();
        moduleEntityDto.setId(module.getId());
        moduleEntityDto.setNumber(module.getNumber());
        moduleEntityDto.setName(module.getName());

        return moduleEntityDto;
    }

    public static Module toModule(ModuleDto moduleDto) {
        var module = new Module();
        module.setNumber(moduleDto.getNumber());
        module.setName(moduleDto.getName());
        return module;
    }

}
