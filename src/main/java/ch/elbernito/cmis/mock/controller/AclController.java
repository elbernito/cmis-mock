package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.service.AclService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acl")
@RequiredArgsConstructor
public class AclController {
    private final Logger log = LoggerFactory.getLogger(AclController.class);
    private final AclService aclService;

    @PostMapping
    public AclDto createAcl(@RequestBody AclDto dto) {
        log.info("REST request to create ACL: {}", dto);
        return aclService.createAcl(dto);
    }

    @GetMapping("/{id}")
    public AclDto getAcl(@PathVariable Long id) {
        log.info("REST request to get ACL: {}", id);
        return aclService.getAcl(id);
    }

    @GetMapping
    public List<AclDto> getAllAcls() {
        log.info("REST request to get all ACLs");
        return aclService.getAllAcls();
    }

    @DeleteMapping("/{id}")
    public void deleteAcl(@PathVariable Long id) {
        log.info("REST request to delete ACL: {}", id);
        aclService.deleteAcl(id);
    }

    @PutMapping("/{id}")
    public AclDto updateAcl(@PathVariable Long id, @RequestBody AclDto dto) {
        log.info("REST request to update ACL: {}", id);
        return aclService.updateAcl(id, dto);
    }
}
