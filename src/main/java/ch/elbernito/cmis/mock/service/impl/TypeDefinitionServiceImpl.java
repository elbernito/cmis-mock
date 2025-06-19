package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.exception.TypeDefinitionNotFoundException;
import ch.elbernito.cmis.mock.mapping.TypeDefinitionMapper;
import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of TypeDefinitionService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TypeDefinitionServiceImpl implements TypeDefinitionService {

    private final TypeDefinitionRepository typeDefinitionRepository;
    private final TypeDefinitionMapper typeDefinitionMapper;

    @Override
    public List<TypeDefinitionDto> getAllTypeDefinitions() {
        log.info("Fetching all type definitions");
        List<TypeDefinitionModel> list = typeDefinitionRepository.findAll();
        List<TypeDefinitionDto> result = new ArrayList<>();
        for (TypeDefinitionModel model : list) {
            result.add(typeDefinitionMapper.toDto(model));
        }
        return result;
    }

    @Override
    public TypeDefinitionDto getTypeDefinition(String typeId) {
        log.info("Fetching type definition by ID: {}", typeId);
        TypeDefinitionModel model = typeDefinitionRepository.findByTypeId(typeId)
                .orElseThrow(() -> new TypeDefinitionNotFoundException("TypeDefinition not found: " + typeId));
        return typeDefinitionMapper.toDto(model);
    }

    @Override
    public TypeDefinitionDto createTypeDefinition(TypeDefinitionDto typeDefinitionDto) {
        log.info("Creating type definition: {}", typeDefinitionDto.getName());
        TypeDefinitionModel entity = typeDefinitionMapper.toEntity(typeDefinitionDto);
        TypeDefinitionModel saved = typeDefinitionRepository.save(entity);
        return typeDefinitionMapper.toDto(saved);
    }
}
