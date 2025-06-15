package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.DiscoveryDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DiscoveryService {
    DiscoveryDto createDiscovery(DiscoveryDto dto);
    List<DiscoveryDto> listDiscoveries();
    DiscoveryDto getDiscovery(String id);
    DiscoveryDto updateDiscovery(String id, DiscoveryDto dto);
    void deleteDiscovery(String id);
}
