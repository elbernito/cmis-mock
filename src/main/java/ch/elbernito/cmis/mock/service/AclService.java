package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.AclDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AclService {
    AclDto createAcl(AclDto dto);
    List<AclDto> listAcls(String objectId);
    AclDto getAcl(String id);
    AclDto updateAcl(String id, AclDto dto);
    void deleteAcl(String id);
}
