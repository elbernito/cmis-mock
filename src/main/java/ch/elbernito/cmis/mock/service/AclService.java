package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.AclDto;
import java.util.List;

public interface AclService {
    AclDto createAcl(AclDto dto);
    AclDto getAcl(Long id);
    List<AclDto> getAllAcls();
    void deleteAcl(Long id);
    AclDto updateAcl(Long id, AclDto dto);
}
