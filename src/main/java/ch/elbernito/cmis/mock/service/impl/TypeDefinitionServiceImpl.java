package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeDefinitionServiceImpl implements TypeDefinitionService {

    private static final Logger logger = LoggerFactory.getLogger(TypeDefinitionServiceImpl.class);

    private final TypeDefinitionRepository repository;

    @Override
    public TypeDefinitionDto create(TypeDefinitionDto dto) {
        logger.info("Creating TypeDefinition: {}", dto);
        TypeDefinitionModel model = new TypeDefinitionModel();
        model.setTypeId(dto.getTypeId());
        model.setDisplayName(dto.getDisplayName());
        model.setBaseTypeId(dto.getBaseTypeId());
        model.setDescription(dto.getDescription());
        model = repository.save(model);
        return toDto(model);
    }

    @Override
    public TypeDefinitionDto getById(Long id) {
        logger.info("Get TypeDefinition by id: {}", id);
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<TypeDefinitionDto> getAll() {
        logger.info("Get all TypeDefinitions");
        List<TypeDefinitionDto> list = new ArrayList<>();
        for (TypeDefinitionModel model : repository.findAll()) {
            list.add(toDto(model));
        }
        return list;
    }

    @Override
    public TypeDefinitionDto update(Long id, TypeDefinitionDto dto) {
        logger.info("Update TypeDefinition id {}: {}", id, dto);
        TypeDefinitionModel model = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("TypeDefinition not found"));
        model.setTypeId(dto.getTypeId());
        model.setDisplayName(dto.getDisplayName());
        model.setBaseTypeId(dto.getBaseTypeId());
        model.setDescription(dto.getDescription());
        return toDto(repository.save(model));
    }

    @Override
    public void delete(Long id) {
        logger.info("Delete TypeDefinition id {}", id);
        repository.deleteById(id);
    }

    // Direktes Mapping ohne Mapper-Klasse!
    private TypeDefinitionDto toDto(TypeDefinitionModel model) {
        if (model == null) return null;
        TypeDefinitionDto dto = new TypeDefinitionDto();
        dto.setId(model.getId());
        dto.setTypeId(model.getTypeId());
        dto.setDisplayName(model.getDisplayName());
        dto.setBaseTypeId(model.getBaseTypeId());
        dto.setDescription(model.getDescription());
        return dto;
    }
}
