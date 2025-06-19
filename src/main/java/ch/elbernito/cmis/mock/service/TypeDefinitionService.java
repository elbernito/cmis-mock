package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;

import java.util.List;

/**
 * Service interface for TypeDefinition management.
 */
public interface TypeDefinitionService {

    List<TypeDefinitionDto> getAllTypeDefinitions();

    TypeDefinitionDto getTypeDefinition(String typeId);

    TypeDefinitionDto createTypeDefinition(TypeDefinitionDto typeDefinitionDto);
}
