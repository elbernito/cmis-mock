package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import java.util.List;

public interface TypeDefinitionService {
    TypeDefinitionDto createTypeDefinition(TypeDefinitionDto dto);
    List<TypeDefinitionDto> listTypeDefinitions();
    TypeDefinitionDto getTypeDefinition(String id);
    TypeDefinitionDto updateTypeDefinition(String id, TypeDefinitionDto dto);
    void deleteTypeDefinition(String id);
}
