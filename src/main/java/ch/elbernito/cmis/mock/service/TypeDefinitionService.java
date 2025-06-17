package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import java.util.List;

public interface TypeDefinitionService {
    TypeDefinitionDto create(TypeDefinitionDto dto);
    TypeDefinitionDto getById(Long id);
    List<TypeDefinitionDto> getAll();
    TypeDefinitionDto update(Long id, TypeDefinitionDto dto);
    void delete(Long id);
}
